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
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import com.pswidersk.gradle.python.PythonPlugin;
import com.pswidersk.gradle.python.VenvTask;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

	public static final String CHECK_PYTHON_FORMATTING_TASK_NAME =
		"checkPythonFormatting";

	public static final String FORMAT_PYTHON_TASK_NAME = "formatPython";

	public static final Plugin<Project> INSTANCE =
		new SourceFormatterDefaultsPlugin();

	public static final String PYTHON_BLACK_INSTALL_TASK_NAME =
		"pythonBlackInstall";

	@Override
	protected void applyPluginDefaults(
		final Project project, SourceFormatterPlugin sourceFormatterPlugin) {

		// Plugins

		GradleUtil.applyPlugin(project, PythonPlugin.class);

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, SourceFormatterPlugin.CONFIGURATION_NAME,
			PortalTools.GROUP, _PORTAL_TOOL_NAME);

		// Tasks

		final TaskProvider<VenvTask> checkPythonFormattingTaskProvider =
			GradleUtil.addTaskProvider(
				project, CHECK_PYTHON_FORMATTING_TASK_NAME, VenvTask.class);
		final TaskProvider<VenvTask> formatPythonTaskProvider =
			GradleUtil.addTaskProvider(
				project, FORMAT_PYTHON_TASK_NAME, VenvTask.class);
		TaskProvider<VenvTask> pythonBlackInstallTaskProvider =
			GradleUtil.addTaskProvider(
				project, PYTHON_BLACK_INSTALL_TASK_NAME, VenvTask.class);

		final TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider =
			GradleUtil.fetchTaskProvider(
				project,
				SourceFormatterPlugin.CHECK_SOURCE_FORMATTING_TASK_NAME,
				FormatSourceTask.class);
		final TaskProvider<FormatSourceTask> formatSourceTaskProvider =
			GradleUtil.fetchTaskProvider(
				project, SourceFormatterPlugin.FORMAT_SOURCE_TASK_NAME,
				FormatSourceTask.class);

		_configureTaskCheckPythonFormattingProvider(
			project, checkPythonFormattingTaskProvider,
			checkSourceFormattingTaskProvider, pythonBlackInstallTaskProvider);
		_configureTaskCheckSourceFormattingProvider(
			project, checkPythonFormattingTaskProvider,
			checkSourceFormattingTaskProvider);
		_configureTaskFormatPythonProvider(
			project, formatPythonTaskProvider, formatSourceTaskProvider,
			pythonBlackInstallTaskProvider);
		_configureTaskFormatSourceProvider(
			project, formatPythonTaskProvider, formatSourceTaskProvider);
		_configureTaskPythonBlackInstallProvider(
			pythonBlackInstallTaskProvider);

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
						project, checkPythonFormattingTaskProvider,
						checkSourceFormattingTaskProvider,
						formatPythonTaskProvider, formatSourceTaskProvider);
				}

			});
	}

	@Override
	protected Class<SourceFormatterPlugin> getPluginClass() {
		return SourceFormatterPlugin.class;
	}

	private void _configurePluginNode(
		Project project,
		TaskProvider<VenvTask> checkPythonFormattingTaskProvider,
		TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider,
		TaskProvider<VenvTask> formatPythonTaskProvider,
		TaskProvider<FormatSourceTask> formatSourceTaskProvider) {

		final TaskProvider<Task> packageRunCheckFormatTaskProvider =
			GradleUtil.fetchTaskProvider(project, "packageRunCheckFormat");

		if (packageRunCheckFormatTaskProvider != null) {
			String skipNodeTask = GradleUtil.getTaskPrefixedProperty(
				project.getPath(), checkSourceFormattingTaskProvider.getName(),
				"skip.node.task");

			if (!Boolean.parseBoolean(skipNodeTask)) {
				checkPythonFormattingTaskProvider.configure(
					new Action<VenvTask>() {

						@Override
						public void execute(
							VenvTask checkPythonFormattingVenvTask) {

							checkPythonFormattingVenvTask.mustRunAfter(
								packageRunCheckFormatTaskProvider);
						}

					});

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
				formatPythonTaskProvider.configure(
					new Action<VenvTask>() {

						@Override
						public void execute(VenvTask formatPythonVenvTask) {
							formatPythonVenvTask.mustRunAfter(
								packageRunFormatTaskProvider);
						}

					});

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

	private void _configureTaskCheckPythonFormattingProvider(
		final Project project,
		TaskProvider<VenvTask> checkPythonFormattingTaskProvider,
		final TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider,
		final TaskProvider<VenvTask> pythonBlackInstallTaskProvider) {

		checkPythonFormattingTaskProvider.configure(
			new Action<VenvTask>() {

				@Override
				public void execute(VenvTask checkPythonFormattingVenvTask) {
					checkPythonFormattingVenvTask.dependsOn(
						pythonBlackInstallTaskProvider);

					List<String> args = new ArrayList<>();

					args.add("--check");
					args.add("--fast");

					args.add("--force-exclude");
					args.add(_PYTHON_FORCE_EXCLUDE);

					args.add("--include");
					args.add(_PYTHON_INCLUDE);

					String baseDir = GradleUtil.getTaskPrefixedProperty(
						project.getPath(),
						checkPythonFormattingVenvTask.getName(), "base.dir");

					if (baseDir == null) {
						FormatSourceTask checkSourceFormattingFormatSourceTask =
							checkSourceFormattingTaskProvider.get();

						File checkSourceFormattingBaseDir =
							checkSourceFormattingFormatSourceTask.getBaseDir();

						baseDir = checkSourceFormattingBaseDir.getPath();
					}

					args.add(baseDir);

					checkPythonFormattingVenvTask.setArgs(args);

					checkPythonFormattingVenvTask.setVenvExec("black");
				}

			});
	}

	private void _configureTaskCheckSourceFormattingProvider(
		final Project project,
		final TaskProvider<VenvTask> checkPythonFormattingTaskProvider,
		TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider) {

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
	}

	private void _configureTaskFormatPythonProvider(
		final Project project, TaskProvider<VenvTask> formatPythonTaskProvider,
		final TaskProvider<FormatSourceTask> formatSourceTaskProvider,
		final TaskProvider<VenvTask> pythonBlackInstallTaskProvider) {

		formatPythonTaskProvider.configure(
			new Action<VenvTask>() {

				@Override
				public void execute(VenvTask formatPythonVenvTask) {
					formatPythonVenvTask.dependsOn(
						pythonBlackInstallTaskProvider);

					List<String> args = new ArrayList<>();

					args.add("--fast");

					args.add("--force-exclude");
					args.add(_PYTHON_FORCE_EXCLUDE);

					args.add("--include");
					args.add(_PYTHON_INCLUDE);

					String baseDir = GradleUtil.getTaskPrefixedProperty(
						project.getPath(), formatPythonVenvTask.getName(),
						"base.dir");

					if (baseDir == null) {
						FormatSourceTask formatSourceTask =
							formatSourceTaskProvider.get();

						File formatSourceBaseDir =
							formatSourceTask.getBaseDir();

						baseDir = formatSourceBaseDir.getPath();
					}

					args.add(baseDir);

					formatPythonVenvTask.setArgs(args);

					formatPythonVenvTask.setVenvExec("black");
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

	private void _configureTaskFormatSourceProvider(
		final Project project,
		final TaskProvider<VenvTask> formatPythonTaskProvider,
		TaskProvider<FormatSourceTask> formatSourceTaskProvider) {

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

	private void _configureTaskPythonBlackInstallProvider(
		TaskProvider<VenvTask> pythonBlackInstallTaskProvider) {

		pythonBlackInstallTaskProvider.configure(
			new Action<VenvTask>() {

				@Override
				public void execute(VenvTask pythonBlackInstallVenvTask) {
					pythonBlackInstallVenvTask.setArgs(
						Arrays.asList("install", "black"));
					pythonBlackInstallVenvTask.setVenvExec("pip");
				}

			});
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

	private static final String _PYTHON_FORCE_EXCLUDE =
		"\"/(\\.git|\\.gradle|bin|build|classes|node_modules|" +
			"node_modules_cache|test-classes|tmp)/\"";

	private static final String _PYTHON_INCLUDE = "\"\\.pyi?$\"";

}