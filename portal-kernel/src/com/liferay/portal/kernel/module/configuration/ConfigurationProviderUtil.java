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

package com.liferay.portal.kernel.module.configuration;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.Dictionary;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationProviderUtil {

	public static <T> void deleteCompanyConfiguration(
			Class<T> clazz, long companyId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.deleteCompanyConfiguration(clazz, companyId);
	}

	public static <T> void deleteGroupConfiguration(
			Class<T> clazz, long groupId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.deleteGroupConfiguration(clazz, groupId);
	}

	public static <T> void deletePortletInstanceConfiguration(
			Class<T> clazz, String portletId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.deletePortletInstanceConfiguration(
			clazz, portletId);
	}

	public static <T> void deleteSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.deleteSystemConfiguration(clazz);
	}

	public static <T> T getCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		return configurationProvider.getCompanyConfiguration(clazz, companyId);
	}

	public static <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		return configurationProvider.getConfiguration(clazz, settingsLocator);
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _configurationProvider.get(0);
	}

	public static <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		return configurationProvider.getGroupConfiguration(clazz, groupId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getPortletInstanceConfiguration(Class, Layout, String)}
	 */
	@Deprecated
	public static <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, PortletInstance portletInstance)
		throws ConfigurationException {

		return getPortletInstanceConfiguration(
			clazz, layout, portletInstance.getPortletInstanceKey());
	}

	public static <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, String portletId)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		return configurationProvider.getPortletInstanceConfiguration(
			clazz, layout, portletId);
	}

	public static <T> T getSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		return configurationProvider.getSystemConfiguration(clazz);
	}

	public static <T> void saveCompanyConfiguration(
			Class<T> clazz, long companyId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.saveCompanyConfiguration(
			clazz, companyId, properties);
	}

	public static <T> void saveGroupConfiguration(
			Class<T> clazz, long groupId, Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.saveGroupConfiguration(
			clazz, groupId, properties);
	}

	public static <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.savePortletInstanceConfiguration(
			clazz, portletId, properties);
	}

	public static <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException {

		ConfigurationProvider configurationProvider =
			getConfigurationProvider();

		configurationProvider.saveSystemConfiguration(clazz, properties);
	}

	private static final ServiceTrackerList<ConfigurationProvider>
		_configurationProvider = ServiceTrackerCollections.openList(
			ConfigurationProvider.class);

}