/**
 * TransitTimeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class TransitTimeType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected TransitTimeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _EIGHTEEN_DAYS = "EIGHTEEN_DAYS";
    public static final java.lang.String _EIGHT_DAYS = "EIGHT_DAYS";
    public static final java.lang.String _ELEVEN_DAYS = "ELEVEN_DAYS";
    public static final java.lang.String _FIFTEEN_DAYS = "FIFTEEN_DAYS";
    public static final java.lang.String _FIVE_DAYS = "FIVE_DAYS";
    public static final java.lang.String _FOURTEEN_DAYS = "FOURTEEN_DAYS";
    public static final java.lang.String _FOUR_DAYS = "FOUR_DAYS";
    public static final java.lang.String _NINETEEN_DAYS = "NINETEEN_DAYS";
    public static final java.lang.String _NINE_DAYS = "NINE_DAYS";
    public static final java.lang.String _ONE_DAY = "ONE_DAY";
    public static final java.lang.String _SEVENTEEN_DAYS = "SEVENTEEN_DAYS";
    public static final java.lang.String _SEVEN_DAYS = "SEVEN_DAYS";
    public static final java.lang.String _SIXTEEN_DAYS = "SIXTEEN_DAYS";
    public static final java.lang.String _SIX_DAYS = "SIX_DAYS";
    public static final java.lang.String _TEN_DAYS = "TEN_DAYS";
    public static final java.lang.String _THIRTEEN_DAYS = "THIRTEEN_DAYS";
    public static final java.lang.String _THREE_DAYS = "THREE_DAYS";
    public static final java.lang.String _TWELVE_DAYS = "TWELVE_DAYS";
    public static final java.lang.String _TWENTY_DAYS = "TWENTY_DAYS";
    public static final java.lang.String _TWO_DAYS = "TWO_DAYS";
    public static final java.lang.String _UNKNOWN = "UNKNOWN";
    public static final TransitTimeType EIGHTEEN_DAYS = new TransitTimeType(_EIGHTEEN_DAYS);
    public static final TransitTimeType EIGHT_DAYS = new TransitTimeType(_EIGHT_DAYS);
    public static final TransitTimeType ELEVEN_DAYS = new TransitTimeType(_ELEVEN_DAYS);
    public static final TransitTimeType FIFTEEN_DAYS = new TransitTimeType(_FIFTEEN_DAYS);
    public static final TransitTimeType FIVE_DAYS = new TransitTimeType(_FIVE_DAYS);
    public static final TransitTimeType FOURTEEN_DAYS = new TransitTimeType(_FOURTEEN_DAYS);
    public static final TransitTimeType FOUR_DAYS = new TransitTimeType(_FOUR_DAYS);
    public static final TransitTimeType NINETEEN_DAYS = new TransitTimeType(_NINETEEN_DAYS);
    public static final TransitTimeType NINE_DAYS = new TransitTimeType(_NINE_DAYS);
    public static final TransitTimeType ONE_DAY = new TransitTimeType(_ONE_DAY);
    public static final TransitTimeType SEVENTEEN_DAYS = new TransitTimeType(_SEVENTEEN_DAYS);
    public static final TransitTimeType SEVEN_DAYS = new TransitTimeType(_SEVEN_DAYS);
    public static final TransitTimeType SIXTEEN_DAYS = new TransitTimeType(_SIXTEEN_DAYS);
    public static final TransitTimeType SIX_DAYS = new TransitTimeType(_SIX_DAYS);
    public static final TransitTimeType TEN_DAYS = new TransitTimeType(_TEN_DAYS);
    public static final TransitTimeType THIRTEEN_DAYS = new TransitTimeType(_THIRTEEN_DAYS);
    public static final TransitTimeType THREE_DAYS = new TransitTimeType(_THREE_DAYS);
    public static final TransitTimeType TWELVE_DAYS = new TransitTimeType(_TWELVE_DAYS);
    public static final TransitTimeType TWENTY_DAYS = new TransitTimeType(_TWENTY_DAYS);
    public static final TransitTimeType TWO_DAYS = new TransitTimeType(_TWO_DAYS);
    public static final TransitTimeType UNKNOWN = new TransitTimeType(_UNKNOWN);
    public java.lang.String getValue() { return _value_;}
    public static TransitTimeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        TransitTimeType enumeration = (TransitTimeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static TransitTimeType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(TransitTimeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TransitTimeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
