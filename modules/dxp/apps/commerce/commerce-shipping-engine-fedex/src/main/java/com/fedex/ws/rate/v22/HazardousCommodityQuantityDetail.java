/**
 * HazardousCommodityQuantityDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Identifies amount and units for quantity of hazardous commodities.
 */
public class HazardousCommodityQuantityDetail  implements java.io.Serializable {
    /* Number of units of the type below. */
    private java.math.BigDecimal amount;

    /* Units by which the hazardous commodity is measured. For IATA
     * commodity, the units values are restricted based on regulation type. */
    private java.lang.String units;

    /* Specifies which measure of quantity is to be validated. */
    private com.fedex.ws.rate.v22.HazardousCommodityQuantityType quantityType;

    public HazardousCommodityQuantityDetail() {
    }

    public HazardousCommodityQuantityDetail(
           java.math.BigDecimal amount,
           java.lang.String units,
           com.fedex.ws.rate.v22.HazardousCommodityQuantityType quantityType) {
           this.amount = amount;
           this.units = units;
           this.quantityType = quantityType;
    }


    /**
     * Gets the amount value for this HazardousCommodityQuantityDetail.
     * 
     * @return amount   * Number of units of the type below.
     */
    public java.math.BigDecimal getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this HazardousCommodityQuantityDetail.
     * 
     * @param amount   * Number of units of the type below.
     */
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
    }


    /**
     * Gets the units value for this HazardousCommodityQuantityDetail.
     * 
     * @return units   * Units by which the hazardous commodity is measured. For IATA
     * commodity, the units values are restricted based on regulation type.
     */
    public java.lang.String getUnits() {
        return units;
    }


    /**
     * Sets the units value for this HazardousCommodityQuantityDetail.
     * 
     * @param units   * Units by which the hazardous commodity is measured. For IATA
     * commodity, the units values are restricted based on regulation type.
     */
    public void setUnits(java.lang.String units) {
        this.units = units;
    }


    /**
     * Gets the quantityType value for this HazardousCommodityQuantityDetail.
     * 
     * @return quantityType   * Specifies which measure of quantity is to be validated.
     */
    public com.fedex.ws.rate.v22.HazardousCommodityQuantityType getQuantityType() {
        return quantityType;
    }


    /**
     * Sets the quantityType value for this HazardousCommodityQuantityDetail.
     * 
     * @param quantityType   * Specifies which measure of quantity is to be validated.
     */
    public void setQuantityType(com.fedex.ws.rate.v22.HazardousCommodityQuantityType quantityType) {
        this.quantityType = quantityType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof HazardousCommodityQuantityDetail)) return false;
        HazardousCommodityQuantityDetail other = (HazardousCommodityQuantityDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.units==null && other.getUnits()==null) || 
             (this.units!=null &&
              this.units.equals(other.getUnits()))) &&
            ((this.quantityType==null && other.getQuantityType()==null) || 
             (this.quantityType!=null &&
              this.quantityType.equals(other.getQuantityType())));
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
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getUnits() != null) {
            _hashCode += getUnits().hashCode();
        }
        if (getQuantityType() != null) {
            _hashCode += getQuantityType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(HazardousCommodityQuantityDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodityQuantityDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Amount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("units");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Units"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantityType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "QuantityType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodityQuantityType"));
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
