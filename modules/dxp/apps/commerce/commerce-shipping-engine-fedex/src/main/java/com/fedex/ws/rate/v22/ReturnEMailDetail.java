/**
 * ReturnEMailDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class ReturnEMailDetail  implements java.io.Serializable {
    private java.lang.String merchantPhoneNumber;

    /* Identifies the allowed (merchant-authorized) special services
     * which may be selected when the subsequent shipment is created. Only
     * services represented in EMailLabelAllowedSpecialServiceType will be
     * controlled by this list. */
    private com.fedex.ws.rate.v22.ReturnEMailAllowedSpecialServiceType[] allowedSpecialServices;

    public ReturnEMailDetail() {
    }

    public ReturnEMailDetail(
           java.lang.String merchantPhoneNumber,
           com.fedex.ws.rate.v22.ReturnEMailAllowedSpecialServiceType[] allowedSpecialServices) {
           this.merchantPhoneNumber = merchantPhoneNumber;
           this.allowedSpecialServices = allowedSpecialServices;
    }


    /**
     * Gets the merchantPhoneNumber value for this ReturnEMailDetail.
     * 
     * @return merchantPhoneNumber
     */
    public java.lang.String getMerchantPhoneNumber() {
        return merchantPhoneNumber;
    }


    /**
     * Sets the merchantPhoneNumber value for this ReturnEMailDetail.
     * 
     * @param merchantPhoneNumber
     */
    public void setMerchantPhoneNumber(java.lang.String merchantPhoneNumber) {
        this.merchantPhoneNumber = merchantPhoneNumber;
    }


    /**
     * Gets the allowedSpecialServices value for this ReturnEMailDetail.
     * 
     * @return allowedSpecialServices   * Identifies the allowed (merchant-authorized) special services
     * which may be selected when the subsequent shipment is created. Only
     * services represented in EMailLabelAllowedSpecialServiceType will be
     * controlled by this list.
     */
    public com.fedex.ws.rate.v22.ReturnEMailAllowedSpecialServiceType[] getAllowedSpecialServices() {
        return allowedSpecialServices;
    }


    /**
     * Sets the allowedSpecialServices value for this ReturnEMailDetail.
     * 
     * @param allowedSpecialServices   * Identifies the allowed (merchant-authorized) special services
     * which may be selected when the subsequent shipment is created. Only
     * services represented in EMailLabelAllowedSpecialServiceType will be
     * controlled by this list.
     */
    public void setAllowedSpecialServices(com.fedex.ws.rate.v22.ReturnEMailAllowedSpecialServiceType[] allowedSpecialServices) {
        this.allowedSpecialServices = allowedSpecialServices;
    }

    public com.fedex.ws.rate.v22.ReturnEMailAllowedSpecialServiceType getAllowedSpecialServices(int i) {
        return this.allowedSpecialServices[i];
    }

    public void setAllowedSpecialServices(int i, com.fedex.ws.rate.v22.ReturnEMailAllowedSpecialServiceType _value) {
        this.allowedSpecialServices[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReturnEMailDetail)) return false;
        ReturnEMailDetail other = (ReturnEMailDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.merchantPhoneNumber==null && other.getMerchantPhoneNumber()==null) || 
             (this.merchantPhoneNumber!=null &&
              this.merchantPhoneNumber.equals(other.getMerchantPhoneNumber()))) &&
            ((this.allowedSpecialServices==null && other.getAllowedSpecialServices()==null) || 
             (this.allowedSpecialServices!=null &&
              java.util.Arrays.equals(this.allowedSpecialServices, other.getAllowedSpecialServices())));
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
        if (getMerchantPhoneNumber() != null) {
            _hashCode += getMerchantPhoneNumber().hashCode();
        }
        if (getAllowedSpecialServices() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAllowedSpecialServices());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAllowedSpecialServices(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReturnEMailDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ReturnEMailDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("merchantPhoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "MerchantPhoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allowedSpecialServices");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AllowedSpecialServices"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ReturnEMailAllowedSpecialServiceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
