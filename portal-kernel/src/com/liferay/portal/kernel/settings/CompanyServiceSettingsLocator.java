/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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