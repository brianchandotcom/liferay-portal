/**
 * CustomerSpecifiedLabelGenerationOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CustomerSpecifiedLabelGenerationOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomerSpecifiedLabelGenerationOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CONTENT_ON_SHIPPING_LABEL_ONLY = "CONTENT_ON_SHIPPING_LABEL_ONLY";
    public static final java.lang.String _CONTENT_ON_SHIPPING_LABEL_PREFERRED = "CONTENT_ON_SHIPPING_LABEL_PREFERRED";
    public static final java.lang.String _CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY = "CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY";
    public static final CustomerSpecifiedLabelGenerationOptionType CONTENT_ON_SHIPPING_LABEL_ONLY = new CustomerSpecifiedLabelGenerationOptionType(_CONTENT_ON_SHIPPING_LABEL_ONLY);
    public static final CustomerSpecifiedLabelGenerationOptionType CONTENT_ON_SHIPPING_LABEL_PREFERRED = new CustomerSpecifiedLabelGenerationOptionType(_CONTENT_ON_SHIPPING_LABEL_PREFERRED);
    public static final CustomerSpecifiedLabelGenerationOptionType CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY = new CustomerSpecifiedLabelGenerationOptionType(_CONTENT_ON_SUPPLEMENTAL_LABEL_ONLY);
    public java.lang.String getValue() { return _value_;}
    public static CustomerSpecifiedLabelGenerationOptionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CustomerSpecifiedLabelGenerationOptionType enumeration = (CustomerSpecifiedLabelGenerationOptionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CustomerSpecifiedLabelGenerationOptionType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomerSpecifiedLabelGenerationOptionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerSpecifiedLabelGenerationOptionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
