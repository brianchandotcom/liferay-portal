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

package com.liferay.gradle.plugins.gulp;

import com.liferay.gradle.plugins.gulp.tasks.GulpTask;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;

import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Rule;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author David Truong
 */
public class GulpPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_GULP_TASK_NAME = "downloadGulp";

	public static final String EXECUTE_GULP_TASK_NAME = "executeGulp";

	public static final String EXTENSION_NAME = "gulp";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		GulpExtension gulpExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, GulpExtension.class);

		addTaskDownloadGulp(project, gulpExtension);

		final TaskContainer tasks = project.getTasks();

		tasks.addRule(
			new Rule() {
				@Override
				public String getDescription() {
					return
						"Pattern: 'gulp_<task>': Executes a named gulp task.";
				}

				@Override
				public void apply(String taskName) {
					if (taskName.startsWith( "gulp_")) {
						GulpTask gulpTask = tasks.create(
							taskName, GulpTask.class);
						gulpTask.setGulpCommand(
							taskName.substring("gulp_".length()));
					}
				}
			}
		);
	}

	protected DownloadNodeModuleTask addTaskDownloadGulp(
		Project project, final GulpExtension gulpExtension) {

		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_GULP_TASK_NAME, DownloadNodeModuleTask.class);

		downloadNodeModuleTask.setModuleName("gulp");

		downloadNodeModuleTask.setModuleVersion(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return gulpExtension.getVersion();
				}

			});

		return downloadNodeModuleTask;
	}

	protected GulpTask addTaskExecuteGulp(
		Project project, final GulpExtension gulpExtension) {

		GulpTask gulpTask = GradleUtil.addTask(
			project, EXECUTE_GULP_TASK_NAME, GulpTask.class);

		return gulpTask;
	}

}