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

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.LiferayYarnPlugin;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PackageRunBuildTask;
import com.liferay.gradle.plugins.node.tasks.YarnInstallTask;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Properties;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.logging.Logger;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.util.GUtil;

/**
 * @author Peter Shin
 */
public class LiferayCIPatcherPlugin implements Plugin<Project> {

	public static final String APPEND_HOTFIX_QUALIFIER_TASK_NAME =
		"appendHotfixQualifier";

	public static final Plugin<Project> INSTANCE = new LiferayCIPatcherPlugin();

	public static final String REMOVE_HOTFIX_QUALIFIER_TASK_NAME =
		"removeHotfixQualifier";

	@Override
	public void apply(final Project project) {
		Task appendHotfixQualifierTask = _addTaskAppendHotfixQualifier(project);
		Task removeHotfixQualifierTask = _addTaskRemoveHotfixQualifier(project);

		_configureTasksPackageRunBuild(project);
		_configureTasksNpmInstall(project);
		_configureTasksYarnInstall(
			project, appendHotfixQualifierTask, removeHotfixQualifierTask);
	}

	private LiferayCIPatcherPlugin() {
	}

	private Task _addTaskAppendHotfixQualifier(final Project project) {
		Task task = project.task(APPEND_HOTFIX_QUALIFIER_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					for (String dirName : _PATCHER_DIRS.split(",")) {
						File bndBndFile = new File(
							new File(project.getProjectDir(), dirName),
							"bnd.bnd");

						if (!bndBndFile.exists()) {
							continue;
						}

						Properties properties = GUtil.loadProperties(
							bndBndFile);

						String version = properties.getProperty(
							Constants.BUNDLE_VERSION);

						if ((version != null) &&
							(version.indexOf(_PATCHER_QUALIFIER) == -1)) {

							_updateBndBndFile(version, bndBndFile);

							Logger logger = project.getLogger();

							if (logger.isLifecycleEnabled()) {
								logger.lifecycle(
									"Appended {}:{}",
									project.relativePath(bndBndFile),
									_PATCHER_QUALIFIER);
							}
						}
					}
				}

				private void _updateBndBndFile(
					String version, File bndBndFile) {

					try {
						String text = new String(
							Files.readAllBytes(bndBndFile.toPath()),
							StandardCharsets.UTF_8);

						text = text.replaceAll(
							"^" + Constants.BUNDLE_VERSION + ": .*$",
							Constants.BUNDLE_VERSION + ": " + version +
								_PATCHER_QUALIFIER);

						Files.write(
							bndBndFile.toPath(),
							text.getBytes(StandardCharsets.UTF_8));
					}
					catch (IOException ioe) {
						throw new UncheckedIOException(ioe);
					}
				}

			});

		return task;
	}

	private Task _addTaskRemoveHotfixQualifier(final Project project) {
		Task task = project.task(REMOVE_HOTFIX_QUALIFIER_TASK_NAME);

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					for (String dirName : _PATCHER_DIRS.split(",")) {
						File bndBndFile = new File(
							new File(project.getProjectDir(), dirName),
							"bnd.bnd");

						if (!bndBndFile.exists()) {
							continue;
						}

						Properties properties = GUtil.loadProperties(
							bndBndFile);

						String version = properties.getProperty(
							Constants.BUNDLE_VERSION);

						if ((version != null) &&
							(version.indexOf(_PATCHER_QUALIFIER) != -1)) {

							_updateBndBndFile(version, bndBndFile);

							Logger logger = project.getLogger();

							if (logger.isLifecycleEnabled()) {
								logger.lifecycle(
									"Removed {}:{}",
									project.relativePath(bndBndFile),
									_PATCHER_QUALIFIER);
							}
						}
					}
				}

				private void _updateBndBndFile(
					String version, File bndBndFile) {

					try {
						String text = new String(
							Files.readAllBytes(bndBndFile.toPath()),
							StandardCharsets.UTF_8);

						int x = version.length();
						int y = _PATCHER_QUALIFIER.length();

						text = text.replaceAll(
							version + "$", version.substring(0, x - y));

						Files.write(
							bndBndFile.toPath(),
							text.getBytes(StandardCharsets.UTF_8));
					}
					catch (IOException ioe) {
						throw new UncheckedIOException(ioe);
					}
				}

			});

		return task;
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

	private void _configureTasksYarnInstall(
		Project project, final Task appendHotfixQualifierTask,
		final Task removeHotfixQualifierTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			YarnInstallTask.class,
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					appendHotfixQualifierTask.mustRunAfter(yarnInstallTask);

					String taskName = yarnInstallTask.getName();

					if (taskName.equals(
							LiferayYarnPlugin.YARN_INSTALL_TASK_NAME)) {

						_configureTaskYarnInstall(
							appendHotfixQualifierTask,
							removeHotfixQualifierTask, yarnInstallTask);
					}
				}

			});
	}

	private void _configureTaskYarnInstall(
		Task appendHotfixQualifierTask, Task removeHotfixQualifierTask,
		YarnInstallTask yarnInstallTask) {

		if (Validator.isNotNull(_PATCHER_DIRS) &&
			Validator.isNotNull(_PATCHER_QUALIFIER)) {

			yarnInstallTask.dependsOn(removeHotfixQualifierTask);
			yarnInstallTask.finalizedBy(appendHotfixQualifierTask);
		}
	}

	private static final String _PATCHER_DIRS = System.getProperty(
		"patcher.hotfix.dirs");

	private static final String _PATCHER_QUALIFIER = System.getProperty(
		"patcher.hotfix.qualifier");

}