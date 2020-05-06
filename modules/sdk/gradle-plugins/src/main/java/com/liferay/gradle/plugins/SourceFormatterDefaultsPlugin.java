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
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
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
	protected void applyPluginDefaults(
		final Project project, SourceFormatterPlugin sourceFormatterPlugin) {

		addPortalToolDependencies(project);

		_configureTasksFormatSource(project);

		GradleUtil.withPlugin(
			project, NodePlugin.class,
			new Action<NodePlugin>() {

				@Override
				public void execute(NodePlugin nodePlugin) {
					_configureTaskForNodePlugin(
						project,
						SourceFormatterPlugin.CHECK_SOURCE_FORMATTING_TASK_NAME,
						"packageRunCheckFormat");
					_configureTaskForNodePlugin(
						project, SourceFormatterPlugin.FORMAT_SOURCE_TASK_NAME,
						"packageRunFormat");
				}

			});
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

	private void _configureTaskForNodePlugin(
		Project project, String taskName, String nodeTaskName) {

		TaskContainer taskContainer = project.getTasks();

		Task nodeTask = taskContainer.findByName(nodeTaskName);

		if (nodeTask != null) {
			Task task = GradleUtil.getTask(project, taskName);

			String skipNodeTask = GradleUtil.getTaskPrefixedProperty(
				task, "skip.node.task");

			if (!Boolean.parseBoolean(skipNodeTask)) {
				task.dependsOn(nodeTask);
			}
		}
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

		String processorThreadCount = GradleUtil.getProperty(
			project, "source.formatter.processor.thread.count", (String)null);

		if (Validator.isNotNull(processorThreadCount)) {
			formatSourceTask.setProcessorThreadCount(
				Integer.parseInt(processorThreadCount));
		}

		String showDebugInformation = GradleUtil.getProperty(
			project, "source.formatter.show.debug.information", (String)null);

		if (Validator.isNotNull(showDebugInformation)) {
			formatSourceTask.setShowDebugInformation(
				Boolean.parseBoolean(showDebugInformation));
		}

		String showDocumentation = GradleUtil.getProperty(
			project, "source.formatter.show.documentation", (String)null);

		if (Validator.isNotNull(showDocumentation)) {
			formatSourceTask.setShowDocumentation(
				Boolean.parseBoolean(showDocumentation));
		}

		String showStatusUpdates = GradleUtil.getProperty(
			project, "source.formatter.show.status.updates", (String)null);

		if (Validator.isNotNull(showStatusUpdates)) {
			formatSourceTask.setShowStatusUpdates(
				Boolean.parseBoolean(showStatusUpdates));
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