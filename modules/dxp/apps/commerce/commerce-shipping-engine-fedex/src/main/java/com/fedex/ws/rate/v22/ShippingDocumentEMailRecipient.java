/**
 * ShippingDocumentEMailRecipient.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Specifies an individual recipient of e-mailed shipping document(s).
 */
public class ShippingDocumentEMailRecipient  implements java.io.Serializable {
    /* Identifies the relationship of this recipient in the shipment. */
    private com.fedex.ws.rate.v22.EMailNotificationRecipientType recipientType;

    /* Address to which the document is to be sent. */
    private java.lang.String address;

    public ShippingDocumentEMailRecipient() {
    }

    public ShippingDocumentEMailRecipient(
           com.fedex.ws.rate.v22.EMailNotificationRecipientType recipientType,
           java.lang.String address) {
           this.recipientType = recipientType;
           this.address = address;
    }


    /**
     * Gets the recipientType value for this ShippingDocumentEMailRecipient.
     * 
     * @return recipientType   * Identifies the relationship of this recipient in the shipment.
     */
    public com.fedex.ws.rate.v22.EMailNotificationRecipientType getRecipientType() {
        return recipientType;
    }


    /**
     * Sets the recipientType value for this ShippingDocumentEMailRecipient.
     * 
     * @param recipientType   * Identifies the relationship of this recipient in the shipment.
     */
    public void setRecipientType(com.fedex.ws.rate.v22.EMailNotificationRecipientType recipientType) {
        this.recipientType = recipientType;
    }


    /**
     * Gets the address value for this ShippingDocumentEMailRecipient.
     * 
     * @return address   * Address to which the document is to be sent.
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this ShippingDocumentEMailRecipient.
     * 
     * @param address   * Address to which the document is to be sent.
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentEMailRecipient)) return false;
        ShippingDocumentEMailRecipient other = (ShippingDocumentEMailRecipient) obj;
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
              this.recipientType.equals(other.getRecipientType()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress())));
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
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentEMailRecipient.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentEMailRecipient"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recipientType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RecipientType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EMailNotificationRecipientType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Address"));
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
