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

package com.liferay.portal.rest.extender.test.activator.configuration;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.rest.extender.test.service.Greeter;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.core.Application;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Carlos Sierra Andrés
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			_cxfConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration",
				null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("contextPath", "/rest-test");

			_cxfConfiguration.update(properties);

			_restConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.rest.extender.configuration." +
					"RestExtenderConfiguration",
				null);

			properties = new Hashtable<>();

			properties.put("contextPaths", new String[] {"/rest-test"});
			properties.put(
				"jaxRsApplicationFilterStrings",
				new String[] {"(jaxrs.application=true)"});

			_restConfiguration.update(properties);

			properties = new Hashtable<>();

			properties.put("jaxrs.application", true);

			_serviceRegistration = bundleContext.registerService(
				Application.class, new Greeter(), properties);

			ServiceTracker<Bus, Bus> serviceTracker =
				ServiceTrackerFactory.open(
					bundleContext,
					"(&(objectClass=" + Bus.class.getName() + ")(" +
						HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_PATH +
							"=/rest-test))");

			Bus bus = serviceTracker.waitForService(10000L);

			if (bus == null) {
				throw new IllegalStateException(
					"Bus was not registered within 10 seconds");
			}

			ServerRegistry serverRegistry = bus.getExtension(
				ServerRegistry.class);

			List<Server> servers = null;

			for (int i = 0; i <= 100; i++) {
				servers = serverRegistry.getServers();

				if (!servers.isEmpty()) {
					break;
				}

				Thread.sleep(100);
			}

			if (servers.isEmpty()) {
				cleanUp(bundleContext);

				throw new IllegalStateException(
					"Endpoint was not registered within 10 seconds");
			}
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		cleanUp(bundleContext);
	}

	private void cleanUp(BundleContext bundleContext) throws Exception {
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceTracker<ServletContextHelper, ServletContextHelper>
			serviceTracker =
				new ServiceTracker<ServletContextHelper, ServletContextHelper>(
					bundleContext, ServletContextHelper.class, null) {

					@Override
					public void removedService(
						ServiceReference<ServletContextHelper> reference,
						ServletContextHelper service) {

						Object contextName = reference.getProperty(
							HttpWhiteboardConstants.
								HTTP_WHITEBOARD_CONTEXT_NAME);

						if (Validator.equals(contextName, "rest-test")) {
							countDownLatch.countDown();

							close();
						}
					}

				};

		serviceTracker.open();

		try {
			_serviceRegistration.unregister();
		}
		catch (Exception e) {
		}

		try {
			_restConfiguration.delete();
		}
		catch (Exception e) {
		}

		try {
			_cxfConfiguration.delete();
		}
		catch (Exception e) {
		}

		if (!countDownLatch.await(10, TimeUnit.MINUTES)) {
			throw new TimeoutException("Service unregister waiting timeout");
		}
	}

	private Configuration _cxfConfiguration;
	private Configuration _restConfiguration;
	private ServiceRegistration<Application> _serviceRegistration;

}