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

package com.liferay.layout.reports.web.internal.configuration.provider;

import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedCompanyConfiguration;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Cristina González
 */
public class LayoutReportsGooglePageSpeedConfigurationProvider {

	public LayoutReportsGooglePageSpeedConfigurationProvider(
		ConfigurationProvider configurationProvider,
		LayoutReportsGooglePageSpeedConfiguration
			layoutReportsGooglePageSpeedConfiguration) {

		_configurationProvider = configurationProvider;
		_layoutReportsGooglePageSpeedConfiguration =
			layoutReportsGooglePageSpeedConfiguration;
	}

	public String getApiKey(Company company) throws ConfigurationException {
		return _getApiKey(company.getCompanyId());
	}

	public String getApiKey(Group group) throws ConfigurationException {
		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		String googlePageSpeedApikey = unicodeProperties.getProperty(
			"googlePageSpeedApiKey");

		if (Validator.isNotNull(googlePageSpeedApikey)) {
			return googlePageSpeedApikey;
		}

		return _getApiKey(group.getCompanyId());
	}

	public boolean isEnabled(Company company) throws ConfigurationException {
		return _isEnabled(company.getCompanyId());
	}

	public boolean isEnabled(Group group) throws ConfigurationException {
		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		return GetterUtil.getBoolean(
			unicodeProperties.getProperty("googlePageSpeedEnabled"),
			_isEnabled(group.getCompanyId()));
	}

	private String _getApiKey(long companyId) throws ConfigurationException {
		LayoutReportsGooglePageSpeedCompanyConfiguration
			layoutReportsGooglePageSpeedCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					LayoutReportsGooglePageSpeedCompanyConfiguration.class,
					companyId);

		String apiKey =
			layoutReportsGooglePageSpeedCompanyConfiguration.apiKey();

		if (Validator.isNotNull(apiKey)) {
			return apiKey;
		}

		return _layoutReportsGooglePageSpeedConfiguration.apiKey();
	}

	private boolean _isEnabled(long companyId) throws ConfigurationException {
		if (!_layoutReportsGooglePageSpeedConfiguration.enabled()) {
			return false;
		}

		LayoutReportsGooglePageSpeedCompanyConfiguration
			layoutReportsGooglePageSpeedCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					LayoutReportsGooglePageSpeedCompanyConfiguration.class,
					companyId);

		if (!layoutReportsGooglePageSpeedCompanyConfiguration.enabled()) {
			return false;
		}

		return true;
	}

	private final ConfigurationProvider _configurationProvider;
	private final LayoutReportsGooglePageSpeedConfiguration
		_layoutReportsGooglePageSpeedConfiguration;

}