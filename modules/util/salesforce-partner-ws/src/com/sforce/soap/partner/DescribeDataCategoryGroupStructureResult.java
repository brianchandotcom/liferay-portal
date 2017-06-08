/**
 * DescribeDataCategoryGroupStructureResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.partner;

public class DescribeDataCategoryGroupStructureResult  implements java.io.Serializable {
    private java.lang.String description;

    private java.lang.String label;

    private java.lang.String name;

    private java.lang.String sobject;

    private com.sforce.soap.partner.DataCategory[] topCategories;

    public DescribeDataCategoryGroupStructureResult() {
    }

    public DescribeDataCategoryGroupStructureResult(
           java.lang.String description,
           java.lang.String label,
           java.lang.String name,
           java.lang.String sobject,
           com.sforce.soap.partner.DataCategory[] topCategories) {
           this.description = description;
           this.label = label;
           this.name = name;
           this.sobject = sobject;
           this.topCategories = topCategories;
    }


    /**
     * Gets the description value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the label value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @return label
     */
    public java.lang.String getLabel() {
        return label;
    }


    /**
     * Sets the label value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @param label
     */
    public void setLabel(java.lang.String label) {
        this.label = label;
    }


    /**
     * Gets the name value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the sobject value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @return sobject
     */
    public java.lang.String getSobject() {
        return sobject;
    }


    /**
     * Sets the sobject value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @param sobject
     */
    public void setSobject(java.lang.String sobject) {
        this.sobject = sobject;
    }


    /**
     * Gets the topCategories value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @return topCategories
     */
    public com.sforce.soap.partner.DataCategory[] getTopCategories() {
        return topCategories;
    }


    /**
     * Sets the topCategories value for this DescribeDataCategoryGroupStructureResult.
     * 
     * @param topCategories
     */
    public void setTopCategories(com.sforce.soap.partner.DataCategory[] topCategories) {
        this.topCategories = topCategories;
    }

    public com.sforce.soap.partner.DataCategory getTopCategories(int i) {
        return this.topCategories[i];
    }

    public void setTopCategories(int i, com.sforce.soap.partner.DataCategory _value) {
        this.topCategories[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeDataCategoryGroupStructureResult)) return false;
        DescribeDataCategoryGroupStructureResult other = (DescribeDataCategoryGroupStructureResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.label==null && other.getLabel()==null) || 
             (this.label!=null &&
              this.label.equals(other.getLabel()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.sobject==null && other.getSobject()==null) || 
             (this.sobject!=null &&
              this.sobject.equals(other.getSobject()))) &&
            ((this.topCategories==null && other.getTopCategories()==null) || 
             (this.topCategories!=null &&
              java.util.Arrays.equals(this.topCategories, other.getTopCategories())));
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getLabel() != null) {
            _hashCode += getLabel().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSobject() != null) {
            _hashCode += getSobject().hashCode();
        }
        if (getTopCategories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTopCategories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTopCategories(), i);
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
        new org.apache.axis.description.TypeDesc(DescribeDataCategoryGroupStructureResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DescribeDataCategoryGroupStructureResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("label");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "label"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sobject");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "sobject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topCategories");
        elemField.setXmlName(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "topCategories"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:partner.soap.sforce.com", "DataCategory"));
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
