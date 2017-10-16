/**
 * Party.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.fedex.ws.rate.v22;

public class Party  implements java.io.Serializable {
    private java.lang.String accountNumber;

    private com.fedex.ws.rate.v22.TaxpayerIdentification[] tins;

    private com.fedex.ws.rate.v22.Contact contact;

    private com.fedex.ws.rate.v22.Address address;

    public Party() {
    }

    public Party(
           java.lang.String accountNumber,
           com.fedex.ws.rate.v22.TaxpayerIdentification[] tins,
           com.fedex.ws.rate.v22.Contact contact,
           com.fedex.ws.rate.v22.Address address) {
           this.accountNumber = accountNumber;
           this.tins = tins;
           this.contact = contact;
           this.address = address;
    }


    /**
     * Gets the accountNumber value for this Party.
     * 
     * @return accountNumber
     */
    public java.lang.String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this Party.
     * 
     * @param accountNumber
     */
    public void setAccountNumber(java.lang.String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the tins value for this Party.
     * 
     * @return tins
     */
    public com.fedex.ws.rate.v22.TaxpayerIdentification[] getTins() {
        return tins;
    }


    /**
     * Sets the tins value for this Party.
     * 
     * @param tins
     */
    public void setTins(com.fedex.ws.rate.v22.TaxpayerIdentification[] tins) {
        this.tins = tins;
    }

    public com.fedex.ws.rate.v22.TaxpayerIdentification getTins(int i) {
        return this.tins[i];
    }

    public void setTins(int i, com.fedex.ws.rate.v22.TaxpayerIdentification _value) {
        this.tins[i] = _value;
    }


    /**
     * Gets the contact value for this Party.
     * 
     * @return contact
     */
    public com.fedex.ws.rate.v22.Contact getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this Party.
     * 
     * @param contact
     */
    public void setContact(com.fedex.ws.rate.v22.Contact contact) {
        this.contact = contact;
    }


    /**
     * Gets the address value for this Party.
     * 
     * @return address
     */
    public com.fedex.ws.rate.v22.Address getAddress() {
        return address;
    }


    /**
     * Sets the address value for this Party.
     * 
     * @param address
     */
    public void setAddress(com.fedex.ws.rate.v22.Address address) {
        this.address = address;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Party)) return false;
        Party other = (Party) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountNumber==null && other.getAccountNumber()==null) || 
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            ((this.tins==null && other.getTins()==null) || 
             (this.tins!=null &&
              java.util.Arrays.equals(this.tins, other.getTins()))) &&
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress())));
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
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        if (getTins() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTins());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTins(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Party.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Party"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "AccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Tins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "TaxpayerIdentification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Contact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://fedex.com/ws/rate/v22", "Address"));
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
