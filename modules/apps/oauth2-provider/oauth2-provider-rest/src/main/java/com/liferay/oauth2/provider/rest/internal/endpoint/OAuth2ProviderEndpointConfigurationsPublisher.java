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

package com.liferay.oauth2.provider.rest.internal.endpoint;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true, property = "context.path=/oauth2")
public class OAuth2ProviderEndpointConfigurationsPublisher {

	@Activate
	protected void activate(
			BundleContext bundleContext, final Map<String, Object> properties)
		throws IOException {

		ServiceReference<ConfigurationAdmin> serviceReference =
			bundleContext.getServiceReference(ConfigurationAdmin.class);

		try {
			ConfigurationAdmin configurationAdmin = bundleContext.getService(
				serviceReference);

			String contextPath = MapUtil.getString(
				properties, "context.path", "/oauth2");

			_createCXFConfiguration(configurationAdmin, contextPath);
			_createRESTConfiguration(configurationAdmin, contextPath);
		}
		catch (InvalidSyntaxException ise) {
			_log.error("Unable to create configuration", ise);
		}
		finally {
			bundleContext.ungetService(serviceReference);
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			_cxfConfiguration.delete();
		}
		catch (IOException ioe) {
			_log.error("Unable to remove CXF configuration", ioe);
		}
	}

	private void _createCXFConfiguration(
			ConfigurationAdmin configurationAdmin, String contextPath)
		throws IOException {

		_cxfConfiguration = configurationAdmin.createFactoryConfiguration(
			"com.liferay.portal.remote.cxf.common.configuration." +
				"CXFEndpointPublisherConfiguration",
			"?");

		Dictionary<String, Object> dictionary = new Hashtable<>();

		dictionary.put("contextPath", contextPath);

		_cxfConfiguration.update(dictionary);
	}

	private void _createRESTConfiguration(
			ConfigurationAdmin configurationAdmin, String contextPath)
		throws InvalidSyntaxException, IOException {

		String restComponentNameFilter =
			"(component.name=" + OAuth2EndpointApplication.class.getName() +
				")";

		StringBundler sb = new StringBundler(5);

		sb.append("(&(service.factoryPid=");
		sb.append("com.liferay.portal.remote.rest.extender.configuration.");
		sb.append("RestExtenderConfiguration)(jaxRsApplicationFilterStrings=");
		sb.append(_escapeFilterArgument(restComponentNameFilter));
		sb.append("))");

		Configuration[] restConfigurations =
			configurationAdmin.listConfigurations(sb.toString());

		if ((restConfigurations != null) && (restConfigurations.length > 0)) {
			return;
		}

		Configuration restConfiguration =
			configurationAdmin.createFactoryConfiguration(
				"com.liferay.portal.remote.rest.extender.configuration." +
					"RestExtenderConfiguration",
				"?");

		Dictionary<String, Object> dictionary = new Hashtable<>();

		dictionary.put("contextPaths", new String[] {contextPath});
		dictionary.put(
			"jaxRsApplicationFilterStrings",
			new String[] {restComponentNameFilter});

		restConfiguration.update(dictionary);
	}

	private String _escapeFilterArgument(String filter) {
		return StringUtil.replace(
			filter, new String[] {"\\", "(", ")"},
			new String[] {"\\\\", "\\(", "\\)"});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ProviderEndpointConfigurationsPublisher.class);

	private Configuration _cxfConfiguration;
	private Configuration _restConfiguration;

}