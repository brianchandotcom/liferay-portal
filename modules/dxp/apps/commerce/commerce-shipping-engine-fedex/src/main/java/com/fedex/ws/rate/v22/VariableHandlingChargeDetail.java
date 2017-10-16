/**
 * VariableHandlingChargeDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * This definition of variable handling charge detail is intended
 * for use in Jan 2011 corp load.
 */
public class VariableHandlingChargeDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.Money fixedValue;

    /* Actual percentage (10 means 10%, which is a mutiplier of 0.1) */
    private java.math.BigDecimal percentValue;

    /* Select the value from a set of rate data to which the percentage
     * is applied. */
    private com.fedex.ws.rate.v22.RateElementBasisType rateElementBasis;

    /* Select the type of rate from which the element is to be selected. */
    private com.fedex.ws.rate.v22.RateTypeBasisType rateTypeBasis;

    public VariableHandlingChargeDetail() {
    }

    public VariableHandlingChargeDetail(
           com.fedex.ws.rate.v22.Money fixedValue,
           java.math.BigDecimal percentValue,
           com.fedex.ws.rate.v22.RateElementBasisType rateElementBasis,
           com.fedex.ws.rate.v22.RateTypeBasisType rateTypeBasis) {
           this.fixedValue = fixedValue;
           this.percentValue = percentValue;
           this.rateElementBasis = rateElementBasis;
           this.rateTypeBasis = rateTypeBasis;
    }


    /**
     * Gets the fixedValue value for this VariableHandlingChargeDetail.
     * 
     * @return fixedValue
     */
    public com.fedex.ws.rate.v22.Money getFixedValue() {
        return fixedValue;
    }


    /**
     * Sets the fixedValue value for this VariableHandlingChargeDetail.
     * 
     * @param fixedValue
     */
    public void setFixedValue(com.fedex.ws.rate.v22.Money fixedValue) {
        this.fixedValue = fixedValue;
    }


    /**
     * Gets the percentValue value for this VariableHandlingChargeDetail.
     * 
     * @return percentValue   * Actual percentage (10 means 10%, which is a mutiplier of 0.1)
     */
    public java.math.BigDecimal getPercentValue() {
        return percentValue;
    }


    /**
     * Sets the percentValue value for this VariableHandlingChargeDetail.
     * 
     * @param percentValue   * Actual percentage (10 means 10%, which is a mutiplier of 0.1)
     */
    public void setPercentValue(java.math.BigDecimal percentValue) {
        this.percentValue = percentValue;
    }


    /**
     * Gets the rateElementBasis value for this VariableHandlingChargeDetail.
     * 
     * @return rateElementBasis   * Select the value from a set of rate data to which the percentage
     * is applied.
     */
    public com.fedex.ws.rate.v22.RateElementBasisType getRateElementBasis() {
        return rateElementBasis;
    }


    /**
     * Sets the rateElementBasis value for this VariableHandlingChargeDetail.
     * 
     * @param rateElementBasis   * Select the value from a set of rate data to which the percentage
     * is applied.
     */
    public void setRateElementBasis(com.fedex.ws.rate.v22.RateElementBasisType rateElementBasis) {
        this.rateElementBasis = rateElementBasis;
    }


    /**
     * Gets the rateTypeBasis value for this VariableHandlingChargeDetail.
     * 
     * @return rateTypeBasis   * Select the type of rate from which the element is to be selected.
     */
    public com.fedex.ws.rate.v22.RateTypeBasisType getRateTypeBasis() {
        return rateTypeBasis;
    }


    /**
     * Sets the rateTypeBasis value for this VariableHandlingChargeDetail.
     * 
     * @param rateTypeBasis   * Select the type of rate from which the element is to be selected.
     */
    public void setRateTypeBasis(com.fedex.ws.rate.v22.RateTypeBasisType rateTypeBasis) {
        this.rateTypeBasis = rateTypeBasis;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VariableHandlingChargeDetail)) return false;
        VariableHandlingChargeDetail other = (VariableHandlingChargeDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fixedValue==null && other.getFixedValue()==null) || 
             (this.fixedValue!=null &&
              this.fixedValue.equals(other.getFixedValue()))) &&
            ((this.percentValue==null && other.getPercentValue()==null) || 
             (this.percentValue!=null &&
              this.percentValue.equals(other.getPercentValue()))) &&
            ((this.rateElementBasis==null && other.getRateElementBasis()==null) || 
             (this.rateElementBasis!=null &&
              this.rateElementBasis.equals(other.getRateElementBasis()))) &&
            ((this.rateTypeBasis==null && other.getRateTypeBasis()==null) || 
             (this.rateTypeBasis!=null &&
              this.rateTypeBasis.equals(other.getRateTypeBasis())));
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
        if (getFixedValue() != null) {
            _hashCode += getFixedValue().hashCode();
        }
        if (getPercentValue() != null) {
            _hashCode += getPercentValue().hashCode();
        }
        if (getRateElementBasis() != null) {
            _hashCode += getRateElementBasis().hashCode();
        }
        if (getRateTypeBasis() != null) {
            _hashCode += getRateTypeBasis().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VariableHandlingChargeDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingChargeDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fixedValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FixedValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PercentValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateElementBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateElementBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateElementBasisType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateTypeBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateTypeBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateTypeBasisType"));
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
