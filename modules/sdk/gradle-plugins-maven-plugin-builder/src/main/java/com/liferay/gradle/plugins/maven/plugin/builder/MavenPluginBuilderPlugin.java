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
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class MavenPluginBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_PLUGIN_DESCRIPTOR_TASK_NAME =
		"buildPluginDescriptor";

	@Override
	public void apply(Project project) {
		addBuildPluginDescriptorTask(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureBuildPluginDescriptorTask(project);
				}

			});
	}

	protected BuildPluginDescriptorTask addBuildPluginDescriptorTask(
		Project project) {

		BuildPluginDescriptorTask buildPluginDescriptorTask =
			GradleUtil.addTask(
				project, BUILD_PLUGIN_DESCRIPTOR_TASK_NAME,
				BuildPluginDescriptorTask.class);

		buildPluginDescriptorTask.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME);

		buildPluginDescriptorTask.setDescription(
			"Generates the Maven plugin descriptor for the project.");
		buildPluginDescriptorTask.setGroup(BasePlugin.BUILD_GROUP);

		return buildPluginDescriptorTask;
	}

	protected void configureBuildPluginDescriptorTask(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		configureBuildPluginDescriptorTaskClassesDir(buildPluginDescriptorTask);
		configureBuildPluginDescriptorTaskConfigurationScopeMappings(
			buildPluginDescriptorTask);
		configureBuildPluginDescriptorTaskOutputDir(buildPluginDescriptorTask);
		configureBuildPluginDescriptorTaskPomArtifactId(
			buildPluginDescriptorTask);
		configureBuildPluginDescriptorTaskPomGroupId(buildPluginDescriptorTask);
		configureBuildPluginDescriptorTaskPomVersion(buildPluginDescriptorTask);
		configureBuildPluginDescriptorTaskSourceDir(buildPluginDescriptorTask);
	}

	protected void configureBuildPluginDescriptorTask(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildPluginDescriptorTask.class,
			new Action<BuildPluginDescriptorTask>() {

				@Override
				public void execute(
					BuildPluginDescriptorTask
						buildPluginDescriptorTask) {

					configureBuildPluginDescriptorTask(
						buildPluginDescriptorTask);
				}

			});
	}

	protected void configureBuildPluginDescriptorTaskClassesDir(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		if (buildPluginDescriptorTask.getClassesDir() != null) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			buildPluginDescriptorTask.getProject(),
			SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		buildPluginDescriptorTask.setClassesDir(
			sourceSetOutput.getClassesDir());
	}

	protected void configureBuildPluginDescriptorTaskOutputDir(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		if (buildPluginDescriptorTask.getOutputDir() != null) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			buildPluginDescriptorTask.getProject(),
			SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getResources();

		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		File outputDir = new File(iterator.next(), "META-INF/maven");

		buildPluginDescriptorTask.setOutputDir(outputDir);
	}

	protected void configureBuildPluginDescriptorTaskPomArtifactId(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		if (Validator.isNotNull(buildPluginDescriptorTask.getPomArtifactId())) {
			return;
		}

		Project project = buildPluginDescriptorTask.getProject();

		buildPluginDescriptorTask.setPomArtifactId(project.getName());
	}

	protected void configureBuildPluginDescriptorTaskPomGroupId(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		if (Validator.isNotNull(buildPluginDescriptorTask.getPomGroupId())) {
			return;
		}

		Project project = buildPluginDescriptorTask.getProject();

		Object group = project.getGroup();

		if (group != null) {
			buildPluginDescriptorTask.setPomGroupId(group.toString());
		}
	}

	protected void configureBuildPluginDescriptorTaskPomVersion(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		if (Validator.isNotNull(buildPluginDescriptorTask.getPomVersion())) {
			return;
		}

		Project project = buildPluginDescriptorTask.getProject();

		Object version = project.getVersion();

		if (version != null) {
			buildPluginDescriptorTask.setPomVersion(version.toString());
		}
	}

	protected void configureBuildPluginDescriptorTaskSourceDir(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		if (buildPluginDescriptorTask.getSourceDir() != null) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			buildPluginDescriptorTask.getProject(),
			SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getJava();

		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		buildPluginDescriptorTask.setSourceDir(iterator.next());
	}

	private void configureBuildPluginDescriptorTaskConfigurationScopeMappings(
		BuildPluginDescriptorTask buildPluginDescriptorTask) {

		Map<String, String> configurationScopeMappings =
			buildPluginDescriptorTask.getConfigurationScopeMappings();

		if (!configurationScopeMappings.isEmpty()) {
			return;
		}

		configurationScopeMappings.put(
			JavaPlugin.COMPILE_CONFIGURATION_NAME, "compile");
	}

}