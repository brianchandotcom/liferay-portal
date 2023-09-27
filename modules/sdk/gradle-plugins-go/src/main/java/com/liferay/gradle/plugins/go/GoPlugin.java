/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.go;

import com.liferay.gradle.plugins.go.internal.util.FileUtil;
import com.liferay.gradle.plugins.go.internal.util.GradleUtil;
import com.liferay.gradle.plugins.go.internal.util.StringUtil;
import com.liferay.gradle.plugins.go.task.DownloadGoTask;
import com.liferay.gradle.plugins.go.task.ExecuteGoTask;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class GoPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_GO_TASK_NAME = "downloadGo";

	public static final String EXTENSION_NAME = "go";

	@Override
	public void apply(Project project) {
		GoExtension goExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, GoExtension.class);

		DownloadGoTask downloadGoTask = addTaskDownloadGo(project);

		_configureTasksExecuteGo(project, GradleUtil.isRunningInsideDaemon());

		_addTasksExecuteGo(downloadGoTask, goExtension);
	}

	protected DownloadGoTask addTaskDownloadGo(Project project) {
		return GradleUtil.addTask(
			project, DOWNLOAD_GO_TASK_NAME, DownloadGoTask.class);
	}

	private ExecuteGoTask _addTaskExecuteGo(
		String command, String description, File file,
		DownloadGoTask downloadGoTask) {

		ExecuteGoTask executeGoTask = GradleUtil.addTask(
			downloadGoTask.getProject(), _getTaskName(command, file),
			ExecuteGoTask.class);

		executeGoTask.dependsOn(downloadGoTask);

		executeGoTask.setArgs(command, file.getAbsolutePath());
		executeGoTask.setDescription(description);
		executeGoTask.setGroup(BasePlugin.BUILD_GROUP);

		return executeGoTask;
	}

	private void _addTasksExecuteGo(
		DownloadGoTask downloadGoTask, GoExtension goExtension) {

		File[] files = FileUtil.getFiles(goExtension.getWorkingDir(), "go");

		if ((files == null) || (files.length == 0)) {
			return;
		}

		for (File file : files) {
			StringBuilder sb = new StringBuilder();

			sb.append("Compile packages and dependencies for the \"");
			sb.append(FileUtil.getSimpleName(file));
			sb.append("\" Go program.");

			_addTaskExecuteGo("build", sb.toString(), file, downloadGoTask);

			sb.setLength(0);

			sb.append("Removes object files for the \"");
			sb.append(FileUtil.getSimpleName(file));
			sb.append("\" Go program.");

			_addTaskExecuteGo("clean", sb.toString(), file, downloadGoTask);

			sb.setLength(0);

			sb.append("Compile and run the \"");
			sb.append(FileUtil.getSimpleName(file));
			sb.append("\" Go program.");

			_addTaskExecuteGo("run", sb.toString(), file, downloadGoTask);

			sb.setLength(0);

			sb.append("Test packages for the \"");
			sb.append(FileUtil.getSimpleName(file));
			sb.append("\" Go program.");

			_addTaskExecuteGo("test", sb.toString(), file, downloadGoTask);
		}
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

	private String _getTaskName(String command, File file) {
		String s = StringUtil.camelCase(FileUtil.getSimpleName(file), true);

		return "go" + StringUtil.camelCase(command, true) + s;
	}

}