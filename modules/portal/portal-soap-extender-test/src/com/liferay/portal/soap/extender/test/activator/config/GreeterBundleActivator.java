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

package com.liferay.portal.soap.extender.test.activator.config;

import com.liferay.portal.soap.extender.test.service.Greeter;
import com.liferay.portal.soap.extender.test.service.GreeterImpl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class GreeterBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("jaxws", true);
		properties.put("soap.address", "/greeter");

		_serviceRegistration = bundleContext.registerService(
			Greeter.class, new GreeterImpl(), properties);

		_configAdminBundleActivator = new ConfigAdminBundleActivator();

		_configAdminBundleActivator.start(bundleContext);

		Filter filter = bundleContext.createFilter(
			"(&(objectClass=" + Bus.class.getName() + ")(" +
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH + "=" +
				"/soap-test))");

		ServiceTracker<Bus, Bus> serviceTracker = new ServiceTracker<>(
			bundleContext, filter, null);

		serviceTracker.open();

		Bus bus = serviceTracker.waitForService(10000L);

		if (bus == null) {
			throw new IllegalStateException(
				"Bus has not been registered past 10 seconds");
		}

		ServerRegistry serverRegistry = bus.getExtension(ServerRegistry.class);

		List<Server> servers = null;

		for (int i = 0; i <= 100; i++) {
			servers = serverRegistry.getServers();

			if (!servers.isEmpty()) {
				break;
			}

			Thread.sleep(100L);
		}

		if (servers.isEmpty()) {
			throw new IllegalStateException(
				"Endpoint has not been registered past 10 seconds");
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_configAdminBundleActivator.stop(bundleContext);

		_serviceRegistration.unregister();
	}

	private ConfigAdminBundleActivator _configAdminBundleActivator;
	private ServiceRegistration<Greeter> _serviceRegistration;

}