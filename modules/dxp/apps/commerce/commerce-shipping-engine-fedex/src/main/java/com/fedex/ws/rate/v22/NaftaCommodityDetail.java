/**
 * NaftaCommodityDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class NaftaCommodityDetail  implements java.io.Serializable {
    /* Defined by NAFTA regulations. */
    private com.fedex.ws.rate.v22.NaftaPreferenceCriterionCode preferenceCriterion;

    /* Defined by NAFTA regulations. */
    private com.fedex.ws.rate.v22.NaftaProducerDeterminationCode producerDetermination;

    /* Identification of which producer is associated with this commodity
     * (if multiple producers are used in a single shipment). */
    private java.lang.String producerId;

    private com.fedex.ws.rate.v22.NaftaNetCostMethodCode netCostMethod;

    /* Date range over which RVC net cost was calculated. */
    private com.fedex.ws.rate.v22.DateRange netCostDateRange;

    public NaftaCommodityDetail() {
    }

    public NaftaCommodityDetail(
           com.fedex.ws.rate.v22.NaftaPreferenceCriterionCode preferenceCriterion,
           com.fedex.ws.rate.v22.NaftaProducerDeterminationCode producerDetermination,
           java.lang.String producerId,
           com.fedex.ws.rate.v22.NaftaNetCostMethodCode netCostMethod,
           com.fedex.ws.rate.v22.DateRange netCostDateRange) {
           this.preferenceCriterion = preferenceCriterion;
           this.producerDetermination = producerDetermination;
           this.producerId = producerId;
           this.netCostMethod = netCostMethod;
           this.netCostDateRange = netCostDateRange;
    }


    /**
     * Gets the preferenceCriterion value for this NaftaCommodityDetail.
     * 
     * @return preferenceCriterion   * Defined by NAFTA regulations.
     */
    public com.fedex.ws.rate.v22.NaftaPreferenceCriterionCode getPreferenceCriterion() {
        return preferenceCriterion;
    }


    /**
     * Sets the preferenceCriterion value for this NaftaCommodityDetail.
     * 
     * @param preferenceCriterion   * Defined by NAFTA regulations.
     */
    public void setPreferenceCriterion(com.fedex.ws.rate.v22.NaftaPreferenceCriterionCode preferenceCriterion) {
        this.preferenceCriterion = preferenceCriterion;
    }


    /**
     * Gets the producerDetermination value for this NaftaCommodityDetail.
     * 
     * @return producerDetermination   * Defined by NAFTA regulations.
     */
    public com.fedex.ws.rate.v22.NaftaProducerDeterminationCode getProducerDetermination() {
        return producerDetermination;
    }


    /**
     * Sets the producerDetermination value for this NaftaCommodityDetail.
     * 
     * @param producerDetermination   * Defined by NAFTA regulations.
     */
    public void setProducerDetermination(com.fedex.ws.rate.v22.NaftaProducerDeterminationCode producerDetermination) {
        this.producerDetermination = producerDetermination;
    }


    /**
     * Gets the producerId value for this NaftaCommodityDetail.
     * 
     * @return producerId   * Identification of which producer is associated with this commodity
     * (if multiple producers are used in a single shipment).
     */
    public java.lang.String getProducerId() {
        return producerId;
    }


    /**
     * Sets the producerId value for this NaftaCommodityDetail.
     * 
     * @param producerId   * Identification of which producer is associated with this commodity
     * (if multiple producers are used in a single shipment).
     */
    public void setProducerId(java.lang.String producerId) {
        this.producerId = producerId;
    }


    /**
     * Gets the netCostMethod value for this NaftaCommodityDetail.
     * 
     * @return netCostMethod
     */
    public com.fedex.ws.rate.v22.NaftaNetCostMethodCode getNetCostMethod() {
        return netCostMethod;
    }


    /**
     * Sets the netCostMethod value for this NaftaCommodityDetail.
     * 
     * @param netCostMethod
     */
    public void setNetCostMethod(com.fedex.ws.rate.v22.NaftaNetCostMethodCode netCostMethod) {
        this.netCostMethod = netCostMethod;
    }


    /**
     * Gets the netCostDateRange value for this NaftaCommodityDetail.
     * 
     * @return netCostDateRange   * Date range over which RVC net cost was calculated.
     */
    public com.fedex.ws.rate.v22.DateRange getNetCostDateRange() {
        return netCostDateRange;
    }


    /**
     * Sets the netCostDateRange value for this NaftaCommodityDetail.
     * 
     * @param netCostDateRange   * Date range over which RVC net cost was calculated.
     */
    public void setNetCostDateRange(com.fedex.ws.rate.v22.DateRange netCostDateRange) {
        this.netCostDateRange = netCostDateRange;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NaftaCommodityDetail)) return false;
        NaftaCommodityDetail other = (NaftaCommodityDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.preferenceCriterion==null && other.getPreferenceCriterion()==null) || 
             (this.preferenceCriterion!=null &&
              this.preferenceCriterion.equals(other.getPreferenceCriterion()))) &&
            ((this.producerDetermination==null && other.getProducerDetermination()==null) || 
             (this.producerDetermination!=null &&
              this.producerDetermination.equals(other.getProducerDetermination()))) &&
            ((this.producerId==null && other.getProducerId()==null) || 
             (this.producerId!=null &&
              this.producerId.equals(other.getProducerId()))) &&
            ((this.netCostMethod==null && other.getNetCostMethod()==null) || 
             (this.netCostMethod!=null &&
              this.netCostMethod.equals(other.getNetCostMethod()))) &&
            ((this.netCostDateRange==null && other.getNetCostDateRange()==null) || 
             (this.netCostDateRange!=null &&
              this.netCostDateRange.equals(other.getNetCostDateRange())));
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
        if (getPreferenceCriterion() != null) {
            _hashCode += getPreferenceCriterion().hashCode();
        }
        if (getProducerDetermination() != null) {
            _hashCode += getProducerDetermination().hashCode();
        }
        if (getProducerId() != null) {
            _hashCode += getProducerId().hashCode();
        }
        if (getNetCostMethod() != null) {
            _hashCode += getNetCostMethod().hashCode();
        }
        if (getNetCostDateRange() != null) {
            _hashCode += getNetCostDateRange().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NaftaCommodityDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaCommodityDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("preferenceCriterion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PreferenceCriterion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaPreferenceCriterionCode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producerDetermination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProducerDetermination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaProducerDeterminationCode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ProducerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netCostMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NetCostMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NaftaNetCostMethodCode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("netCostDateRange");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NetCostDateRange"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DateRange"));
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
