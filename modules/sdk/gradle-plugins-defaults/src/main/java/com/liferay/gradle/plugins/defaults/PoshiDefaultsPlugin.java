/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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