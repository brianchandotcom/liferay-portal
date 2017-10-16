/**
 * InternationalControlledExportDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class InternationalControlledExportDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.InternationalControlledExportType type;

    private java.lang.String foreignTradeZoneCode;

    private java.lang.String entryNumber;

    private java.lang.String licenseOrPermitNumber;

    private java.util.Date licenseOrPermitExpirationDate;

    public InternationalControlledExportDetail() {
    }

    public InternationalControlledExportDetail(
           com.fedex.ws.rate.v22.InternationalControlledExportType type,
           java.lang.String foreignTradeZoneCode,
           java.lang.String entryNumber,
           java.lang.String licenseOrPermitNumber,
           java.util.Date licenseOrPermitExpirationDate) {
           this.type = type;
           this.foreignTradeZoneCode = foreignTradeZoneCode;
           this.entryNumber = entryNumber;
           this.licenseOrPermitNumber = licenseOrPermitNumber;
           this.licenseOrPermitExpirationDate = licenseOrPermitExpirationDate;
    }


    /**
     * Gets the type value for this InternationalControlledExportDetail.
     * 
     * @return type
     */
    public com.fedex.ws.rate.v22.InternationalControlledExportType getType() {
        return type;
    }


    /**
     * Sets the type value for this InternationalControlledExportDetail.
     * 
     * @param type
     */
    public void setType(com.fedex.ws.rate.v22.InternationalControlledExportType type) {
        this.type = type;
    }


    /**
     * Gets the foreignTradeZoneCode value for this InternationalControlledExportDetail.
     * 
     * @return foreignTradeZoneCode
     */
    public java.lang.String getForeignTradeZoneCode() {
        return foreignTradeZoneCode;
    }


    /**
     * Sets the foreignTradeZoneCode value for this InternationalControlledExportDetail.
     * 
     * @param foreignTradeZoneCode
     */
    public void setForeignTradeZoneCode(java.lang.String foreignTradeZoneCode) {
        this.foreignTradeZoneCode = foreignTradeZoneCode;
    }


    /**
     * Gets the entryNumber value for this InternationalControlledExportDetail.
     * 
     * @return entryNumber
     */
    public java.lang.String getEntryNumber() {
        return entryNumber;
    }


    /**
     * Sets the entryNumber value for this InternationalControlledExportDetail.
     * 
     * @param entryNumber
     */
    public void setEntryNumber(java.lang.String entryNumber) {
        this.entryNumber = entryNumber;
    }


    /**
     * Gets the licenseOrPermitNumber value for this InternationalControlledExportDetail.
     * 
     * @return licenseOrPermitNumber
     */
    public java.lang.String getLicenseOrPermitNumber() {
        return licenseOrPermitNumber;
    }


    /**
     * Sets the licenseOrPermitNumber value for this InternationalControlledExportDetail.
     * 
     * @param licenseOrPermitNumber
     */
    public void setLicenseOrPermitNumber(java.lang.String licenseOrPermitNumber) {
        this.licenseOrPermitNumber = licenseOrPermitNumber;
    }


    /**
     * Gets the licenseOrPermitExpirationDate value for this InternationalControlledExportDetail.
     * 
     * @return licenseOrPermitExpirationDate
     */
    public java.util.Date getLicenseOrPermitExpirationDate() {
        return licenseOrPermitExpirationDate;
    }


    /**
     * Sets the licenseOrPermitExpirationDate value for this InternationalControlledExportDetail.
     * 
     * @param licenseOrPermitExpirationDate
     */
    public void setLicenseOrPermitExpirationDate(java.util.Date licenseOrPermitExpirationDate) {
        this.licenseOrPermitExpirationDate = licenseOrPermitExpirationDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InternationalControlledExportDetail)) return false;
        InternationalControlledExportDetail other = (InternationalControlledExportDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.foreignTradeZoneCode==null && other.getForeignTradeZoneCode()==null) || 
             (this.foreignTradeZoneCode!=null &&
              this.foreignTradeZoneCode.equals(other.getForeignTradeZoneCode()))) &&
            ((this.entryNumber==null && other.getEntryNumber()==null) || 
             (this.entryNumber!=null &&
              this.entryNumber.equals(other.getEntryNumber()))) &&
            ((this.licenseOrPermitNumber==null && other.getLicenseOrPermitNumber()==null) || 
             (this.licenseOrPermitNumber!=null &&
              this.licenseOrPermitNumber.equals(other.getLicenseOrPermitNumber()))) &&
            ((this.licenseOrPermitExpirationDate==null && other.getLicenseOrPermitExpirationDate()==null) || 
             (this.licenseOrPermitExpirationDate!=null &&
              this.licenseOrPermitExpirationDate.equals(other.getLicenseOrPermitExpirationDate())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getForeignTradeZoneCode() != null) {
            _hashCode += getForeignTradeZoneCode().hashCode();
        }
        if (getEntryNumber() != null) {
            _hashCode += getEntryNumber().hashCode();
        }
        if (getLicenseOrPermitNumber() != null) {
            _hashCode += getLicenseOrPermitNumber().hashCode();
        }
        if (getLicenseOrPermitExpirationDate() != null) {
            _hashCode += getLicenseOrPermitExpirationDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InternationalControlledExportDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "InternationalControlledExportDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "InternationalControlledExportType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foreignTradeZoneCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ForeignTradeZoneCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EntryNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseOrPermitNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LicenseOrPermitNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("licenseOrPermitExpirationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LicenseOrPermitExpirationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
