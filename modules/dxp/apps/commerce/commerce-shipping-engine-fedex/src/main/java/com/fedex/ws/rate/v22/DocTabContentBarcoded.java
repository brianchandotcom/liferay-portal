/**
 * DocTabContentBarcoded.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class DocTabContentBarcoded  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.BarcodeSymbologyType symbology;

    private com.fedex.ws.rate.v22.DocTabZoneSpecification specification;

    public DocTabContentBarcoded() {
    }

    public DocTabContentBarcoded(
           com.fedex.ws.rate.v22.BarcodeSymbologyType symbology,
           com.fedex.ws.rate.v22.DocTabZoneSpecification specification) {
           this.symbology = symbology;
           this.specification = specification;
    }


    /**
     * Gets the symbology value for this DocTabContentBarcoded.
     * 
     * @return symbology
     */
    public com.fedex.ws.rate.v22.BarcodeSymbologyType getSymbology() {
        return symbology;
    }


    /**
     * Sets the symbology value for this DocTabContentBarcoded.
     * 
     * @param symbology
     */
    public void setSymbology(com.fedex.ws.rate.v22.BarcodeSymbologyType symbology) {
        this.symbology = symbology;
    }


    /**
     * Gets the specification value for this DocTabContentBarcoded.
     * 
     * @return specification
     */
    public com.fedex.ws.rate.v22.DocTabZoneSpecification getSpecification() {
        return specification;
    }


    /**
     * Sets the specification value for this DocTabContentBarcoded.
     * 
     * @param specification
     */
    public void setSpecification(com.fedex.ws.rate.v22.DocTabZoneSpecification specification) {
        this.specification = specification;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DocTabContentBarcoded)) return false;
        DocTabContentBarcoded other = (DocTabContentBarcoded) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.symbology==null && other.getSymbology()==null) || 
             (this.symbology!=null &&
              this.symbology.equals(other.getSymbology()))) &&
            ((this.specification==null && other.getSpecification()==null) || 
             (this.specification!=null &&
              this.specification.equals(other.getSpecification())));
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
        if (getSymbology() != null) {
            _hashCode += getSymbology().hashCode();
        }
        if (getSpecification() != null) {
            _hashCode += getSpecification().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DocTabContentBarcoded.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocTabContentBarcoded"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("symbology");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Symbology"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BarcodeSymbologyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specification");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Specification"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DocTabZoneSpecification"));
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
