/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.soap.extender.test;

import com.liferay.petra.string.StringBundler;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.TimeoutException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

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
				"com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration",
				null);

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("contextPath", "/soap-test");

			_cxfConfiguration.update(properties);

			_jaxWsApiConfiguration = configurationAdmin.getConfiguration(
				"com.liferay.portal.remote.soap.extender.internal." +
					"configuration.JaxWsApiConfiguration",
				null);

			_jaxWsApiConfigurationProperties =
				_jaxWsApiConfiguration.getProperties();

			properties = new Hashtable<>();

			properties.put("contextPath", "/soap-test");
			properties.put("timeout", 10_000);

			_jaxWsApiConfiguration.update(properties);

			_soapConfiguration = configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.remote.soap.extender.internal." +
					"configuration.SoapExtenderConfiguration",
				null);

			properties = new Hashtable<>();

			properties.put("contextPaths", new String[] {"/soap-test"});
			properties.put(
				"jaxWsHandlerFilterStrings", new String[] {"(soap.address=*)"});
			properties.put(
				"jaxWsServiceFilterStrings", new String[] {"(jaxws=true)"});

			_soapConfiguration.update(properties);

			_filterString = StringBundler.concat(
				"(&(objectClass=", ServletContextHelper.class.getName(), ")(",
				HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME,
				"=soap-test))");

			try {
				WaiterUtil.waitForFilter(bundleContext, _filterString, 10_000);
			}
			catch (TimeoutException timeoutException) {
				_cleanUp();

				throw timeoutException;
			}
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		WaiterUtil.Waiter waiter = WaiterUtil.waitForFilterToDisappear(
			bundleContext, _filterString);

		_cleanUp();

		waiter.waitFor(10_000 * 60);
	}

	private void _cleanUp() {
		try {
			_soapConfiguration.delete();
		}
		catch (Exception exception) {
		}

		try {
			if (_jaxWsApiConfigurationProperties != null) {
				_jaxWsApiConfiguration.update(_jaxWsApiConfigurationProperties);
			}
			else {
				_jaxWsApiConfiguration.delete();
			}
		}
		catch (Exception exception) {
		}

		try {
			_cxfConfiguration.delete();
		}
		catch (Exception exception) {
		}
	}

	private Configuration _cxfConfiguration;
	private String _filterString;
	private Configuration _jaxWsApiConfiguration;
	private Dictionary<String, Object> _jaxWsApiConfigurationProperties;
	private Configuration _soapConfiguration;

}