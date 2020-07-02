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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.internal.util.StringUtil;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.ExecutePackageManagerTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.YarnInstallTask;

import groovy.json.JsonSlurper;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileTree;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 */
public class LiferayYarnPlugin implements Plugin<Project> {

	public static final String YARN_CHECK_FORMAT_TASK_NAME = "yarnCheckFormat";

	public static final String YARN_FORMAT_TASK_NAME = "yarnFormat";

	public static final String YARN_INSTALL_TASK_NAME = "yarnInstall";

	public static final String YARN_LOCK_TASK_NAME = "yarnLock";

	@Override
	public void apply(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, NodePlugin.class);

		GradleUtil.applyPlugin(project, NodeDefaultsPlugin.class);

		// Tasks

		Map<TaskProvider<ExecutePackageManagerTask>, File>
			yarnCheckFormatTaskProviders = new HashMap<>();
		Map<TaskProvider<ExecutePackageManagerTask>, File>
			yarnFormatTaskProviders = new HashMap<>();
		Map<TaskProvider<YarnInstallTask>, File> yarnInstallTaskProviders =
			new HashMap<>();
		Map<TaskProvider<YarnInstallTask>, File> yarnLockTaskProviders =
			new HashMap<>();

		for (File yarnLockFile : _getYarnLockFiles(project)) {
			yarnInstallTaskProviders.put(
				GradleUtil.addTaskProvider(
					project,
					_getYarnTaskName(YARN_INSTALL_TASK_NAME, yarnLockFile),
					YarnInstallTask.class),
				yarnLockFile);
			yarnLockTaskProviders.put(
				GradleUtil.addTaskProvider(
					project,
					_getYarnTaskName(YARN_LOCK_TASK_NAME, yarnLockFile),
					YarnInstallTask.class),
				yarnLockFile);

			if (_hasPackageJsonScript(
					_CHECK_FORMAT_SCRIPT_NAME, yarnLockFile)) {

				yarnCheckFormatTaskProviders.put(
					GradleUtil.addTaskProvider(
						project,
						_getYarnTaskName(
							YARN_CHECK_FORMAT_TASK_NAME, yarnLockFile),
						ExecutePackageManagerTask.class),
					yarnLockFile);
			}

			if (_hasPackageJsonScript(_FORMAT_SCRIPT_NAME, yarnLockFile)) {
				yarnFormatTaskProviders.put(
					GradleUtil.addTaskProvider(
						project,
						_getYarnTaskName(YARN_FORMAT_TASK_NAME, yarnLockFile),
						ExecutePackageManagerTask.class),
					yarnLockFile);
			}
		}

		for (Map.Entry<TaskProvider<ExecutePackageManagerTask>, File> entry :
				yarnCheckFormatTaskProviders.entrySet()) {

			_configureTaskYarnCheckFormatProvider(
				entry.getKey(), entry.getValue());
		}

		for (Map.Entry<TaskProvider<ExecutePackageManagerTask>, File> entry :
				yarnFormatTaskProviders.entrySet()) {

			_configureTaskYarnFormatProvider(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<TaskProvider<YarnInstallTask>, File> entry :
				yarnInstallTaskProviders.entrySet()) {

			_configureTaskYarnInstallProvider(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<TaskProvider<YarnInstallTask>, File> entry :
				yarnLockTaskProviders.entrySet()) {

			_configureTaskYarnLockProvider(entry.getKey(), entry.getValue());
		}

		TaskProvider<Task> yarnCheckFormatTaskProvider =
			GradleUtil.addTaskProvider(
				project, YARN_CHECK_FORMAT_TASK_NAME, Task.class);
		TaskProvider<Task> yarnFormatTaskProvider = GradleUtil.addTaskProvider(
			project, YARN_FORMAT_TASK_NAME, Task.class);
		final TaskProvider<Task> yarnInstallTaskProvider =
			GradleUtil.addTaskProvider(
				project, YARN_INSTALL_TASK_NAME, Task.class);
		TaskProvider<Task> yarnLockTaskProvider = GradleUtil.addTaskProvider(
			project, YARN_LOCK_TASK_NAME, Task.class);

		_configureTaskYarnCheckFormatProvider(
			yarnCheckFormatTaskProvider, yarnCheckFormatTaskProviders.keySet());
		_configureTaskYarnFormatProvider(
			yarnFormatTaskProvider, yarnFormatTaskProviders.keySet());
		_configureTaskYarnInstallProvider(
			yarnInstallTaskProvider, yarnInstallTaskProviders.keySet());
		_configureTaskYarnLockProvider(
			yarnLockTaskProvider, yarnLockTaskProviders.keySet());

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

	private void _configureTaskNpmInstallAfterEvaluate(
		NpmInstallTask npmInstallTask,
		TaskProvider<Task> yarnInstallTaskProvider) {

		if (!npmInstallTask.isUseNpm()) {
			npmInstallTask.finalizedBy(yarnInstallTaskProvider);
		}
	}

	private void _configureTaskYarnCheckFormatProvider(
		TaskProvider<ExecutePackageManagerTask> yarnCheckFormatTaskProvider,
		final File yarnLockFile) {

		yarnCheckFormatTaskProvider.configure(
			new Action<ExecutePackageManagerTask>() {

				@Override
				public void execute(
					ExecutePackageManagerTask
						yarnCheckFormatExecutePackageManagerTask) {

					yarnCheckFormatExecutePackageManagerTask.args(
						_CHECK_FORMAT_SCRIPT_NAME);
					yarnCheckFormatExecutePackageManagerTask.setDescription(
						"Runs the Yarn \"" + _CHECK_FORMAT_SCRIPT_NAME +
							"\" script.");
					yarnCheckFormatExecutePackageManagerTask.setWorkingDir(
						yarnLockFile.getParentFile());
				}

			});
	}

	private void _configureTaskYarnCheckFormatProvider(
		TaskProvider<Task> yarnCheckFormatTaskProvider,
		final Set<TaskProvider<ExecutePackageManagerTask>>
			yarnCheckFormatTaskProviders) {

		yarnCheckFormatTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task yarnCheckFormatTask) {
					yarnCheckFormatTask.setDescription(
						"Runs the Yarn \"" + _CHECK_FORMAT_SCRIPT_NAME +
							"\" script.");
					yarnCheckFormatTask.setGroup("formatting");

					for (TaskProvider<ExecutePackageManagerTask>
							yarnCheckFormatTaskProvider :
								yarnCheckFormatTaskProviders) {

						yarnCheckFormatTask.finalizedBy(
							yarnCheckFormatTaskProvider);
					}
				}

			});
	}

	private void _configureTaskYarnFormatProvider(
		TaskProvider<ExecutePackageManagerTask> yarnFormatTaskProvider,
		final File yarnLockFile) {

		yarnFormatTaskProvider.configure(
			new Action<ExecutePackageManagerTask>() {

				@Override
				public void execute(
					ExecutePackageManagerTask
						yarnFormatExecutePackageManagerTask) {

					yarnFormatExecutePackageManagerTask.args(
						_FORMAT_SCRIPT_NAME);
					yarnFormatExecutePackageManagerTask.setDescription(
						"Runs the Yarn \"" + _FORMAT_SCRIPT_NAME +
							"\" script.");
					yarnFormatExecutePackageManagerTask.setWorkingDir(
						yarnLockFile.getParentFile());
				}

			});
	}

	private void _configureTaskYarnFormatProvider(
		TaskProvider<Task> yarnFormatTaskProvider,
		final Set<TaskProvider<ExecutePackageManagerTask>>
			yarnFormatTaskProviders) {

		yarnFormatTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task yarnFormatTask) {
					yarnFormatTask.setDescription(
						"Runs the Yarn \"" + _FORMAT_SCRIPT_NAME +
							"\" script.");
					yarnFormatTask.setGroup("formatting");

					for (TaskProvider<ExecutePackageManagerTask>
							yarnFormatTaskProvider : yarnFormatTaskProviders) {

						yarnFormatTask.finalizedBy(yarnFormatTaskProvider);
					}
				}

			});
	}

	private void _configureTaskYarnInstallProvider(
		TaskProvider<Task> yarnInstallTaskProvider,
		final Set<TaskProvider<YarnInstallTask>> yarnInstallTaskProviders) {

		yarnInstallTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task yarnInstallTask) {
					yarnInstallTask.setDescription(
						"Installs the Node.js packages.");
					yarnInstallTask.setGroup(BasePlugin.BUILD_GROUP);

					for (TaskProvider<YarnInstallTask> yarnInstallTaskProvider :
							yarnInstallTaskProviders) {

						yarnInstallTask.finalizedBy(yarnInstallTaskProvider);
					}
				}

			});
	}

	private void _configureTaskYarnInstallProvider(
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider,
		final File yarnLockFile) {

		yarnInstallTaskProvider.configure(
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					yarnInstallTask.setDescription(
						"Installs the Node.js packages.");
					yarnInstallTask.setFrozenLockFile(true);
					yarnInstallTask.setWorkingDir(yarnLockFile.getParentFile());
				}

			});
	}

	private void _configureTaskYarnLockProvider(
		TaskProvider<Task> yarnLockTaskProvider,
		final Set<TaskProvider<YarnInstallTask>> yarnLockTaskProviders) {

		yarnLockTaskProvider.configure(
			new Action<Task>() {

				@Override
				public void execute(Task yarnLockTask) {
					yarnLockTask.setDescription(
						"Installs the Node.js packages and updates the " +
							"yarn.lock file");
					yarnLockTask.setGroup(BasePlugin.BUILD_GROUP);

					for (TaskProvider<YarnInstallTask> yarnLockTaskProvider :
							yarnLockTaskProviders) {

						yarnLockTask.finalizedBy(yarnLockTaskProvider);
					}
				}

			});
	}

	private void _configureTaskYarnLockProvider(
		TaskProvider<YarnInstallTask> yarnLockYarnInstallTaskProvider,
		final File yarnLockFile) {

		yarnLockYarnInstallTaskProvider.configure(
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnLockYarnInstallTask) {
					yarnLockYarnInstallTask.setDescription(
						"Installs the Node.js packages and updates the " +
							"yarn.lock file");
					yarnLockYarnInstallTask.setFrozenLockFile(false);
					yarnLockYarnInstallTask.setWorkingDir(
						yarnLockFile.getParentFile());
				}

			});
	}

	private List<File> _getYarnLockFiles(Project project) {
		Map<String, Object> args = new HashMap<>();

		args.put("dir", project.getProjectDir());
		args.put("excludes", _excludes);
		args.put("includes", _includes);

		FileTree yarnLockFileTree = project.fileTree(args);

		return new ArrayList<>(yarnLockFileTree.getFiles());
	}

	private String _getYarnTaskName(String parentTaskName, File yarnLockFile) {
		File workingDir = yarnLockFile.getParentFile();

		String suffix = StringUtil.camelCase(workingDir.getName(), true);

		return parentTaskName + suffix;
	}

	@SuppressWarnings("unchecked")
	private boolean _hasPackageJsonScript(
		String scriptName, File yarnLockFile) {

		File packageJsonFile = new File(
			yarnLockFile.getParentFile(), "package.json");

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

	private static final String _CHECK_FORMAT_SCRIPT_NAME = "checkFormat";

	private static final String _FORMAT_SCRIPT_NAME = "format";

	private static final List<String> _excludes = Arrays.asList(
		"**/bin/", "**/build/", "**/classes/", "**/node_modules/",
		"**/node_modules_cache/", "**/test-classes/", "**/tmp/");
	private static final List<String> _includes = Arrays.asList(
		"yarn.lock", "private/yarn.lock", "apps/*/yarn.lock",
		"private/apps/*/yarn.lock");

}