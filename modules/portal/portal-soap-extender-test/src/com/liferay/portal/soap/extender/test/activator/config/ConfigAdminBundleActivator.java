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

package com.liferay.portal.soap.extender.test.activator.config;

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
public class ConfigAdminBundleActivator implements BundleActivator {

	public static final String _TEST_CONTEXT_PATH = "/soap-test";

	@Override
	public void start(BundleContext context) throws Exception {
		ServiceReference<ConfigurationAdmin> serviceReference =
			context.getServiceReference(ConfigurationAdmin.class);

		ConfigurationAdmin configurationAdmin = context.getService(
			serviceReference);

		try {
			_cxfConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.ws.WebServicePublisherConfiguration", null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("contextPath", _TEST_CONTEXT_PATH);

			_cxfConfiguration.update(properties);

			_soapConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.soap.extender.SoapExtenderConfiguration",
				null);

			properties = new Hashtable<>();

			properties.put("contextPaths", new String[] {_TEST_CONTEXT_PATH});
			properties.put("handlers", new String[] {"(soap.address=*)"});
			properties.put("serviceFilters", new String[] {"(jaxws=true)"});

			_soapConfiguration.update(properties);

			_jaxwsApiConfiguration = configurationAdmin.getConfiguration(
				"com.liferay.portal.soap.extender.JaxwsApiConfiguration", null);

			properties = new Hashtable<>();

			properties.put("contextPath", _TEST_CONTEXT_PATH);
			properties.put("timeout", 10000);

			_jaxwsApiConfiguration.update(properties);
		}
		finally {
			context.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		_jaxwsApiConfiguration.delete();

		_soapConfiguration.delete();

		_cxfConfiguration.delete();
	}

	private Configuration _cxfConfiguration;
	private Configuration _jaxwsApiConfiguration;
	private Configuration _soapConfiguration;

}