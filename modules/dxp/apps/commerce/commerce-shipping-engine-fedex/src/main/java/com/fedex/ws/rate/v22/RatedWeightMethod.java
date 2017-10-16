/**
 * RatedWeightMethod.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RatedWeightMethod implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RatedWeightMethod(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ACTUAL = "ACTUAL";
    public static final java.lang.String _AVERAGE_PACKAGE_WEIGHT_MINIMUM = "AVERAGE_PACKAGE_WEIGHT_MINIMUM";
    public static final java.lang.String _BALLOON = "BALLOON";
    public static final java.lang.String _DEFAULT_WEIGHT_APPLIED = "DEFAULT_WEIGHT_APPLIED";
    public static final java.lang.String _DIM = "DIM";
    public static final java.lang.String _FREIGHT_MINIMUM = "FREIGHT_MINIMUM";
    public static final java.lang.String _MIXED = "MIXED";
    public static final java.lang.String _OVERSIZE = "OVERSIZE";
    public static final java.lang.String _OVERSIZE_1 = "OVERSIZE_1";
    public static final java.lang.String _OVERSIZE_2 = "OVERSIZE_2";
    public static final java.lang.String _OVERSIZE_3 = "OVERSIZE_3";
    public static final java.lang.String _PACKAGING_MINIMUM = "PACKAGING_MINIMUM";
    public static final java.lang.String _WEIGHT_BREAK = "WEIGHT_BREAK";
    public static final RatedWeightMethod ACTUAL = new RatedWeightMethod(_ACTUAL);
    public static final RatedWeightMethod AVERAGE_PACKAGE_WEIGHT_MINIMUM = new RatedWeightMethod(_AVERAGE_PACKAGE_WEIGHT_MINIMUM);
    public static final RatedWeightMethod BALLOON = new RatedWeightMethod(_BALLOON);
    public static final RatedWeightMethod DEFAULT_WEIGHT_APPLIED = new RatedWeightMethod(_DEFAULT_WEIGHT_APPLIED);
    public static final RatedWeightMethod DIM = new RatedWeightMethod(_DIM);
    public static final RatedWeightMethod FREIGHT_MINIMUM = new RatedWeightMethod(_FREIGHT_MINIMUM);
    public static final RatedWeightMethod MIXED = new RatedWeightMethod(_MIXED);
    public static final RatedWeightMethod OVERSIZE = new RatedWeightMethod(_OVERSIZE);
    public static final RatedWeightMethod OVERSIZE_1 = new RatedWeightMethod(_OVERSIZE_1);
    public static final RatedWeightMethod OVERSIZE_2 = new RatedWeightMethod(_OVERSIZE_2);
    public static final RatedWeightMethod OVERSIZE_3 = new RatedWeightMethod(_OVERSIZE_3);
    public static final RatedWeightMethod PACKAGING_MINIMUM = new RatedWeightMethod(_PACKAGING_MINIMUM);
    public static final RatedWeightMethod WEIGHT_BREAK = new RatedWeightMethod(_WEIGHT_BREAK);
    public java.lang.String getValue() { return _value_;}
    public static RatedWeightMethod fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RatedWeightMethod enumeration = (RatedWeightMethod)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RatedWeightMethod fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RatedWeightMethod.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RatedWeightMethod"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
