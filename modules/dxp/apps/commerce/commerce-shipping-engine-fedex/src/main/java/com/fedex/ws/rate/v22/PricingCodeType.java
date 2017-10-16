/**
 * PricingCodeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class PricingCodeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PricingCodeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ACTUAL = "ACTUAL";
    public static final java.lang.String _ALTERNATE = "ALTERNATE";
    public static final java.lang.String _BASE = "BASE";
    public static final java.lang.String _HUNDREDWEIGHT = "HUNDREDWEIGHT";
    public static final java.lang.String _HUNDREDWEIGHT_ALTERNATE = "HUNDREDWEIGHT_ALTERNATE";
    public static final java.lang.String _INTERNATIONAL_DISTRIBUTION = "INTERNATIONAL_DISTRIBUTION";
    public static final java.lang.String _INTERNATIONAL_ECONOMY_SERVICE = "INTERNATIONAL_ECONOMY_SERVICE";
    public static final java.lang.String _LTL_FREIGHT = "LTL_FREIGHT";
    public static final java.lang.String _PACKAGE = "PACKAGE";
    public static final java.lang.String _SHIPMENT = "SHIPMENT";
    public static final java.lang.String _SHIPMENT_FIVE_POUND_OPTIONAL = "SHIPMENT_FIVE_POUND_OPTIONAL";
    public static final java.lang.String _SHIPMENT_OPTIONAL = "SHIPMENT_OPTIONAL";
    public static final java.lang.String _SPECIAL = "SPECIAL";
    public static final PricingCodeType ACTUAL = new PricingCodeType(_ACTUAL);
    public static final PricingCodeType ALTERNATE = new PricingCodeType(_ALTERNATE);
    public static final PricingCodeType BASE = new PricingCodeType(_BASE);
    public static final PricingCodeType HUNDREDWEIGHT = new PricingCodeType(_HUNDREDWEIGHT);
    public static final PricingCodeType HUNDREDWEIGHT_ALTERNATE = new PricingCodeType(_HUNDREDWEIGHT_ALTERNATE);
    public static final PricingCodeType INTERNATIONAL_DISTRIBUTION = new PricingCodeType(_INTERNATIONAL_DISTRIBUTION);
    public static final PricingCodeType INTERNATIONAL_ECONOMY_SERVICE = new PricingCodeType(_INTERNATIONAL_ECONOMY_SERVICE);
    public static final PricingCodeType LTL_FREIGHT = new PricingCodeType(_LTL_FREIGHT);
    public static final PricingCodeType PACKAGE = new PricingCodeType(_PACKAGE);
    public static final PricingCodeType SHIPMENT = new PricingCodeType(_SHIPMENT);
    public static final PricingCodeType SHIPMENT_FIVE_POUND_OPTIONAL = new PricingCodeType(_SHIPMENT_FIVE_POUND_OPTIONAL);
    public static final PricingCodeType SHIPMENT_OPTIONAL = new PricingCodeType(_SHIPMENT_OPTIONAL);
    public static final PricingCodeType SPECIAL = new PricingCodeType(_SPECIAL);
    public java.lang.String getValue() { return _value_;}
    public static PricingCodeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PricingCodeType enumeration = (PricingCodeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PricingCodeType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PricingCodeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PricingCodeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
