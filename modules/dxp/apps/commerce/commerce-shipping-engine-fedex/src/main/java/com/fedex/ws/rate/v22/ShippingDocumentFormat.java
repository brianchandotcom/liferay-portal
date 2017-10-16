/**
 * ShippingDocumentFormat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Specifies characteristics of a shipping document to be produced.
 */
public class ShippingDocumentFormat  implements java.io.Serializable {
    /* Specifies how to create, organize, and return the document. */
    private com.fedex.ws.rate.v22.ShippingDocumentDispositionDetail[] dispositions;

    /* Specifies how far down the page to move the beginning of the
     * image; allows for printing on letterhead and other pre-printed stock. */
    private com.fedex.ws.rate.v22.LinearMeasure topOfPageOffset;

    private com.fedex.ws.rate.v22.ShippingDocumentImageType imageType;

    private com.fedex.ws.rate.v22.ShippingDocumentStockType stockType;

    /* For those shipping document types which have both a "form"
     * and "instructions" component (e.g. NAFTA Certificate of Origin and
     * General Agency Agreement), this field indicates whether to provide
     * the instructions. */
    private java.lang.Boolean provideInstructions;

    private com.fedex.ws.rate.v22.DocumentFormatOptionType[] optionsRequested;

    /* Governs the language to be used for this individual document,
     * independently from other content returned for the same shipment. */
    private com.fedex.ws.rate.v22.Localization localization;

    public ShippingDocumentFormat() {
    }

    public ShippingDocumentFormat(
           com.fedex.ws.rate.v22.ShippingDocumentDispositionDetail[] dispositions,
           com.fedex.ws.rate.v22.LinearMeasure topOfPageOffset,
           com.fedex.ws.rate.v22.ShippingDocumentImageType imageType,
           com.fedex.ws.rate.v22.ShippingDocumentStockType stockType,
           java.lang.Boolean provideInstructions,
           com.fedex.ws.rate.v22.DocumentFormatOptionType[] optionsRequested,
           com.fedex.ws.rate.v22.Localization localization) {
           this.dispositions = dispositions;
           this.topOfPageOffset = topOfPageOffset;
           this.imageType = imageType;
           this.stockType = stockType;
           this.provideInstructions = provideInstructions;
           this.optionsRequested = optionsRequested;
           this.localization = localization;
    }


    /**
     * Gets the dispositions value for this ShippingDocumentFormat.
     * 
     * @return dispositions   * Specifies how to create, organize, and return the document.
     */
    public com.fedex.ws.rate.v22.ShippingDocumentDispositionDetail[] getDispositions() {
        return dispositions;
    }


    /**
     * Sets the dispositions value for this ShippingDocumentFormat.
     * 
     * @param dispositions   * Specifies how to create, organize, and return the document.
     */
    public void setDispositions(com.fedex.ws.rate.v22.ShippingDocumentDispositionDetail[] dispositions) {
        this.dispositions = dispositions;
    }

    public com.fedex.ws.rate.v22.ShippingDocumentDispositionDetail getDispositions(int i) {
        return this.dispositions[i];
    }

    public void setDispositions(int i, com.fedex.ws.rate.v22.ShippingDocumentDispositionDetail _value) {
        this.dispositions[i] = _value;
    }


    /**
     * Gets the topOfPageOffset value for this ShippingDocumentFormat.
     * 
     * @return topOfPageOffset   * Specifies how far down the page to move the beginning of the
     * image; allows for printing on letterhead and other pre-printed stock.
     */
    public com.fedex.ws.rate.v22.LinearMeasure getTopOfPageOffset() {
        return topOfPageOffset;
    }


    /**
     * Sets the topOfPageOffset value for this ShippingDocumentFormat.
     * 
     * @param topOfPageOffset   * Specifies how far down the page to move the beginning of the
     * image; allows for printing on letterhead and other pre-printed stock.
     */
    public void setTopOfPageOffset(com.fedex.ws.rate.v22.LinearMeasure topOfPageOffset) {
        this.topOfPageOffset = topOfPageOffset;
    }


    /**
     * Gets the imageType value for this ShippingDocumentFormat.
     * 
     * @return imageType
     */
    public com.fedex.ws.rate.v22.ShippingDocumentImageType getImageType() {
        return imageType;
    }


    /**
     * Sets the imageType value for this ShippingDocumentFormat.
     * 
     * @param imageType
     */
    public void setImageType(com.fedex.ws.rate.v22.ShippingDocumentImageType imageType) {
        this.imageType = imageType;
    }


    /**
     * Gets the stockType value for this ShippingDocumentFormat.
     * 
     * @return stockType
     */
    public com.fedex.ws.rate.v22.ShippingDocumentStockType getStockType() {
        return stockType;
    }


    /**
     * Sets the stockType value for this ShippingDocumentFormat.
     * 
     * @param stockType
     */
    public void setStockType(com.fedex.ws.rate.v22.ShippingDocumentStockType stockType) {
        this.stockType = stockType;
    }


    /**
     * Gets the provideInstructions value for this ShippingDocumentFormat.
     * 
     * @return provideInstructions   * For those shipping document types which have both a "form"
     * and "instructions" component (e.g. NAFTA Certificate of Origin and
     * General Agency Agreement), this field indicates whether to provide
     * the instructions.
     */
    public java.lang.Boolean getProvideInstructions() {
        return provideInstructions;
    }


    /**
     * Sets the provideInstructions value for this ShippingDocumentFormat.
     * 
     * @param provideInstructions   * For those shipping document types which have both a "form"
     * and "instructions" component (e.g. NAFTA Certificate of Origin and
     * General Agency Agreement), this field indicates whether to provide
     * the instructions.
     */
    public void setProvideInstructions(java.lang.Boolean provideInstructions) {
        this.provideInstructions = provideInstructions;
    }


    /**
     * Gets the optionsRequested value for this ShippingDocumentFormat.
     * 
     * @return optionsRequested
     */
    public com.fedex.ws.rate.v22.DocumentFormatOptionType[] getOptionsRequested() {
        return optionsRequested;
    }


    /**
     * Sets the optionsRequested value for this ShippingDocumentFormat.
     * 
     * @param optionsRequested
     */
    public void setOptionsRequested(com.fedex.ws.rate.v22.DocumentFormatOptionType[] optionsRequested) {
        this.optionsRequested = optionsRequested;
    }


    /**
     * Gets the localization value for this ShippingDocumentFormat.
     * 
     * @return localization   * Governs the language to be used for this individual document,
     * independently from other content returned for the same shipment.
     */
    public com.fedex.ws.rate.v22.Localization getLocalization() {
        return localization;
    }


    /**
     * Sets the localization value for this ShippingDocumentFormat.
     * 
     * @param localization   * Governs the language to be used for this individual document,
     * independently from other content returned for the same shipment.
     */
    public void setLocalization(com.fedex.ws.rate.v22.Localization localization) {
        this.localization = localization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShippingDocumentFormat)) return false;
        ShippingDocumentFormat other = (ShippingDocumentFormat) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dispositions==null && other.getDispositions()==null) || 
             (this.dispositions!=null &&
              java.util.Arrays.equals(this.dispositions, other.getDispositions()))) &&
            ((this.topOfPageOffset==null && other.getTopOfPageOffset()==null) || 
             (this.topOfPageOffset!=null &&
              this.topOfPageOffset.equals(other.getTopOfPageOffset()))) &&
            ((this.imageType==null && other.getImageType()==null) || 
             (this.imageType!=null &&
              this.imageType.equals(other.getImageType()))) &&
            ((this.stockType==null && other.getStockType()==null) || 
             (this.stockType!=null &&
              this.stockType.equals(other.getStockType()))) &&
            ((this.provideInstructions==null && other.getProvideInstructions()==null) || 
             (this.provideInstructions!=null &&
              this.provideInstructions.equals(other.getProvideInstructions()))) &&
            ((this.optionsRequested==null && other.getOptionsRequested()==null) || 
             (this.optionsRequested!=null &&
              java.util.Arrays.equals(this.optionsRequested, other.getOptionsRequested()))) &&
            ((this.localization==null && other.getLocalization()==null) || 
             (this.localization!=null &&
              this.localization.equals(other.getLocalization())));
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
        if (getDispositions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDispositions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDispositions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTopOfPageOffset() != null) {
            _hashCode += getTopOfPageOffset().hashCode();
        }
        if (getImageType() != null) {
            _hashCode += getImageType().hashCode();
        }
        if (getStockType() != null) {
            _hashCode += getStockType().hashCode();
        }
        if (getProvideInstructions() != null) {
            _hashCode += getProvideInstructions().hashCode();
        }
        if (getOptionsRequested() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOptionsRequested());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getOptionsRequested(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLocalization() != null) {
            _hashCode += getLocalization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShippingDocumentFormat.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentFormat"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispositions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dispositions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentDispositionDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topOfPageOffset");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TopOfPageOffset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LinearMeasure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("imageType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ImageType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentImageType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stockType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "StockType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentStockType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provideInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProvideInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("optionsRequested");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OptionsRequested"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocumentFormatOptionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Options"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Localization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Localization"));
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
