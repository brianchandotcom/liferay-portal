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

package com.liferay.gradle.plugins.jsdoc;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
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
public abstract class BaseJSDocPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_JSDOC_TASK_NAME = "downloadJSDoc";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		DownloadNodeModuleTask downloadJSDocTask = _addTaskDownloadJSDoc(
			project);

		_configureTasksJSDoc(downloadJSDocTask);
	}

	private DownloadNodeModuleTask _addTaskDownloadJSDoc(Project project) {
		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_JSDOC_TASK_NAME, DownloadNodeModuleTask.class);

		downloadNodeModuleTask.args("--no-save");
		downloadNodeModuleTask.setDescription("Downloads JSDoc.");
		downloadNodeModuleTask.setModuleName("jsdoc");
		downloadNodeModuleTask.setModuleVersion(_VERSION);

		return downloadNodeModuleTask;
	}

	private void _configureTaskJSDoc(
		JSDocTask jsdocTask, final DownloadNodeModuleTask downloadJSDocTask) {

		jsdocTask.dependsOn(downloadJSDocTask);

		jsdocTask.setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						downloadJSDocTask.getModuleDir(), "jsdoc.js");
				}

			});
	}

	private void _configureTasksJSDoc(
		final DownloadNodeModuleTask downloadJSDocTask) {

		Project project = downloadJSDocTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JSDocTask.class,
			new Action<JSDocTask>() {

				@Override
				public void execute(JSDocTask jsdocTask) {
					_configureTaskJSDoc(jsdocTask, downloadJSDocTask);
				}

			});
	}

	private static final String _VERSION = "3.5.5";

}