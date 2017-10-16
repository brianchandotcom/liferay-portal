/**
 * DelayDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Information about why a shipment delivery is delayed and at what
 * level( country/service etc.).
 */
public class DelayDetail  implements java.io.Serializable {
    /* The date of the delay */
    private java.util.Date date;

    private com.fedex.ws.rate.v22.DayOfWeekType dayOfWeek;

    /* The attribute of the shipment that caused the delay(e.g. Country,
     * City, LocationId, Zip, service area, special handling ) */
    private com.fedex.ws.rate.v22.DelayLevelType level;

    /* The point where the delay is occurring (e.g. Origin, Destination,
     * Broker location) */
    private com.fedex.ws.rate.v22.DelayPointType point;

    /* The reason for the delay (e.g. holiday, weekend, etc.). */
    private com.fedex.ws.rate.v22.CommitmentDelayType type;

    /* The name of the holiday in that country that is causing the
     * delay. */
    private java.lang.String description;

    public DelayDetail() {
    }

    public DelayDetail(
           java.util.Date date,
           com.fedex.ws.rate.v22.DayOfWeekType dayOfWeek,
           com.fedex.ws.rate.v22.DelayLevelType level,
           com.fedex.ws.rate.v22.DelayPointType point,
           com.fedex.ws.rate.v22.CommitmentDelayType type,
           java.lang.String description) {
           this.date = date;
           this.dayOfWeek = dayOfWeek;
           this.level = level;
           this.point = point;
           this.type = type;
           this.description = description;
    }


    /**
     * Gets the date value for this DelayDetail.
     * 
     * @return date   * The date of the delay
     */
    public java.util.Date getDate() {
        return date;
    }


    /**
     * Sets the date value for this DelayDetail.
     * 
     * @param date   * The date of the delay
     */
    public void setDate(java.util.Date date) {
        this.date = date;
    }


    /**
     * Gets the dayOfWeek value for this DelayDetail.
     * 
     * @return dayOfWeek
     */
    public com.fedex.ws.rate.v22.DayOfWeekType getDayOfWeek() {
        return dayOfWeek;
    }


    /**
     * Sets the dayOfWeek value for this DelayDetail.
     * 
     * @param dayOfWeek
     */
    public void setDayOfWeek(com.fedex.ws.rate.v22.DayOfWeekType dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    /**
     * Gets the level value for this DelayDetail.
     * 
     * @return level   * The attribute of the shipment that caused the delay(e.g. Country,
     * City, LocationId, Zip, service area, special handling )
     */
    public com.fedex.ws.rate.v22.DelayLevelType getLevel() {
        return level;
    }


    /**
     * Sets the level value for this DelayDetail.
     * 
     * @param level   * The attribute of the shipment that caused the delay(e.g. Country,
     * City, LocationId, Zip, service area, special handling )
     */
    public void setLevel(com.fedex.ws.rate.v22.DelayLevelType level) {
        this.level = level;
    }


    /**
     * Gets the point value for this DelayDetail.
     * 
     * @return point   * The point where the delay is occurring (e.g. Origin, Destination,
     * Broker location)
     */
    public com.fedex.ws.rate.v22.DelayPointType getPoint() {
        return point;
    }


    /**
     * Sets the point value for this DelayDetail.
     * 
     * @param point   * The point where the delay is occurring (e.g. Origin, Destination,
     * Broker location)
     */
    public void setPoint(com.fedex.ws.rate.v22.DelayPointType point) {
        this.point = point;
    }


    /**
     * Gets the type value for this DelayDetail.
     * 
     * @return type   * The reason for the delay (e.g. holiday, weekend, etc.).
     */
    public com.fedex.ws.rate.v22.CommitmentDelayType getType() {
        return type;
    }


    /**
     * Sets the type value for this DelayDetail.
     * 
     * @param type   * The reason for the delay (e.g. holiday, weekend, etc.).
     */
    public void setType(com.fedex.ws.rate.v22.CommitmentDelayType type) {
        this.type = type;
    }


    /**
     * Gets the description value for this DelayDetail.
     * 
     * @return description   * The name of the holiday in that country that is causing the
     * delay.
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this DelayDetail.
     * 
     * @param description   * The name of the holiday in that country that is causing the
     * delay.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DelayDetail)) return false;
        DelayDetail other = (DelayDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.dayOfWeek==null && other.getDayOfWeek()==null) || 
             (this.dayOfWeek!=null &&
              this.dayOfWeek.equals(other.getDayOfWeek()))) &&
            ((this.level==null && other.getLevel()==null) || 
             (this.level!=null &&
              this.level.equals(other.getLevel()))) &&
            ((this.point==null && other.getPoint()==null) || 
             (this.point!=null &&
              this.point.equals(other.getPoint()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription())));
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
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getDayOfWeek() != null) {
            _hashCode += getDayOfWeek().hashCode();
        }
        if (getLevel() != null) {
            _hashCode += getLevel().hashCode();
        }
        if (getPoint() != null) {
            _hashCode += getPoint().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DelayDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DelayDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dayOfWeek");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeek"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("level");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DelayLevelType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("point");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Point"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DelayPointType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CommitmentDelayType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
