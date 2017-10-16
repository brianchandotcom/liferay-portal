/**
 * DestinationControlDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;


/**
 * Data required to complete the Destionation Control Statement for
 * US exports.
 */
public class DestinationControlDetail  implements java.io.Serializable {
    private com.fedex.ws.rate.v22.DestinationControlStatementType[] statementTypes;

    /* Comma-separated list of up to four country codes, required
     * for DEPARTMENT_OF_STATE statement. */
    private java.lang.String destinationCountries;

    /* Name of end user, required for DEPARTMENT_OF_STATE statement. */
    private java.lang.String endUser;

    public DestinationControlDetail() {
    }

    public DestinationControlDetail(
           com.fedex.ws.rate.v22.DestinationControlStatementType[] statementTypes,
           java.lang.String destinationCountries,
           java.lang.String endUser) {
           this.statementTypes = statementTypes;
           this.destinationCountries = destinationCountries;
           this.endUser = endUser;
    }


    /**
     * Gets the statementTypes value for this DestinationControlDetail.
     * 
     * @return statementTypes
     */
    public com.fedex.ws.rate.v22.DestinationControlStatementType[] getStatementTypes() {
        return statementTypes;
    }


    /**
     * Sets the statementTypes value for this DestinationControlDetail.
     * 
     * @param statementTypes
     */
    public void setStatementTypes(com.fedex.ws.rate.v22.DestinationControlStatementType[] statementTypes) {
        this.statementTypes = statementTypes;
    }

    public com.fedex.ws.rate.v22.DestinationControlStatementType getStatementTypes(int i) {
        return this.statementTypes[i];
    }

    public void setStatementTypes(int i, com.fedex.ws.rate.v22.DestinationControlStatementType _value) {
        this.statementTypes[i] = _value;
    }


    /**
     * Gets the destinationCountries value for this DestinationControlDetail.
     * 
     * @return destinationCountries   * Comma-separated list of up to four country codes, required
     * for DEPARTMENT_OF_STATE statement.
     */
    public java.lang.String getDestinationCountries() {
        return destinationCountries;
    }


    /**
     * Sets the destinationCountries value for this DestinationControlDetail.
     * 
     * @param destinationCountries   * Comma-separated list of up to four country codes, required
     * for DEPARTMENT_OF_STATE statement.
     */
    public void setDestinationCountries(java.lang.String destinationCountries) {
        this.destinationCountries = destinationCountries;
    }


    /**
     * Gets the endUser value for this DestinationControlDetail.
     * 
     * @return endUser   * Name of end user, required for DEPARTMENT_OF_STATE statement.
     */
    public java.lang.String getEndUser() {
        return endUser;
    }


    /**
     * Sets the endUser value for this DestinationControlDetail.
     * 
     * @param endUser   * Name of end user, required for DEPARTMENT_OF_STATE statement.
     */
    public void setEndUser(java.lang.String endUser) {
        this.endUser = endUser;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DestinationControlDetail)) return false;
        DestinationControlDetail other = (DestinationControlDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statementTypes==null && other.getStatementTypes()==null) || 
             (this.statementTypes!=null &&
              java.util.Arrays.equals(this.statementTypes, other.getStatementTypes()))) &&
            ((this.destinationCountries==null && other.getDestinationCountries()==null) || 
             (this.destinationCountries!=null &&
              this.destinationCountries.equals(other.getDestinationCountries()))) &&
            ((this.endUser==null && other.getEndUser()==null) || 
             (this.endUser!=null &&
              this.endUser.equals(other.getEndUser())));
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
        if (getStatementTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStatementTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStatementTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDestinationCountries() != null) {
            _hashCode += getDestinationCountries().hashCode();
        }
        if (getEndUser() != null) {
            _hashCode += getEndUser().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DestinationControlDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationControlDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statementTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "StatementTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationControlStatementType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationCountries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "DestinationCountries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endUser");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "EndUser"));
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
