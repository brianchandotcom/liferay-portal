/**
 * PackageRateDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Data for a package's rates, as calculated per a specific rate type.
 */
public class PackageRateDetail  implements java.io.Serializable {
    /* Type used for this specific set of rate data. */
    private com.fedex.ws.rate.v22.ReturnedRateType rateType;

    /* Indicates which weight was used. */
    private com.fedex.ws.rate.v22.RatedWeightMethod ratedWeightMethod;

    /* INTERNAL FEDEX USE ONLY. */
    private com.fedex.ws.rate.v22.MinimumChargeType minimumChargeType;

    private com.fedex.ws.rate.v22.Weight billingWeight;

    /* The dimensional weight of this package (if greater than actual). */
    private com.fedex.ws.rate.v22.Weight dimWeight;

    /* The oversize weight of this package (if the package is oversize). */
    private com.fedex.ws.rate.v22.Weight oversizeWeight;

    /* The transportation charge only (prior to any discounts applied)
     * for this package. */
    private com.fedex.ws.rate.v22.Money baseCharge;

    /* The sum of all discounts on this package. */
    private com.fedex.ws.rate.v22.Money totalFreightDiscounts;

    /* This package's baseCharge - totalFreightDiscounts. */
    private com.fedex.ws.rate.v22.Money netFreight;

    /* The sum of all surcharges on this package. */
    private com.fedex.ws.rate.v22.Money totalSurcharges;

    /* This package's netFreight + totalSurcharges (not including
     * totalTaxes). */
    private com.fedex.ws.rate.v22.Money netFedExCharge;

    /* The sum of all taxes on this package. */
    private com.fedex.ws.rate.v22.Money totalTaxes;

    /* This package's netFreight + totalSurcharges + totalTaxes. */
    private com.fedex.ws.rate.v22.Money netCharge;

    private com.fedex.ws.rate.v22.Money totalRebates;

    /* All rate discounts that apply to this package. */
    private com.fedex.ws.rate.v22.RateDiscount[] freightDiscounts;

    /* All rebates that apply to this package. */
    private com.fedex.ws.rate.v22.Rebate[] rebates;

    /* All surcharges that apply to this package (either because of
     * characteristics of the package itself, or because it is carrying per-shipment
     * surcharges for the shipment of which it is a part). */
    private com.fedex.ws.rate.v22.Surcharge[] surcharges;

    /* All taxes applicable (or distributed to) this package. */
    private com.fedex.ws.rate.v22.Tax[] taxes;

    private com.fedex.ws.rate.v22.VariableHandlingCharges variableHandlingCharges;

    public PackageRateDetail() {
    }

    public PackageRateDetail(
           com.fedex.ws.rate.v22.ReturnedRateType rateType,
           com.fedex.ws.rate.v22.RatedWeightMethod ratedWeightMethod,
           com.fedex.ws.rate.v22.MinimumChargeType minimumChargeType,
           com.fedex.ws.rate.v22.Weight billingWeight,
           com.fedex.ws.rate.v22.Weight dimWeight,
           com.fedex.ws.rate.v22.Weight oversizeWeight,
           com.fedex.ws.rate.v22.Money baseCharge,
           com.fedex.ws.rate.v22.Money totalFreightDiscounts,
           com.fedex.ws.rate.v22.Money netFreight,
           com.fedex.ws.rate.v22.Money totalSurcharges,
           com.fedex.ws.rate.v22.Money netFedExCharge,
           com.fedex.ws.rate.v22.Money totalTaxes,
           com.fedex.ws.rate.v22.Money netCharge,
           com.fedex.ws.rate.v22.Money totalRebates,
           com.fedex.ws.rate.v22.RateDiscount[] freightDiscounts,
           com.fedex.ws.rate.v22.Rebate[] rebates,
           com.fedex.ws.rate.v22.Surcharge[] surcharges,
           com.fedex.ws.rate.v22.Tax[] taxes,
           com.fedex.ws.rate.v22.VariableHandlingCharges variableHandlingCharges) {
           this.rateType = rateType;
           this.ratedWeightMethod = ratedWeightMethod;
           this.minimumChargeType = minimumChargeType;
           this.billingWeight = billingWeight;
           this.dimWeight = dimWeight;
           this.oversizeWeight = oversizeWeight;
           this.baseCharge = baseCharge;
           this.totalFreightDiscounts = totalFreightDiscounts;
           this.netFreight = netFreight;
           this.totalSurcharges = totalSurcharges;
           this.netFedExCharge = netFedExCharge;
           this.totalTaxes = totalTaxes;
           this.netCharge = netCharge;
           this.totalRebates = totalRebates;
           this.freightDiscounts = freightDiscounts;
           this.rebates = rebates;
           this.surcharges = surcharges;
           this.taxes = taxes;
           this.variableHandlingCharges = variableHandlingCharges;
    }


    /**
     * Gets the rateType value for this PackageRateDetail.
     * 
     * @return rateType   * Type used for this specific set of rate data.
     */
    public com.fedex.ws.rate.v22.ReturnedRateType getRateType() {
        return rateType;
    }


    /**
     * Sets the rateType value for this PackageRateDetail.
     * 
     * @param rateType   * Type used for this specific set of rate data.
     */
    public void setRateType(com.fedex.ws.rate.v22.ReturnedRateType rateType) {
        this.rateType = rateType;
    }


    /**
     * Gets the ratedWeightMethod value for this PackageRateDetail.
     * 
     * @return ratedWeightMethod   * Indicates which weight was used.
     */
    public com.fedex.ws.rate.v22.RatedWeightMethod getRatedWeightMethod() {
        return ratedWeightMethod;
    }


    /**
     * Sets the ratedWeightMethod value for this PackageRateDetail.
     * 
     * @param ratedWeightMethod   * Indicates which weight was used.
     */
    public void setRatedWeightMethod(com.fedex.ws.rate.v22.RatedWeightMethod ratedWeightMethod) {
        this.ratedWeightMethod = ratedWeightMethod;
    }


    /**
     * Gets the minimumChargeType value for this PackageRateDetail.
     * 
     * @return minimumChargeType   * INTERNAL FEDEX USE ONLY.
     */
    public com.fedex.ws.rate.v22.MinimumChargeType getMinimumChargeType() {
        return minimumChargeType;
    }


    /**
     * Sets the minimumChargeType value for this PackageRateDetail.
     * 
     * @param minimumChargeType   * INTERNAL FEDEX USE ONLY.
     */
    public void setMinimumChargeType(com.fedex.ws.rate.v22.MinimumChargeType minimumChargeType) {
        this.minimumChargeType = minimumChargeType;
    }


    /**
     * Gets the billingWeight value for this PackageRateDetail.
     * 
     * @return billingWeight
     */
    public com.fedex.ws.rate.v22.Weight getBillingWeight() {
        return billingWeight;
    }


    /**
     * Sets the billingWeight value for this PackageRateDetail.
     * 
     * @param billingWeight
     */
    public void setBillingWeight(com.fedex.ws.rate.v22.Weight billingWeight) {
        this.billingWeight = billingWeight;
    }


    /**
     * Gets the dimWeight value for this PackageRateDetail.
     * 
     * @return dimWeight   * The dimensional weight of this package (if greater than actual).
     */
    public com.fedex.ws.rate.v22.Weight getDimWeight() {
        return dimWeight;
    }


    /**
     * Sets the dimWeight value for this PackageRateDetail.
     * 
     * @param dimWeight   * The dimensional weight of this package (if greater than actual).
     */
    public void setDimWeight(com.fedex.ws.rate.v22.Weight dimWeight) {
        this.dimWeight = dimWeight;
    }


    /**
     * Gets the oversizeWeight value for this PackageRateDetail.
     * 
     * @return oversizeWeight   * The oversize weight of this package (if the package is oversize).
     */
    public com.fedex.ws.rate.v22.Weight getOversizeWeight() {
        return oversizeWeight;
    }


    /**
     * Sets the oversizeWeight value for this PackageRateDetail.
     * 
     * @param oversizeWeight   * The oversize weight of this package (if the package is oversize).
     */
    public void setOversizeWeight(com.fedex.ws.rate.v22.Weight oversizeWeight) {
        this.oversizeWeight = oversizeWeight;
    }


    /**
     * Gets the baseCharge value for this PackageRateDetail.
     * 
     * @return baseCharge   * The transportation charge only (prior to any discounts applied)
     * for this package.
     */
    public com.fedex.ws.rate.v22.Money getBaseCharge() {
        return baseCharge;
    }


    /**
     * Sets the baseCharge value for this PackageRateDetail.
     * 
     * @param baseCharge   * The transportation charge only (prior to any discounts applied)
     * for this package.
     */
    public void setBaseCharge(com.fedex.ws.rate.v22.Money baseCharge) {
        this.baseCharge = baseCharge;
    }


    /**
     * Gets the totalFreightDiscounts value for this PackageRateDetail.
     * 
     * @return totalFreightDiscounts   * The sum of all discounts on this package.
     */
    public com.fedex.ws.rate.v22.Money getTotalFreightDiscounts() {
        return totalFreightDiscounts;
    }


    /**
     * Sets the totalFreightDiscounts value for this PackageRateDetail.
     * 
     * @param totalFreightDiscounts   * The sum of all discounts on this package.
     */
    public void setTotalFreightDiscounts(com.fedex.ws.rate.v22.Money totalFreightDiscounts) {
        this.totalFreightDiscounts = totalFreightDiscounts;
    }


    /**
     * Gets the netFreight value for this PackageRateDetail.
     * 
     * @return netFreight   * This package's baseCharge - totalFreightDiscounts.
     */
    public com.fedex.ws.rate.v22.Money getNetFreight() {
        return netFreight;
    }


    /**
     * Sets the netFreight value for this PackageRateDetail.
     * 
     * @param netFreight   * This package's baseCharge - totalFreightDiscounts.
     */
    public void setNetFreight(com.fedex.ws.rate.v22.Money netFreight) {
        this.netFreight = netFreight;
    }


    /**
     * Gets the totalSurcharges value for this PackageRateDetail.
     * 
     * @return totalSurcharges   * The sum of all surcharges on this package.
     */
    public com.fedex.ws.rate.v22.Money getTotalSurcharges() {
        return totalSurcharges;
    }


    /**
     * Sets the totalSurcharges value for this PackageRateDetail.
     * 
     * @param totalSurcharges   * The sum of all surcharges on this package.
     */
    public void setTotalSurcharges(com.fedex.ws.rate.v22.Money totalSurcharges) {
        this.totalSurcharges = totalSurcharges;
    }


    /**
     * Gets the netFedExCharge value for this PackageRateDetail.
     * 
     * @return netFedExCharge   * This package's netFreight + totalSurcharges (not including
     * totalTaxes).
     */
    public com.fedex.ws.rate.v22.Money getNetFedExCharge() {
        return netFedExCharge;
    }


    /**
     * Sets the netFedExCharge value for this PackageRateDetail.
     * 
     * @param netFedExCharge   * This package's netFreight + totalSurcharges (not including
     * totalTaxes).
     */
    public void setNetFedExCharge(com.fedex.ws.rate.v22.Money netFedExCharge) {
        this.netFedExCharge = netFedExCharge;
    }


    /**
     * Gets the totalTaxes value for this PackageRateDetail.
     * 
     * @return totalTaxes   * The sum of all taxes on this package.
     */
    public com.fedex.ws.rate.v22.Money getTotalTaxes() {
        return totalTaxes;
    }


    /**
     * Sets the totalTaxes value for this PackageRateDetail.
     * 
     * @param totalTaxes   * The sum of all taxes on this package.
     */
    public void setTotalTaxes(com.fedex.ws.rate.v22.Money totalTaxes) {
        this.totalTaxes = totalTaxes;
    }


    /**
     * Gets the netCharge value for this PackageRateDetail.
     * 
     * @return netCharge   * This package's netFreight + totalSurcharges + totalTaxes.
     */
    public com.fedex.ws.rate.v22.Money getNetCharge() {
        return netCharge;
    }


    /**
     * Sets the netCharge value for this PackageRateDetail.
     * 
     * @param netCharge   * This package's netFreight + totalSurcharges + totalTaxes.
     */
    public void setNetCharge(com.fedex.ws.rate.v22.Money netCharge) {
        this.netCharge = netCharge;
    }


    /**
     * Gets the totalRebates value for this PackageRateDetail.
     * 
     * @return totalRebates
     */
    public com.fedex.ws.rate.v22.Money getTotalRebates() {
        return totalRebates;
    }


    /**
     * Sets the totalRebates value for this PackageRateDetail.
     * 
     * @param totalRebates
     */
    public void setTotalRebates(com.fedex.ws.rate.v22.Money totalRebates) {
        this.totalRebates = totalRebates;
    }


    /**
     * Gets the freightDiscounts value for this PackageRateDetail.
     * 
     * @return freightDiscounts   * All rate discounts that apply to this package.
     */
    public com.fedex.ws.rate.v22.RateDiscount[] getFreightDiscounts() {
        return freightDiscounts;
    }


    /**
     * Sets the freightDiscounts value for this PackageRateDetail.
     * 
     * @param freightDiscounts   * All rate discounts that apply to this package.
     */
    public void setFreightDiscounts(com.fedex.ws.rate.v22.RateDiscount[] freightDiscounts) {
        this.freightDiscounts = freightDiscounts;
    }

    public com.fedex.ws.rate.v22.RateDiscount getFreightDiscounts(int i) {
        return this.freightDiscounts[i];
    }

    public void setFreightDiscounts(int i, com.fedex.ws.rate.v22.RateDiscount _value) {
        this.freightDiscounts[i] = _value;
    }


    /**
     * Gets the rebates value for this PackageRateDetail.
     * 
     * @return rebates   * All rebates that apply to this package.
     */
    public com.fedex.ws.rate.v22.Rebate[] getRebates() {
        return rebates;
    }


    /**
     * Sets the rebates value for this PackageRateDetail.
     * 
     * @param rebates   * All rebates that apply to this package.
     */
    public void setRebates(com.fedex.ws.rate.v22.Rebate[] rebates) {
        this.rebates = rebates;
    }

    public com.fedex.ws.rate.v22.Rebate getRebates(int i) {
        return this.rebates[i];
    }

    public void setRebates(int i, com.fedex.ws.rate.v22.Rebate _value) {
        this.rebates[i] = _value;
    }


    /**
     * Gets the surcharges value for this PackageRateDetail.
     * 
     * @return surcharges   * All surcharges that apply to this package (either because of
     * characteristics of the package itself, or because it is carrying per-shipment
     * surcharges for the shipment of which it is a part).
     */
    public com.fedex.ws.rate.v22.Surcharge[] getSurcharges() {
        return surcharges;
    }


    /**
     * Sets the surcharges value for this PackageRateDetail.
     * 
     * @param surcharges   * All surcharges that apply to this package (either because of
     * characteristics of the package itself, or because it is carrying per-shipment
     * surcharges for the shipment of which it is a part).
     */
    public void setSurcharges(com.fedex.ws.rate.v22.Surcharge[] surcharges) {
        this.surcharges = surcharges;
    }

    public com.fedex.ws.rate.v22.Surcharge getSurcharges(int i) {
        return this.surcharges[i];
    }

    public void setSurcharges(int i, com.fedex.ws.rate.v22.Surcharge _value) {
        this.surcharges[i] = _value;
    }


    /**
     * Gets the taxes value for this PackageRateDetail.
     * 
     * @return taxes   * All taxes applicable (or distributed to) this package.
     */
    public com.fedex.ws.rate.v22.Tax[] getTaxes() {
        return taxes;
    }


    /**
     * Sets the taxes value for this PackageRateDetail.
     * 
     * @param taxes   * All taxes applicable (or distributed to) this package.
     */
    public void setTaxes(com.fedex.ws.rate.v22.Tax[] taxes) {
        this.taxes = taxes;
    }

    public com.fedex.ws.rate.v22.Tax getTaxes(int i) {
        return this.taxes[i];
    }

    public void setTaxes(int i, com.fedex.ws.rate.v22.Tax _value) {
        this.taxes[i] = _value;
    }


    /**
     * Gets the variableHandlingCharges value for this PackageRateDetail.
     * 
     * @return variableHandlingCharges
     */
    public com.fedex.ws.rate.v22.VariableHandlingCharges getVariableHandlingCharges() {
        return variableHandlingCharges;
    }


    /**
     * Sets the variableHandlingCharges value for this PackageRateDetail.
     * 
     * @param variableHandlingCharges
     */
    public void setVariableHandlingCharges(com.fedex.ws.rate.v22.VariableHandlingCharges variableHandlingCharges) {
        this.variableHandlingCharges = variableHandlingCharges;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PackageRateDetail)) return false;
        PackageRateDetail other = (PackageRateDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.rateType==null && other.getRateType()==null) || 
             (this.rateType!=null &&
              this.rateType.equals(other.getRateType()))) &&
            ((this.ratedWeightMethod==null && other.getRatedWeightMethod()==null) || 
             (this.ratedWeightMethod!=null &&
              this.ratedWeightMethod.equals(other.getRatedWeightMethod()))) &&
            ((this.minimumChargeType==null && other.getMinimumChargeType()==null) || 
             (this.minimumChargeType!=null &&
              this.minimumChargeType.equals(other.getMinimumChargeType()))) &&
            ((this.billingWeight==null && other.getBillingWeight()==null) || 
             (this.billingWeight!=null &&
              this.billingWeight.equals(other.getBillingWeight()))) &&
            ((this.dimWeight==null && other.getDimWeight()==null) || 
             (this.dimWeight!=null &&
              this.dimWeight.equals(other.getDimWeight()))) &&
            ((this.oversizeWeight==null && other.getOversizeWeight()==null) || 
             (this.oversizeWeight!=null &&
              this.oversizeWeight.equals(other.getOversizeWeight()))) &&
            ((this.baseCharge==null && other.getBaseCharge()==null) || 
             (this.baseCharge!=null &&
              this.baseCharge.equals(other.getBaseCharge()))) &&
            ((this.totalFreightDiscounts==null && other.getTotalFreightDiscounts()==null) || 
             (this.totalFreightDiscounts!=null &&
              this.totalFreightDiscounts.equals(other.getTotalFreightDiscounts()))) &&
            ((this.netFreight==null && other.getNetFreight()==null) || 
             (this.netFreight!=null &&
              this.netFreight.equals(other.getNetFreight()))) &&
            ((this.totalSurcharges==null && other.getTotalSurcharges()==null) || 
             (this.totalSurcharges!=null &&
              this.totalSurcharges.equals(other.getTotalSurcharges()))) &&
            ((this.netFedExCharge==null && other.getNetFedExCharge()==null) || 
             (this.netFedExCharge!=null &&
              this.netFedExCharge.equals(other.getNetFedExCharge()))) &&
            ((this.totalTaxes==null && other.getTotalTaxes()==null) || 
             (this.totalTaxes!=null &&
              this.totalTaxes.equals(other.getTotalTaxes()))) &&
            ((this.netCharge==null && other.getNetCharge()==null) || 
             (this.netCharge!=null &&
              this.netCharge.equals(other.getNetCharge()))) &&
            ((this.totalRebates==null && other.getTotalRebates()==null) || 
             (this.totalRebates!=null &&
              this.totalRebates.equals(other.getTotalRebates()))) &&
            ((this.freightDiscounts==null && other.getFreightDiscounts()==null) || 
             (this.freightDiscounts!=null &&
              java.util.Arrays.equals(this.freightDiscounts, other.getFreightDiscounts()))) &&
            ((this.rebates==null && other.getRebates()==null) || 
             (this.rebates!=null &&
              java.util.Arrays.equals(this.rebates, other.getRebates()))) &&
            ((this.surcharges==null && other.getSurcharges()==null) || 
             (this.surcharges!=null &&
              java.util.Arrays.equals(this.surcharges, other.getSurcharges()))) &&
            ((this.taxes==null && other.getTaxes()==null) || 
             (this.taxes!=null &&
              java.util.Arrays.equals(this.taxes, other.getTaxes()))) &&
            ((this.variableHandlingCharges==null && other.getVariableHandlingCharges()==null) || 
             (this.variableHandlingCharges!=null &&
              this.variableHandlingCharges.equals(other.getVariableHandlingCharges())));
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
        if (getRateType() != null) {
            _hashCode += getRateType().hashCode();
        }
        if (getRatedWeightMethod() != null) {
            _hashCode += getRatedWeightMethod().hashCode();
        }
        if (getMinimumChargeType() != null) {
            _hashCode += getMinimumChargeType().hashCode();
        }
        if (getBillingWeight() != null) {
            _hashCode += getBillingWeight().hashCode();
        }
        if (getDimWeight() != null) {
            _hashCode += getDimWeight().hashCode();
        }
        if (getOversizeWeight() != null) {
            _hashCode += getOversizeWeight().hashCode();
        }
        if (getBaseCharge() != null) {
            _hashCode += getBaseCharge().hashCode();
        }
        if (getTotalFreightDiscounts() != null) {
            _hashCode += getTotalFreightDiscounts().hashCode();
        }
        if (getNetFreight() != null) {
            _hashCode += getNetFreight().hashCode();
        }
        if (getTotalSurcharges() != null) {
            _hashCode += getTotalSurcharges().hashCode();
        }
        if (getNetFedExCharge() != null) {
            _hashCode += getNetFedExCharge().hashCode();
        }
        if (getTotalTaxes() != null) {
            _hashCode += getTotalTaxes().hashCode();
        }
        if (getNetCharge() != null) {
            _hashCode += getNetCharge().hashCode();
        }
        if (getTotalRebates() != null) {
            _hashCode += getTotalRebates().hashCode();
        }
        if (getFreightDiscounts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFreightDiscounts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFreightDiscounts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRebates() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRebates());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRebates(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSurcharges() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSurcharges());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSurcharges(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTaxes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTaxes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTaxes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVariableHandlingCharges() != null) {
            _hashCode += getVariableHandlingCharges().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PackageRateDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackageRateDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ReturnedRateType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratedWeightMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedWeightMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedWeightMethod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minimumChargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "MinimumChargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "MinimumChargeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("billingWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BillingWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DimWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oversizeWeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OversizeWeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("baseCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BaseCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalFreightDiscounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalFreightDiscounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netFreight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NetFreight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalSurcharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalSurcharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netFedExCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NetFedExCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalTaxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalTaxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NetCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRebates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalRebates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightDiscounts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightDiscounts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateDiscount"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rebates");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Rebates"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Rebate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surcharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Surcharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Surcharge"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Taxes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Tax"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("variableHandlingCharges");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingCharges"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "VariableHandlingCharges"));
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
