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

package com.liferay.portal.remote.soap.extender.internal;

import com.liferay.portal.remote.soap.extender.SoapDescriptorBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
public class CXFJaxWsServiceRegistrator {

	public synchronized void addBus(Bus bus) {
		_swapClassLoader(bus, this::_addBus);
	}

	public synchronized void addHandler(Handler<?> handler) {
		_swapClassLoader(handler, this::_addHandler);
	}

	public synchronized void addService(
		Map<String, Object> properties, Object service) {

		Class<?> clazz = service.getClass();

		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		try {
			thread.setContextClassLoader(clazz.getClassLoader());

			_addService(properties, service);
		}
		finally {
			thread.setContextClassLoader(classLoader);
		}
	}

	public synchronized void removeBus(Bus bus) {
		_swapClassLoader(bus, this::_removeBus);
	}

	public synchronized void removeHandler(Handler<?> handler) {
		_swapClassLoader(handler, this::_removeHandler);
	}

	public synchronized void removeService(Object service) {
		_swapClassLoader(service, this::_removeService);
	}

	public void setSoapDescriptorBuilder(
		SoapDescriptorBuilder soapDescriptorBuilder) {

		_soapDescriptorBuilder = soapDescriptorBuilder;
	}

	protected void registerService(
		Bus bus, Object service, Map<String, Object> properties) {

		JaxWsServerFactoryBean jaxWsServerFactoryBean =
			new JaxWsServerFactoryBean();

		SoapDescriptorBuilder.SoapDescriptor soapDescriptor =
			_soapDescriptorBuilder.buildSoapDescriptor(service, properties);

		jaxWsServerFactoryBean.setAddress(
			soapDescriptor.getPublicationAddress());

		jaxWsServerFactoryBean.setBus(bus);

		QName endpointName = soapDescriptor.getEndpointName();

		if (endpointName != null) {
			jaxWsServerFactoryBean.setEndpointName(endpointName);
		}

		jaxWsServerFactoryBean.setHandlers(_handlers);
		jaxWsServerFactoryBean.setProperties(properties);
		jaxWsServerFactoryBean.setServiceBean(service);

		Class<?> serviceClass = soapDescriptor.getServiceClass();

		if (serviceClass != null) {
			jaxWsServerFactoryBean.setServiceClass(serviceClass);
		}

		String wsdlLocation = soapDescriptor.getWsdlLocation();

		if (wsdlLocation != null) {
			jaxWsServerFactoryBean.setWsdlLocation(wsdlLocation);
		}

		Server server = jaxWsServerFactoryBean.create();

		store(bus, server, service);
	}

	protected void store(Bus bus, Server server, Object service) {
		Map<Object, Server> servers = _busServers.get(bus);

		if (servers == null) {
			servers = new HashMap<>();

			_busServers.put(bus, servers);
		}

		servers.put(service, server);
	}

	private void _addBus(Bus bus) {
		_buses.add(bus);

		for (Map.Entry<Object, Map<String, Object>> entry :
				_serviceProperties.entrySet()) {

			registerService(bus, entry.getKey(), entry.getValue());
		}
	}

	private void _addHandler(Handler<?> handler) {
		_handlers.add(handler);

		for (Map<Object, Server> servers : _busServers.values()) {
			for (Server server : servers.values()) {
				JaxWsEndpointImpl jaxWsEndpointImpl =
					(JaxWsEndpointImpl)server.getEndpoint();

				Binding binding = jaxWsEndpointImpl.getJaxwsBinding();

				@SuppressWarnings("rawtypes")
				List<Handler> handlers = binding.getHandlerChain();

				handlers.add(handler);

				binding.setHandlerChain(handlers);
			}
		}
	}

	private void _addService(Map<String, Object> properties, Object service) {
		for (Bus bus : _buses) {
			registerService(bus, service, properties);
		}

		_serviceProperties.put(service, properties);
	}

	private void _removeBus(Bus bus) {
		_buses.remove(bus);

		Map<Object, Server> servers = _busServers.remove(bus);

		if (servers == null) {
			return;
		}

		for (Server server : servers.values()) {
			server.destroy();
		}
	}

	private void _removeHandler(Handler<?> handler) {
		for (Map<Object, Server> servers : _busServers.values()) {
			for (Server server : servers.values()) {
				JaxWsEndpointImpl jaxWsEndpointImpl =
					(JaxWsEndpointImpl)server.getEndpoint();

				Binding binding = jaxWsEndpointImpl.getJaxwsBinding();

				@SuppressWarnings("rawtypes")
				List<Handler> handlers = binding.getHandlerChain();

				handlers.remove(handler);

				binding.setHandlerChain(handlers);
			}
		}

		_handlers.remove(handler);
	}

	private void _removeService(Object service) {
		_serviceProperties.remove(service);

		for (Map<Object, Server> servers : _busServers.values()) {
			Server server = servers.get(service);

			if (server != null) {
				server.destroy();
			}
		}
	}

	private <T> void _swapClassLoader(T t, Consumer<T> consumer) {
		Class<?> clazz = t.getClass();

		Thread thread = Thread.currentThread();

		ClassLoader classLoader = thread.getContextClassLoader();

		try {
			thread.setContextClassLoader(clazz.getClassLoader());

			consumer.accept(t);
		}
		finally {
			thread.setContextClassLoader(classLoader);
		}
	}

	private final Collection<Bus> _buses = new ArrayList<>();
	private final Map<Bus, Map<Object, Server>> _busServers =
		new IdentityHashMap<>();

	@SuppressWarnings("rawtypes")
	private final List<Handler> _handlers = new ArrayList<>();

	private final Map<Object, Map<String, Object>> _serviceProperties =
		new IdentityHashMap<>();
	private SoapDescriptorBuilder _soapDescriptorBuilder;

}