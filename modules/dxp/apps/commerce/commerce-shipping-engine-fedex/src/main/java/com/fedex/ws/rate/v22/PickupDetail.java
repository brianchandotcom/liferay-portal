/**
 * PickupDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * This class describes the pickup characteristics of a shipment (e.g.
 * for use in a tag request).
 */
public class PickupDetail  implements java.io.Serializable {
    private java.util.Calendar readyDateTime;

    private java.util.Calendar latestPickupDateTime;

    private java.lang.String courierInstructions;

    private com.fedex.ws.rate.v22.PickupRequestType requestType;

    private com.fedex.ws.rate.v22.PickupRequestSourceType requestSource;

    public PickupDetail() {
    }

    public PickupDetail(
           java.util.Calendar readyDateTime,
           java.util.Calendar latestPickupDateTime,
           java.lang.String courierInstructions,
           com.fedex.ws.rate.v22.PickupRequestType requestType,
           com.fedex.ws.rate.v22.PickupRequestSourceType requestSource) {
           this.readyDateTime = readyDateTime;
           this.latestPickupDateTime = latestPickupDateTime;
           this.courierInstructions = courierInstructions;
           this.requestType = requestType;
           this.requestSource = requestSource;
    }


    /**
     * Gets the readyDateTime value for this PickupDetail.
     * 
     * @return readyDateTime
     */
    public java.util.Calendar getReadyDateTime() {
        return readyDateTime;
    }


    /**
     * Sets the readyDateTime value for this PickupDetail.
     * 
     * @param readyDateTime
     */
    public void setReadyDateTime(java.util.Calendar readyDateTime) {
        this.readyDateTime = readyDateTime;
    }


    /**
     * Gets the latestPickupDateTime value for this PickupDetail.
     * 
     * @return latestPickupDateTime
     */
    public java.util.Calendar getLatestPickupDateTime() {
        return latestPickupDateTime;
    }


    /**
     * Sets the latestPickupDateTime value for this PickupDetail.
     * 
     * @param latestPickupDateTime
     */
    public void setLatestPickupDateTime(java.util.Calendar latestPickupDateTime) {
        this.latestPickupDateTime = latestPickupDateTime;
    }


    /**
     * Gets the courierInstructions value for this PickupDetail.
     * 
     * @return courierInstructions
     */
    public java.lang.String getCourierInstructions() {
        return courierInstructions;
    }


    /**
     * Sets the courierInstructions value for this PickupDetail.
     * 
     * @param courierInstructions
     */
    public void setCourierInstructions(java.lang.String courierInstructions) {
        this.courierInstructions = courierInstructions;
    }


    /**
     * Gets the requestType value for this PickupDetail.
     * 
     * @return requestType
     */
    public com.fedex.ws.rate.v22.PickupRequestType getRequestType() {
        return requestType;
    }


    /**
     * Sets the requestType value for this PickupDetail.
     * 
     * @param requestType
     */
    public void setRequestType(com.fedex.ws.rate.v22.PickupRequestType requestType) {
        this.requestType = requestType;
    }


    /**
     * Gets the requestSource value for this PickupDetail.
     * 
     * @return requestSource
     */
    public com.fedex.ws.rate.v22.PickupRequestSourceType getRequestSource() {
        return requestSource;
    }


    /**
     * Sets the requestSource value for this PickupDetail.
     * 
     * @param requestSource
     */
    public void setRequestSource(com.fedex.ws.rate.v22.PickupRequestSourceType requestSource) {
        this.requestSource = requestSource;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PickupDetail)) return false;
        PickupDetail other = (PickupDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.readyDateTime==null && other.getReadyDateTime()==null) || 
             (this.readyDateTime!=null &&
              this.readyDateTime.equals(other.getReadyDateTime()))) &&
            ((this.latestPickupDateTime==null && other.getLatestPickupDateTime()==null) || 
             (this.latestPickupDateTime!=null &&
              this.latestPickupDateTime.equals(other.getLatestPickupDateTime()))) &&
            ((this.courierInstructions==null && other.getCourierInstructions()==null) || 
             (this.courierInstructions!=null &&
              this.courierInstructions.equals(other.getCourierInstructions()))) &&
            ((this.requestType==null && other.getRequestType()==null) || 
             (this.requestType!=null &&
              this.requestType.equals(other.getRequestType()))) &&
            ((this.requestSource==null && other.getRequestSource()==null) || 
             (this.requestSource!=null &&
              this.requestSource.equals(other.getRequestSource())));
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
        if (getReadyDateTime() != null) {
            _hashCode += getReadyDateTime().hashCode();
        }
        if (getLatestPickupDateTime() != null) {
            _hashCode += getLatestPickupDateTime().hashCode();
        }
        if (getCourierInstructions() != null) {
            _hashCode += getCourierInstructions().hashCode();
        }
        if (getRequestType() != null) {
            _hashCode += getRequestType().hashCode();
        }
        if (getRequestSource() != null) {
            _hashCode += getRequestSource().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PickupDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PickupDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("readyDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ReadyDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latestPickupDateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LatestPickupDateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("courierInstructions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CourierInstructions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PickupRequestType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestSource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RequestSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PickupRequestSourceType"));
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
