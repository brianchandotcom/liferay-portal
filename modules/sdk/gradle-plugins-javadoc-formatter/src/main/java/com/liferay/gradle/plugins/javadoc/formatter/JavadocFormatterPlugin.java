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

package com.liferay.gradle.plugins.javadoc.formatter;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "javadocFormatter";

	public static final String FORMAT_JAVADOC_TASK_NAME = "formatJavadoc";

	@Override
	public void apply(Project project) {
		Configuration javadocFormatterConfiguration =
			addConfigurationJavadocFormatter(project);

		addTaskFormatJavadoc(project);

		configureTasksFormatJavadoc(project, javadocFormatterConfiguration);
	}

	protected Configuration addConfigurationJavadocFormatter(
		final Project project) {

		final Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Javadoc Formatter for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesJavadocFormatter(project);
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureConfigurationJavadocFormatterForJavaPlugin(
						project, configuration);
				}

			});

		return configuration;
	}

	protected void addDependenciesJavadocFormatter(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.javadoc.formatter", "latest.release");
	}

	protected FormatJavadocTask addTaskFormatJavadoc(Project project) {
		FormatJavadocTask formatJavadocTask = GradleUtil.addTask(
			project, FORMAT_JAVADOC_TASK_NAME, FormatJavadocTask.class);

		formatJavadocTask.setDescription(
			"Runs Liferay Javadoc Formatter to format files.");

		return formatJavadocTask;
	}

	protected void configureConfigurationJavadocFormatterForJavaPlugin(
		Project project, Configuration configuration) {

		Configuration compileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

		configuration.extendsFrom(compileConfiguration);
	}

	protected void configureTaskFormatJavadocClasspath(
		FormatJavadocTask formatJavadocTask, FileCollection fileCollection) {

		formatJavadocTask.setClasspath(fileCollection);
	}

	protected void configureTasksFormatJavadoc(
		Project project, final Configuration javadocFormatterConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatJavadocTask.class,
			new Action<FormatJavadocTask>() {

				@Override
				public void execute(FormatJavadocTask formatJavadoc) {
					configureTaskFormatJavadocClasspath(
						formatJavadoc, javadocFormatterConfiguration);
				}

			});
	}

}