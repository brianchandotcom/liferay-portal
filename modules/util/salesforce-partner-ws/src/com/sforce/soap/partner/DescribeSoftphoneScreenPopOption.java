/**
 * DescribeSoftphoneScreenPopOption.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.partner;

public class DescribeSoftphoneScreenPopOption  implements java.io.Serializable {
    private java.lang.String matchType;

    private java.lang.String screenPopData;

    private java.lang.String screenPopType;

    public DescribeSoftphoneScreenPopOption() {
    }

    public DescribeSoftphoneScreenPopOption(
           java.lang.String matchType,
           java.lang.String screenPopData,
           java.lang.String screenPopType) {
           this.matchType = matchType;
           this.screenPopData = screenPopData;
           this.screenPopType = screenPopType;
    }


    /**
     * Gets the matchType value for this DescribeSoftphoneScreenPopOption.
     * 
     * @return matchType
     */
    public java.lang.String getMatchType() {
        return matchType;
    }


    /**
     * Sets the matchType value for this DescribeSoftphoneScreenPopOption.
     * 
     * @param matchType
     */
    public void setMatchType(java.lang.String matchType) {
        this.matchType = matchType;
    }


    /**
     * Gets the screenPopData value for this DescribeSoftphoneScreenPopOption.
     * 
     * @return screenPopData
     */
    public java.lang.String getScreenPopData() {
        return screenPopData;
    }


    /**
     * Sets the screenPopData value for this DescribeSoftphoneScreenPopOption.
     * 
     * @param screenPopData
     */
    public void setScreenPopData(java.lang.String screenPopData) {
        this.screenPopData = screenPopData;
    }


    /**
     * Gets the screenPopType value for this DescribeSoftphoneScreenPopOption.
     * 
     * @return screenPopType
     */
    public java.lang.String getScreenPopType() {
        return screenPopType;
    }


    /**
     * Sets the screenPopType value for this DescribeSoftphoneScreenPopOption.
     * 
     * @param screenPopType
     */
    public void setScreenPopType(java.lang.String screenPopType) {
        this.screenPopType = screenPopType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeSoftphoneScreenPopOption)) return false;
        DescribeSoftphoneScreenPopOption other = (DescribeSoftphoneScreenPopOption) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.matchType==null && other.getMatchType()==null) || 
             (this.matchType!=null &&
              this.matchType.equals(other.getMatchType()))) &&
            ((this.screenPopData==null && other.getScreenPopData()==null) || 
             (this.screenPopData!=null &&
              this.screenPopData.equals(other.getScreenPopData()))) &&
            ((this.screenPopType==null && other.getScreenPopType()==null) || 
             (this.screenPopType!=null &&
              this.screenPopType.equals(other.getScreenPopType())));
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
        if (getMatchType() != null) {
            _hashCode += getMatchType().hashCode();
        }
        if (getScreenPopData() != null) {
            _hashCode += getScreenPopData().hashCode();
        }
        if (getScreenPopType() != null) {
            _hashCode += getScreenPopType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DescribeSoftphoneScreenPopOption.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DescribeSoftphoneScreenPopOption"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchType");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "matchType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("screenPopData");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "screenPopData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("screenPopType");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "screenPopType"));
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
