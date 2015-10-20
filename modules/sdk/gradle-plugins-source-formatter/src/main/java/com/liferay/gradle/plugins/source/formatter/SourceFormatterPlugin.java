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

package com.liferay.gradle.plugins.source.formatter;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
public class SourceFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "sourceFormatter";

	public static final String FORMAT_SOURCE_TASK_NAME = "formatSource";

	@Override
	public void apply(Project project) {
		addSourceFormatterConfiguration(project);

		addFormatSourceTask(project);
	}

	protected FormatSourceTask addFormatSourceTask(Project project) {
		FormatSourceTask formatSourceTask = GradleUtil.addTask(
			project, FORMAT_SOURCE_TASK_NAME, FormatSourceTask.class);

		formatSourceTask.setDescription(
			"Runs Liferay Source Formatter to format files.");

		return formatSourceTask;
	}

	protected Configuration addSourceFormatterConfiguration(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Source Formatter for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addSourceFormatterDependencies(project);
				}

			});

		return configuration;
	}

	protected void addSourceFormatterDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.source.formatter", "latest.release");
	}

}