/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.javadoc.formatter.JavadocFormatterPlugin;
import com.liferay.gradle.plugins.util.PortalTools;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterDefaultsPlugin
	extends BaseDefaultsPlugin<JavadocFormatterPlugin> {

	public static final Plugin<Project> INSTANCE =
		new JavadocFormatterDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, JavadocFormatterPlugin javadocFormatterPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, JavadocFormatterPlugin.CONFIGURATION_NAME,
			PortalTools.GROUP, _PORTAL_TOOL_NAME);
	}

	@Override
	protected Class<JavadocFormatterPlugin> getPluginClass() {
		return JavadocFormatterPlugin.class;
	}

	private JavadocFormatterDefaultsPlugin() {
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.javadoc.formatter";

}