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

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class JspCPlugin implements Plugin<Project> {

	public static final String COMPILE_JSP_TASK_NAME = "compileJSP";

	public static final String CONFIGURATION_NAME = "jspC";

	public static final String GENERATE_JSP_JAVA_TASK_NAME = "generateJSPJava";

	public static final String TOOL_CONFIGURATION_NAME = "jspCTool";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		Configuration jspCConfiguration = addConfigurationJspC(project);
		Configuration jspCToolConfiguration = addConfigurationJspCTool(project);

		final CompileJSPTask generateJSPJavaTask = addTaskGenerateJSPJava(
			project, jspCConfiguration, jspCToolConfiguration);

		addTaskCompileJSP(
			generateJSPJavaTask, jspCConfiguration, jspCToolConfiguration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addDependenciesJspC(project);
				}

			});
	}

	protected Configuration addConfigurationJspC(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the classpath of the JSP compilation tasks.");
		configuration.setVisible(false);

		return configuration;
	}

	protected Configuration addConfigurationJspCTool(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, TOOL_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addDependenciesJspCTool(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Jasper JspC for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addDependenciesJspC(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		ConfigurableFileCollection configurableFileCollection = project.files(
			jar);

		configurableFileCollection.builtBy(jar);

		dependencyHandler.add(CONFIGURATION_NAME, configurableFileCollection);

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		dependencyHandler.add(
			CONFIGURATION_NAME, sourceSet.getCompileClasspath());
	}

	protected void addDependenciesJspCTool(Project project) {
		GradleUtil.addDependency(
			project, TOOL_CONFIGURATION_NAME, "org.apache.ant", "ant", "1.9.4");

		ModuleDependency moduleDependency =
			(ModuleDependency)GradleUtil.addDependency(
				project, TOOL_CONFIGURATION_NAME, "com.liferay",
				"com.liferay.jasper.jspc", "latest.release");

		moduleDependency.exclude(
			Collections.singletonMap("group", "com.liferay.portal"));
	}

	protected JavaCompile addTaskCompileJSP(
		CompileJSPTask generateJSPJavaTask, Configuration jspCConfiguration,
		Configuration jspCToolConfiguration) {

		JavaCompile javaCompile = GradleUtil.addTask(
			generateJSPJavaTask.getProject(), COMPILE_JSP_TASK_NAME,
			JavaCompile.class);

		javaCompile.setClasspath(jspCToolConfiguration.plus(jspCConfiguration));
		javaCompile.setDescription("Compile JSP files to check for errors.");
		javaCompile.setDestinationDir(javaCompile.getTemporaryDir());
		javaCompile.setGroup(JavaBasePlugin.VERIFICATION_GROUP);
		javaCompile.setSource(generateJSPJavaTask.getOutputs());

		return javaCompile;
	}

	protected CompileJSPTask addTaskGenerateJSPJava(
		Project project, Configuration jspCConfiguration,
		Configuration jspCToolConfiguration) {

		final CompileJSPTask compileJSPTask = GradleUtil.addTask(
			project, GENERATE_JSP_JAVA_TASK_NAME, CompileJSPTask.class);

		compileJSPTask.setClasspath(jspCToolConfiguration);
		compileJSPTask.setDescription(
			"Compiles JSP files to Java source files to check for errors.");
		compileJSPTask.setDestinationDir(
			new File(project.getBuildDir(), "jspc"));
		compileJSPTask.setJspCClasspath(jspCConfiguration);

		compileJSPTask.setWebAppDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						compileJSPTask.getProject(),
						SourceSet.MAIN_SOURCE_SET_NAME);

					return getSrcDir(sourceSet.getResources());
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			WarPlugin.class,
			new Action<WarPlugin>() {

				@Override
				public void execute(WarPlugin warPlugin) {
					configureTaskGenerateJSPJavaForWarPlugin(compileJSPTask);
				}

			});

		return compileJSPTask;
	}

	protected void configureTaskGenerateJSPJavaForWarPlugin(
		final CompileJSPTask compileJSPTask) {

		compileJSPTask.setWebAppDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					WarPluginConvention warPluginConvention =
						GradleUtil.getConvention(
							compileJSPTask.getProject(),
							WarPluginConvention.class);

					return warPluginConvention.getWebAppDir();
				}

			});
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

}