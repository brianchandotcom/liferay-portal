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
import com.liferay.gradle.plugins.node.tasks.YarnInstallTask;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 * @author David Truong
 */
public class YarnPlugin implements Plugin<Project> {

	public static final String YARN_INSTALL_TASK_NAME = "yarnInstall";

	@Override
	public void apply(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, NodePlugin.class);

		// Tasks

		TaskProvider<YarnInstallTask> yarnInstallTaskProvider =
			GradleUtil.addTaskProvider(
				project, YARN_INSTALL_TASK_NAME, YarnInstallTask.class);

		_configureTaskYarnInstallProvider(yarnInstallTaskProvider);
	}

	private void _configureTaskYarnInstallProvider(
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider) {

		yarnInstallTaskProvider.configure(
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					yarnInstallTask.setDescription(
						"Installs Node packages from package.json.");
					yarnInstallTask.setFrozenLockFile(
						Boolean.parseBoolean(
							System.getProperty(
								"frozen.lockfile", Boolean.TRUE.toString())));
				}

			});
	}

}