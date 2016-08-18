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

package com.liferay.gradle.plugins.xml.formatter;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class XMLFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "xmlFormatter";

	public static final String FORMAT_XML_TASK_NAME = "formatXML";

	@Override
	public void apply(Project project) {
		Configuration xmlFormatterConfiguration = _addConfigurationXMLFormatter(
			project);

		_addTaskFormatXML(project);

		_configureTasksFormatXML(project, xmlFormatterConfiguration);
	}

	private Configuration _addConfigurationXMLFormatter(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesXMLFormatter(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay XML Formatter for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesXMLFormatter(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.xml.formatter", "latest.release");
	}

	private FormatXMLTask _addTaskFormatXML(Project project) {
		final FormatXMLTask formatXMLTask = GradleUtil.addTask(
			project, FORMAT_XML_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format the project files.");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskFormatXMLForJavaPlugin(formatXMLTask);
				}

			});

		return formatXMLTask;
	}

	private void _configureTaskFormatXML(
		FormatXMLTask formatXMLTask, FileCollection classpath) {

		formatXMLTask.setClasspath(classpath);
	}

	private void _configureTaskFormatXMLForJavaPlugin(
		FormatXMLTask formatXMLTask) {

		formatXMLTask.setIncludes(Collections.singleton("**/*.xml"));

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			formatXMLTask.getProject(), SourceSet.MAIN_SOURCE_SET_NAME);

		formatXMLTask.setSource(
			new Callable<Iterable<File>>() {

				@Override
				public Iterable<File> call() throws Exception {
					SourceDirectorySet sourceDirectorySet =
						sourceSet.getResources();

					return sourceDirectorySet.getSrcDirs();
				}

			});
	}

	private void _configureTasksFormatXML(
		Project project, final FileCollection classpath) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatXMLTask.class,
			new Action<FormatXMLTask>() {

				@Override
				public void execute(FormatXMLTask formatXMLTask) {
					_configureTaskFormatXML(formatXMLTask, classpath);
				}

			});
	}

}