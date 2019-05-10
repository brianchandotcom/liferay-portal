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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getPortletInstanceConfiguration(Class, Layout, String)}
	 */
	@Deprecated
	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, PortletInstance portletInstance)
		throws ConfigurationException;

	public <T> T getPortletInstanceConfiguration(
			Class<T> clazz, Layout layout, String portletId)
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

	public <T> void savePortletInstanceConfiguration(
			Class<T> clazz, String portletId,
			Dictionary<String, Object> properties)
		throws ConfigurationException;

	public <T> void saveSystemConfiguration(
			Class<T> clazz, Dictionary<String, Object> properties)
		throws ConfigurationException;

}