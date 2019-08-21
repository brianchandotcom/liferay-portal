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
import com.liferay.gradle.plugins.SourceFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;

import java.io.File;

import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayRootDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(final Project project) {
		if (FileUtil.exists(project, "app.bnd")) {
			GradleUtil.applyPlugin(project, LiferayAppDefaultsPlugin.class);
		}

		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		SourceFormatterDefaultsPlugin.INSTANCE.apply(project);

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		GradlePluginsDefaultsUtil.configureRepositories(project, portalRootDir);

		for (Project subproject : project.getSubprojects()) {
			Map<String, Project> childProjects = subproject.getChildProjects();

			if (childProjects.isEmpty()) {
				GradleUtil.applyPlugin(subproject, LiferayDefaultsPlugin.class);
			}
		}

		if ((portalRootDir == null) && _hasYarnScriptFile(project)) {
			GradleUtil.applyPlugin(project, LiferayYarnPlugin.class);

			for (Project subproject : project.getSubprojects()) {
				_configureTasksNpmInstall(project, subproject);
			}
		}
	}

	private void _configureTaskNpmInstall(
		Project rootProject, NpmInstallTask npmInstallTask) {

		File scriptFile = npmInstallTask.getScriptFile();

		if (scriptFile == null) {
			return;
		}

		String fileName = scriptFile.getName();

		if (!fileName.startsWith("yarn-") || !fileName.endsWith(".js")) {
			return;
		}

		TaskContainer taskContainer = rootProject.getTasks();

		Task yarnInstallTask = taskContainer.findByName(
			LiferayYarnPlugin.YARN_INSTALL_TASK_NAME);

		if (yarnInstallTask != null) {
			npmInstallTask.finalizedBy(yarnInstallTask);
		}
	}

	private void _configureTasksNpmInstall(
		final Project rootProject, Project project) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			NpmInstallTask.class,
			new Action<NpmInstallTask>() {

				@Override
				public void execute(NpmInstallTask npmInstallTask) {
					_configureTaskNpmInstall(rootProject, npmInstallTask);
				}

			});
	}

	private boolean _hasYarnScriptFile(Project project) {
		File projectDir = project.getProjectDir();

		File[] files = FileUtil.getFiles(projectDir, "yarn-", ".js");

		if ((files != null) && (files.length > 0)) {
			return true;
		}

		return false;
	}

}