/**
 * DataCategoryGroupSobjectTypePair.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.partner;

public class DataCategoryGroupSobjectTypePair  implements java.io.Serializable {
    private java.lang.String dataCategoryGroupName;

    private java.lang.String sobject;

    public DataCategoryGroupSobjectTypePair() {
    }

    public DataCategoryGroupSobjectTypePair(
           java.lang.String dataCategoryGroupName,
           java.lang.String sobject) {
           this.dataCategoryGroupName = dataCategoryGroupName;
           this.sobject = sobject;
    }


    /**
     * Gets the dataCategoryGroupName value for this DataCategoryGroupSobjectTypePair.
     * 
     * @return dataCategoryGroupName
     */
    public java.lang.String getDataCategoryGroupName() {
        return dataCategoryGroupName;
    }


    /**
     * Sets the dataCategoryGroupName value for this DataCategoryGroupSobjectTypePair.
     * 
     * @param dataCategoryGroupName
     */
    public void setDataCategoryGroupName(java.lang.String dataCategoryGroupName) {
        this.dataCategoryGroupName = dataCategoryGroupName;
    }


    /**
     * Gets the sobject value for this DataCategoryGroupSobjectTypePair.
     * 
     * @return sobject
     */
    public java.lang.String getSobject() {
        return sobject;
    }


    /**
     * Sets the sobject value for this DataCategoryGroupSobjectTypePair.
     * 
     * @param sobject
     */
    public void setSobject(java.lang.String sobject) {
        this.sobject = sobject;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DataCategoryGroupSobjectTypePair)) return false;
        DataCategoryGroupSobjectTypePair other = (DataCategoryGroupSobjectTypePair) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataCategoryGroupName==null && other.getDataCategoryGroupName()==null) || 
             (this.dataCategoryGroupName!=null &&
              this.dataCategoryGroupName.equals(other.getDataCategoryGroupName()))) &&
            ((this.sobject==null && other.getSobject()==null) || 
             (this.sobject!=null &&
              this.sobject.equals(other.getSobject())));
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
        if (getDataCategoryGroupName() != null) {
            _hashCode += getDataCategoryGroupName().hashCode();
        }
        if (getSobject() != null) {
            _hashCode += getSobject().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DataCategoryGroupSobjectTypePair.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DataCategoryGroupSobjectTypePair"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataCategoryGroupName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "dataCategoryGroupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sobject");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "sobject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
