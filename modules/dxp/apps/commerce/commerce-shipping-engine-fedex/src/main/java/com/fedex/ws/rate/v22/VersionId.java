/**
 * VersionId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Identifies the version/level of a service operation expected by
 * a caller (in each request) and performed by the callee (in each reply).
 */
public class VersionId  implements java.io.Serializable {
    /* Identifies a system or sub-system which performs an operation. */
    private java.lang.String serviceId;

    /* Identifies the service business level. */
    private int major;

    /* Identifies the service interface level. */
    private int intermediate;

    /* Identifies the service code level. */
    private int minor;

    public VersionId() {
    }

    public VersionId(
           java.lang.String serviceId,
           int major,
           int intermediate,
           int minor) {
           this.serviceId = serviceId;
           this.major = major;
           this.intermediate = intermediate;
           this.minor = minor;
    }


    /**
     * Gets the serviceId value for this VersionId.
     * 
     * @return serviceId   * Identifies a system or sub-system which performs an operation.
     */
    public java.lang.String getServiceId() {
        return serviceId;
    }


    /**
     * Sets the serviceId value for this VersionId.
     * 
     * @param serviceId   * Identifies a system or sub-system which performs an operation.
     */
    public void setServiceId(java.lang.String serviceId) {
        this.serviceId = serviceId;
    }


    /**
     * Gets the major value for this VersionId.
     * 
     * @return major   * Identifies the service business level.
     */
    public int getMajor() {
        return major;
    }


    /**
     * Sets the major value for this VersionId.
     * 
     * @param major   * Identifies the service business level.
     */
    public void setMajor(int major) {
        this.major = major;
    }


    /**
     * Gets the intermediate value for this VersionId.
     * 
     * @return intermediate   * Identifies the service interface level.
     */
    public int getIntermediate() {
        return intermediate;
    }


    /**
     * Sets the intermediate value for this VersionId.
     * 
     * @param intermediate   * Identifies the service interface level.
     */
    public void setIntermediate(int intermediate) {
        this.intermediate = intermediate;
    }


    /**
     * Gets the minor value for this VersionId.
     * 
     * @return minor   * Identifies the service code level.
     */
    public int getMinor() {
        return minor;
    }


    /**
     * Sets the minor value for this VersionId.
     * 
     * @param minor   * Identifies the service code level.
     */
    public void setMinor(int minor) {
        this.minor = minor;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VersionId)) return false;
        VersionId other = (VersionId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceId==null && other.getServiceId()==null) || 
             (this.serviceId!=null &&
              this.serviceId.equals(other.getServiceId()))) &&
            this.major == other.getMajor() &&
            this.intermediate == other.getIntermediate() &&
            this.minor == other.getMinor();
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
        if (getServiceId() != null) {
            _hashCode += getServiceId().hashCode();
        }
        _hashCode += getMajor();
        _hashCode += getIntermediate();
        _hashCode += getMinor();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VersionId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VersionId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("major");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Major"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intermediate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Intermediate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Minor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
