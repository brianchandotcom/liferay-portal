/**
 * FreightShipmentLineItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Description of an individual commodity or class of content in a
 * shipment.
 */
public class FreightShipmentLineItem  implements java.io.Serializable {
    /* Freight class for this line item. */
    private com.fedex.ws.rate.v22.FreightClassType freightClass;

    /* Specification of handling-unit packaging for this commodity
     * or class line. */
    private com.fedex.ws.rate.v22.PhysicalPackagingType packaging;

    /* Number of pieces for this commodity or class line. */
    private org.apache.axis.types.NonNegativeInteger pieces;

    /* Customer-provided description for this commodity or class line. */
    private java.lang.String description;

    /* Weight for this commodity or class line. */
    private com.fedex.ws.rate.v22.Weight weight;

    private com.fedex.ws.rate.v22.Dimensions dimensions;

    /* Volume (cubic measure) for this commodity or class line. */
    private com.fedex.ws.rate.v22.Volume volume;

    public FreightShipmentLineItem() {
    }

    public FreightShipmentLineItem(
           com.fedex.ws.rate.v22.FreightClassType freightClass,
           com.fedex.ws.rate.v22.PhysicalPackagingType packaging,
           org.apache.axis.types.NonNegativeInteger pieces,
           java.lang.String description,
           com.fedex.ws.rate.v22.Weight weight,
           com.fedex.ws.rate.v22.Dimensions dimensions,
           com.fedex.ws.rate.v22.Volume volume) {
           this.freightClass = freightClass;
           this.packaging = packaging;
           this.pieces = pieces;
           this.description = description;
           this.weight = weight;
           this.dimensions = dimensions;
           this.volume = volume;
    }


    /**
     * Gets the freightClass value for this FreightShipmentLineItem.
     * 
     * @return freightClass   * Freight class for this line item.
     */
    public com.fedex.ws.rate.v22.FreightClassType getFreightClass() {
        return freightClass;
    }


    /**
     * Sets the freightClass value for this FreightShipmentLineItem.
     * 
     * @param freightClass   * Freight class for this line item.
     */
    public void setFreightClass(com.fedex.ws.rate.v22.FreightClassType freightClass) {
        this.freightClass = freightClass;
    }


    /**
     * Gets the packaging value for this FreightShipmentLineItem.
     * 
     * @return packaging   * Specification of handling-unit packaging for this commodity
     * or class line.
     */
    public com.fedex.ws.rate.v22.PhysicalPackagingType getPackaging() {
        return packaging;
    }


    /**
     * Sets the packaging value for this FreightShipmentLineItem.
     * 
     * @param packaging   * Specification of handling-unit packaging for this commodity
     * or class line.
     */
    public void setPackaging(com.fedex.ws.rate.v22.PhysicalPackagingType packaging) {
        this.packaging = packaging;
    }


    /**
     * Gets the pieces value for this FreightShipmentLineItem.
     * 
     * @return pieces   * Number of pieces for this commodity or class line.
     */
    public org.apache.axis.types.NonNegativeInteger getPieces() {
        return pieces;
    }


    /**
     * Sets the pieces value for this FreightShipmentLineItem.
     * 
     * @param pieces   * Number of pieces for this commodity or class line.
     */
    public void setPieces(org.apache.axis.types.NonNegativeInteger pieces) {
        this.pieces = pieces;
    }


    /**
     * Gets the description value for this FreightShipmentLineItem.
     * 
     * @return description   * Customer-provided description for this commodity or class line.
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this FreightShipmentLineItem.
     * 
     * @param description   * Customer-provided description for this commodity or class line.
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the weight value for this FreightShipmentLineItem.
     * 
     * @return weight   * Weight for this commodity or class line.
     */
    public com.fedex.ws.rate.v22.Weight getWeight() {
        return weight;
    }


    /**
     * Sets the weight value for this FreightShipmentLineItem.
     * 
     * @param weight   * Weight for this commodity or class line.
     */
    public void setWeight(com.fedex.ws.rate.v22.Weight weight) {
        this.weight = weight;
    }


    /**
     * Gets the dimensions value for this FreightShipmentLineItem.
     * 
     * @return dimensions
     */
    public com.fedex.ws.rate.v22.Dimensions getDimensions() {
        return dimensions;
    }


    /**
     * Sets the dimensions value for this FreightShipmentLineItem.
     * 
     * @param dimensions
     */
    public void setDimensions(com.fedex.ws.rate.v22.Dimensions dimensions) {
        this.dimensions = dimensions;
    }


    /**
     * Gets the volume value for this FreightShipmentLineItem.
     * 
     * @return volume   * Volume (cubic measure) for this commodity or class line.
     */
    public com.fedex.ws.rate.v22.Volume getVolume() {
        return volume;
    }


    /**
     * Sets the volume value for this FreightShipmentLineItem.
     * 
     * @param volume   * Volume (cubic measure) for this commodity or class line.
     */
    public void setVolume(com.fedex.ws.rate.v22.Volume volume) {
        this.volume = volume;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FreightShipmentLineItem)) return false;
        FreightShipmentLineItem other = (FreightShipmentLineItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.freightClass==null && other.getFreightClass()==null) || 
             (this.freightClass!=null &&
              this.freightClass.equals(other.getFreightClass()))) &&
            ((this.packaging==null && other.getPackaging()==null) || 
             (this.packaging!=null &&
              this.packaging.equals(other.getPackaging()))) &&
            ((this.pieces==null && other.getPieces()==null) || 
             (this.pieces!=null &&
              this.pieces.equals(other.getPieces()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.weight==null && other.getWeight()==null) || 
             (this.weight!=null &&
              this.weight.equals(other.getWeight()))) &&
            ((this.dimensions==null && other.getDimensions()==null) || 
             (this.dimensions!=null &&
              this.dimensions.equals(other.getDimensions()))) &&
            ((this.volume==null && other.getVolume()==null) || 
             (this.volume!=null &&
              this.volume.equals(other.getVolume())));
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
        if (getFreightClass() != null) {
            _hashCode += getFreightClass().hashCode();
        }
        if (getPackaging() != null) {
            _hashCode += getPackaging().hashCode();
        }
        if (getPieces() != null) {
            _hashCode += getPieces().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getWeight() != null) {
            _hashCode += getWeight().hashCode();
        }
        if (getDimensions() != null) {
            _hashCode += getDimensions().hashCode();
        }
        if (getVolume() != null) {
            _hashCode += getVolume().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FreightShipmentLineItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightShipmentLineItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("freightClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FreightClassType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packaging");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Packaging"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PhysicalPackagingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pieces");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Pieces"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("weight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Weight"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimensions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Dimensions"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volume");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Volume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Volume"));
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
