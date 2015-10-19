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

import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;

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

		JSModuleConfigGeneratorExtension jsModuleConfigGeneratorExtension =
			GradleUtil.addExtension(
				project, EXTENSION_NAME,
				JSModuleConfigGeneratorExtension.class);

		addTaskDownloadLfrModuleConfigGenerator(
			project, jsModuleConfigGeneratorExtension);
		addTaskConfigJSModules(project);
	}

	protected ConfigJSModulesTask addTaskConfigJSModules(
		final Project project) {

		ConfigJSModulesTask configJSModulesTask = GradleUtil.addTask(
			project, CONFIG_JS_MODULES_TASK_NAME, ConfigJSModulesTask.class);

		configJSModulesTask.setDescription(
			"Generates the config file needed to load AMD files via " +
				"combo loader in Liferay.");
		configJSModulesTask.setGroup(BasePlugin.BUILD_GROUP);
		configJSModulesTask.setModuleConfigFile(project.file("bower.json"));

		configJSModulesTask.setOutputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return new File(
						sourceSetOutput.getResourcesDir(),
						"META-INF/config.json");
				}

			});

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(configJSModulesTask);

		return configJSModulesTask;
	}

	protected DownloadNodeModuleTask addTaskDownloadLfrModuleConfigGenerator(
		Project project, final JSModuleConfigGeneratorExtension
			jsModuleConfigGeneratorExtension) {

		DownloadNodeModuleTask downloadLfrModuleConfigGeneratorTask =
			GradleUtil.addTask(
				project, DOWNLOAD_LFR_MODULE_CONFIG_GENERATOR_TASK_NAME,
				DownloadNodeModuleTask.class);

		downloadLfrModuleConfigGeneratorTask.setModuleName(
			"lfr-module-config-generator");

		downloadLfrModuleConfigGeneratorTask.setModuleVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return jsModuleConfigGeneratorExtension.getVersion();
				}

			});

		return downloadLfrModuleConfigGeneratorTask;
	}

}