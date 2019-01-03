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

package com.liferay.portal.kernel.settings;

/**
 * @author Iván Zaera
 * @author Jorge Ferrer
 * @author Drew Brokke
 */
public class CompanyServiceSettingsLocator implements SettingsLocator {

	public CompanyServiceSettingsLocator(long companyId, String settingsId) {
		this(companyId, settingsId, settingsId);
	}

	public CompanyServiceSettingsLocator(
		long companyId, String settingsId, String configurationPid) {

		_companyId = companyId;
		_settingsId = settingsId;
		_configurationPid = configurationPid;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		SystemSettingsLocator systemSettingsLocator = new SystemSettingsLocator(
			_configurationPid);

		Settings portalPreferencesSettings =
			_settingsLocatorHelper.getPortalPreferencesSettings(
				_companyId, systemSettingsLocator.getSettings());

		Settings companyConfigurationBeanSettings =
			_settingsLocatorHelper.getCompanyConfigurationBeanSettings(
				_companyId, _configurationPid, portalPreferencesSettings);

		return _settingsLocatorHelper.getCompanyPortletPreferencesSettings(
			_companyId, _settingsId, companyConfigurationBeanSettings);
	}

	@Override
	public String getSettingsId() {
		return _settingsId;
	}

	private final long _companyId;
	private final String _configurationPid;
	private final String _settingsId;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}