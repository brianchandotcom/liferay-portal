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

import com.liferay.gradle.util.GradleUtil;

import com.moowork.gradle.node.NodeExtension;
import com.moowork.gradle.node.NodePlugin;
import com.moowork.gradle.node.task.NpmSetupTask;
import com.moowork.gradle.node.task.NpmTask;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Andrea Di Giorgi
 */
public class JSModuleConfigGeneratorPlugin implements Plugin<Project> {

	public static final String CONFIG_JS_MODULES_TASK_NAME = "configJSModules";

	public static final String DOWNLOAD_LFR_MODULE_CONFIG_GENERATOR_TASK_NAME =
		"downloadLfrModuleConfigGenerator";

	public static final String EXTENSION_NAME = "jsModuleConfigGenerator";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		final JSModuleConfigGeneratorExtension
			jsModuleConfigGeneratorExtension = GradleUtil.addExtension(
				project, EXTENSION_NAME,
				JSModuleConfigGeneratorExtension.class);

		final NpmTask downloadLfrModuleConfigGeneratorTask =
			addTaskDownloadModule(
				project, DOWNLOAD_LFR_MODULE_CONFIG_GENERATOR_TASK_NAME);

		final ConfigJSModulesTask configJSModulesTask = addTaskConfigJSModules(
			project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTaskDownloadModule(
						downloadLfrModuleConfigGeneratorTask,
						"lfr-module-config-generator",
						jsModuleConfigGeneratorExtension.getVersion());

					configureTaskConfigJSModules(configJSModulesTask);
				}

			});
	}

	protected ConfigJSModulesTask addTaskConfigJSModules(Project project) {
		ConfigJSModulesTask configJSModulesTask = GradleUtil.addTask(
			project, CONFIG_JS_MODULES_TASK_NAME, ConfigJSModulesTask.class);

		configJSModulesTask.setDescription(
			"Generates the config file needed to load AMD files via " +
				"combobox in Liferay.");
		configJSModulesTask.setGroup(BasePlugin.BUILD_GROUP);
		configJSModulesTask.setModuleConfigFile(project.file("bower.json"));

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(configJSModulesTask);

		return configJSModulesTask;
	}

	protected NpmTask addTaskDownloadModule(
		final Project project, String taskName) {

		NpmTask npmTask = GradleUtil.addTask(project, taskName, NpmTask.class);

		npmTask.dependsOn(NpmSetupTask.NAME);

		npmTask.setWorkingDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					NodeExtension nodeExtension = GradleUtil.getExtension(
						project, NodeExtension.class);

					return nodeExtension.getNodeModulesDir();
				}

			});

		return npmTask;
	}

	protected void configureTaskConfigJSModules(
		ConfigJSModulesTask configJSModulesTask) {

		configureTaskConfigJSModulesOutputFile(configJSModulesTask);
	}

	protected void configureTaskConfigJSModulesOutputFile(
		ConfigJSModulesTask configJSModulesTask) {

		if (configJSModulesTask.getOutputFile() != null) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			configJSModulesTask.getProject(), SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		File outputFile = new File(
			sourceSetOutput.getResourcesDir(), "META-INF/config.json");

		configJSModulesTask.setOutputFile(outputFile);
	}

	protected void configureTaskDownloadModule(
		NpmTask npmTask, String moduleName, String moduleVersion) {

		Project project = npmTask.getProject();

		NodeExtension nodeExtension = GradleUtil.getExtension(
			project, NodeExtension.class);

		List<String> args = new ArrayList<>();

		args.add("install");
		args.add(moduleName + "@" + moduleVersion);

		npmTask.setArgs(args);

		npmTask.setWorkingDir(nodeExtension.getNodeModulesDir());

		TaskInputs taskInputs = npmTask.getInputs();

		taskInputs.property("version", moduleVersion);

		TaskOutputs taskOutputs = npmTask.getOutputs();

		File moduleDir = new File(
			nodeExtension.getNodeModulesDir(), "node_modules/" + moduleName);

		taskOutputs.dir(moduleDir);
	}

}