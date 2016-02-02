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

package com.liferay.gradle.plugins.maven.plugin.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.Upload;

/**
 * @author Andrea Di Giorgi
 */
public class MavenPluginBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_PLUGIN_DESCRIPTOR_TASK_NAME =
		"buildPluginDescriptor";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		BuildPluginDescriptorTask buildPluginDescriptorTask =
			addTaskBuildPluginDescriptor(project);

		configureTasksUpload(project, buildPluginDescriptorTask);
	}

	protected BuildPluginDescriptorTask addTaskBuildPluginDescriptor(
		final Project project) {

		BuildPluginDescriptorTask buildPluginDescriptorTask =
			GradleUtil.addTask(
				project, BUILD_PLUGIN_DESCRIPTOR_TASK_NAME,
				BuildPluginDescriptorTask.class);

		buildPluginDescriptorTask.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME);

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		buildPluginDescriptorTask.setClassesDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getClassesDir();
				}

			});

		buildPluginDescriptorTask.setDescription(
			"Generates the Maven plugin descriptor for the project.");
		buildPluginDescriptorTask.setGroup(BasePlugin.BUILD_GROUP);

		buildPluginDescriptorTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getSrcDir(sourceSet.getResources());

					return new File(resourcesDir, "META-INF/maven");
				}

			});

		buildPluginDescriptorTask.setPomArtifactId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _osgiHelper.getBundleSymbolicName(project);
				}

			});

		buildPluginDescriptorTask.setPomGroupId(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getGroup();
				}

			});

		buildPluginDescriptorTask.setPomVersion(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});

		buildPluginDescriptorTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getSrcDir(sourceSet.getJava());
				}

			});

		Task processResourcesTask = GradleUtil.getTask(
			project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		processResourcesTask.mustRunAfter(buildPluginDescriptorTask);

		return buildPluginDescriptorTask;
	}

	protected void configureTasksUpload(
		Project project,
		final BuildPluginDescriptorTask buildPluginDescriptorTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Upload.class,
			new Action<Upload>() {

				@Override
				public void execute(Upload upload) {
					configureTaskUpload(upload, buildPluginDescriptorTask);
				}

			});
	}

	protected void configureTaskUpload(
		Upload upload, BuildPluginDescriptorTask buildPluginDescriptorTask) {

		upload.dependsOn(buildPluginDescriptorTask);
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}