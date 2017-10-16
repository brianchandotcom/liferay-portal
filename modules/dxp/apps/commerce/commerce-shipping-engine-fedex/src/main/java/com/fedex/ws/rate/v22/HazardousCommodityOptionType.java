/**
 * HazardousCommodityOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class HazardousCommodityOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected HazardousCommodityOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BATTERY = "BATTERY";
    public static final java.lang.String _HAZARDOUS_MATERIALS = "HAZARDOUS_MATERIALS";
    public static final java.lang.String _LIMITED_QUANTITIES_COMMODITIES = "LIMITED_QUANTITIES_COMMODITIES";
    public static final java.lang.String _ORM_D = "ORM_D";
    public static final java.lang.String _REPORTABLE_QUANTITIES = "REPORTABLE_QUANTITIES";
    public static final java.lang.String _SMALL_QUANTITY_EXCEPTION = "SMALL_QUANTITY_EXCEPTION";
    public static final HazardousCommodityOptionType BATTERY = new HazardousCommodityOptionType(_BATTERY);
    public static final HazardousCommodityOptionType HAZARDOUS_MATERIALS = new HazardousCommodityOptionType(_HAZARDOUS_MATERIALS);
    public static final HazardousCommodityOptionType LIMITED_QUANTITIES_COMMODITIES = new HazardousCommodityOptionType(_LIMITED_QUANTITIES_COMMODITIES);
    public static final HazardousCommodityOptionType ORM_D = new HazardousCommodityOptionType(_ORM_D);
    public static final HazardousCommodityOptionType REPORTABLE_QUANTITIES = new HazardousCommodityOptionType(_REPORTABLE_QUANTITIES);
    public static final HazardousCommodityOptionType SMALL_QUANTITY_EXCEPTION = new HazardousCommodityOptionType(_SMALL_QUANTITY_EXCEPTION);
    public java.lang.String getValue() { return _value_;}
    public static HazardousCommodityOptionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        HazardousCommodityOptionType enumeration = (HazardousCommodityOptionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static HazardousCommodityOptionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(HazardousCommodityOptionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodityOptionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
