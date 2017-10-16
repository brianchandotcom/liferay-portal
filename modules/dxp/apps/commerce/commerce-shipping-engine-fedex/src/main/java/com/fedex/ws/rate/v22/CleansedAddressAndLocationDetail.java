/**
 * CleansedAddressAndLocationDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class CleansedAddressAndLocationDetail  implements java.io.Serializable {
    /* This represents the internal FedEx-system recognized country
     * code. */
    private java.lang.String countryCode;

    /* This represents the internal FedEx-system recognized state
     * or province code. */
    private java.lang.String stateOrProvinceCode;

    /* This represents the internal FedEx-system recognized postal
     * code. */
    private java.lang.String postalCode;

    private java.lang.String serviceArea;

    /* The unique location identifier */
    private java.lang.String locationId;

    /* The op-co specific numeric identifier for a location */
    private java.lang.Integer locationNumber;

    private java.lang.String airportId;

    public CleansedAddressAndLocationDetail() {
    }

    public CleansedAddressAndLocationDetail(
           java.lang.String countryCode,
           java.lang.String stateOrProvinceCode,
           java.lang.String postalCode,
           java.lang.String serviceArea,
           java.lang.String locationId,
           java.lang.Integer locationNumber,
           java.lang.String airportId) {
           this.countryCode = countryCode;
           this.stateOrProvinceCode = stateOrProvinceCode;
           this.postalCode = postalCode;
           this.serviceArea = serviceArea;
           this.locationId = locationId;
           this.locationNumber = locationNumber;
           this.airportId = airportId;
    }


    /**
     * Gets the countryCode value for this CleansedAddressAndLocationDetail.
     * 
     * @return countryCode   * This represents the internal FedEx-system recognized country
     * code.
     */
    public java.lang.String getCountryCode() {
        return countryCode;
    }


    /**
     * Sets the countryCode value for this CleansedAddressAndLocationDetail.
     * 
     * @param countryCode   * This represents the internal FedEx-system recognized country
     * code.
     */
    public void setCountryCode(java.lang.String countryCode) {
        this.countryCode = countryCode;
    }


    /**
     * Gets the stateOrProvinceCode value for this CleansedAddressAndLocationDetail.
     * 
     * @return stateOrProvinceCode   * This represents the internal FedEx-system recognized state
     * or province code.
     */
    public java.lang.String getStateOrProvinceCode() {
        return stateOrProvinceCode;
    }


    /**
     * Sets the stateOrProvinceCode value for this CleansedAddressAndLocationDetail.
     * 
     * @param stateOrProvinceCode   * This represents the internal FedEx-system recognized state
     * or province code.
     */
    public void setStateOrProvinceCode(java.lang.String stateOrProvinceCode) {
        this.stateOrProvinceCode = stateOrProvinceCode;
    }


    /**
     * Gets the postalCode value for this CleansedAddressAndLocationDetail.
     * 
     * @return postalCode   * This represents the internal FedEx-system recognized postal
     * code.
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this CleansedAddressAndLocationDetail.
     * 
     * @param postalCode   * This represents the internal FedEx-system recognized postal
     * code.
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the serviceArea value for this CleansedAddressAndLocationDetail.
     * 
     * @return serviceArea
     */
    public java.lang.String getServiceArea() {
        return serviceArea;
    }


    /**
     * Sets the serviceArea value for this CleansedAddressAndLocationDetail.
     * 
     * @param serviceArea
     */
    public void setServiceArea(java.lang.String serviceArea) {
        this.serviceArea = serviceArea;
    }


    /**
     * Gets the locationId value for this CleansedAddressAndLocationDetail.
     * 
     * @return locationId   * The unique location identifier
     */
    public java.lang.String getLocationId() {
        return locationId;
    }


    /**
     * Sets the locationId value for this CleansedAddressAndLocationDetail.
     * 
     * @param locationId   * The unique location identifier
     */
    public void setLocationId(java.lang.String locationId) {
        this.locationId = locationId;
    }


    /**
     * Gets the locationNumber value for this CleansedAddressAndLocationDetail.
     * 
     * @return locationNumber   * The op-co specific numeric identifier for a location
     */
    public java.lang.Integer getLocationNumber() {
        return locationNumber;
    }


    /**
     * Sets the locationNumber value for this CleansedAddressAndLocationDetail.
     * 
     * @param locationNumber   * The op-co specific numeric identifier for a location
     */
    public void setLocationNumber(java.lang.Integer locationNumber) {
        this.locationNumber = locationNumber;
    }


    /**
     * Gets the airportId value for this CleansedAddressAndLocationDetail.
     * 
     * @return airportId
     */
    public java.lang.String getAirportId() {
        return airportId;
    }


    /**
     * Sets the airportId value for this CleansedAddressAndLocationDetail.
     * 
     * @param airportId
     */
    public void setAirportId(java.lang.String airportId) {
        this.airportId = airportId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CleansedAddressAndLocationDetail)) return false;
        CleansedAddressAndLocationDetail other = (CleansedAddressAndLocationDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.countryCode==null && other.getCountryCode()==null) || 
             (this.countryCode!=null &&
              this.countryCode.equals(other.getCountryCode()))) &&
            ((this.stateOrProvinceCode==null && other.getStateOrProvinceCode()==null) || 
             (this.stateOrProvinceCode!=null &&
              this.stateOrProvinceCode.equals(other.getStateOrProvinceCode()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.serviceArea==null && other.getServiceArea()==null) || 
             (this.serviceArea!=null &&
              this.serviceArea.equals(other.getServiceArea()))) &&
            ((this.locationId==null && other.getLocationId()==null) || 
             (this.locationId!=null &&
              this.locationId.equals(other.getLocationId()))) &&
            ((this.locationNumber==null && other.getLocationNumber()==null) || 
             (this.locationNumber!=null &&
              this.locationNumber.equals(other.getLocationNumber()))) &&
            ((this.airportId==null && other.getAirportId()==null) || 
             (this.airportId!=null &&
              this.airportId.equals(other.getAirportId())));
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
        if (getCountryCode() != null) {
            _hashCode += getCountryCode().hashCode();
        }
        if (getStateOrProvinceCode() != null) {
            _hashCode += getStateOrProvinceCode().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getServiceArea() != null) {
            _hashCode += getServiceArea().hashCode();
        }
        if (getLocationId() != null) {
            _hashCode += getLocationId().hashCode();
        }
        if (getLocationNumber() != null) {
            _hashCode += getLocationNumber().hashCode();
        }
        if (getAirportId() != null) {
            _hashCode += getAirportId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CleansedAddressAndLocationDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CleansedAddressAndLocationDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CountryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateOrProvinceCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "StateOrProvinceCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PostalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ServiceArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("locationNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "LocationNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("airportId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AirportId"));
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
