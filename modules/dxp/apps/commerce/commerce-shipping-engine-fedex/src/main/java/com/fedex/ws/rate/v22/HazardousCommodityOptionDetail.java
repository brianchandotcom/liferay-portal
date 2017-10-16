/**
 * HazardousCommodityOptionDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Customer-provided specifications for handling individual commodities.
 */
public class HazardousCommodityOptionDetail  implements java.io.Serializable {
    /* Specifies how the customer wishes the label text to be handled
     * for this commodity in this package. */
    private com.fedex.ws.rate.v22.HazardousCommodityLabelTextOptionType labelTextOption;

    /* Text used in labeling the commodity under control of the labelTextOption
     * field. */
    private java.lang.String customerSuppliedLabelText;

    public HazardousCommodityOptionDetail() {
    }

    public HazardousCommodityOptionDetail(
           com.fedex.ws.rate.v22.HazardousCommodityLabelTextOptionType labelTextOption,
           java.lang.String customerSuppliedLabelText) {
           this.labelTextOption = labelTextOption;
           this.customerSuppliedLabelText = customerSuppliedLabelText;
    }


    /**
     * Gets the labelTextOption value for this HazardousCommodityOptionDetail.
     * 
     * @return labelTextOption   * Specifies how the customer wishes the label text to be handled
     * for this commodity in this package.
     */
    public com.fedex.ws.rate.v22.HazardousCommodityLabelTextOptionType getLabelTextOption() {
        return labelTextOption;
    }


    /**
     * Sets the labelTextOption value for this HazardousCommodityOptionDetail.
     * 
     * @param labelTextOption   * Specifies how the customer wishes the label text to be handled
     * for this commodity in this package.
     */
    public void setLabelTextOption(com.fedex.ws.rate.v22.HazardousCommodityLabelTextOptionType labelTextOption) {
        this.labelTextOption = labelTextOption;
    }


    /**
     * Gets the customerSuppliedLabelText value for this HazardousCommodityOptionDetail.
     * 
     * @return customerSuppliedLabelText   * Text used in labeling the commodity under control of the labelTextOption
     * field.
     */
    public java.lang.String getCustomerSuppliedLabelText() {
        return customerSuppliedLabelText;
    }


    /**
     * Sets the customerSuppliedLabelText value for this HazardousCommodityOptionDetail.
     * 
     * @param customerSuppliedLabelText   * Text used in labeling the commodity under control of the labelTextOption
     * field.
     */
    public void setCustomerSuppliedLabelText(java.lang.String customerSuppliedLabelText) {
        this.customerSuppliedLabelText = customerSuppliedLabelText;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HazardousCommodityOptionDetail)) return false;
        HazardousCommodityOptionDetail other = (HazardousCommodityOptionDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.labelTextOption==null && other.getLabelTextOption()==null) || 
             (this.labelTextOption!=null &&
              this.labelTextOption.equals(other.getLabelTextOption()))) &&
            ((this.customerSuppliedLabelText==null && other.getCustomerSuppliedLabelText()==null) || 
             (this.customerSuppliedLabelText!=null &&
              this.customerSuppliedLabelText.equals(other.getCustomerSuppliedLabelText())));
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
        if (getLabelTextOption() != null) {
            _hashCode += getLabelTextOption().hashCode();
        }
        if (getCustomerSuppliedLabelText() != null) {
            _hashCode += getCustomerSuppliedLabelText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HazardousCommodityOptionDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodityOptionDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("labelTextOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LabelTextOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodityLabelTextOptionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerSuppliedLabelText");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerSuppliedLabelText"));
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
