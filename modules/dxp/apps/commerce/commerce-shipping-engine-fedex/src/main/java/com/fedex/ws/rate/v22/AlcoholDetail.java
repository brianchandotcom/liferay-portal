/**
 * AlcoholDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Specifies details for a package containing alcohol
 */
public class AlcoholDetail  implements java.io.Serializable {
    /* The license type that the recipient of the alcohol package. */
    private com.fedex.ws.rate.v22.AlcoholRecipientType recipientType;

    public AlcoholDetail() {
    }

    public AlcoholDetail(
           com.fedex.ws.rate.v22.AlcoholRecipientType recipientType) {
           this.recipientType = recipientType;
    }


    /**
     * Gets the recipientType value for this AlcoholDetail.
     * 
     * @return recipientType   * The license type that the recipient of the alcohol package.
     */
    public com.fedex.ws.rate.v22.AlcoholRecipientType getRecipientType() {
        return recipientType;
    }


    /**
     * Sets the recipientType value for this AlcoholDetail.
     * 
     * @param recipientType   * The license type that the recipient of the alcohol package.
     */
    public void setRecipientType(com.fedex.ws.rate.v22.AlcoholRecipientType recipientType) {
        this.recipientType = recipientType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AlcoholDetail)) return false;
        AlcoholDetail other = (AlcoholDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.recipientType==null && other.getRecipientType()==null) || 
             (this.recipientType!=null &&
              this.recipientType.equals(other.getRecipientType())));
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
        if (getRecipientType() != null) {
            _hashCode += getRecipientType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AlcoholDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AlcoholDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RecipientType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AlcoholRecipientType"));
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
