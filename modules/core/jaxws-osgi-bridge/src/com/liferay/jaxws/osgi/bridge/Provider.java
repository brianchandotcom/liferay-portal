/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jaxws.osgi.bridge;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.net.URL;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.spi.Invoker;
import javax.xml.ws.spi.ServiceDelegate;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.w3c.dom.Element;

/**
 * @author Carlos Sierra Andrés
 */
public class Provider extends javax.xml.ws.spi.Provider {

	public Provider() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			javax.xml.ws.spi.Provider.class);

		_serviceTracker.open();
	}

	@Override
	public Endpoint createAndPublishEndpoint(
		String address, Object implementor) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createAndPublishEndpoint(address, implementor);
	}

	@Override
	public Endpoint createAndPublishEndpoint(
		String address, Object implementor, WebServiceFeature... features) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createAndPublishEndpoint(
			address, implementor, features);
	}

	@Override
	public Endpoint createEndpoint(
		String bindingId, Class<?> implementorClass, Invoker invoker,
		WebServiceFeature... features) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createEndpoint(
			bindingId, implementorClass, invoker, features);
	}

	@Override
	public Endpoint createEndpoint(String bindingId, Object implementor) {
		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createEndpoint(bindingId, implementor);
	}

	@Override
	public Endpoint createEndpoint(
		String bindingId, Object implementor, WebServiceFeature... features) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createEndpoint(bindingId, implementor, features);
	}

	@Override
	public ServiceDelegate createServiceDelegate(
		URL wsdlDocumentLocation, QName serviceName,
		Class<? extends Service> serviceClass) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createServiceDelegate(
			wsdlDocumentLocation, serviceName, serviceClass);
	}

	@Override
	public ServiceDelegate createServiceDelegate(
		URL wsdlDocumentLocation, QName serviceName,
		Class<? extends Service> serviceClass, WebServiceFeature... features) {

		return _getProvider().createServiceDelegate(
			wsdlDocumentLocation, serviceName, serviceClass, features);
	}

	@Override
	public W3CEndpointReference createW3CEndpointReference(
		String address, QName serviceName, QName portName,
		List<Element> metadata, String wsdlDocumentLocation,
		List<Element> referenceParameters) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createW3CEndpointReference(
			address, serviceName, portName, metadata, wsdlDocumentLocation,
			referenceParameters);
	}

	@Override
	public W3CEndpointReference createW3CEndpointReference(
		String address, QName interfaceName, QName serviceName, QName portName,
		List<Element> metadata, String wsdlDocumentLocation,
		List<Element> referenceParameters, List<Element> elements,
		Map<QName, String> attributes) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.createW3CEndpointReference(
			address, interfaceName, serviceName, portName, metadata,
			wsdlDocumentLocation, referenceParameters, elements, attributes);
	}

	@Override
	public <T> T getPort(
		EndpointReference endpointReference, Class<T> serviceEndpointInterface,
		WebServiceFeature... features) {

		javax.xml.ws.spi.Provider provider = _getProvider();

		return provider.getPort(
			endpointReference, serviceEndpointInterface, features);
	}

	@Override
	public EndpointReference readEndpointReference(Source eprInfoset) {
		return _getProvider().readEndpointReference(eprInfoset);
	}

	private javax.xml.ws.spi.Provider _getProvider() {
		try {
			return _serviceTracker.waitForService(10 * 1000L);
		}
		catch (InterruptedException ie) {
			throw new RuntimeException(ie);
		}
	}

	private final ServiceTracker
		<javax.xml.ws.spi.Provider, javax.xml.ws.spi.Provider> _serviceTracker;

}