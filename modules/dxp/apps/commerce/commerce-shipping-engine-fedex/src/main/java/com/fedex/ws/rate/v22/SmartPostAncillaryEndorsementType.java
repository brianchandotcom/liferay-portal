/**
 * SmartPostAncillaryEndorsementType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class SmartPostAncillaryEndorsementType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SmartPostAncillaryEndorsementType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ADDRESS_CORRECTION = "ADDRESS_CORRECTION";
    public static final java.lang.String _CARRIER_LEAVE_IF_NO_RESPONSE = "CARRIER_LEAVE_IF_NO_RESPONSE";
    public static final java.lang.String _CHANGE_SERVICE = "CHANGE_SERVICE";
    public static final java.lang.String _FORWARDING_SERVICE = "FORWARDING_SERVICE";
    public static final java.lang.String _RETURN_SERVICE = "RETURN_SERVICE";
    public static final SmartPostAncillaryEndorsementType ADDRESS_CORRECTION = new SmartPostAncillaryEndorsementType(_ADDRESS_CORRECTION);
    public static final SmartPostAncillaryEndorsementType CARRIER_LEAVE_IF_NO_RESPONSE = new SmartPostAncillaryEndorsementType(_CARRIER_LEAVE_IF_NO_RESPONSE);
    public static final SmartPostAncillaryEndorsementType CHANGE_SERVICE = new SmartPostAncillaryEndorsementType(_CHANGE_SERVICE);
    public static final SmartPostAncillaryEndorsementType FORWARDING_SERVICE = new SmartPostAncillaryEndorsementType(_FORWARDING_SERVICE);
    public static final SmartPostAncillaryEndorsementType RETURN_SERVICE = new SmartPostAncillaryEndorsementType(_RETURN_SERVICE);
    public java.lang.String getValue() { return _value_;}
    public static SmartPostAncillaryEndorsementType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SmartPostAncillaryEndorsementType enumeration = (SmartPostAncillaryEndorsementType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SmartPostAncillaryEndorsementType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SmartPostAncillaryEndorsementType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SmartPostAncillaryEndorsementType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
