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
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
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

		addConfigurationXSDBuilder(project);

		addTaskBuildXSD(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTaskBuildXSD(project);
				}

			});
	}

	protected Configuration addConfigurationXSDBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Apache XMLBeans for generating XMLBeans bindings.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesXSDBuilder(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesXSDBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "org.apache.xmlbeans", "xmlbeans",
			"2.5.0");
	}

	protected BuildXSDTask addTaskBuildXSD(Project project) {
		final BuildXSDTask buildXSDTask = GradleUtil.addTask(
			project, BUILD_XSD_TASK_NAME, BuildXSDTask.class);

		buildXSDTask.setArchiveName(
			project.getName() + "-xbean." + Jar.DEFAULT_EXTENSION);
		buildXSDTask.setDescription("Generates XMLBeans bindings.");
		buildXSDTask.setDestinationDir(project.file("lib"));
		buildXSDTask.setGroup(BasePlugin.BUILD_GROUP);
		buildXSDTask.setInputDir("xsd");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					configureTaskBuildXSDForWarPlugin(buildXSDTask);
				}

			});

		return buildXSDTask;
	}

	protected Task addTaskBuildXSDCompile(
		BuildXSDTask buildXSDTask, Task generateTask) {

		Project project = buildXSDTask.getProject();

		JavaCompile javaCompile = GradleUtil.addTask(
			project, buildXSDTask.getName() + "Compile", JavaCompile.class);

		javaCompile.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaCompile.getName()));

		javaCompile.setClasspath(
			GradleUtil.getConfiguration(project, CONFIGURATION_NAME));

		File tmpBinDir = new File(
			project.getBuildDir(), buildXSDTask.getName() + "/bin");

		javaCompile.setDestinationDir(tmpBinDir);

		javaCompile.setSource(generateTask.getOutputs());

		return javaCompile;
	}

	protected Task addTaskBuildXSDGenerate(BuildXSDTask buildXSDTask) {
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
		javaExec.setMain("org.apache.xmlbeans.impl.tool.SchemaCompiler");

		TaskInputs taskInputs = javaExec.getInputs();

		taskInputs.files(xsdFiles);

		TaskOutputs taskOutputs = javaExec.getOutputs();

		taskOutputs.dir(tmpSrcDir);

		return javaExec;
	}

	protected void configureTaskBuildXSD(BuildXSDTask buildXSDTask) {
		FileCollection inputFiles = buildXSDTask.getInputFiles();

		if (inputFiles.isEmpty()) {
			return;
		}

		Task generateTask = addTaskBuildXSDGenerate(buildXSDTask);

		Task compileTask = addTaskBuildXSDCompile(buildXSDTask, generateTask);

		buildXSDTask.from(compileTask.getOutputs());
		buildXSDTask.from(generateTask.getOutputs());

		TaskOutputs taskOutputs = buildXSDTask.getOutputs();

		GradleUtil.addDependency(
			buildXSDTask.getProject(), JavaPlugin.COMPILE_CONFIGURATION_NAME,
			taskOutputs.getFiles());
	}

	protected void configureTaskBuildXSD(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildXSDTask.class,
			new Action<BuildXSDTask>() {

				@Override
				public void execute(BuildXSDTask buildXSDTask) {
					configureTaskBuildXSD(buildXSDTask);
				}

			});
	}

	protected void configureTaskBuildXSDForWarPlugin(
		final BuildXSDTask buildXSDTask) {

		buildXSDTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getWebAppDir(buildXSDTask.getProject()), "WEB-INF/lib");
				}

			});

		buildXSDTask.setInputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getWebAppDir(buildXSDTask.getProject()), "WEB-INF/xsd");
				}

			});
	}

	protected File getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

}