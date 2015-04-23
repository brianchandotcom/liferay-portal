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

package com.liferay.gradle.plugins.poshi.runner;

import com.liferay.gradle.plugins.poshi.runner.util.Validator;

import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerPlugin implements Plugin<Project> {

	public static final String POSHI_RUNNER_CONFIGURATION_NAME = "poshiRunner";

	public static final String POSHI_RUNNER_TASK_NAME = "runPoshi";

	@Override
	public void apply(Project project) {
		final PoshiRunnerExtension poshiRunnerExtension =
			addPoshiRunnerExtension(project);

		addPoshiRunnerConfiguration(project, poshiRunnerExtension);
		addPoshiRunnerTask(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configurePoshiRunnerTasks(project, poshiRunnerExtension);
				}

			});
	}

	protected void addPoshiRunnerConfiguration(
		final Project project,
		final PoshiRunnerExtension poshiRunnerExtension) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final Configuration configuration = configurationContainer.create(
			POSHI_RUNNER_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Poshi Runner for this project.");
		configuration.setVisible(false);

		ResolvableDependencies resolvableDependencies =
			configuration.getIncoming();

		resolvableDependencies.beforeResolve(
			new Action<ResolvableDependencies>() {

				@Override
				public void execute(
					ResolvableDependencies resolvableDependencies) {

					Set<Dependency> dependencies =
						configuration.getDependencies();

					if (dependencies.isEmpty()) {
						DependencyHandler dependencyHandler =
							project.getDependencies();

						Dependency dependency = dependencyHandler.create(
							"com.liferay:com.liferay.poshi.runner:" +
								poshiRunnerExtension.getVersion());

						dependencies.add(dependency);
					}
				}

			});
	}

	protected PoshiRunnerExtension addPoshiRunnerExtension(Project project) {
		ExtensionContainer extensionContainer = project.getExtensions();

		return extensionContainer.create(
			"poshiRunner", PoshiRunnerExtension.class, project);
	}

	protected PoshiRunnerTask addPoshiRunnerTask(Project project) {
		TaskContainer taskContainer = project.getTasks();

		PoshiRunnerTask poshiRunnerTask = taskContainer.create(
			POSHI_RUNNER_TASK_NAME, PoshiRunnerTask.class);

		poshiRunnerTask.setDescription("Execute tests using Poshi Runner.");
		poshiRunnerTask.setGroup("verification");

		return poshiRunnerTask;
	}

	protected void configurePoshiRunnerTask(
		PoshiRunnerTask poshiRunnerTask,
		PoshiRunnerExtension poshiRunnerExtension) {

		configurePoshiRunnerTaskBaseDir(poshiRunnerTask, poshiRunnerExtension);
		configurePoshiRunnerTaskPoshiProperties(
			poshiRunnerTask, poshiRunnerExtension);
		configurePoshiRunnerTaskTestName(poshiRunnerTask);
	}

	protected void configurePoshiRunnerTaskBaseDir(
		PoshiRunnerTask poshiRunnerTask,
		PoshiRunnerExtension poshiRunnerExtension) {

		if (poshiRunnerTask.getBaseDir() == null) {
			poshiRunnerTask.setBaseDir(poshiRunnerExtension.getBaseDir());
		}
	}

	protected void configurePoshiRunnerTaskPoshiProperties(
		PoshiRunnerTask poshiRunnerTask,
		PoshiRunnerExtension poshiRunnerExtension) {

		Map<String, Object> taskPoshiProperties =
			poshiRunnerTask.getPoshiProperties();

		Map<String, Object> poshiProperties =
			poshiRunnerExtension.getPoshiProperties();

		for (Map.Entry<String, Object> entry : poshiProperties.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (!taskPoshiProperties.containsKey(key)) {
				taskPoshiProperties.put(key, value);
			}
		}
	}

	protected void configurePoshiRunnerTasks(
		Project project, final PoshiRunnerExtension poshiRunnerExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PoshiRunnerTask.class,
			new Action<PoshiRunnerTask>() {

				@Override
				public void execute(PoshiRunnerTask poshiRunnerTask) {
					configurePoshiRunnerTask(
						poshiRunnerTask, poshiRunnerExtension);
				}

			});
	}

	protected void configurePoshiRunnerTaskTestName(
		PoshiRunnerTask poshiRunnerTask) {

		Project project = poshiRunnerTask.getProject();

		if (Validator.isNull(poshiRunnerTask.getTestName()) &&
			project.hasProperty("poshiTestName")) {

			poshiRunnerTask.setTestName(
				(String)project.property("poshiTestName"));
		}
	}

}