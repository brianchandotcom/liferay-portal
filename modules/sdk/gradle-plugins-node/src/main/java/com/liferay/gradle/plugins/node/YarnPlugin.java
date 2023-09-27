/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node;

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.task.YarnInstallTask;

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