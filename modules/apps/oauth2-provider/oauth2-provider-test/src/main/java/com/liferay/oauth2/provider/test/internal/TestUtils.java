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

package com.liferay.oauth2.provider.test.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Carlos Sierra Andrés
 */
public class TestUtils {

	public static Configuration configurationExists(
			BundleContext bundleContext, String pid)
		throws InvalidSyntaxException, IOException {

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			Configuration[] configurations =
				configurationAdmin.listConfigurations(
					"(" + Constants.SERVICE_PID + "=" + pid + ")");

			if (ArrayUtil.isEmpty(configurations)) {
				return null;
			}

			return configurations[0];
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	public static Configuration createFactoryConfiguration(
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
						return
							"Test managedservicefactory for pid " + factoryPid;
					}

					@Override
					public void updated(
						String pid, Dictionary<String, ?> updatedProperties) {

						if (Validator.isNull(updatedProperties)) {
							return;
						}

						if (isIncluded(properties, updatedProperties)) {
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

	public static void deleteConfiguration(
		BundleContext bundleContext, String pid) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		HashMapDictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		registrationProperties.put(Constants.SERVICE_PID, pid);

		ServiceRegistration<ManagedService> serviceRegistration =
			bundleContext.registerService(
				ManagedService.class,
				incomingProperties -> {
					if (Validator.isNull(incomingProperties)) {
						countDownLatch.countDown();
					}
				},
				registrationProperties);

		try {
			ServiceReference<ConfigurationAdmin> serviceReference =
				bundleContext.getServiceReference(ConfigurationAdmin.class);

			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			try {
				Configuration configuration =
					configurationAdmin.getConfiguration(
						pid, StringPool.QUESTION);

				configuration.delete();

				countDownLatch.await(5, TimeUnit.MINUTES);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	public static boolean isIncluded(
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

	public static Configuration updateConfiguration(
		BundleContext bundleContext, String servicePid,
		Dictionary<String, ?> properties) {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		HashMapDictionary<String, Object> registrationProperties =
			new HashMapDictionary<>();

		registrationProperties.put(Constants.SERVICE_PID, servicePid);

		ServiceRegistration<ManagedService> serviceRegistration =
			bundleContext.registerService(
				ManagedService.class,
				updatedProperties -> {
					if (Validator.isNull(updatedProperties)) {
						return;
					}

					if (isIncluded(properties, updatedProperties)) {
						countDownLatch.countDown();
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
				configuration = configurationAdmin.getConfiguration(
					servicePid, StringPool.QUESTION);

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

}