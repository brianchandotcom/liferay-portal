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

package com.liferay.gradle.plugins.lang.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_LANG_TASK_NAME = "buildLang";

	public static final String CONFIGURATION_NAME = "langBuilder";

	@Override
	public void apply(Project project) {
		Configuration langBuilderConfiguration = addConfigurationLangBuilder(
			project);

		addTaskBuildLang(project);

		configureTasksBuildLang(project, langBuilderConfiguration);
	}

	protected Configuration addConfigurationLangBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Lang Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesLangBuilder(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesLangBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.lang.builder", "latest.release");
	}

	protected BuildLangTask addTaskBuildLang(Project project) {
		final BuildLangTask buildLangTask = GradleUtil.addTask(
			project, BUILD_LANG_TASK_NAME, BuildLangTask.class);

		buildLangTask.setDescription(
			"Runs Liferay Lang Builder to translate language property files.");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskBuildLangForJavaPlugin(buildLangTask);
				}

			});

		return buildLangTask;
	}

	protected void configureTaskBuildLangClasspath(
		BuildLangTask buildLangTask, FileCollection fileCollection) {

		buildLangTask.setClasspath(fileCollection);
	}

	protected void configureTaskBuildLangForJavaPlugin(
		final BuildLangTask buildLangTask) {

		buildLangTask.setLangDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getResourcesDir(buildLangTask.getProject()), "content");
				}

			});
	}

	protected void configureTasksBuildLang(
		Project project, final Configuration langBuilderConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildLangTask.class,
			new Action<BuildLangTask>() {

				@Override
				public void execute(BuildLangTask buildLangTask) {
					configureTaskBuildLangClasspath(
						buildLangTask, langBuilderConfiguration);
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

}