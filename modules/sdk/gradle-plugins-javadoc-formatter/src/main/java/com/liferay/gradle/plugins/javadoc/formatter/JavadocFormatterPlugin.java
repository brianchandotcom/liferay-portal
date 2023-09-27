/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.javadoc.formatter;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "javadocFormatter";

	public static final String FORMAT_JAVADOC_TASK_NAME = "formatJavadoc";

	@Override
	public void apply(Project project) {
		Configuration javadocFormatterConfiguration =
			addConfigurationJavadocFormatter(project);

		addTaskFormatJavadoc(project);

		configureTasksFormatJavadoc(project, javadocFormatterConfiguration);
	}

	protected Configuration addConfigurationJavadocFormatter(
		final Project project) {

		final Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addDependenciesJavadocFormatter(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Javadoc Formatter for this project.");
		configuration.setVisible(false);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaLibraryPlugin.class,
			new Action<JavaLibraryPlugin>() {

				@Override
				public void execute(JavaLibraryPlugin javaLibraryPlugin) {
					configureConfigurationJavadocFormatterForJavaLibraryPlugin(
						project, configuration);
				}

			});

		return configuration;
	}

	protected void addDependenciesJavadocFormatter(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.javadoc.formatter", "latest.release");
	}

	protected FormatJavadocTask addTaskFormatJavadoc(Project project) {
		FormatJavadocTask formatJavadocTask = GradleUtil.addTask(
			project, FORMAT_JAVADOC_TASK_NAME, FormatJavadocTask.class);

		formatJavadocTask.setDescription(
			"Runs Liferay Javadoc Formatter to format files.");
		formatJavadocTask.setGroup("formatting");

		return formatJavadocTask;
	}

	protected void configureConfigurationJavadocFormatterForJavaLibraryPlugin(
		Project project, Configuration configuration) {

		Configuration apiConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.API_CONFIGURATION_NAME);

		configuration.extendsFrom(apiConfiguration);
	}

	protected void configureTaskFormatJavadoc(
		FormatJavadocTask formatJavadocTask, FileCollection classpath) {

		formatJavadocTask.setClasspath(classpath);

		String generateXml = GradleUtil.getTaskPrefixedProperty(
			formatJavadocTask, "generate.xml");

		if (Validator.isNotNull(generateXml)) {
			formatJavadocTask.setGenerateXml(Boolean.parseBoolean(generateXml));
		}

		String init = GradleUtil.getTaskPrefixedProperty(
			formatJavadocTask, "init");

		if (Validator.isNotNull(init)) {
			formatJavadocTask.setInitializeMissingJavadocs(
				Boolean.parseBoolean(init));
		}

		String limit = GradleUtil.getTaskPrefixedProperty(
			formatJavadocTask, "limit");

		if (Validator.isNotNull(limit)) {
			formatJavadocTask.setLimits((Object[])limit.split(","));
		}

		String update = GradleUtil.getTaskPrefixedProperty(
			formatJavadocTask, "update");

		if (Validator.isNotNull(update)) {
			formatJavadocTask.setUpdateJavadocs(Boolean.parseBoolean(update));
		}
	}

	protected void configureTasksFormatJavadoc(
		Project project, final FileCollection classpath) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			FormatJavadocTask.class,
			new Action<FormatJavadocTask>() {

				@Override
				public void execute(FormatJavadocTask formatJavadoc) {
					configureTaskFormatJavadoc(formatJavadoc, classpath);
				}

			});
	}

}