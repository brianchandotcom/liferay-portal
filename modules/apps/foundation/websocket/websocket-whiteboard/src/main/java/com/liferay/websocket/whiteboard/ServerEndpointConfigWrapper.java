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

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.websocket.CloseReason;
import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Extension;
import javax.websocket.Session;
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

		if (entry == null) {
			return _null;
		}

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

	private final Configurator _null = new ServerEndpointConfig.Configurator() {

		@Override
		@SuppressWarnings("unchecked")
		public <T> T getEndpointInstance(Class<T> endpointClass) {
			return (T)new NullEndpoint();
		}

	};

	private ServerEndpointConfig _serverEndpointConfig;
	private ConcurrentNavigableMap<ServiceReference<Endpoint>,
		ServiceObjectsConfigurator> _endpoints = new ConcurrentSkipListMap<>();

	final class NullEndpoint extends Endpoint {

		@Override
		public void onOpen(Session session, EndpointConfig config) {
			try {
				session.close(
					new CloseReason(
						CloseReason.CloseCodes.GOING_AWAY,
						"Service has gone away"));
			}
			catch (IOException ioe) {
				_log.log(
					LogService.LOG_ERROR,
					"It is not possible close the session", ioe);
			}
		}

	}

	private final LogService _log;
}