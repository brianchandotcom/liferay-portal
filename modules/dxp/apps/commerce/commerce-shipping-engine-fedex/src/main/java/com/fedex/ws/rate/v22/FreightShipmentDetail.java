/**
 * FreightShipmentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Data applicable to shipments using FEDEX_FREIGHT_ECONOMY and FEDEX_FREIGHT_PRIORITY
 * services.
 */
public class FreightShipmentDetail  implements java.io.Serializable {
    /* Account number used with FEDEX_FREIGHT service. */
    private java.lang.String fedExFreightAccountNumber;

    /* Used for validating FedEx Freight account number and (optionally)
     * identifying third party payment on the bill of lading. */
    private com.fedex.ws.rate.v22.ContactAndAddress fedExFreightBillingContactAndAddress;

    /* Used in connection with "Send Bill To" (SBT) identification
     * of customer's account used for billing. */
    private com.fedex.ws.rate.v22.Party alternateBilling;

    /* Indicates the role of the party submitting the transaction. */
    private com.fedex.ws.rate.v22.FreightShipmentRoleType role;

    /* Designates the terms of the "collect" payment for a Freight
     * Shipment. */
    private com.fedex.ws.rate.v22.FreightCollectTermsType collectTermsType;

    /* Identifies the declared value for the shipment */
    private com.fedex.ws.rate.v22.Money declaredValuePerUnit;

    /* Identifies the declared value units corresponding to the above
     * defined declared value */
    private java.lang.String declaredValueUnits;

    private com.fedex.ws.rate.v22.LiabilityCoverageDetail liabilityCoverageDetail;

    /* Identifiers for promotional discounts offered to customers. */
    private java.lang.String[] coupons;

    /* Total number of individual handling units in the entire shipment
     * (for unit pricing). */
    private org.apache.axis.types.NonNegativeInteger totalHandlingUnits;

    /* Estimated discount rate provided by client for unsecured rate
     * quote. */
    private java.math.BigDecimal clientDiscountPercent;

    /* Total weight of pallets used in shipment. */
    private com.fedex.ws.rate.v22.Weight palletWeight;

    /* Overall shipment dimensions. */
    private com.fedex.ws.rate.v22.Dimensions shipmentDimensions;

    /* Description for the shipment. */
    private java.lang.String comment;

    /* Specifies which party will pay surcharges for any special services
     * which support split billing. */
    private com.fedex.ws.rate.v22.FreightSpecialServicePayment[] specialServicePayments;

    private java.lang.String hazardousMaterialsOfferor;

    /* Details of the commodities in the shipment. */
    private com.fedex.ws.rate.v22.FreightShipmentLineItem[] lineItems;

    public FreightShipmentDetail() {
    }

    public FreightShipmentDetail(
           java.lang.String fedExFreightAccountNumber,
           com.fedex.ws.rate.v22.ContactAndAddress fedExFreightBillingContactAndAddress,
           com.fedex.ws.rate.v22.Party alternateBilling,
           com.fedex.ws.rate.v22.FreightShipmentRoleType role,
           com.fedex.ws.rate.v22.FreightCollectTermsType collectTermsType,
           com.fedex.ws.rate.v22.Money declaredValuePerUnit,
           java.lang.String declaredValueUnits,
           com.fedex.ws.rate.v22.LiabilityCoverageDetail liabilityCoverageDetail,
           java.lang.String[] coupons,
           org.apache.axis.types.NonNegativeInteger totalHandlingUnits,
           java.math.BigDecimal clientDiscountPercent,
           com.fedex.ws.rate.v22.Weight palletWeight,
           com.fedex.ws.rate.v22.Dimensions shipmentDimensions,
           java.lang.String comment,
           com.fedex.ws.rate.v22.FreightSpecialServicePayment[] specialServicePayments,
           java.lang.String hazardousMaterialsOfferor,
           com.fedex.ws.rate.v22.FreightShipmentLineItem[] lineItems) {
           this.fedExFreightAccountNumber = fedExFreightAccountNumber;
           this.fedExFreightBillingContactAndAddress = fedExFreightBillingContactAndAddress;
           this.alternateBilling = alternateBilling;
           this.role = role;
           this.collectTermsType = collectTermsType;
           this.declaredValuePerUnit = declaredValuePerUnit;
           this.declaredValueUnits = declaredValueUnits;
           this.liabilityCoverageDetail = liabilityCoverageDetail;
           this.coupons = coupons;
           this.totalHandlingUnits = totalHandlingUnits;
           this.clientDiscountPercent = clientDiscountPercent;
           this.palletWeight = palletWeight;
           this.shipmentDimensions = shipmentDimensions;
           this.comment = comment;
           this.specialServicePayments = specialServicePayments;
           this.hazardousMaterialsOfferor = hazardousMaterialsOfferor;
           this.lineItems = lineItems;
    }


    /**
     * Gets the fedExFreightAccountNumber value for this FreightShipmentDetail.
     * 
     * @return fedExFreightAccountNumber   * Account number used with FEDEX_FREIGHT service.
     */
    public java.lang.String getFedExFreightAccountNumber() {
        return fedExFreightAccountNumber;
    }


    /**
     * Sets the fedExFreightAccountNumber value for this FreightShipmentDetail.
     * 
     * @param fedExFreightAccountNumber   * Account number used with FEDEX_FREIGHT service.
     */
    public void setFedExFreightAccountNumber(java.lang.String fedExFreightAccountNumber) {
        this.fedExFreightAccountNumber = fedExFreightAccountNumber;
    }


    /**
     * Gets the fedExFreightBillingContactAndAddress value for this FreightShipmentDetail.
     * 
     * @return fedExFreightBillingContactAndAddress   * Used for validating FedEx Freight account number and (optionally)
     * identifying third party payment on the bill of lading.
     */
    public com.fedex.ws.rate.v22.ContactAndAddress getFedExFreightBillingContactAndAddress() {
        return fedExFreightBillingContactAndAddress;
    }


    /**
     * Sets the fedExFreightBillingContactAndAddress value for this FreightShipmentDetail.
     * 
     * @param fedExFreightBillingContactAndAddress   * Used for validating FedEx Freight account number and (optionally)
     * identifying third party payment on the bill of lading.
     */
    public void setFedExFreightBillingContactAndAddress(com.fedex.ws.rate.v22.ContactAndAddress fedExFreightBillingContactAndAddress) {
        this.fedExFreightBillingContactAndAddress = fedExFreightBillingContactAndAddress;
    }


    /**
     * Gets the alternateBilling value for this FreightShipmentDetail.
     * 
     * @return alternateBilling   * Used in connection with "Send Bill To" (SBT) identification
     * of customer's account used for billing.
     */
    public com.fedex.ws.rate.v22.Party getAlternateBilling() {
        return alternateBilling;
    }


    /**
     * Sets the alternateBilling value for this FreightShipmentDetail.
     * 
     * @param alternateBilling   * Used in connection with "Send Bill To" (SBT) identification
     * of customer's account used for billing.
     */
    public void setAlternateBilling(com.fedex.ws.rate.v22.Party alternateBilling) {
        this.alternateBilling = alternateBilling;
    }


    /**
     * Gets the role value for this FreightShipmentDetail.
     * 
     * @return role   * Indicates the role of the party submitting the transaction.
     */
    public com.fedex.ws.rate.v22.FreightShipmentRoleType getRole() {
        return role;
    }


    /**
     * Sets the role value for this FreightShipmentDetail.
     * 
     * @param role   * Indicates the role of the party submitting the transaction.
     */
    public void setRole(com.fedex.ws.rate.v22.FreightShipmentRoleType role) {
        this.role = role;
    }


    /**
     * Gets the collectTermsType value for this FreightShipmentDetail.
     * 
     * @return collectTermsType   * Designates the terms of the "collect" payment for a Freight
     * Shipment.
     */
    public com.fedex.ws.rate.v22.FreightCollectTermsType getCollectTermsType() {
        return collectTermsType;
    }


    /**
     * Sets the collectTermsType value for this FreightShipmentDetail.
     * 
     * @param collectTermsType   * Designates the terms of the "collect" payment for a Freight
     * Shipment.
     */
    public void setCollectTermsType(com.fedex.ws.rate.v22.FreightCollectTermsType collectTermsType) {
        this.collectTermsType = collectTermsType;
    }


    /**
     * Gets the declaredValuePerUnit value for this FreightShipmentDetail.
     * 
     * @return declaredValuePerUnit   * Identifies the declared value for the shipment
     */
    public com.fedex.ws.rate.v22.Money getDeclaredValuePerUnit() {
        return declaredValuePerUnit;
    }


    /**
     * Sets the declaredValuePerUnit value for this FreightShipmentDetail.
     * 
     * @param declaredValuePerUnit   * Identifies the declared value for the shipment
     */
    public void setDeclaredValuePerUnit(com.fedex.ws.rate.v22.Money declaredValuePerUnit) {
        this.declaredValuePerUnit = declaredValuePerUnit;
    }


    /**
     * Gets the declaredValueUnits value for this FreightShipmentDetail.
     * 
     * @return declaredValueUnits   * Identifies the declared value units corresponding to the above
     * defined declared value
     */
    public java.lang.String getDeclaredValueUnits() {
        return declaredValueUnits;
    }


    /**
     * Sets the declaredValueUnits value for this FreightShipmentDetail.
     * 
     * @param declaredValueUnits   * Identifies the declared value units corresponding to the above
     * defined declared value
     */
    public void setDeclaredValueUnits(java.lang.String declaredValueUnits) {
        this.declaredValueUnits = declaredValueUnits;
    }


    /**
     * Gets the liabilityCoverageDetail value for this FreightShipmentDetail.
     * 
     * @return liabilityCoverageDetail
     */
    public com.fedex.ws.rate.v22.LiabilityCoverageDetail getLiabilityCoverageDetail() {
        return liabilityCoverageDetail;
    }


    /**
     * Sets the liabilityCoverageDetail value for this FreightShipmentDetail.
     * 
     * @param liabilityCoverageDetail
     */
    public void setLiabilityCoverageDetail(com.fedex.ws.rate.v22.LiabilityCoverageDetail liabilityCoverageDetail) {
        this.liabilityCoverageDetail = liabilityCoverageDetail;
    }


    /**
     * Gets the coupons value for this FreightShipmentDetail.
     * 
     * @return coupons   * Identifiers for promotional discounts offered to customers.
     */
    public java.lang.String[] getCoupons() {
        return coupons;
    }


    /**
     * Sets the coupons value for this FreightShipmentDetail.
     * 
     * @param coupons   * Identifiers for promotional discounts offered to customers.
     */
    public void setCoupons(java.lang.String[] coupons) {
        this.coupons = coupons;
    }

    public java.lang.String getCoupons(int i) {
        return this.coupons[i];
    }

    public void setCoupons(int i, java.lang.String _value) {
        this.coupons[i] = _value;
    }


    /**
     * Gets the totalHandlingUnits value for this FreightShipmentDetail.
     * 
     * @return totalHandlingUnits   * Total number of individual handling units in the entire shipment
     * (for unit pricing).
     */
    public org.apache.axis.types.NonNegativeInteger getTotalHandlingUnits() {
        return totalHandlingUnits;
    }


    /**
     * Sets the totalHandlingUnits value for this FreightShipmentDetail.
     * 
     * @param totalHandlingUnits   * Total number of individual handling units in the entire shipment
     * (for unit pricing).
     */
    public void setTotalHandlingUnits(org.apache.axis.types.NonNegativeInteger totalHandlingUnits) {
        this.totalHandlingUnits = totalHandlingUnits;
    }


    /**
     * Gets the clientDiscountPercent value for this FreightShipmentDetail.
     * 
     * @return clientDiscountPercent   * Estimated discount rate provided by client for unsecured rate
     * quote.
     */
    public java.math.BigDecimal getClientDiscountPercent() {
        return clientDiscountPercent;
    }


    /**
     * Sets the clientDiscountPercent value for this FreightShipmentDetail.
     * 
     * @param clientDiscountPercent   * Estimated discount rate provided by client for unsecured rate
     * quote.
     */
    public void setClientDiscountPercent(java.math.BigDecimal clientDiscountPercent) {
        this.clientDiscountPercent = clientDiscountPercent;
    }


    /**
     * Gets the palletWeight value for this FreightShipmentDetail.
     * 
     * @return palletWeight   * Total weight of pallets used in shipment.
     */
    public com.fedex.ws.rate.v22.Weight getPalletWeight() {
        return palletWeight;
    }


    /**
     * Sets the palletWeight value for this FreightShipmentDetail.
     * 
     * @param palletWeight   * Total weight of pallets used in shipment.
     */
    public void setPalletWeight(com.fedex.ws.rate.v22.Weight palletWeight) {
        this.palletWeight = palletWeight;
    }


    /**
     * Gets the shipmentDimensions value for this FreightShipmentDetail.
     * 
     * @return shipmentDimensions   * Overall shipment dimensions.
     */
    public com.fedex.ws.rate.v22.Dimensions getShipmentDimensions() {
        return shipmentDimensions;
    }


    /**
     * Sets the shipmentDimensions value for this FreightShipmentDetail.
     * 
     * @param shipmentDimensions   * Overall shipment dimensions.
     */
    public void setShipmentDimensions(com.fedex.ws.rate.v22.Dimensions shipmentDimensions) {
        this.shipmentDimensions = shipmentDimensions;
    }


    /**
     * Gets the comment value for this FreightShipmentDetail.
     * 
     * @return comment   * Description for the shipment.
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this FreightShipmentDetail.
     * 
     * @param comment   * Description for the shipment.
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the specialServicePayments value for this FreightShipmentDetail.
     * 
     * @return specialServicePayments   * Specifies which party will pay surcharges for any special services
     * which support split billing.
     */
    public com.fedex.ws.rate.v22.FreightSpecialServicePayment[] getSpecialServicePayments() {
        return specialServicePayments;
    }


    /**
     * Sets the specialServicePayments value for this FreightShipmentDetail.
     * 
     * @param specialServicePayments   * Specifies which party will pay surcharges for any special services
     * which support split billing.
     */
    public void setSpecialServicePayments(com.fedex.ws.rate.v22.FreightSpecialServicePayment[] specialServicePayments) {
        this.specialServicePayments = specialServicePayments;
    }

    public com.fedex.ws.rate.v22.FreightSpecialServicePayment getSpecialServicePayments(int i) {
        return this.specialServicePayments[i];
    }

    public void setSpecialServicePayments(int i, com.fedex.ws.rate.v22.FreightSpecialServicePayment _value) {
        this.specialServicePayments[i] = _value;
    }


    /**
     * Gets the hazardousMaterialsOfferor value for this FreightShipmentDetail.
     * 
     * @return hazardousMaterialsOfferor
     */
    public java.lang.String getHazardousMaterialsOfferor() {
        return hazardousMaterialsOfferor;
    }


    /**
     * Sets the hazardousMaterialsOfferor value for this FreightShipmentDetail.
     * 
     * @param hazardousMaterialsOfferor
     */
    public void setHazardousMaterialsOfferor(java.lang.String hazardousMaterialsOfferor) {
        this.hazardousMaterialsOfferor = hazardousMaterialsOfferor;
    }


    /**
     * Gets the lineItems value for this FreightShipmentDetail.
     * 
     * @return lineItems   * Details of the commodities in the shipment.
     */
    public com.fedex.ws.rate.v22.FreightShipmentLineItem[] getLineItems() {
        return lineItems;
    }


    /**
     * Sets the lineItems value for this FreightShipmentDetail.
     * 
     * @param lineItems   * Details of the commodities in the shipment.
     */
    public void setLineItems(com.fedex.ws.rate.v22.FreightShipmentLineItem[] lineItems) {
        this.lineItems = lineItems;
    }

    public com.fedex.ws.rate.v22.FreightShipmentLineItem getLineItems(int i) {
        return this.lineItems[i];
    }

    public void setLineItems(int i, com.fedex.ws.rate.v22.FreightShipmentLineItem _value) {
        this.lineItems[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightShipmentDetail)) return false;
        FreightShipmentDetail other = (FreightShipmentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fedExFreightAccountNumber==null && other.getFedExFreightAccountNumber()==null) || 
             (this.fedExFreightAccountNumber!=null &&
              this.fedExFreightAccountNumber.equals(other.getFedExFreightAccountNumber()))) &&
            ((this.fedExFreightBillingContactAndAddress==null && other.getFedExFreightBillingContactAndAddress()==null) || 
             (this.fedExFreightBillingContactAndAddress!=null &&
              this.fedExFreightBillingContactAndAddress.equals(other.getFedExFreightBillingContactAndAddress()))) &&
            ((this.alternateBilling==null && other.getAlternateBilling()==null) || 
             (this.alternateBilling!=null &&
              this.alternateBilling.equals(other.getAlternateBilling()))) &&
            ((this.role==null && other.getRole()==null) || 
             (this.role!=null &&
              this.role.equals(other.getRole()))) &&
            ((this.collectTermsType==null && other.getCollectTermsType()==null) || 
             (this.collectTermsType!=null &&
              this.collectTermsType.equals(other.getCollectTermsType()))) &&
            ((this.declaredValuePerUnit==null && other.getDeclaredValuePerUnit()==null) || 
             (this.declaredValuePerUnit!=null &&
              this.declaredValuePerUnit.equals(other.getDeclaredValuePerUnit()))) &&
            ((this.declaredValueUnits==null && other.getDeclaredValueUnits()==null) || 
             (this.declaredValueUnits!=null &&
              this.declaredValueUnits.equals(other.getDeclaredValueUnits()))) &&
            ((this.liabilityCoverageDetail==null && other.getLiabilityCoverageDetail()==null) || 
             (this.liabilityCoverageDetail!=null &&
              this.liabilityCoverageDetail.equals(other.getLiabilityCoverageDetail()))) &&
            ((this.coupons==null && other.getCoupons()==null) || 
             (this.coupons!=null &&
              java.util.Arrays.equals(this.coupons, other.getCoupons()))) &&
            ((this.totalHandlingUnits==null && other.getTotalHandlingUnits()==null) || 
             (this.totalHandlingUnits!=null &&
              this.totalHandlingUnits.equals(other.getTotalHandlingUnits()))) &&
            ((this.clientDiscountPercent==null && other.getClientDiscountPercent()==null) || 
             (this.clientDiscountPercent!=null &&
              this.clientDiscountPercent.equals(other.getClientDiscountPercent()))) &&
            ((this.palletWeight==null && other.getPalletWeight()==null) || 
             (this.palletWeight!=null &&
              this.palletWeight.equals(other.getPalletWeight()))) &&
            ((this.shipmentDimensions==null && other.getShipmentDimensions()==null) || 
             (this.shipmentDimensions!=null &&
              this.shipmentDimensions.equals(other.getShipmentDimensions()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.specialServicePayments==null && other.getSpecialServicePayments()==null) || 
             (this.specialServicePayments!=null &&
              java.util.Arrays.equals(this.specialServicePayments, other.getSpecialServicePayments()))) &&
            ((this.hazardousMaterialsOfferor==null && other.getHazardousMaterialsOfferor()==null) || 
             (this.hazardousMaterialsOfferor!=null &&
              this.hazardousMaterialsOfferor.equals(other.getHazardousMaterialsOfferor()))) &&
            ((this.lineItems==null && other.getLineItems()==null) || 
             (this.lineItems!=null &&
              java.util.Arrays.equals(this.lineItems, other.getLineItems())));
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
        if (getFedExFreightAccountNumber() != null) {
            _hashCode += getFedExFreightAccountNumber().hashCode();
        }
        if (getFedExFreightBillingContactAndAddress() != null) {
            _hashCode += getFedExFreightBillingContactAndAddress().hashCode();
        }
        if (getAlternateBilling() != null) {
            _hashCode += getAlternateBilling().hashCode();
        }
        if (getRole() != null) {
            _hashCode += getRole().hashCode();
        }
        if (getCollectTermsType() != null) {
            _hashCode += getCollectTermsType().hashCode();
        }
        if (getDeclaredValuePerUnit() != null) {
            _hashCode += getDeclaredValuePerUnit().hashCode();
        }
        if (getDeclaredValueUnits() != null) {
            _hashCode += getDeclaredValueUnits().hashCode();
        }
        if (getLiabilityCoverageDetail() != null) {
            _hashCode += getLiabilityCoverageDetail().hashCode();
        }
        if (getCoupons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCoupons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCoupons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTotalHandlingUnits() != null) {
            _hashCode += getTotalHandlingUnits().hashCode();
        }
        if (getClientDiscountPercent() != null) {
            _hashCode += getClientDiscountPercent().hashCode();
        }
        if (getPalletWeight() != null) {
            _hashCode += getPalletWeight().hashCode();
        }
        if (getShipmentDimensions() != null) {
            _hashCode += getShipmentDimensions().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getSpecialServicePayments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSpecialServicePayments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSpecialServicePayments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getHazardousMaterialsOfferor() != null) {
            _hashCode += getHazardousMaterialsOfferor().hashCode();
        }
        if (getLineItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLineItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLineItems(), i);
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
        new org.apache.axis.description.TypeDesc(FreightShipmentDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightShipmentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fedExFreightAccountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FedExFreightAccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fedExFreightBillingContactAndAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FedExFreightBillingContactAndAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ContactAndAddress"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alternateBilling");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AlternateBilling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Party"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("role");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Role"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightShipmentRoleType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectTermsType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CollectTermsType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightCollectTermsType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declaredValuePerUnit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeclaredValuePerUnit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declaredValueUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeclaredValueUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityCoverageDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LiabilityCoverageDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LiabilityCoverageDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coupons");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Coupons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalHandlingUnits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalHandlingUnits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("clientDiscountPercent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ClientDiscountPercent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("palletWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PalletWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shipmentDimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentDimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialServicePayments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SpecialServicePayments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightSpecialServicePayment"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousMaterialsOfferor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousMaterialsOfferor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineItems");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LineItems"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightShipmentLineItem"));
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
