/**
 * ConsolidationKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Uniquely identifies a consolidation, which is a logical container
 * for a collection of shipments.
 */
public class ConsolidationKey  implements java.io.Serializable {
    /* Specifies the type of consolidation. */
    private com.fedex.ws.rate.v22.ConsolidationType type;

    /* Uniquely identifies the consolidation, within a given type
     * and date. */
    private java.lang.String index;

    /* The date on which the consolidation was created. */
    private java.util.Date date;

    public ConsolidationKey() {
    }

    public ConsolidationKey(
           com.fedex.ws.rate.v22.ConsolidationType type,
           java.lang.String index,
           java.util.Date date) {
           this.type = type;
           this.index = index;
           this.date = date;
    }


    /**
     * Gets the type value for this ConsolidationKey.
     * 
     * @return type   * Specifies the type of consolidation.
     */
    public com.fedex.ws.rate.v22.ConsolidationType getType() {
        return type;
    }


    /**
     * Sets the type value for this ConsolidationKey.
     * 
     * @param type   * Specifies the type of consolidation.
     */
    public void setType(com.fedex.ws.rate.v22.ConsolidationType type) {
        this.type = type;
    }


    /**
     * Gets the index value for this ConsolidationKey.
     * 
     * @return index   * Uniquely identifies the consolidation, within a given type
     * and date.
     */
    public java.lang.String getIndex() {
        return index;
    }


    /**
     * Sets the index value for this ConsolidationKey.
     * 
     * @param index   * Uniquely identifies the consolidation, within a given type
     * and date.
     */
    public void setIndex(java.lang.String index) {
        this.index = index;
    }


    /**
     * Gets the date value for this ConsolidationKey.
     * 
     * @return date   * The date on which the consolidation was created.
     */
    public java.util.Date getDate() {
        return date;
    }


    /**
     * Sets the date value for this ConsolidationKey.
     * 
     * @param date   * The date on which the consolidation was created.
     */
    public void setDate(java.util.Date date) {
        this.date = date;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsolidationKey)) return false;
        ConsolidationKey other = (ConsolidationKey) obj;
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
            ((this.index==null && other.getIndex()==null) || 
             (this.index!=null &&
              this.index.equals(other.getIndex()))) &&
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate())));
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
        if (getIndex() != null) {
            _hashCode += getIndex().hashCode();
        }
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsolidationKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ConsolidationKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ConsolidationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("index");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Index"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Date"));
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
