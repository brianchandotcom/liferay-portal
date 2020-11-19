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

package com.liferay.gradle.plugins.workspace.internal;

import com.liferay.gradle.plugins.LiferayThemePlugin;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.gulp.GulpPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ConfigurablePublishArtifact;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemeGulpPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE = new LiferayThemeGulpPlugin();

	@Override
	public void apply(final Project project) {

		// Containers

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			LiferayThemePlugin.class,
			new Action<LiferayThemePlugin>() {

				@Override
				public void execute(LiferayThemePlugin liferayThemePlugin) {
					_applyPluginDefaults(project);
				}

			});
	}

	private LiferayThemeGulpPlugin() {
	}

	private void _applyPluginDefaults(Project project) {

		// Plugins

		GradleUtil.applyPlugin(project, GulpPlugin.class);

		// Tasks

		final TaskProvider<Task> createLiferayThemeJsonTaskProvider =
			GradleUtil.getTaskProvider(
				project,
				LiferayThemePlugin.CREATE_LIFERAY_THEME_JSON_TASK_NAME);
		final TaskProvider<Task> gulpBuildTaskProvider =
			GradleUtil.getTaskProvider(project, _GULP_BUILD_TASK_NAME);

		// Containers

		final TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGulpTask.class,
			new Action<ExecuteGulpTask>() {

				@Override
				public void execute(ExecuteGulpTask executeGulpTask) {
					_configureTaskExecuteGulp(
						createLiferayThemeJsonTaskProvider, executeGulpTask);
				}

			});

		// Other

		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION, _getWarFile(project),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(
					ConfigurablePublishArtifact configurablePublishArtifact) {

					Task packageRunBuildTask = taskContainer.findByName(
						NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);

					if (packageRunBuildTask != null) {
						gulpBuildTaskProvider.configure(
							new Action<Task>() {

								@Override
								public void execute(Task gulpBuildTask) {
									gulpBuildTask.finalizedBy(
										NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);
								}

							});
					}

					configurablePublishArtifact.builtBy(
						gulpBuildTaskProvider.get());
				}

			});
	}

	private void _configureTaskExecuteGulp(
		TaskProvider<Task> createLiferayThemeJsonTaskProvider,
		ExecuteGulpTask executeGulpTask) {

		executeGulpTask.dependsOn(createLiferayThemeJsonTaskProvider);
	}

	private File _getWarFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".war");
	}

	private static final String _GULP_BUILD_TASK_NAME = "gulpBuild";

}