/**
 * CommitDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Information about the transit time and delivery commitment date
 * and time.
 */
public class CommitDetail  implements java.io.Serializable {
    /* The Commodity applicable to this commitment. */
    private java.lang.String commodityName;

    /* The FedEx service type applicable to this commitment. */
    private com.fedex.ws.rate.v22.ServiceType serviceType;

    /* Shows the specific combination of service options combined
     * with the service type that produced this committment in the set returned
     * to the caller. */
    private com.fedex.ws.rate.v22.ServiceOptionType[] appliedOptions;

    /* Supporting detail for applied options identified in preceding
     * field. */
    private com.fedex.ws.rate.v22.ServiceSubOptionDetail appliedSubOptions;

    private com.fedex.ws.rate.v22.SignatureOptionDetail derivedShipmentSignatureOption;

    private com.fedex.ws.rate.v22.SignatureOptionDetail[] derivedPackageSignatureOptions;

    private com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail derivedOriginDetail;

    private com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail derivedDestinationDetail;

    /* THe delivery commitment date/time. Express Only. */
    private java.util.Calendar commitTimestamp;

    /* The delivery commitment day of the week. */
    private com.fedex.ws.rate.v22.DayOfWeekType dayOfWeek;

    /* The number of transit days; applies to Ground and LTL Freight;
     * indicates minimum transit time for SmartPost. */
    private com.fedex.ws.rate.v22.TransitTimeType transitTime;

    /* Maximum number of transit days, for SmartPost shipments. */
    private com.fedex.ws.rate.v22.TransitTimeType maximumTransitTime;

    /* The service area code for the destination of this shipment.
     * Express only. */
    private java.lang.String destinationServiceArea;

    /* The address of the broker to be used for this shipment. */
    private com.fedex.ws.rate.v22.Address brokerAddress;

    /* The FedEx location identifier for the broker. */
    private java.lang.String brokerLocationId;

    /* The delivery commitment date/time the shipment will arrive
     * at the border. */
    private java.util.Calendar brokerCommitTimestamp;

    /* The delivery commitment day of the week the shipment will arrive
     * at the border. */
    private com.fedex.ws.rate.v22.DayOfWeekType brokerCommitDayOfWeek;

    /* The number of days it will take for the shipment to make it
     * from broker to destination */
    private org.apache.axis.types.NonNegativeInteger brokerToDestinationDays;

    /* The delivery commitment date for shipment served by GSP (Global
     * Service Provider) */
    private java.util.Date proofOfDeliveryDate;

    /* The delivery commitment day of the week for the shipment served
     * by GSP (Global Service Provider) */
    private com.fedex.ws.rate.v22.DayOfWeekType proofOfDeliveryDayOfWeek;

    /* Messages concerning the ability to provide an accurate delivery
     * commitment on an International commit quote. These could be messages
     * providing information about why a commitment could not be returned
     * or a successful message such as "REQUEST COMPLETED" */
    private com.fedex.ws.rate.v22.Notification[] commitMessages;

    /* Messages concerning the delivery commitment on an International
     * commit quote such as "0:00 A.M. IF NO CUSTOMS DELAY" */
    private java.lang.String[] deliveryMessages;

    /* Information about why a shipment delivery is delayed and at
     * what level (country/service etc.). */
    private com.fedex.ws.rate.v22.DelayDetail[] delayDetails;

    private com.fedex.ws.rate.v22.InternationalDocumentContentType documentContent;

    /* Required documentation for this shipment. */
    private com.fedex.ws.rate.v22.RequiredShippingDocumentType[] requiredDocuments;

    /* Freight origin and destination city center information and
     * total distance between origin and destination city centers. */
    private com.fedex.ws.rate.v22.FreightCommitDetail freightCommitDetail;

    public CommitDetail() {
    }

    public CommitDetail(
           java.lang.String commodityName,
           com.fedex.ws.rate.v22.ServiceType serviceType,
           com.fedex.ws.rate.v22.ServiceOptionType[] appliedOptions,
           com.fedex.ws.rate.v22.ServiceSubOptionDetail appliedSubOptions,
           com.fedex.ws.rate.v22.SignatureOptionDetail derivedShipmentSignatureOption,
           com.fedex.ws.rate.v22.SignatureOptionDetail[] derivedPackageSignatureOptions,
           com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail derivedOriginDetail,
           com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail derivedDestinationDetail,
           java.util.Calendar commitTimestamp,
           com.fedex.ws.rate.v22.DayOfWeekType dayOfWeek,
           com.fedex.ws.rate.v22.TransitTimeType transitTime,
           com.fedex.ws.rate.v22.TransitTimeType maximumTransitTime,
           java.lang.String destinationServiceArea,
           com.fedex.ws.rate.v22.Address brokerAddress,
           java.lang.String brokerLocationId,
           java.util.Calendar brokerCommitTimestamp,
           com.fedex.ws.rate.v22.DayOfWeekType brokerCommitDayOfWeek,
           org.apache.axis.types.NonNegativeInteger brokerToDestinationDays,
           java.util.Date proofOfDeliveryDate,
           com.fedex.ws.rate.v22.DayOfWeekType proofOfDeliveryDayOfWeek,
           com.fedex.ws.rate.v22.Notification[] commitMessages,
           java.lang.String[] deliveryMessages,
           com.fedex.ws.rate.v22.DelayDetail[] delayDetails,
           com.fedex.ws.rate.v22.InternationalDocumentContentType documentContent,
           com.fedex.ws.rate.v22.RequiredShippingDocumentType[] requiredDocuments,
           com.fedex.ws.rate.v22.FreightCommitDetail freightCommitDetail) {
           this.commodityName = commodityName;
           this.serviceType = serviceType;
           this.appliedOptions = appliedOptions;
           this.appliedSubOptions = appliedSubOptions;
           this.derivedShipmentSignatureOption = derivedShipmentSignatureOption;
           this.derivedPackageSignatureOptions = derivedPackageSignatureOptions;
           this.derivedOriginDetail = derivedOriginDetail;
           this.derivedDestinationDetail = derivedDestinationDetail;
           this.commitTimestamp = commitTimestamp;
           this.dayOfWeek = dayOfWeek;
           this.transitTime = transitTime;
           this.maximumTransitTime = maximumTransitTime;
           this.destinationServiceArea = destinationServiceArea;
           this.brokerAddress = brokerAddress;
           this.brokerLocationId = brokerLocationId;
           this.brokerCommitTimestamp = brokerCommitTimestamp;
           this.brokerCommitDayOfWeek = brokerCommitDayOfWeek;
           this.brokerToDestinationDays = brokerToDestinationDays;
           this.proofOfDeliveryDate = proofOfDeliveryDate;
           this.proofOfDeliveryDayOfWeek = proofOfDeliveryDayOfWeek;
           this.commitMessages = commitMessages;
           this.deliveryMessages = deliveryMessages;
           this.delayDetails = delayDetails;
           this.documentContent = documentContent;
           this.requiredDocuments = requiredDocuments;
           this.freightCommitDetail = freightCommitDetail;
    }


    /**
     * Gets the commodityName value for this CommitDetail.
     * 
     * @return commodityName   * The Commodity applicable to this commitment.
     */
    public java.lang.String getCommodityName() {
        return commodityName;
    }


    /**
     * Sets the commodityName value for this CommitDetail.
     * 
     * @param commodityName   * The Commodity applicable to this commitment.
     */
    public void setCommodityName(java.lang.String commodityName) {
        this.commodityName = commodityName;
    }


    /**
     * Gets the serviceType value for this CommitDetail.
     * 
     * @return serviceType   * The FedEx service type applicable to this commitment.
     */
    public com.fedex.ws.rate.v22.ServiceType getServiceType() {
        return serviceType;
    }


    /**
     * Sets the serviceType value for this CommitDetail.
     * 
     * @param serviceType   * The FedEx service type applicable to this commitment.
     */
    public void setServiceType(com.fedex.ws.rate.v22.ServiceType serviceType) {
        this.serviceType = serviceType;
    }


    /**
     * Gets the appliedOptions value for this CommitDetail.
     * 
     * @return appliedOptions   * Shows the specific combination of service options combined
     * with the service type that produced this committment in the set returned
     * to the caller.
     */
    public com.fedex.ws.rate.v22.ServiceOptionType[] getAppliedOptions() {
        return appliedOptions;
    }


    /**
     * Sets the appliedOptions value for this CommitDetail.
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
     * Gets the appliedSubOptions value for this CommitDetail.
     * 
     * @return appliedSubOptions   * Supporting detail for applied options identified in preceding
     * field.
     */
    public com.fedex.ws.rate.v22.ServiceSubOptionDetail getAppliedSubOptions() {
        return appliedSubOptions;
    }


    /**
     * Sets the appliedSubOptions value for this CommitDetail.
     * 
     * @param appliedSubOptions   * Supporting detail for applied options identified in preceding
     * field.
     */
    public void setAppliedSubOptions(com.fedex.ws.rate.v22.ServiceSubOptionDetail appliedSubOptions) {
        this.appliedSubOptions = appliedSubOptions;
    }


    /**
     * Gets the derivedShipmentSignatureOption value for this CommitDetail.
     * 
     * @return derivedShipmentSignatureOption
     */
    public com.fedex.ws.rate.v22.SignatureOptionDetail getDerivedShipmentSignatureOption() {
        return derivedShipmentSignatureOption;
    }


    /**
     * Sets the derivedShipmentSignatureOption value for this CommitDetail.
     * 
     * @param derivedShipmentSignatureOption
     */
    public void setDerivedShipmentSignatureOption(com.fedex.ws.rate.v22.SignatureOptionDetail derivedShipmentSignatureOption) {
        this.derivedShipmentSignatureOption = derivedShipmentSignatureOption;
    }


    /**
     * Gets the derivedPackageSignatureOptions value for this CommitDetail.
     * 
     * @return derivedPackageSignatureOptions
     */
    public com.fedex.ws.rate.v22.SignatureOptionDetail[] getDerivedPackageSignatureOptions() {
        return derivedPackageSignatureOptions;
    }


    /**
     * Sets the derivedPackageSignatureOptions value for this CommitDetail.
     * 
     * @param derivedPackageSignatureOptions
     */
    public void setDerivedPackageSignatureOptions(com.fedex.ws.rate.v22.SignatureOptionDetail[] derivedPackageSignatureOptions) {
        this.derivedPackageSignatureOptions = derivedPackageSignatureOptions;
    }

    public com.fedex.ws.rate.v22.SignatureOptionDetail getDerivedPackageSignatureOptions(int i) {
        return this.derivedPackageSignatureOptions[i];
    }

    public void setDerivedPackageSignatureOptions(int i, com.fedex.ws.rate.v22.SignatureOptionDetail _value) {
        this.derivedPackageSignatureOptions[i] = _value;
    }


    /**
     * Gets the derivedOriginDetail value for this CommitDetail.
     * 
     * @return derivedOriginDetail
     */
    public com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail getDerivedOriginDetail() {
        return derivedOriginDetail;
    }


    /**
     * Sets the derivedOriginDetail value for this CommitDetail.
     * 
     * @param derivedOriginDetail
     */
    public void setDerivedOriginDetail(com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail derivedOriginDetail) {
        this.derivedOriginDetail = derivedOriginDetail;
    }


    /**
     * Gets the derivedDestinationDetail value for this CommitDetail.
     * 
     * @return derivedDestinationDetail
     */
    public com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail getDerivedDestinationDetail() {
        return derivedDestinationDetail;
    }


    /**
     * Sets the derivedDestinationDetail value for this CommitDetail.
     * 
     * @param derivedDestinationDetail
     */
    public void setDerivedDestinationDetail(com.fedex.ws.rate.v22.CleansedAddressAndLocationDetail derivedDestinationDetail) {
        this.derivedDestinationDetail = derivedDestinationDetail;
    }


    /**
     * Gets the commitTimestamp value for this CommitDetail.
     * 
     * @return commitTimestamp   * THe delivery commitment date/time. Express Only.
     */
    public java.util.Calendar getCommitTimestamp() {
        return commitTimestamp;
    }


    /**
     * Sets the commitTimestamp value for this CommitDetail.
     * 
     * @param commitTimestamp   * THe delivery commitment date/time. Express Only.
     */
    public void setCommitTimestamp(java.util.Calendar commitTimestamp) {
        this.commitTimestamp = commitTimestamp;
    }


    /**
     * Gets the dayOfWeek value for this CommitDetail.
     * 
     * @return dayOfWeek   * The delivery commitment day of the week.
     */
    public com.fedex.ws.rate.v22.DayOfWeekType getDayOfWeek() {
        return dayOfWeek;
    }


    /**
     * Sets the dayOfWeek value for this CommitDetail.
     * 
     * @param dayOfWeek   * The delivery commitment day of the week.
     */
    public void setDayOfWeek(com.fedex.ws.rate.v22.DayOfWeekType dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    /**
     * Gets the transitTime value for this CommitDetail.
     * 
     * @return transitTime   * The number of transit days; applies to Ground and LTL Freight;
     * indicates minimum transit time for SmartPost.
     */
    public com.fedex.ws.rate.v22.TransitTimeType getTransitTime() {
        return transitTime;
    }


    /**
     * Sets the transitTime value for this CommitDetail.
     * 
     * @param transitTime   * The number of transit days; applies to Ground and LTL Freight;
     * indicates minimum transit time for SmartPost.
     */
    public void setTransitTime(com.fedex.ws.rate.v22.TransitTimeType transitTime) {
        this.transitTime = transitTime;
    }


    /**
     * Gets the maximumTransitTime value for this CommitDetail.
     * 
     * @return maximumTransitTime   * Maximum number of transit days, for SmartPost shipments.
     */
    public com.fedex.ws.rate.v22.TransitTimeType getMaximumTransitTime() {
        return maximumTransitTime;
    }


    /**
     * Sets the maximumTransitTime value for this CommitDetail.
     * 
     * @param maximumTransitTime   * Maximum number of transit days, for SmartPost shipments.
     */
    public void setMaximumTransitTime(com.fedex.ws.rate.v22.TransitTimeType maximumTransitTime) {
        this.maximumTransitTime = maximumTransitTime;
    }


    /**
     * Gets the destinationServiceArea value for this CommitDetail.
     * 
     * @return destinationServiceArea   * The service area code for the destination of this shipment.
     * Express only.
     */
    public java.lang.String getDestinationServiceArea() {
        return destinationServiceArea;
    }


    /**
     * Sets the destinationServiceArea value for this CommitDetail.
     * 
     * @param destinationServiceArea   * The service area code for the destination of this shipment.
     * Express only.
     */
    public void setDestinationServiceArea(java.lang.String destinationServiceArea) {
        this.destinationServiceArea = destinationServiceArea;
    }


    /**
     * Gets the brokerAddress value for this CommitDetail.
     * 
     * @return brokerAddress   * The address of the broker to be used for this shipment.
     */
    public com.fedex.ws.rate.v22.Address getBrokerAddress() {
        return brokerAddress;
    }


    /**
     * Sets the brokerAddress value for this CommitDetail.
     * 
     * @param brokerAddress   * The address of the broker to be used for this shipment.
     */
    public void setBrokerAddress(com.fedex.ws.rate.v22.Address brokerAddress) {
        this.brokerAddress = brokerAddress;
    }


    /**
     * Gets the brokerLocationId value for this CommitDetail.
     * 
     * @return brokerLocationId   * The FedEx location identifier for the broker.
     */
    public java.lang.String getBrokerLocationId() {
        return brokerLocationId;
    }


    /**
     * Sets the brokerLocationId value for this CommitDetail.
     * 
     * @param brokerLocationId   * The FedEx location identifier for the broker.
     */
    public void setBrokerLocationId(java.lang.String brokerLocationId) {
        this.brokerLocationId = brokerLocationId;
    }


    /**
     * Gets the brokerCommitTimestamp value for this CommitDetail.
     * 
     * @return brokerCommitTimestamp   * The delivery commitment date/time the shipment will arrive
     * at the border.
     */
    public java.util.Calendar getBrokerCommitTimestamp() {
        return brokerCommitTimestamp;
    }


    /**
     * Sets the brokerCommitTimestamp value for this CommitDetail.
     * 
     * @param brokerCommitTimestamp   * The delivery commitment date/time the shipment will arrive
     * at the border.
     */
    public void setBrokerCommitTimestamp(java.util.Calendar brokerCommitTimestamp) {
        this.brokerCommitTimestamp = brokerCommitTimestamp;
    }


    /**
     * Gets the brokerCommitDayOfWeek value for this CommitDetail.
     * 
     * @return brokerCommitDayOfWeek   * The delivery commitment day of the week the shipment will arrive
     * at the border.
     */
    public com.fedex.ws.rate.v22.DayOfWeekType getBrokerCommitDayOfWeek() {
        return brokerCommitDayOfWeek;
    }


    /**
     * Sets the brokerCommitDayOfWeek value for this CommitDetail.
     * 
     * @param brokerCommitDayOfWeek   * The delivery commitment day of the week the shipment will arrive
     * at the border.
     */
    public void setBrokerCommitDayOfWeek(com.fedex.ws.rate.v22.DayOfWeekType brokerCommitDayOfWeek) {
        this.brokerCommitDayOfWeek = brokerCommitDayOfWeek;
    }


    /**
     * Gets the brokerToDestinationDays value for this CommitDetail.
     * 
     * @return brokerToDestinationDays   * The number of days it will take for the shipment to make it
     * from broker to destination
     */
    public org.apache.axis.types.NonNegativeInteger getBrokerToDestinationDays() {
        return brokerToDestinationDays;
    }


    /**
     * Sets the brokerToDestinationDays value for this CommitDetail.
     * 
     * @param brokerToDestinationDays   * The number of days it will take for the shipment to make it
     * from broker to destination
     */
    public void setBrokerToDestinationDays(org.apache.axis.types.NonNegativeInteger brokerToDestinationDays) {
        this.brokerToDestinationDays = brokerToDestinationDays;
    }


    /**
     * Gets the proofOfDeliveryDate value for this CommitDetail.
     * 
     * @return proofOfDeliveryDate   * The delivery commitment date for shipment served by GSP (Global
     * Service Provider)
     */
    public java.util.Date getProofOfDeliveryDate() {
        return proofOfDeliveryDate;
    }


    /**
     * Sets the proofOfDeliveryDate value for this CommitDetail.
     * 
     * @param proofOfDeliveryDate   * The delivery commitment date for shipment served by GSP (Global
     * Service Provider)
     */
    public void setProofOfDeliveryDate(java.util.Date proofOfDeliveryDate) {
        this.proofOfDeliveryDate = proofOfDeliveryDate;
    }


    /**
     * Gets the proofOfDeliveryDayOfWeek value for this CommitDetail.
     * 
     * @return proofOfDeliveryDayOfWeek   * The delivery commitment day of the week for the shipment served
     * by GSP (Global Service Provider)
     */
    public com.fedex.ws.rate.v22.DayOfWeekType getProofOfDeliveryDayOfWeek() {
        return proofOfDeliveryDayOfWeek;
    }


    /**
     * Sets the proofOfDeliveryDayOfWeek value for this CommitDetail.
     * 
     * @param proofOfDeliveryDayOfWeek   * The delivery commitment day of the week for the shipment served
     * by GSP (Global Service Provider)
     */
    public void setProofOfDeliveryDayOfWeek(com.fedex.ws.rate.v22.DayOfWeekType proofOfDeliveryDayOfWeek) {
        this.proofOfDeliveryDayOfWeek = proofOfDeliveryDayOfWeek;
    }


    /**
     * Gets the commitMessages value for this CommitDetail.
     * 
     * @return commitMessages   * Messages concerning the ability to provide an accurate delivery
     * commitment on an International commit quote. These could be messages
     * providing information about why a commitment could not be returned
     * or a successful message such as "REQUEST COMPLETED"
     */
    public com.fedex.ws.rate.v22.Notification[] getCommitMessages() {
        return commitMessages;
    }


    /**
     * Sets the commitMessages value for this CommitDetail.
     * 
     * @param commitMessages   * Messages concerning the ability to provide an accurate delivery
     * commitment on an International commit quote. These could be messages
     * providing information about why a commitment could not be returned
     * or a successful message such as "REQUEST COMPLETED"
     */
    public void setCommitMessages(com.fedex.ws.rate.v22.Notification[] commitMessages) {
        this.commitMessages = commitMessages;
    }

    public com.fedex.ws.rate.v22.Notification getCommitMessages(int i) {
        return this.commitMessages[i];
    }

    public void setCommitMessages(int i, com.fedex.ws.rate.v22.Notification _value) {
        this.commitMessages[i] = _value;
    }


    /**
     * Gets the deliveryMessages value for this CommitDetail.
     * 
     * @return deliveryMessages   * Messages concerning the delivery commitment on an International
     * commit quote such as "0:00 A.M. IF NO CUSTOMS DELAY"
     */
    public java.lang.String[] getDeliveryMessages() {
        return deliveryMessages;
    }


    /**
     * Sets the deliveryMessages value for this CommitDetail.
     * 
     * @param deliveryMessages   * Messages concerning the delivery commitment on an International
     * commit quote such as "0:00 A.M. IF NO CUSTOMS DELAY"
     */
    public void setDeliveryMessages(java.lang.String[] deliveryMessages) {
        this.deliveryMessages = deliveryMessages;
    }

    public java.lang.String getDeliveryMessages(int i) {
        return this.deliveryMessages[i];
    }

    public void setDeliveryMessages(int i, java.lang.String _value) {
        this.deliveryMessages[i] = _value;
    }


    /**
     * Gets the delayDetails value for this CommitDetail.
     * 
     * @return delayDetails   * Information about why a shipment delivery is delayed and at
     * what level (country/service etc.).
     */
    public com.fedex.ws.rate.v22.DelayDetail[] getDelayDetails() {
        return delayDetails;
    }


    /**
     * Sets the delayDetails value for this CommitDetail.
     * 
     * @param delayDetails   * Information about why a shipment delivery is delayed and at
     * what level (country/service etc.).
     */
    public void setDelayDetails(com.fedex.ws.rate.v22.DelayDetail[] delayDetails) {
        this.delayDetails = delayDetails;
    }

    public com.fedex.ws.rate.v22.DelayDetail getDelayDetails(int i) {
        return this.delayDetails[i];
    }

    public void setDelayDetails(int i, com.fedex.ws.rate.v22.DelayDetail _value) {
        this.delayDetails[i] = _value;
    }


    /**
     * Gets the documentContent value for this CommitDetail.
     * 
     * @return documentContent
     */
    public com.fedex.ws.rate.v22.InternationalDocumentContentType getDocumentContent() {
        return documentContent;
    }


    /**
     * Sets the documentContent value for this CommitDetail.
     * 
     * @param documentContent
     */
    public void setDocumentContent(com.fedex.ws.rate.v22.InternationalDocumentContentType documentContent) {
        this.documentContent = documentContent;
    }


    /**
     * Gets the requiredDocuments value for this CommitDetail.
     * 
     * @return requiredDocuments   * Required documentation for this shipment.
     */
    public com.fedex.ws.rate.v22.RequiredShippingDocumentType[] getRequiredDocuments() {
        return requiredDocuments;
    }


    /**
     * Sets the requiredDocuments value for this CommitDetail.
     * 
     * @param requiredDocuments   * Required documentation for this shipment.
     */
    public void setRequiredDocuments(com.fedex.ws.rate.v22.RequiredShippingDocumentType[] requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public com.fedex.ws.rate.v22.RequiredShippingDocumentType getRequiredDocuments(int i) {
        return this.requiredDocuments[i];
    }

    public void setRequiredDocuments(int i, com.fedex.ws.rate.v22.RequiredShippingDocumentType _value) {
        this.requiredDocuments[i] = _value;
    }


    /**
     * Gets the freightCommitDetail value for this CommitDetail.
     * 
     * @return freightCommitDetail   * Freight origin and destination city center information and
     * total distance between origin and destination city centers.
     */
    public com.fedex.ws.rate.v22.FreightCommitDetail getFreightCommitDetail() {
        return freightCommitDetail;
    }


    /**
     * Sets the freightCommitDetail value for this CommitDetail.
     * 
     * @param freightCommitDetail   * Freight origin and destination city center information and
     * total distance between origin and destination city centers.
     */
    public void setFreightCommitDetail(com.fedex.ws.rate.v22.FreightCommitDetail freightCommitDetail) {
        this.freightCommitDetail = freightCommitDetail;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommitDetail)) return false;
        CommitDetail other = (CommitDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.commodityName==null && other.getCommodityName()==null) || 
             (this.commodityName!=null &&
              this.commodityName.equals(other.getCommodityName()))) &&
            ((this.serviceType==null && other.getServiceType()==null) || 
             (this.serviceType!=null &&
              this.serviceType.equals(other.getServiceType()))) &&
            ((this.appliedOptions==null && other.getAppliedOptions()==null) || 
             (this.appliedOptions!=null &&
              java.util.Arrays.equals(this.appliedOptions, other.getAppliedOptions()))) &&
            ((this.appliedSubOptions==null && other.getAppliedSubOptions()==null) || 
             (this.appliedSubOptions!=null &&
              this.appliedSubOptions.equals(other.getAppliedSubOptions()))) &&
            ((this.derivedShipmentSignatureOption==null && other.getDerivedShipmentSignatureOption()==null) || 
             (this.derivedShipmentSignatureOption!=null &&
              this.derivedShipmentSignatureOption.equals(other.getDerivedShipmentSignatureOption()))) &&
            ((this.derivedPackageSignatureOptions==null && other.getDerivedPackageSignatureOptions()==null) || 
             (this.derivedPackageSignatureOptions!=null &&
              java.util.Arrays.equals(this.derivedPackageSignatureOptions, other.getDerivedPackageSignatureOptions()))) &&
            ((this.derivedOriginDetail==null && other.getDerivedOriginDetail()==null) || 
             (this.derivedOriginDetail!=null &&
              this.derivedOriginDetail.equals(other.getDerivedOriginDetail()))) &&
            ((this.derivedDestinationDetail==null && other.getDerivedDestinationDetail()==null) || 
             (this.derivedDestinationDetail!=null &&
              this.derivedDestinationDetail.equals(other.getDerivedDestinationDetail()))) &&
            ((this.commitTimestamp==null && other.getCommitTimestamp()==null) || 
             (this.commitTimestamp!=null &&
              this.commitTimestamp.equals(other.getCommitTimestamp()))) &&
            ((this.dayOfWeek==null && other.getDayOfWeek()==null) || 
             (this.dayOfWeek!=null &&
              this.dayOfWeek.equals(other.getDayOfWeek()))) &&
            ((this.transitTime==null && other.getTransitTime()==null) || 
             (this.transitTime!=null &&
              this.transitTime.equals(other.getTransitTime()))) &&
            ((this.maximumTransitTime==null && other.getMaximumTransitTime()==null) || 
             (this.maximumTransitTime!=null &&
              this.maximumTransitTime.equals(other.getMaximumTransitTime()))) &&
            ((this.destinationServiceArea==null && other.getDestinationServiceArea()==null) || 
             (this.destinationServiceArea!=null &&
              this.destinationServiceArea.equals(other.getDestinationServiceArea()))) &&
            ((this.brokerAddress==null && other.getBrokerAddress()==null) || 
             (this.brokerAddress!=null &&
              this.brokerAddress.equals(other.getBrokerAddress()))) &&
            ((this.brokerLocationId==null && other.getBrokerLocationId()==null) || 
             (this.brokerLocationId!=null &&
              this.brokerLocationId.equals(other.getBrokerLocationId()))) &&
            ((this.brokerCommitTimestamp==null && other.getBrokerCommitTimestamp()==null) || 
             (this.brokerCommitTimestamp!=null &&
              this.brokerCommitTimestamp.equals(other.getBrokerCommitTimestamp()))) &&
            ((this.brokerCommitDayOfWeek==null && other.getBrokerCommitDayOfWeek()==null) || 
             (this.brokerCommitDayOfWeek!=null &&
              this.brokerCommitDayOfWeek.equals(other.getBrokerCommitDayOfWeek()))) &&
            ((this.brokerToDestinationDays==null && other.getBrokerToDestinationDays()==null) || 
             (this.brokerToDestinationDays!=null &&
              this.brokerToDestinationDays.equals(other.getBrokerToDestinationDays()))) &&
            ((this.proofOfDeliveryDate==null && other.getProofOfDeliveryDate()==null) || 
             (this.proofOfDeliveryDate!=null &&
              this.proofOfDeliveryDate.equals(other.getProofOfDeliveryDate()))) &&
            ((this.proofOfDeliveryDayOfWeek==null && other.getProofOfDeliveryDayOfWeek()==null) || 
             (this.proofOfDeliveryDayOfWeek!=null &&
              this.proofOfDeliveryDayOfWeek.equals(other.getProofOfDeliveryDayOfWeek()))) &&
            ((this.commitMessages==null && other.getCommitMessages()==null) || 
             (this.commitMessages!=null &&
              java.util.Arrays.equals(this.commitMessages, other.getCommitMessages()))) &&
            ((this.deliveryMessages==null && other.getDeliveryMessages()==null) || 
             (this.deliveryMessages!=null &&
              java.util.Arrays.equals(this.deliveryMessages, other.getDeliveryMessages()))) &&
            ((this.delayDetails==null && other.getDelayDetails()==null) || 
             (this.delayDetails!=null &&
              java.util.Arrays.equals(this.delayDetails, other.getDelayDetails()))) &&
            ((this.documentContent==null && other.getDocumentContent()==null) || 
             (this.documentContent!=null &&
              this.documentContent.equals(other.getDocumentContent()))) &&
            ((this.requiredDocuments==null && other.getRequiredDocuments()==null) || 
             (this.requiredDocuments!=null &&
              java.util.Arrays.equals(this.requiredDocuments, other.getRequiredDocuments()))) &&
            ((this.freightCommitDetail==null && other.getFreightCommitDetail()==null) || 
             (this.freightCommitDetail!=null &&
              this.freightCommitDetail.equals(other.getFreightCommitDetail())));
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
        if (getCommodityName() != null) {
            _hashCode += getCommodityName().hashCode();
        }
        if (getServiceType() != null) {
            _hashCode += getServiceType().hashCode();
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
        if (getDerivedShipmentSignatureOption() != null) {
            _hashCode += getDerivedShipmentSignatureOption().hashCode();
        }
        if (getDerivedPackageSignatureOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDerivedPackageSignatureOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDerivedPackageSignatureOptions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDerivedOriginDetail() != null) {
            _hashCode += getDerivedOriginDetail().hashCode();
        }
        if (getDerivedDestinationDetail() != null) {
            _hashCode += getDerivedDestinationDetail().hashCode();
        }
        if (getCommitTimestamp() != null) {
            _hashCode += getCommitTimestamp().hashCode();
        }
        if (getDayOfWeek() != null) {
            _hashCode += getDayOfWeek().hashCode();
        }
        if (getTransitTime() != null) {
            _hashCode += getTransitTime().hashCode();
        }
        if (getMaximumTransitTime() != null) {
            _hashCode += getMaximumTransitTime().hashCode();
        }
        if (getDestinationServiceArea() != null) {
            _hashCode += getDestinationServiceArea().hashCode();
        }
        if (getBrokerAddress() != null) {
            _hashCode += getBrokerAddress().hashCode();
        }
        if (getBrokerLocationId() != null) {
            _hashCode += getBrokerLocationId().hashCode();
        }
        if (getBrokerCommitTimestamp() != null) {
            _hashCode += getBrokerCommitTimestamp().hashCode();
        }
        if (getBrokerCommitDayOfWeek() != null) {
            _hashCode += getBrokerCommitDayOfWeek().hashCode();
        }
        if (getBrokerToDestinationDays() != null) {
            _hashCode += getBrokerToDestinationDays().hashCode();
        }
        if (getProofOfDeliveryDate() != null) {
            _hashCode += getProofOfDeliveryDate().hashCode();
        }
        if (getProofOfDeliveryDayOfWeek() != null) {
            _hashCode += getProofOfDeliveryDayOfWeek().hashCode();
        }
        if (getCommitMessages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCommitMessages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCommitMessages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeliveryMessages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeliveryMessages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeliveryMessages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDelayDetails() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDelayDetails());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDelayDetails(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDocumentContent() != null) {
            _hashCode += getDocumentContent().hashCode();
        }
        if (getRequiredDocuments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRequiredDocuments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRequiredDocuments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFreightCommitDetail() != null) {
            _hashCode += getFreightCommitDetail().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommitDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commodityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommodityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceType"));
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
        elemField.setFieldName("derivedShipmentSignatureOption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DerivedShipmentSignatureOption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SignatureOptionDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("derivedPackageSignatureOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DerivedPackageSignatureOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SignatureOptionDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("derivedOriginDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DerivedOriginDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CleansedAddressAndLocationDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("derivedDestinationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DerivedDestinationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CleansedAddressAndLocationDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dayOfWeek");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeek"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
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
        elemField.setFieldName("destinationServiceArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationServiceArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brokerAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BrokerAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brokerLocationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BrokerLocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brokerCommitTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BrokerCommitTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brokerCommitDayOfWeek");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BrokerCommitDayOfWeek"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("brokerToDestinationDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BrokerToDestinationDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proofOfDeliveryDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProofOfDeliveryDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("proofOfDeliveryDayOfWeek");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProofOfDeliveryDayOfWeek"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commitMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Notification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeliveryMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delayDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DelayDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DelayDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentContent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocumentContent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "InternationalDocumentContentType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requiredDocuments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequiredDocuments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequiredShippingDocumentType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightCommitDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightCommitDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightCommitDetail"));
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
