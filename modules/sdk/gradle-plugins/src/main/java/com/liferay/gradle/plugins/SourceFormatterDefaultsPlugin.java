/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
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
		final TaskProvider<FormatSourceTask> checkSourceFormattingTaskProvider,
		final TaskProvider<FormatSourceTask> formatSourceTaskProvider) {

		TaskProvider<VenvTask> checkPythonFormattingTaskProvider =
			GradleUtil.getTaskProvider(
				project, PythonPlugin.CHECK_PYTHON_FORMATTING_TASK_NAME,
				VenvTask.class);
		TaskProvider<VenvTask> formatPythonTaskProvider =
			GradleUtil.getTaskProvider(
				project, PythonPlugin.FORMAT_PYTHON_TASK_NAME, VenvTask.class);

		checkPythonFormattingTaskProvider.configure(
			new Action<VenvTask>() {

				@Override
				public void execute(VenvTask checkPythonFormattingTask) {
					checkPythonFormattingTask.finalizedBy(
						checkSourceFormattingTaskProvider);

					checkPythonFormattingTask.setEnabled(false);
				}

			});

		formatPythonTaskProvider.configure(
			new Action<VenvTask>() {

				@Override
				public void execute(VenvTask formatPythonTask) {
					formatPythonTask.finalizedBy(formatSourceTaskProvider);

					formatPythonTask.setEnabled(false);
				}

			});
	}

	private void _configureTaskFormatSource(FormatSourceTask formatSourceTask) {
		Project project = formatSourceTask.getProject();

		String autoFix = GradleUtil.getProperty(
			project, "source.auto.fix", (String)null);

		if (Validator.isNotNull(autoFix)) {
			formatSourceTask.setAutoFix(Boolean.parseBoolean(autoFix));
		}

		String baseDirName = GradleUtil.getProperty(
			project, "source.base.dir", (String)null);

		if (Validator.isNotNull(baseDirName)) {
			formatSourceTask.setBaseDirName(baseDirName);
		}

		String checkCategoryNames = GradleUtil.getProperty(
			project, "source.check.category.names", (String)null);

		if (Validator.isNotNull(checkCategoryNames)) {
			formatSourceTask.setCheckCategoryNames(
				checkCategoryNames.split(","));
		}

		String checkNames = GradleUtil.getProperty(
			project, "source.check.names", (String)null);

		if (Validator.isNotNull(checkNames)) {
			formatSourceTask.setCheckNames(checkNames.split(","));
		}

		String failOnAutoFix = GradleUtil.getProperty(
			project, "source.fail.on.auto.fix", (String)null);

		if (Validator.isNotNull(failOnAutoFix)) {
			formatSourceTask.setFailOnAutoFix(
				Boolean.parseBoolean(failOnAutoFix));
		}

		String failOnHasWarning = GradleUtil.getProperty(
			project, "source.fail.on.has.warning", (String)null);

		if (Validator.isNotNull(failOnHasWarning)) {
			formatSourceTask.setFailOnHasWarning(
				Boolean.parseBoolean(failOnHasWarning));
		}

		String formatCurrentBranch = GradleUtil.getProperty(
			project, "format.current.branch", (String)null);

		if (Validator.isNotNull(formatCurrentBranch)) {
			formatSourceTask.setFormatCurrentBranch(
				Boolean.parseBoolean(formatCurrentBranch));
		}

		String formatLatestAuthor = GradleUtil.getProperty(
			project, "format.latest.author", (String)null);

		if (Validator.isNotNull(formatLatestAuthor)) {
			formatSourceTask.setFormatLatestAuthor(
				Boolean.parseBoolean(formatLatestAuthor));
		}

		String formatLocalChanges = GradleUtil.getProperty(
			project, "format.local.changes", (String)null);

		if (Validator.isNotNull(formatLocalChanges)) {
			formatSourceTask.setFormatLocalChanges(
				Boolean.parseBoolean(formatLocalChanges));
		}

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

		String javaParserEnabled = GradleUtil.getProperty(
			project, "java.parser.enabled", (String)null);

		if (Validator.isNotNull(javaParserEnabled)) {
			formatSourceTask.setJavaParserEnabled(
				Boolean.parseBoolean(javaParserEnabled));
		}

		String maxLineLength = GradleUtil.getProperty(
			project, "source.formatter.max.line.length", (String)null);

		if (Validator.isNotNull(maxLineLength)) {
			formatSourceTask.setMaxLineLength(Integer.parseInt(maxLineLength));
		}

		String printErrors = GradleUtil.getProperty(
			project, "source.print.errors", (String)null);

		if (Validator.isNotNull(printErrors)) {
			formatSourceTask.setPrintErrors(Boolean.parseBoolean(printErrors));
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

		String sourceFileNames = GradleUtil.getProperty(
			project, "source.files", (String)null);

		if (Validator.isNotNull(sourceFileNames)) {
			formatSourceTask.setFileNames(sourceFileNames.split(","));
		}

		String sourceFileExtensions = GradleUtil.getProperty(
			project, "source.file.extensions", (String)null);

		if (Validator.isNotNull(sourceFileExtensions)) {
			formatSourceTask.setFileExtensions(sourceFileExtensions.split(","));
		}

		String validateCommitMessages = GradleUtil.getProperty(
			project, "validate.commit.message", (String)null);

		if (Validator.isNotNull(validateCommitMessages)) {
			formatSourceTask.setValidateCommitMessages(
				Boolean.parseBoolean(validateCommitMessages));
		}
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.source.formatter";

}