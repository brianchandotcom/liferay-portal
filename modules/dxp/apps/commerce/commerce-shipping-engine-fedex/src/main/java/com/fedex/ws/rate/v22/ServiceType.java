/**
 * ServiceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class ServiceType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ServiceType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EUROPE_FIRST_INTERNATIONAL_PRIORITY = "EUROPE_FIRST_INTERNATIONAL_PRIORITY";
    public static final java.lang.String _FEDEX_1_DAY_FREIGHT = "FEDEX_1_DAY_FREIGHT";
    public static final java.lang.String _FEDEX_2_DAY = "FEDEX_2_DAY";
    public static final java.lang.String _FEDEX_2_DAY_AM = "FEDEX_2_DAY_AM";
    public static final java.lang.String _FEDEX_2_DAY_FREIGHT = "FEDEX_2_DAY_FREIGHT";
    public static final java.lang.String _FEDEX_3_DAY_FREIGHT = "FEDEX_3_DAY_FREIGHT";
    public static final java.lang.String _FEDEX_DISTANCE_DEFERRED = "FEDEX_DISTANCE_DEFERRED";
    public static final java.lang.String _FEDEX_EXPRESS_SAVER = "FEDEX_EXPRESS_SAVER";
    public static final java.lang.String _FEDEX_FIRST_FREIGHT = "FEDEX_FIRST_FREIGHT";
    public static final java.lang.String _FEDEX_FREIGHT_ECONOMY = "FEDEX_FREIGHT_ECONOMY";
    public static final java.lang.String _FEDEX_FREIGHT_PRIORITY = "FEDEX_FREIGHT_PRIORITY";
    public static final java.lang.String _FEDEX_GROUND = "FEDEX_GROUND";
    public static final java.lang.String _FEDEX_NEXT_DAY_AFTERNOON = "FEDEX_NEXT_DAY_AFTERNOON";
    public static final java.lang.String _FEDEX_NEXT_DAY_EARLY_MORNING = "FEDEX_NEXT_DAY_EARLY_MORNING";
    public static final java.lang.String _FEDEX_NEXT_DAY_END_OF_DAY = "FEDEX_NEXT_DAY_END_OF_DAY";
    public static final java.lang.String _FEDEX_NEXT_DAY_FREIGHT = "FEDEX_NEXT_DAY_FREIGHT";
    public static final java.lang.String _FEDEX_NEXT_DAY_MID_MORNING = "FEDEX_NEXT_DAY_MID_MORNING";
    public static final java.lang.String _FIRST_OVERNIGHT = "FIRST_OVERNIGHT";
    public static final java.lang.String _GROUND_HOME_DELIVERY = "GROUND_HOME_DELIVERY";
    public static final java.lang.String _INTERNATIONAL_ECONOMY = "INTERNATIONAL_ECONOMY";
    public static final java.lang.String _INTERNATIONAL_ECONOMY_FREIGHT = "INTERNATIONAL_ECONOMY_FREIGHT";
    public static final java.lang.String _INTERNATIONAL_FIRST = "INTERNATIONAL_FIRST";
    public static final java.lang.String _INTERNATIONAL_PRIORITY = "INTERNATIONAL_PRIORITY";
    public static final java.lang.String _INTERNATIONAL_PRIORITY_EXPRESS = "INTERNATIONAL_PRIORITY_EXPRESS";
    public static final java.lang.String _INTERNATIONAL_PRIORITY_FREIGHT = "INTERNATIONAL_PRIORITY_FREIGHT";
    public static final java.lang.String _PRIORITY_OVERNIGHT = "PRIORITY_OVERNIGHT";
    public static final java.lang.String _SAME_DAY = "SAME_DAY";
    public static final java.lang.String _SAME_DAY_CITY = "SAME_DAY_CITY";
    public static final java.lang.String _SMART_POST = "SMART_POST";
    public static final java.lang.String _STANDARD_OVERNIGHT = "STANDARD_OVERNIGHT";
    public static final ServiceType EUROPE_FIRST_INTERNATIONAL_PRIORITY = new ServiceType(_EUROPE_FIRST_INTERNATIONAL_PRIORITY);
    public static final ServiceType FEDEX_1_DAY_FREIGHT = new ServiceType(_FEDEX_1_DAY_FREIGHT);
    public static final ServiceType FEDEX_2_DAY = new ServiceType(_FEDEX_2_DAY);
    public static final ServiceType FEDEX_2_DAY_AM = new ServiceType(_FEDEX_2_DAY_AM);
    public static final ServiceType FEDEX_2_DAY_FREIGHT = new ServiceType(_FEDEX_2_DAY_FREIGHT);
    public static final ServiceType FEDEX_3_DAY_FREIGHT = new ServiceType(_FEDEX_3_DAY_FREIGHT);
    public static final ServiceType FEDEX_DISTANCE_DEFERRED = new ServiceType(_FEDEX_DISTANCE_DEFERRED);
    public static final ServiceType FEDEX_EXPRESS_SAVER = new ServiceType(_FEDEX_EXPRESS_SAVER);
    public static final ServiceType FEDEX_FIRST_FREIGHT = new ServiceType(_FEDEX_FIRST_FREIGHT);
    public static final ServiceType FEDEX_FREIGHT_ECONOMY = new ServiceType(_FEDEX_FREIGHT_ECONOMY);
    public static final ServiceType FEDEX_FREIGHT_PRIORITY = new ServiceType(_FEDEX_FREIGHT_PRIORITY);
    public static final ServiceType FEDEX_GROUND = new ServiceType(_FEDEX_GROUND);
    public static final ServiceType FEDEX_NEXT_DAY_AFTERNOON = new ServiceType(_FEDEX_NEXT_DAY_AFTERNOON);
    public static final ServiceType FEDEX_NEXT_DAY_EARLY_MORNING = new ServiceType(_FEDEX_NEXT_DAY_EARLY_MORNING);
    public static final ServiceType FEDEX_NEXT_DAY_END_OF_DAY = new ServiceType(_FEDEX_NEXT_DAY_END_OF_DAY);
    public static final ServiceType FEDEX_NEXT_DAY_FREIGHT = new ServiceType(_FEDEX_NEXT_DAY_FREIGHT);
    public static final ServiceType FEDEX_NEXT_DAY_MID_MORNING = new ServiceType(_FEDEX_NEXT_DAY_MID_MORNING);
    public static final ServiceType FIRST_OVERNIGHT = new ServiceType(_FIRST_OVERNIGHT);
    public static final ServiceType GROUND_HOME_DELIVERY = new ServiceType(_GROUND_HOME_DELIVERY);
    public static final ServiceType INTERNATIONAL_ECONOMY = new ServiceType(_INTERNATIONAL_ECONOMY);
    public static final ServiceType INTERNATIONAL_ECONOMY_FREIGHT = new ServiceType(_INTERNATIONAL_ECONOMY_FREIGHT);
    public static final ServiceType INTERNATIONAL_FIRST = new ServiceType(_INTERNATIONAL_FIRST);
    public static final ServiceType INTERNATIONAL_PRIORITY = new ServiceType(_INTERNATIONAL_PRIORITY);
    public static final ServiceType INTERNATIONAL_PRIORITY_EXPRESS = new ServiceType(_INTERNATIONAL_PRIORITY_EXPRESS);
    public static final ServiceType INTERNATIONAL_PRIORITY_FREIGHT = new ServiceType(_INTERNATIONAL_PRIORITY_FREIGHT);
    public static final ServiceType PRIORITY_OVERNIGHT = new ServiceType(_PRIORITY_OVERNIGHT);
    public static final ServiceType SAME_DAY = new ServiceType(_SAME_DAY);
    public static final ServiceType SAME_DAY_CITY = new ServiceType(_SAME_DAY_CITY);
    public static final ServiceType SMART_POST = new ServiceType(_SMART_POST);
    public static final ServiceType STANDARD_OVERNIGHT = new ServiceType(_STANDARD_OVERNIGHT);
    public java.lang.String getValue() { return _value_;}
    public static ServiceType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ServiceType enumeration = (ServiceType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ServiceType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ServiceType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
