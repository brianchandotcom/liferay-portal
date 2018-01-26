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

package com.liferay.portal.configuration.test.util;

import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.cm.ManagedService;

/**
 * @author Drew Brokke
 */
public class ConfigurationTestUtil {

	public static void deleteConfiguration(Configuration configuration)
		throws Exception {

		_updateProperties(configuration, null);
	}

	public static void deleteConfiguration(String pid) throws Exception {
		_updateProperties(_getConfiguration(pid), null);
	}

	public static void saveConfiguration(
			Configuration configuration, Dictionary<String, Object> properties)
		throws Exception {

		_updateProperties(configuration, properties);
	}

	public static void saveConfiguration(
			String pid, Dictionary<String, Object> properties)
		throws Exception {

		_updateProperties(_getConfiguration(pid), properties);
	}

	private static Configuration _getConfiguration(String pid)
		throws Exception {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));
	}

	private static void _updateProperties(
			Configuration configuration, Dictionary<String, Object> dictionary)
		throws Exception {

		CountDownLatch eventCountdownLatch = new CountDownLatch(1);
		CountDownLatch updateCountDownLatch = new CountDownLatch(2);

		String markerPID = ConfigurationTestUtil.class.getName();

		ConfigurationListener configurationListener = configurationEvent -> {
			if (markerPID.equals(configurationEvent.getPid())) {
				eventCountdownLatch.countDown();
			}
		};

		ServiceRegistration<ConfigurationListener>
			configurationListenerServiceRegistration =
				_bundleContext.registerService(
					ConfigurationListener.class, configurationListener, null);

		ManagedService managedService = properties -> {
			try {
				eventCountdownLatch.await();
			}
			catch (InterruptedException ie) {
				ReflectionUtil.throwException(ie);
			}

			updateCountDownLatch.countDown();
		};

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(Constants.SERVICE_PID, markerPID);

		ServiceRegistration<ManagedService> managedServiceServiceRegistration =
			_bundleContext.registerService(
				ManagedService.class, managedService, properties);

		try {
			if (dictionary == null) {
				configuration.delete();
			}
			else {
				configuration.update(dictionary);
			}

			Configuration markerConfiguration = OSGiServiceUtil.callService(
				_bundleContext, ConfigurationAdmin.class,
				configurationAdmin -> configurationAdmin.getConfiguration(
					markerPID, StringPool.QUESTION));

			markerConfiguration.update();

			markerConfiguration.delete();

			updateCountDownLatch.await();
		}
		finally {
			configurationListenerServiceRegistration.unregister();

			managedServiceServiceRegistration.unregister();
		}
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ConfigurationTestUtil.class);

		_bundleContext = bundle.getBundleContext();
	}

}