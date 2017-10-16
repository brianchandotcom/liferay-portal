/**
 * ServiceOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class ServiceOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ServiceOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_ONE_RATE = "FEDEX_ONE_RATE";
    public static final java.lang.String _FREIGHT_GUARANTEE = "FREIGHT_GUARANTEE";
    public static final java.lang.String _SATURDAY_DELIVERY = "SATURDAY_DELIVERY";
    public static final java.lang.String _SMART_POST_ALLOWED_INDICIA = "SMART_POST_ALLOWED_INDICIA";
    public static final java.lang.String _SMART_POST_HUB_ID = "SMART_POST_HUB_ID";
    public static final ServiceOptionType FEDEX_ONE_RATE = new ServiceOptionType(_FEDEX_ONE_RATE);
    public static final ServiceOptionType FREIGHT_GUARANTEE = new ServiceOptionType(_FREIGHT_GUARANTEE);
    public static final ServiceOptionType SATURDAY_DELIVERY = new ServiceOptionType(_SATURDAY_DELIVERY);
    public static final ServiceOptionType SMART_POST_ALLOWED_INDICIA = new ServiceOptionType(_SMART_POST_ALLOWED_INDICIA);
    public static final ServiceOptionType SMART_POST_HUB_ID = new ServiceOptionType(_SMART_POST_HUB_ID);
    public java.lang.String getValue() { return _value_;}
    public static ServiceOptionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ServiceOptionType enumeration = (ServiceOptionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ServiceOptionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ServiceOptionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceOptionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
