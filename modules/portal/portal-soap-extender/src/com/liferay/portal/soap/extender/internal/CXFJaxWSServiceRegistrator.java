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

package com.liferay.portal.soap.extender.internal;

import com.liferay.portal.soap.extender.SoapDescriptorBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;

/**
* @author Carlos Sierra Andrés
*/
public class CXFJaxWSServiceRegistrator {

	public synchronized void addBus(Bus bus) {
		_buses.add(bus);

		for (Map.Entry<Object, Map<String, Object>> entry :
				_propertiesByService.entrySet()) {

			registerServiceInBus(bus, entry.getKey(), entry.getValue());
		}
	}

	public synchronized void addHandler(Handler<?> handler) {
		_handlers.add(handler);

		for (Map<Object, Server> servers : _serversPerBus.values()) {
			for (Server server : servers.values()) {
				JaxWsEndpointImpl jaxWsEndpoint =
					(JaxWsEndpointImpl)server.getEndpoint();

				Binding binding = jaxWsEndpoint.getJaxwsBinding();

				@SuppressWarnings("rawtypes")
				List<Handler> handlers = binding.getHandlerChain();

				handlers.add(handler);

				binding.setHandlerChain(handlers);
			}
		}
	}

	public synchronized void addService(
		Map<String, Object> properties, Object service) {

		for (Bus bus : _buses) {
			registerServiceInBus(bus, service, properties);
		}

		_propertiesByService.put(service, properties);
	}

	public synchronized void removeBus(Bus bus) {
		_buses.remove(bus);

		synchronized (_serversPerBus) {
			Map<Object, Server> servers = _serversPerBus.remove(bus);

			if (servers == null) {
				return;
			}

			for (Server server : servers.values()) {
				server.destroy();
			}
		}
	}

	public synchronized void removeHandler(Handler<?> handler) {
		for (Map<Object, Server> servers : _serversPerBus.values()) {
			for (Server server : servers.values()) {
				JaxWsEndpointImpl jaxWsEndpoint =
					(JaxWsEndpointImpl)server.getEndpoint();

				Binding binding = jaxWsEndpoint.getJaxwsBinding();

				@SuppressWarnings("rawtypes")
				List<Handler> handlers = binding.getHandlerChain();

				handlers.remove(handler);

				binding.setHandlerChain(handlers);
			}
		}

		_handlers.remove(handler);
	}

	public synchronized void removeService(Object service) {
		_propertiesByService.remove(service);

		for (Map<Object, Server> servers : _serversPerBus.values()) {
			Server server = servers.get(service);

			if (server != null) {
				server.destroy();
			}
		}
	}

	public void setSoapDescriptorBuilder(
		SoapDescriptorBuilder soapDescriptorBuilder) {

		_soapDescriptorBuilder = soapDescriptorBuilder;
	}

	protected void registerServiceInBus(
		Bus bus, Object service, Map<String, Object> properties) {

		SoapDescriptorBuilder.SoapDescriptor soapDescriptor =
			_soapDescriptorBuilder.buildSoapDescriptor(properties, service);

		JaxWsServerFactoryBean jaxWsServerFactoryBean =
			new JaxWsServerFactoryBean();

		jaxWsServerFactoryBean.setAddress(
			soapDescriptor.getPublicationAddress());
		jaxWsServerFactoryBean.setBus(bus);
		jaxWsServerFactoryBean.setProperties(properties);

		QName endpointName = soapDescriptor.getEndpointName();

		if (endpointName != null) {
			jaxWsServerFactoryBean.setEndpointName(endpointName);
		}

		jaxWsServerFactoryBean.setServiceBean(service);

		Class<?> serviceClass = soapDescriptor.getServiceClass();

		if (serviceClass != null) {
			jaxWsServerFactoryBean.setServiceClass(serviceClass);
		}

		String wsdlLocation = soapDescriptor.getWsdlLocation();

		if (wsdlLocation!= null) {
			jaxWsServerFactoryBean.setWsdlLocation(wsdlLocation);
		}

		jaxWsServerFactoryBean.setHandlers(_handlers);

		Server server = jaxWsServerFactoryBean.create();

		storeForBus(bus, service, server);
	}

	protected void storeForBus(Bus bus, Object object, Server server) {
		synchronized (_serversPerBus) {
			Map<Object, Server> servers = _serversPerBus.get(bus);

			if (servers == null) {
				servers = new HashMap<>();

				_serversPerBus.put(bus, servers);
			}

			servers.put(object, server);
		}
	}

	private final Collection<Bus> _buses = new ArrayList<>();

	@SuppressWarnings("rawtypes")
	private final List<Handler> _handlers = new ArrayList<>();

	private final Map<Object, Map<String, Object>> _propertiesByService =
		new IdentityHashMap<>();
	private final Map<Bus, Map<Object, Server>> _serversPerBus =
		new IdentityHashMap<>();
	private SoapDescriptorBuilder _soapDescriptorBuilder;

}