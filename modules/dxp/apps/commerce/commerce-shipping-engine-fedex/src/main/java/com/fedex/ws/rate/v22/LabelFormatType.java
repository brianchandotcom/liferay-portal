/**
 * LabelFormatType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class LabelFormatType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelFormatType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COMMON2D = "COMMON2D";
    public static final java.lang.String _LABEL_DATA_ONLY = "LABEL_DATA_ONLY";
    public static final java.lang.String _MAILROOM = "MAILROOM";
    public static final java.lang.String _NO_LABEL = "NO_LABEL";
    public static final java.lang.String _OPERATIONAL_LABEL = "OPERATIONAL_LABEL";
    public static final java.lang.String _PRE_COMMON2D = "PRE_COMMON2D";
    public static final LabelFormatType COMMON2D = new LabelFormatType(_COMMON2D);
    public static final LabelFormatType LABEL_DATA_ONLY = new LabelFormatType(_LABEL_DATA_ONLY);
    public static final LabelFormatType MAILROOM = new LabelFormatType(_MAILROOM);
    public static final LabelFormatType NO_LABEL = new LabelFormatType(_NO_LABEL);
    public static final LabelFormatType OPERATIONAL_LABEL = new LabelFormatType(_OPERATIONAL_LABEL);
    public static final LabelFormatType PRE_COMMON2D = new LabelFormatType(_PRE_COMMON2D);
    public java.lang.String getValue() { return _value_;}
    public static LabelFormatType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        LabelFormatType enumeration = (LabelFormatType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static LabelFormatType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(LabelFormatType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LabelFormatType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
