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

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class JSDocPlugin extends BaseJSDocPlugin {

	public static final String JSDOC_TASK_NAME = "jsdoc";

	@Override
	public void apply(Project project) {
		super.apply(project);

		_addTaskJSDoc(project);
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

		jsdocTask.setSourceDirs(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						_getResourcesDir(project), "META-INF/resources");
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

}