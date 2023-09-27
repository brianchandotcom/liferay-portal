/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.extra.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Leon Chi
 */
public class ExtraPortletAppConfigRegistry {

	public static ExtraPortletAppConfig getExtraPortletAppConfig(
		String servletContextName) {

		return _extraPortletAppConfigs.get(servletContextName);
	}

	public static void registerExtraPortletAppConfig(
		String servletContextName,
		ExtraPortletAppConfig extraPortletAppConfig) {

		_extraPortletAppConfigs.put(servletContextName, extraPortletAppConfig);
	}

	public static void unregisterExtraPortletAppConfig(
		String servletContextName) {

		_extraPortletAppConfigs.remove(servletContextName);
	}

	private static final ConcurrentMap<String, ExtraPortletAppConfig>
		_extraPortletAppConfigs = new ConcurrentHashMap<>();

}