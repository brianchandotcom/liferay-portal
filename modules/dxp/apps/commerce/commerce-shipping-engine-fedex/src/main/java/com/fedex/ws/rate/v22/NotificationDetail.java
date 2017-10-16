/**
 * NotificationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class NotificationDetail  implements java.io.Serializable {
    /* Indicates the type of notification that will be sent. */
    private com.fedex.ws.rate.v22.NotificationType notificationType;

    /* Specifies the email notification details. */
    private com.fedex.ws.rate.v22.EMailDetail emailDetail;

    /* Specifies the localization for this notification. */
    private com.fedex.ws.rate.v22.Localization localization;

    public NotificationDetail() {
    }

    public NotificationDetail(
           com.fedex.ws.rate.v22.NotificationType notificationType,
           com.fedex.ws.rate.v22.EMailDetail emailDetail,
           com.fedex.ws.rate.v22.Localization localization) {
           this.notificationType = notificationType;
           this.emailDetail = emailDetail;
           this.localization = localization;
    }


    /**
     * Gets the notificationType value for this NotificationDetail.
     * 
     * @return notificationType   * Indicates the type of notification that will be sent.
     */
    public com.fedex.ws.rate.v22.NotificationType getNotificationType() {
        return notificationType;
    }


    /**
     * Sets the notificationType value for this NotificationDetail.
     * 
     * @param notificationType   * Indicates the type of notification that will be sent.
     */
    public void setNotificationType(com.fedex.ws.rate.v22.NotificationType notificationType) {
        this.notificationType = notificationType;
    }


    /**
     * Gets the emailDetail value for this NotificationDetail.
     * 
     * @return emailDetail   * Specifies the email notification details.
     */
    public com.fedex.ws.rate.v22.EMailDetail getEmailDetail() {
        return emailDetail;
    }


    /**
     * Sets the emailDetail value for this NotificationDetail.
     * 
     * @param emailDetail   * Specifies the email notification details.
     */
    public void setEmailDetail(com.fedex.ws.rate.v22.EMailDetail emailDetail) {
        this.emailDetail = emailDetail;
    }


    /**
     * Gets the localization value for this NotificationDetail.
     * 
     * @return localization   * Specifies the localization for this notification.
     */
    public com.fedex.ws.rate.v22.Localization getLocalization() {
        return localization;
    }


    /**
     * Sets the localization value for this NotificationDetail.
     * 
     * @param localization   * Specifies the localization for this notification.
     */
    public void setLocalization(com.fedex.ws.rate.v22.Localization localization) {
        this.localization = localization;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NotificationDetail)) return false;
        NotificationDetail other = (NotificationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.notificationType==null && other.getNotificationType()==null) || 
             (this.notificationType!=null &&
              this.notificationType.equals(other.getNotificationType()))) &&
            ((this.emailDetail==null && other.getEmailDetail()==null) || 
             (this.emailDetail!=null &&
              this.emailDetail.equals(other.getEmailDetail()))) &&
            ((this.localization==null && other.getLocalization()==null) || 
             (this.localization!=null &&
              this.localization.equals(other.getLocalization())));
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
        if (getNotificationType() != null) {
            _hashCode += getNotificationType().hashCode();
        }
        if (getEmailDetail() != null) {
            _hashCode += getEmailDetail().hashCode();
        }
        if (getLocalization() != null) {
            _hashCode += getLocalization().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NotificationDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("notificationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emailDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EmailDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EMailDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localization");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Localization"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Localization"));
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
