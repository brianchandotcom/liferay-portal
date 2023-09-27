/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.gradle.api.plugins.JavaLibraryPlugin;
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
		final JSDocTask jsDocTask = GradleUtil.addTask(
			project, JSDOC_TASK_NAME, JSDocTask.class);

		jsDocTask.setDescription(
			"Generates the API documentation for the JavaScript code in this " +
				"project.");

		jsDocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					Project project = jsDocTask.getProject();

					return new File(project.getBuildDir(), "jsdoc");
				}

			});

		jsDocTask.setGroup(JavaBasePlugin.DOCUMENTATION_GROUP);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaLibraryPlugin.class,
			new Action<JavaLibraryPlugin>() {

				@Override
				public void execute(JavaLibraryPlugin javaLibraryPlugin) {
					_configureTaskJSDocForJavaLibraryPlugin(jsDocTask);
				}

			});

		return jsDocTask;
	}

	private void _configureTaskJSDocForJavaLibraryPlugin(JSDocTask jsDocTask) {
		final Project project = jsDocTask.getProject();

		jsDocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					JavaPluginConvention javaPluginConvention =
						GradleUtil.getConvention(
							project, JavaPluginConvention.class);

					return new File(javaPluginConvention.getDocsDir(), "jsdoc");
				}

			});

		jsDocTask.setSourceDirs(
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