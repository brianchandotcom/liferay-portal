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

package com.liferay.gradle.plugins.tld.formatter;

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
public class TLDFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "tldFormatter";

	public static final String FORMAT_TLD_TASK_NAME = "formatTLD";

	@Override
	public void apply(Project project) {
		Configuration tldFormatterConfiguration = addConfigurationTLDFormatter(
			project);

		addTaskFormatTLD(project);

		configureTasksFormatTLD(project, tldFormatterConfiguration);
	}

	protected Configuration addConfigurationTLDFormatter(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay TLD Formatter for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesTLDFormatter(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesTLDFormatter(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.tld.formatter", "latest.release");
	}

	protected FormatTLDTask addTaskFormatTLD(Project project) {
		FormatTLDTask formatTLDTask = GradleUtil.addTask(
			project, FORMAT_TLD_TASK_NAME, FormatTLDTask.class);

		formatTLDTask.setDescription(
			"Runs Liferay TLD Formatter to format files.");

		return formatTLDTask;
	}

	protected void configureTaskFormatTLDClasspath(
		FormatTLDTask formatTLDTask, FileCollection fileCollection) {

		formatTLDTask.setClasspath(fileCollection);
	}

	protected void configureTasksFormatTLD(
		Project project, final Configuration tldFormatterConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatTLDTask.class,
			new Action<FormatTLDTask>() {

				@Override
				public void execute(FormatTLDTask formatTLDTask) {
					configureTaskFormatTLDClasspath(
						formatTLDTask, tldFormatterConfiguration);
				}

			});
	}

}