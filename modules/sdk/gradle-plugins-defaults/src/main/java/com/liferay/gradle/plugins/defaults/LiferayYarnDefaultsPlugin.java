/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.NodeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayCIPatcherPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayCIPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.CIUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.YarnPlugin;
import com.liferay.gradle.plugins.node.task.YarnInstallTask;
import com.liferay.gradle.util.Validator;

import org.gradle.StartParameter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 */
public class LiferayYarnDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, YarnPlugin.class);

		GradleUtil.applyPlugin(project, NodeDefaultsPlugin.class);

		com.liferay.gradle.plugins.defaults.internal.NodeDefaultsPlugin.
			INSTANCE.apply(project);

		if (CIUtil.isRunningInCIEnvironment()) {
			LiferayCIPlugin.INSTANCE.apply(project);
		}

		if (CIUtil.isRunningInCIPatcherEnvironment()) {
			LiferayCIPatcherPlugin.INSTANCE.apply(project);
		}

		TaskProvider<YarnInstallTask> yarnInstallTaskProvider =
			GradleUtil.getTaskProvider(
				project, YarnPlugin.YARN_INSTALL_TASK_NAME,
				YarnInstallTask.class);

		_configureTaskYarnInstallProvider(project, yarnInstallTaskProvider);
	}

	private void _configureTaskYarnInstallProvider(
		final Project project,
		TaskProvider<YarnInstallTask> yarnInstallTaskProvider) {

		yarnInstallTaskProvider.configure(
			new Action<YarnInstallTask>() {

				@Override
				public void execute(YarnInstallTask yarnInstallTask) {
					Gradle gradle = project.getGradle();

					StartParameter startParameter = gradle.getStartParameter();

					String buildProfile = System.getProperty("build.profile");

					if (startParameter.isParallelProjectExecutionEnabled() ||
						Validator.isNotNull(buildProfile)) {

						yarnInstallTask.setEnabled(false);
					}
				}

			});
	}

}