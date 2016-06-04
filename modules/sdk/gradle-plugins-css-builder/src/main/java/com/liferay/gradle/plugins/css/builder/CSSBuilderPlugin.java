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

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_CSS_TASK_NAME = "buildCSS";

	public static final String CSS_BUILDER_CONFIGURATION_NAME = "cssBuilder";

	public static final String PORTAL_COMMON_CSS_CONFIGURATION_NAME =
		"portalCommonCSS";

	@Override
	public void apply(Project project) {
		Configuration cssBuilderConfiguration = addConfigurationCSSBuilder(
			project);
		Configuration portalCommonCSSConfiguration =
			addConfigurationPortalCommonCSS(project);

		addTaskBuildCSS(project);

		configureTasksBuildCSS(
			project, cssBuilderConfiguration, portalCommonCSSConfiguration);
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
			"Configures com.liferay.frontend.css.common for compiling CSS " +
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
			"com.liferay.frontend.css.common", "latest.release", false);
	}

	protected BuildCSSTask addTaskBuildCSS(Project project) {
		final BuildCSSTask buildCSSTask = GradleUtil.addTask(
			project, BUILD_CSS_TASK_NAME, BuildCSSTask.class);

		buildCSSTask.setDescription("Build CSS files.");
		buildCSSTask.setGroup(BasePlugin.BUILD_GROUP);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskBuildCSSForJavaPlugin(buildCSSTask);
				}

			});

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					configureTaskBuildCSSForWarPlugin(buildCSSTask);
				}

			});

		return buildCSSTask;
	}

	protected void configureTaskBuildCSSClasspath(
		BuildCSSTask buildCSSTask, FileCollection classpath) {

		buildCSSTask.setClasspath(classpath);
	}

	protected void configureTaskBuildCSSForJavaPlugin(
		final BuildCSSTask buildCSSTask) {

		buildCSSTask.setDocrootDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getResourcesDir(buildCSSTask.getProject());
				}

			});

		Task processResourcesTask = GradleUtil.getTask(
			buildCSSTask.getProject(), JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		processResourcesTask.dependsOn(buildCSSTask);
	}

	protected void configureTaskBuildCSSForWarPlugin(
		final BuildCSSTask buildCSSTask) {

		buildCSSTask.setDocrootDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getWebAppDir(buildCSSTask.getProject());
				}

			});
	}

	protected void configureTaskBuildCSSPortalCommonFile(
		BuildCSSTask buildCSSTask,
		final Configuration portalCommonCSSConfiguration) {

		buildCSSTask.setPortalCommonFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return portalCommonCSSConfiguration.getSingleFile();
				}

			});
	}

	protected void configureTasksBuildCSS(
		Project project, final Configuration cssBuilderConfiguration,
		final Configuration portalCommonCSSConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildCSSTask.class,
			new Action<BuildCSSTask>() {

				@Override
				public void execute(BuildCSSTask buildCSSTask) {
					configureTaskBuildCSSClasspath(
						buildCSSTask, cssBuilderConfiguration);
					configureTaskBuildCSSPortalCommonFile(
						buildCSSTask, portalCommonCSSConfiguration);
				}

			});
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getResources());
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	protected File getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

}