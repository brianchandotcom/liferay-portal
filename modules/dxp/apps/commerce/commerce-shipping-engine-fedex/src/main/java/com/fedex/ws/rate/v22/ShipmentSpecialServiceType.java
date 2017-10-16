/**
 * ShipmentSpecialServiceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class ShipmentSpecialServiceType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ShipmentSpecialServiceType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _BROKER_SELECT_OPTION = "BROKER_SELECT_OPTION";
    public static final java.lang.String _CALL_BEFORE_DELIVERY = "CALL_BEFORE_DELIVERY";
    public static final java.lang.String _COD = "COD";
    public static final java.lang.String _CUSTOM_DELIVERY_WINDOW = "CUSTOM_DELIVERY_WINDOW";
    public static final java.lang.String _CUT_FLOWERS = "CUT_FLOWERS";
    public static final java.lang.String _DANGEROUS_GOODS = "DANGEROUS_GOODS";
    public static final java.lang.String _DO_NOT_BREAK_DOWN_PALLETS = "DO_NOT_BREAK_DOWN_PALLETS";
    public static final java.lang.String _DO_NOT_STACK_PALLETS = "DO_NOT_STACK_PALLETS";
    public static final java.lang.String _DRY_ICE = "DRY_ICE";
    public static final java.lang.String _EAST_COAST_SPECIAL = "EAST_COAST_SPECIAL";
    public static final java.lang.String _ELECTRONIC_TRADE_DOCUMENTS = "ELECTRONIC_TRADE_DOCUMENTS";
    public static final java.lang.String _EVENT_NOTIFICATION = "EVENT_NOTIFICATION";
    public static final java.lang.String _EXCLUDE_FROM_CONSOLIDATION = "EXCLUDE_FROM_CONSOLIDATION";
    public static final java.lang.String _EXTREME_LENGTH = "EXTREME_LENGTH";
    public static final java.lang.String _FEDEX_ONE_RATE = "FEDEX_ONE_RATE";
    public static final java.lang.String _FOOD = "FOOD";
    public static final java.lang.String _FREIGHT_GUARANTEE = "FREIGHT_GUARANTEE";
    public static final java.lang.String _FREIGHT_TO_COLLECT = "FREIGHT_TO_COLLECT";
    public static final java.lang.String _FUTURE_DAY_SHIPMENT = "FUTURE_DAY_SHIPMENT";
    public static final java.lang.String _HOLD_AT_LOCATION = "HOLD_AT_LOCATION";
    public static final java.lang.String _HOME_DELIVERY_PREMIUM = "HOME_DELIVERY_PREMIUM";
    public static final java.lang.String _INSIDE_DELIVERY = "INSIDE_DELIVERY";
    public static final java.lang.String _INSIDE_PICKUP = "INSIDE_PICKUP";
    public static final java.lang.String _INTERNATIONAL_CONTROLLED_EXPORT_SERVICE = "INTERNATIONAL_CONTROLLED_EXPORT_SERVICE";
    public static final java.lang.String _INTERNATIONAL_TRAFFIC_IN_ARMS_REGULATIONS = "INTERNATIONAL_TRAFFIC_IN_ARMS_REGULATIONS";
    public static final java.lang.String _LIFTGATE_DELIVERY = "LIFTGATE_DELIVERY";
    public static final java.lang.String _LIFTGATE_PICKUP = "LIFTGATE_PICKUP";
    public static final java.lang.String _LIMITED_ACCESS_DELIVERY = "LIMITED_ACCESS_DELIVERY";
    public static final java.lang.String _LIMITED_ACCESS_PICKUP = "LIMITED_ACCESS_PICKUP";
    public static final java.lang.String _PENDING_SHIPMENT = "PENDING_SHIPMENT";
    public static final java.lang.String _PHARMACY_DELIVERY = "PHARMACY_DELIVERY";
    public static final java.lang.String _POISON = "POISON";
    public static final java.lang.String _PROTECTION_FROM_FREEZING = "PROTECTION_FROM_FREEZING";
    public static final java.lang.String _RETURNS_CLEARANCE = "RETURNS_CLEARANCE";
    public static final java.lang.String _RETURN_SHIPMENT = "RETURN_SHIPMENT";
    public static final java.lang.String _SATURDAY_DELIVERY = "SATURDAY_DELIVERY";
    public static final java.lang.String _SATURDAY_PICKUP = "SATURDAY_PICKUP";
    public static final java.lang.String _THIRD_PARTY_CONSIGNEE = "THIRD_PARTY_CONSIGNEE";
    public static final java.lang.String _TOP_LOAD = "TOP_LOAD";
    public static final ShipmentSpecialServiceType BROKER_SELECT_OPTION = new ShipmentSpecialServiceType(_BROKER_SELECT_OPTION);
    public static final ShipmentSpecialServiceType CALL_BEFORE_DELIVERY = new ShipmentSpecialServiceType(_CALL_BEFORE_DELIVERY);
    public static final ShipmentSpecialServiceType COD = new ShipmentSpecialServiceType(_COD);
    public static final ShipmentSpecialServiceType CUSTOM_DELIVERY_WINDOW = new ShipmentSpecialServiceType(_CUSTOM_DELIVERY_WINDOW);
    public static final ShipmentSpecialServiceType CUT_FLOWERS = new ShipmentSpecialServiceType(_CUT_FLOWERS);
    public static final ShipmentSpecialServiceType DANGEROUS_GOODS = new ShipmentSpecialServiceType(_DANGEROUS_GOODS);
    public static final ShipmentSpecialServiceType DO_NOT_BREAK_DOWN_PALLETS = new ShipmentSpecialServiceType(_DO_NOT_BREAK_DOWN_PALLETS);
    public static final ShipmentSpecialServiceType DO_NOT_STACK_PALLETS = new ShipmentSpecialServiceType(_DO_NOT_STACK_PALLETS);
    public static final ShipmentSpecialServiceType DRY_ICE = new ShipmentSpecialServiceType(_DRY_ICE);
    public static final ShipmentSpecialServiceType EAST_COAST_SPECIAL = new ShipmentSpecialServiceType(_EAST_COAST_SPECIAL);
    public static final ShipmentSpecialServiceType ELECTRONIC_TRADE_DOCUMENTS = new ShipmentSpecialServiceType(_ELECTRONIC_TRADE_DOCUMENTS);
    public static final ShipmentSpecialServiceType EVENT_NOTIFICATION = new ShipmentSpecialServiceType(_EVENT_NOTIFICATION);
    public static final ShipmentSpecialServiceType EXCLUDE_FROM_CONSOLIDATION = new ShipmentSpecialServiceType(_EXCLUDE_FROM_CONSOLIDATION);
    public static final ShipmentSpecialServiceType EXTREME_LENGTH = new ShipmentSpecialServiceType(_EXTREME_LENGTH);
    public static final ShipmentSpecialServiceType FEDEX_ONE_RATE = new ShipmentSpecialServiceType(_FEDEX_ONE_RATE);
    public static final ShipmentSpecialServiceType FOOD = new ShipmentSpecialServiceType(_FOOD);
    public static final ShipmentSpecialServiceType FREIGHT_GUARANTEE = new ShipmentSpecialServiceType(_FREIGHT_GUARANTEE);
    public static final ShipmentSpecialServiceType FREIGHT_TO_COLLECT = new ShipmentSpecialServiceType(_FREIGHT_TO_COLLECT);
    public static final ShipmentSpecialServiceType FUTURE_DAY_SHIPMENT = new ShipmentSpecialServiceType(_FUTURE_DAY_SHIPMENT);
    public static final ShipmentSpecialServiceType HOLD_AT_LOCATION = new ShipmentSpecialServiceType(_HOLD_AT_LOCATION);
    public static final ShipmentSpecialServiceType HOME_DELIVERY_PREMIUM = new ShipmentSpecialServiceType(_HOME_DELIVERY_PREMIUM);
    public static final ShipmentSpecialServiceType INSIDE_DELIVERY = new ShipmentSpecialServiceType(_INSIDE_DELIVERY);
    public static final ShipmentSpecialServiceType INSIDE_PICKUP = new ShipmentSpecialServiceType(_INSIDE_PICKUP);
    public static final ShipmentSpecialServiceType INTERNATIONAL_CONTROLLED_EXPORT_SERVICE = new ShipmentSpecialServiceType(_INTERNATIONAL_CONTROLLED_EXPORT_SERVICE);
    public static final ShipmentSpecialServiceType INTERNATIONAL_TRAFFIC_IN_ARMS_REGULATIONS = new ShipmentSpecialServiceType(_INTERNATIONAL_TRAFFIC_IN_ARMS_REGULATIONS);
    public static final ShipmentSpecialServiceType LIFTGATE_DELIVERY = new ShipmentSpecialServiceType(_LIFTGATE_DELIVERY);
    public static final ShipmentSpecialServiceType LIFTGATE_PICKUP = new ShipmentSpecialServiceType(_LIFTGATE_PICKUP);
    public static final ShipmentSpecialServiceType LIMITED_ACCESS_DELIVERY = new ShipmentSpecialServiceType(_LIMITED_ACCESS_DELIVERY);
    public static final ShipmentSpecialServiceType LIMITED_ACCESS_PICKUP = new ShipmentSpecialServiceType(_LIMITED_ACCESS_PICKUP);
    public static final ShipmentSpecialServiceType PENDING_SHIPMENT = new ShipmentSpecialServiceType(_PENDING_SHIPMENT);
    public static final ShipmentSpecialServiceType PHARMACY_DELIVERY = new ShipmentSpecialServiceType(_PHARMACY_DELIVERY);
    public static final ShipmentSpecialServiceType POISON = new ShipmentSpecialServiceType(_POISON);
    public static final ShipmentSpecialServiceType PROTECTION_FROM_FREEZING = new ShipmentSpecialServiceType(_PROTECTION_FROM_FREEZING);
    public static final ShipmentSpecialServiceType RETURNS_CLEARANCE = new ShipmentSpecialServiceType(_RETURNS_CLEARANCE);
    public static final ShipmentSpecialServiceType RETURN_SHIPMENT = new ShipmentSpecialServiceType(_RETURN_SHIPMENT);
    public static final ShipmentSpecialServiceType SATURDAY_DELIVERY = new ShipmentSpecialServiceType(_SATURDAY_DELIVERY);
    public static final ShipmentSpecialServiceType SATURDAY_PICKUP = new ShipmentSpecialServiceType(_SATURDAY_PICKUP);
    public static final ShipmentSpecialServiceType THIRD_PARTY_CONSIGNEE = new ShipmentSpecialServiceType(_THIRD_PARTY_CONSIGNEE);
    public static final ShipmentSpecialServiceType TOP_LOAD = new ShipmentSpecialServiceType(_TOP_LOAD);
    public java.lang.String getValue() { return _value_;}
    public static ShipmentSpecialServiceType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ShipmentSpecialServiceType enumeration = (ShipmentSpecialServiceType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ShipmentSpecialServiceType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ShipmentSpecialServiceType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentSpecialServiceType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
