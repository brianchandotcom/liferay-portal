/**
 * CarrierCodeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CarrierCodeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CarrierCodeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FDXC = "FDXC";
    public static final java.lang.String _FDXE = "FDXE";
    public static final java.lang.String _FDXG = "FDXG";
    public static final java.lang.String _FXCC = "FXCC";
    public static final java.lang.String _FXFR = "FXFR";
    public static final java.lang.String _FXSP = "FXSP";
    public static final CarrierCodeType FDXC = new CarrierCodeType(_FDXC);
    public static final CarrierCodeType FDXE = new CarrierCodeType(_FDXE);
    public static final CarrierCodeType FDXG = new CarrierCodeType(_FDXG);
    public static final CarrierCodeType FXCC = new CarrierCodeType(_FXCC);
    public static final CarrierCodeType FXFR = new CarrierCodeType(_FXFR);
    public static final CarrierCodeType FXSP = new CarrierCodeType(_FXSP);
    public java.lang.String getValue() { return _value_;}
    public static CarrierCodeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CarrierCodeType enumeration = (CarrierCodeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CarrierCodeType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CarrierCodeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CarrierCodeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
