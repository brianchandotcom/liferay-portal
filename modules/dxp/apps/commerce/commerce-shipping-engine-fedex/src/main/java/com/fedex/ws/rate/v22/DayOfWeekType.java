/**
 * DayOfWeekType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class DayOfWeekType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DayOfWeekType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FRI = "FRI";
    public static final java.lang.String _MON = "MON";
    public static final java.lang.String _SAT = "SAT";
    public static final java.lang.String _SUN = "SUN";
    public static final java.lang.String _THU = "THU";
    public static final java.lang.String _TUE = "TUE";
    public static final java.lang.String _WED = "WED";
    public static final DayOfWeekType FRI = new DayOfWeekType(_FRI);
    public static final DayOfWeekType MON = new DayOfWeekType(_MON);
    public static final DayOfWeekType SAT = new DayOfWeekType(_SAT);
    public static final DayOfWeekType SUN = new DayOfWeekType(_SUN);
    public static final DayOfWeekType THU = new DayOfWeekType(_THU);
    public static final DayOfWeekType TUE = new DayOfWeekType(_TUE);
    public static final DayOfWeekType WED = new DayOfWeekType(_WED);
    public java.lang.String getValue() { return _value_;}
    public static DayOfWeekType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DayOfWeekType enumeration = (DayOfWeekType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DayOfWeekType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DayOfWeekType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
