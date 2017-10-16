/**
 * BatteryClassificationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Describes attributes of a battery or cell that are used for classification
 * purposes. Typically this structure would be used to allow customers
 * to declare batteries or cells for which full dangerous goods documentation
 * and procedures are not required.
 */
public class BatteryClassificationDetail  implements java.io.Serializable {
    /* Describes the material composition of the battery or cell. */
    private com.fedex.ws.rate.v22.BatteryMaterialType material;

    /* Describes the packing arrangement of the battery or cell with
     * respect to other items within the same package. */
    private com.fedex.ws.rate.v22.BatteryPackingType packing;

    /* A regulation specific classification for the battery or cell. */
    private com.fedex.ws.rate.v22.BatteryRegulatorySubType regulatorySubType;

    public BatteryClassificationDetail() {
    }

    public BatteryClassificationDetail(
           com.fedex.ws.rate.v22.BatteryMaterialType material,
           com.fedex.ws.rate.v22.BatteryPackingType packing,
           com.fedex.ws.rate.v22.BatteryRegulatorySubType regulatorySubType) {
           this.material = material;
           this.packing = packing;
           this.regulatorySubType = regulatorySubType;
    }


    /**
     * Gets the material value for this BatteryClassificationDetail.
     * 
     * @return material   * Describes the material composition of the battery or cell.
     */
    public com.fedex.ws.rate.v22.BatteryMaterialType getMaterial() {
        return material;
    }


    /**
     * Sets the material value for this BatteryClassificationDetail.
     * 
     * @param material   * Describes the material composition of the battery or cell.
     */
    public void setMaterial(com.fedex.ws.rate.v22.BatteryMaterialType material) {
        this.material = material;
    }


    /**
     * Gets the packing value for this BatteryClassificationDetail.
     * 
     * @return packing   * Describes the packing arrangement of the battery or cell with
     * respect to other items within the same package.
     */
    public com.fedex.ws.rate.v22.BatteryPackingType getPacking() {
        return packing;
    }


    /**
     * Sets the packing value for this BatteryClassificationDetail.
     * 
     * @param packing   * Describes the packing arrangement of the battery or cell with
     * respect to other items within the same package.
     */
    public void setPacking(com.fedex.ws.rate.v22.BatteryPackingType packing) {
        this.packing = packing;
    }


    /**
     * Gets the regulatorySubType value for this BatteryClassificationDetail.
     * 
     * @return regulatorySubType   * A regulation specific classification for the battery or cell.
     */
    public com.fedex.ws.rate.v22.BatteryRegulatorySubType getRegulatorySubType() {
        return regulatorySubType;
    }


    /**
     * Sets the regulatorySubType value for this BatteryClassificationDetail.
     * 
     * @param regulatorySubType   * A regulation specific classification for the battery or cell.
     */
    public void setRegulatorySubType(com.fedex.ws.rate.v22.BatteryRegulatorySubType regulatorySubType) {
        this.regulatorySubType = regulatorySubType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BatteryClassificationDetail)) return false;
        BatteryClassificationDetail other = (BatteryClassificationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.material==null && other.getMaterial()==null) || 
             (this.material!=null &&
              this.material.equals(other.getMaterial()))) &&
            ((this.packing==null && other.getPacking()==null) || 
             (this.packing!=null &&
              this.packing.equals(other.getPacking()))) &&
            ((this.regulatorySubType==null && other.getRegulatorySubType()==null) || 
             (this.regulatorySubType!=null &&
              this.regulatorySubType.equals(other.getRegulatorySubType())));
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
        if (getMaterial() != null) {
            _hashCode += getMaterial().hashCode();
        }
        if (getPacking() != null) {
            _hashCode += getPacking().hashCode();
        }
        if (getRegulatorySubType() != null) {
            _hashCode += getRegulatorySubType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BatteryClassificationDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BatteryClassificationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("material");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Material"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BatteryMaterialType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packing");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Packing"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BatteryPackingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regulatorySubType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RegulatorySubType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BatteryRegulatorySubType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
