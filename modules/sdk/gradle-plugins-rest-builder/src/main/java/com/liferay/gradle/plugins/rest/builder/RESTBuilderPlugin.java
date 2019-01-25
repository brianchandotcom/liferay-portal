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

package com.liferay.gradle.plugins.rest.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class RESTBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_REST_TASK_NAME = "buildREST";

	public static final String CONFIGURATION_NAME = "restBuilder";

	@Override
	public void apply(Project project) {
		Configuration restBuilderConfiguration = _addConfigurationRESTBuilder(
			project);

		_addTaskBuildREST(project);

		_configureTasksBuildREST(project, restBuilderConfiguration);
	}

	private Configuration _addConfigurationRESTBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesRESTBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay REST Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesRESTBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.rest.builder", "latest.release");
	}

	private BuildRESTTask _addTaskBuildREST(Project project) {
		BuildRESTTask buildRESTTask = GradleUtil.addTask(
			project, BUILD_REST_TASK_NAME, BuildRESTTask.class);

		buildRESTTask.setDescription("Runs Liferay REST Builder.");
		buildRESTTask.setGroup(BasePlugin.BUILD_GROUP);

		File restConfigFile = new File(
			project.getProjectDir(), "rest-config.yaml");

		if (restConfigFile.exists()) {
			buildRESTTask.setRESTConfigFile("rest-config.yaml");
		}

		buildRESTTask.setRESTOpenAPIFile("rest-openapi.yaml");

		return buildRESTTask;
	}

	private void _configureTaskBuildRESTClasspath(
		BuildRESTTask buildRESTTask, Configuration restBuilderConfiguration) {

		buildRESTTask.setClasspath(restBuilderConfiguration);
	}

	private void _configureTasksBuildREST(
		Project project, final Configuration restBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildRESTTask.class,
			new Action<BuildRESTTask>() {

				@Override
				public void execute(BuildRESTTask buildRESTTask) {
					_configureTaskBuildRESTClasspath(
						buildRESTTask, restBuilderConfiguration);
				}

			});
	}

}