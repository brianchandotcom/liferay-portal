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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileTree;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;

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
		GradleUtil.applyPlugin(project, NodePlugin.class);

		GradleUtil.applyPlugin(project, NodeDefaultsPlugin.class);

		Task yarnInstallTask = _addTaskYarnInstall(project);

		_addTaskYarnCheckFormat(project);
		_addTaskYarnFormat(project);
		_addTaskYarnLock(project);

		Gradle gradle = project.getGradle();

		StartParameter startParameter = gradle.getStartParameter();

		if (!startParameter.isParallelProjectExecutionEnabled()) {
			for (final Project subproject : project.getSubprojects()) {
				subproject.afterEvaluate(
					new Action<Project>() {

						@Override
						public void execute(Project project) {
							_configureTasksNpmInstall(
								subproject, yarnInstallTask);
						}

					});
			}
		}
	}

	private ExecutePackageManagerTask _addTaskYarnCheckFormat(
		File yarnLockFile, Project project) {

		File workingDir = yarnLockFile.getParentFile();

		String suffix = StringUtil.camelCase(workingDir.getName(), true);

		ExecutePackageManagerTask executePackageManagerTask =
			GradleUtil.addTask(
				project, YARN_CHECK_FORMAT_TASK_NAME + suffix,
				ExecutePackageManagerTask.class);

		executePackageManagerTask.args(_CHECK_FORMAT_SCRIPT_NAME);
		executePackageManagerTask.setDescription(
			"Runs the Yarn \"" + _CHECK_FORMAT_SCRIPT_NAME + "\" script.");
		executePackageManagerTask.setWorkingDir(workingDir);

		return executePackageManagerTask;
	}

	private Task _addTaskYarnCheckFormat(Project project) {
		Task task = project.task(YARN_CHECK_FORMAT_TASK_NAME);

		task.setDescription(
			"Runs the Yarn \"" + _CHECK_FORMAT_SCRIPT_NAME + "\" script.");
		task.setGroup("formatting");

		task.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Logger logger = project.getLogger();

					if (logger.isLifecycleEnabled()) {
						StringBuilder sb = new StringBuilder();

						sb.append("Running the Yarn \"");
						sb.append(_CHECK_FORMAT_SCRIPT_NAME);
						sb.append("\" script");

						logger.lifecycle(sb.toString());
					}
				}

			});

		FileTree yarnLockFiles = _getYarnLockFiles(project);

		yarnLockFiles.forEach(
			yarnLockFile -> {
				File packageJsonFile = new File(
					yarnLockFile.getParentFile(), "package.json");

				if (_hasPackageJsonScript(
						_CHECK_FORMAT_SCRIPT_NAME, packageJsonFile)) {

					task.finalizedBy(
						_addTaskYarnCheckFormat(yarnLockFile, project));
				}
			});

		return task;
	}

	private ExecutePackageManagerTask _addTaskYarnFormat(
		File yarnLockFile, Project project) {

		File workingDir = yarnLockFile.getParentFile();

		String suffix = StringUtil.camelCase(workingDir.getName(), true);

		ExecutePackageManagerTask executePackageManagerTask =
			GradleUtil.addTask(
				project, YARN_FORMAT_TASK_NAME + suffix,
				ExecutePackageManagerTask.class);

		executePackageManagerTask.args(_FORMAT_SCRIPT_NAME);
		executePackageManagerTask.setDescription(
			"Runs the Yarn \"" + _FORMAT_SCRIPT_NAME + "\" script.");
		executePackageManagerTask.setWorkingDir(workingDir);

		return executePackageManagerTask;
	}

	private Task _addTaskYarnFormat(Project project) {
		Task task = project.task(YARN_FORMAT_TASK_NAME);

		task.setDescription(
			"Runs the Yarn \"" + _FORMAT_SCRIPT_NAME + "\" script.");
		task.setGroup("formatting");

		task.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Logger logger = project.getLogger();

					if (logger.isLifecycleEnabled()) {
						StringBuilder sb = new StringBuilder();

						sb.append("Running the Yarn \"");
						sb.append(_FORMAT_SCRIPT_NAME);
						sb.append("\" script");

						logger.lifecycle(sb.toString());
					}
				}

			});

		FileTree yarnLockFiles = _getYarnLockFiles(project);

		yarnLockFiles.forEach(
			yarnLockFile -> {
				File packageJsonFile = new File(
					yarnLockFile.getParentFile(), "package.json");

				if (_hasPackageJsonScript(
						_FORMAT_SCRIPT_NAME, packageJsonFile)) {

					task.finalizedBy(_addTaskYarnFormat(yarnLockFile, project));
				}
			});

		return task;
	}

	private Task _addTaskYarnInstall(Project project) {
		final Task task = project.task(YARN_INSTALL_TASK_NAME);

		task.setDescription("Installs the Node.js packages.");
		task.setGroup(BasePlugin.BUILD_GROUP);

		task.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Logger logger = project.getLogger();

					if (logger.isLifecycleEnabled()) {
						logger.lifecycle("Installing the Node.js packages");
					}
				}

			});

		FileTree yarnLockFiles = _getYarnLockFiles(project);

		yarnLockFiles.forEach(
			yarnLockFile -> task.finalizedBy(
				_addTaskYarnInstall(task, yarnLockFile, true)));

		return task;
	}

	private YarnInstallTask _addTaskYarnInstall(
		Task task, File yarnLockFile, boolean frozenLockFile) {

		File workingDir = yarnLockFile.getParentFile();

		String suffix = StringUtil.camelCase(workingDir.getName(), true);

		YarnInstallTask yarnInstallTask = GradleUtil.addTask(
			task.getProject(), task.getName() + suffix, YarnInstallTask.class);

		yarnInstallTask.setDescription("Installs the Node.js packages.");
		yarnInstallTask.setFrozenLockFile(frozenLockFile);
		yarnInstallTask.setWorkingDir(workingDir);

		return yarnInstallTask;
	}

	private Task _addTaskYarnLock(Project project) {
		final Task task = project.task(YARN_LOCK_TASK_NAME);

		task.setDescription(
			"Installs the Node.js packages and updates the yarn.lock file");
		task.setGroup(BasePlugin.BUILD_GROUP);

		task.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Logger logger = project.getLogger();

					if (logger.isLifecycleEnabled()) {
						logger.lifecycle("Updating the yarn.lock file");
					}
				}

			});

		FileTree yarnLockFiles = _getYarnLockFiles(project);

		yarnLockFiles.forEach(
			yarnLockFile -> task.finalizedBy(
				_addTaskYarnInstall(task, yarnLockFile, false)));

		return task;
	}

	private void _configureTaskNpmInstall(
		NpmInstallTask npmInstallTask, Task yarnInstallTask) {

		if (!npmInstallTask.isUseNpm()) {
			npmInstallTask.finalizedBy(yarnInstallTask);
		}
	}

	private void _configureTasksNpmInstall(
		Project project, final Task yarnInstallTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskNpmInstall(npmInstallTask, yarnInstallTask);
				}

			});
	}

	private FileTree _getYarnLockFiles(Project project) {
		Map<String, Object> args = new HashMap<>();

		args.put("dir", project.getProjectDir());
		args.put("excludes", _excludes);
		args.put("includes", _includes);

		return project.fileTree(args);
	}

	@SuppressWarnings("unchecked")
	private boolean _hasPackageJsonScript(
		String scriptName, File packageJsonFile) {

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