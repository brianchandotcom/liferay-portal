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

package com.liferay.frontend.taglib.form.navigator.util;

import com.liferay.frontend.taglib.form.navigator.configuration.FormNavigatorConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	public List<String> getFormNavigatorEntryKeys(
		String formNavigatorId, String categoryKey, String variant) {

		try {
			String expectedKey = variant + StringPool.PERIOD + categoryKey;
			Properties properties = _getFormNavigatorEntryKeys(formNavigatorId);

			String entryKeys = properties.getProperty(expectedKey);

			if (entryKeys != null) {
				return Arrays.asList(entryKeys.split(StringPool.COMMA));
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return new ArrayList<>();
	}

	@Reference(bind = "-", unbind = "-")
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		_configurationAdmin = configurationAdmin;
	}

	private void _addProperties(
		StringBuilder propertiesString,
		FormNavigatorConfiguration configuration) {

		for (String line : configuration.hiddenFormNavigatorEntryQueries()) {
			propertiesString.append(line);
			propertiesString.append("\n");
		}
	}

	private List<FormNavigatorConfiguration> _getConfigurations()
		throws InvalidSyntaxException, IOException {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.factoryPid=" +
				FormNavigatorConfiguration.class.getName() + ")");

		return Arrays.stream(configurations).map(
			configuration -> ConfigurableUtil.createConfigurable(
				FormNavigatorConfiguration.class,
				configuration.getProperties())).collect(Collectors.toList());
	}

	private Properties _getFormNavigatorEntryKeys(String formNavigatorId)
		throws InvalidSyntaxException, IOException {

		Properties properties = new Properties();
		StringBuilder sb = new StringBuilder();

		for (FormNavigatorConfiguration configuration : _getConfigurations()) {
			if (configuration.formNavigatorId().equals(formNavigatorId)) {
				_addProperties(sb, configuration);
			}
		}

		properties.load(new StringReader(sb.toString()));

		return properties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormNavigatorEntryConfigurationRetriever.class);

	private ConfigurationAdmin _configurationAdmin;

}