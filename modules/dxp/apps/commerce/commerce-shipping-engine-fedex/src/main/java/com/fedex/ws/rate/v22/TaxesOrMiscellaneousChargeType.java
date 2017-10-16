/**
 * TaxesOrMiscellaneousChargeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class TaxesOrMiscellaneousChargeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TaxesOrMiscellaneousChargeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _COMMISSIONS = "COMMISSIONS";
    public static final java.lang.String _DISCOUNTS = "DISCOUNTS";
    public static final java.lang.String _HANDLING_FEES = "HANDLING_FEES";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _ROYALTIES_AND_LICENSE_FEES = "ROYALTIES_AND_LICENSE_FEES";
    public static final java.lang.String _TAXES = "TAXES";
    public static final TaxesOrMiscellaneousChargeType COMMISSIONS = new TaxesOrMiscellaneousChargeType(_COMMISSIONS);
    public static final TaxesOrMiscellaneousChargeType DISCOUNTS = new TaxesOrMiscellaneousChargeType(_DISCOUNTS);
    public static final TaxesOrMiscellaneousChargeType HANDLING_FEES = new TaxesOrMiscellaneousChargeType(_HANDLING_FEES);
    public static final TaxesOrMiscellaneousChargeType OTHER = new TaxesOrMiscellaneousChargeType(_OTHER);
    public static final TaxesOrMiscellaneousChargeType ROYALTIES_AND_LICENSE_FEES = new TaxesOrMiscellaneousChargeType(_ROYALTIES_AND_LICENSE_FEES);
    public static final TaxesOrMiscellaneousChargeType TAXES = new TaxesOrMiscellaneousChargeType(_TAXES);
    public java.lang.String getValue() { return _value_;}
    public static TaxesOrMiscellaneousChargeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TaxesOrMiscellaneousChargeType enumeration = (TaxesOrMiscellaneousChargeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TaxesOrMiscellaneousChargeType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TaxesOrMiscellaneousChargeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TaxesOrMiscellaneousChargeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
