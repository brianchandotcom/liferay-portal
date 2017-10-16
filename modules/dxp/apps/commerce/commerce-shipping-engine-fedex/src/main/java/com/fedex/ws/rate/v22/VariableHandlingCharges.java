/**
 * VariableHandlingCharges.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class VariableHandlingCharges  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.Money variableHandlingCharge;

    private com.fedex.ws.rate.v22.Money fixedVariableHandlingCharge;

    private com.fedex.ws.rate.v22.Money percentVariableHandlingCharge;

    private com.fedex.ws.rate.v22.Money totalCustomerCharge;

    public VariableHandlingCharges() {
    }

    public VariableHandlingCharges(
           com.fedex.ws.rate.v22.Money variableHandlingCharge,
           com.fedex.ws.rate.v22.Money fixedVariableHandlingCharge,
           com.fedex.ws.rate.v22.Money percentVariableHandlingCharge,
           com.fedex.ws.rate.v22.Money totalCustomerCharge) {
           this.variableHandlingCharge = variableHandlingCharge;
           this.fixedVariableHandlingCharge = fixedVariableHandlingCharge;
           this.percentVariableHandlingCharge = percentVariableHandlingCharge;
           this.totalCustomerCharge = totalCustomerCharge;
    }


    /**
     * Gets the variableHandlingCharge value for this VariableHandlingCharges.
     * 
     * @return variableHandlingCharge
     */
    public com.fedex.ws.rate.v22.Money getVariableHandlingCharge() {
        return variableHandlingCharge;
    }


    /**
     * Sets the variableHandlingCharge value for this VariableHandlingCharges.
     * 
     * @param variableHandlingCharge
     */
    public void setVariableHandlingCharge(com.fedex.ws.rate.v22.Money variableHandlingCharge) {
        this.variableHandlingCharge = variableHandlingCharge;
    }


    /**
     * Gets the fixedVariableHandlingCharge value for this VariableHandlingCharges.
     * 
     * @return fixedVariableHandlingCharge
     */
    public com.fedex.ws.rate.v22.Money getFixedVariableHandlingCharge() {
        return fixedVariableHandlingCharge;
    }


    /**
     * Sets the fixedVariableHandlingCharge value for this VariableHandlingCharges.
     * 
     * @param fixedVariableHandlingCharge
     */
    public void setFixedVariableHandlingCharge(com.fedex.ws.rate.v22.Money fixedVariableHandlingCharge) {
        this.fixedVariableHandlingCharge = fixedVariableHandlingCharge;
    }


    /**
     * Gets the percentVariableHandlingCharge value for this VariableHandlingCharges.
     * 
     * @return percentVariableHandlingCharge
     */
    public com.fedex.ws.rate.v22.Money getPercentVariableHandlingCharge() {
        return percentVariableHandlingCharge;
    }


    /**
     * Sets the percentVariableHandlingCharge value for this VariableHandlingCharges.
     * 
     * @param percentVariableHandlingCharge
     */
    public void setPercentVariableHandlingCharge(com.fedex.ws.rate.v22.Money percentVariableHandlingCharge) {
        this.percentVariableHandlingCharge = percentVariableHandlingCharge;
    }


    /**
     * Gets the totalCustomerCharge value for this VariableHandlingCharges.
     * 
     * @return totalCustomerCharge
     */
    public com.fedex.ws.rate.v22.Money getTotalCustomerCharge() {
        return totalCustomerCharge;
    }


    /**
     * Sets the totalCustomerCharge value for this VariableHandlingCharges.
     * 
     * @param totalCustomerCharge
     */
    public void setTotalCustomerCharge(com.fedex.ws.rate.v22.Money totalCustomerCharge) {
        this.totalCustomerCharge = totalCustomerCharge;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VariableHandlingCharges)) return false;
        VariableHandlingCharges other = (VariableHandlingCharges) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.variableHandlingCharge==null && other.getVariableHandlingCharge()==null) || 
             (this.variableHandlingCharge!=null &&
              this.variableHandlingCharge.equals(other.getVariableHandlingCharge()))) &&
            ((this.fixedVariableHandlingCharge==null && other.getFixedVariableHandlingCharge()==null) || 
             (this.fixedVariableHandlingCharge!=null &&
              this.fixedVariableHandlingCharge.equals(other.getFixedVariableHandlingCharge()))) &&
            ((this.percentVariableHandlingCharge==null && other.getPercentVariableHandlingCharge()==null) || 
             (this.percentVariableHandlingCharge!=null &&
              this.percentVariableHandlingCharge.equals(other.getPercentVariableHandlingCharge()))) &&
            ((this.totalCustomerCharge==null && other.getTotalCustomerCharge()==null) || 
             (this.totalCustomerCharge!=null &&
              this.totalCustomerCharge.equals(other.getTotalCustomerCharge())));
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
        if (getVariableHandlingCharge() != null) {
            _hashCode += getVariableHandlingCharge().hashCode();
        }
        if (getFixedVariableHandlingCharge() != null) {
            _hashCode += getFixedVariableHandlingCharge().hashCode();
        }
        if (getPercentVariableHandlingCharge() != null) {
            _hashCode += getPercentVariableHandlingCharge().hashCode();
        }
        if (getTotalCustomerCharge() != null) {
            _hashCode += getTotalCustomerCharge().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VariableHandlingCharges.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingCharges"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableHandlingCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fixedVariableHandlingCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FixedVariableHandlingCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentVariableHandlingCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PercentVariableHandlingCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCustomerCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalCustomerCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
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
