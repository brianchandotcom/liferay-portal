/**
 * UploadDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class UploadDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UploadDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _ETD_LABEL = "ETD_LABEL";
    public static final java.lang.String _NAFTA_CERTIFICATE_OF_ORIGIN = "NAFTA_CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _NET_RATE_SHEET = "NET_RATE_SHEET";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _PRO_FORMA_INVOICE = "PRO_FORMA_INVOICE";
    public static final UploadDocumentType CERTIFICATE_OF_ORIGIN = new UploadDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final UploadDocumentType COMMERCIAL_INVOICE = new UploadDocumentType(_COMMERCIAL_INVOICE);
    public static final UploadDocumentType ETD_LABEL = new UploadDocumentType(_ETD_LABEL);
    public static final UploadDocumentType NAFTA_CERTIFICATE_OF_ORIGIN = new UploadDocumentType(_NAFTA_CERTIFICATE_OF_ORIGIN);
    public static final UploadDocumentType NET_RATE_SHEET = new UploadDocumentType(_NET_RATE_SHEET);
    public static final UploadDocumentType OTHER = new UploadDocumentType(_OTHER);
    public static final UploadDocumentType PRO_FORMA_INVOICE = new UploadDocumentType(_PRO_FORMA_INVOICE);
    public java.lang.String getValue() { return _value_;}
    public static UploadDocumentType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        UploadDocumentType enumeration = (UploadDocumentType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static UploadDocumentType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(UploadDocumentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UploadDocumentType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
