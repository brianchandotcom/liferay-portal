/**
 * NaftaProducerDeterminationCode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class NaftaProducerDeterminationCode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NaftaProducerDeterminationCode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NO_1 = "NO_1";
    public static final java.lang.String _NO_2 = "NO_2";
    public static final java.lang.String _NO_3 = "NO_3";
    public static final java.lang.String _YES = "YES";
    public static final NaftaProducerDeterminationCode NO_1 = new NaftaProducerDeterminationCode(_NO_1);
    public static final NaftaProducerDeterminationCode NO_2 = new NaftaProducerDeterminationCode(_NO_2);
    public static final NaftaProducerDeterminationCode NO_3 = new NaftaProducerDeterminationCode(_NO_3);
    public static final NaftaProducerDeterminationCode YES = new NaftaProducerDeterminationCode(_YES);
    public java.lang.String getValue() { return _value_;}
    public static NaftaProducerDeterminationCode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        NaftaProducerDeterminationCode enumeration = (NaftaProducerDeterminationCode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static NaftaProducerDeterminationCode fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(NaftaProducerDeterminationCode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaProducerDeterminationCode"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
