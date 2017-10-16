/**
 * TransactionDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class TransactionDetail  implements java.io.Serializable {
    /* Free form text to be echoed back in the reply. Used to match
     * requests and replies. */
    private java.lang.String customerTransactionId;

    /* Governs data payload language/translations (contrasted with
     * ClientDetail.localization, which governs Notification.localizedMessage
     * language selection). */
    private com.fedex.ws.rate.v22.Localization localization;

    public TransactionDetail() {
    }

    public TransactionDetail(
           java.lang.String customerTransactionId,
           com.fedex.ws.rate.v22.Localization localization) {
           this.customerTransactionId = customerTransactionId;
           this.localization = localization;
    }


    /**
     * Gets the customerTransactionId value for this TransactionDetail.
     * 
     * @return customerTransactionId   * Free form text to be echoed back in the reply. Used to match
     * requests and replies.
     */
    public java.lang.String getCustomerTransactionId() {
        return customerTransactionId;
    }


    /**
     * Sets the customerTransactionId value for this TransactionDetail.
     * 
     * @param customerTransactionId   * Free form text to be echoed back in the reply. Used to match
     * requests and replies.
     */
    public void setCustomerTransactionId(java.lang.String customerTransactionId) {
        this.customerTransactionId = customerTransactionId;
    }


    /**
     * Gets the localization value for this TransactionDetail.
     * 
     * @return localization   * Governs data payload language/translations (contrasted with
     * ClientDetail.localization, which governs Notification.localizedMessage
     * language selection).
     */
    public com.fedex.ws.rate.v22.Localization getLocalization() {
        return localization;
    }


    /**
     * Sets the localization value for this TransactionDetail.
     * 
     * @param localization   * Governs data payload language/translations (contrasted with
     * ClientDetail.localization, which governs Notification.localizedMessage
     * language selection).
     */
    public void setLocalization(com.fedex.ws.rate.v22.Localization localization) {
        this.localization = localization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TransactionDetail)) return false;
        TransactionDetail other = (TransactionDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.customerTransactionId==null && other.getCustomerTransactionId()==null) || 
             (this.customerTransactionId!=null &&
              this.customerTransactionId.equals(other.getCustomerTransactionId()))) &&
            ((this.localization==null && other.getLocalization()==null) || 
             (this.localization!=null &&
              this.localization.equals(other.getLocalization())));
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
        if (getCustomerTransactionId() != null) {
            _hashCode += getCustomerTransactionId().hashCode();
        }
        if (getLocalization() != null) {
            _hashCode += getLocalization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TransactionDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TransactionDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerTransactionId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerTransactionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Localization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Localization"));
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
