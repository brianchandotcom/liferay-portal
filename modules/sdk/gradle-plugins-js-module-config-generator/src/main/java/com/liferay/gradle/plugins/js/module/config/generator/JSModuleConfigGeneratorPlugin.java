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

package com.liferay.gradle.plugins.js.module.config.generator;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JSModuleConfigGeneratorPlugin implements Plugin<Project> {

	public static final String CONFIG_JS_MODULES_TASK_NAME = "configJSModules";

	public static final String
		DOWNLOAD_LIFERAY_MODULE_CONFIG_GENERATOR_TASK_NAME =
			"downloadLiferayModuleConfigGenerator";

	public static final String EXTENSION_NAME = "jsModuleConfigGenerator";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			GradleUtil.addExtension(
				project, EXTENSION_NAME,
				JSModuleConfigGeneratorExtension.class);

		addTaskDownloadLiferayModuleConfigGenerator(
			project, jsModuleConfigGeneratorExtension);
		addTaskConfigJSModules(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksConfigJSModules(project);
				}

			});
	}

	protected ConfigJSModulesTask addTaskConfigJSModules(
		final Project project) {

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
			"Generates the config file needed to load AMD files via " +
				"combo loader in Liferay.");
		configJSModulesTask.setGroup(BasePlugin.BUILD_GROUP);
		configJSModulesTask.setModuleConfigFile(project.file("package.json"));

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskConfigJSModulesForJavaPlugin(
						configJSModulesTask);
				}

			});

		return configJSModulesTask;
	}

	protected DownloadNodeModuleTask
		addTaskDownloadLiferayModuleConfigGenerator(
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

	protected void configureTaskConfigJSModulesEnabled(
		ConfigJSModulesTask configJSModulesTask) {

		File file = configJSModulesTask.getModuleConfigFile();

		if ((file == null) || !file.exists()) {
			configJSModulesTask.setDependsOn(Collections.emptySet());
			configJSModulesTask.setEnabled(false);
		}
	}

	protected void configureTaskConfigJSModulesForJavaPlugin(
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

	protected void configureTasksConfigJSModules(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ConfigJSModulesTask.class,
			new Action<ConfigJSModulesTask>() {

				@Override
				public void execute(ConfigJSModulesTask configJSModulesTask) {
					configureTaskConfigJSModulesEnabled(configJSModulesTask);
				}

			});
	}

	private static final String _TRANSPILE_JS_TASK_NAME = "transpileJS";

}