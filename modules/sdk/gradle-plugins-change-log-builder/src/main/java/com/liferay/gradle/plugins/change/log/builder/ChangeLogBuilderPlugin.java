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

package com.liferay.gradle.plugins.change.log.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class ChangeLogBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_CHANGE_LOG_TASK_NAME = "buildChangeLog";

	@Override
	public void apply(Project project) {
		addTaskBuildChangeLog(project);
	}

	protected BuildChangeLogTask addTaskBuildChangeLog(Project project) {
		final BuildChangeLogTask buildChangeLogTask = GradleUtil.addTask(
			project, BUILD_CHANGE_LOG_TASK_NAME, BuildChangeLogTask.class);

		buildChangeLogTask.setChangeLogFile(_CHANGE_LOG_FILE_NAME);
		buildChangeLogTask.setDescription(
			"Builds the change log file for this project.");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskBuildChangeLogForJavaPlugin(
						buildChangeLogTask);
				}

			});

		return buildChangeLogTask;
	}

	protected void configureTaskBuildChangeLogForJavaPlugin(
		final BuildChangeLogTask buildChangeLogTask) {

		buildChangeLogTask.setChangeLogFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(
						buildChangeLogTask.getProject());

					return new File(
						resourcesDir, "META-INF/" + _CHANGE_LOG_FILE_NAME);
				}

			});
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getResources());
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final String _CHANGE_LOG_FILE_NAME =
		"liferay-releng.changelog";

}