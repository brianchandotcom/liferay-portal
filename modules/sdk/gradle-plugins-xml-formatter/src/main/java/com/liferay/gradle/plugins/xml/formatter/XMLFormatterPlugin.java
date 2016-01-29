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

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class XMLFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "xmlFormatter";

	public static final String FORMAT_XML_TASK_NAME = "formatXML";

	@Override
	public void apply(Project project) {
		Configuration xmlFormatterConfiguration = addConfigurationXMLFormatter(
			project);

		addTaskFormatXML(project);

		configureTasksFormatXML(project, xmlFormatterConfiguration);
	}

	protected Configuration addConfigurationXMLFormatter(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay XML Formatter for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesXMLFormatter(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesXMLFormatter(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.xml.formatter", "latest.release");
	}

	protected FormatXMLTask addTaskFormatXML(Project project) {
		FormatXMLTask formatXMLTask = GradleUtil.addTask(
			project, FORMAT_XML_TASK_NAME, FormatXMLTask.class);

		formatXMLTask.setDescription(
			"Runs Liferay XML Formatter to format files.");

		return formatXMLTask;
	}

	protected void configureTaskFormatXMLClasspath(
		FormatXMLTask formatXMLTask, FileCollection fileCollection) {

		formatXMLTask.setClasspath(fileCollection);
	}

	protected void configureTasksFormatXML(
		Project project, final Configuration xmlFormatterConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatXMLTask.class,
			new Action<FormatXMLTask>() {

				@Override
				public void execute(FormatXMLTask formatXMLTask) {
					configureTaskFormatXMLClasspath(
						formatXMLTask, xmlFormatterConfiguration);
				}

			});
	}

}