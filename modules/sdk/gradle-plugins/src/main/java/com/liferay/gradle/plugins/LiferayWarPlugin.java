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

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.tasks.WatchTask;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Gregory Amerson
 */
public class LiferayWarPlugin implements Plugin<Project> {

	public static final String BUILD_WAR_DIR_TASK_NAME = "buildWarDir";

	public static final String WATCH_TASK_NAME = "watch";

	@Override
	public void apply(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, WarPlugin.class);

		// Tasks

		TaskProvider<Sync> buildWarDirTaskProvider = GradleUtil.addTaskProvider(
			project, BUILD_WAR_DIR_TASK_NAME, Sync.class);
		TaskProvider<WatchTask> watchTaskProvider = GradleUtil.addTaskProvider(
			project, WATCH_TASK_NAME, WatchTask.class);

		TaskProvider<War> warTaskProvider = GradleUtil.getTaskProvider(
			project, WarPlugin.WAR_TASK_NAME, War.class);

		_configureTaskBuildWarDirProvider(
			project, buildWarDirTaskProvider, warTaskProvider);
		_configureTaskWatchProvider(
			buildWarDirTaskProvider, warTaskProvider, watchTaskProvider);
	}

	private void _configureTaskBuildWarDirProvider(
		final Project project, TaskProvider<Sync> buildWarDirTaskProvider,
		final TaskProvider<War> warTaskProvider) {

		buildWarDirTaskProvider.configure(
			new Action<Sync>() {

				@Override
				public void execute(Sync buildWarDirSync) {
					buildWarDirSync.dependsOn(warTaskProvider);

					buildWarDirSync.from(
						new Callable<FileTree>() {

							@Override
							public FileTree call() throws Exception {
								War war = warTaskProvider.get();

								return project.zipTree(war.getArchivePath());
							}

						});

					buildWarDirSync.into(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									project.getBuildDir(),
									BUILD_WAR_DIR_TASK_NAME);
							}

						});

					buildWarDirSync.setDescription(
						"Unzips the project's WAR file into a temporary " +
							"directory.");
				}

			});
	}

	private void _configureTaskWatchProvider(
		final TaskProvider<Sync> buildWarDirTaskProvider,
		final TaskProvider<War> warTaskProvider,
		TaskProvider<WatchTask> watchTaskProvider) {

		watchTaskProvider.configure(
			new Action<WatchTask>() {

				@Override
				public void execute(WatchTask watchTask) {
					watchTask.dependsOn(buildWarDirTaskProvider);

					watchTask.setBundleDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								Sync buildWarDirSync =
									buildWarDirTaskProvider.get();

								return buildWarDirSync.getDestinationDir();
							}

						});

					watchTask.setBundleSymbolicName(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								War war = warTaskProvider.get();

								return war.getBaseName();
							}

						});

					watchTask.setDescription(
						"Continuously redeploys the project's WAR dir.");
					watchTask.setGroup(BasePlugin.BUILD_GROUP);
				}

			});
	}

}