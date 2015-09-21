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

package com.liferay.gradle.plugins.alloy.taglib;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class AlloyTaglibPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksBuildTaglibs(project);
				}

			});
	}

	protected void configureTaskBuildTaglibsJspParentDir(
		BuildTaglibsTask buildTaglibsTask) {

		if (buildTaglibsTask.getJspParentDir() != null) {
			return;
		}

		File jspParentDir = getResourcesDir(buildTaglibsTask.getProject());

		buildTaglibsTask.setJspParentDir(jspParentDir);
	}

	protected void configureTaskBuildTaglibsTldDir(
		BuildTaglibsTask buildTaglibsTask) {

		if (buildTaglibsTask.getTldDir() != null) {
			return;
		}

		File tldDir = getResourcesDir(buildTaglibsTask.getProject());

		buildTaglibsTask.setTldDir(tldDir);
	}

	protected void configureTasksBuildTaglibs(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildTaglibsTask.class,
			new Action<BuildTaglibsTask>() {

				@Override
				public void execute(BuildTaglibsTask buildTaglibsTask) {
					configureTaskBuildTaglibsJspParentDir(buildTaglibsTask);
					configureTaskBuildTaglibsTldDir(buildTaglibsTask);
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

}