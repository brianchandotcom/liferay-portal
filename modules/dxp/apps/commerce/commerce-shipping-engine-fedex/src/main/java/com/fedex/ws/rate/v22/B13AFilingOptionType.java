/**
 * B13AFilingOptionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class B13AFilingOptionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected B13AFilingOptionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_TO_STAMP = "FEDEX_TO_STAMP";
    public static final java.lang.String _FILED_ELECTRONICALLY = "FILED_ELECTRONICALLY";
    public static final java.lang.String _MANUALLY_ATTACHED = "MANUALLY_ATTACHED";
    public static final java.lang.String _NOT_REQUIRED = "NOT_REQUIRED";
    public static final java.lang.String _SUMMARY_REPORTING = "SUMMARY_REPORTING";
    public static final B13AFilingOptionType FEDEX_TO_STAMP = new B13AFilingOptionType(_FEDEX_TO_STAMP);
    public static final B13AFilingOptionType FILED_ELECTRONICALLY = new B13AFilingOptionType(_FILED_ELECTRONICALLY);
    public static final B13AFilingOptionType MANUALLY_ATTACHED = new B13AFilingOptionType(_MANUALLY_ATTACHED);
    public static final B13AFilingOptionType NOT_REQUIRED = new B13AFilingOptionType(_NOT_REQUIRED);
    public static final B13AFilingOptionType SUMMARY_REPORTING = new B13AFilingOptionType(_SUMMARY_REPORTING);
    public java.lang.String getValue() { return _value_;}
    public static B13AFilingOptionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        B13AFilingOptionType enumeration = (B13AFilingOptionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static B13AFilingOptionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(B13AFilingOptionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "B13AFilingOptionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
