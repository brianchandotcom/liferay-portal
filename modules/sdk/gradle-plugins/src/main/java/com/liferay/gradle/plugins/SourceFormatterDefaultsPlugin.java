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
import com.liferay.gradle.plugins.python.PythonPlugin;
import com.liferay.gradle.plugins.source.formatter.FormatSourceTask;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import com.pswidersk.gradle.python.VenvTask;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Andrea Di Giorgi
 * @author Hugo Huijser
 */
public class SourceFormatterDefaultsPlugin
	extends BaseDefaultsPlugin<SourceFormatterPlugin> {

	public static final Plugin<Project> INSTANCE =
		new SourceFormatterDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		final Project project, SourceFormatterPlugin sourceFormatterPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, SourceFormatterPlugin.CONFIGURATION_NAME,
			PortalTools.GROUP, _PORTAL_TOOL_NAME);

		// Tasks

		final TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider =
			GradleUtil.getTaskProvider(
				project,
				SourceFormatterPlugin.CHECK_SOURCE_FORMATTING_TASK_NAME,
				FormatSourceTask.class);
		final TaskProvider<FormatSourceTask> formatSourceTaskProvider =
			GradleUtil.getTaskProvider(
				project, SourceFormatterPlugin.FORMAT_SOURCE_TASK_NAME,
				FormatSourceTask.class);

		// Containers

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatSourceTask.class,
			new Action<FormatSourceTask>() {

				@Override
				public void execute(FormatSourceTask formatSourceTask) {
					_configureTaskFormatSource(formatSourceTask);
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			NodePlugin.class,
			new Action<NodePlugin>() {

				@Override
				public void execute(NodePlugin nodePlugin) {
					_configurePluginNode(
						project, checkSourceFormattingTaskProvider,
						formatSourceTaskProvider);
				}

			});

		pluginContainer.withType(
			PythonPlugin.class,
			new Action<PythonPlugin>() {

				@Override
				public void execute(PythonPlugin pythonPlugin) {
					_configurePluginPython(
						project, checkSourceFormattingTaskProvider,
						formatSourceTaskProvider);
				}

			});
	}

	@Override
	protected Class<SourceFormatterPlugin> getPluginClass() {
		return SourceFormatterPlugin.class;
	}

	private void _configurePluginNode(
		Project project,
		TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider,
		TaskProvider<FormatSourceTask> formatSourceTaskProvider) {

		final TaskProvider<Task> packageRunCheckFormatTaskProvider =
			GradleUtil.fetchTaskProvider(project, "packageRunCheckFormat");

		if (packageRunCheckFormatTaskProvider != null) {
			String skipNodeTask = GradleUtil.getTaskPrefixedProperty(
				project.getPath(), checkSourceFormattingTaskProvider.getName(),
				"skip.node.task");

			if (!Boolean.parseBoolean(skipNodeTask)) {
				checkSourceFormattingTaskProvider.configure(
					new Action<FormatSourceTask>() {

						@Override
						public void execute(
							FormatSourceTask
								checkSourceFormattingFormatSourceTask) {

							checkSourceFormattingFormatSourceTask.finalizedBy(
								packageRunCheckFormatTaskProvider);
						}

					});
			}
		}

		final TaskProvider<Task> packageRunFormatTaskProvider =
			GradleUtil.fetchTaskProvider(project, "packageRunFormat");

		if (packageRunFormatTaskProvider != null) {
			String skipNodeTask = GradleUtil.getTaskPrefixedProperty(
				project.getPath(), formatSourceTaskProvider.getName(),
				"skip.node.task");

			if (!Boolean.parseBoolean(skipNodeTask)) {
				formatSourceTaskProvider.configure(
					new Action<FormatSourceTask>() {

						@Override
						public void execute(FormatSourceTask formatSourceTask) {
							formatSourceTask.finalizedBy(
								packageRunFormatTaskProvider);
						}

					});
			}
		}
	}

	private void _configurePluginPython(
		Project project,
		TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider,
		TaskProvider<FormatSourceTask> formatSourceTaskProvider) {

		final TaskProvider<VenvTask> checkPythonFormattingTaskProvider =
			GradleUtil.getTaskProvider(
				project, PythonPlugin.CHECK_PYTHON_FORMATTING_TASK_NAME,
				VenvTask.class);
		final TaskProvider<VenvTask> formatPythonTaskProvider =
			GradleUtil.getTaskProvider(
				project, PythonPlugin.FORMAT_PYTHON_TASK_NAME, VenvTask.class);

		checkSourceFormattingTaskProvider.configure(
			new Action<FormatSourceTask>() {

				@Override
				public void execute(
					FormatSourceTask checkSourceFormattingFormatSourceTask) {

					if (_hasPythonFiles(
							project,
							checkSourceFormattingFormatSourceTask.
								getBaseDir())) {

						checkSourceFormattingFormatSourceTask.finalizedBy(
							checkPythonFormattingTaskProvider);
					}
				}

			});

		formatSourceTaskProvider.configure(
			new Action<FormatSourceTask>() {

				@Override
				public void execute(FormatSourceTask formatSourceTask) {
					if (_hasPythonFiles(
							project, formatSourceTask.getBaseDir())) {

						formatSourceTask.finalizedBy(formatPythonTaskProvider);
					}
				}

			});
	}

	private void _configureTaskFormatSource(FormatSourceTask formatSourceTask) {
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

	private boolean _hasPythonFiles(Project project, File baseDir) {
		Map<String, Object> args = new HashMap<>();

		args.put("dir", baseDir);
		args.put("includes", Arrays.asList("**/*.py", "**/*.pyi"));

		FileTree fileTree = project.fileTree(args);

		if (fileTree.isEmpty()) {
			return false;
		}

		return true;
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.source.formatter";

}