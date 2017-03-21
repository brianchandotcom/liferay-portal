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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletInstance;
import com.liferay.portal.kernel.settings.SettingsLocator;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface ConfigurationProvider {

	public <T> T getCompanyConfiguration(Class<T> clazz, long companyId)
		throws ConfigurationException;

	public <T> T getConfiguration(
			Class<T> clazz, SettingsLocator settingsLocator)
		throws ConfigurationException;

	public <T> T getGroupConfiguration(Class<T> clazz, long groupId)
		throws ConfigurationException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
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

}