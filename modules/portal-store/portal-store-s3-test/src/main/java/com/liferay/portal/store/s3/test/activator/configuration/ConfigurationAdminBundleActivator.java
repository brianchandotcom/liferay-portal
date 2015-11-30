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

package com.liferay.portal.store.s3.test.activator.configuration;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portlet.documentlibrary.store.Store;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Manuel de la Peña
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		if (!dlStoreImpl.equals("com.liferay.portal.store.s3.S3Store")) {
			return;
		}

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration s3StoreConfiguration =
				configurationAdmin.getConfiguration(
					"com.liferay.portal.store.s3.configuration." +
						"S3StoreConfiguration",
					null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("accessKey", "");
			properties.put("bucketName", "test");
			properties.put("httpClientMaxConnections", "50");
			properties.put("s3Region", "us-east-1");
			properties.put("s3StorageClass", "STANDARD");
			properties.put("secretKey", "");
			properties.put("tempDirCleanUpExpunge", "7");
			properties.put("tempDirCleanUpFrequency", "100");

			s3StoreConfiguration.update(properties);

			ServiceTracker<?, ?> serviceTracker = ServiceTrackerFactory.open(
				bundleContext,
				"(&(objectClass=" + Store.class.getName() +
					")(store.type=com.liferay.portal.store.s3.S3Store))");

			Object s3Store = serviceTracker.waitForService(10000);

			serviceTracker.close();

			if (s3Store == null) {
				s3StoreConfiguration.delete();

				throw new IllegalStateException(
					"S3 store was not registered within 10 seconds");
			}
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws IOException {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration s3StoreConfiguration =
				configurationAdmin.getConfiguration(
					"com.liferay.portal.store.s3.configuration." +
						"S3StoreConfiguration",
					null);

			s3StoreConfiguration.delete();
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

}