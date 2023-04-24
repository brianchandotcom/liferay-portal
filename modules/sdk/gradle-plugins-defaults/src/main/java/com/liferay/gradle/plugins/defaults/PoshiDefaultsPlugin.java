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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.testray.TestrayPlugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiDefaultsPlugin implements Plugin<Project> {

	public static final String FORMAT_POSHI_TASK_NAME = "formatPoshi";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, PoshiRunnerDefaultsPlugin.class);
		GradleUtil.applyPlugin(
			project, PoshiRunnerResourcesDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, TestrayPlugin.class);

		_addTaskFormatPoshi(project);
	}

	private FormatSourceTask _addTaskFormatPoshi(Project project) {
		FormatSourceTask formatSourceTask = GradleUtil.addTask(
			project, FORMAT_POSHI_TASK_NAME, FormatSourceTask.class);

		formatSourceTask.setDescription(
			"Runs Liferay Source Formatter to format Poshi files.");
		formatSourceTask.setFileExtensions(
			new String[] {"function", "macro", "path", "testcase"});
		formatSourceTask.setGroup("formatting");

		return formatSourceTask;
	}

}