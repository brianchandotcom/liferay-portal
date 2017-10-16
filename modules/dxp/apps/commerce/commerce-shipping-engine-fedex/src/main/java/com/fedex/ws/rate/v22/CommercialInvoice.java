/**
 * CommercialInvoice.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * CommercialInvoice element is required for electronic upload of
 * CI data. It will serve to create/transmit an Electronic Commercial
 * Invoice through the FedEx Systems. Customers are responsible for printing
 * their own Commercial Invoice.If you would likeFedEx to generate a
 * Commercial Invoice and transmit it to Customs. for clearance purposes,
 * you need to specify that in the ShippingDocumentSpecification element.
 * If you would like a copy of the Commercial Invoice that FedEx generated
 * returned to you in reply it needs to be specified in the ETDDetail/RequestedDocumentCopies
 * element. Commercial Invoice support consists of maximum of 99 commodity
 * line items.
 */
public class CommercialInvoice  implements java.io.Serializable {
    /* Any comments that need to be communicated about this shipment. */
    private java.lang.String[] comments;

    /* Any freight charges that are associated with this shipment. */
    private com.fedex.ws.rate.v22.Money freightCharge;

    /* Any taxes or miscellaneous charges(other than Freight charges
     * or Insurance charges) that are associated with this shipment. */
    private com.fedex.ws.rate.v22.Money taxesOrMiscellaneousCharge;

    /* Specifies which kind of charge is being recorded in the preceding
     * field. */
    private com.fedex.ws.rate.v22.TaxesOrMiscellaneousChargeType taxesOrMiscellaneousChargeType;

    /* Any packing costs that are associated with this shipment. */
    private com.fedex.ws.rate.v22.Money packingCosts;

    /* Any handling costs that are associated with this shipment. */
    private com.fedex.ws.rate.v22.Money handlingCosts;

    /* Free-form text. */
    private java.lang.String specialInstructions;

    /* Free-form text. */
    private java.lang.String declarationStatement;

    /* Free-form text. */
    private java.lang.String paymentTerms;

    /* The reason for the shipment. Note: SOLD is not a valid purpose
     * for a Proforma Invoice. */
    private com.fedex.ws.rate.v22.PurposeOfShipmentType purpose;

    /* Name of the International Expert that completed the Commercial
     * Invoice different from Sender. */
    private java.lang.String originatorName;

    /* Required for dutiable international Express or Ground shipments.
     * This field is not applicable to an international PIB(document) or
     * a non-document which does not require a Commercial Invoice. */
    private java.lang.String termsOfSale;

    public CommercialInvoice() {
    }

    public CommercialInvoice(
           java.lang.String[] comments,
           com.fedex.ws.rate.v22.Money freightCharge,
           com.fedex.ws.rate.v22.Money taxesOrMiscellaneousCharge,
           com.fedex.ws.rate.v22.TaxesOrMiscellaneousChargeType taxesOrMiscellaneousChargeType,
           com.fedex.ws.rate.v22.Money packingCosts,
           com.fedex.ws.rate.v22.Money handlingCosts,
           java.lang.String specialInstructions,
           java.lang.String declarationStatement,
           java.lang.String paymentTerms,
           com.fedex.ws.rate.v22.PurposeOfShipmentType purpose,
           java.lang.String originatorName,
           java.lang.String termsOfSale) {
           this.comments = comments;
           this.freightCharge = freightCharge;
           this.taxesOrMiscellaneousCharge = taxesOrMiscellaneousCharge;
           this.taxesOrMiscellaneousChargeType = taxesOrMiscellaneousChargeType;
           this.packingCosts = packingCosts;
           this.handlingCosts = handlingCosts;
           this.specialInstructions = specialInstructions;
           this.declarationStatement = declarationStatement;
           this.paymentTerms = paymentTerms;
           this.purpose = purpose;
           this.originatorName = originatorName;
           this.termsOfSale = termsOfSale;
    }


    /**
     * Gets the comments value for this CommercialInvoice.
     * 
     * @return comments   * Any comments that need to be communicated about this shipment.
     */
    public java.lang.String[] getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this CommercialInvoice.
     * 
     * @param comments   * Any comments that need to be communicated about this shipment.
     */
    public void setComments(java.lang.String[] comments) {
        this.comments = comments;
    }

    public java.lang.String getComments(int i) {
        return this.comments[i];
    }

    public void setComments(int i, java.lang.String _value) {
        this.comments[i] = _value;
    }


    /**
     * Gets the freightCharge value for this CommercialInvoice.
     * 
     * @return freightCharge   * Any freight charges that are associated with this shipment.
     */
    public com.fedex.ws.rate.v22.Money getFreightCharge() {
        return freightCharge;
    }


    /**
     * Sets the freightCharge value for this CommercialInvoice.
     * 
     * @param freightCharge   * Any freight charges that are associated with this shipment.
     */
    public void setFreightCharge(com.fedex.ws.rate.v22.Money freightCharge) {
        this.freightCharge = freightCharge;
    }


    /**
     * Gets the taxesOrMiscellaneousCharge value for this CommercialInvoice.
     * 
     * @return taxesOrMiscellaneousCharge   * Any taxes or miscellaneous charges(other than Freight charges
     * or Insurance charges) that are associated with this shipment.
     */
    public com.fedex.ws.rate.v22.Money getTaxesOrMiscellaneousCharge() {
        return taxesOrMiscellaneousCharge;
    }


    /**
     * Sets the taxesOrMiscellaneousCharge value for this CommercialInvoice.
     * 
     * @param taxesOrMiscellaneousCharge   * Any taxes or miscellaneous charges(other than Freight charges
     * or Insurance charges) that are associated with this shipment.
     */
    public void setTaxesOrMiscellaneousCharge(com.fedex.ws.rate.v22.Money taxesOrMiscellaneousCharge) {
        this.taxesOrMiscellaneousCharge = taxesOrMiscellaneousCharge;
    }


    /**
     * Gets the taxesOrMiscellaneousChargeType value for this CommercialInvoice.
     * 
     * @return taxesOrMiscellaneousChargeType   * Specifies which kind of charge is being recorded in the preceding
     * field.
     */
    public com.fedex.ws.rate.v22.TaxesOrMiscellaneousChargeType getTaxesOrMiscellaneousChargeType() {
        return taxesOrMiscellaneousChargeType;
    }


    /**
     * Sets the taxesOrMiscellaneousChargeType value for this CommercialInvoice.
     * 
     * @param taxesOrMiscellaneousChargeType   * Specifies which kind of charge is being recorded in the preceding
     * field.
     */
    public void setTaxesOrMiscellaneousChargeType(com.fedex.ws.rate.v22.TaxesOrMiscellaneousChargeType taxesOrMiscellaneousChargeType) {
        this.taxesOrMiscellaneousChargeType = taxesOrMiscellaneousChargeType;
    }


    /**
     * Gets the packingCosts value for this CommercialInvoice.
     * 
     * @return packingCosts   * Any packing costs that are associated with this shipment.
     */
    public com.fedex.ws.rate.v22.Money getPackingCosts() {
        return packingCosts;
    }


    /**
     * Sets the packingCosts value for this CommercialInvoice.
     * 
     * @param packingCosts   * Any packing costs that are associated with this shipment.
     */
    public void setPackingCosts(com.fedex.ws.rate.v22.Money packingCosts) {
        this.packingCosts = packingCosts;
    }


    /**
     * Gets the handlingCosts value for this CommercialInvoice.
     * 
     * @return handlingCosts   * Any handling costs that are associated with this shipment.
     */
    public com.fedex.ws.rate.v22.Money getHandlingCosts() {
        return handlingCosts;
    }


    /**
     * Sets the handlingCosts value for this CommercialInvoice.
     * 
     * @param handlingCosts   * Any handling costs that are associated with this shipment.
     */
    public void setHandlingCosts(com.fedex.ws.rate.v22.Money handlingCosts) {
        this.handlingCosts = handlingCosts;
    }


    /**
     * Gets the specialInstructions value for this CommercialInvoice.
     * 
     * @return specialInstructions   * Free-form text.
     */
    public java.lang.String getSpecialInstructions() {
        return specialInstructions;
    }


    /**
     * Sets the specialInstructions value for this CommercialInvoice.
     * 
     * @param specialInstructions   * Free-form text.
     */
    public void setSpecialInstructions(java.lang.String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }


    /**
     * Gets the declarationStatement value for this CommercialInvoice.
     * 
     * @return declarationStatement   * Free-form text.
     */
    public java.lang.String getDeclarationStatement() {
        return declarationStatement;
    }


    /**
     * Sets the declarationStatement value for this CommercialInvoice.
     * 
     * @param declarationStatement   * Free-form text.
     */
    public void setDeclarationStatement(java.lang.String declarationStatement) {
        this.declarationStatement = declarationStatement;
    }


    /**
     * Gets the paymentTerms value for this CommercialInvoice.
     * 
     * @return paymentTerms   * Free-form text.
     */
    public java.lang.String getPaymentTerms() {
        return paymentTerms;
    }


    /**
     * Sets the paymentTerms value for this CommercialInvoice.
     * 
     * @param paymentTerms   * Free-form text.
     */
    public void setPaymentTerms(java.lang.String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }


    /**
     * Gets the purpose value for this CommercialInvoice.
     * 
     * @return purpose   * The reason for the shipment. Note: SOLD is not a valid purpose
     * for a Proforma Invoice.
     */
    public com.fedex.ws.rate.v22.PurposeOfShipmentType getPurpose() {
        return purpose;
    }


    /**
     * Sets the purpose value for this CommercialInvoice.
     * 
     * @param purpose   * The reason for the shipment. Note: SOLD is not a valid purpose
     * for a Proforma Invoice.
     */
    public void setPurpose(com.fedex.ws.rate.v22.PurposeOfShipmentType purpose) {
        this.purpose = purpose;
    }


    /**
     * Gets the originatorName value for this CommercialInvoice.
     * 
     * @return originatorName   * Name of the International Expert that completed the Commercial
     * Invoice different from Sender.
     */
    public java.lang.String getOriginatorName() {
        return originatorName;
    }


    /**
     * Sets the originatorName value for this CommercialInvoice.
     * 
     * @param originatorName   * Name of the International Expert that completed the Commercial
     * Invoice different from Sender.
     */
    public void setOriginatorName(java.lang.String originatorName) {
        this.originatorName = originatorName;
    }


    /**
     * Gets the termsOfSale value for this CommercialInvoice.
     * 
     * @return termsOfSale   * Required for dutiable international Express or Ground shipments.
     * This field is not applicable to an international PIB(document) or
     * a non-document which does not require a Commercial Invoice.
     */
    public java.lang.String getTermsOfSale() {
        return termsOfSale;
    }


    /**
     * Sets the termsOfSale value for this CommercialInvoice.
     * 
     * @param termsOfSale   * Required for dutiable international Express or Ground shipments.
     * This field is not applicable to an international PIB(document) or
     * a non-document which does not require a Commercial Invoice.
     */
    public void setTermsOfSale(java.lang.String termsOfSale) {
        this.termsOfSale = termsOfSale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommercialInvoice)) return false;
        CommercialInvoice other = (CommercialInvoice) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              java.util.Arrays.equals(this.comments, other.getComments()))) &&
            ((this.freightCharge==null && other.getFreightCharge()==null) || 
             (this.freightCharge!=null &&
              this.freightCharge.equals(other.getFreightCharge()))) &&
            ((this.taxesOrMiscellaneousCharge==null && other.getTaxesOrMiscellaneousCharge()==null) || 
             (this.taxesOrMiscellaneousCharge!=null &&
              this.taxesOrMiscellaneousCharge.equals(other.getTaxesOrMiscellaneousCharge()))) &&
            ((this.taxesOrMiscellaneousChargeType==null && other.getTaxesOrMiscellaneousChargeType()==null) || 
             (this.taxesOrMiscellaneousChargeType!=null &&
              this.taxesOrMiscellaneousChargeType.equals(other.getTaxesOrMiscellaneousChargeType()))) &&
            ((this.packingCosts==null && other.getPackingCosts()==null) || 
             (this.packingCosts!=null &&
              this.packingCosts.equals(other.getPackingCosts()))) &&
            ((this.handlingCosts==null && other.getHandlingCosts()==null) || 
             (this.handlingCosts!=null &&
              this.handlingCosts.equals(other.getHandlingCosts()))) &&
            ((this.specialInstructions==null && other.getSpecialInstructions()==null) || 
             (this.specialInstructions!=null &&
              this.specialInstructions.equals(other.getSpecialInstructions()))) &&
            ((this.declarationStatement==null && other.getDeclarationStatement()==null) || 
             (this.declarationStatement!=null &&
              this.declarationStatement.equals(other.getDeclarationStatement()))) &&
            ((this.paymentTerms==null && other.getPaymentTerms()==null) || 
             (this.paymentTerms!=null &&
              this.paymentTerms.equals(other.getPaymentTerms()))) &&
            ((this.purpose==null && other.getPurpose()==null) || 
             (this.purpose!=null &&
              this.purpose.equals(other.getPurpose()))) &&
            ((this.originatorName==null && other.getOriginatorName()==null) || 
             (this.originatorName!=null &&
              this.originatorName.equals(other.getOriginatorName()))) &&
            ((this.termsOfSale==null && other.getTermsOfSale()==null) || 
             (this.termsOfSale!=null &&
              this.termsOfSale.equals(other.getTermsOfSale())));
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
        if (getComments() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComments());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComments(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFreightCharge() != null) {
            _hashCode += getFreightCharge().hashCode();
        }
        if (getTaxesOrMiscellaneousCharge() != null) {
            _hashCode += getTaxesOrMiscellaneousCharge().hashCode();
        }
        if (getTaxesOrMiscellaneousChargeType() != null) {
            _hashCode += getTaxesOrMiscellaneousChargeType().hashCode();
        }
        if (getPackingCosts() != null) {
            _hashCode += getPackingCosts().hashCode();
        }
        if (getHandlingCosts() != null) {
            _hashCode += getHandlingCosts().hashCode();
        }
        if (getSpecialInstructions() != null) {
            _hashCode += getSpecialInstructions().hashCode();
        }
        if (getDeclarationStatement() != null) {
            _hashCode += getDeclarationStatement().hashCode();
        }
        if (getPaymentTerms() != null) {
            _hashCode += getPaymentTerms().hashCode();
        }
        if (getPurpose() != null) {
            _hashCode += getPurpose().hashCode();
        }
        if (getOriginatorName() != null) {
            _hashCode += getOriginatorName().hashCode();
        }
        if (getTermsOfSale() != null) {
            _hashCode += getTermsOfSale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommercialInvoice.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommercialInvoice"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxesOrMiscellaneousCharge");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TaxesOrMiscellaneousCharge"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("taxesOrMiscellaneousChargeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TaxesOrMiscellaneousChargeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TaxesOrMiscellaneousChargeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackingCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("handlingCosts");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HandlingCosts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Money"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specialInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SpecialInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("declarationStatement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DeclarationStatement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentTerms");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PaymentTerms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("purpose");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Purpose"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PurposeOfShipmentType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originatorName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OriginatorName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termsOfSale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TermsOfSale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
