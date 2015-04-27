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

	public static final String _TEST_CONTEXT_PATH = "/soap-test";

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = bundleContext.getService(
			serviceReference);

		try {
			_cxfConfiguration = configurationAdmin.createFactoryConfiguration(
				_CXF_ENDPOINT_PUBLISHER_CONFIGURATION, null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("contextPath", _TEST_CONTEXT_PATH);

			_cxfConfiguration.update(properties);

			_jaxWsApiConfiguration = configurationAdmin.getConfiguration(
				_JAX_WS_API_CONFIGURATION, null);

			properties = new Hashtable<>();

			properties.put("contextPath", _TEST_CONTEXT_PATH);
			properties.put("timeout", 10000);

			_jaxWsApiConfiguration.update(properties);

			_soapConfiguration = configurationAdmin.createFactoryConfiguration(
				_SOAP_EXTENDER_CONFIGURATION, null);

			properties = new Hashtable<>();

			properties.put("contextPaths", new String[] {_TEST_CONTEXT_PATH});
			properties.put("jaxWsHandlerFilterStrings", new String[] {"(soap.address=*)"});
			properties.put("jaxWsServiceFilterStrings", new String[] {"(jaxws=true)"});

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

	private static final String _CXF_ENDPOINT_PUBLISHER_CONFIGURATION =
		"com.liferay.portal.cxf.common.configuration." +
			"CXFEndpointPublisherConfiguration";

	private static final String _JAX_WS_API_CONFIGURATION =
		"com.liferay.portal.soap.extender.internal.configuration." +
			"JaxWsApiConfiguration";

	private static final String _SOAP_EXTENDER_CONFIGURATION =
		"com.liferay.portal.soap.extender.internal.configuration." +
			"SoapExtenderConfiguration";

	private Configuration _cxfConfiguration;
	private Configuration _jaxWsApiConfiguration;
	private Configuration _soapConfiguration;

}