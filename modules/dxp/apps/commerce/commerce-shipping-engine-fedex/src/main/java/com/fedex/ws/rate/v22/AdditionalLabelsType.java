/**
 * AdditionalLabelsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class AdditionalLabelsType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AdditionalLabelsType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BROKER = "BROKER";
    public static final java.lang.String _CONSIGNEE = "CONSIGNEE";
    public static final java.lang.String _CUSTOMS = "CUSTOMS";
    public static final java.lang.String _DESTINATION = "DESTINATION";
    public static final java.lang.String _MANIFEST = "MANIFEST";
    public static final java.lang.String _ORIGIN = "ORIGIN";
    public static final java.lang.String _RECIPIENT = "RECIPIENT";
    public static final java.lang.String _SHIPPER = "SHIPPER";
    public static final AdditionalLabelsType BROKER = new AdditionalLabelsType(_BROKER);
    public static final AdditionalLabelsType CONSIGNEE = new AdditionalLabelsType(_CONSIGNEE);
    public static final AdditionalLabelsType CUSTOMS = new AdditionalLabelsType(_CUSTOMS);
    public static final AdditionalLabelsType DESTINATION = new AdditionalLabelsType(_DESTINATION);
    public static final AdditionalLabelsType MANIFEST = new AdditionalLabelsType(_MANIFEST);
    public static final AdditionalLabelsType ORIGIN = new AdditionalLabelsType(_ORIGIN);
    public static final AdditionalLabelsType RECIPIENT = new AdditionalLabelsType(_RECIPIENT);
    public static final AdditionalLabelsType SHIPPER = new AdditionalLabelsType(_SHIPPER);
    public java.lang.String getValue() { return _value_;}
    public static AdditionalLabelsType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AdditionalLabelsType enumeration = (AdditionalLabelsType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AdditionalLabelsType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AdditionalLabelsType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AdditionalLabelsType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
