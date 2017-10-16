/**
 * CodAddTransportationChargesDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CodAddTransportationChargesDetail  implements java.io.Serializable {
    /* Select the type of rate from which the element is to be selected. */
    private com.fedex.ws.rate.v22.RateTypeBasisType rateTypeBasis;

    private com.fedex.ws.rate.v22.CodAddTransportationChargeBasisType chargeBasis;

    private com.fedex.ws.rate.v22.ChargeBasisLevelType chargeBasisLevel;

    public CodAddTransportationChargesDetail() {
    }

    public CodAddTransportationChargesDetail(
           com.fedex.ws.rate.v22.RateTypeBasisType rateTypeBasis,
           com.fedex.ws.rate.v22.CodAddTransportationChargeBasisType chargeBasis,
           com.fedex.ws.rate.v22.ChargeBasisLevelType chargeBasisLevel) {
           this.rateTypeBasis = rateTypeBasis;
           this.chargeBasis = chargeBasis;
           this.chargeBasisLevel = chargeBasisLevel;
    }


    /**
     * Gets the rateTypeBasis value for this CodAddTransportationChargesDetail.
     * 
     * @return rateTypeBasis   * Select the type of rate from which the element is to be selected.
     */
    public com.fedex.ws.rate.v22.RateTypeBasisType getRateTypeBasis() {
        return rateTypeBasis;
    }


    /**
     * Sets the rateTypeBasis value for this CodAddTransportationChargesDetail.
     * 
     * @param rateTypeBasis   * Select the type of rate from which the element is to be selected.
     */
    public void setRateTypeBasis(com.fedex.ws.rate.v22.RateTypeBasisType rateTypeBasis) {
        this.rateTypeBasis = rateTypeBasis;
    }


    /**
     * Gets the chargeBasis value for this CodAddTransportationChargesDetail.
     * 
     * @return chargeBasis
     */
    public com.fedex.ws.rate.v22.CodAddTransportationChargeBasisType getChargeBasis() {
        return chargeBasis;
    }


    /**
     * Sets the chargeBasis value for this CodAddTransportationChargesDetail.
     * 
     * @param chargeBasis
     */
    public void setChargeBasis(com.fedex.ws.rate.v22.CodAddTransportationChargeBasisType chargeBasis) {
        this.chargeBasis = chargeBasis;
    }


    /**
     * Gets the chargeBasisLevel value for this CodAddTransportationChargesDetail.
     * 
     * @return chargeBasisLevel
     */
    public com.fedex.ws.rate.v22.ChargeBasisLevelType getChargeBasisLevel() {
        return chargeBasisLevel;
    }


    /**
     * Sets the chargeBasisLevel value for this CodAddTransportationChargesDetail.
     * 
     * @param chargeBasisLevel
     */
    public void setChargeBasisLevel(com.fedex.ws.rate.v22.ChargeBasisLevelType chargeBasisLevel) {
        this.chargeBasisLevel = chargeBasisLevel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CodAddTransportationChargesDetail)) return false;
        CodAddTransportationChargesDetail other = (CodAddTransportationChargesDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rateTypeBasis==null && other.getRateTypeBasis()==null) || 
             (this.rateTypeBasis!=null &&
              this.rateTypeBasis.equals(other.getRateTypeBasis()))) &&
            ((this.chargeBasis==null && other.getChargeBasis()==null) || 
             (this.chargeBasis!=null &&
              this.chargeBasis.equals(other.getChargeBasis()))) &&
            ((this.chargeBasisLevel==null && other.getChargeBasisLevel()==null) || 
             (this.chargeBasisLevel!=null &&
              this.chargeBasisLevel.equals(other.getChargeBasisLevel())));
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
        if (getRateTypeBasis() != null) {
            _hashCode += getRateTypeBasis().hashCode();
        }
        if (getChargeBasis() != null) {
            _hashCode += getChargeBasis().hashCode();
        }
        if (getChargeBasisLevel() != null) {
            _hashCode += getChargeBasisLevel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CodAddTransportationChargesDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CodAddTransportationChargesDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateTypeBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateTypeBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateTypeBasisType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeBasis");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ChargeBasis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CodAddTransportationChargeBasisType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("chargeBasisLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ChargeBasisLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ChargeBasisLevelType"));
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
