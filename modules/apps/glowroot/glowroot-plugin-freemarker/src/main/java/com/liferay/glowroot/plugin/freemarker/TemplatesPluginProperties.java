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

	public static boolean captureTemplateScriptInTransaction() {
		return _captureTemplateScriptInTransaction;
	}

	public static boolean captureTemplateTransformationsInOuterTransaction() {
		return _captureTemplateTransformationsInOuterTransaction;
	}

	private static boolean _captureTemplateScriptInTransaction;
	private static boolean _captureTemplateTransformationsInOuterTransaction;
	private static final ConfigService _configService = Agent.getConfigService(
		"liferay-templates-plugin");

	private static class TemplatesPluginConfigListener
		implements ConfigListener {

		@Override
		public void onChange() {
			_recalculateProperties();
		}

		private void _recalculateProperties() {
			_captureTemplateTransformationsInOuterTransaction =
				_configService.getBooleanProperty(
					"captureTemplateTransformationsInOuterTransaction"
				).value();
			_captureTemplateScriptInTransaction =
				_configService.getBooleanProperty(
					"captureTemplateScriptInTransaction"
				).value();
		}

	}

	static {
		_configService.registerConfigListener(
			new TemplatesPluginConfigListener());
	}

}