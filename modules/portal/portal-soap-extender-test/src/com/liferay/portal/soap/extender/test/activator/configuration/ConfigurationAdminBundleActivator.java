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

package com.liferay.portal.soap.extender.test.activator.configuration;

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

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			_cxfConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration",
				null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("contextPath", "/soap-test");

			_cxfConfiguration.update(properties);

			_jaxWsApiConfiguration = configurationAdmin.getConfiguration(
				"com.liferay.portal.soap.extender.configuration." +
					"JaxWsApiConfiguration",
				null);

			properties = new Hashtable<>();

			properties.put("contextPath", "/soap-test");
			properties.put("timeout", 10000);

			_jaxWsApiConfiguration.update(properties);

			_soapConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.soap.extender.configuration." +
					"SoapExtenderConfiguration",
				null);

			properties = new Hashtable<>();

			properties.put("contextPaths", new String[] {"/soap-test"});
			properties.put(
				"jaxWsHandlerFilterStrings", new String[] {"(soap.address=*)"});
			properties.put(
				"jaxWsServiceFilterStrings", new String[] {"(jaxws=true)"});

			_soapConfiguration.update(properties);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		try {
			_cxfConfiguration.delete();
		}
		catch (Exception e) {
		}

		try {
			_jaxWsApiConfiguration.delete();
		}
		catch (Exception e) {
		}

		try {
			_soapConfiguration.delete();
		}
		catch (Exception e) {
		}
	}

	private Configuration _cxfConfiguration;
	private Configuration _jaxWsApiConfiguration;
	private Configuration _soapConfiguration;

}