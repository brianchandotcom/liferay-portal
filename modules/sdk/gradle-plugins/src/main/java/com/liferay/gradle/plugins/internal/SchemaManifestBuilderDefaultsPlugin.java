/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.schema.manifest.builder.SchemaManifestBuilderPlugin;
import com.liferay.gradle.plugins.util.PortalTools;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Luis Ortiz
 */
public class SchemaManifestBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<SchemaManifestBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new SchemaManifestBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project,
		SchemaManifestBuilderPlugin schemaManifestBuilderPlugin) {

		PortalTools.addPortalToolDependencies(
			project, SchemaManifestBuilderPlugin.CONFIGURATION_NAME,
			PortalTools.GROUP,
			"com.liferay.portal.tools.schema.manifest.builder");
	}

	@Override
	protected Class<SchemaManifestBuilderPlugin> getPluginClass() {
		return SchemaManifestBuilderPlugin.class;
	}

	private SchemaManifestBuilderDefaultsPlugin() {
	}

}