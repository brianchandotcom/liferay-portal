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

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public class SettingsFactoryUtil {

	public static ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		return getSettingsFactory().getPortletInstanceArchivedSettings(
			groupId, portletId, name);
	}

	public static List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		return getSettingsFactory().getPortletInstanceArchivedSettingsList(
			groupId, portletId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             SettingsLocatorHelperUtil#getServerSettings(String)}
	 */
	@Deprecated
	public static Settings getServerSettings(String settingsId) {
		return getSettingsFactory().getServerSettings(settingsId);
	}

	public static Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		return getSettingsFactory().getSettings(settingsLocator);
	}

	public static SettingsDescriptor getSettingsDescriptor(String settingsId) {
		return getSettingsFactory().getSettingsDescriptor(settingsId);
	}

	public static SettingsFactory getSettingsFactory() {
		return _settingsFactories.get(0);
	}

	public static void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		getSettingsFactory().registerSettingsMetadata(
			settingsClass, configurationBean, fallbackKeys);
	}

	private static final ServiceTrackerList<SettingsFactory>
		_settingsFactories = ServiceTrackerCollections.openList(
			SettingsFactory.class);

}