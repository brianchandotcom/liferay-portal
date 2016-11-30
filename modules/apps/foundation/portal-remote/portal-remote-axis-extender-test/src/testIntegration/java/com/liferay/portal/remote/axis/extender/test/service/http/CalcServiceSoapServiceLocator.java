/**
 * CalcServiceSoapServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liferay.portal.remote.axis.extender.test.service.http;

public class CalcServiceSoapServiceLocator extends org.apache.axis.client.Service implements com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapService {

    public CalcServiceSoapServiceLocator() {
    }


    public CalcServiceSoapServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CalcServiceSoapServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CalcServiceSoapPort
    private java.lang.String CalcServiceSoapPort_address = "http://localhost:8080/o/com.liferay.portal.remote.axis.extender.test/api/axis/CalcService";

    public java.lang.String getCalcServiceSoapPortAddress() {
        return CalcServiceSoapPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CalcServiceSoapPortWSDDServiceName = "CalcServiceSoapPort";

    public java.lang.String getCalcServiceSoapPortWSDDServiceName() {
        return CalcServiceSoapPortWSDDServiceName;
    }

    public void setCalcServiceSoapPortWSDDServiceName(java.lang.String name) {
        CalcServiceSoapPortWSDDServiceName = name;
    }

    public com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoap getCalcServiceSoapPort() throws javax.xml.rpc.ServiceException {
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CalcServiceSoapPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCalcServiceSoapPort(endpoint);
    }

    public com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoap getCalcServiceSoapPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapPortSoapBindingStub _stub = new com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapPortSoapBindingStub(portAddress, this);
            _stub.setPortName(getCalcServiceSoapPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCalcServiceSoapPortEndpointAddress(java.lang.String address) {
        CalcServiceSoapPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapPortSoapBindingStub _stub = new com.liferay.portal.remote.axis.extender.test.service.http.CalcServiceSoapPortSoapBindingStub(new java.net.URL(CalcServiceSoapPort_address), this);
                _stub.setPortName(getCalcServiceSoapPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CalcServiceSoapPort".equals(inputPortName)) {
            return getCalcServiceSoapPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:http.service.test.extender.axis.remote.portal.liferay.com", "CalcServiceSoapService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:http.service.test.extender.axis.remote.portal.liferay.com", "CalcServiceSoapPort"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("CalcServiceSoapPort".equals(portName)) {
            setCalcServiceSoapPortEndpointAddress(address);
        }
        else
        { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
