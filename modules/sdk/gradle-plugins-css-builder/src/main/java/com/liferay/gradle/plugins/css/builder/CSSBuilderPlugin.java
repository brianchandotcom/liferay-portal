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

package com.liferay.gradle.plugins.css.builder;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_CSS_TASK_NAME = "buildCSS";

	public static final String CSS_BUILDER_CONFIGURATION_NAME = "cssBuilder";

	public static final String EXPAND_PORTAL_COMMON_CSS_TASK_NAME =
		"expandPortalCommonCSS";

	public static final String PORTAL_COMMON_CSS_CONFIGURATION_NAME =
		"portalCommonCSS";

	@Override
	public void apply(Project project) {
		addConfigurationCSSBuilder(project);
		addConfigurationPortalCommonCSS(project);

		addTaskBuildCSS(project);
		addTaskExpandPortalCommonCSS(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksBuildCSS(project);
				}

			});
	}

	protected Configuration addConfigurationCSSBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CSS_BUILDER_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay CSS Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesCSSBuilder(project);
				}

			});

		return configuration;
	}

	protected Configuration addConfigurationPortalCommonCSS(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, PORTAL_COMMON_CSS_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures com.liferay.frontend.common.css for compiling CSS " +
				"files.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesPortalCommonCSS(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesCSSBuilder(Project project) {
		GradleUtil.addDependency(
			project, CSS_BUILDER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.css.builder", "latest.release");
	}

	protected void addDependenciesPortalCommonCSS(Project project) {
		GradleUtil.addDependency(
			project, PORTAL_COMMON_CSS_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.frontend.common.css", "latest.release", false);
	}

	protected BuildCSSTask addTaskBuildCSS(Project project) {
		BuildCSSTask buildCSSTask = GradleUtil.addTask(
			project, BUILD_CSS_TASK_NAME, BuildCSSTask.class);

		buildCSSTask.setGroup(BasePlugin.BUILD_GROUP);
		buildCSSTask.setDescription("Build CSS files.");

		return buildCSSTask;
	}

	protected Copy addTaskExpandPortalCommonCSS(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, EXPAND_PORTAL_COMMON_CSS_TASK_NAME, Copy.class);

		copy.eachFile(new StripPathSegmentsAction(5));

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public FileTree doCall() {
				Configuration configuration = GradleUtil.getConfiguration(
					project, PORTAL_COMMON_CSS_CONFIGURATION_NAME);

				return project.zipTree(configuration.getSingleFile());
			}

		};

		copy.from(closure);

		copy.include("META-INF/resources/**");
		copy.into(new File(project.getBuildDir(), "portal-common-css"));
		copy.setIncludeEmptyDirs(false);

		return copy;
	}

	protected void configureTaskBuildCSS(BuildCSSTask buildCSSTask) {
		Project project = buildCSSTask.getProject();

		String portalCommonDirName = buildCSSTask.getPortalCommonDirName();

		if (Validator.isNotNull(portalCommonDirName)) {
			return;
		}

		Task expandPortalCommonCSSTask = GradleUtil.getTask(
			project, EXPAND_PORTAL_COMMON_CSS_TASK_NAME);

		FileCollection cssFiles = buildCSSTask.getCSSFiles();

		if (!cssFiles.isEmpty()) {
			buildCSSTask.dependsOn(expandPortalCommonCSSTask);
		}

		TaskOutputs taskOutputs = expandPortalCommonCSSTask.getOutputs();

		FileCollection fileCollection = taskOutputs.getFiles();

		buildCSSTask.setPortalCommonDirName(
			project.relativePath(fileCollection.getSingleFile()));
	}

	protected void configureTasksBuildCSS(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildCSSTask.class,
			new Action<BuildCSSTask>() {

				@Override
				public void execute(BuildCSSTask buildCSSTask) {
					configureTaskBuildCSS(buildCSSTask);
				}

			});
	}

}