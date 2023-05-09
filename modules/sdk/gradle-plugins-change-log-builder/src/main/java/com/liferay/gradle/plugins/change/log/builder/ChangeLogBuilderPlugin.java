/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;

/**
 * @author Andrea Di Giorgi
 */
public class ChangeLogBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_CHANGE_LOG_TASK_NAME = "buildChangeLog";

	@Override
	public void apply(Project project) {
		_addTaskBuildChangeLog(project);
	}

	private BuildChangeLogTask _addTaskBuildChangeLog(Project project) {
		final BuildChangeLogTask buildChangeLogTask = GradleUtil.addTask(
			project, BUILD_CHANGE_LOG_TASK_NAME, BuildChangeLogTask.class);

		buildChangeLogTask.setChangeLogHeader(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					Project project = buildChangeLogTask.getProject();

					return "Bundle Version " + project.getVersion();
				}

			});

		buildChangeLogTask.setChangeLogFile(_CHANGE_LOG_FILE_NAME);
		buildChangeLogTask.setDescription(
			"Builds the change log file for this project.");
		buildChangeLogTask.setDirs(project.getProjectDir());

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaLibraryPlugin.class,
			new Action<JavaLibraryPlugin>() {

				@Override
				public void execute(JavaLibraryPlugin javaLibraryPlugin) {
					_configureTaskBuildChangeLogForJavaLibraryPlugin(
						buildChangeLogTask);
				}

			});

		return buildChangeLogTask;
	}

	private void _configureTaskBuildChangeLogForJavaLibraryPlugin(
		final BuildChangeLogTask buildChangeLogTask) {

		buildChangeLogTask.setChangeLogFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = _getResourcesDir(
						buildChangeLogTask.getProject());

					return new File(
						resourcesDir, "META-INF/" + _CHANGE_LOG_FILE_NAME);
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

	private static final String _CHANGE_LOG_FILE_NAME =
		"liferay-releng.changelog";

}