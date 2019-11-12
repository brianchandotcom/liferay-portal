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

package com.liferay.gradle.plugins.soy;

import com.liferay.gradle.plugins.soy.internal.SoyPluginConstants;
import com.liferay.gradle.plugins.soy.internal.util.GradleUtil;
import com.liferay.gradle.plugins.soy.tasks.BuildSoyTask;
import com.liferay.gradle.plugins.soy.tasks.WrapSoyAlloyTemplateTask;

import java.io.File;

import java.util.Collections;
import java.util.Iterator;
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
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class SoyPlugin implements Plugin<Project> {

	public static final String BUILD_SOY_TASK_NAME = "buildSoy";

	public static final String CONFIGURATION_NAME = "soy";

	public static final String WRAP_SOY_ALLOY_TEMPLATE_TASK_NAME =
		"wrapSoyAlloyTemplate";

	@Override
	public void apply(Project project) {
		Configuration soyConfiguration = _addConfigurationSoy(project);

		_addTaskBuildSoy(project);
		_addTaskWrapSoyAlloyTemplate(project);

		_configureTasksBuildSoy(project, soyConfiguration);
	}

	private Configuration _addConfigurationSoy(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesSoy(project);
				}

			});

		configuration.setDescription(
			"Configures Closure Templates for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesSoy(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.google.template", "soy",
			_VERSION);
	}

	private BuildSoyTask _addTaskBuildSoy(Project project) {
		final BuildSoyTask buildSoyTask = GradleUtil.addTask(
			project, BUILD_SOY_TASK_NAME, BuildSoyTask.class);

		buildSoyTask.setDescription(
			"Compiles Closure Templates into JavaScript functions.");
		buildSoyTask.setGroup(BasePlugin.BUILD_GROUP);
		buildSoyTask.setIncludes(Collections.singleton("**/*.soy"));

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskBuildSoyForJavaPlugin(buildSoyTask);
				}

			});

		return buildSoyTask;
	}

	@SuppressWarnings("rawtypes")
	private WrapSoyAlloyTemplateTask _addTaskWrapSoyAlloyTemplate(
		Project project) {

		final WrapSoyAlloyTemplateTask wrapSoyAlloyTemplateTask =
			GradleUtil.addTask(
				project, WRAP_SOY_ALLOY_TEMPLATE_TASK_NAME,
				WrapSoyAlloyTemplateTask.class);

		wrapSoyAlloyTemplateTask.setDescription(
			"Wraps the Javascript functions compiled from Closure Templates " +
				"into AlloyUI modules.");
		wrapSoyAlloyTemplateTask.setEnabled(false);
		wrapSoyAlloyTemplateTask.setIncludes(
			Collections.singleton("**/*.soy.js"));

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withId(
			SoyPluginConstants.JS_MODULE_CONFIG_GENERATOR_PLUGIN_ID,
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					wrapSoyAlloyTemplateTask.dependsOn(
						SoyPluginConstants.CONFIG_JS_MODULES_TASK_NAME);
				}

			});

		pluginContainer.withId(
			SoyPluginConstants.JS_TRANSPILER_PLUGIN_ID,
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					wrapSoyAlloyTemplateTask.dependsOn(
						SoyPluginConstants.TRANSPILE_JS_TASK_NAME);
				}

			});

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskWrapSoyAlloyTemplateForJavaPlugin(
						wrapSoyAlloyTemplateTask);
				}

			});

		return wrapSoyAlloyTemplateTask;
	}

	private void _configureTaskBuildSoyClasspath(
		BuildSoyTask buildSoyTask, FileCollection fileCollection) {

		buildSoyTask.setClasspath(fileCollection);
	}

	private void _configureTaskBuildSoyForJavaPlugin(
		final BuildSoyTask buildSoyTask) {

		buildSoyTask.setSource(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getResourcesDir(buildSoyTask.getProject());
				}

			});
	}

	private void _configureTasksBuildSoy(
		Project project, final Configuration soyConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildSoyTask.class,
			new Action<BuildSoyTask>() {

				@Override
				public void execute(BuildSoyTask buildSoyTask) {
					_configureTaskBuildSoyClasspath(
						buildSoyTask, soyConfiguration);
				}

			});
	}

	private void _configureTaskWrapSoyAlloyTemplateForJavaPlugin(
		final WrapSoyAlloyTemplateTask wrapSoyAlloyTemplateTask) {

		wrapSoyAlloyTemplateTask.dependsOn(
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		wrapSoyAlloyTemplateTask.setSource(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						wrapSoyAlloyTemplateTask.getProject(),
						SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getResourcesDir();
				}

			});

		Task classesTask = GradleUtil.getTask(
			wrapSoyAlloyTemplateTask.getProject(),
			JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(wrapSoyAlloyTemplateTask);
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

	private static final String _VERSION = "2015-04-10";

}