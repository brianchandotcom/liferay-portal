/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static SettingsDescriptor getSettingsDescriptor(String settingsId) {
		return _settingsLocatorHelper.getSettingsDescriptor(settingsId);
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