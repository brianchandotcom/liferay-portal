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
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Dictionary;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationProviderUtil {

	public static <T> void deleteCompanyConfiguration(
			Class<T> clazz, long companyId)
		throws ConfigurationException {

		_configurationProvider.deleteCompanyConfiguration(clazz, companyId);
	}

	public static <T> void deleteGroupConfiguration(
			Class<T> clazz, long groupId)
		throws ConfigurationException {

		_configurationProvider.deleteGroupConfiguration(clazz, groupId);
	}

	public static <T> void deletePortletInstanceConfiguration(
			Class<T> clazz, String portletId)
		throws ConfigurationException {

		_configurationProvider.deletePortletInstanceConfiguration(
			clazz, portletId);
	}

	public static <T> void deleteSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		_configurationProvider.deleteSystemConfiguration(clazz);
	}

	public static <T> T getCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException {

		return _configurationProvider.getCompanyConfiguration(clazz, companyId);
	}

	public static <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(clazz, settingsLocator);
	}

	public static ConfigurationProvider getConfigurationProvider() {
		return _configurationProvider;
	}

	public static <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException {

		return _configurationProvider.getGroupConfiguration(clazz, groupId);
	}

	public static <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, String portletId)
		throws ConfigurationException {

		return _configurationProvider.getPortletInstanceConfiguration(
			clazz, layout, portletId);
	}

	public static <T> T getSystemConfiguration(Class<T> clazz)
		throws ConfigurationException {

		return _configurationProvider.getSystemConfiguration(clazz);
	}

	public static <T> void saveCompanyConfiguration(
			Class<T> clazz, long companyId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		_configurationProvider.saveCompanyConfiguration(
			clazz, companyId, properties);
	}

	public static <T> void saveGroupConfiguration(
			Class<T> clazz, long groupId, Dictionary<String, Object> properties)
		throws ConfigurationException {

		_configurationProvider.saveGroupConfiguration(
			clazz, groupId, properties);
	}

	public static <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException {

		_configurationProvider.savePortletInstanceConfiguration(
			clazz, portletId, properties);
	}

	public static <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException {

		_configurationProvider.saveSystemConfiguration(clazz, properties);
	}

	private static volatile ConfigurationProvider _configurationProvider =
		ServiceProxyFactory.newServiceTrackedInstance(
			ConfigurationProvider.class, ConfigurationProviderUtil.class,
			"_configurationProvider", true);

}