/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.alloy.taglib;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSGiUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class AlloyTaglibPlugin implements Plugin<Project> {

	public static final String BUILD_TAGLIBS_TASK_NAME = "buildTaglibs";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaLibraryPlugin.class);

		_addTaskBuildTaglibs(project);
	}

	private BuildTaglibsTask _addTaskBuildTaglibs(Project project) {
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
						_getJavaDir(buildTaglibsTask.getProject()),
						javaPackage.replace('.', '/'));
				}

			});

		buildTaglibsTask.setJspParentDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = _getResourcesDir(
						buildTaglibsTask.getProject());

					return new File(resourcesDir, "META-INF/resources");
				}

			});

		buildTaglibsTask.setOsgiModuleSymbolicName(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return OSGiUtil.getBundleSymbolicName(
						buildTaglibsTask.getProject());
				}

			});

		buildTaglibsTask.setTldDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = _getResourcesDir(
						buildTaglibsTask.getProject());

					return new File(resourcesDir, "META-INF/resources");
				}

			});

		return buildTaglibsTask;
	}

	private File _getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return _getSrcDir(sourceSet.getJava());
	}

	private File _getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return _getSrcDir(sourceSet.getResources());
	}

	private File _getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

}