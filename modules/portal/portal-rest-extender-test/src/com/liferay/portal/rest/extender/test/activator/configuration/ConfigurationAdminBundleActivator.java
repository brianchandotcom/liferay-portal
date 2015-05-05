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

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Carlos Sierra Andrés
 */
public class ConfigurationAdminBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			_cxfConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration", null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("contextPath", "/rest-test");

			_cxfConfiguration.update(properties);

			_restConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.rest.extender.configuration." +
					"RestExtenderConfiguration", null);

			properties = new Hashtable<>();

			properties.put(
				"jaxRsApplicationFilterStrings",
				new String[] {"(jaxrs.application=true)"});
			properties.put("contextPaths", new String[] {"/rest-test"});

			_restConfiguration.update(properties);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_restConfiguration.delete();

		_cxfConfiguration.delete();
	}

	private Configuration _cxfConfiguration;
	private Configuration _restConfiguration;

}