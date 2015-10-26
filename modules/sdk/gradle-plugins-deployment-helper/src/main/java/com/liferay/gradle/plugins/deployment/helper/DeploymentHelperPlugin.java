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

package com.liferay.gradle.plugins.deployment.helper;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class DeploymentHelperPlugin implements Plugin<Project> {

	public static final String BUILD_DEPLOYMENT_HELPER_TASK_NAME =
		"buildDeploymentHelper";

	public static final String CONFIGURATION_NAME = "deploymentHelper";

	@Override
	public void apply(Project project) {
		addConfigurationDeploymentHelper(project);
		addTaskBuildDeploymentHelper(project);

		configureTasksBuildDeploymentHelper(project);
	}

	protected Configuration addConfigurationDeploymentHelper(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Deployment Helper for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesDeploymentHelper(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesDeploymentHelper(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.deployment.helper", "latest.release");
	}

	protected BuildDeploymentHelperTask addTaskBuildDeploymentHelper(
		final Project project) {

		BuildDeploymentHelperTask buildDeploymentHelperTask =
			GradleUtil.addTask(
				project, BUILD_DEPLOYMENT_HELPER_TASK_NAME,
				BuildDeploymentHelperTask.class);

		buildDeploymentHelperTask.setDeploymentFiles(
			new Callable<List<Jar>>() {

				@Override
				public List<Jar> call() throws Exception {
					List<Jar> jarTasks = new ArrayList<>();

					for (Project curProject : project.getAllprojects()) {
						TaskContainer taskContainer = curProject.getTasks();

						Task task = taskContainer.findByName(
							JavaPlugin.JAR_TASK_NAME);

						if (task instanceof Jar) {
							jarTasks.add((Jar)task);
						}
					}

					return jarTasks;
				}

			});

		buildDeploymentHelperTask.setDescription(
			"Assembles a war file to deploy one or more apps to a cluster.");

		buildDeploymentHelperTask.setOutputFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						project.getBuildDir(), project.getName() + ".war");
				}

			});

		return buildDeploymentHelperTask;
	}

	protected void configureTaskBuildDeploymentHelperClasspath(
		BuildDeploymentHelperTask buildDeploymentHelperTask) {

		FileCollection fileCollection =
			buildDeploymentHelperTask.getClasspath();

		if ((fileCollection != null) && !fileCollection.isEmpty()) {
			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			buildDeploymentHelperTask.getProject(), CONFIGURATION_NAME);

		buildDeploymentHelperTask.setClasspath(configuration);
	}

	protected void configureTasksBuildDeploymentHelper(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildDeploymentHelperTask.class,
			new Action<BuildDeploymentHelperTask>() {

				@Override
				public void execute(
					BuildDeploymentHelperTask buildDeploymentHelperTask) {

					configureTaskBuildDeploymentHelperClasspath(
						buildDeploymentHelperTask);
				}

			});
	}

}