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

package com.liferay.gradle.plugins.service.builder;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class ServiceBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_SERVICE_TASK_NAME = "buildService";

	public static final String CONFIGURATION_NAME = "serviceBuilder";

	@Override
	public void apply(Project project) {
		Configuration serviceBuilderConfiguration =
			addServiceBuilderConfiguration(project);

		addBuildServiceTask(project);

		configureTasksBuildService(project, serviceBuilderConfiguration);
	}

	protected BuildServiceTask addBuildServiceTask(Project project) {
		BuildServiceTask buildServiceTask = GradleUtil.addTask(
			project, BUILD_SERVICE_TASK_NAME, BuildServiceTask.class);

		buildServiceTask.setDescription("Runs Liferay Service Builder.");
		buildServiceTask.setGroup(BasePlugin.BUILD_GROUP);

		return buildServiceTask;
	}

	protected Configuration addServiceBuilderConfiguration(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Service Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addServiceBuilderDependencies(project);
				}

			});

		return configuration;
	}

	protected void addServiceBuilderDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.service.builder", "latest.release");
	}

	protected void configureTaskBuildServiceClasspath(
		BuildServiceTask buildServiceTask,
		Configuration serviceBuilderConfiguration) {

		buildServiceTask.setClasspath(serviceBuilderConfiguration);
	}

	protected void configureTasksBuildService(
		Project project, final Configuration serviceBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildServiceTask.class,
			new Action<BuildServiceTask>() {

				@Override
				public void execute(BuildServiceTask buildServiceTask) {
					configureTaskBuildServiceClasspath(
						buildServiceTask, serviceBuilderConfiguration);
				}

			});
	}

}