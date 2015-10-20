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

package com.liferay.gradle.plugins.soy;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class SoyPlugin implements Plugin<Project> {

	public static final String BUILD_SOY_TASK_NAME = "buildSoy";

	@Override
	public void apply(Project project) {
		addTaskBuildSoy(project);
	}

	protected BuildSoyTask addTaskBuildSoy(final Project project) {
		BuildSoyTask buildSoyTask = GradleUtil.addTask(
			project, BUILD_SOY_TASK_NAME, BuildSoyTask.class);

		buildSoyTask.setDescription(
			"Compiles Closure Templates into JavaScript functions.");
		buildSoyTask.setGroup(BasePlugin.BUILD_GROUP);

		buildSoyTask.setClasspath(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						project, SourceSet.MAIN_SOURCE_SET_NAME);

					return sourceSet.getCompileClasspath();
				}

			});

		buildSoyTask.setIncludes(Collections.singleton("**/*.soy"));

		buildSoyTask.setSource(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getResourcesDir(project);
				}

			});

		return buildSoyTask;
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

}