/**
 * RequestedPackageLineItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * This class rationalizes RequestedPackage and RequestedPackageSummary
 * from previous interfaces.
 */
public class RequestedPackageLineItem  implements java.io.Serializable {
    /* Used only with INDIVIDUAL_PACKAGE, as a unique identifier of
     * each requested package. */
    private org.apache.axis.types.PositiveInteger sequenceNumber;

    /* Used only with PACKAGE_GROUPS, as a unique identifier of each
     * group of identical packages. */
    private org.apache.axis.types.NonNegativeInteger groupNumber;

    /* Used only with PACKAGE_GROUPS, as a count of packages within
     * a group of identical packages. */
    private org.apache.axis.types.NonNegativeInteger groupPackageCount;

    private com.fedex.ws.rate.v22.VariableHandlingChargeDetail variableHandlingChargeDetail;

    /* Specifies the declared value for carriage of the package. The
     * declared value for carriage represents the maximum liability of FedEx
     * in connection with a shipment, including, but not limited to, any
     * loss, damage, delay, mis-delivery, nondelivery, misinformation, any
     * failure to provide information, or mis-delivery of information relating
     * to the package. This field is only used for INDIVIDUAL_PACKAGES and
     * PACKAGE_GROUPS. Ignored for PACKAGE_SUMMARY, in which case totalInsuredValue
     * and packageCount on the shipment will be used to determine this value. */
    private com.fedex.ws.rate.v22.Money insuredValue;

    /* Only used for INDIVIDUAL_PACKAGES and PACKAGE_GROUPS. Ignored
     * for PACKAGE_SUMMARY, in which case total weight and packageCount on
     * the shipment will be used to determine this value. */
    private com.fedex.ws.rate.v22.Weight weight;

    private com.fedex.ws.rate.v22.Dimensions dimensions;

    /* Provides additional detail on how the customer has physically
     * packaged this item. As of June 2009, required for packages moving
     * under international and SmartPost services. */
    private com.fedex.ws.rate.v22.PhysicalPackagingType physicalPackaging;

    /* Human-readable text describing the package. */
    private java.lang.String itemDescription;

    /* Human-readable text describing the contents of the package
     * to be used for clearance purposes. */
    private java.lang.String itemDescriptionForClearance;

    private com.fedex.ws.rate.v22.CustomerReference[] customerReferences;

    private com.fedex.ws.rate.v22.PackageSpecialServicesRequested specialServicesRequested;

    /* Only used for INDIVIDUAL_PACKAGES and PACKAGE_GROUPS. */
    private com.fedex.ws.rate.v22.ContentRecord[] contentRecords;

    public RequestedPackageLineItem() {
    }

    public RequestedPackageLineItem(
           org.apache.axis.types.PositiveInteger sequenceNumber,
           org.apache.axis.types.NonNegativeInteger groupNumber,
           org.apache.axis.types.NonNegativeInteger groupPackageCount,
           com.fedex.ws.rate.v22.VariableHandlingChargeDetail variableHandlingChargeDetail,
           com.fedex.ws.rate.v22.Money insuredValue,
           com.fedex.ws.rate.v22.Weight weight,
           com.fedex.ws.rate.v22.Dimensions dimensions,
           com.fedex.ws.rate.v22.PhysicalPackagingType physicalPackaging,
           java.lang.String itemDescription,
           java.lang.String itemDescriptionForClearance,
           com.fedex.ws.rate.v22.CustomerReference[] customerReferences,
           com.fedex.ws.rate.v22.PackageSpecialServicesRequested specialServicesRequested,
           com.fedex.ws.rate.v22.ContentRecord[] contentRecords) {
           this.sequenceNumber = sequenceNumber;
           this.groupNumber = groupNumber;
           this.groupPackageCount = groupPackageCount;
           this.variableHandlingChargeDetail = variableHandlingChargeDetail;
           this.insuredValue = insuredValue;
           this.weight = weight;
           this.dimensions = dimensions;
           this.physicalPackaging = physicalPackaging;
           this.itemDescription = itemDescription;
           this.itemDescriptionForClearance = itemDescriptionForClearance;
           this.customerReferences = customerReferences;
           this.specialServicesRequested = specialServicesRequested;
           this.contentRecords = contentRecords;
    }


    /**
     * Gets the sequenceNumber value for this RequestedPackageLineItem.
     * 
     * @return sequenceNumber   * Used only with INDIVIDUAL_PACKAGE, as a unique identifier of
     * each requested package.
     */
    public org.apache.axis.types.PositiveInteger getSequenceNumber() {
        return sequenceNumber;
    }


    /**
     * Sets the sequenceNumber value for this RequestedPackageLineItem.
     * 
     * @param sequenceNumber   * Used only with INDIVIDUAL_PACKAGE, as a unique identifier of
     * each requested package.
     */
    public void setSequenceNumber(org.apache.axis.types.PositiveInteger sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }


    /**
     * Gets the groupNumber value for this RequestedPackageLineItem.
     * 
     * @return groupNumber   * Used only with PACKAGE_GROUPS, as a unique identifier of each
     * group of identical packages.
     */
    public org.apache.axis.types.NonNegativeInteger getGroupNumber() {
        return groupNumber;
    }


    /**
     * Sets the groupNumber value for this RequestedPackageLineItem.
     * 
     * @param groupNumber   * Used only with PACKAGE_GROUPS, as a unique identifier of each
     * group of identical packages.
     */
    public void setGroupNumber(org.apache.axis.types.NonNegativeInteger groupNumber) {
        this.groupNumber = groupNumber;
    }


    /**
     * Gets the groupPackageCount value for this RequestedPackageLineItem.
     * 
     * @return groupPackageCount   * Used only with PACKAGE_GROUPS, as a count of packages within
     * a group of identical packages.
     */
    public org.apache.axis.types.NonNegativeInteger getGroupPackageCount() {
        return groupPackageCount;
    }


    /**
     * Sets the groupPackageCount value for this RequestedPackageLineItem.
     * 
     * @param groupPackageCount   * Used only with PACKAGE_GROUPS, as a count of packages within
     * a group of identical packages.
     */
    public void setGroupPackageCount(org.apache.axis.types.NonNegativeInteger groupPackageCount) {
        this.groupPackageCount = groupPackageCount;
    }


    /**
     * Gets the variableHandlingChargeDetail value for this RequestedPackageLineItem.
     * 
     * @return variableHandlingChargeDetail
     */
    public com.fedex.ws.rate.v22.VariableHandlingChargeDetail getVariableHandlingChargeDetail() {
        return variableHandlingChargeDetail;
    }


    /**
     * Sets the variableHandlingChargeDetail value for this RequestedPackageLineItem.
     * 
     * @param variableHandlingChargeDetail
     */
    public void setVariableHandlingChargeDetail(com.fedex.ws.rate.v22.VariableHandlingChargeDetail variableHandlingChargeDetail) {
        this.variableHandlingChargeDetail = variableHandlingChargeDetail;
    }


    /**
     * Gets the insuredValue value for this RequestedPackageLineItem.
     * 
     * @return insuredValue   * Specifies the declared value for carriage of the package. The
     * declared value for carriage represents the maximum liability of FedEx
     * in connection with a shipment, including, but not limited to, any
     * loss, damage, delay, mis-delivery, nondelivery, misinformation, any
     * failure to provide information, or mis-delivery of information relating
     * to the package. This field is only used for INDIVIDUAL_PACKAGES and
     * PACKAGE_GROUPS. Ignored for PACKAGE_SUMMARY, in which case totalInsuredValue
     * and packageCount on the shipment will be used to determine this value.
     */
    public com.fedex.ws.rate.v22.Money getInsuredValue() {
        return insuredValue;
    }


    /**
     * Sets the insuredValue value for this RequestedPackageLineItem.
     * 
     * @param insuredValue   * Specifies the declared value for carriage of the package. The
     * declared value for carriage represents the maximum liability of FedEx
     * in connection with a shipment, including, but not limited to, any
     * loss, damage, delay, mis-delivery, nondelivery, misinformation, any
     * failure to provide information, or mis-delivery of information relating
     * to the package. This field is only used for INDIVIDUAL_PACKAGES and
     * PACKAGE_GROUPS. Ignored for PACKAGE_SUMMARY, in which case totalInsuredValue
     * and packageCount on the shipment will be used to determine this value.
     */
    public void setInsuredValue(com.fedex.ws.rate.v22.Money insuredValue) {
        this.insuredValue = insuredValue;
    }


    /**
     * Gets the weight value for this RequestedPackageLineItem.
     * 
     * @return weight   * Only used for INDIVIDUAL_PACKAGES and PACKAGE_GROUPS. Ignored
     * for PACKAGE_SUMMARY, in which case total weight and packageCount on
     * the shipment will be used to determine this value.
     */
    public com.fedex.ws.rate.v22.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this RequestedPackageLineItem.
     * 
     * @param weight   * Only used for INDIVIDUAL_PACKAGES and PACKAGE_GROUPS. Ignored
     * for PACKAGE_SUMMARY, in which case total weight and packageCount on
     * the shipment will be used to determine this value.
     */
    public void setWeight(com.fedex.ws.rate.v22.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the dimensions value for this RequestedPackageLineItem.
     * 
     * @return dimensions
     */
    public com.fedex.ws.rate.v22.Dimensions getDimensions() {
        return dimensions;
    }


    /**
     * Sets the dimensions value for this RequestedPackageLineItem.
     * 
     * @param dimensions
     */
    public void setDimensions(com.fedex.ws.rate.v22.Dimensions dimensions) {
        this.dimensions = dimensions;
    }


    /**
     * Gets the physicalPackaging value for this RequestedPackageLineItem.
     * 
     * @return physicalPackaging   * Provides additional detail on how the customer has physically
     * packaged this item. As of June 2009, required for packages moving
     * under international and SmartPost services.
     */
    public com.fedex.ws.rate.v22.PhysicalPackagingType getPhysicalPackaging() {
        return physicalPackaging;
    }


    /**
     * Sets the physicalPackaging value for this RequestedPackageLineItem.
     * 
     * @param physicalPackaging   * Provides additional detail on how the customer has physically
     * packaged this item. As of June 2009, required for packages moving
     * under international and SmartPost services.
     */
    public void setPhysicalPackaging(com.fedex.ws.rate.v22.PhysicalPackagingType physicalPackaging) {
        this.physicalPackaging = physicalPackaging;
    }


    /**
     * Gets the itemDescription value for this RequestedPackageLineItem.
     * 
     * @return itemDescription   * Human-readable text describing the package.
     */
    public java.lang.String getItemDescription() {
        return itemDescription;
    }


    /**
     * Sets the itemDescription value for this RequestedPackageLineItem.
     * 
     * @param itemDescription   * Human-readable text describing the package.
     */
    public void setItemDescription(java.lang.String itemDescription) {
        this.itemDescription = itemDescription;
    }


    /**
     * Gets the itemDescriptionForClearance value for this RequestedPackageLineItem.
     * 
     * @return itemDescriptionForClearance   * Human-readable text describing the contents of the package
     * to be used for clearance purposes.
     */
    public java.lang.String getItemDescriptionForClearance() {
        return itemDescriptionForClearance;
    }


    /**
     * Sets the itemDescriptionForClearance value for this RequestedPackageLineItem.
     * 
     * @param itemDescriptionForClearance   * Human-readable text describing the contents of the package
     * to be used for clearance purposes.
     */
    public void setItemDescriptionForClearance(java.lang.String itemDescriptionForClearance) {
        this.itemDescriptionForClearance = itemDescriptionForClearance;
    }


    /**
     * Gets the customerReferences value for this RequestedPackageLineItem.
     * 
     * @return customerReferences
     */
    public com.fedex.ws.rate.v22.CustomerReference[] getCustomerReferences() {
        return customerReferences;
    }


    /**
     * Sets the customerReferences value for this RequestedPackageLineItem.
     * 
     * @param customerReferences
     */
    public void setCustomerReferences(com.fedex.ws.rate.v22.CustomerReference[] customerReferences) {
        this.customerReferences = customerReferences;
    }

    public com.fedex.ws.rate.v22.CustomerReference getCustomerReferences(int i) {
        return this.customerReferences[i];
    }

    public void setCustomerReferences(int i, com.fedex.ws.rate.v22.CustomerReference _value) {
        this.customerReferences[i] = _value;
    }


    /**
     * Gets the specialServicesRequested value for this RequestedPackageLineItem.
     * 
     * @return specialServicesRequested
     */
    public com.fedex.ws.rate.v22.PackageSpecialServicesRequested getSpecialServicesRequested() {
        return specialServicesRequested;
    }


    /**
     * Sets the specialServicesRequested value for this RequestedPackageLineItem.
     * 
     * @param specialServicesRequested
     */
    public void setSpecialServicesRequested(com.fedex.ws.rate.v22.PackageSpecialServicesRequested specialServicesRequested) {
        this.specialServicesRequested = specialServicesRequested;
    }


    /**
     * Gets the contentRecords value for this RequestedPackageLineItem.
     * 
     * @return contentRecords   * Only used for INDIVIDUAL_PACKAGES and PACKAGE_GROUPS.
     */
    public com.fedex.ws.rate.v22.ContentRecord[] getContentRecords() {
        return contentRecords;
    }


    /**
     * Sets the contentRecords value for this RequestedPackageLineItem.
     * 
     * @param contentRecords   * Only used for INDIVIDUAL_PACKAGES and PACKAGE_GROUPS.
     */
    public void setContentRecords(com.fedex.ws.rate.v22.ContentRecord[] contentRecords) {
        this.contentRecords = contentRecords;
    }

    public com.fedex.ws.rate.v22.ContentRecord getContentRecords(int i) {
        return this.contentRecords[i];
    }

    public void setContentRecords(int i, com.fedex.ws.rate.v22.ContentRecord _value) {
        this.contentRecords[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestedPackageLineItem)) return false;
        RequestedPackageLineItem other = (RequestedPackageLineItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sequenceNumber==null && other.getSequenceNumber()==null) || 
             (this.sequenceNumber!=null &&
              this.sequenceNumber.equals(other.getSequenceNumber()))) &&
            ((this.groupNumber==null && other.getGroupNumber()==null) || 
             (this.groupNumber!=null &&
              this.groupNumber.equals(other.getGroupNumber()))) &&
            ((this.groupPackageCount==null && other.getGroupPackageCount()==null) || 
             (this.groupPackageCount!=null &&
              this.groupPackageCount.equals(other.getGroupPackageCount()))) &&
            ((this.variableHandlingChargeDetail==null && other.getVariableHandlingChargeDetail()==null) || 
             (this.variableHandlingChargeDetail!=null &&
              this.variableHandlingChargeDetail.equals(other.getVariableHandlingChargeDetail()))) &&
            ((this.insuredValue==null && other.getInsuredValue()==null) || 
             (this.insuredValue!=null &&
              this.insuredValue.equals(other.getInsuredValue()))) &&
            ((this.weight==null && other.getWeight()==null) || 
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.dimensions==null && other.getDimensions()==null) || 
             (this.dimensions!=null &&
              this.dimensions.equals(other.getDimensions()))) &&
            ((this.physicalPackaging==null && other.getPhysicalPackaging()==null) || 
             (this.physicalPackaging!=null &&
              this.physicalPackaging.equals(other.getPhysicalPackaging()))) &&
            ((this.itemDescription==null && other.getItemDescription()==null) || 
             (this.itemDescription!=null &&
              this.itemDescription.equals(other.getItemDescription()))) &&
            ((this.itemDescriptionForClearance==null && other.getItemDescriptionForClearance()==null) || 
             (this.itemDescriptionForClearance!=null &&
              this.itemDescriptionForClearance.equals(other.getItemDescriptionForClearance()))) &&
            ((this.customerReferences==null && other.getCustomerReferences()==null) || 
             (this.customerReferences!=null &&
              java.util.Arrays.equals(this.customerReferences, other.getCustomerReferences()))) &&
            ((this.specialServicesRequested==null && other.getSpecialServicesRequested()==null) || 
             (this.specialServicesRequested!=null &&
              this.specialServicesRequested.equals(other.getSpecialServicesRequested()))) &&
            ((this.contentRecords==null && other.getContentRecords()==null) || 
             (this.contentRecords!=null &&
              java.util.Arrays.equals(this.contentRecords, other.getContentRecords())));
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
        if (getSequenceNumber() != null) {
            _hashCode += getSequenceNumber().hashCode();
        }
        if (getGroupNumber() != null) {
            _hashCode += getGroupNumber().hashCode();
        }
        if (getGroupPackageCount() != null) {
            _hashCode += getGroupPackageCount().hashCode();
        }
        if (getVariableHandlingChargeDetail() != null) {
            _hashCode += getVariableHandlingChargeDetail().hashCode();
        }
        if (getInsuredValue() != null) {
            _hashCode += getInsuredValue().hashCode();
        }
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getDimensions() != null) {
            _hashCode += getDimensions().hashCode();
        }
        if (getPhysicalPackaging() != null) {
            _hashCode += getPhysicalPackaging().hashCode();
        }
        if (getItemDescription() != null) {
            _hashCode += getItemDescription().hashCode();
        }
        if (getItemDescriptionForClearance() != null) {
            _hashCode += getItemDescriptionForClearance().hashCode();
        }
        if (getCustomerReferences() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomerReferences());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomerReferences(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSpecialServicesRequested() != null) {
            _hashCode += getSpecialServicesRequested().hashCode();
        }
        if (getContentRecords() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getContentRecords());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getContentRecords(), i);
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
        new org.apache.axis.description.TypeDesc(RequestedPackageLineItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestedPackageLineItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sequenceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SequenceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "GroupNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupPackageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "GroupPackageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableHandlingChargeDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingChargeDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingChargeDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insuredValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "InsuredValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
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
        elemField.setFieldName("dimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("physicalPackaging");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PhysicalPackaging"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PhysicalPackagingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ItemDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemDescriptionForClearance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ItemDescriptionForClearance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerReferences");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerReferences"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerReference"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialServicesRequested");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SpecialServicesRequested"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackageSpecialServicesRequested"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentRecords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ContentRecords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ContentRecord"));
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
