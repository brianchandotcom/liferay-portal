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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.StringReader;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = FormNavigatorEntryConfigurationRetriever.class)
public class FormNavigatorEntryConfigurationRetriever {

	public Optional<List<String>> getFormNavigatorEntryKeys(
		String formNavigatorId, String categoryKey, String context) {

		try {
			Properties formNavigatorEntryKeysProperties =
				_getFormNavigatorEntryKeysProperties(formNavigatorId);

			String formNavigatorEntryKeys = null;

			if (Validator.isNotNull(context)) {
				formNavigatorEntryKeys =
					formNavigatorEntryKeysProperties.getProperty(
						context + StringPool.PERIOD + categoryKey);
			}

			if (formNavigatorEntryKeys == null) {
				formNavigatorEntryKeys =
					formNavigatorEntryKeysProperties.getProperty(context);
			}

			if (formNavigatorEntryKeys != null) {
				return Optional.of(_splitKeys(formNavigatorEntryKeys));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return Optional.empty();
	}

	@Reference(bind = "-", unbind = "-")
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		_configurationAdmin = configurationAdmin;
	}

	private void _addProperties(
		StringBuilder sb,
		FormNavigatorConfiguration formNavigatorConfiguration) {

		String[] formNavigatorEntryKeys =
			formNavigatorConfiguration.formNavigatorEntryKeys();

		for (String line : formNavigatorEntryKeys) {
			sb.append(line);
			sb.append(StringPool.NEW_LINE);
		}
	}

	private List<FormNavigatorConfiguration> _getConfigurations()
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=" +
				FormNavigatorConfiguration.class.getName() + ")");

		return ListUtil.toList(configurations).stream().map(
			configuration -> ConfigurableUtil.createConfigurable(
				FormNavigatorConfiguration.class,
				configuration.getProperties())).collect(Collectors.toList());
	}

	private Properties _getFormNavigatorEntryKeysProperties(
			String formNavigatorId)
		throws InvalidSyntaxException, IOException {

		StringBuilder sb = new StringBuilder();

		List<FormNavigatorConfiguration> configurations = _getConfigurations();

		for (FormNavigatorConfiguration configuration : configurations) {
			String curFormNavigatorId = configuration.formNavigatorId();

			if (curFormNavigatorId.equals(formNavigatorId)) {
				_addProperties(sb, configuration);
			}
		}

		Properties properties = new Properties();

		properties.load(new StringReader(sb.toString()));

		return properties;
	}

	private List<String> _splitKeys(String formNavigatorEntryKeys) {
		return Arrays.stream(StringUtil.split(formNavigatorEntryKeys)).map(
			String::trim).collect(Collectors.toList());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormNavigatorEntryConfigurationRetriever.class);

	private ConfigurationAdmin _configurationAdmin;

}