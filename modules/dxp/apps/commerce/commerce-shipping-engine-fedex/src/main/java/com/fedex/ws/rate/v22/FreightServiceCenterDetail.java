/**
 * FreightServiceCenterDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * This class describes the relationship between a customer-specified
 * address and the FedEx Freight / FedEx National Freight Service Center
 * that supports that address.
 */
public class FreightServiceCenterDetail  implements java.io.Serializable {
    /* Freight Industry standard non-FedEx carrier identification */
    private java.lang.String interlineCarrierCode;

    /* The name of the Interline carrier. */
    private java.lang.String interlineCarrierName;

    /* Additional time it might take at the origin or destination
     * to pickup or deliver the freight. This is usually due to the remoteness
     * of the location. This time is included in the total transit time. */
    private java.lang.Integer additionalDays;

    /* Service branding which may be used for local pickup or delivery,
     * distinct from service used for line-haul of customer's shipment. */
    private com.fedex.ws.rate.v22.ServiceType localService;

    /* Distance between customer address (pickup or delivery) and
     * the supporting Freight / National Freight service center. */
    private com.fedex.ws.rate.v22.Distance localDistance;

    /* Time to travel between customer address (pickup or delivery)
     * and the supporting Freight / National Freight service center. */
    private org.apache.axis.types.Duration localDuration;

    /* Specifies when/how the customer can arrange for pickup or delivery. */
    private com.fedex.ws.rate.v22.FreightServiceSchedulingType localServiceScheduling;

    /* Specifies days of operation if localServiceScheduling is LIMITED. */
    private com.fedex.ws.rate.v22.DayOfWeekType[] limitedServiceDays;

    /* Freight service center that is a gateway on the border of Canada
     * or Mexico. */
    private java.lang.String gatewayLocationId;

    /* Alphabetical code identifying a Freight Service Center */
    private java.lang.String location;

    /* Freight service center Contact and Address */
    private com.fedex.ws.rate.v22.ContactAndAddress contactAndAddress;

    public FreightServiceCenterDetail() {
    }

    public FreightServiceCenterDetail(
           java.lang.String interlineCarrierCode,
           java.lang.String interlineCarrierName,
           java.lang.Integer additionalDays,
           com.fedex.ws.rate.v22.ServiceType localService,
           com.fedex.ws.rate.v22.Distance localDistance,
           org.apache.axis.types.Duration localDuration,
           com.fedex.ws.rate.v22.FreightServiceSchedulingType localServiceScheduling,
           com.fedex.ws.rate.v22.DayOfWeekType[] limitedServiceDays,
           java.lang.String gatewayLocationId,
           java.lang.String location,
           com.fedex.ws.rate.v22.ContactAndAddress contactAndAddress) {
           this.interlineCarrierCode = interlineCarrierCode;
           this.interlineCarrierName = interlineCarrierName;
           this.additionalDays = additionalDays;
           this.localService = localService;
           this.localDistance = localDistance;
           this.localDuration = localDuration;
           this.localServiceScheduling = localServiceScheduling;
           this.limitedServiceDays = limitedServiceDays;
           this.gatewayLocationId = gatewayLocationId;
           this.location = location;
           this.contactAndAddress = contactAndAddress;
    }


    /**
     * Gets the interlineCarrierCode value for this FreightServiceCenterDetail.
     * 
     * @return interlineCarrierCode   * Freight Industry standard non-FedEx carrier identification
     */
    public java.lang.String getInterlineCarrierCode() {
        return interlineCarrierCode;
    }


    /**
     * Sets the interlineCarrierCode value for this FreightServiceCenterDetail.
     * 
     * @param interlineCarrierCode   * Freight Industry standard non-FedEx carrier identification
     */
    public void setInterlineCarrierCode(java.lang.String interlineCarrierCode) {
        this.interlineCarrierCode = interlineCarrierCode;
    }


    /**
     * Gets the interlineCarrierName value for this FreightServiceCenterDetail.
     * 
     * @return interlineCarrierName   * The name of the Interline carrier.
     */
    public java.lang.String getInterlineCarrierName() {
        return interlineCarrierName;
    }


    /**
     * Sets the interlineCarrierName value for this FreightServiceCenterDetail.
     * 
     * @param interlineCarrierName   * The name of the Interline carrier.
     */
    public void setInterlineCarrierName(java.lang.String interlineCarrierName) {
        this.interlineCarrierName = interlineCarrierName;
    }


    /**
     * Gets the additionalDays value for this FreightServiceCenterDetail.
     * 
     * @return additionalDays   * Additional time it might take at the origin or destination
     * to pickup or deliver the freight. This is usually due to the remoteness
     * of the location. This time is included in the total transit time.
     */
    public java.lang.Integer getAdditionalDays() {
        return additionalDays;
    }


    /**
     * Sets the additionalDays value for this FreightServiceCenterDetail.
     * 
     * @param additionalDays   * Additional time it might take at the origin or destination
     * to pickup or deliver the freight. This is usually due to the remoteness
     * of the location. This time is included in the total transit time.
     */
    public void setAdditionalDays(java.lang.Integer additionalDays) {
        this.additionalDays = additionalDays;
    }


    /**
     * Gets the localService value for this FreightServiceCenterDetail.
     * 
     * @return localService   * Service branding which may be used for local pickup or delivery,
     * distinct from service used for line-haul of customer's shipment.
     */
    public com.fedex.ws.rate.v22.ServiceType getLocalService() {
        return localService;
    }


    /**
     * Sets the localService value for this FreightServiceCenterDetail.
     * 
     * @param localService   * Service branding which may be used for local pickup or delivery,
     * distinct from service used for line-haul of customer's shipment.
     */
    public void setLocalService(com.fedex.ws.rate.v22.ServiceType localService) {
        this.localService = localService;
    }


    /**
     * Gets the localDistance value for this FreightServiceCenterDetail.
     * 
     * @return localDistance   * Distance between customer address (pickup or delivery) and
     * the supporting Freight / National Freight service center.
     */
    public com.fedex.ws.rate.v22.Distance getLocalDistance() {
        return localDistance;
    }


    /**
     * Sets the localDistance value for this FreightServiceCenterDetail.
     * 
     * @param localDistance   * Distance between customer address (pickup or delivery) and
     * the supporting Freight / National Freight service center.
     */
    public void setLocalDistance(com.fedex.ws.rate.v22.Distance localDistance) {
        this.localDistance = localDistance;
    }


    /**
     * Gets the localDuration value for this FreightServiceCenterDetail.
     * 
     * @return localDuration   * Time to travel between customer address (pickup or delivery)
     * and the supporting Freight / National Freight service center.
     */
    public org.apache.axis.types.Duration getLocalDuration() {
        return localDuration;
    }


    /**
     * Sets the localDuration value for this FreightServiceCenterDetail.
     * 
     * @param localDuration   * Time to travel between customer address (pickup or delivery)
     * and the supporting Freight / National Freight service center.
     */
    public void setLocalDuration(org.apache.axis.types.Duration localDuration) {
        this.localDuration = localDuration;
    }


    /**
     * Gets the localServiceScheduling value for this FreightServiceCenterDetail.
     * 
     * @return localServiceScheduling   * Specifies when/how the customer can arrange for pickup or delivery.
     */
    public com.fedex.ws.rate.v22.FreightServiceSchedulingType getLocalServiceScheduling() {
        return localServiceScheduling;
    }


    /**
     * Sets the localServiceScheduling value for this FreightServiceCenterDetail.
     * 
     * @param localServiceScheduling   * Specifies when/how the customer can arrange for pickup or delivery.
     */
    public void setLocalServiceScheduling(com.fedex.ws.rate.v22.FreightServiceSchedulingType localServiceScheduling) {
        this.localServiceScheduling = localServiceScheduling;
    }


    /**
     * Gets the limitedServiceDays value for this FreightServiceCenterDetail.
     * 
     * @return limitedServiceDays   * Specifies days of operation if localServiceScheduling is LIMITED.
     */
    public com.fedex.ws.rate.v22.DayOfWeekType[] getLimitedServiceDays() {
        return limitedServiceDays;
    }


    /**
     * Sets the limitedServiceDays value for this FreightServiceCenterDetail.
     * 
     * @param limitedServiceDays   * Specifies days of operation if localServiceScheduling is LIMITED.
     */
    public void setLimitedServiceDays(com.fedex.ws.rate.v22.DayOfWeekType[] limitedServiceDays) {
        this.limitedServiceDays = limitedServiceDays;
    }

    public com.fedex.ws.rate.v22.DayOfWeekType getLimitedServiceDays(int i) {
        return this.limitedServiceDays[i];
    }

    public void setLimitedServiceDays(int i, com.fedex.ws.rate.v22.DayOfWeekType _value) {
        this.limitedServiceDays[i] = _value;
    }


    /**
     * Gets the gatewayLocationId value for this FreightServiceCenterDetail.
     * 
     * @return gatewayLocationId   * Freight service center that is a gateway on the border of Canada
     * or Mexico.
     */
    public java.lang.String getGatewayLocationId() {
        return gatewayLocationId;
    }


    /**
     * Sets the gatewayLocationId value for this FreightServiceCenterDetail.
     * 
     * @param gatewayLocationId   * Freight service center that is a gateway on the border of Canada
     * or Mexico.
     */
    public void setGatewayLocationId(java.lang.String gatewayLocationId) {
        this.gatewayLocationId = gatewayLocationId;
    }


    /**
     * Gets the location value for this FreightServiceCenterDetail.
     * 
     * @return location   * Alphabetical code identifying a Freight Service Center
     */
    public java.lang.String getLocation() {
        return location;
    }


    /**
     * Sets the location value for this FreightServiceCenterDetail.
     * 
     * @param location   * Alphabetical code identifying a Freight Service Center
     */
    public void setLocation(java.lang.String location) {
        this.location = location;
    }


    /**
     * Gets the contactAndAddress value for this FreightServiceCenterDetail.
     * 
     * @return contactAndAddress   * Freight service center Contact and Address
     */
    public com.fedex.ws.rate.v22.ContactAndAddress getContactAndAddress() {
        return contactAndAddress;
    }


    /**
     * Sets the contactAndAddress value for this FreightServiceCenterDetail.
     * 
     * @param contactAndAddress   * Freight service center Contact and Address
     */
    public void setContactAndAddress(com.fedex.ws.rate.v22.ContactAndAddress contactAndAddress) {
        this.contactAndAddress = contactAndAddress;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightServiceCenterDetail)) return false;
        FreightServiceCenterDetail other = (FreightServiceCenterDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.interlineCarrierCode==null && other.getInterlineCarrierCode()==null) || 
             (this.interlineCarrierCode!=null &&
              this.interlineCarrierCode.equals(other.getInterlineCarrierCode()))) &&
            ((this.interlineCarrierName==null && other.getInterlineCarrierName()==null) || 
             (this.interlineCarrierName!=null &&
              this.interlineCarrierName.equals(other.getInterlineCarrierName()))) &&
            ((this.additionalDays==null && other.getAdditionalDays()==null) || 
             (this.additionalDays!=null &&
              this.additionalDays.equals(other.getAdditionalDays()))) &&
            ((this.localService==null && other.getLocalService()==null) || 
             (this.localService!=null &&
              this.localService.equals(other.getLocalService()))) &&
            ((this.localDistance==null && other.getLocalDistance()==null) || 
             (this.localDistance!=null &&
              this.localDistance.equals(other.getLocalDistance()))) &&
            ((this.localDuration==null && other.getLocalDuration()==null) || 
             (this.localDuration!=null &&
              this.localDuration.equals(other.getLocalDuration()))) &&
            ((this.localServiceScheduling==null && other.getLocalServiceScheduling()==null) || 
             (this.localServiceScheduling!=null &&
              this.localServiceScheduling.equals(other.getLocalServiceScheduling()))) &&
            ((this.limitedServiceDays==null && other.getLimitedServiceDays()==null) || 
             (this.limitedServiceDays!=null &&
              java.util.Arrays.equals(this.limitedServiceDays, other.getLimitedServiceDays()))) &&
            ((this.gatewayLocationId==null && other.getGatewayLocationId()==null) || 
             (this.gatewayLocationId!=null &&
              this.gatewayLocationId.equals(other.getGatewayLocationId()))) &&
            ((this.location==null && other.getLocation()==null) || 
             (this.location!=null &&
              this.location.equals(other.getLocation()))) &&
            ((this.contactAndAddress==null && other.getContactAndAddress()==null) || 
             (this.contactAndAddress!=null &&
              this.contactAndAddress.equals(other.getContactAndAddress())));
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
        if (getInterlineCarrierCode() != null) {
            _hashCode += getInterlineCarrierCode().hashCode();
        }
        if (getInterlineCarrierName() != null) {
            _hashCode += getInterlineCarrierName().hashCode();
        }
        if (getAdditionalDays() != null) {
            _hashCode += getAdditionalDays().hashCode();
        }
        if (getLocalService() != null) {
            _hashCode += getLocalService().hashCode();
        }
        if (getLocalDistance() != null) {
            _hashCode += getLocalDistance().hashCode();
        }
        if (getLocalDuration() != null) {
            _hashCode += getLocalDuration().hashCode();
        }
        if (getLocalServiceScheduling() != null) {
            _hashCode += getLocalServiceScheduling().hashCode();
        }
        if (getLimitedServiceDays() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLimitedServiceDays());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLimitedServiceDays(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGatewayLocationId() != null) {
            _hashCode += getGatewayLocationId().hashCode();
        }
        if (getLocation() != null) {
            _hashCode += getLocation().hashCode();
        }
        if (getContactAndAddress() != null) {
            _hashCode += getContactAndAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightServiceCenterDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightServiceCenterDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interlineCarrierCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "InterlineCarrierCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interlineCarrierName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "InterlineCarrierName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("additionalDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AdditionalDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localService");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocalService"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localDistance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocalDistance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Distance"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localDuration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocalDuration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "duration"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localServiceScheduling");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocalServiceScheduling"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightServiceSchedulingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitedServiceDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LimitedServiceDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DayOfWeekType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gatewayLocationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "GatewayLocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("location");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Location"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contactAndAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ContactAndAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ContactAndAddress"));
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
