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

package com.liferay.websocket.whiteboard.internal;

import java.io.IOException;

import java.util.List;
import java.util.Map;
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

	public ServerEndpointConfigWrapper(
		String path, List<Class<? extends Decoder>> decoders,
		List<Class<? extends Encoder>> encoders, List<String> subprotocols,
		LogService logService) {

		_logService = logService;

		_init(path, decoders, encoders, subprotocols);
	}

	@Override
	public Configurator getConfigurator() {
		Map.Entry<ServiceReference<Endpoint>, ServiceObjectsConfigurator>
			entry = _endpoints.firstEntry();

		if (entry == null) {
			return _configurator;
		}

		return entry.getValue();
	}

	@Override
	public List<Class<? extends Decoder>> getDecoders() {
		return _serverEndpointConfig.getDecoders();
	}

	@Override
	public List<Class<? extends Encoder>> getEncoders() {
		return _serverEndpointConfig.getEncoders();
	}

	@Override
	public Class<?> getEndpointClass() {
		return _serverEndpointConfig.getEndpointClass();
	}

	@Override
	public List<Extension> getExtensions() {
		return _serverEndpointConfig.getExtensions();
	}

	@Override
	public String getPath() {
		return _serverEndpointConfig.getPath();
	}

	@Override
	public List<String> getSubprotocols() {
		return _serverEndpointConfig.getSubprotocols();
	}

	@Override
	public Map<String, Object> getUserProperties() {
		return _serverEndpointConfig.getUserProperties();
	}

	public void override(
		List<Class<? extends Decoder>> decoders,
		List<Class<? extends Encoder>> encoders, List<String> subprotocols) {

		_init(
			_serverEndpointConfig.getPath(), decoders, encoders, subprotocols);
	}

	public ServiceObjectsConfigurator removeConfigurator(
		ServiceReference<Endpoint> reference) {

		return _endpoints.remove(reference);
	}

	public void setConfigurator(
		ServiceReference<Endpoint> serviceReference,
		ServiceObjectsConfigurator serviceObjectsConfigurator) {

		_endpoints.put(serviceReference, serviceObjectsConfigurator);
	}

	public final class NullEndpoint extends Endpoint {

		@Override
		public void onOpen(Session session, EndpointConfig config) {
			try {
				session.close(
					new CloseReason(
						CloseReason.CloseCodes.GOING_AWAY,
						"Service is gone away"));
			}
			catch (IOException ioe) {
				_logService.log(
					LogService.LOG_ERROR, "Unable to close session", ioe);
			}
		}

	}

	private void _init(
		String path, List<Class<? extends Decoder>> decoders,
		List<Class<? extends Encoder>> encoders, List<String> subprotocols) {

		ServerEndpointConfig.Builder builder =
			ServerEndpointConfig.Builder.create(Endpoint.class, path);

		builder.decoders(decoders);
		builder.encoders(encoders);
		builder.subprotocols(subprotocols);

		_serverEndpointConfig = builder.build();

		_endpoints = new ConcurrentSkipListMap<>();
	}

	private final Configurator _configurator =
		new ServerEndpointConfig.Configurator() {

			@Override
			public <T> T getEndpointInstance(Class<T> endpointClass) {
				return (T)new NullEndpoint();
			}

		};

	private ConcurrentNavigableMap
		<ServiceReference<Endpoint>, ServiceObjectsConfigurator> _endpoints;
	private final LogService _logService;
	private ServerEndpointConfig _serverEndpointConfig;

}