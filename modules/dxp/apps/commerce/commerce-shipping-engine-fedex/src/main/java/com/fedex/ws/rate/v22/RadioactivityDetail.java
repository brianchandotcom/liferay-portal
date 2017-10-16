/**
 * RadioactivityDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RadioactivityDetail  implements java.io.Serializable {
    private java.math.BigDecimal transportIndex;

    private java.math.BigDecimal surfaceReading;

    private java.math.BigDecimal criticalitySafetyIndex;

    private com.fedex.ws.rate.v22.Dimensions dimensions;

    public RadioactivityDetail() {
    }

    public RadioactivityDetail(
           java.math.BigDecimal transportIndex,
           java.math.BigDecimal surfaceReading,
           java.math.BigDecimal criticalitySafetyIndex,
           com.fedex.ws.rate.v22.Dimensions dimensions) {
           this.transportIndex = transportIndex;
           this.surfaceReading = surfaceReading;
           this.criticalitySafetyIndex = criticalitySafetyIndex;
           this.dimensions = dimensions;
    }


    /**
     * Gets the transportIndex value for this RadioactivityDetail.
     * 
     * @return transportIndex
     */
    public java.math.BigDecimal getTransportIndex() {
        return transportIndex;
    }


    /**
     * Sets the transportIndex value for this RadioactivityDetail.
     * 
     * @param transportIndex
     */
    public void setTransportIndex(java.math.BigDecimal transportIndex) {
        this.transportIndex = transportIndex;
    }


    /**
     * Gets the surfaceReading value for this RadioactivityDetail.
     * 
     * @return surfaceReading
     */
    public java.math.BigDecimal getSurfaceReading() {
        return surfaceReading;
    }


    /**
     * Sets the surfaceReading value for this RadioactivityDetail.
     * 
     * @param surfaceReading
     */
    public void setSurfaceReading(java.math.BigDecimal surfaceReading) {
        this.surfaceReading = surfaceReading;
    }


    /**
     * Gets the criticalitySafetyIndex value for this RadioactivityDetail.
     * 
     * @return criticalitySafetyIndex
     */
    public java.math.BigDecimal getCriticalitySafetyIndex() {
        return criticalitySafetyIndex;
    }


    /**
     * Sets the criticalitySafetyIndex value for this RadioactivityDetail.
     * 
     * @param criticalitySafetyIndex
     */
    public void setCriticalitySafetyIndex(java.math.BigDecimal criticalitySafetyIndex) {
        this.criticalitySafetyIndex = criticalitySafetyIndex;
    }


    /**
     * Gets the dimensions value for this RadioactivityDetail.
     * 
     * @return dimensions
     */
    public com.fedex.ws.rate.v22.Dimensions getDimensions() {
        return dimensions;
    }


    /**
     * Sets the dimensions value for this RadioactivityDetail.
     * 
     * @param dimensions
     */
    public void setDimensions(com.fedex.ws.rate.v22.Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RadioactivityDetail)) return false;
        RadioactivityDetail other = (RadioactivityDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.transportIndex==null && other.getTransportIndex()==null) || 
             (this.transportIndex!=null &&
              this.transportIndex.equals(other.getTransportIndex()))) &&
            ((this.surfaceReading==null && other.getSurfaceReading()==null) || 
             (this.surfaceReading!=null &&
              this.surfaceReading.equals(other.getSurfaceReading()))) &&
            ((this.criticalitySafetyIndex==null && other.getCriticalitySafetyIndex()==null) || 
             (this.criticalitySafetyIndex!=null &&
              this.criticalitySafetyIndex.equals(other.getCriticalitySafetyIndex()))) &&
            ((this.dimensions==null && other.getDimensions()==null) || 
             (this.dimensions!=null &&
              this.dimensions.equals(other.getDimensions())));
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
        if (getTransportIndex() != null) {
            _hashCode += getTransportIndex().hashCode();
        }
        if (getSurfaceReading() != null) {
            _hashCode += getSurfaceReading().hashCode();
        }
        if (getCriticalitySafetyIndex() != null) {
            _hashCode += getCriticalitySafetyIndex().hashCode();
        }
        if (getDimensions() != null) {
            _hashCode += getDimensions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RadioactivityDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RadioactivityDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transportIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TransportIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surfaceReading");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SurfaceReading"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("criticalitySafetyIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CriticalitySafetyIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
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
