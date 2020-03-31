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

package com.liferay.portal.search.elasticsearch7.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;

import java.util.Dictionary;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Bryan Engler
 */
public class UpgradeElasticsearchConfiguration extends UpgradeProcess {

	public UpgradeElasticsearchConfiguration(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeElasticsearchConfigurations();
	}

	protected void upgradeElasticsearchConfigurations() throws Exception {
		Configuration elasticsearchConfiguration = _getConfiguration(
			ElasticsearchConfiguration.class.getName());

		Dictionary<String, Object> elasticsearchConfigurationProperties =
			new HashMapDictionary<>();

		if (elasticsearchConfiguration != null) {
			elasticsearchConfigurationProperties =
				elasticsearchConfiguration.getProperties();
		}

		String operationMode = GetterUtil.getString(
			elasticsearchConfigurationProperties.get("operationMode"));

		if ((elasticsearchConfiguration == null) ||
			!operationMode.equals("REMOTE")) {

			_setDefaultConfigurationActivePropertyToFalse();

			return;
		}

		String remoteClusterConnectionId = GetterUtil.getString(
			elasticsearchConfigurationProperties.get(
				"remoteClusterConnectionId"));

		if (Validator.isBlank(remoteClusterConnectionId)) {
			elasticsearchConfigurationProperties.put(
				"remoteClusterConnectionId", "remote");

			elasticsearchConfiguration.update(
				elasticsearchConfigurationProperties);
		}
		else if (!remoteClusterConnectionId.equals("remote")) {
			_setDefaultConfigurationActivePropertyToFalse();
		}
	}

	private Configuration _getConfiguration(String className) throws Exception {
		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=", className, ")");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations != null) {
			return configurations[0];
		}

		return null;
	}

	private Configuration _getDefaultConfiguration(String className)
		throws Exception {

		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=", className, ".*)");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (ArrayUtil.isEmpty(configurations)) {
			return null;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String fileName = GetterUtil.getString(
				properties.get("felix.fileinstall.filename"));

			if (fileName.endsWith("-default.config")) {
				return configuration;
			}
		}

		return null;
	}

	private void _setDefaultConfigurationActivePropertyToFalse()
		throws Exception {

		Configuration elasticsearchConnectionConfiguration =
			_getDefaultConfiguration(
				ElasticsearchConnectionConfiguration.class.getName());

		if (elasticsearchConnectionConfiguration == null) {
			return;
		}

		Dictionary<String, Object>
			elasticsearchConnectionConfigurationProperties =
				elasticsearchConnectionConfiguration.getProperties();

		elasticsearchConnectionConfigurationProperties.put("active", false);

		elasticsearchConnectionConfiguration.update(
			elasticsearchConnectionConfigurationProperties);
	}

	private final ConfigurationAdmin _configurationAdmin;

}