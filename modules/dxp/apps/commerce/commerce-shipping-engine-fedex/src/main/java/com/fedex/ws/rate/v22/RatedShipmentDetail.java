/**
 * RatedShipmentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * This class groups the shipment and package rating data for a specific
 * rate type for use in a rating reply, which groups result data by rate
 * type.
 */
public class RatedShipmentDetail  implements java.io.Serializable {
    /* The difference between "list" and "account" total net charge. */
    private com.fedex.ws.rate.v22.Money effectiveNetDiscount;

    /* Express COD is shipment level. */
    private com.fedex.ws.rate.v22.Money adjustedCodCollectionAmount;

    /* The shipment-level totals for this rate type. */
    private com.fedex.ws.rate.v22.ShipmentRateDetail shipmentRateDetail;

    /* The package-level data for this rate type. */
    private com.fedex.ws.rate.v22.RatedPackageDetail[] ratedPackages;

    public RatedShipmentDetail() {
    }

    public RatedShipmentDetail(
           com.fedex.ws.rate.v22.Money effectiveNetDiscount,
           com.fedex.ws.rate.v22.Money adjustedCodCollectionAmount,
           com.fedex.ws.rate.v22.ShipmentRateDetail shipmentRateDetail,
           com.fedex.ws.rate.v22.RatedPackageDetail[] ratedPackages) {
           this.effectiveNetDiscount = effectiveNetDiscount;
           this.adjustedCodCollectionAmount = adjustedCodCollectionAmount;
           this.shipmentRateDetail = shipmentRateDetail;
           this.ratedPackages = ratedPackages;
    }


    /**
     * Gets the effectiveNetDiscount value for this RatedShipmentDetail.
     * 
     * @return effectiveNetDiscount   * The difference between "list" and "account" total net charge.
     */
    public com.fedex.ws.rate.v22.Money getEffectiveNetDiscount() {
        return effectiveNetDiscount;
    }


    /**
     * Sets the effectiveNetDiscount value for this RatedShipmentDetail.
     * 
     * @param effectiveNetDiscount   * The difference between "list" and "account" total net charge.
     */
    public void setEffectiveNetDiscount(com.fedex.ws.rate.v22.Money effectiveNetDiscount) {
        this.effectiveNetDiscount = effectiveNetDiscount;
    }


    /**
     * Gets the adjustedCodCollectionAmount value for this RatedShipmentDetail.
     * 
     * @return adjustedCodCollectionAmount   * Express COD is shipment level.
     */
    public com.fedex.ws.rate.v22.Money getAdjustedCodCollectionAmount() {
        return adjustedCodCollectionAmount;
    }


    /**
     * Sets the adjustedCodCollectionAmount value for this RatedShipmentDetail.
     * 
     * @param adjustedCodCollectionAmount   * Express COD is shipment level.
     */
    public void setAdjustedCodCollectionAmount(com.fedex.ws.rate.v22.Money adjustedCodCollectionAmount) {
        this.adjustedCodCollectionAmount = adjustedCodCollectionAmount;
    }


    /**
     * Gets the shipmentRateDetail value for this RatedShipmentDetail.
     * 
     * @return shipmentRateDetail   * The shipment-level totals for this rate type.
     */
    public com.fedex.ws.rate.v22.ShipmentRateDetail getShipmentRateDetail() {
        return shipmentRateDetail;
    }


    /**
     * Sets the shipmentRateDetail value for this RatedShipmentDetail.
     * 
     * @param shipmentRateDetail   * The shipment-level totals for this rate type.
     */
    public void setShipmentRateDetail(com.fedex.ws.rate.v22.ShipmentRateDetail shipmentRateDetail) {
        this.shipmentRateDetail = shipmentRateDetail;
    }


    /**
     * Gets the ratedPackages value for this RatedShipmentDetail.
     * 
     * @return ratedPackages   * The package-level data for this rate type.
     */
    public com.fedex.ws.rate.v22.RatedPackageDetail[] getRatedPackages() {
        return ratedPackages;
    }


    /**
     * Sets the ratedPackages value for this RatedShipmentDetail.
     * 
     * @param ratedPackages   * The package-level data for this rate type.
     */
    public void setRatedPackages(com.fedex.ws.rate.v22.RatedPackageDetail[] ratedPackages) {
        this.ratedPackages = ratedPackages;
    }

    public com.fedex.ws.rate.v22.RatedPackageDetail getRatedPackages(int i) {
        return this.ratedPackages[i];
    }

    public void setRatedPackages(int i, com.fedex.ws.rate.v22.RatedPackageDetail _value) {
        this.ratedPackages[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RatedShipmentDetail)) return false;
        RatedShipmentDetail other = (RatedShipmentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.effectiveNetDiscount==null && other.getEffectiveNetDiscount()==null) || 
             (this.effectiveNetDiscount!=null &&
              this.effectiveNetDiscount.equals(other.getEffectiveNetDiscount()))) &&
            ((this.adjustedCodCollectionAmount==null && other.getAdjustedCodCollectionAmount()==null) || 
             (this.adjustedCodCollectionAmount!=null &&
              this.adjustedCodCollectionAmount.equals(other.getAdjustedCodCollectionAmount()))) &&
            ((this.shipmentRateDetail==null && other.getShipmentRateDetail()==null) || 
             (this.shipmentRateDetail!=null &&
              this.shipmentRateDetail.equals(other.getShipmentRateDetail()))) &&
            ((this.ratedPackages==null && other.getRatedPackages()==null) || 
             (this.ratedPackages!=null &&
              java.util.Arrays.equals(this.ratedPackages, other.getRatedPackages())));
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
        if (getEffectiveNetDiscount() != null) {
            _hashCode += getEffectiveNetDiscount().hashCode();
        }
        if (getAdjustedCodCollectionAmount() != null) {
            _hashCode += getAdjustedCodCollectionAmount().hashCode();
        }
        if (getShipmentRateDetail() != null) {
            _hashCode += getShipmentRateDetail().hashCode();
        }
        if (getRatedPackages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRatedPackages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRatedPackages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RatedShipmentDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedShipmentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effectiveNetDiscount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EffectiveNetDiscount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adjustedCodCollectionAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AdjustedCodCollectionAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentRateDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentRateDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentRateDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedPackages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedPackages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedPackageDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
