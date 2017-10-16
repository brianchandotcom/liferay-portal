/**
 * DocTabZoneSpecification.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class DocTabZoneSpecification  implements java.io.Serializable {
    private org.apache.axis.types.PositiveInteger zoneNumber;

    private java.lang.String header;

    private java.lang.String dataField;

    private java.lang.String literalValue;

    private com.fedex.ws.rate.v22.DocTabZoneJustificationType justification;

    public DocTabZoneSpecification() {
    }

    public DocTabZoneSpecification(
           org.apache.axis.types.PositiveInteger zoneNumber,
           java.lang.String header,
           java.lang.String dataField,
           java.lang.String literalValue,
           com.fedex.ws.rate.v22.DocTabZoneJustificationType justification) {
           this.zoneNumber = zoneNumber;
           this.header = header;
           this.dataField = dataField;
           this.literalValue = literalValue;
           this.justification = justification;
    }


    /**
     * Gets the zoneNumber value for this DocTabZoneSpecification.
     * 
     * @return zoneNumber
     */
    public org.apache.axis.types.PositiveInteger getZoneNumber() {
        return zoneNumber;
    }


    /**
     * Sets the zoneNumber value for this DocTabZoneSpecification.
     * 
     * @param zoneNumber
     */
    public void setZoneNumber(org.apache.axis.types.PositiveInteger zoneNumber) {
        this.zoneNumber = zoneNumber;
    }


    /**
     * Gets the header value for this DocTabZoneSpecification.
     * 
     * @return header
     */
    public java.lang.String getHeader() {
        return header;
    }


    /**
     * Sets the header value for this DocTabZoneSpecification.
     * 
     * @param header
     */
    public void setHeader(java.lang.String header) {
        this.header = header;
    }


    /**
     * Gets the dataField value for this DocTabZoneSpecification.
     * 
     * @return dataField
     */
    public java.lang.String getDataField() {
        return dataField;
    }


    /**
     * Sets the dataField value for this DocTabZoneSpecification.
     * 
     * @param dataField
     */
    public void setDataField(java.lang.String dataField) {
        this.dataField = dataField;
    }


    /**
     * Gets the literalValue value for this DocTabZoneSpecification.
     * 
     * @return literalValue
     */
    public java.lang.String getLiteralValue() {
        return literalValue;
    }


    /**
     * Sets the literalValue value for this DocTabZoneSpecification.
     * 
     * @param literalValue
     */
    public void setLiteralValue(java.lang.String literalValue) {
        this.literalValue = literalValue;
    }


    /**
     * Gets the justification value for this DocTabZoneSpecification.
     * 
     * @return justification
     */
    public com.fedex.ws.rate.v22.DocTabZoneJustificationType getJustification() {
        return justification;
    }


    /**
     * Sets the justification value for this DocTabZoneSpecification.
     * 
     * @param justification
     */
    public void setJustification(com.fedex.ws.rate.v22.DocTabZoneJustificationType justification) {
        this.justification = justification;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocTabZoneSpecification)) return false;
        DocTabZoneSpecification other = (DocTabZoneSpecification) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.zoneNumber==null && other.getZoneNumber()==null) || 
             (this.zoneNumber!=null &&
              this.zoneNumber.equals(other.getZoneNumber()))) &&
            ((this.header==null && other.getHeader()==null) || 
             (this.header!=null &&
              this.header.equals(other.getHeader()))) &&
            ((this.dataField==null && other.getDataField()==null) || 
             (this.dataField!=null &&
              this.dataField.equals(other.getDataField()))) &&
            ((this.literalValue==null && other.getLiteralValue()==null) || 
             (this.literalValue!=null &&
              this.literalValue.equals(other.getLiteralValue()))) &&
            ((this.justification==null && other.getJustification()==null) || 
             (this.justification!=null &&
              this.justification.equals(other.getJustification())));
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
        if (getZoneNumber() != null) {
            _hashCode += getZoneNumber().hashCode();
        }
        if (getHeader() != null) {
            _hashCode += getHeader().hashCode();
        }
        if (getDataField() != null) {
            _hashCode += getDataField().hashCode();
        }
        if (getLiteralValue() != null) {
            _hashCode += getLiteralValue().hashCode();
        }
        if (getJustification() != null) {
            _hashCode += getJustification().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocTabZoneSpecification.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocTabZoneSpecification"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zoneNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ZoneNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataField");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DataField"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("literalValue");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LiteralValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Justification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocTabZoneJustificationType"));
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
