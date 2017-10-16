/**
 * DelayLevelType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class DelayLevelType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DelayLevelType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CITY = "CITY";
    public static final java.lang.String _COUNTRY = "COUNTRY";
    public static final java.lang.String _LOCATION = "LOCATION";
    public static final java.lang.String _POSTAL_CODE = "POSTAL_CODE";
    public static final java.lang.String _SERVICE_AREA = "SERVICE_AREA";
    public static final java.lang.String _SERVICE_AREA_SPECIAL_SERVICE = "SERVICE_AREA_SPECIAL_SERVICE";
    public static final java.lang.String _SPECIAL_SERVICE = "SPECIAL_SERVICE";
    public static final DelayLevelType CITY = new DelayLevelType(_CITY);
    public static final DelayLevelType COUNTRY = new DelayLevelType(_COUNTRY);
    public static final DelayLevelType LOCATION = new DelayLevelType(_LOCATION);
    public static final DelayLevelType POSTAL_CODE = new DelayLevelType(_POSTAL_CODE);
    public static final DelayLevelType SERVICE_AREA = new DelayLevelType(_SERVICE_AREA);
    public static final DelayLevelType SERVICE_AREA_SPECIAL_SERVICE = new DelayLevelType(_SERVICE_AREA_SPECIAL_SERVICE);
    public static final DelayLevelType SPECIAL_SERVICE = new DelayLevelType(_SPECIAL_SERVICE);
    public java.lang.String getValue() { return _value_;}
    public static DelayLevelType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DelayLevelType enumeration = (DelayLevelType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DelayLevelType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DelayLevelType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DelayLevelType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
