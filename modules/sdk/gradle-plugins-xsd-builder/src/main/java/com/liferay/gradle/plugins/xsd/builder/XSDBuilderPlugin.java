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

package com.liferay.gradle.plugins.xsd.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class XSDBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_XSD_TASK_NAME = "buildXSD";

	public static final String CONFIGURATION_NAME = "xsdBuilder";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		_addConfigurationXSDBuilder(project);

		_addTaskBuildXSD(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureTasksBuildXSD(project);
				}

			});
	}

	private Configuration _addConfigurationXSDBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesXSDBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Apache XMLBeans for generating XMLBeans bindings.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesXSDBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "org.apache.xmlbeans", "xmlbeans",
			"2.5.0");
	}

	private BuildXSDTask _addTaskBuildXSD(Project project) {
		final BuildXSDTask buildXSDTask = GradleUtil.addTask(
			project, BUILD_XSD_TASK_NAME, BuildXSDTask.class);

		buildXSDTask.setDescription(
			"Generates XMLBeans bindings and compiles them in a JAR file.");
		buildXSDTask.setDestinationDir(project.file("lib"));
		buildXSDTask.setGroup(BasePlugin.BUILD_GROUP);
		buildXSDTask.setInputDir("xsd");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					_configureTaskBuildXSDForWarPlugin(buildXSDTask);
				}

			});

		return buildXSDTask;
	}

	private Task _addTaskBuildXSDCompile(
		BuildXSDTask buildXSDTask, Task generateTask) {

		Project project = buildXSDTask.getProject();

		JavaCompile javaCompile = GradleUtil.addTask(
			project, buildXSDTask.getName() + "Compile", JavaCompile.class);

		javaCompile.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaCompile.getName()));

		javaCompile.setClasspath(
			GradleUtil.getConfiguration(project, CONFIGURATION_NAME));
		javaCompile.setDescription("Compiles the generated Java types.");

		File tmpBinDir = new File(
			project.getBuildDir(), buildXSDTask.getName() + "/bin");

		javaCompile.setDestinationDir(tmpBinDir);

		javaCompile.setSource(generateTask.getOutputs());

		return javaCompile;
	}

	private Task _addTaskBuildXSDGenerate(BuildXSDTask buildXSDTask) {
		Project project = buildXSDTask.getProject();

		JavaExec javaExec = GradleUtil.addTask(
			project, buildXSDTask.getName() + "Generate", JavaExec.class);

		File tmpSrcDir = new File(
			project.getBuildDir(), buildXSDTask.getName() + "/src");

		javaExec.args("-d");
		javaExec.args(FileUtil.getAbsolutePath(tmpSrcDir));
		javaExec.args("-srconly");

		Iterable<File> xsdFiles = buildXSDTask.getInputFiles();

		for (File xsdFile : xsdFiles) {
			javaExec.args(FileUtil.getAbsolutePath(xsdFile));
		}

		javaExec.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaExec.getName()));

		javaExec.setClasspath(
			GradleUtil.getConfiguration(project, CONFIGURATION_NAME));
		javaExec.setDescription(
			"Invokes the XMLBeans Schema Compiler in order to generate Java " +
				"types from XML Schema.");
		javaExec.setMain("org.apache.xmlbeans.impl.tool.SchemaCompiler");

		TaskInputs taskInputs = javaExec.getInputs();

		taskInputs.files(xsdFiles);

		TaskOutputs taskOutputs = javaExec.getOutputs();

		taskOutputs.dir(tmpSrcDir);

		return javaExec;
	}

	private void _configureTaskBuildXSD(BuildXSDTask buildXSDTask) {
		FileCollection inputFiles = buildXSDTask.getInputFiles();

		if (inputFiles.isEmpty()) {
			return;
		}

		Task generateTask = _addTaskBuildXSDGenerate(buildXSDTask);

		Task compileTask = _addTaskBuildXSDCompile(buildXSDTask, generateTask);

		buildXSDTask.from(compileTask.getOutputs());
		buildXSDTask.from(generateTask.getOutputs());

		TaskOutputs taskOutputs = buildXSDTask.getOutputs();

		GradleUtil.addDependency(
			buildXSDTask.getProject(), JavaPlugin.COMPILE_CONFIGURATION_NAME,
			taskOutputs.getFiles());
	}

	private void _configureTaskBuildXSDForWarPlugin(
		final BuildXSDTask buildXSDTask) {

		buildXSDTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildXSDTask.getProject());

					return new File(webAppDir, "WEB-INF/lib");
				}

			});

		buildXSDTask.setInputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File webAppDir = GradleUtil.getWebAppDir(
						buildXSDTask.getProject());

					return new File(webAppDir, "WEB-INF/xsd");
				}

			});
	}

	private void _configureTasksBuildXSD(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildXSDTask.class,
			new Action<BuildXSDTask>() {

				@Override
				public void execute(BuildXSDTask buildXSDTask) {
					_configureTaskBuildXSD(buildXSDTask);
				}

			});
	}

}