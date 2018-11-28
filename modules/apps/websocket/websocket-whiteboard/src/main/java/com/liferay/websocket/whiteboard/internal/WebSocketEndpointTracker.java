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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import javax.websocket.Decoder;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceObjects;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Cristina González
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = {})
public class WebSocketEndpointTracker {

	@Activate
	protected void activate(final BundleContext bundleContext) {
		Object serverContainer = _servletContext.getAttribute(
			"javax.websocket.server.ServerContainer");

		if (serverContainer == null) {
			if (_log.isInfoEnabled()) {
				_log.info("A WebSocket server container is not registered");
			}

			return;
		}

		_serverEndpointConfigWrapperServiceTracker = new ServiceTracker<>(
			bundleContext, Endpoint.class,
			new ServiceTrackerCustomizer
				<Endpoint, ServerEndpointConfigWrapper>() {

				@Override
				public ServerEndpointConfigWrapper addingService(
					ServiceReference<Endpoint> serviceReference) {

					String path = (String)serviceReference.getProperty(
						"org.osgi.http.websocket.endpoint.path");

					if ((path == null) || path.isEmpty()) {
						return null;
					}

					List<Class<? extends Decoder>> decoders =
						(List<Class<? extends Decoder>>)
							serviceReference.getProperty(
								"org.osgi.http.websocket.endpoint.decoders");
					List<Class<? extends Encoder>> encoders =
						(List<Class<? extends Encoder>>)
							serviceReference.getProperty(
								"org.osgi.http.websocket.endpoint.encoders");
					List<String> subprotocol =
						(List<String>)serviceReference.getProperty(
							"org.osgi.http.websocket.endpoint.subprotocol");

					final ServiceObjects<Endpoint> serviceObjects =
						bundleContext.getServiceObjects(serviceReference);

					ServerEndpointConfigWrapper serverEndpointConfigWrapper =
						_serverEndpointConfigWrappers.get(path);

					boolean isNew = false;

					if (serverEndpointConfigWrapper == null) {
						serverEndpointConfigWrapper =
							new ServerEndpointConfigWrapper(
								path, decoders, encoders, subprotocol,
								_logService);

						isNew = true;
					}
					else {
						Class<?> endpointClass =
							serverEndpointConfigWrapper.getEndpointClass();

						ServerEndpointConfig.Configurator configurator =
							serverEndpointConfigWrapper.getConfigurator();

						try {
							Object endpointInstance =
								configurator.getEndpointInstance(endpointClass);

							Class<?> endpointInstanceClass =
								endpointInstance.getClass();

							if (endpointInstanceClass.equals(
									ServerEndpointConfigWrapper.NullEndpoint.
										class)) {

								serverEndpointConfigWrapper.override(
									decoders, encoders, subprotocol);
							}
						}
						catch (InstantiationException ie) {
							Endpoint endpoint = serviceObjects.getService();

							_logService.log(
								LogService.LOG_ERROR,
								StringBundler.concat(
									"Unable to register WebSocket endpoint ",
									endpoint.getClass(), " for path ", path),
								ie);
						}
					}

					serverEndpointConfigWrapper.setConfigurator(
						serviceReference,
						new ServiceObjectsConfigurator(
							serviceObjects, _logService));

					if (isNew) {
						ServerContainer serverContainer =
							(ServerContainer)_servletContext.getAttribute(
								ServerContainer.class.getName());

						try {
							serverContainer.addEndpoint(
								serverEndpointConfigWrapper);
						}
						catch (DeploymentException de) {
							Endpoint endpoint = serviceObjects.getService();

							_logService.log(
								LogService.LOG_ERROR,
								StringBundler.concat(
									"Unable to register WebSocket endpoint ",
									endpoint.getClass(), " for path ", path),
								de);

							return null;
						}

						_serverEndpointConfigWrappers.put(
							path, serverEndpointConfigWrapper);
					}

					return serverEndpointConfigWrapper;
				}

				@Override
				public void modifiedService(
					ServiceReference<Endpoint> serviceReference,
					ServerEndpointConfigWrapper serverEndpointConfigWrapper) {

					removedService(
						serviceReference, serverEndpointConfigWrapper);

					addingService(serviceReference);
				}

				@Override
				public void removedService(
					ServiceReference<Endpoint> serviceReference,
					ServerEndpointConfigWrapper serverEndpointConfigWrapper) {

					ServiceObjectsConfigurator serviceObjectsConfigurator =
						serverEndpointConfigWrapper.removeConfigurator(
							serviceReference);

					serviceObjectsConfigurator.close();
				}

			});

		_serverEndpointConfigWrapperServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		if (_serverEndpointConfigWrapperServiceTracker != null) {
			_serverEndpointConfigWrapperServiceTracker.close();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebSocketEndpointTracker.class);

	@Reference
	private LogService _logService;

	private final ConcurrentMap<String, ServerEndpointConfigWrapper>
		_serverEndpointConfigWrappers = new ConcurrentHashMap<>();
	private ServiceTracker<Endpoint, ServerEndpointConfigWrapper>
		_serverEndpointConfigWrapperServiceTracker;

	@Reference(target = "(original.bean=true)")
	private ServletContext _servletContext;

}