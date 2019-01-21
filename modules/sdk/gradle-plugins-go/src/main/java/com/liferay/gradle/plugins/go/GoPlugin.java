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

package com.liferay.gradle.plugins.go;

import com.liferay.gradle.plugins.go.internal.util.GradleUtil;
import com.liferay.gradle.plugins.go.tasks.DownloadGoTask;
import com.liferay.gradle.plugins.go.tasks.ExecuteGoTask;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class GoPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_GO_TASK_NAME = "downloadGo";

	public static final String EXTENSION_NAME = "go";

	@Override
	public void apply(Project project) {
		GradleUtil.addExtension(project, EXTENSION_NAME, GoExtension.class);

		addTaskDownloadGo(project);

		_configureTasksExecuteGo(project, GradleUtil.isRunningInsideDaemon());
	}

	protected DownloadGoTask addTaskDownloadGo(Project project) {
		return GradleUtil.addTask(
			project, DOWNLOAD_GO_TASK_NAME, DownloadGoTask.class);
	}

	private void _configureTaskExecuteGo(
		ExecuteGoTask executeGoTask, boolean useGradleExec) {

		executeGoTask.setUseGradleExec(useGradleExec);
	}

	private void _configureTasksExecuteGo(
		Project project, final boolean useGradleExec) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGoTask.class,
			new Action<ExecuteGoTask>() {

				@Override
				public void execute(ExecuteGoTask executeGoTask) {
					_configureTaskExecuteGo(executeGoTask, useGradleExec);
				}

			});
	}

}