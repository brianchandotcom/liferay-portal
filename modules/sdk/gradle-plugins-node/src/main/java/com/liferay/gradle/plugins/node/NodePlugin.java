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

import com.liferay.gradle.plugins.node.tasks.DownloadNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNodeTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.node.tasks.PublishNodeModuleTask;
import com.liferay.gradle.plugins.node.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodePlugin implements Plugin<Project> {

	public static final String DOWNLOAD_NODE_TASK_NAME = "downloadNode";

	public static final String EXTENSION_NAME = "node";

	public static final String NPM_INSTALL_TASK_NAME = "npmInstall";

	@Override
	public void apply(Project project) {
		final NodeExtension nodeExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, NodeExtension.class);

		addTaskDownloadNode(project, nodeExtension);
		addTaskNpmInstall(project, nodeExtension);

		configureTasksExecuteNode(project, nodeExtension);
		configureTasksPublishNodeModule(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksExecuteNpm(project, nodeExtension);
				}

			});
	}

	protected DownloadNodeTask addTaskDownloadNode(
		Project project, final NodeExtension nodeExtension) {

		DownloadNodeTask downloadNodeTask = GradleUtil.addTask(
			project, DOWNLOAD_NODE_TASK_NAME, DownloadNodeTask.class);

		downloadNodeTask.setNodeDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return nodeExtension.getNodeDir();
				}

			});

		downloadNodeTask.setNodeExeUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNodeExeUrl();
				}

			});

		downloadNodeTask.setNodeUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNodeUrl();
				}

			});

		downloadNodeTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return nodeExtension.isDownload();
				}

			});

		downloadNodeTask.setDescription(
			"Downloads Node.js in the project build directory.");

		return downloadNodeTask;
	}

	protected NpmInstallTask addTaskNpmInstall(
		Project project, final NodeExtension nodeExtension) {

		final NpmInstallTask npmInstallTask = GradleUtil.addTask(
			project, NPM_INSTALL_TASK_NAME, NpmInstallTask.class);

		npmInstallTask.setDescription(
			"Install Node packages from package.json.");

		return npmInstallTask;
	}

	protected void configureTaskExecuteNode(
		ExecuteNodeTask executeNodeTask, final NodeExtension nodeExtension) {

		executeNodeTask.setNodeDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					if (nodeExtension.isDownload()) {
						return nodeExtension.getNodeDir();
					}

					return null;
				}

			});
	}

	protected void configureTaskExecuteNpm(
		ExecuteNpmTask executeNpmTask, NodeExtension nodeExtension) {

		executeNpmTask.args(nodeExtension.getNpmArgs());
	}

	protected void configureTaskPublishNodeModule(
		PublishNodeModuleTask publishNodeModuleTask) {

		final Project project = publishNodeModuleTask.getProject();

		publishNodeModuleTask.setModuleDescription(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return project.getDescription();
				}

			});

		publishNodeModuleTask.setModuleName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					String moduleName = _osgiHelper.getBundleSymbolicName(
						project);

					int pos = moduleName.indexOf('.');

					if (pos != -1) {
						moduleName = moduleName.substring(pos + 1);

						moduleName = moduleName.replace('.', '-');
					}

					return moduleName;
				}

			});

		publishNodeModuleTask.setModuleVersion(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});
	}

	protected void configureTasksExecuteNode(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNodeTask.class,
			new Action<ExecuteNodeTask>() {

				@Override
				public void execute(ExecuteNodeTask executeNodeTask) {
					configureTaskExecuteNode(executeNodeTask, nodeExtension);
				}

			});
	}

	protected void configureTasksExecuteNpm(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteNpmTask.class,
			new Action<ExecuteNpmTask>() {

				@Override
				public void execute(ExecuteNpmTask executeNpmTask) {
					configureTaskExecuteNpm(executeNpmTask, nodeExtension);
				}

			});
	}

	protected void configureTasksPublishNodeModule(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			PublishNodeModuleTask.class,
			new Action<PublishNodeModuleTask>() {

				@Override
				public void execute(
					PublishNodeModuleTask publishNodeModuleTask) {

					configureTaskPublishNodeModule(publishNodeModuleTask);
				}

			});
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}