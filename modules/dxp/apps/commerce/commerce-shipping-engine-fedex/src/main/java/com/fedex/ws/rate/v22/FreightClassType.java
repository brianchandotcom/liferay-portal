/**
 * FreightClassType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class FreightClassType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FreightClassType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CLASS_050 = "CLASS_050";
    public static final java.lang.String _CLASS_055 = "CLASS_055";
    public static final java.lang.String _CLASS_060 = "CLASS_060";
    public static final java.lang.String _CLASS_065 = "CLASS_065";
    public static final java.lang.String _CLASS_070 = "CLASS_070";
    public static final java.lang.String _CLASS_077_5 = "CLASS_077_5";
    public static final java.lang.String _CLASS_085 = "CLASS_085";
    public static final java.lang.String _CLASS_092_5 = "CLASS_092_5";
    public static final java.lang.String _CLASS_100 = "CLASS_100";
    public static final java.lang.String _CLASS_110 = "CLASS_110";
    public static final java.lang.String _CLASS_125 = "CLASS_125";
    public static final java.lang.String _CLASS_150 = "CLASS_150";
    public static final java.lang.String _CLASS_175 = "CLASS_175";
    public static final java.lang.String _CLASS_200 = "CLASS_200";
    public static final java.lang.String _CLASS_250 = "CLASS_250";
    public static final java.lang.String _CLASS_300 = "CLASS_300";
    public static final java.lang.String _CLASS_400 = "CLASS_400";
    public static final java.lang.String _CLASS_500 = "CLASS_500";
    public static final FreightClassType CLASS_050 = new FreightClassType(_CLASS_050);
    public static final FreightClassType CLASS_055 = new FreightClassType(_CLASS_055);
    public static final FreightClassType CLASS_060 = new FreightClassType(_CLASS_060);
    public static final FreightClassType CLASS_065 = new FreightClassType(_CLASS_065);
    public static final FreightClassType CLASS_070 = new FreightClassType(_CLASS_070);
    public static final FreightClassType CLASS_077_5 = new FreightClassType(_CLASS_077_5);
    public static final FreightClassType CLASS_085 = new FreightClassType(_CLASS_085);
    public static final FreightClassType CLASS_092_5 = new FreightClassType(_CLASS_092_5);
    public static final FreightClassType CLASS_100 = new FreightClassType(_CLASS_100);
    public static final FreightClassType CLASS_110 = new FreightClassType(_CLASS_110);
    public static final FreightClassType CLASS_125 = new FreightClassType(_CLASS_125);
    public static final FreightClassType CLASS_150 = new FreightClassType(_CLASS_150);
    public static final FreightClassType CLASS_175 = new FreightClassType(_CLASS_175);
    public static final FreightClassType CLASS_200 = new FreightClassType(_CLASS_200);
    public static final FreightClassType CLASS_250 = new FreightClassType(_CLASS_250);
    public static final FreightClassType CLASS_300 = new FreightClassType(_CLASS_300);
    public static final FreightClassType CLASS_400 = new FreightClassType(_CLASS_400);
    public static final FreightClassType CLASS_500 = new FreightClassType(_CLASS_500);
    public java.lang.String getValue() { return _value_;}
    public static FreightClassType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FreightClassType enumeration = (FreightClassType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FreightClassType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FreightClassType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightClassType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
