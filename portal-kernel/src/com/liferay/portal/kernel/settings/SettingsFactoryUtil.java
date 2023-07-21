/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Raymond Augé
 * @author Jorge Ferrer
 */
public class SettingsFactoryUtil {

	public static Settings getSettings(SettingsLocator settingsLocator)
		throws SettingsException {

		Settings settings = settingsLocator.getSettings();

		if (settings instanceof FallbackSettings) {
			return settings;
		}

		String settingsId = settingsLocator.getSettingsId();

		settingsId = PortletIdCodec.decodePortletName(settingsId);

		FallbackKeys fallbackKeys = _fallbackKeysServiceTrackerMap.getService(
			settingsId);

		if (fallbackKeys != null) {
			settings = new FallbackSettings(settings, fallbackKeys);
		}

		return settings;
	}

	public static SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	private static final ServiceTrackerMap<String, FallbackKeys>
		_fallbackKeysServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				SystemBundleUtil.getBundleContext(), FallbackKeys.class,
				"settingsId");
	private static volatile SettingsFactory _settingsFactory =
		ServiceProxyFactory.newServiceTrackedInstance(
			SettingsFactory.class, SettingsFactoryUtil.class,
			"_settingsFactory", true);

}