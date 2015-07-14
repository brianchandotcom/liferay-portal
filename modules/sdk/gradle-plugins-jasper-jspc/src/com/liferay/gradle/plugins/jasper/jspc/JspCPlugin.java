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

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class JspCPlugin implements Plugin<Project> {

	public static final String COMPILE_JSP_SOURCES_TASK_NAME =
		"compileJSPSources";

	public static final String COMPILE_JSP_TASK_NAME = "compileJSP";

	public static final String CONFIGURATION_NAME = "jspC";

	public static final String EXTENSION_NAME = "jspC";

	@Override
	public void apply(Project project) {
		GradleUtil.addExtension(project, EXTENSION_NAME, JspCExtension.class);

		addJasperJspCConfiguration(project);

		addTaskCompileJSPSources(project);

		addTaskCompileJSP(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					JspCExtension jspCExtension = GradleUtil.getExtension(
						project, JspCExtension.class);

					addJspCDependencies(project);
					configureJspcExtension(project, jspCExtension);
					configureTaskCompileJSPSources(project, jspCExtension);
				}

			});
	}

	protected Configuration addJasperJspCConfiguration(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Jasper JspC for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addJspCDependencies(Project project) {
		JspCExtension jspCExtension = GradleUtil.getExtension(
			project, JspCExtension.class);

		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "org.apache.ant", "ant",
			jspCExtension.getAntVersion());
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.jasper.jspc", jspCExtension.getJspCVersion());

		DependencyHandler dependencyHandler = project.getDependencies();

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		dependencyHandler.add(CONFIGURATION_NAME, sourceSet.getOutput());

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		dependencyHandler.add(CONFIGURATION_NAME, configuration);
	}

	protected JavaCompile addTaskCompileJSP(Project project) {
		JavaCompile javaCompile = GradleUtil.addTask(
			project, COMPILE_JSP_TASK_NAME, JavaCompile.class);

		javaCompile.setClasspath(
			GradleUtil.getConfiguration(project, CONFIGURATION_NAME));
		javaCompile.setDescription("Compile JSP files to check for errors");
		javaCompile.setDestinationDir(javaCompile.getTemporaryDir());
		javaCompile.setGroup("verification");

		Task compileJSPSourcesTask = GradleUtil.getTask(
			project, COMPILE_JSP_SOURCES_TASK_NAME);

		javaCompile.setSource(compileJSPSourcesTask.getOutputs());

		return javaCompile;
	}

	protected CompileJSPTask addTaskCompileJSPSources(Project project) {
		CompileJSPTask compileJSPTask = GradleUtil.addTask(
			project, COMPILE_JSP_SOURCES_TASK_NAME, CompileJSPTask.class);

		compileJSPTask.setDestinationDir(
			new File(project.getBuildDir(), "jspc"));

		return compileJSPTask;
	}

	protected void configureJspcExtension(
		Project project, JspCExtension jspCExtension) {

		configureJspCExtensionWebAppDir(project, jspCExtension);
	}

	protected void configureJspCExtensionWebAppDir(
		Project project, JspCExtension jspCExtension) {

		if (jspCExtension.getWebAppDir() != null) {
			return;
		}

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getResources();

		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		jspCExtension.setWebAppDir(iterator.next());
	}

	protected void configureTaskCompileJSPSources(
		Project project, JspCExtension jspCExtension) {

		CompileJSPTask compileJSPTask = (CompileJSPTask)GradleUtil.getTask(
			project, COMPILE_JSP_SOURCES_TASK_NAME);

		configureTaskCompileJSPSourcesModuleWeb(compileJSPTask, jspCExtension);
		configureTaskCompileJSPSourcesPortalDir(compileJSPTask, jspCExtension);
		configureTaskCompileJSPSourcesWebAppDir(compileJSPTask, jspCExtension);
	}

	protected void configureTaskCompileJSPSourcesModuleWeb(
		CompileJSPTask compileJSPTask, JspCExtension jspCExtension) {

		compileJSPTask.setModuleWeb(jspCExtension.isModuleWeb());
	}

	protected void configureTaskCompileJSPSourcesPortalDir(
		CompileJSPTask compileJSPTask, JspCExtension jspCExtension) {

		if (compileJSPTask.getPortalDir() != null) {
			return;
		}

		compileJSPTask.setPortalDir(jspCExtension.getPortalDir());
	}

	protected void configureTaskCompileJSPSourcesWebAppDir(
		CompileJSPTask compileJSPTask, JspCExtension jspCExtension) {

		if (compileJSPTask.getWebAppDir() != null) {
			return;
		}

		compileJSPTask.setWebAppDir(jspCExtension.getWebAppDir());
	}

}