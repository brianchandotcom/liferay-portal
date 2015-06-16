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

package com.liferay.portal.rest.extender.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

/**
 * @author Carlos Sierra Andrés
 */
public class CXFJaxRsServiceRegistrator {

	public CXFJaxRsServiceRegistrator(Map<String, Object> properties) {
		_properties = properties;
	}

	public synchronized void addApplication(Application application) {
		_applications.add(application);

		rewire();
	}

	public synchronized void addBus(Bus bus) {
		_buses.add(bus);

		for (Application application : _applications) {
			registerApplication(bus, application);
		}
	}

	public synchronized void addProvider(Object provider) {
		_providers.add(provider);

		rewire();
	}

	public synchronized void addService(Object service) {
		_services.add(service);

		rewire();
	}

	public synchronized void removeApplication(Application application) {
		_applications.remove(application);

		remove(application);
	}

	public synchronized void removeBus(Bus bus) {
		_buses.remove(bus);

		Map<Object, Server> servers = _busServers.remove(bus);

		if (servers == null) {
			return;
		}

		for (Server server : servers.values()) {
			server.destroy();
		}
	}

	public synchronized void removeProvider(Object provider) {
		_providers.remove(provider);

		rewire();
	}

	public synchronized void removeService(Object service) {
		_services.remove(service);

		rewire();
	}

	protected void registerApplication(Bus bus, Application application) {
		RuntimeDelegate runtimeDelegate = RuntimeDelegate.getInstance();

		JAXRSServerFactoryBean jaxRsServerFactoryBean =
			runtimeDelegate.createEndpoint(
				application, JAXRSServerFactoryBean.class);

		jaxRsServerFactoryBean.setBus(bus);
		jaxRsServerFactoryBean.setProperties(_properties);

		JSONProvider<Object> jsonProvider = new JSONProvider<>();

		jsonProvider.setDropCollectionWrapperElement(true);
		jsonProvider.setDropRootElement(true);
		jsonProvider.setSerializeAsArray(true);
		jsonProvider.setSupportUnwrapped(true);

		jaxRsServerFactoryBean.setProvider(jsonProvider);

		for (Object provider : _providers) {
			jaxRsServerFactoryBean.setProvider(provider);
		}

		for (Object service : _services) {
			jaxRsServerFactoryBean.setServiceBean(service);
		}

		Server server = jaxRsServerFactoryBean.create();

		server.start();

		store(bus, application, server);
	}

	protected void registerApplications() {
		for (Bus bus : _buses) {
			for (Application application : _applications) {
				registerApplication(bus, application);
			}
		}
	}

	protected void remove(Object application) {
		for (Map<Object, Server> servers : _busServers.values()) {
			Server server = servers.remove(application);

			if (server != null) {
				server.destroy();
			}
		}
	}

	protected void rewire() {
		for (Application application : _applications) {
			remove(application);
		}

		registerApplications();
	}

	protected void store(Bus bus, Object object, Server server) {
		Map<Object, Server> servers = _busServers.get(bus);

		if (servers == null) {
			servers = new HashMap<>();

			_busServers.put(bus, servers);
		}

		servers.put(object, server);
	}

	private final Collection<Application> _applications = new ArrayList<>();
	private final Collection<Bus> _buses = new ArrayList<>();
	private final Map<Bus, Map<Object, Server>> _busServers =
		new IdentityHashMap<>();
	private final Map<String, Object> _properties;
	private final Collection<Object> _providers = new ArrayList<>();
	private final Collection<Object> _services = new ArrayList<>();

}