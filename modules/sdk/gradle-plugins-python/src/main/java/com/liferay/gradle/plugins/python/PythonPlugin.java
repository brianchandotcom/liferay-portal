/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.python;

import com.liferay.gradle.util.GradleUtil;

import com.pswidersk.gradle.python.VenvTask;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Peter Shin
 */
public class PythonPlugin implements Plugin<Project> {

	public static final String CHECK_PYTHON_FORMATTING_TASK_NAME =
		"checkPythonFormatting";

	public static final String FORMAT_PYTHON_TASK_NAME = "formatPython";

	public static final String PYTHON_BLACK_INSTALL_TASK_NAME =
		"pythonBlackInstall";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(
			project, com.pswidersk.gradle.python.PythonPlugin.class);

		TaskProvider<VenvTask> checkPythonFormattingTaskProvider =
			GradleUtil.addTaskProvider(
				project, CHECK_PYTHON_FORMATTING_TASK_NAME, VenvTask.class);
		TaskProvider<VenvTask> formatPythonTaskProvider =
			GradleUtil.addTaskProvider(
				project, FORMAT_PYTHON_TASK_NAME, VenvTask.class);
		TaskProvider<VenvTask> pythonBlackInstallTaskProvider =
			GradleUtil.addTaskProvider(
				project, PYTHON_BLACK_INSTALL_TASK_NAME, VenvTask.class);

		_configureTaskCheckPythonFormattingProvider(
			project, checkPythonFormattingTaskProvider,
			pythonBlackInstallTaskProvider);
		_configureTaskFormatPythonProvider(
			project, formatPythonTaskProvider, pythonBlackInstallTaskProvider);
		_configureTaskPythonBlackInstallProvider(
			pythonBlackInstallTaskProvider);
	}

	private void _configureTaskCheckPythonFormattingProvider(
		final Project project,
		TaskProvider<VenvTask> checkPythonFormattingTaskProvider,
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
						File projectDir = project.getProjectDir();

						baseDir = projectDir.getPath();
					}

					args.add(baseDir);

					checkPythonFormattingVenvTask.setArgs(args);

					checkPythonFormattingVenvTask.setVenvExec("black");
				}

			});
	}

	private void _configureTaskFormatPythonProvider(
		final Project project, TaskProvider<VenvTask> formatPythonTaskProvider,
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
						File projectDir = project.getProjectDir();

						baseDir = projectDir.getPath();
					}

					args.add(baseDir);

					formatPythonVenvTask.setArgs(args);

					formatPythonVenvTask.setVenvExec("black");
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

	private static final String _PYTHON_FORCE_EXCLUDE =
		"\"/(\\.git|\\.gradle|bin|build|classes|node_modules|" +
			"node_modules_cache|test-classes|tmp)/\"";

	private static final String _PYTHON_INCLUDE = "\"\\.pyi?$\"";

}