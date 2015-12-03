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

package com.liferay.portal.store.jcr.test.activator.configuration;

import com.liferay.osgi.util.ServiceTrackerFactory;
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
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			Configuration jcrConfiguration =
				configurationAdmin.getConfiguration(
					"com.liferay.portal.store.jcr.configuration." +
						"JCRStoreConfiguration",
					null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("initializeOnStartup", Boolean.TRUE);
			properties.put("jackrabbitConfigFilePath", "repository.xml");
			properties.put("jackrabbitCredentialsPassword", "none");
			properties.put("jackrabbitCredentialsUsername", "none");
			properties.put("jackrabbitRepositoryHome", "home");
			properties.put("jackrabbitRepositoryRoot", "data/jackrabbit");
			properties.put("moveVersionLabels", Boolean.FALSE);
			properties.put("nodeDocumentlibrary", "documentlibrary");
			properties.put("workspaceName", "liferay");
			properties.put("wrapSession", Boolean.TRUE);

			jcrConfiguration.update(properties);

			ServiceTracker<?, ?> serviceTracker = ServiceTrackerFactory.open(
				bundleContext,
				"(&(objectClass=" + Store.class.getName() +
					")(store.type=com.liferay.portal.store.jcr.JCRStore))");

			Object jcrStore = serviceTracker.waitForService(10000);

			serviceTracker.close();

			if (jcrStore == null) {
				jcrConfiguration.delete();

				throw new IllegalStateException(
					"JCR store was not registered within 10 seconds");
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

			Configuration jcrConfiguration =
				configurationAdmin.getConfiguration(
					"com.liferay.portal.store.jcr.configuration." +
						"JCRStoreConfiguration",
					null);

			jcrConfiguration.delete();
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

}