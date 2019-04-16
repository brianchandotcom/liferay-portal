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

package com.liferay.portal.remote.cors.test.internal.activator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.configuration.WebContextCORSConfiguration;

import java.io.IOException;

import java.util.ArrayDeque;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class BaseTestPreparatorBundleActivator
	implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		prepareTest();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		for (AutoCloseable autoCloseable : _autoCloseables) {
			autoCloseable.close();
		}
	}

	private Configuration _createFactoryConfiguration(
		BundleContext bundleContext, String factoryPid,
		Dictionary<String, Object> properties) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Dictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		registrationProperties.put(Constants.SERVICE_PID, factoryPid);

		ServiceRegistration<ManagedServiceFactory> serviceRegistration =
			bundleContext.registerService(
				ManagedServiceFactory.class,
				new ManagedServiceFactory() {

					@Override
					public void deleted(String pid) {
					}

					@Override
					public String getName() {
						return "Test managed service factory for PID " +
							factoryPid;
					}

					@Override
					public void updated(
						String pid, Dictionary<String, ?> updatedProperties) {

						if (updatedProperties == null) {
							return;
						}

						if (_isIncluded(properties, updatedProperties)) {
							countDownLatch.countDown();
						}
					}

				},
				registrationProperties);

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration configuration = null;

			try {
				configuration = configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION);

				configuration.update(properties);

				countDownLatch.await(5, TimeUnit.MINUTES);

				return configuration;
			}
			catch (IOException ioe) {
				throw new RuntimeException(ioe);
			}
			catch (InterruptedException ie) {
				try {
					configuration.delete();
				}
				catch (IOException ioe) {
					throw new RuntimeException(ioe);
				}

				throw new RuntimeException(ie);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	protected void createFactoryConfiguration(
		Dictionary<String, Object> properties) {

		Configuration configuration = _createFactoryConfiguration(
			_bundleContext, WebContextCORSConfiguration.class.getName(),
			properties);

		_autoCloseables.add(configuration::delete);
	}

	private boolean _isIncluded(
		Dictionary<String, ?> properties1, Dictionary<String, ?> properties2) {

		if (properties1.size() > properties2.size()) {
			return false;
		}

		Enumeration<String> keys = properties1.keys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (!Objects.deepEquals(
					properties1.get(key), properties2.get(key))) {

				return false;
			}
		}

		return true;
	}

	protected abstract void prepareTest();

	protected void registerJaxRsApplication(
		Application application, String path,
		Dictionary<String, Object> properties) {

		if ((properties == null) || properties.isEmpty()) {
			properties = new HashMapDictionary<>();
		}

		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/" + path);

		ServiceRegistration<Application> serviceRegistration =
			_bundleContext.registerService(
				Application.class, application, properties);

		_autoCloseables.add(serviceRegistration::unregister);
	}

	private final Queue<AutoCloseable> _autoCloseables = new ArrayDeque<>();
	private BundleContext _bundleContext;

}