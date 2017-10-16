/**
 * RequiredShippingDocumentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class RequiredShippingDocumentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RequiredShippingDocumentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CANADIAN_B13A = "CANADIAN_B13A";
    public static final java.lang.String _CERTIFICATE_OF_ORIGIN = "CERTIFICATE_OF_ORIGIN";
    public static final java.lang.String _COMMERCIAL_INVOICE = "COMMERCIAL_INVOICE";
    public static final java.lang.String _INTERNATIONAL_AIRWAY_BILL = "INTERNATIONAL_AIRWAY_BILL";
    public static final java.lang.String _MAIL_SERVICE_AIRWAY_BILL = "MAIL_SERVICE_AIRWAY_BILL";
    public static final java.lang.String _SHIPPERS_EXPORT_DECLARATION = "SHIPPERS_EXPORT_DECLARATION";
    public static final RequiredShippingDocumentType CANADIAN_B13A = new RequiredShippingDocumentType(_CANADIAN_B13A);
    public static final RequiredShippingDocumentType CERTIFICATE_OF_ORIGIN = new RequiredShippingDocumentType(_CERTIFICATE_OF_ORIGIN);
    public static final RequiredShippingDocumentType COMMERCIAL_INVOICE = new RequiredShippingDocumentType(_COMMERCIAL_INVOICE);
    public static final RequiredShippingDocumentType INTERNATIONAL_AIRWAY_BILL = new RequiredShippingDocumentType(_INTERNATIONAL_AIRWAY_BILL);
    public static final RequiredShippingDocumentType MAIL_SERVICE_AIRWAY_BILL = new RequiredShippingDocumentType(_MAIL_SERVICE_AIRWAY_BILL);
    public static final RequiredShippingDocumentType SHIPPERS_EXPORT_DECLARATION = new RequiredShippingDocumentType(_SHIPPERS_EXPORT_DECLARATION);
    public java.lang.String getValue() { return _value_;}
    public static RequiredShippingDocumentType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RequiredShippingDocumentType enumeration = (RequiredShippingDocumentType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RequiredShippingDocumentType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RequiredShippingDocumentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequiredShippingDocumentType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
