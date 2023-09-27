/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.js.module.config.generator;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.task.DownloadNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class JSModuleConfigGeneratorPlugin implements Plugin<Project> {

	public static final String CONFIG_JS_MODULES_TASK_NAME = "configJSModules";

	public static final String
		DOWNLOAD_LIFERAY_MODULE_CONFIG_GENERATOR_TASK_NAME =
			"downloadLiferayModuleConfigGenerator";

	public static final String EXTENSION_NAME = "jsModuleConfigGenerator";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		final Task npmInstallTask = GradleUtil.getTask(
			project, NodePlugin.NPM_INSTALL_TASK_NAME);

		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			GradleUtil.addExtension(
				project, EXTENSION_NAME,
				JSModuleConfigGeneratorExtension.class);

		final DownloadNodeModuleTask downloadLiferayModuleConfigGeneratorTask =
			_addTaskDownloadLiferayModuleConfigGenerator(
				project, jsModuleConfigGeneratorExtension);

		_addTaskConfigJSModules(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTasksConfigJSModules(
						project, downloadLiferayModuleConfigGeneratorTask,
						npmInstallTask);
				}

			});
	}

	private ConfigJSModulesTask _addTaskConfigJSModules(final Project project) {
		final ConfigJSModulesTask configJSModulesTask = GradleUtil.addTask(
			project, CONFIG_JS_MODULES_TASK_NAME, ConfigJSModulesTask.class);

		configJSModulesTask.mustRunAfter(
			new Callable<Task>() {

				@Override
				public Task call() throws Exception {
					TaskContainer taskContainer = project.getTasks();

					return taskContainer.findByName(_TRANSPILE_JS_TASK_NAME);
				}

			});

		configJSModulesTask.setDescription(
			"Generates the config file needed to load AMD files via combo " +
				"loader in Liferay.");
		configJSModulesTask.setGroup(BasePlugin.BUILD_GROUP);
		configJSModulesTask.setModuleConfigFile(project.file("package.json"));

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaLibraryPlugin.class,
			new Action<JavaLibraryPlugin>() {

				@Override
				public void execute(JavaLibraryPlugin javaLibraryPlugin) {
					_configureTaskConfigJSModulesForJavaLibraryPlugin(
						configJSModulesTask);
				}

			});

		return configJSModulesTask;
	}

	private DownloadNodeModuleTask _addTaskDownloadLiferayModuleConfigGenerator(
		Project project,
		final JSModuleConfigGeneratorExtension
			jsModuleConfigGeneratorExtension) {

		DownloadNodeModuleTask downloadLiferayModuleConfigGeneratorTask =
			GradleUtil.addTask(
				project, DOWNLOAD_LIFERAY_MODULE_CONFIG_GENERATOR_TASK_NAME,
				DownloadNodeModuleTask.class);

		downloadLiferayModuleConfigGeneratorTask.setModuleName(
			"liferay-module-config-generator");

		downloadLiferayModuleConfigGeneratorTask.setModuleVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return jsModuleConfigGeneratorExtension.getVersion();
				}

			});

		return downloadLiferayModuleConfigGeneratorTask;
	}

	private void _configureTaskConfigJSModules(
		ConfigJSModulesTask configJSModulesTask,
		final DownloadNodeModuleTask downloadLiferayModuleConfigGeneratorTask,
		Task npmInstallTask) {

		File file = configJSModulesTask.getModuleConfigFile();

		if (!configJSModulesTask.isEnabled() || (file == null) ||
			!file.exists()) {

			configJSModulesTask.setDependsOn(Collections.emptySet());
			configJSModulesTask.setEnabled(false);

			return;
		}

		configJSModulesTask.dependsOn(
			downloadLiferayModuleConfigGeneratorTask, npmInstallTask);

		configJSModulesTask.setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						downloadLiferayModuleConfigGeneratorTask.getModuleDir(),
						"bin/index.js");
				}

			});
	}

	private void _configureTaskConfigJSModulesForJavaLibraryPlugin(
		ConfigJSModulesTask configJSModulesTask) {

		configJSModulesTask.mustRunAfter(
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		Project project = configJSModulesTask.getProject();

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		final SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		configJSModulesTask.setOutputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						sourceSetOutput.getResourcesDir(),
						"META-INF/config.json");
				}

			});

		configJSModulesTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						sourceSetOutput.getResourcesDir(),
						"META-INF/resources");
				}

			});

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(configJSModulesTask);
	}

	private void _configureTasksConfigJSModules(
		Project project,
		final DownloadNodeModuleTask downloadLiferayModuleConfigGeneratorTask,
		final Task npmInstallTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ConfigJSModulesTask.class,
			new Action<ConfigJSModulesTask>() {

				@Override
				public void execute(ConfigJSModulesTask configJSModulesTask) {
					_configureTaskConfigJSModules(
						configJSModulesTask,
						downloadLiferayModuleConfigGeneratorTask,
						npmInstallTask);
				}

			});
	}

	private static final String _TRANSPILE_JS_TASK_NAME = "transpileJS";

}