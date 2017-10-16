/**
 * Surcharge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class Surcharge  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.SurchargeType surchargeType;

    private com.fedex.ws.rate.v22.SurchargeLevelType level;

    private java.lang.String description;

    private com.fedex.ws.rate.v22.Money amount;

    public Surcharge() {
    }

    public Surcharge(
           com.fedex.ws.rate.v22.SurchargeType surchargeType,
           com.fedex.ws.rate.v22.SurchargeLevelType level,
           java.lang.String description,
           com.fedex.ws.rate.v22.Money amount) {
           this.surchargeType = surchargeType;
           this.level = level;
           this.description = description;
           this.amount = amount;
    }


    /**
     * Gets the surchargeType value for this Surcharge.
     * 
     * @return surchargeType
     */
    public com.fedex.ws.rate.v22.SurchargeType getSurchargeType() {
        return surchargeType;
    }


    /**
     * Sets the surchargeType value for this Surcharge.
     * 
     * @param surchargeType
     */
    public void setSurchargeType(com.fedex.ws.rate.v22.SurchargeType surchargeType) {
        this.surchargeType = surchargeType;
    }


    /**
     * Gets the level value for this Surcharge.
     * 
     * @return level
     */
    public com.fedex.ws.rate.v22.SurchargeLevelType getLevel() {
        return level;
    }


    /**
     * Sets the level value for this Surcharge.
     * 
     * @param level
     */
    public void setLevel(com.fedex.ws.rate.v22.SurchargeLevelType level) {
        this.level = level;
    }


    /**
     * Gets the description value for this Surcharge.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Surcharge.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the amount value for this Surcharge.
     * 
     * @return amount
     */
    public com.fedex.ws.rate.v22.Money getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this Surcharge.
     * 
     * @param amount
     */
    public void setAmount(com.fedex.ws.rate.v22.Money amount) {
        this.amount = amount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Surcharge)) return false;
        Surcharge other = (Surcharge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.surchargeType==null && other.getSurchargeType()==null) || 
             (this.surchargeType!=null &&
              this.surchargeType.equals(other.getSurchargeType()))) &&
            ((this.level==null && other.getLevel()==null) || 
             (this.level!=null &&
              this.level.equals(other.getLevel()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount())));
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
        if (getSurchargeType() != null) {
            _hashCode += getSurchargeType().hashCode();
        }
        if (getLevel() != null) {
            _hashCode += getLevel().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Surcharge.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Surcharge"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surchargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SurchargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SurchargeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("level");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SurchargeLevelType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Amount"));
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
