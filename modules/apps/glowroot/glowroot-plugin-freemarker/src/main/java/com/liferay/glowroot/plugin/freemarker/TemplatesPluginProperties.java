/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.glowroot.plugin.freemarker;

import org.glowroot.agent.plugin.api.Agent;
import org.glowroot.agent.plugin.api.config.ConfigListener;
import org.glowroot.agent.plugin.api.config.ConfigService;
import org.glowroot.agent.plugin.api.config.StringProperty;

/**
 * @author Fabian Bouché
 */
public class TemplatesPluginProperties {

	public static String instrumentationLevel() {
		return _instrumentationLevel;
	}

	private static final ConfigService _configService = Agent.getConfigService(
		"liferay-freemarker-templates-plugin");
	private static String _instrumentationLevel;

	private static class TemplatesPluginConfigListener
		implements ConfigListener {

		@Override
		public void onChange() {
			StringProperty stringProperty = _configService.getStringProperty(
				"instrumentationLevel");

			_instrumentationLevel = stringProperty.value();
		}

	}

	static {
		_configService.registerConfigListener(
			new TemplatesPluginConfigListener());
	}

}