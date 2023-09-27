/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<WSDDBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new WSDDBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, WSDDBuilderPlugin wsddBuilderPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, WSDDBuilderPlugin.CONFIGURATION_NAME, PortalTools.GROUP,
			_PORTAL_TOOL_NAME);
	}

	@Override
	protected Class<WSDDBuilderPlugin> getPluginClass() {
		return WSDDBuilderPlugin.class;
	}

	private WSDDBuilderDefaultsPlugin() {
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.wsdd.builder";

}