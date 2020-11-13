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
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.execution.TaskExecutionGraph;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Raymond Augé
 * @author Andrea Di Giorgi
 */
public class SourceFormatterPlugin implements Plugin<Project> {

	public static final String CHECK_SOURCE_FORMATTING_TASK_NAME =
		"checkSourceFormatting";

	public static final String CONFIGURATION_NAME = "sourceFormatter";

	public static final String FORMAT_SOURCE_TASK_NAME = "formatSource";

	public static final String PYTHON_BLACK_INSTALL_TASK_NAME =
		"pythonBlackInstallTask";

	public static final String PYTHON_BLACK_TASK_NAME = "pythonBlackTask";

	@Override
	public void apply(Project project) {
		Configuration sourceFormatterConfiguration =
			_addConfigurationSourceFormatter(project);

		GradleUtil.applyPlugin(project, PythonPlugin.class);

		_addTaskCheckSourceFormatting(project);

		FormatSourceTask formatSourceTask = _addTaskFormatSource(project);

		_configureTasksFormatSource(project, sourceFormatterConfiguration);

		_configurePythonBlack(project, formatSourceTask);
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

	private FormatSourceTask _addTaskCheckSourceFormatting(Project project) {
		FormatSourceTask formatSourceTask = GradleUtil.addTask(
			project, CHECK_SOURCE_FORMATTING_TASK_NAME, FormatSourceTask.class);

		formatSourceTask.onlyIf(_skipIfExecutingParentTaskSpec);
		formatSourceTask.setAutoFix(false);
		formatSourceTask.setDescription(
			"Checks the source formatting of this project.");
		formatSourceTask.setFailOnAutoFix(true);
		formatSourceTask.setFailOnHasWarning(true);
		formatSourceTask.setGroup(LifecycleBasePlugin.VERIFICATION_GROUP);
		formatSourceTask.setPrintErrors(true);
		formatSourceTask.setShowStatusUpdates(false);

		return formatSourceTask;
	}

	private FormatSourceTask _addTaskFormatSource(Project project) {
		FormatSourceTask formatSourceTask = GradleUtil.addTask(
			project, FORMAT_SOURCE_TASK_NAME, FormatSourceTask.class);

		formatSourceTask.onlyIf(_skipIfExecutingParentTaskSpec);
		formatSourceTask.setDescription(
			"Runs Liferay Source Formatter to format the project files.");
		formatSourceTask.setGroup("formatting");
		formatSourceTask.setShowStatusUpdates(true);

		return formatSourceTask;
	}

	private void _configurePythonBlack(
		Project project, FormatSourceTask formatSourceTask) {

		VenvTask pythonBlackInstallTask = GradleUtil.addTask(
			project, PYTHON_BLACK_INSTALL_TASK_NAME, VenvTask.class);

		List<String> pythonBlackInstallTaskArgs = new ArrayList<>();

		pythonBlackInstallTaskArgs.add("install");

		pythonBlackInstallTaskArgs.add("black");

		pythonBlackInstallTask.setArgs(pythonBlackInstallTaskArgs);

		pythonBlackInstallTask.setVenvExec("pip");

		List<String> pythonBlackTaskArgs = new ArrayList<>();

		VenvTask pythonBlackTask = GradleUtil.addTask(
			project, PYTHON_BLACK_TASK_NAME, VenvTask.class);

		File baseDir = formatSourceTask.getBaseDir();

		pythonBlackTaskArgs.add("--fast");

		pythonBlackTaskArgs.add(baseDir.getAbsolutePath());

		pythonBlackTask.setArgs(pythonBlackTaskArgs);

		pythonBlackTask.setVenvExec("black");

		pythonBlackTask.dependsOn(pythonBlackInstallTask);

		formatSourceTask.finalizedBy(pythonBlackTask);
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