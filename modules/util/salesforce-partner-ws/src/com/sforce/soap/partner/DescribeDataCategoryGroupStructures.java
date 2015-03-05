/**
 * DescribeDataCategoryGroupStructures.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.partner;

public class DescribeDataCategoryGroupStructures  implements java.io.Serializable {
    private com.sforce.soap.partner.DataCategoryGroupSobjectTypePair[] pairs;

    private boolean topCategoriesOnly;

    public DescribeDataCategoryGroupStructures() {
    }

    public DescribeDataCategoryGroupStructures(
           com.sforce.soap.partner.DataCategoryGroupSobjectTypePair[] pairs,
           boolean topCategoriesOnly) {
           this.pairs = pairs;
           this.topCategoriesOnly = topCategoriesOnly;
    }


    /**
     * Gets the pairs value for this DescribeDataCategoryGroupStructures.
     * 
     * @return pairs
     */
    public com.sforce.soap.partner.DataCategoryGroupSobjectTypePair[] getPairs() {
        return pairs;
    }


    /**
     * Sets the pairs value for this DescribeDataCategoryGroupStructures.
     * 
     * @param pairs
     */
    public void setPairs(com.sforce.soap.partner.DataCategoryGroupSobjectTypePair[] pairs) {
        this.pairs = pairs;
    }

    public com.sforce.soap.partner.DataCategoryGroupSobjectTypePair getPairs(int i) {
        return this.pairs[i];
    }

    public void setPairs(int i, com.sforce.soap.partner.DataCategoryGroupSobjectTypePair _value) {
        this.pairs[i] = _value;
    }


    /**
     * Gets the topCategoriesOnly value for this DescribeDataCategoryGroupStructures.
     * 
     * @return topCategoriesOnly
     */
    public boolean isTopCategoriesOnly() {
        return topCategoriesOnly;
    }


    /**
     * Sets the topCategoriesOnly value for this DescribeDataCategoryGroupStructures.
     * 
     * @param topCategoriesOnly
     */
    public void setTopCategoriesOnly(boolean topCategoriesOnly) {
        this.topCategoriesOnly = topCategoriesOnly;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeDataCategoryGroupStructures)) return false;
        DescribeDataCategoryGroupStructures other = (DescribeDataCategoryGroupStructures) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pairs==null && other.getPairs()==null) || 
             (this.pairs!=null &&
              java.util.Arrays.equals(this.pairs, other.getPairs()))) &&
            this.topCategoriesOnly == other.isTopCategoriesOnly();
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
        if (getPairs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPairs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPairs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += (isTopCategoriesOnly() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DescribeDataCategoryGroupStructures.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", ">describeDataCategoryGroupStructures"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pairs");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "pairs"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DataCategoryGroupSobjectTypePair"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topCategoriesOnly");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "topCategoriesOnly"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
