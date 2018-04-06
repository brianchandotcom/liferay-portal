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

import com.liferay.gradle.plugins.tasks.WatchTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.bundling.AbstractArchiveTask;

/**
 * @author Gregory Amerson
 */
public class WatchPlugin implements Plugin<Project> {

	public static final String BUNDLE_DIR_TASK_NAME = "bundleDir";

	public static final String PLUGIN_NAME = "watcher";

	public static final String WATCH_TASK_NAME = "watch";

	@Override
	public void apply(final Project project) {
		File bundleDir = new File(project.getBuildDir(), BUNDLE_DIR_TASK_NAME);

		Task bundleDirTask = _addTaskBundleDir(project, bundleDir);

		_addTaskWatch(project, bundleDirTask);
	}

	private Task _addTaskBundleDir(final Project project, File bundleDir) {
		final Sync bundleDirTask = GradleUtil.addTask(
			project, BUNDLE_DIR_TASK_NAME, Sync.class);

		final AbstractArchiveTask jarTask =
			(AbstractArchiveTask)GradleUtil.getTask(
				project, JavaPlugin.JAR_TASK_NAME);

		bundleDirTask.dependsOn(jarTask);

		bundleDirTask.from(
			new Callable<FileTree>() {

				@Override
				public FileTree call() throws Exception {
					return project.zipTree(jarTask.getArchivePath());
				}

			});

		bundleDirTask.into(bundleDir);

		return bundleDirTask;
	}

	private WatchTask _addTaskWatch(Project project, Task bundleDirTask) {
		final WatchTask watchTask = GradleUtil.addTask(
			project, WATCH_TASK_NAME, WatchTask.class);

		watchTask.dependsOn(bundleDirTask);
		watchTask.setGroup(BasePlugin.BUILD_GROUP);
		watchTask.setBundleDir(
			new File(project.getBuildDir(), BUNDLE_DIR_TASK_NAME));

		return watchTask;
	}

}