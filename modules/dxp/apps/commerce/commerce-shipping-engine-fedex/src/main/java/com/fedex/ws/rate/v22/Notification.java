/**
 * Notification.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * The descriptive data regarding the result of the submitted transaction.
 */
public class Notification  implements java.io.Serializable {
    /* The severity of this notification. This can indicate success
     * or failure or some other information about the request. The values
     * that can be returned are SUCCESS - Your transaction succeeded with
     * no other applicable information. NOTE - Additional information that
     * may be of interest to you about your transaction. WARNING - Additional
     * information that you need to know about your transaction that you
     * may need to take action on. ERROR - Information about an error that
     * occurred while processing your transaction. FAILURE - FedEx was unable
     * to process your transaction at this time due to a system failure.
     * Please try again later */
    private com.fedex.ws.rate.v22.NotificationSeverityType severity;

    /* Indicates the source of this notification. Combined with the
     * Code it uniquely identifies this notification */
    private java.lang.String source;

    /* A code that represents this notification. Combined with the
     * Source it uniquely identifies this notification. */
    private java.lang.String code;

    /* Human-readable text that explains this notification. */
    private java.lang.String message;

    /* The translated message. The language and locale specified in
     * the ClientDetail. Localization are used to determine the representation.
     * Currently only supported in a TrackReply. */
    private java.lang.String localizedMessage;

    /* A collection of name/value pairs that provide specific data
     * to help the client determine the nature of an error (or warning, etc.)
     * without having to parse the message string. */
    private com.fedex.ws.rate.v22.NotificationParameter[] messageParameters;

    public Notification() {
    }

    public Notification(
           com.fedex.ws.rate.v22.NotificationSeverityType severity,
           java.lang.String source,
           java.lang.String code,
           java.lang.String message,
           java.lang.String localizedMessage,
           com.fedex.ws.rate.v22.NotificationParameter[] messageParameters) {
           this.severity = severity;
           this.source = source;
           this.code = code;
           this.message = message;
           this.localizedMessage = localizedMessage;
           this.messageParameters = messageParameters;
    }


    /**
     * Gets the severity value for this Notification.
     * 
     * @return severity   * The severity of this notification. This can indicate success
     * or failure or some other information about the request. The values
     * that can be returned are SUCCESS - Your transaction succeeded with
     * no other applicable information. NOTE - Additional information that
     * may be of interest to you about your transaction. WARNING - Additional
     * information that you need to know about your transaction that you
     * may need to take action on. ERROR - Information about an error that
     * occurred while processing your transaction. FAILURE - FedEx was unable
     * to process your transaction at this time due to a system failure.
     * Please try again later
     */
    public com.fedex.ws.rate.v22.NotificationSeverityType getSeverity() {
        return severity;
    }


    /**
     * Sets the severity value for this Notification.
     * 
     * @param severity   * The severity of this notification. This can indicate success
     * or failure or some other information about the request. The values
     * that can be returned are SUCCESS - Your transaction succeeded with
     * no other applicable information. NOTE - Additional information that
     * may be of interest to you about your transaction. WARNING - Additional
     * information that you need to know about your transaction that you
     * may need to take action on. ERROR - Information about an error that
     * occurred while processing your transaction. FAILURE - FedEx was unable
     * to process your transaction at this time due to a system failure.
     * Please try again later
     */
    public void setSeverity(com.fedex.ws.rate.v22.NotificationSeverityType severity) {
        this.severity = severity;
    }


    /**
     * Gets the source value for this Notification.
     * 
     * @return source   * Indicates the source of this notification. Combined with the
     * Code it uniquely identifies this notification
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this Notification.
     * 
     * @param source   * Indicates the source of this notification. Combined with the
     * Code it uniquely identifies this notification
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the code value for this Notification.
     * 
     * @return code   * A code that represents this notification. Combined with the
     * Source it uniquely identifies this notification.
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this Notification.
     * 
     * @param code   * A code that represents this notification. Combined with the
     * Source it uniquely identifies this notification.
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the message value for this Notification.
     * 
     * @return message   * Human-readable text that explains this notification.
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Notification.
     * 
     * @param message   * Human-readable text that explains this notification.
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the localizedMessage value for this Notification.
     * 
     * @return localizedMessage   * The translated message. The language and locale specified in
     * the ClientDetail. Localization are used to determine the representation.
     * Currently only supported in a TrackReply.
     */
    public java.lang.String getLocalizedMessage() {
        return localizedMessage;
    }


    /**
     * Sets the localizedMessage value for this Notification.
     * 
     * @param localizedMessage   * The translated message. The language and locale specified in
     * the ClientDetail. Localization are used to determine the representation.
     * Currently only supported in a TrackReply.
     */
    public void setLocalizedMessage(java.lang.String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }


    /**
     * Gets the messageParameters value for this Notification.
     * 
     * @return messageParameters   * A collection of name/value pairs that provide specific data
     * to help the client determine the nature of an error (or warning, etc.)
     * without having to parse the message string.
     */
    public com.fedex.ws.rate.v22.NotificationParameter[] getMessageParameters() {
        return messageParameters;
    }


    /**
     * Sets the messageParameters value for this Notification.
     * 
     * @param messageParameters   * A collection of name/value pairs that provide specific data
     * to help the client determine the nature of an error (or warning, etc.)
     * without having to parse the message string.
     */
    public void setMessageParameters(com.fedex.ws.rate.v22.NotificationParameter[] messageParameters) {
        this.messageParameters = messageParameters;
    }

    public com.fedex.ws.rate.v22.NotificationParameter getMessageParameters(int i) {
        return this.messageParameters[i];
    }

    public void setMessageParameters(int i, com.fedex.ws.rate.v22.NotificationParameter _value) {
        this.messageParameters[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Notification)) return false;
        Notification other = (Notification) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.severity==null && other.getSeverity()==null) || 
             (this.severity!=null &&
              this.severity.equals(other.getSeverity()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.localizedMessage==null && other.getLocalizedMessage()==null) || 
             (this.localizedMessage!=null &&
              this.localizedMessage.equals(other.getLocalizedMessage()))) &&
            ((this.messageParameters==null && other.getMessageParameters()==null) || 
             (this.messageParameters!=null &&
              java.util.Arrays.equals(this.messageParameters, other.getMessageParameters())));
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
        if (getSeverity() != null) {
            _hashCode += getSeverity().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getLocalizedMessage() != null) {
            _hashCode += getLocalizedMessage().hashCode();
        }
        if (getMessageParameters() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMessageParameters());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMessageParameters(), i);
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
        new org.apache.axis.description.TypeDesc(Notification.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Notification"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("severity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Severity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationSeverityType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("source");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Source"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localizedMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocalizedMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("messageParameters");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "MessageParameters"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NotificationParameter"));
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
