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

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.js.module.config.generator.ConfigJSModulesTask;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorExtension;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JSModuleConfigGeneratorDefaultsPlugin
	extends BaseDefaultsPlugin<JSModuleConfigGeneratorPlugin> {

	public static final Plugin<Project> INSTANCE =
		new JSModuleConfigGeneratorDefaultsPlugin();

	@Override
	protected void configureDefaults(
		Project project,
		JSModuleConfigGeneratorPlugin jsModuleConfigGeneratorPlugin) {

		_configureJSModuleConfigGenerator(project);
		_configureTaskConfigJSModules(project);
	}

	@Override
	protected Class<JSModuleConfigGeneratorPlugin> getPluginClass() {
		return JSModuleConfigGeneratorPlugin.class;
	}

	private JSModuleConfigGeneratorDefaultsPlugin() {
	}

	private void _configureJSModuleConfigGenerator(final Project project) {
		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			GradleUtil.getExtension(
				project, JSModuleConfigGeneratorExtension.class);

		String version = GradleUtil.getProperty(
			project, "nodejs.liferay.module.config.generator.version",
			_VERSION);

		jsModuleConfigGeneratorExtension.setVersion(version);
	}

	private void _configureTaskConfigJSModules(Project project) {
		ConfigJSModulesTask configJSModulesTask =
			(ConfigJSModulesTask)GradleUtil.getTask(
				project,
				JSModuleConfigGeneratorPlugin.CONFIG_JS_MODULES_TASK_NAME);

		configJSModulesTask.setConfigVariable("");
		configJSModulesTask.setIgnorePath(true);
		configJSModulesTask.setModuleExtension("");
		configJSModulesTask.setModuleFormat("/_/g,-");
	}

	private static final String _VERSION = "1.1.10";

}