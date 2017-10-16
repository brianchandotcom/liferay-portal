/**
 * RateDimensionalDivisorType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RateDimensionalDivisorType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RateDimensionalDivisorType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COUNTRY = "COUNTRY";
    public static final java.lang.String _CUSTOMER = "CUSTOMER";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _PRODUCT = "PRODUCT";
    public static final java.lang.String _WAIVED = "WAIVED";
    public static final RateDimensionalDivisorType COUNTRY = new RateDimensionalDivisorType(_COUNTRY);
    public static final RateDimensionalDivisorType CUSTOMER = new RateDimensionalDivisorType(_CUSTOMER);
    public static final RateDimensionalDivisorType OTHER = new RateDimensionalDivisorType(_OTHER);
    public static final RateDimensionalDivisorType PRODUCT = new RateDimensionalDivisorType(_PRODUCT);
    public static final RateDimensionalDivisorType WAIVED = new RateDimensionalDivisorType(_WAIVED);
    public java.lang.String getValue() { return _value_;}
    public static RateDimensionalDivisorType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RateDimensionalDivisorType enumeration = (RateDimensionalDivisorType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RateDimensionalDivisorType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RateDimensionalDivisorType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RateDimensionalDivisorType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
