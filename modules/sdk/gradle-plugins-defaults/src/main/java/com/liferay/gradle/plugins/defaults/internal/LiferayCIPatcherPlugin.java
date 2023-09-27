/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.node.task.NpmInstallTask;
import com.liferay.gradle.plugins.node.task.PackageRunBuildTask;

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