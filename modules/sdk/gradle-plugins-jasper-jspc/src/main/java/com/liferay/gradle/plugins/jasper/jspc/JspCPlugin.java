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
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class JspCPlugin implements Plugin<Project> {

	public static final String COMPILE_JSP_TASK_NAME = "compileJSP";

	public static final String CONFIGURATION_NAME = "jspC";

	public static final String EXTENSION_NAME = "jspC";

	public static final String GENERATE_JSP_JAVA_TASK_NAME = "generateJSPJava";

	public static final String TOOL_CONFIGURATION_NAME = "jspCTool";

	@Override
	public void apply(Project project) {
		GradleUtil.addExtension(project, EXTENSION_NAME, JspCExtension.class);

		addJspCConfiguration(project);
		addJspCToolConfiguration(project);

		addTaskGenerateJSPJava(project);

		addTaskCompileJSP(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					JspCExtension jspCExtension = GradleUtil.getExtension(
						project, JspCExtension.class);

					addJspCDependencies(project);
					addJspCToolDependencies(project);
					configureJspcExtension(project, jspCExtension);
					configureTaskGenerateJSPJava(project, jspCExtension);
				}

			});
	}

	protected Configuration addJspCConfiguration(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the classpath of the JSP compilation tasks.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addJspCDependencies(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		dependencyHandler.add(CONFIGURATION_NAME, sourceSet.getOutput());

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		dependencyHandler.add(CONFIGURATION_NAME, configuration);
	}

	protected Configuration addJspCToolConfiguration(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, TOOL_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Jasper JspC for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addJspCToolDependencies(Project project) {
		JspCExtension jspCExtension = GradleUtil.getExtension(
			project, JspCExtension.class);

		GradleUtil.addDependency(
			project, TOOL_CONFIGURATION_NAME, "org.apache.ant", "ant",
			jspCExtension.getAntVersion());
		GradleUtil.addDependency(
			project, TOOL_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.jasper.jspc", jspCExtension.getJspCVersion());
	}

	protected JavaCompile addTaskCompileJSP(Project project) {
		JavaCompile javaCompile = GradleUtil.addTask(
			project, COMPILE_JSP_TASK_NAME, JavaCompile.class);

		javaCompile.setClasspath(getClasspath(project));
		javaCompile.setDescription("Compile JSP files to check for errors.");
		javaCompile.setDestinationDir(javaCompile.getTemporaryDir());
		javaCompile.setGroup("verification");

		Task generateJSPJavaTask = GradleUtil.getTask(
			project, GENERATE_JSP_JAVA_TASK_NAME);

		javaCompile.setSource(generateJSPJavaTask.getOutputs());

		return javaCompile;
	}

	protected CompileJSPTask addTaskGenerateJSPJava(Project project) {
		CompileJSPTask compileJSPTask = GradleUtil.addTask(
			project, GENERATE_JSP_JAVA_TASK_NAME, CompileJSPTask.class);

		compileJSPTask.setClasspath(getClasspath(project));
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

		File webAppDir = null;

		PluginContainer pluginContainer = project.getPlugins();

		if (pluginContainer.hasPlugin(WarPlugin.class)) {
			WarPluginConvention warPluginConvention = GradleUtil.getConvention(
				project, WarPluginConvention.class);

			webAppDir = warPluginConvention.getWebAppDir();
		}
		else {
			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, SourceSet.MAIN_SOURCE_SET_NAME);

			SourceDirectorySet sourceDirectorySet = sourceSet.getResources();

			Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

			Iterator<File> iterator = srcDirs.iterator();

			webAppDir = iterator.next();
		}

		jspCExtension.setWebAppDir(webAppDir);
	}

	protected void configureTaskGenerateJSPJava(
		Project project, JspCExtension jspCExtension) {

		CompileJSPTask compileJSPTask = (CompileJSPTask)GradleUtil.getTask(
			project, GENERATE_JSP_JAVA_TASK_NAME);

		jspCExtension.copyTo(compileJSPTask);
	}

	protected FileCollection getClasspath(Project project) {
		Configuration toolConfiguration = GradleUtil.getConfiguration(
			project, TOOL_CONFIGURATION_NAME);

		Configuration configuration = GradleUtil.getConfiguration(
			project, CONFIGURATION_NAME);

		return toolConfiguration.plus(configuration);
	}

}