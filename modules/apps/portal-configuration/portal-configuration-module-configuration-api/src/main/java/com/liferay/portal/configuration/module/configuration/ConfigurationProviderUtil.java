/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Dictionary;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationProviderUtil {

	public static <T> void deleteCompanyConfiguration(
			Class<T> clazz, long companyId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.deleteCompanyConfiguration(clazz, companyId);
	}

	public static <T> void deleteGroupConfiguration(
			Class<T> clazz, long groupId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.deleteGroupConfiguration(clazz, groupId);
	}

	public static <T> void deletePortletInstanceConfiguration(
			Class<T> clazz, String portletId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.deletePortletInstanceConfiguration(
			clazz, portletId);
	}

	public static <T> void deleteSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.deleteSystemConfiguration(clazz);
	}

	public static <T> T getCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		return configurationProvider.getCompanyConfiguration(clazz, companyId);
	}

	public static <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		return configurationProvider.getConfiguration(clazz, settingsLocator);
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _configurationProviderSnapshot.get();
	}

	public static <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		return configurationProvider.getGroupConfiguration(clazz, groupId);
	}

	public static <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, String portletId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		return configurationProvider.getPortletInstanceConfiguration(
			clazz, layout, portletId);
	}

	public static <T> T getPortletInstanceConfiguration(
			Class<T> clazz, ThemeDisplay themeDisplay)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		return configurationProvider.getPortletInstanceConfiguration(
			clazz, themeDisplay);
	}

	public static <T> T getSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		return configurationProvider.getSystemConfiguration(clazz);
	}

	public static <T> void saveCompanyConfiguration(
			Class<T> clazz, long companyId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.saveCompanyConfiguration(
			clazz, companyId, properties);
	}

	public static <T> void saveGroupConfiguration(
			Class<T> clazz, long groupId, Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.saveGroupConfiguration(
			clazz, groupId, properties);
	}

	public static <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.savePortletInstanceConfiguration(
			clazz, portletId, properties);
	}

	public static <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			_configurationProviderSnapshot.get();

		configurationProvider.saveSystemConfiguration(clazz, properties);
	}

	private static final Snapshot<ConfigurationProvider>
		_configurationProviderSnapshot = new Snapshot<>(
			ConfigurationProviderUtil.class, ConfigurationProvider.class);

}