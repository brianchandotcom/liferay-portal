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

package com.liferay.gradle.plugins.wsdd.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_WSDD_TASK_NAME = "buildWSDD";

	public static final String CONFIGURATION_NAME = "wsddBuilder";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		Configuration wsddBuilderConfiguration = _addConfigurationWSDDBuilder(
			project);

		_addTaskBuildWSDD(project);

		_configureTasksBuildWSDD(project, wsddBuilderConfiguration);
	}

	private Configuration _addConfigurationWSDDBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesWSDDBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay WSDD Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesWSDDBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.wsdd.builder", "latest.release");
	}

	private BuildWSDDTask _addTaskBuildWSDD(Project project) {
		final BuildWSDDTask buildWSDDTask = GradleUtil.addTask(
			project, BUILD_WSDD_TASK_NAME, BuildWSDDTask.class);

		buildWSDDTask.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME);

		buildWSDDTask.setBuilderClasspath(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					Project project = buildWSDDTask.getProject();

					Task compileJavaTask = GradleUtil.getTask(
						project, JavaPlugin.COMPILE_JAVA_TASK_NAME);

					TaskOutputs taskOutputs = compileJavaTask.getOutputs();

					FileCollection fileCollection = taskOutputs.getFiles();

					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					fileCollection = fileCollection.plus(
						sourceSet.getCompileClasspath());
					fileCollection = fileCollection.plus(
						sourceSet.getRuntimeClasspath());

					return fileCollection.getAsPath();
				}

			});

		buildWSDDTask.setDescription("Runs Liferay WSDD Builder.");
		buildWSDDTask.setGroup(BasePlugin.BUILD_GROUP);
		buildWSDDTask.setInputFile("service.xml");

		buildWSDDTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return GradleUtil.getMainResourcesDir(
						buildWSDDTask.getProject());
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					_configureTaskBuildWSDDForWarPlugin(buildWSDDTask);
				}

			});

		return buildWSDDTask;
	}

	private void _configureTaskBuildWSDDClasspath(
		BuildWSDDTask buildWSDDTask, FileCollection fileCollection) {

		buildWSDDTask.setClasspath(fileCollection);
	}

	private void _configureTaskBuildWSDDForWarPlugin(
		final BuildWSDDTask buildWSDDTask) {

		buildWSDDTask.setInputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildWSDDTask.getProject());

					return new File(webAppDir, "WEB-INF/service.xml");
				}

			});

		buildWSDDTask.setServerConfigFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildWSDDTask.getProject());

					return new File(webAppDir, "WEB-INF/server-config.wsdd");
				}

			});
	}

	private void _configureTasksBuildWSDD(
		Project project, final Configuration wsddBuilderCOnfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildWSDDTask.class,
			new Action<BuildWSDDTask>() {

				@Override
				public void execute(BuildWSDDTask buildWSDDTask) {
					_configureTaskBuildWSDDClasspath(
						buildWSDDTask, wsddBuilderCOnfiguration);
				}

			});
	}

}