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

package com.liferay.websocket.whiteboard;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerEndpointConfig;

import org.osgi.framework.ServiceObjects;
import org.osgi.service.log.LogService;

/**
 * @author Cristina González
 */
public class ServiceObjectsConfigurator
	extends ServerEndpointConfig.Configurator {

	public ServiceObjectsConfigurator(
		ServiceObjects<Endpoint> serviceObjects, LogService log) {

		_serviceObjects = serviceObjects;
		_log = log;
	}

	public void close() {
		Iterator<EndpointWrapper> iterator = _endpoints.iterator();

		while (iterator.hasNext()) {
			EndpointWrapper wrappedEndpoint = iterator.next();

			iterator.remove();

			wrappedEndpoint.close();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getEndpointInstance(Class<T> endpointClass) {
		return (T) _wrapped();
	}

	private EndpointWrapper _wrapped() {
		EndpointWrapper wrappedEndpoint = new EndpointWrapper(
			_serviceObjects, _log);

		_endpoints.add(wrappedEndpoint);

		return wrappedEndpoint;
	}

	private final Set<EndpointWrapper> _endpoints = new HashSet<>();
	private final LogService _log;
	private final ServiceObjects<Endpoint> _serviceObjects;

}