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
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
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
		addWSDDBuilderConfiguration(project);

		addBuildWSDDTask(project);
	}

	protected BuildWSDDTask addBuildWSDDTask(Project project) {
		BuildWSDDTask buildWSDDTask = GradleUtil.addTask(
			project, BUILD_WSDD_TASK_NAME, BuildWSDDTask.class);

		buildWSDDTask.setDescription("Runs Liferay WSDD Builder.");
		buildWSDDTask.setGroup(BasePlugin.BUILD_GROUP);

		buildWSDDTask.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME);

		buildWSDDTask.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					configureBuildWSDDTaskBuilderClasspath((BuildWSDDTask)task);
				}

			});

		return buildWSDDTask;
	}

	protected Configuration addWSDDBuilderConfiguration(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay WSDD Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addWSDDBuilderDependencies(project);
				}

			});

		return configuration;
	}

	protected void addWSDDBuilderDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.wsdd.builder", "latest.release");
	}

	protected void configureBuildWSDDTaskBuilderClasspath(
		BuildWSDDTask buildWSDDTask) {

		if (Validator.isNotNull(buildWSDDTask.getBuilderClasspath())) {
			return;
		}

		Project project = buildWSDDTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		Task compileJavaTask = taskContainer.findByName(
			JavaPlugin.COMPILE_JAVA_TASK_NAME);

		TaskOutputs taskOutputs = compileJavaTask.getOutputs();

		FileCollection fileCollection = taskOutputs.getFiles();

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		fileCollection = fileCollection.plus(sourceSet.getCompileClasspath());
		fileCollection = fileCollection.plus(sourceSet.getRuntimeClasspath());

		buildWSDDTask.setBuilderClasspath(fileCollection.getAsPath());
	}

}