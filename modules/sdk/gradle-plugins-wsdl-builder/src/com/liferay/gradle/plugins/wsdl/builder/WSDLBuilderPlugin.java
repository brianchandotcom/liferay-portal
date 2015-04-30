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

package com.liferay.gradle.plugins.wsdl.builder;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class WSDLBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_WSDL_TASK_NAME = "buildWSDL";

	public static final String CONFIGURATION_NAME = "wsdlBuilder";

	@Override
	public void apply(Project project) {
		final WSDLBuilderExtension wsdlBuilderExtension =
			GradleUtil.addExtension(
				project, "wsdlBuilder", WSDLBuilderExtension.class);

		addWSDLBuilderConfiguration(project);

		addTaskBuildWSDL(project);

		project.afterEvaluate(
			new Action<Project>() {

			@Override
			public void execute(Project project) {
				addTaskBuildWSDLTasks(project, wsdlBuilderExtension);
			}

		});
	}

	protected Task addTaskBuildWSDL(Project project) {
		Task task = project.task(BUILD_WSDL_TASK_NAME);

		task.setDescription("Generates WSDL client stubs.");
		task.setGroup(BasePlugin.BUILD_GROUP);

		return task;
	}

	protected Task addTaskBuildWSDLCompile(
		Project project, File wsdlFile, File tmpDir, Task generateTask) {

		String taskName = GradleUtil.getTaskName(
			BUILD_WSDL_TASK_NAME + "Compile", wsdlFile);

		JavaCompile javaCompile = GradleUtil.addTask(
			project, taskName, JavaCompile.class);

		javaCompile.setClasspath(
			GradleUtil.getConfiguration(project, CONFIGURATION_NAME));

		File tmpBinDir = new File(tmpDir, "bin");

		javaCompile.setDestinationDir(tmpBinDir);

		javaCompile.setSource(generateTask.getOutputs());

		return javaCompile;
	}

	protected Task addTaskBuildWSDLGenerate(
		Project project, File wsdlFile, File tmpDir) {

		String taskName = GradleUtil.getTaskName(
			BUILD_WSDL_TASK_NAME + "Generate", wsdlFile);

		JavaExec javaExec = GradleUtil.addTask(
			project, taskName, JavaExec.class);

		File tmpSrcDir = new File(tmpDir, "src");

		javaExec.args("--output=" + FileUtil.getAbsolutePath(tmpSrcDir));

		javaExec.args(FileUtil.getAbsolutePath(wsdlFile));

		javaExec.setClasspath(
			GradleUtil.getConfiguration(project, CONFIGURATION_NAME));
		javaExec.setMain("org.apache.axis.wsdl.WSDL2Java");

		TaskInputs taskInputs = javaExec.getInputs();

		taskInputs.file(wsdlFile);

		TaskOutputs taskOutputs = javaExec.getOutputs();

		taskOutputs.dir(tmpSrcDir);

		return javaExec;
	}

	protected Task addTaskBuildWSDLJar(
		Project project, WSDLBuilderExtension wsdlBuilderExtension,
		File wsdlFile, Task compileTask, Task generateTask) {

		String taskName = GradleUtil.getTaskName(
			BUILD_WSDL_TASK_NAME, wsdlFile);

		Jar jar = GradleUtil.addTask(project, taskName, Jar.class);

		jar.from(compileTask.getOutputs());

		if (wsdlBuilderExtension.isIncludeSource()) {
			jar.from(generateTask.getOutputs());
		}

		jar.setDestinationDir(wsdlBuilderExtension.getDestinationDir());

		String wsdlName = FileUtil.stripExtension(wsdlFile.getName());

		jar.setArchiveName(wsdlName + "-ws.jar");

		return jar;
	}

	protected void addTaskBuildWSDLTasks(
		Project project, WSDLBuilderExtension wsdlBuilderExtension) {

		FileCollection wsdlFiles = getWSDLFiles(project, wsdlBuilderExtension);

		if (wsdlFiles.isEmpty()) {
			return;
		}

		Task buildWSDLTask = GradleUtil.getTask(project, BUILD_WSDL_TASK_NAME);

		for (File file : wsdlFiles) {
			addTaskBuildWSDLTasks(buildWSDLTask, wsdlBuilderExtension, file);
		}

		TaskOutputs taskOutputs = buildWSDLTask.getOutputs();

		GradleUtil.addDependency(
			buildWSDLTask.getProject(), JavaPlugin.COMPILE_CONFIGURATION_NAME,
			taskOutputs.getFiles());
	}

	protected void addTaskBuildWSDLTasks(
		Task buildWSDLTask, WSDLBuilderExtension wsdlBuilderExtension,
		File wsdlFile) {

		Project project = buildWSDLTask.getProject();

		String tmpDirName = "build-wsdl/" + FileUtil.stripExtension(
			wsdlFile.getName());

		File tmpDir = new File(project.getBuildDir(), tmpDirName);

		Task generateTask = addTaskBuildWSDLGenerate(project, wsdlFile, tmpDir);

		Task compileTask = addTaskBuildWSDLCompile(
			project, wsdlFile, tmpDir, generateTask);

		Task jarTask = addTaskBuildWSDLJar(
			project, wsdlBuilderExtension, wsdlFile, compileTask, generateTask);

		buildWSDLTask.dependsOn(jarTask);

		TaskOutputs taskOutputs = buildWSDLTask.getOutputs();

		taskOutputs.file(jarTask.getOutputs());
	}

	protected Configuration addWSDLBuilderConfiguration(final Project project) {
		final Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Apache Axis for generating WSDL client stubs.");
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
						addWSDLBuilderDependencies(project);
					}
				}

			});

		return configuration;
	}

	protected void addWSDLBuilderDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "axis", "axis-wsdl4j", "1.5.1");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay", "org.apache.axis",
			"1.4.LIFERAY-PATCHED-1");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "commons-discovery",
			"commons-discovery", "0.2");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "commons-logging", "commons-logging",
			"1.0.4");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "javax.activation", "activation",
			"1.1");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "javax.mail", "mail", "1.4");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "org.apache.axis", "axis-jaxrpc",
			"1.4");
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "org.apache.axis", "axis-saaj", "1.4");
	}

	protected FileCollection getWSDLFiles(
		Project project, WSDLBuilderExtension wsdlBuilderExtension) {

		Map<String, Object> args = new HashMap<>();

		args.put("dir", wsdlBuilderExtension.getWSDLDir());
		args.put("include", "*.wsdl");

		return project.fileTree(args);
	}

}