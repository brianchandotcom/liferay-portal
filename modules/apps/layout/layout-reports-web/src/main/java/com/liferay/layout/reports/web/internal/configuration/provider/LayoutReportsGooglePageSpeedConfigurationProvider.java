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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration",
	service = LayoutReportsGooglePageSpeedConfigurationProvider.class
)
public class LayoutReportsGooglePageSpeedConfigurationProvider {

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

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutReportsGooglePageSpeedConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutReportsGooglePageSpeedConfiguration.class, properties);
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

	@Reference
	private ConfigurationProvider _configurationProvider;

	private LayoutReportsGooglePageSpeedConfiguration
		_layoutReportsGooglePageSpeedConfiguration;

}