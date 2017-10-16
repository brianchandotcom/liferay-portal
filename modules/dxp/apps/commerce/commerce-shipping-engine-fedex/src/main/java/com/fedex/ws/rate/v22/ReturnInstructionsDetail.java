/**
 * ReturnInstructionsDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * The instructions indicating how to print the return instructions(
 * e.g. image type) Specifies characteristics of a shipping document
 * to be produced.
 */
public class ReturnInstructionsDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.ShippingDocumentFormat format;

    /* Specifies additional customer provided text to be inserted
     * into the return document. */
    private java.lang.String customText;

    public ReturnInstructionsDetail() {
    }

    public ReturnInstructionsDetail(
           com.fedex.ws.rate.v22.ShippingDocumentFormat format,
           java.lang.String customText) {
           this.format = format;
           this.customText = customText;
    }


    /**
     * Gets the format value for this ReturnInstructionsDetail.
     * 
     * @return format
     */
    public com.fedex.ws.rate.v22.ShippingDocumentFormat getFormat() {
        return format;
    }


    /**
     * Sets the format value for this ReturnInstructionsDetail.
     * 
     * @param format
     */
    public void setFormat(com.fedex.ws.rate.v22.ShippingDocumentFormat format) {
        this.format = format;
    }


    /**
     * Gets the customText value for this ReturnInstructionsDetail.
     * 
     * @return customText   * Specifies additional customer provided text to be inserted
     * into the return document.
     */
    public java.lang.String getCustomText() {
        return customText;
    }


    /**
     * Sets the customText value for this ReturnInstructionsDetail.
     * 
     * @param customText   * Specifies additional customer provided text to be inserted
     * into the return document.
     */
    public void setCustomText(java.lang.String customText) {
        this.customText = customText;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReturnInstructionsDetail)) return false;
        ReturnInstructionsDetail other = (ReturnInstructionsDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.customText==null && other.getCustomText()==null) || 
             (this.customText!=null &&
              this.customText.equals(other.getCustomText())));
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
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getCustomText() != null) {
            _hashCode += getCustomText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReturnInstructionsDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ReturnInstructionsDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentFormat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomText"));
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
