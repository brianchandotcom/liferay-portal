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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.LiferayOSGiDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.LiferayRelengUtil;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.MavenPlugin;

/**
 * @author Andrea Di Giorgi
 */
public class MavenDefaultsPlugin extends BaseDefaultsPlugin<MavenPlugin> {

	public static final Plugin<Project> INSTANCE = new MavenDefaultsPlugin();

	@Override
	protected void configureDefaults(Project project, MavenPlugin mavenPlugin) {
		_configureTaskUploadArchives(project);
	}

	@Override
	protected Class<MavenPlugin> getPluginClass() {
		return MavenPlugin.class;
	}

	protected static final Action<Task> failReleaseOnWrongBranchAction =
		new Action<Task>() {

			@Override
			public void execute(Task task) {
				Project project = task.getProject();

				File relengIgnoreDir = GradleUtil.getRootDir(
					project, LiferayRelengPlugin.RELENG_IGNORE_FILE_NAME);

				if (relengIgnoreDir != null) {
					return;
				}

				File portalRootDir = GradleUtil.getRootDir(
					project.getRootProject(), "portal-impl");

				if (portalRootDir == null) {
					return;
				}

				if (GradlePluginsDefaultsUtil.isSnapshot(project)) {
					File relengDir = new File(portalRootDir, "modules/.releng");

					if (relengDir.exists()) {
						throw new GradleException(
							"Please run this task from a master branch " +
								"instead");
					}

					return;
				}

				File releasePortalRootDir = GradleUtil.getProperty(
					project,
					LiferayOSGiDefaultsPlugin.
						RELEASE_PORTAL_ROOT_DIR_PROPERTY_NAME,
					(File)null);

				if (releasePortalRootDir == null) {
					throw new GradleException(
						"Please set the property \"" +
							LiferayOSGiDefaultsPlugin.
								RELEASE_PORTAL_ROOT_DIR_PROPERTY_NAME + "\".");
				}

				String relativePath = FileUtil.relativize(
					project.getProjectDir(), portalRootDir);

				File releaseProjectDir = new File(
					releasePortalRootDir, relativePath);

				if (!releaseProjectDir.exists()) {
					return;
				}

				File relengDir = LiferayRelengUtil.getRelengDir(project);
				File releaseRelengDir = LiferayRelengUtil.getRelengDir(
					releaseProjectDir);

				if ((relengDir == null) && (releaseRelengDir != null) &&
					releaseRelengDir.isDirectory()) {

					throw new GradleException(
						"Please run this task from " + releaseProjectDir +
							" instead");
				}
			}

		};

	private MavenDefaultsPlugin() {
	}

	private void _configureTaskUploadArchives(Project project) {
		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		uploadArchivesTask.doFirst(failReleaseOnWrongBranchAction);
	}

}