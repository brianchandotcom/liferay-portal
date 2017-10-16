/**
 * RegulatoryLabelContentDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Specifies details needed to generate any label artifacts required
 * due to regulatory requirements.
 */
public class RegulatoryLabelContentDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.RegulatoryLabelType type;

    /* Specifies how the customer requested the regulatory label to
     * be generated. */
    private com.fedex.ws.rate.v22.CustomerSpecifiedLabelGenerationOptionType[] generationOptions;

    public RegulatoryLabelContentDetail() {
    }

    public RegulatoryLabelContentDetail(
           com.fedex.ws.rate.v22.RegulatoryLabelType type,
           com.fedex.ws.rate.v22.CustomerSpecifiedLabelGenerationOptionType[] generationOptions) {
           this.type = type;
           this.generationOptions = generationOptions;
    }


    /**
     * Gets the type value for this RegulatoryLabelContentDetail.
     * 
     * @return type
     */
    public com.fedex.ws.rate.v22.RegulatoryLabelType getType() {
        return type;
    }


    /**
     * Sets the type value for this RegulatoryLabelContentDetail.
     * 
     * @param type
     */
    public void setType(com.fedex.ws.rate.v22.RegulatoryLabelType type) {
        this.type = type;
    }


    /**
     * Gets the generationOptions value for this RegulatoryLabelContentDetail.
     * 
     * @return generationOptions   * Specifies how the customer requested the regulatory label to
     * be generated.
     */
    public com.fedex.ws.rate.v22.CustomerSpecifiedLabelGenerationOptionType[] getGenerationOptions() {
        return generationOptions;
    }


    /**
     * Sets the generationOptions value for this RegulatoryLabelContentDetail.
     * 
     * @param generationOptions   * Specifies how the customer requested the regulatory label to
     * be generated.
     */
    public void setGenerationOptions(com.fedex.ws.rate.v22.CustomerSpecifiedLabelGenerationOptionType[] generationOptions) {
        this.generationOptions = generationOptions;
    }

    public com.fedex.ws.rate.v22.CustomerSpecifiedLabelGenerationOptionType getGenerationOptions(int i) {
        return this.generationOptions[i];
    }

    public void setGenerationOptions(int i, com.fedex.ws.rate.v22.CustomerSpecifiedLabelGenerationOptionType _value) {
        this.generationOptions[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RegulatoryLabelContentDetail)) return false;
        RegulatoryLabelContentDetail other = (RegulatoryLabelContentDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.generationOptions==null && other.getGenerationOptions()==null) || 
             (this.generationOptions!=null &&
              java.util.Arrays.equals(this.generationOptions, other.getGenerationOptions())));
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
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getGenerationOptions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGenerationOptions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGenerationOptions(), i);
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
        new org.apache.axis.description.TypeDesc(RegulatoryLabelContentDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RegulatoryLabelContentDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RegulatoryLabelType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("generationOptions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "GenerationOptions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomerSpecifiedLabelGenerationOptionType"));
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
