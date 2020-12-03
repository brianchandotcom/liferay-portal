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
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
public class SourceFormatterPlugin implements Plugin<Project> {

	public static final String CHECK_PYTHON_FORMATTING_TASK_NAME =
		"checkPythonFormatting";

	public static final String CHECK_SOURCE_FORMATTING_TASK_NAME =
		"checkSourceFormatting";

	public static final String CONFIGURATION_NAME = "sourceFormatter";

	public static final String FORMAT_PYTHON_TASK_NAME = "formatPython";

	public static final String FORMAT_SOURCE_TASK_NAME = "formatSource";

	public static final String PYTHON_BLACK_INSTALL_TASK_NAME =
		"pythonBlackInstall";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, PythonPlugin.class);

		Configuration sourceFormatterConfiguration =
			_addConfigurationSourceFormatter(project);

		TaskProvider<VenvTask> checkPythonFormattingTaskProvider =
			GradleUtil.addTaskProvider(
				project, CHECK_PYTHON_FORMATTING_TASK_NAME, VenvTask.class);
		TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider =
			GradleUtil.addTaskProvider(
				project, CHECK_SOURCE_FORMATTING_TASK_NAME,
				FormatSourceTask.class);
		TaskProvider<VenvTask> formatPythonTaskProvider =
			GradleUtil.addTaskProvider(
				project, FORMAT_PYTHON_TASK_NAME, VenvTask.class);
		TaskProvider<FormatSourceTask> formatSourceTaskProvider =
			GradleUtil.addTaskProvider(
				project, FORMAT_SOURCE_TASK_NAME, FormatSourceTask.class);
		TaskProvider<VenvTask> pythonBlackInstallTaskProvider =
			GradleUtil.addTaskProvider(
				project, PYTHON_BLACK_INSTALL_TASK_NAME, VenvTask.class);

		_configureTaskCheckSourceFormattingProvider(
			project, checkPythonFormattingTaskProvider,
			checkSourceFormattingTaskProvider);
		_configureTaskCheckPythonFormattingProvider(
			project, checkPythonFormattingTaskProvider,
			checkSourceFormattingTaskProvider, pythonBlackInstallTaskProvider);
		_configureTaskFormatPythonProvider(
			project, formatPythonTaskProvider, formatSourceTaskProvider,
			pythonBlackInstallTaskProvider);
		_configureTaskFormatSourceProvider(
			project, formatPythonTaskProvider, formatSourceTaskProvider);
		_configureTaskPythonBlackInstallProvider(
			pythonBlackInstallTaskProvider);

		_configureTasksFormatSource(project, sourceFormatterConfiguration);
	}

	private Configuration _addConfigurationSourceFormatter(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesSourceFormatter(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Source Formatter for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesSourceFormatter(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.source.formatter", "latest.release");
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

					checkSourceFormattingFormatSourceTask.onlyIf(
						_skipIfExecutingParentTaskSpec);
					checkSourceFormattingFormatSourceTask.setAutoFix(false);
					checkSourceFormattingFormatSourceTask.setDescription(
						"Checks the source formatting of this project.");
					checkSourceFormattingFormatSourceTask.setFailOnAutoFix(
						true);
					checkSourceFormattingFormatSourceTask.setFailOnHasWarning(
						true);
					checkSourceFormattingFormatSourceTask.setGroup(
						LifecycleBasePlugin.VERIFICATION_GROUP);
					checkSourceFormattingFormatSourceTask.setPrintErrors(true);
					checkSourceFormattingFormatSourceTask.setShowStatusUpdates(
						false);

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

	private void _configureTaskFormatSource(
		FormatSourceTask formatSourceTask, FileCollection classpath) {

		formatSourceTask.setClasspath(classpath);

		String fileExtensions = GradleUtil.getTaskPrefixedProperty(
			formatSourceTask, "file.extensions");

		if (Validator.isNotNull(fileExtensions)) {
			formatSourceTask.setFileExtensions(fileExtensions.split(","));
		}

		String fileNames = GradleUtil.getTaskPrefixedProperty(
			formatSourceTask, "file.names");

		if (Validator.isNotNull(fileNames)) {
			formatSourceTask.setFileNames(fileNames.split(","));
		}

		String formatCurrentBranch = GradleUtil.getTaskPrefixedProperty(
			formatSourceTask, "format.current.branch");

		if (Validator.isNotNull(formatCurrentBranch)) {
			formatSourceTask.setFormatCurrentBranch(
				Boolean.parseBoolean(formatCurrentBranch));
		}

		String formatLatestAuthor = GradleUtil.getTaskPrefixedProperty(
			formatSourceTask, "format.latest.author");

		if (Validator.isNotNull(formatLatestAuthor)) {
			formatSourceTask.setFormatLatestAuthor(
				Boolean.parseBoolean(formatLatestAuthor));
		}

		String formatLocalChanges = GradleUtil.getTaskPrefixedProperty(
			formatSourceTask, "format.local.changes");

		if (Validator.isNotNull(formatLocalChanges)) {
			formatSourceTask.setFormatLocalChanges(
				Boolean.parseBoolean(formatLocalChanges));
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
					formatSourceTask.onlyIf(_skipIfExecutingParentTaskSpec);
					formatSourceTask.setDescription(
						"Runs Liferay Source Formatter to format the project " +
							"files.");
					formatSourceTask.setGroup("formatting");
					formatSourceTask.setShowStatusUpdates(true);

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

	private void _configureTasksFormatSource(
		Project project, final FileCollection classpath) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatSourceTask.class,
			new Action<FormatSourceTask>() {

				@Override
				public void execute(FormatSourceTask formatSourceTask) {
					_configureTaskFormatSource(formatSourceTask, classpath);
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

	private static final String _PYTHON_FORCE_EXCLUDE =
		"\"/(\\.git|\\.gradle|bin|build|classes|node_modules|" +
			"node_modules_cache|test-classes|tmp)/\"";

	private static final String _PYTHON_INCLUDE = "\"\\.pyi?$\"";

	private static final Spec<Task> _skipIfExecutingParentTaskSpec =
		new Spec<Task>() {

			@Override
			public boolean isSatisfiedBy(Task task) {
				Project project = task.getProject();

				Gradle gradle = project.getGradle();

				TaskExecutionGraph taskExecutionGraph = gradle.getTaskGraph();

				Project parentProject = project;

				while ((parentProject = parentProject.getParent()) != null) {
					TaskContainer parentProjectTaskContainer =
						parentProject.getTasks();

					Task parentProjectTask =
						parentProjectTaskContainer.findByName(task.getName());

					if ((parentProjectTask != null) &&
						taskExecutionGraph.hasTask(parentProjectTask)) {

						return false;
					}
				}

				return true;
			}

		};

}