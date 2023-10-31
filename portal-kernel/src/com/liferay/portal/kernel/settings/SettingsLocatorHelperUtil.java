/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Iván Zaera
 */
public class SettingsLocatorHelperUtil {

	public static Settings getCompanyConfigurationBeanSettings(
		long companyId, String configurationPid, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getCompanyConfigurationBeanSettings(
			companyId, configurationPid, parentSettings);
	}

	public static Settings getCompanyPortletPreferencesSettings(
		long companyId, String settingsId, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getCompanyPortletPreferencesSettings(
			companyId, settingsId, parentSettings);
	}

	public static Settings getGroupConfigurationBeanSettings(
		long groupId, String configurationPid, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getGroupConfigurationBeanSettings(
			groupId, configurationPid, parentSettings);
	}

	public static Settings getPortletInstanceConfigurationBeanSettings(
		String portletId, String configurationPid, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.
			getPortletInstanceConfigurationBeanSettings(
				portletId, configurationPid, parentSettings);
	}

	public static SettingsDescriptor getSettingsDescriptor(String settingsId) {
		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getSettingsDescriptor(settingsId);
	}

	public static SettingsLocatorHelper getSettingsLocatorHelper() {
		return _settingsLocatorHelperSnapshot.get();
	}

	public Settings getConfigurationBeanSettings(String settingsId) {
		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getConfigurationBeanSettings(settingsId);
	}

	public Settings getGroupPortletPreferencesSettings(
		long groupId, String settingsId, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getGroupPortletPreferencesSettings(
			groupId, settingsId, parentSettings);
	}

	public Settings getPortalPreferencesSettings(
		long companyId, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getPortalPreferencesSettings(
			companyId, parentSettings);
	}

	public Settings getPortletInstancePortletPreferencesSettings(
		long companyId, long plid, String portletId, Settings parentSettings) {

		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.
			getPortletInstancePortletPreferencesSettings(
				companyId, plid, portletId, parentSettings);
	}

	public Settings getServerSettings(String settingsId) {
		SettingsLocatorHelper settingsLocatorHelper =
			_settingsLocatorHelperSnapshot.get();

		return settingsLocatorHelper.getServerSettings(settingsId);
	}

	private static final Snapshot<SettingsLocatorHelper>
		_settingsLocatorHelperSnapshot = new Snapshot<>(
			SettingsLocatorHelperUtil.class, SettingsLocatorHelper.class);

}