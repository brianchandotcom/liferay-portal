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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.connection.constants.ConnectionConstants;

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
			String connectionId = StringUtil.unquote(
				(String)properties.get("connectionId"));

			_validateUniqueConnectionId(pid, connectionId);

			_validateNetworkHostAddresses(properties);
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

	private String _getMessage(String key, Object... arguments) {
		try {
			ResourceBundle resourceBundle = _getResourceBundle();

			return ResourceBundleUtil.getString(resourceBundle, key, arguments);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());
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

		_log.error("Unable to validate network host addresses");

		throw new Exception(
			_getMessage("please-set-at-least-one-network-host-address"));
	}

	private void _validateUniqueConnectionId(String pid, String connectionId)
		throws Exception {

		if (Validator.isBlank(connectionId)) {
			_log.error("Connection ID is blank");

			throw new Exception(_getMessage("please-set-a-connection-id"));
		}

		if (connectionId.equals(ConnectionConstants.REMOTE_CONNECTION_ID) ||
			connectionId.equals(ConnectionConstants.SIDECAR_CONNECTION_ID)) {

			_log.error("The ID you entered is reserved: " + connectionId);

			throw new Exception(
				_getMessage("the-id-you-entered-is-reserved-x", connectionId));
		}

		Configuration configuration = configurationAdmin.getConfiguration(
			pid, StringPool.QUESTION);

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties != null) {
			String previousConnectionId = StringUtil.unquote(
				(String)properties.get("connectionId"));

			if (!previousConnectionId.equals(connectionId)) {
				_log.error("The connection ID can not be changed");

				throw new Exception(
					_getMessage("the-connection-id-can-not-be-changed"));
			}
		}

		String filterString = String.format(
			"(&(service.factoryPid=%s)(connectionId=%s))",
			ElasticsearchConnectionConfiguration.class.getName(), connectionId);

		Configuration[] configurations = configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		configuration = configurations[0];

		if (pid.equals(configuration.getPid())) {
			return;
		}

		_log.error(
			"There is already a connection with the ID: " + connectionId);

		throw new Exception(
			_getMessage(
				"there-is-already-a-connection-with-the-id-x", connectionId));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchConnectionConfigurationModelListener.class);

}