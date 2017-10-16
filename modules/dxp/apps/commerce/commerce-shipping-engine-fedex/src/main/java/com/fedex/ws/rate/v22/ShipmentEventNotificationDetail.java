/**
 * ShipmentEventNotificationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class ShipmentEventNotificationDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.ShipmentNotificationAggregationType aggregationType;

    private java.lang.String personalMessage;

    private com.fedex.ws.rate.v22.ShipmentEventNotificationSpecification[] eventNotifications;

    public ShipmentEventNotificationDetail() {
    }

    public ShipmentEventNotificationDetail(
           com.fedex.ws.rate.v22.ShipmentNotificationAggregationType aggregationType,
           java.lang.String personalMessage,
           com.fedex.ws.rate.v22.ShipmentEventNotificationSpecification[] eventNotifications) {
           this.aggregationType = aggregationType;
           this.personalMessage = personalMessage;
           this.eventNotifications = eventNotifications;
    }


    /**
     * Gets the aggregationType value for this ShipmentEventNotificationDetail.
     * 
     * @return aggregationType
     */
    public com.fedex.ws.rate.v22.ShipmentNotificationAggregationType getAggregationType() {
        return aggregationType;
    }


    /**
     * Sets the aggregationType value for this ShipmentEventNotificationDetail.
     * 
     * @param aggregationType
     */
    public void setAggregationType(com.fedex.ws.rate.v22.ShipmentNotificationAggregationType aggregationType) {
        this.aggregationType = aggregationType;
    }


    /**
     * Gets the personalMessage value for this ShipmentEventNotificationDetail.
     * 
     * @return personalMessage
     */
    public java.lang.String getPersonalMessage() {
        return personalMessage;
    }


    /**
     * Sets the personalMessage value for this ShipmentEventNotificationDetail.
     * 
     * @param personalMessage
     */
    public void setPersonalMessage(java.lang.String personalMessage) {
        this.personalMessage = personalMessage;
    }


    /**
     * Gets the eventNotifications value for this ShipmentEventNotificationDetail.
     * 
     * @return eventNotifications
     */
    public com.fedex.ws.rate.v22.ShipmentEventNotificationSpecification[] getEventNotifications() {
        return eventNotifications;
    }


    /**
     * Sets the eventNotifications value for this ShipmentEventNotificationDetail.
     * 
     * @param eventNotifications
     */
    public void setEventNotifications(com.fedex.ws.rate.v22.ShipmentEventNotificationSpecification[] eventNotifications) {
        this.eventNotifications = eventNotifications;
    }

    public com.fedex.ws.rate.v22.ShipmentEventNotificationSpecification getEventNotifications(int i) {
        return this.eventNotifications[i];
    }

    public void setEventNotifications(int i, com.fedex.ws.rate.v22.ShipmentEventNotificationSpecification _value) {
        this.eventNotifications[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShipmentEventNotificationDetail)) return false;
        ShipmentEventNotificationDetail other = (ShipmentEventNotificationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.aggregationType==null && other.getAggregationType()==null) || 
             (this.aggregationType!=null &&
              this.aggregationType.equals(other.getAggregationType()))) &&
            ((this.personalMessage==null && other.getPersonalMessage()==null) || 
             (this.personalMessage!=null &&
              this.personalMessage.equals(other.getPersonalMessage()))) &&
            ((this.eventNotifications==null && other.getEventNotifications()==null) || 
             (this.eventNotifications!=null &&
              java.util.Arrays.equals(this.eventNotifications, other.getEventNotifications())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAggregationType() != null) {
            _hashCode += getAggregationType().hashCode();
        }
        if (getPersonalMessage() != null) {
            _hashCode += getPersonalMessage().hashCode();
        }
        if (getEventNotifications() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEventNotifications());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEventNotifications(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ShipmentEventNotificationDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentEventNotificationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aggregationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AggregationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentNotificationAggregationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personalMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PersonalMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventNotifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EventNotifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ShipmentEventNotificationSpecification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
