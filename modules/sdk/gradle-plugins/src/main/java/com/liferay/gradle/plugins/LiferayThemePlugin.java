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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.gulp.GulpPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ConfigurablePublishArtifact;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemePlugin implements Plugin<Project> {

	public static final String CREATE_LIFERAY_THEME_JSON_TASK_NAME =
		"createLiferayThemeJson";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);
		GradleUtil.applyPlugin(project, GulpPlugin.class);
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		Map<String, Object> packageJson = getPackageJson(project);

		configureArchivesBaseName(project, packageJson);
		configureVersion(project, packageJson);

		// liferay-theme-tasks already uses the "build" directory

		project.setBuildDir("build_gradle");

		Task createLiferayThemeJsonTask = addTaskCreateLiferayThemeJson(
			project, liferayExtension);

		configureArtifacts(project);
		configureTaskClean(project);
		configureTaskDeploy(project);
		configureTasksExecuteGulp(project, createLiferayThemeJsonTask);
	}

	protected Task addTaskCreateLiferayThemeJson(
		Project project, final LiferayExtension liferayExtension) {

		Task task = project.task(CREATE_LIFERAY_THEME_JSON_TASK_NAME);

		final File liferayThemeJsonFile = project.file("liferay-theme.json");

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Map<String, Object> map = new HashMap<>();

					map.put(
						"appServerPath",
						FileUtil.getAbsolutePath(
							liferayExtension.getAppServerDir()));

					File appServerThemeDir = new File(
						liferayExtension.getAppServerDeployDir(),
						project.getName());

					map.put(
						"appServerPathTheme",
						FileUtil.getAbsolutePath(appServerThemeDir));

					map.put("deployed", false);

					map.put(
						"deployPath",
						FileUtil.getAbsolutePath(
							liferayExtension.getDeployDir()));
					map.put("themeName", project.getName());

					String json = JsonOutput.toJson(
						Collections.singletonMap("LiferayTheme", map));

					try {
						Files.write(
							liferayThemeJsonFile.toPath(),
							json.getBytes(StandardCharsets.UTF_8));
					}
					catch (IOException ioe) {
						throw new GradleException(
							"Unable to write " + liferayThemeJsonFile, ioe);
					}
				}

			});

		task.setDescription(
			"Generates the " + liferayThemeJsonFile.getName() +
				" file for this project.");

		return task;
	}

	protected void configureArchivesBaseName(
		Project project, Map<String, Object> packageJson) {

		String name = (String)packageJson.get("name");

		if (Validator.isNull(name)) {
			return;
		}

		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		basePluginConvention.setArchivesBaseName(name);
	}

	protected void configureArtifacts(final Project project) {
		ArtifactHandler artifacts = project.getArtifacts();

		File warFile = getWarFile(project);

		artifacts.add(
			Dependency.ARCHIVES_CONFIGURATION, warFile,
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(
					ConfigurablePublishArtifact configurablePublishArtifact) {

					Task gulpBuildTask = GradleUtil.getTask(
						project, GULP_BUILD_TASK_NAME);

					configurablePublishArtifact.builtBy(gulpBuildTask);
				}

			});
	}

	protected void configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
	}

	protected void configureTaskDeploy(Project project) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME);

		copy.dependsOn(BasePlugin.ASSEMBLE_TASK_NAME);
		copy.from(getWarFile(project));
	}

	protected void configureTaskExecuteGulp(
		ExecuteGulpTask executeGulpTask, Task createLiferayThemeJsonTask) {

		executeGulpTask.dependsOn(
			createLiferayThemeJsonTask, NodePlugin.NPM_INSTALL_TASK_NAME);
	}

	protected void configureTasksExecuteGulp(
		Project project, final Task createLiferayThemeJsonTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGulpTask.class,
			new Action<ExecuteGulpTask>() {

				@Override
				public void execute(ExecuteGulpTask executeGulpTask) {
					configureTaskExecuteGulp(
						executeGulpTask, createLiferayThemeJsonTask);
				}

			});
	}

	protected void configureVersion(
		Project project, Map<String, Object> packageJson) {

		String version = (String)packageJson.get("version");

		if (Validator.isNotNull(version)) {
			project.setVersion(version);
		}
	}

	protected Map<String, Object> getPackageJson(Project project) {
		File file = project.file("package.json");

		if (!file.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(file);
	}

	protected File getWarFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + ".war");
	}

	protected static final String GULP_BUILD_TASK_NAME = "gulpBuild";

}