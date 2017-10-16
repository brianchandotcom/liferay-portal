/**
 * CommitmentDelayType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CommitmentDelayType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CommitmentDelayType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _HOLIDAY = "HOLIDAY";
    public static final java.lang.String _NON_WORKDAY = "NON_WORKDAY";
    public static final java.lang.String _NO_CITY_DELIVERY = "NO_CITY_DELIVERY";
    public static final java.lang.String _NO_HOLD_AT_LOCATION = "NO_HOLD_AT_LOCATION";
    public static final java.lang.String _NO_LOCATION_DELIVERY = "NO_LOCATION_DELIVERY";
    public static final java.lang.String _NO_SERVICE_AREA_DELIVERY = "NO_SERVICE_AREA_DELIVERY";
    public static final java.lang.String _NO_SERVICE_AREA_SPECIAL_SERVICE_DELIVERY = "NO_SERVICE_AREA_SPECIAL_SERVICE_DELIVERY";
    public static final java.lang.String _NO_SPECIAL_SERVICE_DELIVERY = "NO_SPECIAL_SERVICE_DELIVERY";
    public static final java.lang.String _NO_ZIP_DELIVERY = "NO_ZIP_DELIVERY";
    public static final java.lang.String _WEEKEND = "WEEKEND";
    public static final java.lang.String _WEEKEND_SPECIAL = "WEEKEND_SPECIAL";
    public static final CommitmentDelayType HOLIDAY = new CommitmentDelayType(_HOLIDAY);
    public static final CommitmentDelayType NON_WORKDAY = new CommitmentDelayType(_NON_WORKDAY);
    public static final CommitmentDelayType NO_CITY_DELIVERY = new CommitmentDelayType(_NO_CITY_DELIVERY);
    public static final CommitmentDelayType NO_HOLD_AT_LOCATION = new CommitmentDelayType(_NO_HOLD_AT_LOCATION);
    public static final CommitmentDelayType NO_LOCATION_DELIVERY = new CommitmentDelayType(_NO_LOCATION_DELIVERY);
    public static final CommitmentDelayType NO_SERVICE_AREA_DELIVERY = new CommitmentDelayType(_NO_SERVICE_AREA_DELIVERY);
    public static final CommitmentDelayType NO_SERVICE_AREA_SPECIAL_SERVICE_DELIVERY = new CommitmentDelayType(_NO_SERVICE_AREA_SPECIAL_SERVICE_DELIVERY);
    public static final CommitmentDelayType NO_SPECIAL_SERVICE_DELIVERY = new CommitmentDelayType(_NO_SPECIAL_SERVICE_DELIVERY);
    public static final CommitmentDelayType NO_ZIP_DELIVERY = new CommitmentDelayType(_NO_ZIP_DELIVERY);
    public static final CommitmentDelayType WEEKEND = new CommitmentDelayType(_WEEKEND);
    public static final CommitmentDelayType WEEKEND_SPECIAL = new CommitmentDelayType(_WEEKEND_SPECIAL);
    public java.lang.String getValue() { return _value_;}
    public static CommitmentDelayType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CommitmentDelayType enumeration = (CommitmentDelayType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CommitmentDelayType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CommitmentDelayType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitmentDelayType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
