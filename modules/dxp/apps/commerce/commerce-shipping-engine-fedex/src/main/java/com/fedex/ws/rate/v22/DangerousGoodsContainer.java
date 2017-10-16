/**
 * DangerousGoodsContainer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Describes an approved container used to package dangerous goods
 * commodities. This does not describe any individual inner receptacles
 * that may be within this container.
 */
public class DangerousGoodsContainer  implements java.io.Serializable {
    /* Indicates whether there are additional inner receptacles within
     * this container. */
    private com.fedex.ws.rate.v22.HazardousContainerPackingType packingType;

    /* Indicates the type of this dangerous goods container, as specified
     * by the IATA packing instructions. For example, steel cylinder, fiberboard
     * box, plastic jerrican and steel drum. */
    private java.lang.String containerType;

    /* Indicates the packaging type of the container used to package
     * the radioactive materials. */
    private com.fedex.ws.rate.v22.RadioactiveContainerClassType radioactiveContainerClass;

    /* Indicates the number of occurrences of this container with
     * identical dangerous goods configuration. */
    private org.apache.axis.types.NonNegativeInteger numberOfContainers;

    /* Documents the kinds and quantities of all hazardous commodities
     * in the current container. */
    private com.fedex.ws.rate.v22.HazardousCommodityContent[] hazardousCommodities;

    public DangerousGoodsContainer() {
    }

    public DangerousGoodsContainer(
           com.fedex.ws.rate.v22.HazardousContainerPackingType packingType,
           java.lang.String containerType,
           com.fedex.ws.rate.v22.RadioactiveContainerClassType radioactiveContainerClass,
           org.apache.axis.types.NonNegativeInteger numberOfContainers,
           com.fedex.ws.rate.v22.HazardousCommodityContent[] hazardousCommodities) {
           this.packingType = packingType;
           this.containerType = containerType;
           this.radioactiveContainerClass = radioactiveContainerClass;
           this.numberOfContainers = numberOfContainers;
           this.hazardousCommodities = hazardousCommodities;
    }


    /**
     * Gets the packingType value for this DangerousGoodsContainer.
     * 
     * @return packingType   * Indicates whether there are additional inner receptacles within
     * this container.
     */
    public com.fedex.ws.rate.v22.HazardousContainerPackingType getPackingType() {
        return packingType;
    }


    /**
     * Sets the packingType value for this DangerousGoodsContainer.
     * 
     * @param packingType   * Indicates whether there are additional inner receptacles within
     * this container.
     */
    public void setPackingType(com.fedex.ws.rate.v22.HazardousContainerPackingType packingType) {
        this.packingType = packingType;
    }


    /**
     * Gets the containerType value for this DangerousGoodsContainer.
     * 
     * @return containerType   * Indicates the type of this dangerous goods container, as specified
     * by the IATA packing instructions. For example, steel cylinder, fiberboard
     * box, plastic jerrican and steel drum.
     */
    public java.lang.String getContainerType() {
        return containerType;
    }


    /**
     * Sets the containerType value for this DangerousGoodsContainer.
     * 
     * @param containerType   * Indicates the type of this dangerous goods container, as specified
     * by the IATA packing instructions. For example, steel cylinder, fiberboard
     * box, plastic jerrican and steel drum.
     */
    public void setContainerType(java.lang.String containerType) {
        this.containerType = containerType;
    }


    /**
     * Gets the radioactiveContainerClass value for this DangerousGoodsContainer.
     * 
     * @return radioactiveContainerClass   * Indicates the packaging type of the container used to package
     * the radioactive materials.
     */
    public com.fedex.ws.rate.v22.RadioactiveContainerClassType getRadioactiveContainerClass() {
        return radioactiveContainerClass;
    }


    /**
     * Sets the radioactiveContainerClass value for this DangerousGoodsContainer.
     * 
     * @param radioactiveContainerClass   * Indicates the packaging type of the container used to package
     * the radioactive materials.
     */
    public void setRadioactiveContainerClass(com.fedex.ws.rate.v22.RadioactiveContainerClassType radioactiveContainerClass) {
        this.radioactiveContainerClass = radioactiveContainerClass;
    }


    /**
     * Gets the numberOfContainers value for this DangerousGoodsContainer.
     * 
     * @return numberOfContainers   * Indicates the number of occurrences of this container with
     * identical dangerous goods configuration.
     */
    public org.apache.axis.types.NonNegativeInteger getNumberOfContainers() {
        return numberOfContainers;
    }


    /**
     * Sets the numberOfContainers value for this DangerousGoodsContainer.
     * 
     * @param numberOfContainers   * Indicates the number of occurrences of this container with
     * identical dangerous goods configuration.
     */
    public void setNumberOfContainers(org.apache.axis.types.NonNegativeInteger numberOfContainers) {
        this.numberOfContainers = numberOfContainers;
    }


    /**
     * Gets the hazardousCommodities value for this DangerousGoodsContainer.
     * 
     * @return hazardousCommodities   * Documents the kinds and quantities of all hazardous commodities
     * in the current container.
     */
    public com.fedex.ws.rate.v22.HazardousCommodityContent[] getHazardousCommodities() {
        return hazardousCommodities;
    }


    /**
     * Sets the hazardousCommodities value for this DangerousGoodsContainer.
     * 
     * @param hazardousCommodities   * Documents the kinds and quantities of all hazardous commodities
     * in the current container.
     */
    public void setHazardousCommodities(com.fedex.ws.rate.v22.HazardousCommodityContent[] hazardousCommodities) {
        this.hazardousCommodities = hazardousCommodities;
    }

    public com.fedex.ws.rate.v22.HazardousCommodityContent getHazardousCommodities(int i) {
        return this.hazardousCommodities[i];
    }

    public void setHazardousCommodities(int i, com.fedex.ws.rate.v22.HazardousCommodityContent _value) {
        this.hazardousCommodities[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DangerousGoodsContainer)) return false;
        DangerousGoodsContainer other = (DangerousGoodsContainer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.packingType==null && other.getPackingType()==null) || 
             (this.packingType!=null &&
              this.packingType.equals(other.getPackingType()))) &&
            ((this.containerType==null && other.getContainerType()==null) || 
             (this.containerType!=null &&
              this.containerType.equals(other.getContainerType()))) &&
            ((this.radioactiveContainerClass==null && other.getRadioactiveContainerClass()==null) || 
             (this.radioactiveContainerClass!=null &&
              this.radioactiveContainerClass.equals(other.getRadioactiveContainerClass()))) &&
            ((this.numberOfContainers==null && other.getNumberOfContainers()==null) || 
             (this.numberOfContainers!=null &&
              this.numberOfContainers.equals(other.getNumberOfContainers()))) &&
            ((this.hazardousCommodities==null && other.getHazardousCommodities()==null) || 
             (this.hazardousCommodities!=null &&
              java.util.Arrays.equals(this.hazardousCommodities, other.getHazardousCommodities())));
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
        if (getPackingType() != null) {
            _hashCode += getPackingType().hashCode();
        }
        if (getContainerType() != null) {
            _hashCode += getContainerType().hashCode();
        }
        if (getRadioactiveContainerClass() != null) {
            _hashCode += getRadioactiveContainerClass().hashCode();
        }
        if (getNumberOfContainers() != null) {
            _hashCode += getNumberOfContainers().hashCode();
        }
        if (getHazardousCommodities() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getHazardousCommodities());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getHazardousCommodities(), i);
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
        new org.apache.axis.description.TypeDesc(DangerousGoodsContainer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DangerousGoodsContainer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "PackingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousContainerPackingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("containerType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ContainerType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("radioactiveContainerClass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RadioactiveContainerClass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RadioactiveContainerClassType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfContainers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "NumberOfContainers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "nonNegativeInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hazardousCommodities");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodities"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "HazardousCommodityContent"));
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
