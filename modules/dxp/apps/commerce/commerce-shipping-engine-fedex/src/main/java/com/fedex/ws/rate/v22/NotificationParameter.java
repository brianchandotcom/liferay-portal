/**
 * NotificationParameter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class NotificationParameter  implements java.io.Serializable {
    /* Identifies the type of data contained in Value (e.g. SERVICE_TYPE,
     * PACKAGE_SEQUENCE, etc..). */
    private java.lang.String id;

    /* The value of the parameter (e.g. PRIORITY_OVERNIGHT, 2, etc..). */
    private java.lang.String value;

    public NotificationParameter() {
    }

    public NotificationParameter(
           java.lang.String id,
           java.lang.String value) {
           this.id = id;
           this.value = value;
    }


    /**
     * Gets the id value for this NotificationParameter.
     * 
     * @return id   * Identifies the type of data contained in Value (e.g. SERVICE_TYPE,
     * PACKAGE_SEQUENCE, etc..).
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this NotificationParameter.
     * 
     * @param id   * Identifies the type of data contained in Value (e.g. SERVICE_TYPE,
     * PACKAGE_SEQUENCE, etc..).
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the value value for this NotificationParameter.
     * 
     * @return value   * The value of the parameter (e.g. PRIORITY_OVERNIGHT, 2, etc..).
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this NotificationParameter.
     * 
     * @param value   * The value of the parameter (e.g. PRIORITY_OVERNIGHT, 2, etc..).
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificationParameter)) return false;
        NotificationParameter other = (NotificationParameter) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificationParameter.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationParameter"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
