/**
 * CustomLabelTextBoxEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Constructed string, based on format and zero or more data fields,
 * printed in specified printer font (for thermal labels) or generic
 * font/size (for plain paper labels).
 */
public class CustomLabelTextBoxEntry  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.CustomLabelPosition topLeftCorner;

    private com.fedex.ws.rate.v22.CustomLabelPosition bottomRightCorner;

    private com.fedex.ws.rate.v22.CustomLabelPosition position;

    private java.lang.String format;

    private java.lang.String[] dataFields;

    /* Printer-specific font name for use with thermal printer labels. */
    private java.lang.String thermalFontId;

    /* Generic font name for use with plain paper labels. */
    private java.lang.String fontName;

    /* Generic font size for use with plain paper labels. */
    private org.apache.axis.types.PositiveInteger fontSize;

    private com.fedex.ws.rate.v22.RotationType rotation;

    public CustomLabelTextBoxEntry() {
    }

    public CustomLabelTextBoxEntry(
           com.fedex.ws.rate.v22.CustomLabelPosition topLeftCorner,
           com.fedex.ws.rate.v22.CustomLabelPosition bottomRightCorner,
           com.fedex.ws.rate.v22.CustomLabelPosition position,
           java.lang.String format,
           java.lang.String[] dataFields,
           java.lang.String thermalFontId,
           java.lang.String fontName,
           org.apache.axis.types.PositiveInteger fontSize,
           com.fedex.ws.rate.v22.RotationType rotation) {
           this.topLeftCorner = topLeftCorner;
           this.bottomRightCorner = bottomRightCorner;
           this.position = position;
           this.format = format;
           this.dataFields = dataFields;
           this.thermalFontId = thermalFontId;
           this.fontName = fontName;
           this.fontSize = fontSize;
           this.rotation = rotation;
    }


    /**
     * Gets the topLeftCorner value for this CustomLabelTextBoxEntry.
     * 
     * @return topLeftCorner
     */
    public com.fedex.ws.rate.v22.CustomLabelPosition getTopLeftCorner() {
        return topLeftCorner;
    }


    /**
     * Sets the topLeftCorner value for this CustomLabelTextBoxEntry.
     * 
     * @param topLeftCorner
     */
    public void setTopLeftCorner(com.fedex.ws.rate.v22.CustomLabelPosition topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }


    /**
     * Gets the bottomRightCorner value for this CustomLabelTextBoxEntry.
     * 
     * @return bottomRightCorner
     */
    public com.fedex.ws.rate.v22.CustomLabelPosition getBottomRightCorner() {
        return bottomRightCorner;
    }


    /**
     * Sets the bottomRightCorner value for this CustomLabelTextBoxEntry.
     * 
     * @param bottomRightCorner
     */
    public void setBottomRightCorner(com.fedex.ws.rate.v22.CustomLabelPosition bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }


    /**
     * Gets the position value for this CustomLabelTextBoxEntry.
     * 
     * @return position
     */
    public com.fedex.ws.rate.v22.CustomLabelPosition getPosition() {
        return position;
    }


    /**
     * Sets the position value for this CustomLabelTextBoxEntry.
     * 
     * @param position
     */
    public void setPosition(com.fedex.ws.rate.v22.CustomLabelPosition position) {
        this.position = position;
    }


    /**
     * Gets the format value for this CustomLabelTextBoxEntry.
     * 
     * @return format
     */
    public java.lang.String getFormat() {
        return format;
    }


    /**
     * Sets the format value for this CustomLabelTextBoxEntry.
     * 
     * @param format
     */
    public void setFormat(java.lang.String format) {
        this.format = format;
    }


    /**
     * Gets the dataFields value for this CustomLabelTextBoxEntry.
     * 
     * @return dataFields
     */
    public java.lang.String[] getDataFields() {
        return dataFields;
    }


    /**
     * Sets the dataFields value for this CustomLabelTextBoxEntry.
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
     * Gets the thermalFontId value for this CustomLabelTextBoxEntry.
     * 
     * @return thermalFontId   * Printer-specific font name for use with thermal printer labels.
     */
    public java.lang.String getThermalFontId() {
        return thermalFontId;
    }


    /**
     * Sets the thermalFontId value for this CustomLabelTextBoxEntry.
     * 
     * @param thermalFontId   * Printer-specific font name for use with thermal printer labels.
     */
    public void setThermalFontId(java.lang.String thermalFontId) {
        this.thermalFontId = thermalFontId;
    }


    /**
     * Gets the fontName value for this CustomLabelTextBoxEntry.
     * 
     * @return fontName   * Generic font name for use with plain paper labels.
     */
    public java.lang.String getFontName() {
        return fontName;
    }


    /**
     * Sets the fontName value for this CustomLabelTextBoxEntry.
     * 
     * @param fontName   * Generic font name for use with plain paper labels.
     */
    public void setFontName(java.lang.String fontName) {
        this.fontName = fontName;
    }


    /**
     * Gets the fontSize value for this CustomLabelTextBoxEntry.
     * 
     * @return fontSize   * Generic font size for use with plain paper labels.
     */
    public org.apache.axis.types.PositiveInteger getFontSize() {
        return fontSize;
    }


    /**
     * Sets the fontSize value for this CustomLabelTextBoxEntry.
     * 
     * @param fontSize   * Generic font size for use with plain paper labels.
     */
    public void setFontSize(org.apache.axis.types.PositiveInteger fontSize) {
        this.fontSize = fontSize;
    }


    /**
     * Gets the rotation value for this CustomLabelTextBoxEntry.
     * 
     * @return rotation
     */
    public com.fedex.ws.rate.v22.RotationType getRotation() {
        return rotation;
    }


    /**
     * Sets the rotation value for this CustomLabelTextBoxEntry.
     * 
     * @param rotation
     */
    public void setRotation(com.fedex.ws.rate.v22.RotationType rotation) {
        this.rotation = rotation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CustomLabelTextBoxEntry)) return false;
        CustomLabelTextBoxEntry other = (CustomLabelTextBoxEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.topLeftCorner==null && other.getTopLeftCorner()==null) || 
             (this.topLeftCorner!=null &&
              this.topLeftCorner.equals(other.getTopLeftCorner()))) &&
            ((this.bottomRightCorner==null && other.getBottomRightCorner()==null) || 
             (this.bottomRightCorner!=null &&
              this.bottomRightCorner.equals(other.getBottomRightCorner()))) &&
            ((this.position==null && other.getPosition()==null) || 
             (this.position!=null &&
              this.position.equals(other.getPosition()))) &&
            ((this.format==null && other.getFormat()==null) || 
             (this.format!=null &&
              this.format.equals(other.getFormat()))) &&
            ((this.dataFields==null && other.getDataFields()==null) || 
             (this.dataFields!=null &&
              java.util.Arrays.equals(this.dataFields, other.getDataFields()))) &&
            ((this.thermalFontId==null && other.getThermalFontId()==null) || 
             (this.thermalFontId!=null &&
              this.thermalFontId.equals(other.getThermalFontId()))) &&
            ((this.fontName==null && other.getFontName()==null) || 
             (this.fontName!=null &&
              this.fontName.equals(other.getFontName()))) &&
            ((this.fontSize==null && other.getFontSize()==null) || 
             (this.fontSize!=null &&
              this.fontSize.equals(other.getFontSize()))) &&
            ((this.rotation==null && other.getRotation()==null) || 
             (this.rotation!=null &&
              this.rotation.equals(other.getRotation())));
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
        if (getTopLeftCorner() != null) {
            _hashCode += getTopLeftCorner().hashCode();
        }
        if (getBottomRightCorner() != null) {
            _hashCode += getBottomRightCorner().hashCode();
        }
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
        if (getThermalFontId() != null) {
            _hashCode += getThermalFontId().hashCode();
        }
        if (getFontName() != null) {
            _hashCode += getFontName().hashCode();
        }
        if (getFontSize() != null) {
            _hashCode += getFontSize().hashCode();
        }
        if (getRotation() != null) {
            _hashCode += getRotation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustomLabelTextBoxEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelTextBoxEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topLeftCorner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TopLeftCorner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelPosition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bottomRightCorner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "BottomRightCorner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "CustomLabelPosition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("thermalFontId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "ThermalFontId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fontName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FontName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fontSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "FontSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "positiveInteger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rotation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Rotation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "RotationType"));
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
