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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.LiferayThemePlugin;
import com.liferay.gradle.plugins.defaults.LiferayThemeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.extensions.LiferayThemeDefaultsExtension;
import com.liferay.gradle.plugins.defaults.internal.util.FileUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.gulp.GulpPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.task.PublishNodeModuleTask;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ConfigurablePublishArtifact;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemeDefaultsGulpPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new LiferayThemeDefaultsGulpPlugin();

	public static final String PUBLISH_NODE_MODULE_TASK_NAME =
		"publishNodeModule";

	@Override
	public void apply(final Project project) {

		// Containers

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			LiferayThemeDefaultsPlugin.class,
			new Action<LiferayThemeDefaultsPlugin>() {

				@Override
				public void execute(
					LiferayThemeDefaultsPlugin liferayThemeDefaultsPlugin) {

					_applyPluginDefaults(project);
				}

			});
	}

	private LiferayThemeDefaultsGulpPlugin() {
	}

	private PublishNodeModuleTask _addTaskPublishNodeModule(
		Task zipResourcesImporterArchivesTask) {

		PublishNodeModuleTask publishNodeModuleTask = GradleUtil.addTask(
			zipResourcesImporterArchivesTask.getProject(),
			PUBLISH_NODE_MODULE_TASK_NAME, PublishNodeModuleTask.class);

		publishNodeModuleTask.dependsOn(zipResourcesImporterArchivesTask);
		publishNodeModuleTask.setDescription(
			"Publishes this project to the NPM registry.");
		publishNodeModuleTask.setGroup(BasePlugin.UPLOAD_GROUP);

		return publishNodeModuleTask;
	}

	private void _applyPluginDefaults(Project project) {
		GradleUtil.applyPlugin(project, GulpPlugin.class);

		final LiferayThemeDefaultsExtension liferayThemeDefaultsExtension =
			GradleUtil.getExtension(
				project, LiferayThemeDefaultsExtension.class);

		Task createLiferayThemeJsonTask = GradleUtil.getTask(
			project, LiferayThemePlugin.CREATE_LIFERAY_THEME_JSON_TASK_NAME);
		final ExecuteGulpTask executeGulpTask =
			(ExecuteGulpTask)GradleUtil.getTask(project, _GULP_BUILD_TASK_NAME);
		final Copy expandFrontendCSSCommonTask = (Copy)GradleUtil.getTask(
			project,
			LiferayThemeDefaultsPlugin.EXPAND_FRONTEND_CSS_COMMON_TASK_NAME);
		Task zipResourcesImporterArchivesTask = GradleUtil.getTask(
			project,
			LiferayThemeDefaultsPlugin.
				ZIP_RESOURCES_IMPORTER_ARCHIVES_TASK_NAME);

		final PublishNodeModuleTask publishNodeModuleTask =
			_addTaskPublishNodeModule(zipResourcesImporterArchivesTask);

		_configureTasksExecuteGulp(
			project, createLiferayThemeJsonTask,
			zipResourcesImporterArchivesTask);

		ArtifactHandler artifacts = project.getArtifacts();

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION, _getWarFile(project),
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(
					ConfigurablePublishArtifact configurablePublishArtifact) {

					TaskContainer taskContainer = project.getTasks();

					Task packageRunBuildTask = taskContainer.findByName(
						NodePlugin.PACKAGE_RUN_BUILD_TASK_NAME);

					if (packageRunBuildTask != null) {
						executeGulpTask.finalizedBy(packageRunBuildTask);
					}

					configurablePublishArtifact.builtBy(executeGulpTask);
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					if (liferayThemeDefaultsExtension.
							isUseLocalDependencies()) {

						_configureTasksExecuteGulpLocalDependencies(
							project, expandFrontendCSSCommonTask);
					}

					_configureTaskUploadArchives(
						project, publishNodeModuleTask);
				}

			});
	}

	private void _configureTaskExecuteGulp(
		Task createLiferayThemeJsonTask, ExecuteGulpTask executeGulpTask,
		Task zipResourcesImporterArchivesTask) {

		executeGulpTask.args("--skip-update-check=true");
		executeGulpTask.dependsOn(createLiferayThemeJsonTask);
		executeGulpTask.dependsOn(zipResourcesImporterArchivesTask);
	}

	private void _configureTaskExecuteGulpLocalDependencies(
		ExecuteGulpTask executeGulpTask, Copy expandFrontendCSSCommonTask) {

		File cssCommonDir = expandFrontendCSSCommonTask.getDestinationDir();

		executeGulpTask.args(
			"--css-common-path=" + FileUtil.getAbsolutePath(cssCommonDir));

		executeGulpTask.dependsOn(expandFrontendCSSCommonTask);

		Project project = executeGulpTask.getProject();

		if (!GradlePluginsDefaultsUtil.isSubrepository(project)) {
			for (String themeProjectName :
					GradlePluginsDefaultsUtil.PARENT_THEME_PROJECT_NAMES) {

				int index = themeProjectName.lastIndexOf("-");

				_configureTaskExecuteGulpLocalDependenciesTheme(
					executeGulpTask,
					_getThemeProject(project, themeProjectName),
					themeProjectName.substring(index + 1));
			}
		}
	}

	private void _configureTaskExecuteGulpLocalDependenciesTheme(
		ExecuteGulpTask executeGulpTask, Project themeProject, String name) {

		if (themeProject == null) {
			return;
		}

		File dir = themeProject.file(
			"src/main/resources/META-INF/resources/_" + name);

		executeGulpTask.args(
			"--" + name + "-path=" + FileUtil.getAbsolutePath(dir));

		executeGulpTask.dependsOn(
			themeProject.getPath() + ":" + JavaPlugin.CLASSES_TASK_NAME);
	}

	private void _configureTasksExecuteGulp(
		Project project, final Task createLiferayThemeJsonTask,
		final Task zipResourcesImporterArchivesTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGulpTask.class,
			new Action<ExecuteGulpTask>() {

				@Override
				public void execute(ExecuteGulpTask executeGulpTask) {
					_configureTaskExecuteGulp(
						createLiferayThemeJsonTask, executeGulpTask,
						zipResourcesImporterArchivesTask);
				}

			});
	}

	private void _configureTasksExecuteGulpLocalDependencies(
		Project project, final Copy expandFrontendCSSCommonTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGulpTask.class,
			new Action<ExecuteGulpTask>() {

				@Override
				public void execute(ExecuteGulpTask executeGulpTask) {
					_configureTaskExecuteGulpLocalDependencies(
						executeGulpTask, expandFrontendCSSCommonTask);
				}

			});
	}

	private void _configureTaskUploadArchives(
		final Project project, PublishNodeModuleTask publishNodeModuleTask) {

		Task uploadArchivesTask = GradleUtil.getTask(
			project, BasePlugin.UPLOAD_ARCHIVES_TASK_NAME);

		if (FileUtil.exists(project, ".lfrbuild-missing-resources-importer")) {
			Action<Task> action = new Action<Task>() {

				@Override
				public void execute(Task task) {
					throw new GradleException(
						"Unable to publish " + project +
							", resources-importer directory is missing");
				}

			};

			publishNodeModuleTask.doFirst(action);
		}

		uploadArchivesTask.dependsOn(publishNodeModuleTask);
	}

	private Project _getThemeProject(Project project, String name) {
		Project parentProject = project.getParent();

		Project themeProject = parentProject.findProject(name);

		if (themeProject == null) {
			themeProject = GradleUtil.getProject(
				project.getRootProject(), name);
		}

		return themeProject;
	}

	private File _getWarFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".war");
	}

	private static final String _GULP_BUILD_TASK_NAME = "gulpBuild";

}