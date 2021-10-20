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

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Iván Zaera
 */
public class SettingsLocatorHelperUtil {

	public static Settings getCompanyConfigurationBeanSettings(
		long companyId, String configurationPid, Settings parentSettings) {

		return _settingsLocatorHelper.getCompanyConfigurationBeanSettings(
			companyId, configurationPid, parentSettings);
	}

	public static Settings getCompanyPortletPreferencesSettings(
		long companyId, String settingsId, Settings parentSettings) {

		return _settingsLocatorHelper.getCompanyPortletPreferencesSettings(
			companyId, settingsId, parentSettings);
	}

	public static Settings getGroupConfigurationBeanSettings(
		long groupId, String configurationPid, Settings parentSettings) {

		return _settingsLocatorHelper.getGroupConfigurationBeanSettings(
			groupId, configurationPid, parentSettings);
	}

	public static Settings getPortletInstanceConfigurationBeanSettings(
		String portletId, String configurationPid, Settings parentSettings) {

		return _settingsLocatorHelper.
			getPortletInstanceConfigurationBeanSettings(
				portletId, configurationPid, parentSettings);
	}

	public static SettingsLocatorHelper getSettingsLocatorHelper() {
		return _settingsLocatorHelper;
	}

	public Settings getConfigurationBeanSettings(String settingsId) {
		return _settingsLocatorHelper.getConfigurationBeanSettings(settingsId);
	}

	public Settings getGroupPortletPreferencesSettings(
		long groupId, String settingsId, Settings parentSettings) {

		return _settingsLocatorHelper.getGroupPortletPreferencesSettings(
			groupId, settingsId, parentSettings);
	}

	public Settings getPortalPreferencesSettings(
		long companyId, Settings parentSettings) {

		return _settingsLocatorHelper.getPortalPreferencesSettings(
			companyId, parentSettings);
	}

	public Settings getPortletInstancePortletPreferencesSettings(
		long companyId, long plid, String portletId, Settings parentSettings) {

		return _settingsLocatorHelper.
			getPortletInstancePortletPreferencesSettings(
				companyId, plid, portletId, parentSettings);
	}

	public Settings getServerSettings(String settingsId) {
		return _settingsLocatorHelper.getServerSettings(settingsId);
	}

	private static volatile SettingsLocatorHelper _settingsLocatorHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			SettingsLocatorHelper.class, SettingsLocatorHelperUtil.class,
			"_settingsLocatorHelper", true);

}