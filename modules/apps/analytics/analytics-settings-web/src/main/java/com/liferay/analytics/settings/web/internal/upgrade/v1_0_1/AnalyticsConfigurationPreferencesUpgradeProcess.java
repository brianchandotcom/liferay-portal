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

package com.liferay.analytics.settings.web.internal.upgrade.v1_0_1;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Shinn Lok
 */
public class AnalyticsConfigurationPreferencesUpgradeProcess
	extends UpgradeProcess {

	public AnalyticsConfigurationPreferencesUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + AnalyticsConfiguration.class.getName() + "*)");

		if (ArrayUtil.isEmpty(configurations)) {
			return;
		}

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			if ((properties == null) ||
				Validator.isNotNull(
					properties.get("liferayAnalyticsProjectId"))) {

				continue;
			}

			String faroBackendURL = GetterUtil.getString(
				properties.get("liferayAnalyticsFaroBackendURL"));

			String projectId = _getProjectId(faroBackendURL);

			if (projectId == null) {
				String liferayAnalyticsEndpointURL = GetterUtil.getString(
					properties.get("liferayAnalyticsEndpointURL"));

				projectId = _getProjectId(liferayAnalyticsEndpointURL);
			}

			properties.put("liferayAnalyticsProjectId", projectId);

			configuration.update(properties);

			UnicodeProperties unicodeProperties = new UnicodeProperties(true);

			unicodeProperties.setProperty(
				"liferayAnalyticsProjectId", projectId);

			_companyLocalService.updatePreferences(
				GetterUtil.getLong(properties.get("companyId")),
				unicodeProperties);
		}
	}

	private String _getProjectId(String faroBackendURL) {
		Matcher matcher = _pattern.matcher(faroBackendURL);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private static final Pattern _pattern = Pattern.compile(
		"https://osbasah(?:.*)-(.*)\\.lfr\\.cloud");

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;

}