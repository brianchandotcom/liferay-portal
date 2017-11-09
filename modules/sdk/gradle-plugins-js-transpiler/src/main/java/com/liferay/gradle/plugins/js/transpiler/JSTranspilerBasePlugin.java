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

package com.liferay.gradle.plugins.js.transpiler;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskDependency;

/**
 * @author Andrea Di Giorgi
 */
public class JSTranspilerBasePlugin implements Plugin<Project> {

	public static final String EXPAND_JS_COMPILE_DEPENDENCIES_TASK_NAME =
		"expandJSCompileDependencies";

	public static final String JS_COMPILE_CONFIGURATION_NAME = "jsCompile";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		final NpmInstallTask npmInstallTask =
			(NpmInstallTask)GradleUtil.getTask(
				project, NodePlugin.NPM_INSTALL_TASK_NAME);

		final Configuration jsCompileConfiguration = _addConfigurationJSCompile(
			project);

		final Task expandJSCompileDependenciesTask =
			_addTaskExpandJSCompileDependencies(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_addTasksExpandJSCompileDependency(
						expandJSCompileDependenciesTask, npmInstallTask,
						jsCompileConfiguration);
				}

			});
	}

	private Configuration _addConfigurationJSCompile(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, JS_COMPILE_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures additional JavaScript dependencies.");
		configuration.setVisible(false);

		return configuration;
	}

	private Copy _addTaskExpandCompileDependency(
		Project project, File file, File destinationDir, String taskNamePrefix,
		RenameDependencyClosure renameDependencyClosure) {

		String taskName = GradleUtil.getTaskName(taskNamePrefix, file);

		Copy copy = GradleUtil.addTask(project, taskName, Copy.class);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					Project project = copy.getProject();

					project.delete(copy.getDestinationDir());
				}

			});

		copy.from(project.zipTree(file));

		String name = renameDependencyClosure.call(file.getName());

		name = name.substring(0, name.length() - 4);

		destinationDir = new File(destinationDir, name);

		copy.setDescription(
			"Expands " + file.getName() + " into " +
				project.relativePath(destinationDir) + ".");
		copy.setDestinationDir(destinationDir);

		return copy;
	}

	private Task _addTaskExpandJSCompileDependencies(Project project) {
		Task task = project.task(EXPAND_JS_COMPILE_DEPENDENCIES_TASK_NAME);

		task.setDescription(
			"Expands the configured additional Javascript dependencies.");

		return task;
	}

	private void _addTasksExpandJSCompileDependency(
		Task expandJSCompileDependenciesTask, NpmInstallTask npmInstallTask,
		Configuration configuration) {

		Project project = expandJSCompileDependenciesTask.getProject();

		RenameDependencyClosure renameDependencyClosure =
			new RenameDependencyClosure(project, configuration.getName());

		Iterable<TaskDependency> taskDependencies = _getTaskDependencies(
			configuration);

		for (File file : configuration) {
			Copy copy = _addTaskExpandCompileDependency(
				project, file, npmInstallTask.getNodeModulesDir(),
				"expandJSCompileDependency", renameDependencyClosure);

			copy.dependsOn(taskDependencies);
			copy.mustRunAfter(npmInstallTask);

			expandJSCompileDependenciesTask.dependsOn(copy);
		}
	}

	private Iterable<TaskDependency> _getTaskDependencies(
		Configuration configuration) {

		Set<TaskDependency> taskDependencies = new HashSet<>();

		DependencySet dependencySet = configuration.getAllDependencies();

		for (ProjectDependency projectDependency : dependencySet.withType(
				ProjectDependency.class)) {

			taskDependencies.add(projectDependency.getBuildDependencies());
		}

		return taskDependencies;
	}

}