/**
 * PhysicalPackagingType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class PhysicalPackagingType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected PhysicalPackagingType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BAG = "BAG";
    public static final java.lang.String _BARREL = "BARREL";
    public static final java.lang.String _BASKET = "BASKET";
    public static final java.lang.String _BOX = "BOX";
    public static final java.lang.String _BUCKET = "BUCKET";
    public static final java.lang.String _BUNDLE = "BUNDLE";
    public static final java.lang.String _CARTON = "CARTON";
    public static final java.lang.String _CASE = "CASE";
    public static final java.lang.String _CONTAINER = "CONTAINER";
    public static final java.lang.String _CRATE = "CRATE";
    public static final java.lang.String _CYLINDER = "CYLINDER";
    public static final java.lang.String _DRUM = "DRUM";
    public static final java.lang.String _ENVELOPE = "ENVELOPE";
    public static final java.lang.String _HAMPER = "HAMPER";
    public static final java.lang.String _OTHER = "OTHER";
    public static final java.lang.String _PAIL = "PAIL";
    public static final java.lang.String _PALLET = "PALLET";
    public static final java.lang.String _PIECE = "PIECE";
    public static final java.lang.String _REEL = "REEL";
    public static final java.lang.String _ROLL = "ROLL";
    public static final java.lang.String _SKID = "SKID";
    public static final java.lang.String _TANK = "TANK";
    public static final java.lang.String _TUBE = "TUBE";
    public static final PhysicalPackagingType BAG = new PhysicalPackagingType(_BAG);
    public static final PhysicalPackagingType BARREL = new PhysicalPackagingType(_BARREL);
    public static final PhysicalPackagingType BASKET = new PhysicalPackagingType(_BASKET);
    public static final PhysicalPackagingType BOX = new PhysicalPackagingType(_BOX);
    public static final PhysicalPackagingType BUCKET = new PhysicalPackagingType(_BUCKET);
    public static final PhysicalPackagingType BUNDLE = new PhysicalPackagingType(_BUNDLE);
    public static final PhysicalPackagingType CARTON = new PhysicalPackagingType(_CARTON);
    public static final PhysicalPackagingType CASE = new PhysicalPackagingType(_CASE);
    public static final PhysicalPackagingType CONTAINER = new PhysicalPackagingType(_CONTAINER);
    public static final PhysicalPackagingType CRATE = new PhysicalPackagingType(_CRATE);
    public static final PhysicalPackagingType CYLINDER = new PhysicalPackagingType(_CYLINDER);
    public static final PhysicalPackagingType DRUM = new PhysicalPackagingType(_DRUM);
    public static final PhysicalPackagingType ENVELOPE = new PhysicalPackagingType(_ENVELOPE);
    public static final PhysicalPackagingType HAMPER = new PhysicalPackagingType(_HAMPER);
    public static final PhysicalPackagingType OTHER = new PhysicalPackagingType(_OTHER);
    public static final PhysicalPackagingType PAIL = new PhysicalPackagingType(_PAIL);
    public static final PhysicalPackagingType PALLET = new PhysicalPackagingType(_PALLET);
    public static final PhysicalPackagingType PIECE = new PhysicalPackagingType(_PIECE);
    public static final PhysicalPackagingType REEL = new PhysicalPackagingType(_REEL);
    public static final PhysicalPackagingType ROLL = new PhysicalPackagingType(_ROLL);
    public static final PhysicalPackagingType SKID = new PhysicalPackagingType(_SKID);
    public static final PhysicalPackagingType TANK = new PhysicalPackagingType(_TANK);
    public static final PhysicalPackagingType TUBE = new PhysicalPackagingType(_TUBE);
    public java.lang.String getValue() { return _value_;}
    public static PhysicalPackagingType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        PhysicalPackagingType enumeration = (PhysicalPackagingType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static PhysicalPackagingType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(PhysicalPackagingType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PhysicalPackagingType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
