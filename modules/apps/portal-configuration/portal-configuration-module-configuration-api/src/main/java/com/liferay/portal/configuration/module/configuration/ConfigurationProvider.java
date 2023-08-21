/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.module.configuration;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Dictionary;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface ConfigurationProvider {

	public <T> void deleteCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException;

	public <T> void deleteGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException;

	public <T> void deletePortletInstanceConfiguration(
			Class<T> clazz, String portletId)
		throws ConfigurationException;

	public <T> void deleteSystemConfiguration(Class<T> clazz)
		throws ConfigurationException;

	public <T> T getCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException;

	public <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException;

	public <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException;

	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, String portletId)
		throws ConfigurationException;

	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, ThemeDisplay themeDisplay)
		throws ConfigurationException;

	public <T> T getSystemConfiguration(Class<T> clazz)
		throws ConfigurationException;

	public <T> void saveCompanyConfiguration(
			Class<T> clazz, long companyId,
			Dictionary<String, Object> properties)
		throws ConfigurationException;

	public <T> void saveGroupConfiguration(
			Class<T> clazz, long groupId, Dictionary<String, Object> properties)
		throws ConfigurationException;

	public <T> void saveGroupConfiguration(
			long groupId, String pid, Dictionary<String, Object> properties)
		throws ConfigurationException;

	public <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException;

	public <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException;

}