/**
 * ShipmentDryIceDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Shipment-level totals of dry ice data across all packages.
 */
public class ShipmentDryIceDetail  implements java.io.Serializable {
    /* Total number of packages in the shipment that contain dry ice. */
    private org.apache.axis.types.NonNegativeInteger packageCount;

    /* Total shipment dry ice weight for all packages. */
    private com.fedex.ws.rate.v22.Weight totalWeight;

    private com.fedex.ws.rate.v22.ShipmentDryIceProcessingOptionType[] processingOptions;

    public ShipmentDryIceDetail() {
    }

    public ShipmentDryIceDetail(
           org.apache.axis.types.NonNegativeInteger packageCount,
           com.fedex.ws.rate.v22.Weight totalWeight,
           com.fedex.ws.rate.v22.ShipmentDryIceProcessingOptionType[] processingOptions) {
           this.packageCount = packageCount;
           this.totalWeight = totalWeight;
           this.processingOptions = processingOptions;
    }


    /**
     * Gets the packageCount value for this ShipmentDryIceDetail.
     * 
     * @return packageCount   * Total number of packages in the shipment that contain dry ice.
     */
    public org.apache.axis.types.NonNegativeInteger getPackageCount() {
        return packageCount;
    }


    /**
     * Sets the packageCount value for this ShipmentDryIceDetail.
     * 
     * @param packageCount   * Total number of packages in the shipment that contain dry ice.
     */
    public void setPackageCount(org.apache.axis.types.NonNegativeInteger packageCount) {
        this.packageCount = packageCount;
    }


    /**
     * Gets the totalWeight value for this ShipmentDryIceDetail.
     * 
     * @return totalWeight   * Total shipment dry ice weight for all packages.
     */
    public com.fedex.ws.rate.v22.Weight getTotalWeight() {
        return totalWeight;
    }


    /**
     * Sets the totalWeight value for this ShipmentDryIceDetail.
     * 
     * @param totalWeight   * Total shipment dry ice weight for all packages.
     */
    public void setTotalWeight(com.fedex.ws.rate.v22.Weight totalWeight) {
        this.totalWeight = totalWeight;
    }


    /**
     * Gets the processingOptions value for this ShipmentDryIceDetail.
     * 
     * @return processingOptions
     */
    public com.fedex.ws.rate.v22.ShipmentDryIceProcessingOptionType[] getProcessingOptions() {
        return processingOptions;
    }


    /**
     * Sets the processingOptions value for this ShipmentDryIceDetail.
     * 
     * @param processingOptions
     */
    public void setProcessingOptions(com.fedex.ws.rate.v22.ShipmentDryIceProcessingOptionType[] processingOptions) {
        this.processingOptions = processingOptions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentDryIceDetail)) return false;
        ShipmentDryIceDetail other = (ShipmentDryIceDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.packageCount==null && other.getPackageCount()==null) || 
             (this.packageCount!=null &&
              this.packageCount.equals(other.getPackageCount()))) &&
            ((this.totalWeight==null && other.getTotalWeight()==null) || 
             (this.totalWeight!=null &&
              this.totalWeight.equals(other.getTotalWeight()))) &&
            ((this.processingOptions==null && other.getProcessingOptions()==null) || 
             (this.processingOptions!=null &&
              java.util.Arrays.equals(this.processingOptions, other.getProcessingOptions())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPackageCount() != null) {
            _hashCode += getPackageCount().hashCode();
        }
        if (getTotalWeight() != null) {
            _hashCode += getTotalWeight().hashCode();
        }
        if (getProcessingOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProcessingOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProcessingOptions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentDryIceDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentDryIceDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processingOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProcessingOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentDryIceProcessingOptionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Options"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
