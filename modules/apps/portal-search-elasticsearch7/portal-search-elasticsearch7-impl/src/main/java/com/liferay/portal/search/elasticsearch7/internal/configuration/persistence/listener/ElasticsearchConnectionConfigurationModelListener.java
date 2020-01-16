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

package com.liferay.portal.search.elasticsearch7.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration",
	service = ConfigurationModelListener.class
)
public class ElasticsearchConnectionConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			String connectionId = (String)properties.get("connectionId");

			_validateUniqueConnectionId(pid, connectionId);

			_validateNetworkHostAddresses(properties);

			_removeOriginalConnectionId(pid, connectionId);
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(),
				ElasticsearchConnectionConfiguration.class, getClass(),
				properties);
		}
	}

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());
	}

	private void _removeOriginalConnectionId(String pid, String connectionId)
		throws Exception {

		String filterString = String.format("(service.pid=%s)", pid);

		Configuration[] configurations = configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> properties = configuration.getProperties();

		String originalConnectionId = (String)properties.get("connectionId");

		if (connectionId.equals(originalConnectionId)) {
			return;
		}

		elasticsearchConnectionManager.removeElasticsearchConnection(
			originalConnectionId);
	}

	private void _validateNetworkHostAddresses(
			Dictionary<String, Object> properties)
		throws Exception {

		String[] networkHostAddresses = GetterUtil.getStringValues(
			properties.get("networkHostAddresses"));

		for (String networkHostAddress : networkHostAddresses) {
			if (!Validator.isBlank(networkHostAddress)) {
				return;
			}
		}

		ResourceBundle resourceBundle = _getResourceBundle();

		String message = ResourceBundleUtil.getString(
			resourceBundle, "please-set-at-least-one-network-host-address");

		throw new Exception(message);
	}

	private void _validateUniqueConnectionId(String pid, String connectionId)
		throws Exception {

		if (connectionId.equals("embedded")) {
			ResourceBundle resourceBundle = _getResourceBundle();

			String message = ResourceBundleUtil.getString(
				resourceBundle, "the-id-you-entered-is-reserved-x",
				connectionId);

			throw new Exception(message);
		}

		String filterString = String.format(
			"(&(service.factoryPid=%s)(connectionId=%s))",
			ElasticsearchConnectionConfiguration.class.getName(), connectionId);

		Configuration[] configurations = configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		if (pid.equals(configuration.getPid())) {
			return;
		}

		ResourceBundle resourceBundle = _getResourceBundle();

		String message = ResourceBundleUtil.getString(
			resourceBundle, "there-is-already-a-connection-with-the-id-x",
			connectionId);

		throw new Exception(message);
	}

}