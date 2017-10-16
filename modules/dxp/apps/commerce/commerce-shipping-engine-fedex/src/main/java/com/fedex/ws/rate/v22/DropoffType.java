/**
 * DropoffType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class DropoffType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DropoffType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BUSINESS_SERVICE_CENTER = "BUSINESS_SERVICE_CENTER";
    public static final java.lang.String _DROP_BOX = "DROP_BOX";
    public static final java.lang.String _REGULAR_PICKUP = "REGULAR_PICKUP";
    public static final java.lang.String _REQUEST_COURIER = "REQUEST_COURIER";
    public static final java.lang.String _STATION = "STATION";
    public static final DropoffType BUSINESS_SERVICE_CENTER = new DropoffType(_BUSINESS_SERVICE_CENTER);
    public static final DropoffType DROP_BOX = new DropoffType(_DROP_BOX);
    public static final DropoffType REGULAR_PICKUP = new DropoffType(_REGULAR_PICKUP);
    public static final DropoffType REQUEST_COURIER = new DropoffType(_REQUEST_COURIER);
    public static final DropoffType STATION = new DropoffType(_STATION);
    public java.lang.String getValue() { return _value_;}
    public static DropoffType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DropoffType enumeration = (DropoffType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DropoffType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DropoffType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DropoffType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
