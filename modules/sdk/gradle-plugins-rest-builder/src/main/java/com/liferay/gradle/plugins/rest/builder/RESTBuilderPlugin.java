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

package com.liferay.gradle.plugins.rest.builder;

import com.liferay.gradle.plugins.rest.builder.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class RESTBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_REST_TASK_NAME = "buildREST";

	public static final String CONFIGURATION_NAME = "restBuilder";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		Configuration restBuilderConfiguration = _addConfigurationRESTBuilder(
			project);

		final BuildRESTTask buildRESTTask = _addTaskBuildREST(project);

		_configureTasksBuildREST(project, restBuilderConfiguration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskBuildREST(buildRESTTask);
				}

			});
	}

	private Configuration _addConfigurationRESTBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesRESTBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay REST Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesRESTBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.rest.builder", "latest.release");
	}

	private BuildRESTTask _addTaskBuildREST(Project project) {
		BuildRESTTask buildRESTTask = GradleUtil.addTask(
			project, BUILD_REST_TASK_NAME, BuildRESTTask.class);

		buildRESTTask.setDescription("Runs Liferay REST Builder.");
		buildRESTTask.setGroup(BasePlugin.BUILD_GROUP);
		buildRESTTask.setRESTConfigDir(project.getProjectDir());

		return buildRESTTask;
	}

	@SuppressWarnings("rawtypes")
	private void _configureTaskBuildREST(final BuildRESTTask buildRESTTask) {
		Project project = buildRESTTask.getProject();

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withId(
			"com.liferay.defaults.plugin",
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					_configureTaskBuildRESTForLiferayDefaultsPlugin(
						buildRESTTask);
				}

			});
	}

	private void _configureTaskBuildRESTClasspath(
		BuildRESTTask buildRESTTask, Configuration restBuilderConfiguration) {

		buildRESTTask.setClasspath(restBuilderConfiguration);
	}

	private void _configureTaskBuildRESTForLiferayDefaultsPlugin(
		BuildRESTTask buildRESTTask) {

		File restConfigDir = buildRESTTask.getRESTConfigDir();

		if ((restConfigDir == null) || !restConfigDir.exists()) {
			return;
		}

		File restConfigYAMLFile = new File(restConfigDir, "rest-config.yaml");

		if (!restConfigYAMLFile.exists()) {
			return;
		}

		Project project = buildRESTTask.getProject();

		String content = null;

		try {
			content = new String(
				Files.readAllBytes(restConfigYAMLFile.toPath()),
				StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			Logger logger = project.getLogger();

			if (logger.isWarnEnabled()) {
				logger.warn("Unable to read {}", restConfigYAMLFile);
			}
		}

		if (Validator.isNull(content)) {
			return;
		}

		Matcher matcher = _apiDirPattern.matcher(content);

		if (!matcher.find()) {
			return;
		}

		File apiProjectDir = GradleUtil.getRootDir(
			project.file(matcher.group(1)), "bnd.bnd");

		if (apiProjectDir == null) {
			return;
		}

		Project rootProject = project.getRootProject();

		String relativePath = FileUtil.relativize(
			apiProjectDir, rootProject.getProjectDir());

		relativePath = relativePath.replace(File.separatorChar, '/');

		String apiProjectPath = ':' + relativePath.replace('/', ':');

		Project apiProject = rootProject.findProject(apiProjectPath);

		if (apiProject == null) {
			String apiProjectName = apiProjectDir.getName();

			apiProject = GradleUtil.findProject(rootProject, apiProjectName);
		}

		if (apiProject == null) {
			return;
		}

		buildRESTTask.finalizedBy(apiProject.getPath() + ":baseline");
	}

	private void _configureTasksBuildREST(
		Project project, final Configuration restBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildRESTTask.class,
			new Action<BuildRESTTask>() {

				@Override
				public void execute(BuildRESTTask buildRESTTask) {
					_configureTaskBuildRESTClasspath(
						buildRESTTask, restBuilderConfiguration);
				}

			});
	}

	private static final Pattern _apiDirPattern = Pattern.compile(
		"^apiDir:\\s*\"(.+)\"", Pattern.MULTILINE);

}