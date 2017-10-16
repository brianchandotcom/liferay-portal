/**
 * RequestedShippingDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RequestedShippingDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RequestedShippingDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _CUSTOMER_SPECIFIED_LABELS = "CUSTOMER_SPECIFIED_LABELS";
    public static final java.lang.String _DANGEROUS_GOODS_SHIPPERS_DECLARATION = "DANGEROUS_GOODS_SHIPPERS_DECLARATION";
    public static final java.lang.String _EXPORT_DECLARATION = "EXPORT_DECLARATION";
    public static final java.lang.String _GENERAL_AGENCY_AGREEMENT = "GENERAL_AGENCY_AGREEMENT";
    public static final java.lang.String _LABEL = "LABEL";
    public static final java.lang.String _NAFTA_CERTIFICATE_OF_ORIGIN = "NAFTA_CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _PRO_FORMA_INVOICE = "PRO_FORMA_INVOICE";
    public static final java.lang.String _RETURN_INSTRUCTIONS = "RETURN_INSTRUCTIONS";
    public static final RequestedShippingDocumentType CERTIFICATE_OF_ORIGIN = new RequestedShippingDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final RequestedShippingDocumentType COMMERCIAL_INVOICE = new RequestedShippingDocumentType(_COMMERCIAL_INVOICE);
    public static final RequestedShippingDocumentType CUSTOMER_SPECIFIED_LABELS = new RequestedShippingDocumentType(_CUSTOMER_SPECIFIED_LABELS);
    public static final RequestedShippingDocumentType DANGEROUS_GOODS_SHIPPERS_DECLARATION = new RequestedShippingDocumentType(_DANGEROUS_GOODS_SHIPPERS_DECLARATION);
    public static final RequestedShippingDocumentType EXPORT_DECLARATION = new RequestedShippingDocumentType(_EXPORT_DECLARATION);
    public static final RequestedShippingDocumentType GENERAL_AGENCY_AGREEMENT = new RequestedShippingDocumentType(_GENERAL_AGENCY_AGREEMENT);
    public static final RequestedShippingDocumentType LABEL = new RequestedShippingDocumentType(_LABEL);
    public static final RequestedShippingDocumentType NAFTA_CERTIFICATE_OF_ORIGIN = new RequestedShippingDocumentType(_NAFTA_CERTIFICATE_OF_ORIGIN);
    public static final RequestedShippingDocumentType PRO_FORMA_INVOICE = new RequestedShippingDocumentType(_PRO_FORMA_INVOICE);
    public static final RequestedShippingDocumentType RETURN_INSTRUCTIONS = new RequestedShippingDocumentType(_RETURN_INSTRUCTIONS);
    public java.lang.String getValue() { return _value_;}
    public static RequestedShippingDocumentType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RequestedShippingDocumentType enumeration = (RequestedShippingDocumentType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RequestedShippingDocumentType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RequestedShippingDocumentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestedShippingDocumentType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
