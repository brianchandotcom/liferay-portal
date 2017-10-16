/**
 * RateReplyDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RateReplyDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.ServiceType serviceType;

    private com.fedex.ws.rate.v22.PackagingType packagingType;

    /* Shows the specific combination of service options combined
     * with the service type that produced this committment in the set returned
     * to the caller. */
    private com.fedex.ws.rate.v22.ServiceOptionType[] appliedOptions;

    /* Supporting detail for applied options identified in preceding
     * field. */
    private com.fedex.ws.rate.v22.ServiceSubOptionDetail appliedSubOptions;

    private java.lang.String deliveryStation;

    private com.fedex.ws.rate.v22.DayOfWeekType deliveryDayOfWeek;

    private java.util.Calendar deliveryTimestamp;

    private com.fedex.ws.rate.v22.CommitDetail[] commitDetails;

    private java.lang.String destinationAirportId;

    private java.lang.Boolean ineligibleForMoneyBackGuarantee;

    /* Not populated by FAST service in Jan07. */
    private java.lang.String originServiceArea;

    /* Not populated by FAST service in Jan07. */
    private java.lang.String destinationServiceArea;

    /* Not populated by FAST service in Jan07. */
    private com.fedex.ws.rate.v22.TransitTimeType transitTime;

    /* Maximum expected transit time */
    private com.fedex.ws.rate.v22.TransitTimeType maximumTransitTime;

    /* Not populated by FAST service in Jan07. Actual signature option
     * applied, to allow for cases in wihch the original value conflicted
     * with other service features in the shipment. */
    private com.fedex.ws.rate.v22.SignatureOptionType signatureOption;

    private com.fedex.ws.rate.v22.ReturnedRateType actualRateType;

    /* Each element contains all rate data for a single rate type. */
    private com.fedex.ws.rate.v22.RatedShipmentDetail[] ratedShipmentDetails;

    public RateReplyDetail() {
    }

    public RateReplyDetail(
           com.fedex.ws.rate.v22.ServiceType serviceType,
           com.fedex.ws.rate.v22.PackagingType packagingType,
           com.fedex.ws.rate.v22.ServiceOptionType[] appliedOptions,
           com.fedex.ws.rate.v22.ServiceSubOptionDetail appliedSubOptions,
           java.lang.String deliveryStation,
           com.fedex.ws.rate.v22.DayOfWeekType deliveryDayOfWeek,
           java.util.Calendar deliveryTimestamp,
           com.fedex.ws.rate.v22.CommitDetail[] commitDetails,
           java.lang.String destinationAirportId,
           java.lang.Boolean ineligibleForMoneyBackGuarantee,
           java.lang.String originServiceArea,
           java.lang.String destinationServiceArea,
           com.fedex.ws.rate.v22.TransitTimeType transitTime,
           com.fedex.ws.rate.v22.TransitTimeType maximumTransitTime,
           com.fedex.ws.rate.v22.SignatureOptionType signatureOption,
           com.fedex.ws.rate.v22.ReturnedRateType actualRateType,
           com.fedex.ws.rate.v22.RatedShipmentDetail[] ratedShipmentDetails) {
           this.serviceType = serviceType;
           this.packagingType = packagingType;
           this.appliedOptions = appliedOptions;
           this.appliedSubOptions = appliedSubOptions;
           this.deliveryStation = deliveryStation;
           this.deliveryDayOfWeek = deliveryDayOfWeek;
           this.deliveryTimestamp = deliveryTimestamp;
           this.commitDetails = commitDetails;
           this.destinationAirportId = destinationAirportId;
           this.ineligibleForMoneyBackGuarantee = ineligibleForMoneyBackGuarantee;
           this.originServiceArea = originServiceArea;
           this.destinationServiceArea = destinationServiceArea;
           this.transitTime = transitTime;
           this.maximumTransitTime = maximumTransitTime;
           this.signatureOption = signatureOption;
           this.actualRateType = actualRateType;
           this.ratedShipmentDetails = ratedShipmentDetails;
    }


    /**
     * Gets the serviceType value for this RateReplyDetail.
     * 
     * @return serviceType
     */
    public com.fedex.ws.rate.v22.ServiceType getServiceType() {
        return serviceType;
    }


    /**
     * Sets the serviceType value for this RateReplyDetail.
     * 
     * @param serviceType
     */
    public void setServiceType(com.fedex.ws.rate.v22.ServiceType serviceType) {
        this.serviceType = serviceType;
    }


    /**
     * Gets the packagingType value for this RateReplyDetail.
     * 
     * @return packagingType
     */
    public com.fedex.ws.rate.v22.PackagingType getPackagingType() {
        return packagingType;
    }


    /**
     * Sets the packagingType value for this RateReplyDetail.
     * 
     * @param packagingType
     */
    public void setPackagingType(com.fedex.ws.rate.v22.PackagingType packagingType) {
        this.packagingType = packagingType;
    }


    /**
     * Gets the appliedOptions value for this RateReplyDetail.
     * 
     * @return appliedOptions   * Shows the specific combination of service options combined
     * with the service type that produced this committment in the set returned
     * to the caller.
     */
    public com.fedex.ws.rate.v22.ServiceOptionType[] getAppliedOptions() {
        return appliedOptions;
    }


    /**
     * Sets the appliedOptions value for this RateReplyDetail.
     * 
     * @param appliedOptions   * Shows the specific combination of service options combined
     * with the service type that produced this committment in the set returned
     * to the caller.
     */
    public void setAppliedOptions(com.fedex.ws.rate.v22.ServiceOptionType[] appliedOptions) {
        this.appliedOptions = appliedOptions;
    }

    public com.fedex.ws.rate.v22.ServiceOptionType getAppliedOptions(int i) {
        return this.appliedOptions[i];
    }

    public void setAppliedOptions(int i, com.fedex.ws.rate.v22.ServiceOptionType _value) {
        this.appliedOptions[i] = _value;
    }


    /**
     * Gets the appliedSubOptions value for this RateReplyDetail.
     * 
     * @return appliedSubOptions   * Supporting detail for applied options identified in preceding
     * field.
     */
    public com.fedex.ws.rate.v22.ServiceSubOptionDetail getAppliedSubOptions() {
        return appliedSubOptions;
    }


    /**
     * Sets the appliedSubOptions value for this RateReplyDetail.
     * 
     * @param appliedSubOptions   * Supporting detail for applied options identified in preceding
     * field.
     */
    public void setAppliedSubOptions(com.fedex.ws.rate.v22.ServiceSubOptionDetail appliedSubOptions) {
        this.appliedSubOptions = appliedSubOptions;
    }


    /**
     * Gets the deliveryStation value for this RateReplyDetail.
     * 
     * @return deliveryStation
     */
    public java.lang.String getDeliveryStation() {
        return deliveryStation;
    }


    /**
     * Sets the deliveryStation value for this RateReplyDetail.
     * 
     * @param deliveryStation
     */
    public void setDeliveryStation(java.lang.String deliveryStation) {
        this.deliveryStation = deliveryStation;
    }


    /**
     * Gets the deliveryDayOfWeek value for this RateReplyDetail.
     * 
     * @return deliveryDayOfWeek
     */
    public com.fedex.ws.rate.v22.DayOfWeekType getDeliveryDayOfWeek() {
        return deliveryDayOfWeek;
    }


    /**
     * Sets the deliveryDayOfWeek value for this RateReplyDetail.
     * 
     * @param deliveryDayOfWeek
     */
    public void setDeliveryDayOfWeek(com.fedex.ws.rate.v22.DayOfWeekType deliveryDayOfWeek) {
        this.deliveryDayOfWeek = deliveryDayOfWeek;
    }


    /**
     * Gets the deliveryTimestamp value for this RateReplyDetail.
     * 
     * @return deliveryTimestamp
     */
    public java.util.Calendar getDeliveryTimestamp() {
        return deliveryTimestamp;
    }


    /**
     * Sets the deliveryTimestamp value for this RateReplyDetail.
     * 
     * @param deliveryTimestamp
     */
    public void setDeliveryTimestamp(java.util.Calendar deliveryTimestamp) {
        this.deliveryTimestamp = deliveryTimestamp;
    }


    /**
     * Gets the commitDetails value for this RateReplyDetail.
     * 
     * @return commitDetails
     */
    public com.fedex.ws.rate.v22.CommitDetail[] getCommitDetails() {
        return commitDetails;
    }


    /**
     * Sets the commitDetails value for this RateReplyDetail.
     * 
     * @param commitDetails
     */
    public void setCommitDetails(com.fedex.ws.rate.v22.CommitDetail[] commitDetails) {
        this.commitDetails = commitDetails;
    }

    public com.fedex.ws.rate.v22.CommitDetail getCommitDetails(int i) {
        return this.commitDetails[i];
    }

    public void setCommitDetails(int i, com.fedex.ws.rate.v22.CommitDetail _value) {
        this.commitDetails[i] = _value;
    }


    /**
     * Gets the destinationAirportId value for this RateReplyDetail.
     * 
     * @return destinationAirportId
     */
    public java.lang.String getDestinationAirportId() {
        return destinationAirportId;
    }


    /**
     * Sets the destinationAirportId value for this RateReplyDetail.
     * 
     * @param destinationAirportId
     */
    public void setDestinationAirportId(java.lang.String destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }


    /**
     * Gets the ineligibleForMoneyBackGuarantee value for this RateReplyDetail.
     * 
     * @return ineligibleForMoneyBackGuarantee
     */
    public java.lang.Boolean getIneligibleForMoneyBackGuarantee() {
        return ineligibleForMoneyBackGuarantee;
    }


    /**
     * Sets the ineligibleForMoneyBackGuarantee value for this RateReplyDetail.
     * 
     * @param ineligibleForMoneyBackGuarantee
     */
    public void setIneligibleForMoneyBackGuarantee(java.lang.Boolean ineligibleForMoneyBackGuarantee) {
        this.ineligibleForMoneyBackGuarantee = ineligibleForMoneyBackGuarantee;
    }


    /**
     * Gets the originServiceArea value for this RateReplyDetail.
     * 
     * @return originServiceArea   * Not populated by FAST service in Jan07.
     */
    public java.lang.String getOriginServiceArea() {
        return originServiceArea;
    }


    /**
     * Sets the originServiceArea value for this RateReplyDetail.
     * 
     * @param originServiceArea   * Not populated by FAST service in Jan07.
     */
    public void setOriginServiceArea(java.lang.String originServiceArea) {
        this.originServiceArea = originServiceArea;
    }


    /**
     * Gets the destinationServiceArea value for this RateReplyDetail.
     * 
     * @return destinationServiceArea   * Not populated by FAST service in Jan07.
     */
    public java.lang.String getDestinationServiceArea() {
        return destinationServiceArea;
    }


    /**
     * Sets the destinationServiceArea value for this RateReplyDetail.
     * 
     * @param destinationServiceArea   * Not populated by FAST service in Jan07.
     */
    public void setDestinationServiceArea(java.lang.String destinationServiceArea) {
        this.destinationServiceArea = destinationServiceArea;
    }


    /**
     * Gets the transitTime value for this RateReplyDetail.
     * 
     * @return transitTime   * Not populated by FAST service in Jan07.
     */
    public com.fedex.ws.rate.v22.TransitTimeType getTransitTime() {
        return transitTime;
    }


    /**
     * Sets the transitTime value for this RateReplyDetail.
     * 
     * @param transitTime   * Not populated by FAST service in Jan07.
     */
    public void setTransitTime(com.fedex.ws.rate.v22.TransitTimeType transitTime) {
        this.transitTime = transitTime;
    }


    /**
     * Gets the maximumTransitTime value for this RateReplyDetail.
     * 
     * @return maximumTransitTime   * Maximum expected transit time
     */
    public com.fedex.ws.rate.v22.TransitTimeType getMaximumTransitTime() {
        return maximumTransitTime;
    }


    /**
     * Sets the maximumTransitTime value for this RateReplyDetail.
     * 
     * @param maximumTransitTime   * Maximum expected transit time
     */
    public void setMaximumTransitTime(com.fedex.ws.rate.v22.TransitTimeType maximumTransitTime) {
        this.maximumTransitTime = maximumTransitTime;
    }


    /**
     * Gets the signatureOption value for this RateReplyDetail.
     * 
     * @return signatureOption   * Not populated by FAST service in Jan07. Actual signature option
     * applied, to allow for cases in wihch the original value conflicted
     * with other service features in the shipment.
     */
    public com.fedex.ws.rate.v22.SignatureOptionType getSignatureOption() {
        return signatureOption;
    }


    /**
     * Sets the signatureOption value for this RateReplyDetail.
     * 
     * @param signatureOption   * Not populated by FAST service in Jan07. Actual signature option
     * applied, to allow for cases in wihch the original value conflicted
     * with other service features in the shipment.
     */
    public void setSignatureOption(com.fedex.ws.rate.v22.SignatureOptionType signatureOption) {
        this.signatureOption = signatureOption;
    }


    /**
     * Gets the actualRateType value for this RateReplyDetail.
     * 
     * @return actualRateType
     */
    public com.fedex.ws.rate.v22.ReturnedRateType getActualRateType() {
        return actualRateType;
    }


    /**
     * Sets the actualRateType value for this RateReplyDetail.
     * 
     * @param actualRateType
     */
    public void setActualRateType(com.fedex.ws.rate.v22.ReturnedRateType actualRateType) {
        this.actualRateType = actualRateType;
    }


    /**
     * Gets the ratedShipmentDetails value for this RateReplyDetail.
     * 
     * @return ratedShipmentDetails   * Each element contains all rate data for a single rate type.
     */
    public com.fedex.ws.rate.v22.RatedShipmentDetail[] getRatedShipmentDetails() {
        return ratedShipmentDetails;
    }


    /**
     * Sets the ratedShipmentDetails value for this RateReplyDetail.
     * 
     * @param ratedShipmentDetails   * Each element contains all rate data for a single rate type.
     */
    public void setRatedShipmentDetails(com.fedex.ws.rate.v22.RatedShipmentDetail[] ratedShipmentDetails) {
        this.ratedShipmentDetails = ratedShipmentDetails;
    }

    public com.fedex.ws.rate.v22.RatedShipmentDetail getRatedShipmentDetails(int i) {
        return this.ratedShipmentDetails[i];
    }

    public void setRatedShipmentDetails(int i, com.fedex.ws.rate.v22.RatedShipmentDetail _value) {
        this.ratedShipmentDetails[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RateReplyDetail)) return false;
        RateReplyDetail other = (RateReplyDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.serviceType==null && other.getServiceType()==null) || 
             (this.serviceType!=null &&
              this.serviceType.equals(other.getServiceType()))) &&
            ((this.packagingType==null && other.getPackagingType()==null) || 
             (this.packagingType!=null &&
              this.packagingType.equals(other.getPackagingType()))) &&
            ((this.appliedOptions==null && other.getAppliedOptions()==null) || 
             (this.appliedOptions!=null &&
              java.util.Arrays.equals(this.appliedOptions, other.getAppliedOptions()))) &&
            ((this.appliedSubOptions==null && other.getAppliedSubOptions()==null) || 
             (this.appliedSubOptions!=null &&
              this.appliedSubOptions.equals(other.getAppliedSubOptions()))) &&
            ((this.deliveryStation==null && other.getDeliveryStation()==null) || 
             (this.deliveryStation!=null &&
              this.deliveryStation.equals(other.getDeliveryStation()))) &&
            ((this.deliveryDayOfWeek==null && other.getDeliveryDayOfWeek()==null) || 
             (this.deliveryDayOfWeek!=null &&
              this.deliveryDayOfWeek.equals(other.getDeliveryDayOfWeek()))) &&
            ((this.deliveryTimestamp==null && other.getDeliveryTimestamp()==null) || 
             (this.deliveryTimestamp!=null &&
              this.deliveryTimestamp.equals(other.getDeliveryTimestamp()))) &&
            ((this.commitDetails==null && other.getCommitDetails()==null) || 
             (this.commitDetails!=null &&
              java.util.Arrays.equals(this.commitDetails, other.getCommitDetails()))) &&
            ((this.destinationAirportId==null && other.getDestinationAirportId()==null) || 
             (this.destinationAirportId!=null &&
              this.destinationAirportId.equals(other.getDestinationAirportId()))) &&
            ((this.ineligibleForMoneyBackGuarantee==null && other.getIneligibleForMoneyBackGuarantee()==null) || 
             (this.ineligibleForMoneyBackGuarantee!=null &&
              this.ineligibleForMoneyBackGuarantee.equals(other.getIneligibleForMoneyBackGuarantee()))) &&
            ((this.originServiceArea==null && other.getOriginServiceArea()==null) || 
             (this.originServiceArea!=null &&
              this.originServiceArea.equals(other.getOriginServiceArea()))) &&
            ((this.destinationServiceArea==null && other.getDestinationServiceArea()==null) || 
             (this.destinationServiceArea!=null &&
              this.destinationServiceArea.equals(other.getDestinationServiceArea()))) &&
            ((this.transitTime==null && other.getTransitTime()==null) || 
             (this.transitTime!=null &&
              this.transitTime.equals(other.getTransitTime()))) &&
            ((this.maximumTransitTime==null && other.getMaximumTransitTime()==null) || 
             (this.maximumTransitTime!=null &&
              this.maximumTransitTime.equals(other.getMaximumTransitTime()))) &&
            ((this.signatureOption==null && other.getSignatureOption()==null) || 
             (this.signatureOption!=null &&
              this.signatureOption.equals(other.getSignatureOption()))) &&
            ((this.actualRateType==null && other.getActualRateType()==null) || 
             (this.actualRateType!=null &&
              this.actualRateType.equals(other.getActualRateType()))) &&
            ((this.ratedShipmentDetails==null && other.getRatedShipmentDetails()==null) || 
             (this.ratedShipmentDetails!=null &&
              java.util.Arrays.equals(this.ratedShipmentDetails, other.getRatedShipmentDetails())));
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
        if (getServiceType() != null) {
            _hashCode += getServiceType().hashCode();
        }
        if (getPackagingType() != null) {
            _hashCode += getPackagingType().hashCode();
        }
        if (getAppliedOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAppliedOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAppliedOptions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAppliedSubOptions() != null) {
            _hashCode += getAppliedSubOptions().hashCode();
        }
        if (getDeliveryStation() != null) {
            _hashCode += getDeliveryStation().hashCode();
        }
        if (getDeliveryDayOfWeek() != null) {
            _hashCode += getDeliveryDayOfWeek().hashCode();
        }
        if (getDeliveryTimestamp() != null) {
            _hashCode += getDeliveryTimestamp().hashCode();
        }
        if (getCommitDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCommitDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCommitDetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestinationAirportId() != null) {
            _hashCode += getDestinationAirportId().hashCode();
        }
        if (getIneligibleForMoneyBackGuarantee() != null) {
            _hashCode += getIneligibleForMoneyBackGuarantee().hashCode();
        }
        if (getOriginServiceArea() != null) {
            _hashCode += getOriginServiceArea().hashCode();
        }
        if (getDestinationServiceArea() != null) {
            _hashCode += getDestinationServiceArea().hashCode();
        }
        if (getTransitTime() != null) {
            _hashCode += getTransitTime().hashCode();
        }
        if (getMaximumTransitTime() != null) {
            _hashCode += getMaximumTransitTime().hashCode();
        }
        if (getSignatureOption() != null) {
            _hashCode += getSignatureOption().hashCode();
        }
        if (getActualRateType() != null) {
            _hashCode += getActualRateType().hashCode();
        }
        if (getRatedShipmentDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRatedShipmentDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRatedShipmentDetails(), i);
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
        new org.apache.axis.description.TypeDesc(RateReplyDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateReplyDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packagingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackagingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackagingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appliedOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AppliedOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceOptionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("appliedSubOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AppliedSubOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceSubOptionDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryStation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeliveryStation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryDayOfWeek");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeliveryDayOfWeek"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeliveryTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationAirportId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationAirportId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ineligibleForMoneyBackGuarantee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "IneligibleForMoneyBackGuarantee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originServiceArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OriginServiceArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationServiceArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationServiceArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transitTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TransitTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TransitTimeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maximumTransitTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "MaximumTransitTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TransitTimeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SignatureOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SignatureOptionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actualRateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ActualRateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ReturnedRateType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedShipmentDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedShipmentDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedShipmentDetail"));
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
