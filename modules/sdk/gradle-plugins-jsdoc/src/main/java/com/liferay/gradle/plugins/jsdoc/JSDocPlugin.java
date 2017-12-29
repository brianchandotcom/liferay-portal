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

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JSDocPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_JSDOC_TASK_NAME = "downloadJSDoc";

	public static final String JSDOC_TASK_NAME = "jsdoc";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		DownloadNodeModuleTask downloadJSDocTask = _addTaskDownloadJSDoc(
			project);

		_addTaskJSDoc(project);

		_configureTasksJSDoc(downloadJSDocTask);
	}

	private DownloadNodeModuleTask _addTaskDownloadJSDoc(Project project) {
		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_JSDOC_TASK_NAME, DownloadNodeModuleTask.class);

		downloadNodeModuleTask.args("--no-save");
		downloadNodeModuleTask.setDescription("Downloads JSDoc.");
		downloadNodeModuleTask.setModuleName("jsdoc");
		downloadNodeModuleTask.setModuleVersion(_VERSION);

		return downloadNodeModuleTask;
	}

	private JSDocTask _addTaskJSDoc(Project project) {
		final JSDocTask jsdocTask = GradleUtil.addTask(
			project, JSDOC_TASK_NAME, JSDocTask.class);

		jsdocTask.setDescription(
			"Generates the API documentation for the JavaScript code in this " +
				"project.");

		jsdocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					Project project = jsdocTask.getProject();

					return new File(project.getBuildDir(), "jsdoc");
				}

			});

		jsdocTask.setGroup(JavaBasePlugin.DOCUMENTATION_GROUP);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskJSDocForJavaPlugin(jsdocTask);
				}

			});

		return jsdocTask;
	}

	private void _configureTaskJSDoc(
		JSDocTask jsdocTask, final DownloadNodeModuleTask downloadJSDocTask) {

		jsdocTask.dependsOn(downloadJSDocTask);

		jsdocTask.setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						downloadJSDocTask.getModuleDir(), "jsdoc.js");
				}

			});
	}

	private void _configureTaskJSDocForJavaPlugin(JSDocTask jsdocTask) {
		final Project project = jsdocTask.getProject();

		jsdocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					JavaPluginConvention javaPluginConvention =
						GradleUtil.getConvention(
							project, JavaPluginConvention.class);

					return new File(javaPluginConvention.getDocsDir(), "jsdoc");
				}

			});

		jsdocTask.setSourcesDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						_getResourcesDir(project), "META-INF/resources");
				}

			});
	}

	private void _configureTasksJSDoc(
		final DownloadNodeModuleTask downloadJSDocTask) {

		Project project = downloadJSDocTask.getProject();

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			JSDocTask.class,
			new Action<JSDocTask>() {

				@Override
				public void execute(JSDocTask jsdocTask) {
					_configureTaskJSDoc(jsdocTask, downloadJSDocTask);
				}

			});
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

	private static final String _VERSION = "3.5.5";

}