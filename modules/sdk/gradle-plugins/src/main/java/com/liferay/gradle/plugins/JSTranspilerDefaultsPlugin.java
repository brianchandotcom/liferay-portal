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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.js.transpiler.JSTranspilerExtension;
import com.liferay.gradle.plugins.js.transpiler.JSTranspilerPlugin;
import com.liferay.gradle.plugins.util.GradleUtil;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JSTranspilerDefaultsPlugin
	extends BaseDefaultsPlugin<JSTranspilerPlugin> {

	@Override
	protected void configureDefaults(
		Project project, JSTranspilerPlugin jsTranspilerPlugin) {

		configureJSTranspiler(project);
	}

	protected void configureJSTranspiler(Project project) {
		JSTranspilerExtension jsTranspilerExtension = GradleUtil.getExtension(
			project, JSTranspilerExtension.class);

		String lfrAmdLoaderVersion = GradleUtil.getProperty(
			project, "nodejs.lfr.amd.loader.version", _LFR_AMD_LOADER_VERSION);

		jsTranspilerExtension.setLfrAmdLoaderVersion(lfrAmdLoaderVersion);

		String metalCliVersion = GradleUtil.getProperty(
			project, "nodejs.metal.cli.version", _METAL_CLI_VERSION);

		jsTranspilerExtension.setMetalCliVersion(metalCliVersion);
	}

	@Override
	protected Class<JSTranspilerPlugin> getPluginClass() {
		return JSTranspilerPlugin.class;
	}

	private static final String _LFR_AMD_LOADER_VERSION = "1.3.5";

	private static final String _METAL_CLI_VERSION = "1.3.1";

}