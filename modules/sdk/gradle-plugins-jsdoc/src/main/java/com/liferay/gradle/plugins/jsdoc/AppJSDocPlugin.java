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

package com.liferay.gradle.plugins.jsdoc;

import com.liferay.gradle.plugins.jsdoc.internal.constants.JSDocConstants;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 * @author Peter Shin
 */
public class AppJSDocPlugin implements Plugin<Project> {

	public static final String APP_JSDOC_TASK_NAME = "appJSDoc";

	public static final String DOWNLOAD_JSDOC_TASK_NAME = "downloadJSDoc";

	public static final String JAR_APP_JSDOC_TASK_NAME = "jarAppJSDoc";

	public static final String PLUGIN_NAME = "appJSDocConfiguration";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		final AppJSDocConfigurationExtension appJSDocConfigurationExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, AppJSDocConfigurationExtension.class);

		DownloadNodeModuleTask downloadJSDocTask = _addTaskDownloadJSDoc(
			project);

		final JSDocTask appJSDocTask = _addTaskAppJSDoc(downloadJSDocTask);

		_addTaskJarAppJSDoc(appJSDocTask);

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project subproject) {
					Set<Project> subprojects =
						appJSDocConfigurationExtension.getSubprojects();

					PluginContainer pluginContainer = subproject.getPlugins();

					if (subprojects.contains(subproject) &&
						pluginContainer.hasPlugin(JSDocPlugin.class)) {

						_configureTaskAppJSDoc(appJSDocTask, subproject);
					}
				}

			});
	}

	private JSDocTask _addTaskAppJSDoc(
		final DownloadNodeModuleTask downloadJSDocTask) {

		final JSDocTask appJSDocTask = GradleUtil.addTask(
			downloadJSDocTask.getProject(), APP_JSDOC_TASK_NAME,
			JSDocTask.class);

		appJSDocTask.dependsOn(downloadJSDocTask);

		appJSDocTask.setDescription(
			"Generates the API documentation for the JavaScript code in this " +
				"app.");

		appJSDocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					Project project = appJSDocTask.getProject();

					return new File(project.getBuildDir(), "jsdoc");
				}

			});

		appJSDocTask.setGroup(JavaBasePlugin.DOCUMENTATION_GROUP);

		appJSDocTask.setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						downloadJSDocTask.getModuleDir(), "jsdoc.js");
				}

			});

		return appJSDocTask;
	}

	private DownloadNodeModuleTask _addTaskDownloadJSDoc(Project project) {
		DownloadNodeModuleTask downloadJSDocTask = GradleUtil.addTask(
			project, DOWNLOAD_JSDOC_TASK_NAME, DownloadNodeModuleTask.class);

		downloadJSDocTask.args("--no-save");
		downloadJSDocTask.setDescription("Downloads JSDoc.");
		downloadJSDocTask.setModuleName("jsdoc");
		downloadJSDocTask.setModuleVersion(JSDocConstants.VERSION);

		return downloadJSDocTask;
	}

	private Jar _addTaskJarAppJSDoc(JSDocTask jsDocTask) {
		Jar jar = GradleUtil.addTask(
			jsDocTask.getProject(), JAR_APP_JSDOC_TASK_NAME, Jar.class);

		jar.from(jsDocTask);
		jar.setClassifier("jsdoc");
		jar.setDescription(
			"Assembles a jar archive containing the JavaScript documentation " +
				"files for this app.");
		jar.setGroup(BasePlugin.BUILD_GROUP);

		return jar;
	}

	private void _configureTaskAppJSDoc(
		JSDocTask appJSDocTask, Project subproject) {

		JSDocTask subprojectJSDocTask = (JSDocTask)GradleUtil.getTask(
			subproject, JSDocPlugin.JSDOC_TASK_NAME);

		FileCollection fileCollection = subprojectJSDocTask.getSourceDirs();

		for (File file : fileCollection) {
			if (file.exists()) {
				appJSDocTask.sourceDirs(file);
			}
		}
	}

}