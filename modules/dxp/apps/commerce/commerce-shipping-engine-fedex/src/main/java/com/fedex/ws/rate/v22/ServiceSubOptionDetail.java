/**
 * ServiceSubOptionDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Supporting detail for applied options identified in a rate quote.
 */
public class ServiceSubOptionDetail  implements java.io.Serializable {
    /* Identifies the type of Freight Guarantee applied, if FREIGHT_GUARANTEE
     * is applied to the rate quote. */
    private com.fedex.ws.rate.v22.FreightGuaranteeType freightGuarantee;

    /* Identifies the smartPostHubId used during rate quote, if SMART_POST_HUB_ID
     * is a variable option on the rate request. */
    private java.lang.String smartPostHubId;

    /* Identifies the indicia used during rate quote, if SMART_POST_ALLOWED_INDICIA
     * is a variable option on the rate request. */
    private com.fedex.ws.rate.v22.SmartPostIndiciaType smartPostIndicia;

    public ServiceSubOptionDetail() {
    }

    public ServiceSubOptionDetail(
           com.fedex.ws.rate.v22.FreightGuaranteeType freightGuarantee,
           java.lang.String smartPostHubId,
           com.fedex.ws.rate.v22.SmartPostIndiciaType smartPostIndicia) {
           this.freightGuarantee = freightGuarantee;
           this.smartPostHubId = smartPostHubId;
           this.smartPostIndicia = smartPostIndicia;
    }


    /**
     * Gets the freightGuarantee value for this ServiceSubOptionDetail.
     * 
     * @return freightGuarantee   * Identifies the type of Freight Guarantee applied, if FREIGHT_GUARANTEE
     * is applied to the rate quote.
     */
    public com.fedex.ws.rate.v22.FreightGuaranteeType getFreightGuarantee() {
        return freightGuarantee;
    }


    /**
     * Sets the freightGuarantee value for this ServiceSubOptionDetail.
     * 
     * @param freightGuarantee   * Identifies the type of Freight Guarantee applied, if FREIGHT_GUARANTEE
     * is applied to the rate quote.
     */
    public void setFreightGuarantee(com.fedex.ws.rate.v22.FreightGuaranteeType freightGuarantee) {
        this.freightGuarantee = freightGuarantee;
    }


    /**
     * Gets the smartPostHubId value for this ServiceSubOptionDetail.
     * 
     * @return smartPostHubId   * Identifies the smartPostHubId used during rate quote, if SMART_POST_HUB_ID
     * is a variable option on the rate request.
     */
    public java.lang.String getSmartPostHubId() {
        return smartPostHubId;
    }


    /**
     * Sets the smartPostHubId value for this ServiceSubOptionDetail.
     * 
     * @param smartPostHubId   * Identifies the smartPostHubId used during rate quote, if SMART_POST_HUB_ID
     * is a variable option on the rate request.
     */
    public void setSmartPostHubId(java.lang.String smartPostHubId) {
        this.smartPostHubId = smartPostHubId;
    }


    /**
     * Gets the smartPostIndicia value for this ServiceSubOptionDetail.
     * 
     * @return smartPostIndicia   * Identifies the indicia used during rate quote, if SMART_POST_ALLOWED_INDICIA
     * is a variable option on the rate request.
     */
    public com.fedex.ws.rate.v22.SmartPostIndiciaType getSmartPostIndicia() {
        return smartPostIndicia;
    }


    /**
     * Sets the smartPostIndicia value for this ServiceSubOptionDetail.
     * 
     * @param smartPostIndicia   * Identifies the indicia used during rate quote, if SMART_POST_ALLOWED_INDICIA
     * is a variable option on the rate request.
     */
    public void setSmartPostIndicia(com.fedex.ws.rate.v22.SmartPostIndiciaType smartPostIndicia) {
        this.smartPostIndicia = smartPostIndicia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ServiceSubOptionDetail)) return false;
        ServiceSubOptionDetail other = (ServiceSubOptionDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.freightGuarantee==null && other.getFreightGuarantee()==null) || 
             (this.freightGuarantee!=null &&
              this.freightGuarantee.equals(other.getFreightGuarantee()))) &&
            ((this.smartPostHubId==null && other.getSmartPostHubId()==null) || 
             (this.smartPostHubId!=null &&
              this.smartPostHubId.equals(other.getSmartPostHubId()))) &&
            ((this.smartPostIndicia==null && other.getSmartPostIndicia()==null) || 
             (this.smartPostIndicia!=null &&
              this.smartPostIndicia.equals(other.getSmartPostIndicia())));
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
        if (getFreightGuarantee() != null) {
            _hashCode += getFreightGuarantee().hashCode();
        }
        if (getSmartPostHubId() != null) {
            _hashCode += getSmartPostHubId().hashCode();
        }
        if (getSmartPostIndicia() != null) {
            _hashCode += getSmartPostIndicia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceSubOptionDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceSubOptionDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightGuarantee");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightGuarantee"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightGuaranteeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smartPostHubId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SmartPostHubId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("smartPostIndicia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SmartPostIndicia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "SmartPostIndiciaType"));
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
