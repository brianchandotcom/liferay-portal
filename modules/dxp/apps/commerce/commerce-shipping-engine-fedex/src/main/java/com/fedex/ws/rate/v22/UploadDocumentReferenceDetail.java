/**
 * UploadDocumentReferenceDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class UploadDocumentReferenceDetail  implements java.io.Serializable {
    private org.apache.axis.types.NonNegativeInteger lineNumber;

    private java.lang.String customerReference;

    /* Description of the uploaded document. */
    private java.lang.String description;

    private com.fedex.ws.rate.v22.UploadDocumentProducerType documentProducer;

    private com.fedex.ws.rate.v22.UploadDocumentType documentType;

    private java.lang.String documentId;

    private com.fedex.ws.rate.v22.UploadDocumentIdProducer documentIdProducer;

    public UploadDocumentReferenceDetail() {
    }

    public UploadDocumentReferenceDetail(
           org.apache.axis.types.NonNegativeInteger lineNumber,
           java.lang.String customerReference,
           java.lang.String description,
           com.fedex.ws.rate.v22.UploadDocumentProducerType documentProducer,
           com.fedex.ws.rate.v22.UploadDocumentType documentType,
           java.lang.String documentId,
           com.fedex.ws.rate.v22.UploadDocumentIdProducer documentIdProducer) {
           this.lineNumber = lineNumber;
           this.customerReference = customerReference;
           this.description = description;
           this.documentProducer = documentProducer;
           this.documentType = documentType;
           this.documentId = documentId;
           this.documentIdProducer = documentIdProducer;
    }


    /**
     * Gets the lineNumber value for this UploadDocumentReferenceDetail.
     * 
     * @return lineNumber
     */
    public org.apache.axis.types.NonNegativeInteger getLineNumber() {
        return lineNumber;
    }


    /**
     * Sets the lineNumber value for this UploadDocumentReferenceDetail.
     * 
     * @param lineNumber
     */
    public void setLineNumber(org.apache.axis.types.NonNegativeInteger lineNumber) {
        this.lineNumber = lineNumber;
    }


    /**
     * Gets the customerReference value for this UploadDocumentReferenceDetail.
     * 
     * @return customerReference
     */
    public java.lang.String getCustomerReference() {
        return customerReference;
    }


    /**
     * Sets the customerReference value for this UploadDocumentReferenceDetail.
     * 
     * @param customerReference
     */
    public void setCustomerReference(java.lang.String customerReference) {
        this.customerReference = customerReference;
    }


    /**
     * Gets the description value for this UploadDocumentReferenceDetail.
     * 
     * @return description   * Description of the uploaded document.
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this UploadDocumentReferenceDetail.
     * 
     * @param description   * Description of the uploaded document.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the documentProducer value for this UploadDocumentReferenceDetail.
     * 
     * @return documentProducer
     */
    public com.fedex.ws.rate.v22.UploadDocumentProducerType getDocumentProducer() {
        return documentProducer;
    }


    /**
     * Sets the documentProducer value for this UploadDocumentReferenceDetail.
     * 
     * @param documentProducer
     */
    public void setDocumentProducer(com.fedex.ws.rate.v22.UploadDocumentProducerType documentProducer) {
        this.documentProducer = documentProducer;
    }


    /**
     * Gets the documentType value for this UploadDocumentReferenceDetail.
     * 
     * @return documentType
     */
    public com.fedex.ws.rate.v22.UploadDocumentType getDocumentType() {
        return documentType;
    }


    /**
     * Sets the documentType value for this UploadDocumentReferenceDetail.
     * 
     * @param documentType
     */
    public void setDocumentType(com.fedex.ws.rate.v22.UploadDocumentType documentType) {
        this.documentType = documentType;
    }


    /**
     * Gets the documentId value for this UploadDocumentReferenceDetail.
     * 
     * @return documentId
     */
    public java.lang.String getDocumentId() {
        return documentId;
    }


    /**
     * Sets the documentId value for this UploadDocumentReferenceDetail.
     * 
     * @param documentId
     */
    public void setDocumentId(java.lang.String documentId) {
        this.documentId = documentId;
    }


    /**
     * Gets the documentIdProducer value for this UploadDocumentReferenceDetail.
     * 
     * @return documentIdProducer
     */
    public com.fedex.ws.rate.v22.UploadDocumentIdProducer getDocumentIdProducer() {
        return documentIdProducer;
    }


    /**
     * Sets the documentIdProducer value for this UploadDocumentReferenceDetail.
     * 
     * @param documentIdProducer
     */
    public void setDocumentIdProducer(com.fedex.ws.rate.v22.UploadDocumentIdProducer documentIdProducer) {
        this.documentIdProducer = documentIdProducer;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UploadDocumentReferenceDetail)) return false;
        UploadDocumentReferenceDetail other = (UploadDocumentReferenceDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lineNumber==null && other.getLineNumber()==null) || 
             (this.lineNumber!=null &&
              this.lineNumber.equals(other.getLineNumber()))) &&
            ((this.customerReference==null && other.getCustomerReference()==null) || 
             (this.customerReference!=null &&
              this.customerReference.equals(other.getCustomerReference()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.documentProducer==null && other.getDocumentProducer()==null) || 
             (this.documentProducer!=null &&
              this.documentProducer.equals(other.getDocumentProducer()))) &&
            ((this.documentType==null && other.getDocumentType()==null) || 
             (this.documentType!=null &&
              this.documentType.equals(other.getDocumentType()))) &&
            ((this.documentId==null && other.getDocumentId()==null) || 
             (this.documentId!=null &&
              this.documentId.equals(other.getDocumentId()))) &&
            ((this.documentIdProducer==null && other.getDocumentIdProducer()==null) || 
             (this.documentIdProducer!=null &&
              this.documentIdProducer.equals(other.getDocumentIdProducer())));
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
        if (getLineNumber() != null) {
            _hashCode += getLineNumber().hashCode();
        }
        if (getCustomerReference() != null) {
            _hashCode += getCustomerReference().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getDocumentProducer() != null) {
            _hashCode += getDocumentProducer().hashCode();
        }
        if (getDocumentType() != null) {
            _hashCode += getDocumentType().hashCode();
        }
        if (getDocumentId() != null) {
            _hashCode += getDocumentId().hashCode();
        }
        if (getDocumentIdProducer() != null) {
            _hashCode += getDocumentIdProducer().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UploadDocumentReferenceDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UploadDocumentReferenceDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LineNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerReference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerReference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentProducer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocumentProducer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UploadDocumentProducerType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocumentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UploadDocumentType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocumentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("documentIdProducer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocumentIdProducer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UploadDocumentIdProducer"));
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
