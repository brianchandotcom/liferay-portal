/**
 * RatedPackageDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RatedPackageDetail  implements java.io.Serializable {
    /* Echoed from the corresponding package in the rate request (if
     * provided). */
    private com.fedex.ws.rate.v22.TrackingId[] trackingIds;

    /* Used with request containing PACKAGE_GROUPS, to identify which
     * group of identical packages was used to produce a reply item. */
    private org.apache.axis.types.NonNegativeInteger groupNumber;

    /* The difference between "list" and "account" net charge. */
    private com.fedex.ws.rate.v22.Money effectiveNetDiscount;

    /* Ground COD is shipment level. */
    private com.fedex.ws.rate.v22.Money adjustedCodCollectionAmount;

    private com.fedex.ws.rate.v22.OversizeClassType oversizeClass;

    /* Rate data that are tied to a specific package and rate type
     * combination. */
    private com.fedex.ws.rate.v22.PackageRateDetail packageRateDetail;

    public RatedPackageDetail() {
    }

    public RatedPackageDetail(
           com.fedex.ws.rate.v22.TrackingId[] trackingIds,
           org.apache.axis.types.NonNegativeInteger groupNumber,
           com.fedex.ws.rate.v22.Money effectiveNetDiscount,
           com.fedex.ws.rate.v22.Money adjustedCodCollectionAmount,
           com.fedex.ws.rate.v22.OversizeClassType oversizeClass,
           com.fedex.ws.rate.v22.PackageRateDetail packageRateDetail) {
           this.trackingIds = trackingIds;
           this.groupNumber = groupNumber;
           this.effectiveNetDiscount = effectiveNetDiscount;
           this.adjustedCodCollectionAmount = adjustedCodCollectionAmount;
           this.oversizeClass = oversizeClass;
           this.packageRateDetail = packageRateDetail;
    }


    /**
     * Gets the trackingIds value for this RatedPackageDetail.
     * 
     * @return trackingIds   * Echoed from the corresponding package in the rate request (if
     * provided).
     */
    public com.fedex.ws.rate.v22.TrackingId[] getTrackingIds() {
        return trackingIds;
    }


    /**
     * Sets the trackingIds value for this RatedPackageDetail.
     * 
     * @param trackingIds   * Echoed from the corresponding package in the rate request (if
     * provided).
     */
    public void setTrackingIds(com.fedex.ws.rate.v22.TrackingId[] trackingIds) {
        this.trackingIds = trackingIds;
    }

    public com.fedex.ws.rate.v22.TrackingId getTrackingIds(int i) {
        return this.trackingIds[i];
    }

    public void setTrackingIds(int i, com.fedex.ws.rate.v22.TrackingId _value) {
        this.trackingIds[i] = _value;
    }


    /**
     * Gets the groupNumber value for this RatedPackageDetail.
     * 
     * @return groupNumber   * Used with request containing PACKAGE_GROUPS, to identify which
     * group of identical packages was used to produce a reply item.
     */
    public org.apache.axis.types.NonNegativeInteger getGroupNumber() {
        return groupNumber;
    }


    /**
     * Sets the groupNumber value for this RatedPackageDetail.
     * 
     * @param groupNumber   * Used with request containing PACKAGE_GROUPS, to identify which
     * group of identical packages was used to produce a reply item.
     */
    public void setGroupNumber(org.apache.axis.types.NonNegativeInteger groupNumber) {
        this.groupNumber = groupNumber;
    }


    /**
     * Gets the effectiveNetDiscount value for this RatedPackageDetail.
     * 
     * @return effectiveNetDiscount   * The difference between "list" and "account" net charge.
     */
    public com.fedex.ws.rate.v22.Money getEffectiveNetDiscount() {
        return effectiveNetDiscount;
    }


    /**
     * Sets the effectiveNetDiscount value for this RatedPackageDetail.
     * 
     * @param effectiveNetDiscount   * The difference between "list" and "account" net charge.
     */
    public void setEffectiveNetDiscount(com.fedex.ws.rate.v22.Money effectiveNetDiscount) {
        this.effectiveNetDiscount = effectiveNetDiscount;
    }


    /**
     * Gets the adjustedCodCollectionAmount value for this RatedPackageDetail.
     * 
     * @return adjustedCodCollectionAmount   * Ground COD is shipment level.
     */
    public com.fedex.ws.rate.v22.Money getAdjustedCodCollectionAmount() {
        return adjustedCodCollectionAmount;
    }


    /**
     * Sets the adjustedCodCollectionAmount value for this RatedPackageDetail.
     * 
     * @param adjustedCodCollectionAmount   * Ground COD is shipment level.
     */
    public void setAdjustedCodCollectionAmount(com.fedex.ws.rate.v22.Money adjustedCodCollectionAmount) {
        this.adjustedCodCollectionAmount = adjustedCodCollectionAmount;
    }


    /**
     * Gets the oversizeClass value for this RatedPackageDetail.
     * 
     * @return oversizeClass
     */
    public com.fedex.ws.rate.v22.OversizeClassType getOversizeClass() {
        return oversizeClass;
    }


    /**
     * Sets the oversizeClass value for this RatedPackageDetail.
     * 
     * @param oversizeClass
     */
    public void setOversizeClass(com.fedex.ws.rate.v22.OversizeClassType oversizeClass) {
        this.oversizeClass = oversizeClass;
    }


    /**
     * Gets the packageRateDetail value for this RatedPackageDetail.
     * 
     * @return packageRateDetail   * Rate data that are tied to a specific package and rate type
     * combination.
     */
    public com.fedex.ws.rate.v22.PackageRateDetail getPackageRateDetail() {
        return packageRateDetail;
    }


    /**
     * Sets the packageRateDetail value for this RatedPackageDetail.
     * 
     * @param packageRateDetail   * Rate data that are tied to a specific package and rate type
     * combination.
     */
    public void setPackageRateDetail(com.fedex.ws.rate.v22.PackageRateDetail packageRateDetail) {
        this.packageRateDetail = packageRateDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RatedPackageDetail)) return false;
        RatedPackageDetail other = (RatedPackageDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.trackingIds==null && other.getTrackingIds()==null) || 
             (this.trackingIds!=null &&
              java.util.Arrays.equals(this.trackingIds, other.getTrackingIds()))) &&
            ((this.groupNumber==null && other.getGroupNumber()==null) || 
             (this.groupNumber!=null &&
              this.groupNumber.equals(other.getGroupNumber()))) &&
            ((this.effectiveNetDiscount==null && other.getEffectiveNetDiscount()==null) || 
             (this.effectiveNetDiscount!=null &&
              this.effectiveNetDiscount.equals(other.getEffectiveNetDiscount()))) &&
            ((this.adjustedCodCollectionAmount==null && other.getAdjustedCodCollectionAmount()==null) || 
             (this.adjustedCodCollectionAmount!=null &&
              this.adjustedCodCollectionAmount.equals(other.getAdjustedCodCollectionAmount()))) &&
            ((this.oversizeClass==null && other.getOversizeClass()==null) || 
             (this.oversizeClass!=null &&
              this.oversizeClass.equals(other.getOversizeClass()))) &&
            ((this.packageRateDetail==null && other.getPackageRateDetail()==null) || 
             (this.packageRateDetail!=null &&
              this.packageRateDetail.equals(other.getPackageRateDetail())));
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
        if (getTrackingIds() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTrackingIds());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTrackingIds(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGroupNumber() != null) {
            _hashCode += getGroupNumber().hashCode();
        }
        if (getEffectiveNetDiscount() != null) {
            _hashCode += getEffectiveNetDiscount().hashCode();
        }
        if (getAdjustedCodCollectionAmount() != null) {
            _hashCode += getAdjustedCodCollectionAmount().hashCode();
        }
        if (getOversizeClass() != null) {
            _hashCode += getOversizeClass().hashCode();
        }
        if (getPackageRateDetail() != null) {
            _hashCode += getPackageRateDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RatedPackageDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedPackageDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingIds");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TrackingIds"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TrackingId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "GroupNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("oversizeClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OversizeClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OversizeClassType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packageRateDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackageRateDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackageRateDetail"));
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
