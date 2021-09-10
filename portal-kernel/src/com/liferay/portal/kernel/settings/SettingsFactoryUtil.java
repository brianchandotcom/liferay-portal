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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Iterator;
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

	public static Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		return getSettingsFactory().getSettings(settingsLocator);
	}

	public static SettingsDescriptor getSettingsDescriptor(String settingsId) {
		return getSettingsFactory().getSettingsDescriptor(settingsId);
	}

	public static SettingsFactory getSettingsFactory() {
		Iterator<SettingsFactory> iterator = _settingsFactories.iterator();

		return iterator.next();
	}

	public static void registerSettingsMetadata(
		Class<?> settingsClass, Object configurationBean,
		FallbackKeys fallbackKeys) {

		getSettingsFactory().registerSettingsMetadata(
			settingsClass, configurationBean, fallbackKeys);
	}

	private static final ServiceTrackerList<SettingsFactory, SettingsFactory>
		_settingsFactories = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), SettingsFactory.class);

}