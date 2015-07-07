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

			_jcrConfiguration = configurationAdmin.getConfiguration(
				"com.liferay.portal.store.jcr.configuration." +
					"JCRStoreConfiguration", null);

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

			_jcrConfiguration.update(properties);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_jcrConfiguration.delete();
		}
		catch (Exception e) {
		}
	}

	private Configuration _jcrConfiguration;

}