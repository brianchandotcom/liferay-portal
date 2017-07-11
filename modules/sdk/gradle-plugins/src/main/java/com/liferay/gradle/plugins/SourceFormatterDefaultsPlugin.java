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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 * @author Hugo Huijser
 */
public class SourceFormatterDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<SourceFormatterPlugin> {

	public static final Plugin<Project> INSTANCE =
		new SourceFormatterDefaultsPlugin();

	@Override
	protected void configureDefaults(
		Project project, SourceFormatterPlugin sourceFormatterPlugin) {

		super.configureDefaults(project, sourceFormatterPlugin);

		_configureTasksFormatSource(project);
	}

	@Override
	protected Class<SourceFormatterPlugin> getPluginClass() {
		return SourceFormatterPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return SourceFormatterPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private SourceFormatterDefaultsPlugin() {
	}

	private void _configureTasksFormatSource(
		FormatSourceTask formatSourceTask) {

		Project project = formatSourceTask.getProject();

		String gitWorkingBranchName = GradleUtil.getProperty(
			project, "git.working.branch.name", (String)null);

		if (Validator.isNotNull(gitWorkingBranchName)) {
			formatSourceTask.setGitWorkingBranchName(gitWorkingBranchName);
		}

		String includeSubrepositories = GradleUtil.getProperty(
			project, "source.formatter.include.subrepositories", (String)null);

		if (Validator.isNotNull(includeSubrepositories)) {
			formatSourceTask.setIncludeSubrepositories(
				Boolean.parseBoolean(includeSubrepositories));
		}

		String maxLineLength = GradleUtil.getProperty(
			project, "source.formatter.max.line.length", (String)null);

		if (Validator.isNotNull(maxLineLength)) {
			formatSourceTask.setMaxLineLength(Integer.parseInt(maxLineLength));
		}

		String showDocumentation = GradleUtil.getProperty(
			project, "source.formatter.show.documentation", (String)null);

		if (Validator.isNotNull(showDocumentation)) {
			formatSourceTask.setShowDocumentation(
				Boolean.parseBoolean(showDocumentation));
		}

		String processorThreadCount = GradleUtil.getProperty(
			project, "source.formatter.processor.thread.count", (String)null);

		if (Validator.isNotNull(processorThreadCount)) {
			formatSourceTask.setProcessorThreadCount(
				Integer.parseInt(processorThreadCount));
		}
	}

	private void _configureTasksFormatSource(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatSourceTask.class,
			new Action<FormatSourceTask>() {

				@Override
				public void execute(FormatSourceTask formatSourceTask) {
					_configureTasksFormatSource(formatSourceTask);
				}

			});
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.source.formatter";

}