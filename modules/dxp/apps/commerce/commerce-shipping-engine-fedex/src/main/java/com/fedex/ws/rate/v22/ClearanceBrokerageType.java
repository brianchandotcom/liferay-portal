/**
 * ClearanceBrokerageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class ClearanceBrokerageType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ClearanceBrokerageType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BROKER_INCLUSIVE = "BROKER_INCLUSIVE";
    public static final java.lang.String _BROKER_INCLUSIVE_NON_RESIDENT_IMPORTER = "BROKER_INCLUSIVE_NON_RESIDENT_IMPORTER";
    public static final java.lang.String _BROKER_SELECT = "BROKER_SELECT";
    public static final java.lang.String _BROKER_SELECT_NON_RESIDENT_IMPORTER = "BROKER_SELECT_NON_RESIDENT_IMPORTER";
    public static final java.lang.String _BROKER_UNASSIGNED = "BROKER_UNASSIGNED";
    public static final ClearanceBrokerageType BROKER_INCLUSIVE = new ClearanceBrokerageType(_BROKER_INCLUSIVE);
    public static final ClearanceBrokerageType BROKER_INCLUSIVE_NON_RESIDENT_IMPORTER = new ClearanceBrokerageType(_BROKER_INCLUSIVE_NON_RESIDENT_IMPORTER);
    public static final ClearanceBrokerageType BROKER_SELECT = new ClearanceBrokerageType(_BROKER_SELECT);
    public static final ClearanceBrokerageType BROKER_SELECT_NON_RESIDENT_IMPORTER = new ClearanceBrokerageType(_BROKER_SELECT_NON_RESIDENT_IMPORTER);
    public static final ClearanceBrokerageType BROKER_UNASSIGNED = new ClearanceBrokerageType(_BROKER_UNASSIGNED);
    public java.lang.String getValue() { return _value_;}
    public static ClearanceBrokerageType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ClearanceBrokerageType enumeration = (ClearanceBrokerageType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ClearanceBrokerageType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ClearanceBrokerageType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ClearanceBrokerageType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
