/**
 * WebAuthenticationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Used in authentication of the sender's identity.
 */
public class WebAuthenticationDetail  implements java.io.Serializable {
    /* This was renamed from cspCredential. */
    private com.fedex.ws.rate.v22.WebAuthenticationCredential parentCredential;

    /* Credential used to authenticate a specific software application.
     * This value is provided by FedEx after registration. */
    private com.fedex.ws.rate.v22.WebAuthenticationCredential userCredential;

    public WebAuthenticationDetail() {
    }

    public WebAuthenticationDetail(
           com.fedex.ws.rate.v22.WebAuthenticationCredential parentCredential,
           com.fedex.ws.rate.v22.WebAuthenticationCredential userCredential) {
           this.parentCredential = parentCredential;
           this.userCredential = userCredential;
    }


    /**
     * Gets the parentCredential value for this WebAuthenticationDetail.
     * 
     * @return parentCredential   * This was renamed from cspCredential.
     */
    public com.fedex.ws.rate.v22.WebAuthenticationCredential getParentCredential() {
        return parentCredential;
    }


    /**
     * Sets the parentCredential value for this WebAuthenticationDetail.
     * 
     * @param parentCredential   * This was renamed from cspCredential.
     */
    public void setParentCredential(com.fedex.ws.rate.v22.WebAuthenticationCredential parentCredential) {
        this.parentCredential = parentCredential;
    }


    /**
     * Gets the userCredential value for this WebAuthenticationDetail.
     * 
     * @return userCredential   * Credential used to authenticate a specific software application.
     * This value is provided by FedEx after registration.
     */
    public com.fedex.ws.rate.v22.WebAuthenticationCredential getUserCredential() {
        return userCredential;
    }


    /**
     * Sets the userCredential value for this WebAuthenticationDetail.
     * 
     * @param userCredential   * Credential used to authenticate a specific software application.
     * This value is provided by FedEx after registration.
     */
    public void setUserCredential(com.fedex.ws.rate.v22.WebAuthenticationCredential userCredential) {
        this.userCredential = userCredential;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WebAuthenticationDetail)) return false;
        WebAuthenticationDetail other = (WebAuthenticationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.parentCredential==null && other.getParentCredential()==null) || 
             (this.parentCredential!=null &&
              this.parentCredential.equals(other.getParentCredential()))) &&
            ((this.userCredential==null && other.getUserCredential()==null) || 
             (this.userCredential!=null &&
              this.userCredential.equals(other.getUserCredential())));
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
        if (getParentCredential() != null) {
            _hashCode += getParentCredential().hashCode();
        }
        if (getUserCredential() != null) {
            _hashCode += getUserCredential().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WebAuthenticationDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "WebAuthenticationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentCredential");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ParentCredential"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "WebAuthenticationCredential"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userCredential");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UserCredential"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "WebAuthenticationCredential"));
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
