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
 * @author Ivan Zaera
 * @author Jorge Ferrer
 */
public class CompanyServiceSettingsLocator implements SettingsLocator {

	public CompanyServiceSettingsLocator(long companyId, String serviceName) {
		_companyId = companyId;
		_serviceName = serviceName;
	}

	@Override
	public Settings getSettings() {
		Settings portalPropertiesSettings =
			_settingsLocatorHelper.getPortalPropertiesSettings();

		Settings configurationBeanSettings =
			_settingsLocatorHelper.getConfigurationBeanSettings(
				_serviceName, portalPropertiesSettings);

		Settings portalPreferencesSettings =
			_settingsLocatorHelper.getPortalPreferencesSettings(
				_companyId, configurationBeanSettings);

		return _settingsLocatorHelper.getCompanyPortletPreferencesSettings(
			_companyId, _serviceName, portalPreferencesSettings);
	}

	@Override
	public String getSettingsId() {
		return _serviceName;
	}

	private final long _companyId;
	private final String _serviceName;
	private final SettingsLocatorHelper _settingsLocatorHelper =
		SettingsLocatorHelperUtil.getSettingsLocatorHelper();

}