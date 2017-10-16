/**
 * PackagingType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class PackagingType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PackagingType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _FEDEX_10KG_BOX = "FEDEX_10KG_BOX";
    public static final java.lang.String _FEDEX_25KG_BOX = "FEDEX_25KG_BOX";
    public static final java.lang.String _FEDEX_BOX = "FEDEX_BOX";
    public static final java.lang.String _FEDEX_ENVELOPE = "FEDEX_ENVELOPE";
    public static final java.lang.String _FEDEX_EXTRA_LARGE_BOX = "FEDEX_EXTRA_LARGE_BOX";
    public static final java.lang.String _FEDEX_LARGE_BOX = "FEDEX_LARGE_BOX";
    public static final java.lang.String _FEDEX_MEDIUM_BOX = "FEDEX_MEDIUM_BOX";
    public static final java.lang.String _FEDEX_PAK = "FEDEX_PAK";
    public static final java.lang.String _FEDEX_SMALL_BOX = "FEDEX_SMALL_BOX";
    public static final java.lang.String _FEDEX_TUBE = "FEDEX_TUBE";
    public static final java.lang.String _YOUR_PACKAGING = "YOUR_PACKAGING";
    public static final PackagingType FEDEX_10KG_BOX = new PackagingType(_FEDEX_10KG_BOX);
    public static final PackagingType FEDEX_25KG_BOX = new PackagingType(_FEDEX_25KG_BOX);
    public static final PackagingType FEDEX_BOX = new PackagingType(_FEDEX_BOX);
    public static final PackagingType FEDEX_ENVELOPE = new PackagingType(_FEDEX_ENVELOPE);
    public static final PackagingType FEDEX_EXTRA_LARGE_BOX = new PackagingType(_FEDEX_EXTRA_LARGE_BOX);
    public static final PackagingType FEDEX_LARGE_BOX = new PackagingType(_FEDEX_LARGE_BOX);
    public static final PackagingType FEDEX_MEDIUM_BOX = new PackagingType(_FEDEX_MEDIUM_BOX);
    public static final PackagingType FEDEX_PAK = new PackagingType(_FEDEX_PAK);
    public static final PackagingType FEDEX_SMALL_BOX = new PackagingType(_FEDEX_SMALL_BOX);
    public static final PackagingType FEDEX_TUBE = new PackagingType(_FEDEX_TUBE);
    public static final PackagingType YOUR_PACKAGING = new PackagingType(_YOUR_PACKAGING);
    public java.lang.String getValue() { return _value_;}
    public static PackagingType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PackagingType enumeration = (PackagingType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PackagingType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PackagingType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackagingType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
