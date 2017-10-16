/**
 * RecipientCustomsId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Specifies how the recipient is identified for customs purposes;
 * the requirements on this information vary with destination country.
 */
public class RecipientCustomsId  implements java.io.Serializable {
    /* Specifies the kind of identification being used. */
    private com.fedex.ws.rate.v22.RecipientCustomsIdType type;

    /* Contains the actual ID value, of the type specified above. */
    private java.lang.String value;

    public RecipientCustomsId() {
    }

    public RecipientCustomsId(
           com.fedex.ws.rate.v22.RecipientCustomsIdType type,
           java.lang.String value) {
           this.type = type;
           this.value = value;
    }


    /**
     * Gets the type value for this RecipientCustomsId.
     * 
     * @return type   * Specifies the kind of identification being used.
     */
    public com.fedex.ws.rate.v22.RecipientCustomsIdType getType() {
        return type;
    }


    /**
     * Sets the type value for this RecipientCustomsId.
     * 
     * @param type   * Specifies the kind of identification being used.
     */
    public void setType(com.fedex.ws.rate.v22.RecipientCustomsIdType type) {
        this.type = type;
    }


    /**
     * Gets the value value for this RecipientCustomsId.
     * 
     * @return value   * Contains the actual ID value, of the type specified above.
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this RecipientCustomsId.
     * 
     * @param value   * Contains the actual ID value, of the type specified above.
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecipientCustomsId)) return false;
        RecipientCustomsId other = (RecipientCustomsId) obj;
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
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue())));
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
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RecipientCustomsId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RecipientCustomsId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RecipientCustomsIdType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
