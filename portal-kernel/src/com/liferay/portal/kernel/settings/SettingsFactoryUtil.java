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

import java.util.List;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public class SettingsFactoryUtil {

	public static ArchivedSettings getPortletInstanceArchivedSettings(
			long groupId, String portletId, String name)
		throws SettingsException {

		return _settingsFactory.getPortletInstanceArchivedSettings(
			groupId, portletId, name);
	}

	public static List<ArchivedSettings> getPortletInstanceArchivedSettingsList(
		long groupId, String portletId) {

		return _settingsFactory.getPortletInstanceArchivedSettingsList(
			groupId, portletId);
	}

	public static Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		return _settingsFactory.getSettings(settingsLocator);
	}

	public static SettingsDescriptor getSettingsDescriptor(String settingsId) {
		return _settingsFactory.getSettingsDescriptor(settingsId);
	}

	public static SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	public static void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		_settingsFactory.registerSettingsMetadata(
			settingsClass, configurationBean, fallbackKeys);
	}

	private static volatile SettingsFactory _settingsFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			SettingsFactory.class, SettingsFactoryUtil.class,
			"_settingsFactory", true);

}