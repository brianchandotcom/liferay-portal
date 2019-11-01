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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
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
		Configuration cssBuilderConfiguration = _addConfigurationCSSBuilder(
			project);
		Configuration portalCommonCSSConfiguration =
			_addConfigurationPortalCommonCSS(project);

		final BuildCSSTask buildCSSTask = _addTaskBuildCSS(project);

		_configureTasksBuildCSS(
			project, cssBuilderConfiguration, portalCommonCSSConfiguration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTaskBuildCSSJvmArgs(buildCSSTask);
				}

			});
	}

	private Configuration _addConfigurationCSSBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CSS_BUILDER_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesCSSBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay CSS Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private Configuration _addConfigurationPortalCommonCSS(
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
					_addDependenciesPortalCommonCSS(project);
				}

			});

		return configuration;
	}

	private void _addDependenciesCSSBuilder(Project project) {
		GradleUtil.addDependency(
			project, CSS_BUILDER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.css.builder", "latest.release");
	}

	private void _addDependenciesPortalCommonCSS(Project project) {
		GradleUtil.addDependency(
			project, PORTAL_COMMON_CSS_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.frontend.css.common", "latest.release", false);
		GradleUtil.addDependency(
			project, PORTAL_COMMON_CSS_CONFIGURATION_NAME, "org.webjars",
			"font-awesome", "latest.release", false);
	}

	private BuildCSSTask _addTaskBuildCSS(Project project) {
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
					_configureTaskBuildCSSForJavaPlugin(buildCSSTask);
				}

			});

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					_configureTaskBuildCSSForWarPlugin(buildCSSTask);
				}

			});

		return buildCSSTask;
	}

	private void _configureTaskBuildCSSClasspath(
		BuildCSSTask buildCSSTask, FileCollection classpath) {

		buildCSSTask.setClasspath(classpath);
	}

	private void _configureTaskBuildCSSForJavaPlugin(
		final BuildCSSTask buildCSSTask) {

		buildCSSTask.setBaseDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getResourcesDir(buildCSSTask.getProject());
				}

			});

		Task processResourcesTask = GradleUtil.getTask(
			buildCSSTask.getProject(), JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		processResourcesTask.dependsOn(buildCSSTask);
	}

	private void _configureTaskBuildCSSForWarPlugin(
		final BuildCSSTask buildCSSTask) {

		buildCSSTask.setBaseDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getWebAppDir(buildCSSTask.getProject());
				}

			});
	}

	private void _configureTaskBuildCSSImportFile(
		BuildCSSTask buildCSSTask,
		final Configuration portalCommonCSSConfiguration) {

		buildCSSTask.setImports(
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					return portalCommonCSSConfiguration;
				}

			});
	}

	private void _configureTaskBuildCSSJvmArgs(BuildCSSTask buildCSSTask) {
		if (Objects.equals("ruby", buildCSSTask.getSassCompilerClassName())) {
			buildCSSTask.jvmArgs("-Xss4096k");
		}
	}

	private void _configureTasksBuildCSS(
		Project project, final Configuration cssBuilderConfiguration,
		final Configuration portalCommonCSSConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildCSSTask.class,
			new Action<BuildCSSTask>() {

				@Override
				public void execute(BuildCSSTask buildCSSTask) {
					_configureTaskBuildCSSClasspath(
						buildCSSTask, cssBuilderConfiguration);
					_configureTaskBuildCSSImportFile(
						buildCSSTask, portalCommonCSSConfiguration);
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

	private File _getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

}