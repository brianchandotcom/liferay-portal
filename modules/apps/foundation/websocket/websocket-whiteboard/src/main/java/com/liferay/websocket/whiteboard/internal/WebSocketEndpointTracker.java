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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerContainer;

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
@Component(immediate = true)
public class WebSocketEndpointTracker
	implements ServiceTrackerCustomizer<Endpoint, ServerEndpointConfigWrapper> {

	@Override
	public ServerEndpointConfigWrapper addingService(
		ServiceReference<Endpoint> serviceReference) {

		String path = (String)serviceReference.getProperty(
			"org.osgi.http.websocket.endpoint.path");

		if ((path == null) || path.isEmpty()) {
			return null;
		}

		final ServiceObjects<Endpoint> serviceObjects =
			_bundleContext.getServiceObjects(serviceReference);

		ServerEndpointConfigWrapper serverEndpointConfigWrapper =
			_serverEndpointConfigWrappers.get(path);

		boolean isNew = false;

		if (serverEndpointConfigWrapper == null) {
			serverEndpointConfigWrapper = new ServerEndpointConfigWrapper(
				path, _logService);

			isNew = true;
		}

		serverEndpointConfigWrapper.setConfigurator(
			serviceReference,
			new ServiceObjectsConfigurator(serviceObjects, _logService));

		if (isNew) {
			ServerContainer serverContainer =
				(ServerContainer)_servletContext.getAttribute(
					ServerContainer.class.getName());

			try {
				serverContainer.addEndpoint(serverEndpointConfigWrapper);
			}
			catch (DeploymentException de) {
				Endpoint endpoint = serviceObjects.getService();

				_logService.log(
					LogService.LOG_ERROR,
					"Unable to register WebSocket endpoint " +
						endpoint.getClass() + " for path " + path,
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

		removedService(serviceReference, serverEndpointConfigWrapper);

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Endpoint> serviceReference,
		ServerEndpointConfigWrapper serverEndpointConfigWrapper) {

		ServiceObjectsConfigurator serviceObjectsConfigurator =
			serverEndpointConfigWrapper.removeConfigurator(serviceReference);

		serviceObjectsConfigurator.close();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serverEndpointConfigWrapperServiceTracker = new ServiceTracker<>(
			bundleContext, Endpoint.class, this);

		_serverEndpointConfigWrapperServiceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serverEndpointConfigWrapperServiceTracker.close();
	}

	private BundleContext _bundleContext;

	@Reference
	private LogService _logService;

	private final ConcurrentMap<String, ServerEndpointConfigWrapper>
		_serverEndpointConfigWrappers = new ConcurrentHashMap<>();
	private ServiceTracker<Endpoint, ServerEndpointConfigWrapper>
		_serverEndpointConfigWrapperServiceTracker;

	@Reference(target = "(websocket.active=true)")
	private ServletContext _servletContext;

}