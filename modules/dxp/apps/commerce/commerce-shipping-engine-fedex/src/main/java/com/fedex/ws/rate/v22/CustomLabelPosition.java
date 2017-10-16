/**
 * CustomLabelPosition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CustomLabelPosition  implements java.io.Serializable {
    /* Horizontal position, relative to left edge of custom area. */
    private org.apache.axis.types.NonNegativeInteger x;

    /* Vertical position, relative to top edge of custom area. */
    private java.lang.Integer y;

    public CustomLabelPosition() {
    }

    public CustomLabelPosition(
           org.apache.axis.types.NonNegativeInteger x,
           java.lang.Integer y) {
           this.x = x;
           this.y = y;
    }


    /**
     * Gets the x value for this CustomLabelPosition.
     * 
     * @return x   * Horizontal position, relative to left edge of custom area.
     */
    public org.apache.axis.types.NonNegativeInteger getX() {
        return x;
    }


    /**
     * Sets the x value for this CustomLabelPosition.
     * 
     * @param x   * Horizontal position, relative to left edge of custom area.
     */
    public void setX(org.apache.axis.types.NonNegativeInteger x) {
        this.x = x;
    }


    /**
     * Gets the y value for this CustomLabelPosition.
     * 
     * @return y   * Vertical position, relative to top edge of custom area.
     */
    public java.lang.Integer getY() {
        return y;
    }


    /**
     * Sets the y value for this CustomLabelPosition.
     * 
     * @param y   * Vertical position, relative to top edge of custom area.
     */
    public void setY(java.lang.Integer y) {
        this.y = y;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomLabelPosition)) return false;
        CustomLabelPosition other = (CustomLabelPosition) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.x==null && other.getX()==null) || 
             (this.x!=null &&
              this.x.equals(other.getX()))) &&
            ((this.y==null && other.getY()==null) || 
             (this.y!=null &&
              this.y.equals(other.getY())));
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
        if (getX() != null) {
            _hashCode += getX().hashCode();
        }
        if (getY() != null) {
            _hashCode += getY().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomLabelPosition.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelPosition"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("x");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "X"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("y");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Y"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
