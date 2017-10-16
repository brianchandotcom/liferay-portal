/**
 * Dimensions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class Dimensions  implements java.io.Serializable {
    private org.apache.axis.types.NonNegativeInteger length;

    private org.apache.axis.types.NonNegativeInteger width;

    private org.apache.axis.types.NonNegativeInteger height;

    private com.fedex.ws.rate.v22.LinearUnits units;

    public Dimensions() {
    }

    public Dimensions(
           org.apache.axis.types.NonNegativeInteger length,
           org.apache.axis.types.NonNegativeInteger width,
           org.apache.axis.types.NonNegativeInteger height,
           com.fedex.ws.rate.v22.LinearUnits units) {
           this.length = length;
           this.width = width;
           this.height = height;
           this.units = units;
    }


    /**
     * Gets the length value for this Dimensions.
     * 
     * @return length
     */
    public org.apache.axis.types.NonNegativeInteger getLength() {
        return length;
    }


    /**
     * Sets the length value for this Dimensions.
     * 
     * @param length
     */
    public void setLength(org.apache.axis.types.NonNegativeInteger length) {
        this.length = length;
    }


    /**
     * Gets the width value for this Dimensions.
     * 
     * @return width
     */
    public org.apache.axis.types.NonNegativeInteger getWidth() {
        return width;
    }


    /**
     * Sets the width value for this Dimensions.
     * 
     * @param width
     */
    public void setWidth(org.apache.axis.types.NonNegativeInteger width) {
        this.width = width;
    }


    /**
     * Gets the height value for this Dimensions.
     * 
     * @return height
     */
    public org.apache.axis.types.NonNegativeInteger getHeight() {
        return height;
    }


    /**
     * Sets the height value for this Dimensions.
     * 
     * @param height
     */
    public void setHeight(org.apache.axis.types.NonNegativeInteger height) {
        this.height = height;
    }


    /**
     * Gets the units value for this Dimensions.
     * 
     * @return units
     */
    public com.fedex.ws.rate.v22.LinearUnits getUnits() {
        return units;
    }


    /**
     * Sets the units value for this Dimensions.
     * 
     * @param units
     */
    public void setUnits(com.fedex.ws.rate.v22.LinearUnits units) {
        this.units = units;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Dimensions)) return false;
        Dimensions other = (Dimensions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.length==null && other.getLength()==null) || 
             (this.length!=null &&
              this.length.equals(other.getLength()))) &&
            ((this.width==null && other.getWidth()==null) || 
             (this.width!=null &&
              this.width.equals(other.getWidth()))) &&
            ((this.height==null && other.getHeight()==null) || 
             (this.height!=null &&
              this.height.equals(other.getHeight()))) &&
            ((this.units==null && other.getUnits()==null) || 
             (this.units!=null &&
              this.units.equals(other.getUnits())));
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
        if (getLength() != null) {
            _hashCode += getLength().hashCode();
        }
        if (getWidth() != null) {
            _hashCode += getWidth().hashCode();
        }
        if (getHeight() != null) {
            _hashCode += getHeight().hashCode();
        }
        if (getUnits() != null) {
            _hashCode += getUnits().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Dimensions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("length");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Length"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("width");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Width"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("height");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Height"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("units");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Units"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LinearUnits"));
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
