/**
 * CustomLabelBarcodeEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Constructed string, based on format and zero or more data fields,
 * printed in specified barcode symbology.
 */
public class CustomLabelBarcodeEntry  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.CustomLabelPosition position;

    private java.lang.String format;

    private java.lang.String[] dataFields;

    private java.lang.Integer barHeight;

    /* Width of thinnest bar/space element in the barcode. */
    private java.lang.Integer thinBarWidth;

    private com.fedex.ws.rate.v22.BarcodeSymbologyType barcodeSymbology;

    public CustomLabelBarcodeEntry() {
    }

    public CustomLabelBarcodeEntry(
           com.fedex.ws.rate.v22.CustomLabelPosition position,
           java.lang.String format,
           java.lang.String[] dataFields,
           java.lang.Integer barHeight,
           java.lang.Integer thinBarWidth,
           com.fedex.ws.rate.v22.BarcodeSymbologyType barcodeSymbology) {
           this.position = position;
           this.format = format;
           this.dataFields = dataFields;
           this.barHeight = barHeight;
           this.thinBarWidth = thinBarWidth;
           this.barcodeSymbology = barcodeSymbology;
    }


    /**
     * Gets the position value for this CustomLabelBarcodeEntry.
     * 
     * @return position
     */
    public com.fedex.ws.rate.v22.CustomLabelPosition getPosition() {
        return position;
    }


    /**
     * Sets the position value for this CustomLabelBarcodeEntry.
     * 
     * @param position
     */
    public void setPosition(com.fedex.ws.rate.v22.CustomLabelPosition position) {
        this.position = position;
    }


    /**
     * Gets the format value for this CustomLabelBarcodeEntry.
     * 
     * @return format
     */
    public java.lang.String getFormat() {
        return format;
    }


    /**
     * Sets the format value for this CustomLabelBarcodeEntry.
     * 
     * @param format
     */
    public void setFormat(java.lang.String format) {
        this.format = format;
    }


    /**
     * Gets the dataFields value for this CustomLabelBarcodeEntry.
     * 
     * @return dataFields
     */
    public java.lang.String[] getDataFields() {
        return dataFields;
    }


    /**
     * Sets the dataFields value for this CustomLabelBarcodeEntry.
     * 
     * @param dataFields
     */
    public void setDataFields(java.lang.String[] dataFields) {
        this.dataFields = dataFields;
    }

    public java.lang.String getDataFields(int i) {
        return this.dataFields[i];
    }

    public void setDataFields(int i, java.lang.String _value) {
        this.dataFields[i] = _value;
    }


    /**
     * Gets the barHeight value for this CustomLabelBarcodeEntry.
     * 
     * @return barHeight
     */
    public java.lang.Integer getBarHeight() {
        return barHeight;
    }


    /**
     * Sets the barHeight value for this CustomLabelBarcodeEntry.
     * 
     * @param barHeight
     */
    public void setBarHeight(java.lang.Integer barHeight) {
        this.barHeight = barHeight;
    }


    /**
     * Gets the thinBarWidth value for this CustomLabelBarcodeEntry.
     * 
     * @return thinBarWidth   * Width of thinnest bar/space element in the barcode.
     */
    public java.lang.Integer getThinBarWidth() {
        return thinBarWidth;
    }


    /**
     * Sets the thinBarWidth value for this CustomLabelBarcodeEntry.
     * 
     * @param thinBarWidth   * Width of thinnest bar/space element in the barcode.
     */
    public void setThinBarWidth(java.lang.Integer thinBarWidth) {
        this.thinBarWidth = thinBarWidth;
    }


    /**
     * Gets the barcodeSymbology value for this CustomLabelBarcodeEntry.
     * 
     * @return barcodeSymbology
     */
    public com.fedex.ws.rate.v22.BarcodeSymbologyType getBarcodeSymbology() {
        return barcodeSymbology;
    }


    /**
     * Sets the barcodeSymbology value for this CustomLabelBarcodeEntry.
     * 
     * @param barcodeSymbology
     */
    public void setBarcodeSymbology(com.fedex.ws.rate.v22.BarcodeSymbologyType barcodeSymbology) {
        this.barcodeSymbology = barcodeSymbology;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomLabelBarcodeEntry)) return false;
        CustomLabelBarcodeEntry other = (CustomLabelBarcodeEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.position==null && other.getPosition()==null) || 
             (this.position!=null &&
              this.position.equals(other.getPosition()))) &&
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.dataFields==null && other.getDataFields()==null) || 
             (this.dataFields!=null &&
              java.util.Arrays.equals(this.dataFields, other.getDataFields()))) &&
            ((this.barHeight==null && other.getBarHeight()==null) || 
             (this.barHeight!=null &&
              this.barHeight.equals(other.getBarHeight()))) &&
            ((this.thinBarWidth==null && other.getThinBarWidth()==null) || 
             (this.thinBarWidth!=null &&
              this.thinBarWidth.equals(other.getThinBarWidth()))) &&
            ((this.barcodeSymbology==null && other.getBarcodeSymbology()==null) || 
             (this.barcodeSymbology!=null &&
              this.barcodeSymbology.equals(other.getBarcodeSymbology())));
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
        if (getPosition() != null) {
            _hashCode += getPosition().hashCode();
        }
        if (getFormat() != null) {
            _hashCode += getFormat().hashCode();
        }
        if (getDataFields() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDataFields());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDataFields(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getBarHeight() != null) {
            _hashCode += getBarHeight().hashCode();
        }
        if (getThinBarWidth() != null) {
            _hashCode += getThinBarWidth().hashCode();
        }
        if (getBarcodeSymbology() != null) {
            _hashCode += getBarcodeSymbology().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomLabelBarcodeEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelBarcodeEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("position");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Position"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelPosition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("format");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Format"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFields");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DataFields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barHeight");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BarHeight"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("thinBarWidth");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ThinBarWidth"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("barcodeSymbology");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BarcodeSymbology"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BarcodeSymbologyType"));
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
