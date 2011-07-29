package com.onarandombox.MultiversePortals;

import java.util.Arrays;
import java.util.List;

import org.bukkit.util.Vector;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiversePortals.utils.MultiverseRegion;

public class PortalLocation {

    private MultiverseRegion region;
    private boolean validLocation = false;

    public PortalLocation(Vector pos1, Vector pos2, MVWorld world) {
        this.validLocation = this.setLocation(pos1, pos2, world);;
    }

    public PortalLocation() {
    }

    /**
     * This constructor takes the Vectors from WorldEdit and converts them to Bukkit vectors.
     * 
     * @param minPt
     * @param maxPt
     */
    public PortalLocation(com.sk89q.worldedit.Vector minPt, com.sk89q.worldedit.Vector maxPt, MVWorld world) {
        this(new Vector(minPt.getX(), minPt.getY(), minPt.getZ()), new Vector(maxPt.getX(), maxPt.getY(), maxPt.getZ()), world);
    }

    public static PortalLocation parseLocation(String locationString, MVWorld world) {
        String[] split = locationString.split(":");
        if (split.length != 2 || world == null) {
            System.out.print("MVP - Failed Parsing Portal (World or Length Error: " + locationString);
            return getInvalidPortalLocation();
        }
        
        
        Vector pos1 = parseVector(split[0]);
        Vector pos2 = parseVector(split[1]);

        if (pos1 == null || pos2 == null) {
            System.out.print("MVP - Failed Parsing Portal location (Vector Error): " + locationString);
            return getInvalidPortalLocation();
        }
        return new PortalLocation(pos1, pos2, world);
       

    }

    private static PortalLocation getInvalidPortalLocation() {
        return new PortalLocation();
    }

    private static Vector parseVector(String vectorString) {
        String[] stringCoords = vectorString.split(",");
        double[] coords = new double[3];
        for (int i = 0; i < 3; i++) {
            try {
                coords[i] = Double.parseDouble(stringCoords[i]);
            } catch (NumberFormatException e) {
                coords[i] = 0;
                return null;
            }
        }
        return new Vector(coords[0], coords[1], coords[2]);
    }

    public boolean setLocation(Vector v1, Vector v2, MVWorld world) {
        if (v1 == null || v2 == null || world == null) {
            this.validLocation = false;
            this.region = null;
        } else {
            this.validLocation = true;
            this.region = new MultiverseRegion(v1, v2, world);
        }
        return this.validLocation;
    }

    public boolean setLocation(String v1, String v2, MVWorld world) {
        if (v1 == null || v2 == null) {
            this.validLocation = false;
            this.region = null;
            return false;
        } else {
            return this.setLocation(parseVector(v1), parseVector(v2), world);
        }

    }

    public boolean isValidLocation() {
        return this.validLocation;
    }

    public List<Vector> getVectors() {
        return Arrays.asList(this.region.getMinimumPoint(), this.region.getMaximumPoint());
    }
    
    public Vector getMinimum() {
        return this.region.getMinimumPoint();
    }
    
    public Vector getMaximum() {
        return this.region.getMaximumPoint();
    }

    @Override
    public String toString() {
        if(this.region == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.region.getMinimumPoint().getX() + ",");
        sb.append(this.region.getMinimumPoint().getY() + ",");
        sb.append(this.region.getMinimumPoint().getZ() + ":");
        sb.append(this.region.getMaximumPoint().getX() + ",");
        sb.append(this.region.getMaximumPoint().getY() + ",");
        sb.append(this.region.getMaximumPoint().getZ());
        return sb.toString();
    }

    public MVWorld getMVWorld() {
        return this.region.getWorld();
    }
    
    public MultiverseRegion getRegion() {
        return this.region;
    }

}