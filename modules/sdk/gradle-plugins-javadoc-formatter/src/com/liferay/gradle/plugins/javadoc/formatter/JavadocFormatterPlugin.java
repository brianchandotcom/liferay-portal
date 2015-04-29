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

package com.liferay.gradle.plugins.javadoc.formatter;

import com.liferay.javadoc.formatter.JavadocFormatterArgs;

import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "javadocFormatter";

	@Override
	public void apply(Project project) {
		addJavadocFormatterConfiguration(project);
		addJavadocFormatterExtension(project);

		addFormatJavadocTask(project);
	}

	protected Task addFormatJavadocTask(Project project) {
		TaskContainer taskContainer = project.getTasks();

		Task task = taskContainer.create(
			"formatJavadoc", JavadocFormatterTask.class);

		task.setDescription("Runs Liferay Javadoc Formatter to format files.");

		return task;
	}

	protected Configuration addJavadocFormatterConfiguration(
		final Project project) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final Configuration configuration = configurationContainer.create(
			CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Javadoc Formatter for this project.");
		configuration.setVisible(false);

		ResolvableDependencies resolvableDependencies =
			configuration.getIncoming();

		resolvableDependencies.beforeResolve(
			new Action<ResolvableDependencies>() {

				@Override
				public void execute(
					ResolvableDependencies resolvableDependencies) {

					Set<Dependency> dependencies =
						configuration.getDependencies();

					if (dependencies.isEmpty()) {
						addJavadocFormatterDependencies(project);
					}
				}

			});

		return configuration;
	}

	protected void addJavadocFormatterDependencies(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		dependencyHandler.add(
			CONFIGURATION_NAME,
			"com.liferay:com.liferay.javadoc.formatter:latest.release");
	}

	protected JavadocFormatterArgs addJavadocFormatterExtension(
		Project project) {

		ExtensionContainer extensionContainer = project.getExtensions();

		return extensionContainer.create(
			"javadocFormatter", JavadocFormatterArgs.class);
	}

}