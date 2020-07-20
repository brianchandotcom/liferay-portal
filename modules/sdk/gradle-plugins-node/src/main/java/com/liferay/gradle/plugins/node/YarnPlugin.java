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

package com.liferay.gradle.plugins.node;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.tasks.ExecutePackageManagerTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.YarnInstallTask;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.Map;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 * @author David Truong
 */
public class YarnPlugin implements Plugin<Project> {

	public static final String YARN_CHECK_FORMAT_TASK_NAME = "yarnCheckFormat";

	public static final String YARN_FORMAT_TASK_NAME = "yarnFormat";

	public static final String YARN_INSTALL_TASK_NAME = "yarnInstall";

	@Override
	public void apply(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, NodePlugin.class);

		// Tasks

		TaskProvider<YarnInstallTask> yarnInstallTaskProvider =
			GradleUtil.addTaskProvider(
				project, YARN_INSTALL_TASK_NAME, YarnInstallTask.class);

		_configureTaskYarnInstallProvider(project, yarnInstallTaskProvider);

		if (_hasPackageJsonScript(project, "checkFormat")) {
			TaskProvider<ExecutePackageManagerTask>
				yarnCheckFormatTaskProvider = GradleUtil.addTaskProvider(
					project, YARN_CHECK_FORMAT_TASK_NAME,
					ExecutePackageManagerTask.class);

			_configureTaskExecutePackageManagerProvider(
				project, yarnCheckFormatTaskProvider, "checkFormat");
		}

		if (_hasPackageJsonScript(project, "format")) {
			TaskProvider<ExecutePackageManagerTask> yarnFormatTaskProvider =
				GradleUtil.addTaskProvider(
					project, YARN_FORMAT_TASK_NAME,
					ExecutePackageManagerTask.class);

			_configureTaskExecutePackageManagerProvider(
				project, yarnFormatTaskProvider, "format");
		}

		// Other

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		if (startParameter.isParallelProjectExecutionEnabled()) {
			return;
		}

		for (Project subproject : project.getSubprojects()) {
			subproject.afterEvaluate(
				new Action<Project>() {

					@Override
					public void execute(Project project) {
						TaskContainer taskContainer = project.getTasks();

						taskContainer.withType(
							NpmInstallTask.class,
							new Action<NpmInstallTask>() {

								@Override
								public void execute(
									NpmInstallTask npmInstallTask) {

									_configureTaskNpmInstallAfterEvaluate(
										npmInstallTask,
										yarnInstallTaskProvider);
								}

							});
					}

				});
		}
	}

	private void _configureTaskExecutePackageManagerProvider(
		final Project project,
		TaskProvider<ExecutePackageManagerTask>
			executePackageManagerTaskProvider,
		final String scriptName) {

		executePackageManagerTaskProvider.configure(
			new Action<ExecutePackageManagerTask>() {

				@Override
				public void execute(
					ExecutePackageManagerTask executePackageManagerTask) {

					executePackageManagerTask.args(scriptName);
					executePackageManagerTask.setDescription(
						"Runs the Yarn \"" + scriptName + "\" script.");
					executePackageManagerTask.setWorkingDir(
						project.getProjectDir());
				}

			});
	}

	private void _configureTaskNpmInstallAfterEvaluate(
		NpmInstallTask npmInstallTask,
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider) {

		if (!npmInstallTask.isUseNpm()) {
			npmInstallTask.finalizedBy(yarnInstallTaskProvider);
		}
	}

	private void _configureTaskYarnInstallProvider(
		final Project project,
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider) {

		yarnInstallTaskProvider.configure(
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					boolean frozenLockfile = Boolean.parseBoolean(
						System.getProperty(
							"frozen.lockfile", Boolean.TRUE.toString()));

					yarnInstallTask.setDescription(
						"Installs the Node.js packages.");
					yarnInstallTask.setFrozenLockFile(frozenLockfile);
					yarnInstallTask.setWorkingDir(project.getProjectDir());
				}

			});
	}

	@SuppressWarnings("unchecked")
	private boolean _hasPackageJsonScript(Project project, String scriptName) {
		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return false;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> packageJsonMap =
			(Map<String, Object>)jsonSlurper.parse(packageJsonFile);

		Map<String, String> scriptsJsonMap =
			(Map<String, String>)packageJsonMap.get("scripts");

		if (scriptsJsonMap == null) {
			return false;
		}

		for (String curScriptName : scriptsJsonMap.keySet()) {
			if (curScriptName.equals(scriptName)) {
				return true;
			}
		}

		return false;
	}

}