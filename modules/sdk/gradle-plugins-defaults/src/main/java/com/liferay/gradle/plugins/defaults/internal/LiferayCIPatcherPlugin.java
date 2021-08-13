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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PackageRunBuildTask;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Peter Shin
 */
public class LiferayCIPatcherPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE = new LiferayCIPatcherPlugin();

	@Override
	public void apply(Project project) {
		_configureTasksPackageRunBuild(project);
		_configureTasksNpmInstall(project);
	}

	private LiferayCIPatcherPlugin() {
	}

	private void _configureTaskNpmInstall(NpmInstallTask npmInstallTask) {
		npmInstallTask.setNodeModulesCacheDir(null);
	}

	private void _configureTaskPackageRunBuild(
		PackageRunBuildTask packageRunBuildTask) {

		TaskOutputs taskOutputs = packageRunBuildTask.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});
	}

	private void _configureTasksNpmInstall(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskNpmInstall(npmInstallTask);
				}

			});
	}

	private void _configureTasksPackageRunBuild(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PackageRunBuildTask.class,
			new Action<PackageRunBuildTask>() {

				@Override
				public void execute(PackageRunBuildTask packageRunBuildTask) {
					_configureTaskPackageRunBuild(packageRunBuildTask);
				}

			});
	}

}