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

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Gregory Amerson
 */
public class LiferayWarPlugin implements Plugin<Project> {

	public static final String BUILD_WAR_DIR_TASK_NAME = "buildWarDir";

	public static final String WATCH_TASK_NAME = "watch";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, WarPlugin.class);

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		Sync buildWarDirTask = _addTaskBuildWarDir(project, war);

		_addTaskWatch(buildWarDirTask, war);
	}

	private Sync _addTaskBuildWarDir(final Project project, final War war) {
		Sync sync = GradleUtil.addTask(
			project, BUILD_WAR_DIR_TASK_NAME, Sync.class);

		sync.dependsOn(war);

		sync.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					return project.zipTree(war.getArchivePath());
				}

			});

		sync.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						project.getBuildDir(), BUILD_WAR_DIR_TASK_NAME);
				}

			});

		sync.setDescription(
			"Unzips the project's WAR file into a temporary directory.");

		return sync;
	}

	private WatchTask _addTaskWatch(final Sync buildWarDirTask, final War war) {
		WatchTask watchTask = GradleUtil.addTask(
			buildWarDirTask.getProject(), WATCH_TASK_NAME, WatchTask.class);

		watchTask.dependsOn(buildWarDirTask);

		watchTask.setBundleDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return buildWarDirTask.getDestinationDir();
				}

			});

		watchTask.setBundleSymbolicName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return war.getBaseName();
				}

			});

		watchTask.setDescription(
			"Continuously redeploys the project's WAR dir.");
		watchTask.setGroup(BasePlugin.BUILD_GROUP);

		return watchTask;
	}

}