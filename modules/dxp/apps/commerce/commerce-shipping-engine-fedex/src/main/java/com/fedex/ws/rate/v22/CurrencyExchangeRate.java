/**
 * CurrencyExchangeRate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CurrencyExchangeRate  implements java.io.Serializable {
    /* The currency code for the original (converted FROM) currency. */
    private java.lang.String fromCurrency;

    /* The currency code for the final (converted INTO) currency. */
    private java.lang.String intoCurrency;

    /* Multiplier used to convert fromCurrency units to intoCurrency
     * units. */
    private java.math.BigDecimal rate;

    public CurrencyExchangeRate() {
    }

    public CurrencyExchangeRate(
           java.lang.String fromCurrency,
           java.lang.String intoCurrency,
           java.math.BigDecimal rate) {
           this.fromCurrency = fromCurrency;
           this.intoCurrency = intoCurrency;
           this.rate = rate;
    }


    /**
     * Gets the fromCurrency value for this CurrencyExchangeRate.
     * 
     * @return fromCurrency   * The currency code for the original (converted FROM) currency.
     */
    public java.lang.String getFromCurrency() {
        return fromCurrency;
    }


    /**
     * Sets the fromCurrency value for this CurrencyExchangeRate.
     * 
     * @param fromCurrency   * The currency code for the original (converted FROM) currency.
     */
    public void setFromCurrency(java.lang.String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }


    /**
     * Gets the intoCurrency value for this CurrencyExchangeRate.
     * 
     * @return intoCurrency   * The currency code for the final (converted INTO) currency.
     */
    public java.lang.String getIntoCurrency() {
        return intoCurrency;
    }


    /**
     * Sets the intoCurrency value for this CurrencyExchangeRate.
     * 
     * @param intoCurrency   * The currency code for the final (converted INTO) currency.
     */
    public void setIntoCurrency(java.lang.String intoCurrency) {
        this.intoCurrency = intoCurrency;
    }


    /**
     * Gets the rate value for this CurrencyExchangeRate.
     * 
     * @return rate   * Multiplier used to convert fromCurrency units to intoCurrency
     * units.
     */
    public java.math.BigDecimal getRate() {
        return rate;
    }


    /**
     * Sets the rate value for this CurrencyExchangeRate.
     * 
     * @param rate   * Multiplier used to convert fromCurrency units to intoCurrency
     * units.
     */
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CurrencyExchangeRate)) return false;
        CurrencyExchangeRate other = (CurrencyExchangeRate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fromCurrency==null && other.getFromCurrency()==null) || 
             (this.fromCurrency!=null &&
              this.fromCurrency.equals(other.getFromCurrency()))) &&
            ((this.intoCurrency==null && other.getIntoCurrency()==null) || 
             (this.intoCurrency!=null &&
              this.intoCurrency.equals(other.getIntoCurrency()))) &&
            ((this.rate==null && other.getRate()==null) || 
             (this.rate!=null &&
              this.rate.equals(other.getRate())));
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
        if (getFromCurrency() != null) {
            _hashCode += getFromCurrency().hashCode();
        }
        if (getIntoCurrency() != null) {
            _hashCode += getIntoCurrency().hashCode();
        }
        if (getRate() != null) {
            _hashCode += getRate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CurrencyExchangeRate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CurrencyExchangeRate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromCurrency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FromCurrency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intoCurrency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "IntoCurrency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Rate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
