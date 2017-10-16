/**
 * CustomDeliveryWindowDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CustomDeliveryWindowDetail  implements java.io.Serializable {
    /* Indicates the type of custom delivery being requested. */
    private com.fedex.ws.rate.v22.CustomDeliveryWindowType type;

    /* Time by which delivery is requested. */
    private org.apache.axis.types.Time requestTime;

    /* Range of dates for custom delivery request; only used if type
     * is BETWEEN. */
    private com.fedex.ws.rate.v22.DateRange requestRange;

    /* Date for custom delivery request; only used for types of ON,
     * BETWEEN, or AFTER. */
    private java.util.Date requestDate;

    public CustomDeliveryWindowDetail() {
    }

    public CustomDeliveryWindowDetail(
           com.fedex.ws.rate.v22.CustomDeliveryWindowType type,
           org.apache.axis.types.Time requestTime,
           com.fedex.ws.rate.v22.DateRange requestRange,
           java.util.Date requestDate) {
           this.type = type;
           this.requestTime = requestTime;
           this.requestRange = requestRange;
           this.requestDate = requestDate;
    }


    /**
     * Gets the type value for this CustomDeliveryWindowDetail.
     * 
     * @return type   * Indicates the type of custom delivery being requested.
     */
    public com.fedex.ws.rate.v22.CustomDeliveryWindowType getType() {
        return type;
    }


    /**
     * Sets the type value for this CustomDeliveryWindowDetail.
     * 
     * @param type   * Indicates the type of custom delivery being requested.
     */
    public void setType(com.fedex.ws.rate.v22.CustomDeliveryWindowType type) {
        this.type = type;
    }


    /**
     * Gets the requestTime value for this CustomDeliveryWindowDetail.
     * 
     * @return requestTime   * Time by which delivery is requested.
     */
    public org.apache.axis.types.Time getRequestTime() {
        return requestTime;
    }


    /**
     * Sets the requestTime value for this CustomDeliveryWindowDetail.
     * 
     * @param requestTime   * Time by which delivery is requested.
     */
    public void setRequestTime(org.apache.axis.types.Time requestTime) {
        this.requestTime = requestTime;
    }


    /**
     * Gets the requestRange value for this CustomDeliveryWindowDetail.
     * 
     * @return requestRange   * Range of dates for custom delivery request; only used if type
     * is BETWEEN.
     */
    public com.fedex.ws.rate.v22.DateRange getRequestRange() {
        return requestRange;
    }


    /**
     * Sets the requestRange value for this CustomDeliveryWindowDetail.
     * 
     * @param requestRange   * Range of dates for custom delivery request; only used if type
     * is BETWEEN.
     */
    public void setRequestRange(com.fedex.ws.rate.v22.DateRange requestRange) {
        this.requestRange = requestRange;
    }


    /**
     * Gets the requestDate value for this CustomDeliveryWindowDetail.
     * 
     * @return requestDate   * Date for custom delivery request; only used for types of ON,
     * BETWEEN, or AFTER.
     */
    public java.util.Date getRequestDate() {
        return requestDate;
    }


    /**
     * Sets the requestDate value for this CustomDeliveryWindowDetail.
     * 
     * @param requestDate   * Date for custom delivery request; only used for types of ON,
     * BETWEEN, or AFTER.
     */
    public void setRequestDate(java.util.Date requestDate) {
        this.requestDate = requestDate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomDeliveryWindowDetail)) return false;
        CustomDeliveryWindowDetail other = (CustomDeliveryWindowDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.requestTime==null && other.getRequestTime()==null) || 
             (this.requestTime!=null &&
              this.requestTime.equals(other.getRequestTime()))) &&
            ((this.requestRange==null && other.getRequestRange()==null) || 
             (this.requestRange!=null &&
              this.requestRange.equals(other.getRequestRange()))) &&
            ((this.requestDate==null && other.getRequestDate()==null) || 
             (this.requestDate!=null &&
              this.requestDate.equals(other.getRequestDate())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getRequestTime() != null) {
            _hashCode += getRequestTime().hashCode();
        }
        if (getRequestRange() != null) {
            _hashCode += getRequestRange().hashCode();
        }
        if (getRequestDate() != null) {
            _hashCode += getRequestDate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomDeliveryWindowDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomDeliveryWindowDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomDeliveryWindowType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "time"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestRange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestRange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DateRange"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
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
