/**
 * CustomLabelBoxEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Solid (filled) rectangular area on label.
 */
public class CustomLabelBoxEntry  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.CustomLabelPosition topLeftCorner;

    private com.fedex.ws.rate.v22.CustomLabelPosition bottomRightCorner;

    public CustomLabelBoxEntry() {
    }

    public CustomLabelBoxEntry(
           com.fedex.ws.rate.v22.CustomLabelPosition topLeftCorner,
           com.fedex.ws.rate.v22.CustomLabelPosition bottomRightCorner) {
           this.topLeftCorner = topLeftCorner;
           this.bottomRightCorner = bottomRightCorner;
    }


    /**
     * Gets the topLeftCorner value for this CustomLabelBoxEntry.
     * 
     * @return topLeftCorner
     */
    public com.fedex.ws.rate.v22.CustomLabelPosition getTopLeftCorner() {
        return topLeftCorner;
    }


    /**
     * Sets the topLeftCorner value for this CustomLabelBoxEntry.
     * 
     * @param topLeftCorner
     */
    public void setTopLeftCorner(com.fedex.ws.rate.v22.CustomLabelPosition topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }


    /**
     * Gets the bottomRightCorner value for this CustomLabelBoxEntry.
     * 
     * @return bottomRightCorner
     */
    public com.fedex.ws.rate.v22.CustomLabelPosition getBottomRightCorner() {
        return bottomRightCorner;
    }


    /**
     * Sets the bottomRightCorner value for this CustomLabelBoxEntry.
     * 
     * @param bottomRightCorner
     */
    public void setBottomRightCorner(com.fedex.ws.rate.v22.CustomLabelPosition bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomLabelBoxEntry)) return false;
        CustomLabelBoxEntry other = (CustomLabelBoxEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.topLeftCorner==null && other.getTopLeftCorner()==null) || 
             (this.topLeftCorner!=null &&
              this.topLeftCorner.equals(other.getTopLeftCorner()))) &&
            ((this.bottomRightCorner==null && other.getBottomRightCorner()==null) || 
             (this.bottomRightCorner!=null &&
              this.bottomRightCorner.equals(other.getBottomRightCorner())));
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
        if (getTopLeftCorner() != null) {
            _hashCode += getTopLeftCorner().hashCode();
        }
        if (getBottomRightCorner() != null) {
            _hashCode += getBottomRightCorner().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomLabelBoxEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelBoxEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topLeftCorner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TopLeftCorner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelPosition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bottomRightCorner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BottomRightCorner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelPosition"));
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
