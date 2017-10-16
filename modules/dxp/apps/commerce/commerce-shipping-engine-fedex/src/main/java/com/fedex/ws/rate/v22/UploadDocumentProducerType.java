/**
 * UploadDocumentProducerType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class UploadDocumentProducerType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UploadDocumentProducerType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CUSTOMER = "CUSTOMER";
    public static final java.lang.String _FEDEX_CAFE = "FEDEX_CAFE";
    public static final java.lang.String _FEDEX_CLS = "FEDEX_CLS";
    public static final java.lang.String _FEDEX_FIDT = "FEDEX_FIDT";
    public static final java.lang.String _FEDEX_FXRS = "FEDEX_FXRS";
    public static final java.lang.String _FEDEX_GSMW = "FEDEX_GSMW";
    public static final java.lang.String _FEDEX_GTM = "FEDEX_GTM";
    public static final java.lang.String _OTHER = "OTHER";
    public static final UploadDocumentProducerType CUSTOMER = new UploadDocumentProducerType(_CUSTOMER);
    public static final UploadDocumentProducerType FEDEX_CAFE = new UploadDocumentProducerType(_FEDEX_CAFE);
    public static final UploadDocumentProducerType FEDEX_CLS = new UploadDocumentProducerType(_FEDEX_CLS);
    public static final UploadDocumentProducerType FEDEX_FIDT = new UploadDocumentProducerType(_FEDEX_FIDT);
    public static final UploadDocumentProducerType FEDEX_FXRS = new UploadDocumentProducerType(_FEDEX_FXRS);
    public static final UploadDocumentProducerType FEDEX_GSMW = new UploadDocumentProducerType(_FEDEX_GSMW);
    public static final UploadDocumentProducerType FEDEX_GTM = new UploadDocumentProducerType(_FEDEX_GTM);
    public static final UploadDocumentProducerType OTHER = new UploadDocumentProducerType(_OTHER);
    public java.lang.String getValue() { return _value_;}
    public static UploadDocumentProducerType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        UploadDocumentProducerType enumeration = (UploadDocumentProducerType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static UploadDocumentProducerType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(UploadDocumentProducerType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "UploadDocumentProducerType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
