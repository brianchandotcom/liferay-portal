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

package com.liferay.gradle.plugins.workspace.internal;

import com.liferay.gradle.plugins.js.module.config.generator.ConfigJSModulesTask;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorExtension;
import com.liferay.gradle.plugins.js.module.config.generator.JSModuleConfigGeneratorPlugin;
import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class JSModuleConfigGeneratorDefaultsPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new JSModuleConfigGeneratorDefaultsPlugin();

	@Override
	public void apply(final Project project) {

		// Containers

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JSModuleConfigGeneratorPlugin.class,
			new Action<JSModuleConfigGeneratorPlugin>() {

				@Override
				public void execute(
					JSModuleConfigGeneratorPlugin
						jsModuleConfigGeneratorPlugin) {

					_applyPluginDefaults(project);
				}

			});
	}

	private JSModuleConfigGeneratorDefaultsPlugin() {
	}

	private void _applyPluginDefaults(Project project) {

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

	private void _configureExtensionJSModuleConfigGenerator(
		Project project,
		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension) {

		jsModuleConfigGeneratorExtension.setVersion(
			GradleUtil.getProperty(
				project, "nodejs.liferay.module.config.generator.version",
				_VERSION));
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