/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

/**
 * @author Drew Brokke
 */
public class SystemSettingsLocator implements SettingsLocator {

	public SystemSettingsLocator(String configurationPid) {
		_configurationPid = configurationPid;
	}

	@Override
	public Settings getSettings() throws SettingsException {
		return _settingsLocatorHelper.getConfigurationBeanSettings(
			_configurationPid);
	}

	@Override
	public String getSettingsId() {
		return _configurationPid;
	}

	private final String _configurationPid;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}