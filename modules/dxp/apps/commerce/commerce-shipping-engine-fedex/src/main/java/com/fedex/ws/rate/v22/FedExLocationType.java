/**
 * FedExLocationType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class FedExLocationType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected FedExLocationType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_EXPRESS_STATION = "FEDEX_EXPRESS_STATION";
    public static final java.lang.String _FEDEX_FACILITY = "FEDEX_FACILITY";
    public static final java.lang.String _FEDEX_FREIGHT_SERVICE_CENTER = "FEDEX_FREIGHT_SERVICE_CENTER";
    public static final java.lang.String _FEDEX_GROUND_TERMINAL = "FEDEX_GROUND_TERMINAL";
    public static final java.lang.String _FEDEX_HOME_DELIVERY_STATION = "FEDEX_HOME_DELIVERY_STATION";
    public static final java.lang.String _FEDEX_OFFICE = "FEDEX_OFFICE";
    public static final java.lang.String _FEDEX_SHIPSITE = "FEDEX_SHIPSITE";
    public static final java.lang.String _FEDEX_SMART_POST_HUB = "FEDEX_SMART_POST_HUB";
    public static final FedExLocationType FEDEX_EXPRESS_STATION = new FedExLocationType(_FEDEX_EXPRESS_STATION);
    public static final FedExLocationType FEDEX_FACILITY = new FedExLocationType(_FEDEX_FACILITY);
    public static final FedExLocationType FEDEX_FREIGHT_SERVICE_CENTER = new FedExLocationType(_FEDEX_FREIGHT_SERVICE_CENTER);
    public static final FedExLocationType FEDEX_GROUND_TERMINAL = new FedExLocationType(_FEDEX_GROUND_TERMINAL);
    public static final FedExLocationType FEDEX_HOME_DELIVERY_STATION = new FedExLocationType(_FEDEX_HOME_DELIVERY_STATION);
    public static final FedExLocationType FEDEX_OFFICE = new FedExLocationType(_FEDEX_OFFICE);
    public static final FedExLocationType FEDEX_SHIPSITE = new FedExLocationType(_FEDEX_SHIPSITE);
    public static final FedExLocationType FEDEX_SMART_POST_HUB = new FedExLocationType(_FEDEX_SMART_POST_HUB);
    public java.lang.String getValue() { return _value_;}
    public static FedExLocationType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        FedExLocationType enumeration = (FedExLocationType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static FedExLocationType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(FedExLocationType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FedExLocationType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
