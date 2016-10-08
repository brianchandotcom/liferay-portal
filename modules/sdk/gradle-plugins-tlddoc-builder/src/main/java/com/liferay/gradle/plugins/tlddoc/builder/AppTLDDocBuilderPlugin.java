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
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class AppTLDDocBuilderPlugin implements Plugin<Project> {

	public static final String APP_TLDDOC_TASK_NAME = "appTLDDoc";

	public static final String COPY_APP_TLDDOC_RESOURCES_TASK_NAME =
		"copyAppTLDDocResources";

	public static final String JAR_APP_TLDDOC_TASK_NAME = "jarAppTLDDoc";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);

		Configuration tlddocConfiguration =
			TLDDocBuilderPlugin.addConfigurationTLDDoc(project);

		final Copy copyAppTLDDocResourcesTask = _addTaskCopyAppTLDDocResources(
			project);

		final TLDDocTask appTLDDocTask = _addTaskAppTLDDoc(
			copyAppTLDDocResourcesTask, tlddocConfiguration);

		_addTaskJarAppTLDDoc(appTLDDocTask);

		for (Project subproject : project.getSubprojects()) {
			subproject.afterEvaluate(
				new Action<Project>() {

					@Override
					public void execute(Project subproject) {
						PluginContainer pluginContainer =
							subproject.getPlugins();

						if (pluginContainer.hasPlugin(
								TLDDocBuilderPlugin.class)) {

							_configureTaskAppTLDDoc(appTLDDocTask, subproject);
							_configureTaskCopyAppTLDDocResources(
								copyAppTLDDocResourcesTask, subproject);
						}
					}

				});
		}
	}

	private TLDDocTask _addTaskAppTLDDoc(
		Copy copyAppTLDDocResourcesTask, FileCollection classpath) {

		final Project project = copyAppTLDDocResourcesTask.getProject();

		TLDDocTask tlddocTask = GradleUtil.addTask(
			project, APP_TLDDOC_TASK_NAME, TLDDocTask.class);

		tlddocTask.dependsOn(copyAppTLDDocResourcesTask);
		tlddocTask.setClasspath(classpath);
		tlddocTask.setDescription(
			"Generates tag library documentation for the app.");

		tlddocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "docs/tlddoc");
				}

			});

		tlddocTask.setGroup(JavaBasePlugin.DOCUMENTATION_GROUP);

		return tlddocTask;
	}

	private Copy _addTaskCopyAppTLDDocResources(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, COPY_APP_TLDDOC_RESOURCES_TASK_NAME, Copy.class);

		copy.into(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					TLDDocTask appTLDDocTask = (TLDDocTask)GradleUtil.getTask(
						project, APP_TLDDOC_TASK_NAME);

					return appTLDDocTask.getDestinationDir();
				}

			});

		copy.setDescription("Copies tag library documentation resources.");

		return copy;
	}

	private Jar _addTaskJarAppTLDDoc(TLDDocTask tlddocTask) {
		Jar jar = GradleUtil.addTask(
			tlddocTask.getProject(), JAR_APP_TLDDOC_TASK_NAME, Jar.class);

		jar.from(tlddocTask);
		jar.setClassifier("taglibdoc");
		jar.setDescription(
			"Assembles a jar archive containing the tag library " +
				"documentation files for this app.");
		jar.setGroup(BasePlugin.BUILD_GROUP);

		return jar;
	}

	private void _configureTaskAppTLDDoc(
		TLDDocTask appTLDDocTask, Project subproject) {

		Task task = GradleUtil.getTask(
			subproject, TLDDocBuilderPlugin.VALIDATE_TLD_TASK_NAME);

		appTLDDocTask.dependsOn(task);

		TLDDocTask tlddocTask = (TLDDocTask)GradleUtil.getTask(
			subproject, TLDDocBuilderPlugin.TLDDOC_TASK_NAME);

		appTLDDocTask.source(tlddocTask.getSource());
	}

	private void _configureTaskCopyAppTLDDocResources(
		Copy copyAppTLDDocResourcesTask, Project subproject) {

		Copy copy = (Copy)GradleUtil.getTask(
			subproject, TLDDocBuilderPlugin.COPY_TLDDOC_RESOURCES_TASK_NAME);

		TaskInputs taskInputs = copy.getInputs();

		copyAppTLDDocResourcesTask.from(taskInputs.getFiles());
	}

}