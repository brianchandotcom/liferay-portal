/**
 * DocTabContentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class DocTabContentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DocTabContentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BARCODED = "BARCODED";
    public static final java.lang.String _CUSTOM = "CUSTOM";
    public static final java.lang.String _MINIMUM = "MINIMUM";
    public static final java.lang.String _STANDARD = "STANDARD";
    public static final java.lang.String _ZONE001 = "ZONE001";
    public static final DocTabContentType BARCODED = new DocTabContentType(_BARCODED);
    public static final DocTabContentType CUSTOM = new DocTabContentType(_CUSTOM);
    public static final DocTabContentType MINIMUM = new DocTabContentType(_MINIMUM);
    public static final DocTabContentType STANDARD = new DocTabContentType(_STANDARD);
    public static final DocTabContentType ZONE001 = new DocTabContentType(_ZONE001);
    public java.lang.String getValue() { return _value_;}
    public static DocTabContentType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DocTabContentType enumeration = (DocTabContentType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DocTabContentType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DocTabContentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocTabContentType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
