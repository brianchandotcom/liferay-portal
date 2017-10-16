/**
 * FreightBaseCharge.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Individual charge which contributes to the total base charge for
 * the shipment.
 */
public class FreightBaseCharge  implements java.io.Serializable {
    /* Freight class for this line item. */
    private com.fedex.ws.rate.v22.FreightClassType freightClass;

    /* Effective freight class used for rating this line item. */
    private com.fedex.ws.rate.v22.FreightClassType ratedAsClass;

    /* NMFC Code for commodity. */
    private java.lang.String nmfcCode;

    /* Customer-provided description for this commodity or class line. */
    private java.lang.String description;

    /* Weight for this commodity or class line. */
    private com.fedex.ws.rate.v22.Weight weight;

    /* Rate or factor applied to this line item. */
    private com.fedex.ws.rate.v22.Money chargeRate;

    /* Identifies the manner in which the chargeRate for this line
     * item was applied. */
    private com.fedex.ws.rate.v22.FreightChargeBasisType chargeBasis;

    /* The net or extended charge for this line item. */
    private com.fedex.ws.rate.v22.Money extendedAmount;

    public FreightBaseCharge() {
    }

    public FreightBaseCharge(
           com.fedex.ws.rate.v22.FreightClassType freightClass,
           com.fedex.ws.rate.v22.FreightClassType ratedAsClass,
           java.lang.String nmfcCode,
           java.lang.String description,
           com.fedex.ws.rate.v22.Weight weight,
           com.fedex.ws.rate.v22.Money chargeRate,
           com.fedex.ws.rate.v22.FreightChargeBasisType chargeBasis,
           com.fedex.ws.rate.v22.Money extendedAmount) {
           this.freightClass = freightClass;
           this.ratedAsClass = ratedAsClass;
           this.nmfcCode = nmfcCode;
           this.description = description;
           this.weight = weight;
           this.chargeRate = chargeRate;
           this.chargeBasis = chargeBasis;
           this.extendedAmount = extendedAmount;
    }


    /**
     * Gets the freightClass value for this FreightBaseCharge.
     * 
     * @return freightClass   * Freight class for this line item.
     */
    public com.fedex.ws.rate.v22.FreightClassType getFreightClass() {
        return freightClass;
    }


    /**
     * Sets the freightClass value for this FreightBaseCharge.
     * 
     * @param freightClass   * Freight class for this line item.
     */
    public void setFreightClass(com.fedex.ws.rate.v22.FreightClassType freightClass) {
        this.freightClass = freightClass;
    }


    /**
     * Gets the ratedAsClass value for this FreightBaseCharge.
     * 
     * @return ratedAsClass   * Effective freight class used for rating this line item.
     */
    public com.fedex.ws.rate.v22.FreightClassType getRatedAsClass() {
        return ratedAsClass;
    }


    /**
     * Sets the ratedAsClass value for this FreightBaseCharge.
     * 
     * @param ratedAsClass   * Effective freight class used for rating this line item.
     */
    public void setRatedAsClass(com.fedex.ws.rate.v22.FreightClassType ratedAsClass) {
        this.ratedAsClass = ratedAsClass;
    }


    /**
     * Gets the nmfcCode value for this FreightBaseCharge.
     * 
     * @return nmfcCode   * NMFC Code for commodity.
     */
    public java.lang.String getNmfcCode() {
        return nmfcCode;
    }


    /**
     * Sets the nmfcCode value for this FreightBaseCharge.
     * 
     * @param nmfcCode   * NMFC Code for commodity.
     */
    public void setNmfcCode(java.lang.String nmfcCode) {
        this.nmfcCode = nmfcCode;
    }


    /**
     * Gets the description value for this FreightBaseCharge.
     * 
     * @return description   * Customer-provided description for this commodity or class line.
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FreightBaseCharge.
     * 
     * @param description   * Customer-provided description for this commodity or class line.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the weight value for this FreightBaseCharge.
     * 
     * @return weight   * Weight for this commodity or class line.
     */
    public com.fedex.ws.rate.v22.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this FreightBaseCharge.
     * 
     * @param weight   * Weight for this commodity or class line.
     */
    public void setWeight(com.fedex.ws.rate.v22.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the chargeRate value for this FreightBaseCharge.
     * 
     * @return chargeRate   * Rate or factor applied to this line item.
     */
    public com.fedex.ws.rate.v22.Money getChargeRate() {
        return chargeRate;
    }


    /**
     * Sets the chargeRate value for this FreightBaseCharge.
     * 
     * @param chargeRate   * Rate or factor applied to this line item.
     */
    public void setChargeRate(com.fedex.ws.rate.v22.Money chargeRate) {
        this.chargeRate = chargeRate;
    }


    /**
     * Gets the chargeBasis value for this FreightBaseCharge.
     * 
     * @return chargeBasis   * Identifies the manner in which the chargeRate for this line
     * item was applied.
     */
    public com.fedex.ws.rate.v22.FreightChargeBasisType getChargeBasis() {
        return chargeBasis;
    }


    /**
     * Sets the chargeBasis value for this FreightBaseCharge.
     * 
     * @param chargeBasis   * Identifies the manner in which the chargeRate for this line
     * item was applied.
     */
    public void setChargeBasis(com.fedex.ws.rate.v22.FreightChargeBasisType chargeBasis) {
        this.chargeBasis = chargeBasis;
    }


    /**
     * Gets the extendedAmount value for this FreightBaseCharge.
     * 
     * @return extendedAmount   * The net or extended charge for this line item.
     */
    public com.fedex.ws.rate.v22.Money getExtendedAmount() {
        return extendedAmount;
    }


    /**
     * Sets the extendedAmount value for this FreightBaseCharge.
     * 
     * @param extendedAmount   * The net or extended charge for this line item.
     */
    public void setExtendedAmount(com.fedex.ws.rate.v22.Money extendedAmount) {
        this.extendedAmount = extendedAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightBaseCharge)) return false;
        FreightBaseCharge other = (FreightBaseCharge) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.freightClass==null && other.getFreightClass()==null) || 
             (this.freightClass!=null &&
              this.freightClass.equals(other.getFreightClass()))) &&
            ((this.ratedAsClass==null && other.getRatedAsClass()==null) || 
             (this.ratedAsClass!=null &&
              this.ratedAsClass.equals(other.getRatedAsClass()))) &&
            ((this.nmfcCode==null && other.getNmfcCode()==null) || 
             (this.nmfcCode!=null &&
              this.nmfcCode.equals(other.getNmfcCode()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.weight==null && other.getWeight()==null) || 
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.chargeRate==null && other.getChargeRate()==null) || 
             (this.chargeRate!=null &&
              this.chargeRate.equals(other.getChargeRate()))) &&
            ((this.chargeBasis==null && other.getChargeBasis()==null) || 
             (this.chargeBasis!=null &&
              this.chargeBasis.equals(other.getChargeBasis()))) &&
            ((this.extendedAmount==null && other.getExtendedAmount()==null) || 
             (this.extendedAmount!=null &&
              this.extendedAmount.equals(other.getExtendedAmount())));
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
        if (getFreightClass() != null) {
            _hashCode += getFreightClass().hashCode();
        }
        if (getRatedAsClass() != null) {
            _hashCode += getRatedAsClass().hashCode();
        }
        if (getNmfcCode() != null) {
            _hashCode += getNmfcCode().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getChargeRate() != null) {
            _hashCode += getChargeRate().hashCode();
        }
        if (getChargeBasis() != null) {
            _hashCode += getChargeBasis().hashCode();
        }
        if (getExtendedAmount() != null) {
            _hashCode += getExtendedAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightBaseCharge.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightBaseCharge"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightClassType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedAsClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedAsClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightClassType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nmfcCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NmfcCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("weight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ChargeRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ChargeBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightChargeBasisType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extendedAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ExtendedAmount"));
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
