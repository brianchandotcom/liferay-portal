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

package com.liferay.gradle.plugins;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.tasks.UpdateVersionTask;
import com.liferay.gradle.plugins.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.MavenPlugin;
import org.gradle.api.tasks.Upload;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemeDefaultsPlugin implements Plugin<Project> {

	public static final String UPDATE_THEME_VERSION_TASK_NAME =
		"updateThemeVersion";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, LiferayThemePlugin.class);

		applyPlugins(project);

		// GRADLE-2427

		addTaskInstall(project);

		applyConfigScripts(project);

		final UpdateVersionTask updateThemeVersionTask =
			addTaskUpdateThemeVersion(project);

		configureDeployDir(project);
		configureProject(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					GradleUtil.setProjectSnapshotVersion(project);

					// setProjectSnapshotVersion must be called before
					// configureTaskUploadArchives, because the latter one needs
					// to know if we are publishing a snapshot or not.

					configureTaskUploadArchives(
						project, updateThemeVersionTask);
				}

			});
	}

	protected Upload addTaskInstall(Project project) {
		Upload upload = GradleUtil.addTask(
			project, MavenPlugin.INSTALL_TASK_NAME, Upload.class);

		Configuration configuration = GradleUtil.getConfiguration(
			project, Dependency.ARCHIVES_CONFIGURATION);

		upload.setConfiguration(configuration);
		upload.setDescription(
			"Installs the '" + configuration.getName() +
				"' artifacts into the local Maven repository.");

		return upload;
	}

	protected UpdateVersionTask addTaskUpdateThemeVersion(
		final Project project) {

		UpdateVersionTask updateVersionTask = GradleUtil.addTask(
			project, UPDATE_THEME_VERSION_TASK_NAME, UpdateVersionTask.class);

		updateVersionTask.pattern(
			"package.json",
			"\"version\": \"" + UpdateVersionTask.VERSION_PLACEHOLDER + "\"");

		updateVersionTask.setDescription(
			"Updates the project version in the " + Constants.BUNDLE_VERSION +
				" header.");

		updateVersionTask.setVersion(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});

		return updateVersionTask;
	}

	protected void applyConfigScripts(Project project) {
		GradleUtil.applyScript(
			project,
			"com/liferay/gradle/plugins/dependencies/config-maven.gradle",
			project);
	}

	protected void applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, MavenPlugin.class);
	}

	protected void configureDeployDir(Project project) {
		final LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						liferayExtension.getLiferayHome(), "deploy");
				}

			});
	}

	protected void configureProject(Project project) {
		project.setGroup(_GROUP);
	}

	protected void configureTaskUploadArchives(
		Project project, UpdateVersionTask updateThemeVersionTask) {

		if (GradleUtil.isSnapshot(project)) {
			return;
		}

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.finalizedBy(updateThemeVersionTask);
	}

	private static final String _GROUP = "com.liferay.plugins";

}