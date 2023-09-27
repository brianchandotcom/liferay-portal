/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.tlddoc.builder;

import com.liferay.gradle.plugins.tlddoc.builder.task.TLDDocTask;
import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
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

	public static final String PLUGIN_NAME = "appTLDDocBuilder";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);

		final AppTLDDocBuilderExtension appTLDDocBuilderExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, AppTLDDocBuilderExtension.class);

		Configuration tlddocConfiguration =
			TLDDocBuilderPlugin.addConfigurationTLDDoc(project);

		final Copy copyAppTLDDocResourcesTask = _addTaskCopyAppTLDDocResources(
			project);

		final TLDDocTask appTLDDocTask = _addTaskAppTLDDoc(
			copyAppTLDDocResourcesTask, tlddocConfiguration);

		_addTaskJarAppTLDDoc(appTLDDocTask);

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project subproject) {
					Set<Project> subprojects =
						appTLDDocBuilderExtension.getSubprojects();

					PluginContainer pluginContainer = subproject.getPlugins();

					if (subprojects.contains(subproject) &&
						pluginContainer.hasPlugin(TLDDocBuilderPlugin.class)) {

						_configureTaskAppTLDDoc(appTLDDocTask, subproject);
						_configureTaskCopyAppTLDDocResources(
							copyAppTLDDocResourcesTask, subproject);
					}
				}

			});
	}

	private TLDDocTask _addTaskAppTLDDoc(
		Copy copyAppTLDDocResourcesTask, FileCollection classpath) {

		final Project project = copyAppTLDDocResourcesTask.getProject();

		TLDDocTask tldDocTask = GradleUtil.addTask(
			project, APP_TLDDOC_TASK_NAME, TLDDocTask.class);

		tldDocTask.dependsOn(copyAppTLDDocResourcesTask);
		tldDocTask.setClasspath(classpath);
		tldDocTask.setDescription(
			"Generates tag library documentation for the app.");

		tldDocTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(project.getBuildDir(), "docs/tlddoc");
				}

			});

		tldDocTask.setGroup(JavaBasePlugin.DOCUMENTATION_GROUP);

		return tldDocTask;
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

	private Jar _addTaskJarAppTLDDoc(TLDDocTask tldDocTask) {
		Jar jar = GradleUtil.addTask(
			tldDocTask.getProject(), JAR_APP_TLDDOC_TASK_NAME, Jar.class);

		jar.from(tldDocTask);
		jar.setClassifier("taglibdoc");
		jar.setDescription(
			"Assembles a jar archive containing the tag library " +
				"documentation files for this app.");
		jar.setGroup(BasePlugin.BUILD_GROUP);

		return jar;
	}

	private void _configureTaskAppTLDDoc(
		TLDDocTask appTLDDocTask, Project subproject) {

		appTLDDocTask.dependsOn(
			GradleUtil.getTask(
				subproject, TLDDocBuilderPlugin.VALIDATE_TLD_TASK_NAME));

		TLDDocTask tldDocTask = (TLDDocTask)GradleUtil.getTask(
			subproject, TLDDocBuilderPlugin.TLDDOC_TASK_NAME);

		appTLDDocTask.source(tldDocTask.getSource());
	}

	private void _configureTaskCopyAppTLDDocResources(
		Copy copyAppTLDDocResourcesTask, Project subproject) {

		Copy copy = (Copy)GradleUtil.getTask(
			subproject, TLDDocBuilderPlugin.COPY_TLDDOC_RESOURCES_TASK_NAME);

		TaskInputs taskInputs = copy.getInputs();

		copyAppTLDDocResourcesTask.from(taskInputs.getFiles());
	}

}