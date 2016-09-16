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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.Extension;
import javax.websocket.server.ServerEndpointConfig;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

/**
 * @author Cristina González Castellano
 */
public class ServerEndpointConfigWrapper implements ServerEndpointConfig {

	public ServerEndpointConfigWrapper(String path, LogService log) {
		_serverEndpointConfig = ServerEndpointConfig.Builder.create(
			Endpoint.class, path).build();

		_log = log;
	}

	public Configurator getConfigurator() {
		Entry<ServiceReference<Endpoint>, ServiceObjectsConfigurator> entry =
			_endpoints.firstEntry();

		return entry.getValue();
	}

	public List<Class<? extends Decoder>> getDecoders() {
		return _serverEndpointConfig.getDecoders();
	}

	public Class<?> getEndpointClass() {
		return _serverEndpointConfig.getEndpointClass();
	}

	public List<Class<? extends Encoder>> getEncoders() {
		return _serverEndpointConfig.getEncoders();
	}

	public List<Extension> getExtensions() {
		return _serverEndpointConfig.getExtensions();
	}

	public String getPath() {
		return _serverEndpointConfig.getPath();
	}

	public Map<String, Object> getUserProperties() {
		return _serverEndpointConfig.getUserProperties();
	}

	public List<String> getSubprotocols() {
		return _serverEndpointConfig.getSubprotocols();
	}

	public ServiceObjectsConfigurator removeConfigurator(
		ServiceReference<Endpoint> reference) {

		return _endpoints.remove(reference);
	}

	public void setConfigurator(
		ServiceReference<Endpoint> reference,
		ServiceObjectsConfigurator configurator) {

		_endpoints.put(reference, configurator);
	}

	private ServerEndpointConfig _serverEndpointConfig;
	private ConcurrentSkipListMap<ServiceReference<Endpoint>,
		ServiceObjectsConfigurator> _endpoints = new ConcurrentSkipListMap<>();

	private final LogService _log;
}