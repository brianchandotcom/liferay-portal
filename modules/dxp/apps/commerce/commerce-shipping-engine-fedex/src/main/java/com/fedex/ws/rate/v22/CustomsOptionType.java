/**
 * CustomsOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CustomsOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected CustomsOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COURTESY_RETURN_LABEL = "COURTESY_RETURN_LABEL";
    public static final java.lang.String _EXHIBITION_TRADE_SHOW = "EXHIBITION_TRADE_SHOW";
    public static final java.lang.String _FAULTY_ITEM = "FAULTY_ITEM";
    public static final java.lang.String _FOLLOWING_REPAIR = "FOLLOWING_REPAIR";
    public static final java.lang.String _FOR_REPAIR = "FOR_REPAIR";
    public static final java.lang.String _ITEM_FOR_LOAN = "ITEM_FOR_LOAN";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _REJECTED = "REJECTED";
    public static final java.lang.String _REPLACEMENT = "REPLACEMENT";
    public static final java.lang.String _TRIAL = "TRIAL";
    public static final CustomsOptionType COURTESY_RETURN_LABEL = new CustomsOptionType(_COURTESY_RETURN_LABEL);
    public static final CustomsOptionType EXHIBITION_TRADE_SHOW = new CustomsOptionType(_EXHIBITION_TRADE_SHOW);
    public static final CustomsOptionType FAULTY_ITEM = new CustomsOptionType(_FAULTY_ITEM);
    public static final CustomsOptionType FOLLOWING_REPAIR = new CustomsOptionType(_FOLLOWING_REPAIR);
    public static final CustomsOptionType FOR_REPAIR = new CustomsOptionType(_FOR_REPAIR);
    public static final CustomsOptionType ITEM_FOR_LOAN = new CustomsOptionType(_ITEM_FOR_LOAN);
    public static final CustomsOptionType OTHER = new CustomsOptionType(_OTHER);
    public static final CustomsOptionType REJECTED = new CustomsOptionType(_REJECTED);
    public static final CustomsOptionType REPLACEMENT = new CustomsOptionType(_REPLACEMENT);
    public static final CustomsOptionType TRIAL = new CustomsOptionType(_TRIAL);
    public java.lang.String getValue() { return _value_;}
    public static CustomsOptionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        CustomsOptionType enumeration = (CustomsOptionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static CustomsOptionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(CustomsOptionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomsOptionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
