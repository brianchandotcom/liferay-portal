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

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationListener;

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

		CountDownLatch countDownLatch = new CountDownLatch(1);

		String markerPID = ConfigurationTestUtil.class.getName();

		ConfigurationListener configurationListener = configurationEvent -> {
			if (markerPID.equals(configurationEvent.getPid())) {
				countDownLatch.countDown();
			}
		};

		ServiceRegistration<ConfigurationListener> serviceRegistration =
			_bundleContext.registerService(
				ConfigurationListener.class, configurationListener, null);

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

			markerConfiguration.delete();

			countDownLatch.await();
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationTemporarySwapper.class);

		_bundleContext = bundle.getBundleContext();
	}

}