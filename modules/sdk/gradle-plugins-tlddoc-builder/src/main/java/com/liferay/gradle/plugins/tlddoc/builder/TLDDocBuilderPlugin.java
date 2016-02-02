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

package com.liferay.gradle.plugins.tlddoc.builder;

import com.liferay.gradle.plugins.tlddoc.builder.tasks.TLDDocTask;
import com.liferay.gradle.plugins.tlddoc.builder.tasks.ValidateSchemaTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class TLDDocBuilderPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "tlddoc";

	public static final String TLDDOC_TASK_NAME = "tlddoc";

	public static final String VALIDATE_TLD_TASK_NAME = "validateTLD";

	@Override
	public void apply(Project project) {
		Configuration tlddocConfiguration = addConfigurationTLDDoc(project);

		ValidateSchemaTask validateTLDTask = addTaskValidateTLD(project);

		addTaskTLDDoc(project, validateTLDTask);

		configureTasksTLDDoc(project, tlddocConfiguration);
	}

	protected Configuration addConfigurationTLDDoc(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addDependenciesTLDDoc(project);
				}

			});

		configuration.setDescription(
			"Configures Tag Library Documentation Generator for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addDependenciesTLDDoc(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "taglibrarydoc", "tlddoc", "1.3");
	}

	protected TLDDocTask addTaskTLDDoc(
		Project project, ValidateSchemaTask validateTLDTask) {

		final TLDDocTask tlddocTask = GradleUtil.addTask(
			project, TLDDOC_TASK_NAME, TLDDocTask.class);

		tlddocTask.dependsOn(validateTLDTask);
		tlddocTask.setDescription("Runs Tag Library Documentation Generator.");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskTLDDocForJavaPlugin(tlddocTask);
				}

			});

		return tlddocTask;
	}

	protected ValidateSchemaTask addTaskValidateTLD(Project project) {
		final ValidateSchemaTask validateSchemaTask = GradleUtil.addTask(
			project, VALIDATE_TLD_TASK_NAME, ValidateSchemaTask.class);

		validateSchemaTask.setDescription("Validates TLD files.");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskValidateSchemaForJavaPlugin(
						validateSchemaTask);
				}

			});

		return validateSchemaTask;
	}

	protected void configureTasksTLDDoc(
		Project project, final Configuration tlddocConfiguration) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			TLDDocTask.class,
			new Action<TLDDocTask>() {

				@Override
				public void execute(TLDDocTask tlddocTask) {
					configureTaskTLDDocClasspath(
						tlddocTask, tlddocConfiguration);
				}

			});
	}

	protected void configureTaskTLDDocClasspath(
		TLDDocTask tlddocTask, FileCollection fileCollection) {

		tlddocTask.setClasspath(fileCollection);
	}

	protected void configureTaskTLDDocForJavaPlugin(TLDDocTask tlddocTask) {
		final Project project = tlddocTask.getProject();

		tlddocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					JavaPluginConvention javaPluginConvention =
						GradleUtil.getConvention(
							project, JavaPluginConvention.class);

					return new File(
						javaPluginConvention.getDocsDir(), "tlddoc");
				}

			});

		tlddocTask.setIncludes(Collections.singleton("**/*.tld"));

		tlddocTask.setSource(
			new Callable<Iterable<File>>() {

				public Iterable<File> call() throws Exception {
					return getResourceDirs(project);
				}

			});
	}

	protected void configureTaskValidateSchemaForJavaPlugin(
		ValidateSchemaTask validateSchemaTask) {

		final Project project = validateSchemaTask.getProject();

		validateSchemaTask.setIncludes(Collections.singleton("**/*.tld"));

		validateSchemaTask.setSource(
			new Callable<Iterable<File>>() {

				public Iterable<File> call() throws Exception {
					return getResourceDirs(project);
				}

			});
	}

	protected Iterable<File> getResourceDirs(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getResources();

		return sourceDirectorySet.getSrcDirs();
	}

}