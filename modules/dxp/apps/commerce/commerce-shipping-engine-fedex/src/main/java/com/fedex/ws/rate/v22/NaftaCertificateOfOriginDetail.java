/**
 * NaftaCertificateOfOriginDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Data required to produce a Certificate of Origin document. Remaining
 * content (business data) to be defined once requirements have been
 * completed.
 */
public class NaftaCertificateOfOriginDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.ShippingDocumentFormat format;

    private com.fedex.ws.rate.v22.DateRange blanketPeriod;

    /* Indicates which Party (if any) from the shipment is to be used
     * as the source of importer data on the NAFTA COO form. */
    private com.fedex.ws.rate.v22.NaftaImporterSpecificationType importerSpecification;

    /* Contact information for "Authorized Signature" area of form. */
    private com.fedex.ws.rate.v22.Contact signatureContact;

    private com.fedex.ws.rate.v22.NaftaProducerSpecificationType producerSpecification;

    private com.fedex.ws.rate.v22.NaftaProducer[] producers;

    private com.fedex.ws.rate.v22.CustomerImageUsage[] customerImageUsages;

    public NaftaCertificateOfOriginDetail() {
    }

    public NaftaCertificateOfOriginDetail(
           com.fedex.ws.rate.v22.ShippingDocumentFormat format,
           com.fedex.ws.rate.v22.DateRange blanketPeriod,
           com.fedex.ws.rate.v22.NaftaImporterSpecificationType importerSpecification,
           com.fedex.ws.rate.v22.Contact signatureContact,
           com.fedex.ws.rate.v22.NaftaProducerSpecificationType producerSpecification,
           com.fedex.ws.rate.v22.NaftaProducer[] producers,
           com.fedex.ws.rate.v22.CustomerImageUsage[] customerImageUsages) {
           this.format = format;
           this.blanketPeriod = blanketPeriod;
           this.importerSpecification = importerSpecification;
           this.signatureContact = signatureContact;
           this.producerSpecification = producerSpecification;
           this.producers = producers;
           this.customerImageUsages = customerImageUsages;
    }


    /**
     * Gets the format value for this NaftaCertificateOfOriginDetail.
     * 
     * @return format
     */
    public com.fedex.ws.rate.v22.ShippingDocumentFormat getFormat() {
        return format;
    }


    /**
     * Sets the format value for this NaftaCertificateOfOriginDetail.
     * 
     * @param format
     */
    public void setFormat(com.fedex.ws.rate.v22.ShippingDocumentFormat format) {
        this.format = format;
    }


    /**
     * Gets the blanketPeriod value for this NaftaCertificateOfOriginDetail.
     * 
     * @return blanketPeriod
     */
    public com.fedex.ws.rate.v22.DateRange getBlanketPeriod() {
        return blanketPeriod;
    }


    /**
     * Sets the blanketPeriod value for this NaftaCertificateOfOriginDetail.
     * 
     * @param blanketPeriod
     */
    public void setBlanketPeriod(com.fedex.ws.rate.v22.DateRange blanketPeriod) {
        this.blanketPeriod = blanketPeriod;
    }


    /**
     * Gets the importerSpecification value for this NaftaCertificateOfOriginDetail.
     * 
     * @return importerSpecification   * Indicates which Party (if any) from the shipment is to be used
     * as the source of importer data on the NAFTA COO form.
     */
    public com.fedex.ws.rate.v22.NaftaImporterSpecificationType getImporterSpecification() {
        return importerSpecification;
    }


    /**
     * Sets the importerSpecification value for this NaftaCertificateOfOriginDetail.
     * 
     * @param importerSpecification   * Indicates which Party (if any) from the shipment is to be used
     * as the source of importer data on the NAFTA COO form.
     */
    public void setImporterSpecification(com.fedex.ws.rate.v22.NaftaImporterSpecificationType importerSpecification) {
        this.importerSpecification = importerSpecification;
    }


    /**
     * Gets the signatureContact value for this NaftaCertificateOfOriginDetail.
     * 
     * @return signatureContact   * Contact information for "Authorized Signature" area of form.
     */
    public com.fedex.ws.rate.v22.Contact getSignatureContact() {
        return signatureContact;
    }


    /**
     * Sets the signatureContact value for this NaftaCertificateOfOriginDetail.
     * 
     * @param signatureContact   * Contact information for "Authorized Signature" area of form.
     */
    public void setSignatureContact(com.fedex.ws.rate.v22.Contact signatureContact) {
        this.signatureContact = signatureContact;
    }


    /**
     * Gets the producerSpecification value for this NaftaCertificateOfOriginDetail.
     * 
     * @return producerSpecification
     */
    public com.fedex.ws.rate.v22.NaftaProducerSpecificationType getProducerSpecification() {
        return producerSpecification;
    }


    /**
     * Sets the producerSpecification value for this NaftaCertificateOfOriginDetail.
     * 
     * @param producerSpecification
     */
    public void setProducerSpecification(com.fedex.ws.rate.v22.NaftaProducerSpecificationType producerSpecification) {
        this.producerSpecification = producerSpecification;
    }


    /**
     * Gets the producers value for this NaftaCertificateOfOriginDetail.
     * 
     * @return producers
     */
    public com.fedex.ws.rate.v22.NaftaProducer[] getProducers() {
        return producers;
    }


    /**
     * Sets the producers value for this NaftaCertificateOfOriginDetail.
     * 
     * @param producers
     */
    public void setProducers(com.fedex.ws.rate.v22.NaftaProducer[] producers) {
        this.producers = producers;
    }

    public com.fedex.ws.rate.v22.NaftaProducer getProducers(int i) {
        return this.producers[i];
    }

    public void setProducers(int i, com.fedex.ws.rate.v22.NaftaProducer _value) {
        this.producers[i] = _value;
    }


    /**
     * Gets the customerImageUsages value for this NaftaCertificateOfOriginDetail.
     * 
     * @return customerImageUsages
     */
    public com.fedex.ws.rate.v22.CustomerImageUsage[] getCustomerImageUsages() {
        return customerImageUsages;
    }


    /**
     * Sets the customerImageUsages value for this NaftaCertificateOfOriginDetail.
     * 
     * @param customerImageUsages
     */
    public void setCustomerImageUsages(com.fedex.ws.rate.v22.CustomerImageUsage[] customerImageUsages) {
        this.customerImageUsages = customerImageUsages;
    }

    public com.fedex.ws.rate.v22.CustomerImageUsage getCustomerImageUsages(int i) {
        return this.customerImageUsages[i];
    }

    public void setCustomerImageUsages(int i, com.fedex.ws.rate.v22.CustomerImageUsage _value) {
        this.customerImageUsages[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NaftaCertificateOfOriginDetail)) return false;
        NaftaCertificateOfOriginDetail other = (NaftaCertificateOfOriginDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.blanketPeriod==null && other.getBlanketPeriod()==null) || 
             (this.blanketPeriod!=null &&
              this.blanketPeriod.equals(other.getBlanketPeriod()))) &&
            ((this.importerSpecification==null && other.getImporterSpecification()==null) || 
             (this.importerSpecification!=null &&
              this.importerSpecification.equals(other.getImporterSpecification()))) &&
            ((this.signatureContact==null && other.getSignatureContact()==null) || 
             (this.signatureContact!=null &&
              this.signatureContact.equals(other.getSignatureContact()))) &&
            ((this.producerSpecification==null && other.getProducerSpecification()==null) || 
             (this.producerSpecification!=null &&
              this.producerSpecification.equals(other.getProducerSpecification()))) &&
            ((this.producers==null && other.getProducers()==null) || 
             (this.producers!=null &&
              java.util.Arrays.equals(this.producers, other.getProducers()))) &&
            ((this.customerImageUsages==null && other.getCustomerImageUsages()==null) || 
             (this.customerImageUsages!=null &&
              java.util.Arrays.equals(this.customerImageUsages, other.getCustomerImageUsages())));
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
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getBlanketPeriod() != null) {
            _hashCode += getBlanketPeriod().hashCode();
        }
        if (getImporterSpecification() != null) {
            _hashCode += getImporterSpecification().hashCode();
        }
        if (getSignatureContact() != null) {
            _hashCode += getSignatureContact().hashCode();
        }
        if (getProducerSpecification() != null) {
            _hashCode += getProducerSpecification().hashCode();
        }
        if (getProducers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProducers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProducers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCustomerImageUsages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCustomerImageUsages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCustomerImageUsages(), i);
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
        new org.apache.axis.description.TypeDesc(NaftaCertificateOfOriginDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaCertificateOfOriginDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShippingDocumentFormat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blanketPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BlanketPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DateRange"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importerSpecification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ImporterSpecification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaImporterSpecificationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signatureContact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SignatureContact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Contact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producerSpecification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProducerSpecification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaProducerSpecificationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Producers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaProducer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerImageUsages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerImageUsages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerImageUsage"));
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
