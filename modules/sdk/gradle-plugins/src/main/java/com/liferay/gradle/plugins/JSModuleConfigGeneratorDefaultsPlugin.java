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

import com.liferay.gradle.plugins.js.module.config.generator.ConfigJSModulesTask;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorExtension;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JSModuleConfigGeneratorDefaultsPlugin
	extends BaseDefaultsPlugin<JSModuleConfigGeneratorPlugin> {

	@Override
	protected void configureDefaults(
		Project project,
		JSModuleConfigGeneratorPlugin jsModuleConfigGeneratorPlugin) {

		configureJSModuleConfigGenerator(project);
		configureTaskConfigJSModules(project);
	}

	protected void configureJSModuleConfigGenerator(final Project project) {
		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			GradleUtil.getExtension(
				project, JSModuleConfigGeneratorExtension.class);

		String version = GradleUtil.getProperty(
			project, "nodejs.lfr.module.config.generator.version", _VERSION);

		jsModuleConfigGeneratorExtension.setVersion(version);
	}

	protected void configureTaskConfigJSModules(Project project) {
		ConfigJSModulesTask configJSModulesTask =
			(ConfigJSModulesTask)GradleUtil.getTask(
				project,
				JSModuleConfigGeneratorPlugin.CONFIG_JS_MODULES_TASK_NAME);

		configJSModulesTask.setConfigVariable("");
		configJSModulesTask.setIgnorePath(true);
		configJSModulesTask.setModuleExtension("");
		configJSModulesTask.setModuleFormat("/_/g,-");
	}

	@Override
	protected Class<JSModuleConfigGeneratorPlugin> getPluginClass() {
		return JSModuleConfigGeneratorPlugin.class;
	}

	private static final String _VERSION = "1.1.7";

}