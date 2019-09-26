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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.LiferayYarnPlugin;
import com.liferay.gradle.plugins.defaults.internal.NodeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.tasks.YarnInstallTask;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class LiferayYarnDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, LiferayYarnPlugin.class);

		NodeDefaultsPlugin.INSTANCE.apply(project);

		_configureTasksYarnInstall(project);
	}

	private void _configureTasksYarnInstall(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			YarnInstallTask.class,
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					String name = yarnInstallTask.getName();

					if (name.startsWith(
							LiferayYarnPlugin.YARN_INSTALL_TASK_NAME)) {

						_configureTaskYarnInstall(yarnInstallTask);
					}
				}

			});
	}

	private void _configureTaskYarnInstall(YarnInstallTask yarnInstallTask) {
		String offline = GradleUtil.getTaskPrefixedProperty(
			yarnInstallTask, "offline");

		if (Validator.isNull(offline)) {
			offline = System.getProperty("yarnInstall.offline");
		}

		if (Boolean.parseBoolean(offline)) {
			yarnInstallTask.setOffline(true);
		}
	}

}