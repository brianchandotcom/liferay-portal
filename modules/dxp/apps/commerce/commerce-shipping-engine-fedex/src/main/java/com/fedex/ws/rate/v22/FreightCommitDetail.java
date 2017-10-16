/**
 * FreightCommitDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Information about the Freight Service Centers associated with this
 * shipment.
 */
public class FreightCommitDetail  implements java.io.Serializable {
    /* Information about the origin Freight Service Center. */
    private com.fedex.ws.rate.v22.FreightServiceCenterDetail originDetail;

    /* Information about the destination Freight Service Center. */
    private com.fedex.ws.rate.v22.FreightServiceCenterDetail destinationDetail;

    /* The distance between the origin and destination FreightService
     * Centers */
    private com.fedex.ws.rate.v22.Distance totalDistance;

    public FreightCommitDetail() {
    }

    public FreightCommitDetail(
           com.fedex.ws.rate.v22.FreightServiceCenterDetail originDetail,
           com.fedex.ws.rate.v22.FreightServiceCenterDetail destinationDetail,
           com.fedex.ws.rate.v22.Distance totalDistance) {
           this.originDetail = originDetail;
           this.destinationDetail = destinationDetail;
           this.totalDistance = totalDistance;
    }


    /**
     * Gets the originDetail value for this FreightCommitDetail.
     * 
     * @return originDetail   * Information about the origin Freight Service Center.
     */
    public com.fedex.ws.rate.v22.FreightServiceCenterDetail getOriginDetail() {
        return originDetail;
    }


    /**
     * Sets the originDetail value for this FreightCommitDetail.
     * 
     * @param originDetail   * Information about the origin Freight Service Center.
     */
    public void setOriginDetail(com.fedex.ws.rate.v22.FreightServiceCenterDetail originDetail) {
        this.originDetail = originDetail;
    }


    /**
     * Gets the destinationDetail value for this FreightCommitDetail.
     * 
     * @return destinationDetail   * Information about the destination Freight Service Center.
     */
    public com.fedex.ws.rate.v22.FreightServiceCenterDetail getDestinationDetail() {
        return destinationDetail;
    }


    /**
     * Sets the destinationDetail value for this FreightCommitDetail.
     * 
     * @param destinationDetail   * Information about the destination Freight Service Center.
     */
    public void setDestinationDetail(com.fedex.ws.rate.v22.FreightServiceCenterDetail destinationDetail) {
        this.destinationDetail = destinationDetail;
    }


    /**
     * Gets the totalDistance value for this FreightCommitDetail.
     * 
     * @return totalDistance   * The distance between the origin and destination FreightService
     * Centers
     */
    public com.fedex.ws.rate.v22.Distance getTotalDistance() {
        return totalDistance;
    }


    /**
     * Sets the totalDistance value for this FreightCommitDetail.
     * 
     * @param totalDistance   * The distance between the origin and destination FreightService
     * Centers
     */
    public void setTotalDistance(com.fedex.ws.rate.v22.Distance totalDistance) {
        this.totalDistance = totalDistance;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightCommitDetail)) return false;
        FreightCommitDetail other = (FreightCommitDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.originDetail==null && other.getOriginDetail()==null) || 
             (this.originDetail!=null &&
              this.originDetail.equals(other.getOriginDetail()))) &&
            ((this.destinationDetail==null && other.getDestinationDetail()==null) || 
             (this.destinationDetail!=null &&
              this.destinationDetail.equals(other.getDestinationDetail()))) &&
            ((this.totalDistance==null && other.getTotalDistance()==null) || 
             (this.totalDistance!=null &&
              this.totalDistance.equals(other.getTotalDistance())));
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
        if (getOriginDetail() != null) {
            _hashCode += getOriginDetail().hashCode();
        }
        if (getDestinationDetail() != null) {
            _hashCode += getDestinationDetail().hashCode();
        }
        if (getTotalDistance() != null) {
            _hashCode += getTotalDistance().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightCommitDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightCommitDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "OriginDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightServiceCenterDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationDetail");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationDetail"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightServiceCenterDetail"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalDistance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TotalDistance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Distance"));
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
