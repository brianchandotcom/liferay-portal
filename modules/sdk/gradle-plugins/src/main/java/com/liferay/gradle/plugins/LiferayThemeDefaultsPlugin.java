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

import com.liferay.gradle.plugins.util.GradleUtil;

import org.gradle.api.Project;
import org.gradle.api.plugins.MavenPlugin;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemeDefaultsPlugin
	extends BaseDefaultsPlugin<LiferayThemePlugin> {

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-maven.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, MavenPlugin.class);
	}

	@Override
	protected void configureDefaults(
		Project project, LiferayThemePlugin liferayThemePlugin) {

		applyPlugins(project);

		applyConfigScripts(project);
	}

	@Override
	protected Class<LiferayThemePlugin> getPluginClass() {
		return LiferayThemePlugin.class;
	}

}