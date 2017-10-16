/**
 * LabelMaskableDataType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class LabelMaskableDataType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected LabelMaskableDataType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _CUSTOMS_VALUE = "CUSTOMS_VALUE";
    public static final java.lang.String _DIMENSIONS = "DIMENSIONS";
    public static final java.lang.String _DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER = "DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER";
    public static final java.lang.String _FREIGHT_PAYOR_ACCOUNT_NUMBER = "FREIGHT_PAYOR_ACCOUNT_NUMBER";
    public static final java.lang.String _PACKAGE_SEQUENCE_AND_COUNT = "PACKAGE_SEQUENCE_AND_COUNT";
    public static final java.lang.String _SECONDARY_BARCODE = "SECONDARY_BARCODE";
    public static final java.lang.String _SHIPPER_ACCOUNT_NUMBER = "SHIPPER_ACCOUNT_NUMBER";
    public static final java.lang.String _SUPPLEMENTAL_LABEL_DOC_TAB = "SUPPLEMENTAL_LABEL_DOC_TAB";
    public static final java.lang.String _TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";
    public static final java.lang.String _TOTAL_WEIGHT = "TOTAL_WEIGHT";
    public static final java.lang.String _TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER = "TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER";
    public static final LabelMaskableDataType CUSTOMS_VALUE = new LabelMaskableDataType(_CUSTOMS_VALUE);
    public static final LabelMaskableDataType DIMENSIONS = new LabelMaskableDataType(_DIMENSIONS);
    public static final LabelMaskableDataType DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER = new LabelMaskableDataType(_DUTIES_AND_TAXES_PAYOR_ACCOUNT_NUMBER);
    public static final LabelMaskableDataType FREIGHT_PAYOR_ACCOUNT_NUMBER = new LabelMaskableDataType(_FREIGHT_PAYOR_ACCOUNT_NUMBER);
    public static final LabelMaskableDataType PACKAGE_SEQUENCE_AND_COUNT = new LabelMaskableDataType(_PACKAGE_SEQUENCE_AND_COUNT);
    public static final LabelMaskableDataType SECONDARY_BARCODE = new LabelMaskableDataType(_SECONDARY_BARCODE);
    public static final LabelMaskableDataType SHIPPER_ACCOUNT_NUMBER = new LabelMaskableDataType(_SHIPPER_ACCOUNT_NUMBER);
    public static final LabelMaskableDataType SUPPLEMENTAL_LABEL_DOC_TAB = new LabelMaskableDataType(_SUPPLEMENTAL_LABEL_DOC_TAB);
    public static final LabelMaskableDataType TERMS_AND_CONDITIONS = new LabelMaskableDataType(_TERMS_AND_CONDITIONS);
    public static final LabelMaskableDataType TOTAL_WEIGHT = new LabelMaskableDataType(_TOTAL_WEIGHT);
    public static final LabelMaskableDataType TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER = new LabelMaskableDataType(_TRANSPORTATION_CHARGES_PAYOR_ACCOUNT_NUMBER);
    public java.lang.String getValue() { return _value_;}
    public static LabelMaskableDataType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        LabelMaskableDataType enumeration = (LabelMaskableDataType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static LabelMaskableDataType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(LabelMaskableDataType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LabelMaskableDataType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
