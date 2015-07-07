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

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Manuel de la Peña
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			_s3Configuration = configurationAdmin.getConfiguration(
				"com.liferay.portal.store.s3.configuration.S3Configuration",
				null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("accessKey", "");
			properties.put("bucketName", "");
			properties.put("httpClientMaxConnections", "50");
			properties.put("s3serviceDefaultBucketLocation", "US");
			properties.put("s3serviceDefaultStorageClass", "STANDARD");
			properties.put("s3serviceS3Endpoint", "s3.amazonws.com");
			properties.put("secretKey", "");
			properties.put("tempDirCleanUpExpunge", "7");
			properties.put("tempDirCleanUpFrequency", "100");

			_s3Configuration.update(properties);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_s3Configuration.delete();
		}
		catch (Exception e) {
		}
	}

	private Configuration _s3Configuration;

}