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
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class NodePlugin implements Plugin<Project> {

	public static final String DOWNLOAD_NODE_TASK_NAME = "downloadNode";

	public static final String EXTENSION_NAME = "node";

	@Override
	public void apply(Project project) {
		final NodeExtension nodeExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, NodeExtension.class);

		addTaskDownloadNode(project);

		configureTasksDownloadNode(project, nodeExtension);
		configureTasksExecuteNode(project, nodeExtension);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksExecuteNpm(project, nodeExtension);
				}

			});
	}

	protected DownloadNodeTask addTaskDownloadNode(Project project) {
		return GradleUtil.addTask(
			project, DOWNLOAD_NODE_TASK_NAME, DownloadNodeTask.class);
	}

	protected void configureTaskDownloadNodeDir(
		DownloadNodeTask downloadNodeTask, final NodeExtension nodeExtension) {

		downloadNodeTask.setNodeDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return nodeExtension.getNodeDir();
				}

			});
	}

	protected void configureTaskDownloadNodeNpmUrl(
		DownloadNodeTask downloadNodeTask, final NodeExtension nodeExtension) {

		downloadNodeTask.setNpmUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNpmUrl();
				}

			});
	}

	protected void configureTaskDownloadNodeUrl(
		DownloadNodeTask downloadNodeTask, final NodeExtension nodeExtension) {

		downloadNodeTask.setNodeUrl(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return nodeExtension.getNodeUrl();
				}

			});
	}

	protected void configureTaskExecuteNodeDir(
		ExecuteNodeTask executeNodeTask, final NodeExtension nodeExtension) {

		executeNodeTask.setNodeDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return nodeExtension.getNodeDir();
				}

			});
	}

	protected void configureTaskExecuteNpmArgs(
		ExecuteNpmTask executeNpmTask, NodeExtension nodeExtension) {

		executeNpmTask.args(nodeExtension.getNpmArgs());
	}

	protected void configureTasksDownloadNode(
		Project project, final NodeExtension nodeExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			DownloadNodeTask.class,
			new Action<DownloadNodeTask>() {

				@Override
				public void execute(DownloadNodeTask downloadNodeTask) {
					configureTaskDownloadNodeDir(
						downloadNodeTask, nodeExtension);
					configureTaskDownloadNodeNpmUrl(
						downloadNodeTask, nodeExtension);
					configureTaskDownloadNodeUrl(
						downloadNodeTask, nodeExtension);
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
					configureTaskExecuteNodeDir(executeNodeTask, nodeExtension);
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
					configureTaskExecuteNpmArgs(executeNpmTask, nodeExtension);
				}

			});
	}

}