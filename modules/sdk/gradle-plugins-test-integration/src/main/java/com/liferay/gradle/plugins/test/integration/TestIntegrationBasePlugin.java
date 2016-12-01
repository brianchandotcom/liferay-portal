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

package com.liferay.gradle.plugins.test.integration;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;

import java.io.File;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.ConventionMapping;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.testing.Test;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.idea.IdeaPlugin;
import org.gradle.plugins.ide.idea.model.IdeaModel;
import org.gradle.plugins.ide.idea.model.IdeaModule;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationBasePlugin implements Plugin<Project> {

	public static final String TEST_INTEGRATION_SOURCE_SET_NAME =
		"testIntegration";

	public static final String TEST_INTEGRATION_TASK_NAME = "testIntegration";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		SourceSet testIntegrationSourceSet = _addSourceSetTestIntegration(
			project);

		Test testIntegrationTask = _addTaskTestIntegration(
			project, testIntegrationSourceSet);

		_configureEclipse(project, testIntegrationSourceSet);
		_configureIdea(project, testIntegrationSourceSet);

		_configureTaskCheck(testIntegrationTask);
	}

	private SourceSet _addSourceSetTestIntegration(Project project) {
		SourceSet testIntegrationSourceSet = GradleUtil.addSourceSet(
			project, TEST_INTEGRATION_SOURCE_SET_NAME);

		Configuration testIntegrationCompileConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getCompileConfigurationName());

		Configuration testCompileConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME);

		testIntegrationCompileConfiguration.extendsFrom(
			testCompileConfiguration);

		Configuration testIntegrationRuntimeConfiguration =
			GradleUtil.getConfiguration(
				project,
				testIntegrationSourceSet.getRuntimeConfigurationName());

		Configuration testRuntimeConfiguration = GradleUtil.getConfiguration(
			project, JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME);

		testIntegrationRuntimeConfiguration.extendsFrom(
			testRuntimeConfiguration, testIntegrationCompileConfiguration);

		SourceSet mainSourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		FileCollection compileClasspath =
			testIntegrationSourceSet.getCompileClasspath();

		testIntegrationSourceSet.setCompileClasspath(
			compileClasspath.plus(mainSourceSet.getOutput()));

		FileCollection runtimeClasspath =
			testIntegrationSourceSet.getRuntimeClasspath();

		testIntegrationSourceSet.setRuntimeClasspath(
			runtimeClasspath.plus(mainSourceSet.getOutput()));

		return testIntegrationSourceSet;
	}

	private Test _addTaskTestIntegration(
		Project project, final SourceSet testIntegrationSourceSet) {

		final Test test = GradleUtil.addTask(
			project, TEST_INTEGRATION_TASK_NAME, Test.class);

		test.mustRunAfter(JavaPlugin.TEST_TASK_NAME);

		test.setDescription("Runs the integration tests.");
		test.setForkEvery(null);
		test.setGroup(JavaBasePlugin.VERIFICATION_GROUP);

		ConventionMapping conventionMapping = test.getConventionMapping();

		conventionMapping.map(
			"classpath",
			new Callable<FileCollection>() {

				@Override
				public FileCollection call() throws Exception {
					return testIntegrationSourceSet.getRuntimeClasspath();
				}

			});

		conventionMapping.map(
			"testClassesDir",
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput =
						testIntegrationSourceSet.getOutput();

					return sourceSetOutput.getClassesDir();
				}

			});

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					Set<String> includes = test.getIncludes();

					if (includes.isEmpty()) {
						test.setIncludes(
							Collections.singleton("**/*Test.class"));
					}
				}

			});

		return test;
	}

	private void _configureEclipse(
		final Project project, final SourceSet testIntegrationSourceSet) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			EclipsePlugin.class,
			new Action<EclipsePlugin>() {

				@Override
				public void execute(EclipsePlugin eclipsePlugin) {
					_configureEclipseClasspath(
						project, testIntegrationSourceSet);
				}

			});
	}

	private void _configureEclipseClasspath(
		Project project, SourceSet testIntegrationSourceSet) {

		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		Collection<Configuration> plusConfigurations =
			eclipseClasspath.getPlusConfigurations();

		Configuration configuration = GradleUtil.getConfiguration(
			project, testIntegrationSourceSet.getRuntimeConfigurationName());

		plusConfigurations.add(configuration);
	}

	private void _configureIdea(
		final Project project, final SourceSet testIntegrationSourceSet) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			IdeaPlugin.class,
			new Action<IdeaPlugin>() {

				@Override
				public void execute(IdeaPlugin ideaPlugin) {
					_configureIdeaModule(project, testIntegrationSourceSet);
				}

			});
	}

	private void _configureIdeaModule(
		Project project, final SourceSet testIntegrationSourceSet) {

		IdeaModel ideaModel = GradleUtil.getExtension(project, IdeaModel.class);

		final IdeaModule ideaModule = ideaModel.getModule();

		Map<String, Map<String, Collection<Configuration>>> scopes =
			ideaModule.getScopes();

		Map<String, Collection<Configuration>> testScope = scopes.get("TEST");

		Collection<Configuration> plusConfigurations = testScope.get("plus");

		Configuration configuration = GradleUtil.getConfiguration(
			project, testIntegrationSourceSet.getRuntimeConfigurationName());

		plusConfigurations.add(configuration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					Set<File> testSrcDirs = new LinkedHashSet<>(
						ideaModule.getTestSourceDirs());

					SourceDirectorySet sourceDirectorySet =
						testIntegrationSourceSet.getAllSource();

					testSrcDirs.addAll(sourceDirectorySet.getSrcDirs());

					ideaModule.setTestSourceDirs(testSrcDirs);
				}

			});
	}

	private void _configureTaskCheck(Test test) {
		Project project = test.getProject();

		Task task = GradleUtil.getTask(
			project, LifecycleBasePlugin.CHECK_TASK_NAME);

		task.dependsOn(test);
	}

}