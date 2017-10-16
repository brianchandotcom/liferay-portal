/**
 * RegulatoryControlType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RegulatoryControlType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RegulatoryControlType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EU_CIRCULATION = "EU_CIRCULATION";
    public static final java.lang.String _FOOD_OR_PERISHABLE = "FOOD_OR_PERISHABLE";
    public static final java.lang.String _NAFTA = "NAFTA";
    public static final java.lang.String _NOT_APPLICABLE_FOR_LOW_CUSTOMS_VALUE_EXCEPTION = "NOT_APPLICABLE_FOR_LOW_CUSTOMS_VALUE_EXCEPTION";
    public static final java.lang.String _NOT_IN_FREE_CIRCULATION = "NOT_IN_FREE_CIRCULATION";
    public static final RegulatoryControlType EU_CIRCULATION = new RegulatoryControlType(_EU_CIRCULATION);
    public static final RegulatoryControlType FOOD_OR_PERISHABLE = new RegulatoryControlType(_FOOD_OR_PERISHABLE);
    public static final RegulatoryControlType NAFTA = new RegulatoryControlType(_NAFTA);
    public static final RegulatoryControlType NOT_APPLICABLE_FOR_LOW_CUSTOMS_VALUE_EXCEPTION = new RegulatoryControlType(_NOT_APPLICABLE_FOR_LOW_CUSTOMS_VALUE_EXCEPTION);
    public static final RegulatoryControlType NOT_IN_FREE_CIRCULATION = new RegulatoryControlType(_NOT_IN_FREE_CIRCULATION);
    public java.lang.String getValue() { return _value_;}
    public static RegulatoryControlType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RegulatoryControlType enumeration = (RegulatoryControlType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RegulatoryControlType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RegulatoryControlType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RegulatoryControlType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
