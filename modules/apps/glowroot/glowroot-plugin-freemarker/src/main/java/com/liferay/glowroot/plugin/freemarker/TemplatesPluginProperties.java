/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.glowroot.plugin.freemarker;

import org.glowroot.agent.plugin.api.Agent;
import org.glowroot.agent.plugin.api.config.ConfigListener;
import org.glowroot.agent.plugin.api.config.ConfigService;

/**
 * @author Fabian Bouché
 */
public class TemplatesPluginProperties {

	public static String templateInstrumentationLevel() {
		return _templateInstrumentationLevel;
	}

	private static final ConfigService _configService = Agent.getConfigService(
		"liferay-templates-plugin");
	private static String _templateInstrumentationLevel;

	private static class TemplatesPluginConfigListener
		implements ConfigListener {

		@Override
		public void onChange() {
			_recalculateProperties();
		}

		private void _recalculateProperties() {
			_templateInstrumentationLevel = _configService.getStringProperty(
				"templateInstrumentationLevel"
			).value();
		}

	}

	static {
		_configService.registerConfigListener(
			new TemplatesPluginConfigListener());
	}

}