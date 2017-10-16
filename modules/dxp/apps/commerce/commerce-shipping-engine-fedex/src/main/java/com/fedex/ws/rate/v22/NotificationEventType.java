/**
 * NotificationEventType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class NotificationEventType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected NotificationEventType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ON_DELIVERY = "ON_DELIVERY";
    public static final java.lang.String _ON_ESTIMATED_DELIVERY = "ON_ESTIMATED_DELIVERY";
    public static final java.lang.String _ON_EXCEPTION = "ON_EXCEPTION";
    public static final java.lang.String _ON_SHIPMENT = "ON_SHIPMENT";
    public static final java.lang.String _ON_TENDER = "ON_TENDER";
    public static final NotificationEventType ON_DELIVERY = new NotificationEventType(_ON_DELIVERY);
    public static final NotificationEventType ON_ESTIMATED_DELIVERY = new NotificationEventType(_ON_ESTIMATED_DELIVERY);
    public static final NotificationEventType ON_EXCEPTION = new NotificationEventType(_ON_EXCEPTION);
    public static final NotificationEventType ON_SHIPMENT = new NotificationEventType(_ON_SHIPMENT);
    public static final NotificationEventType ON_TENDER = new NotificationEventType(_ON_TENDER);
    public java.lang.String getValue() { return _value_;}
    public static NotificationEventType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        NotificationEventType enumeration = (NotificationEventType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static NotificationEventType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(NotificationEventType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationEventType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
