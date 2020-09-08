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

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class JSModuleConfigGeneratorDefaultsPlugin
	extends BaseDefaultsPlugin<JSModuleConfigGeneratorPlugin> {

	public static final Plugin<Project> INSTANCE =
		new JSModuleConfigGeneratorDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project,
		JSModuleConfigGeneratorPlugin jsModuleConfigGeneratorPlugin) {

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			extensionContainer.getByType(
				JSModuleConfigGeneratorExtension.class);

		_configureExtensionJSModuleConfigGenerator(
			project, jsModuleConfigGeneratorExtension);

		// Tasks

		TaskProvider<ConfigJSModulesTask> configJSModulesTaskProvider =
			GradleUtil.getTaskProvider(
				project,
				JSModuleConfigGeneratorPlugin.CONFIG_JS_MODULES_TASK_NAME,
				ConfigJSModulesTask.class);

		_configureTaskConfigJSModulesProvider(configJSModulesTaskProvider);
	}

	@Override
	protected Class<JSModuleConfigGeneratorPlugin> getPluginClass() {
		return JSModuleConfigGeneratorPlugin.class;
	}

	private JSModuleConfigGeneratorDefaultsPlugin() {
	}

	private void _configureExtensionJSModuleConfigGenerator(
		Project project,
		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension) {

		String version = GradleUtil.getProperty(
			project, "nodejs.liferay.module.config.generator.version",
			_VERSION);

		jsModuleConfigGeneratorExtension.setVersion(version);
	}

	private void _configureTaskConfigJSModulesProvider(
		TaskProvider<ConfigJSModulesTask> configJSModulesTaskProvider) {

		configJSModulesTaskProvider.configure(
			new Action<ConfigJSModulesTask>() {

				@Override
				public void execute(ConfigJSModulesTask configJSModulesTask) {
					configJSModulesTask.setConfigVariable("");
					configJSModulesTask.setIgnorePath(true);
					configJSModulesTask.setModuleExtension("");
					configJSModulesTask.setModuleFormat("/_/g,-");
				}

			});
	}

	private static final String _VERSION = "1.3.3";

}