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
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class AlloyTaglibPlugin implements Plugin<Project> {

	public static final String BUILD_TAGLIBS_TASK_NAME = "buildTaglibs";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		addTaskBuildTaglibs(project);
	}

	protected BuildTaglibsTask addTaskBuildTaglibs(Project project) {
		final BuildTaglibsTask buildTaglibsTask = GradleUtil.addTask(
			project, BUILD_TAGLIBS_TASK_NAME, BuildTaglibsTask.class);

		buildTaglibsTask.setDescription(
			"Builds the AlloyUI JSP Taglibs for this project.");
		buildTaglibsTask.setGroup(BasePlugin.BUILD_GROUP);

		buildTaglibsTask.setJavaDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					String javaPackage = buildTaglibsTask.getJavaPackage();

					if (Validator.isNull(javaPackage)) {
						return null;
					}

					return new File(
						getJavaDir(buildTaglibsTask.getProject()),
						javaPackage.replace('.', '/'));
				}

			});

		buildTaglibsTask.setJspParentDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(
						buildTaglibsTask.getProject());

					return new File(resourcesDir, "META-INF/resources");
				}

			});

		buildTaglibsTask.setOsgiModuleSymbolicName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _osgiHelper.getBundleSymbolicName(
						buildTaglibsTask.getProject());
				}

			});

		buildTaglibsTask.setTldDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(
						buildTaglibsTask.getProject());

					return new File(resourcesDir, "META-INF/resources");
				}

			});

		return buildTaglibsTask;
	}

	protected File getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getJava());
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

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}