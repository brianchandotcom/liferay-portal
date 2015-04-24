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

package com.liferay.poshi.runner.gradle;

import com.liferay.poshi.runner.util.OSDetector;

import java.io.File;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.FileTree;
import org.gradle.api.initialization.dsl.ScriptHandler;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.reporting.DirectoryReport;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestTaskReports;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		final PoshiRunnerExtension poshiRunnerExtension =
			addPoshiRunnerExtension(project);

		addPoshiRunnerConfiguration(project, poshiRunnerExtension);

		addTasksExpandPoshiRunner(project);
		addTasksRunPoshi(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksExpandPoshiRunner(project);
					configureTasksRunPoshi(project, poshiRunnerExtension);
				}

			});
	}

	protected Configuration addPoshiRunnerConfiguration(
		final Project project,
		final PoshiRunnerExtension poshiRunnerExtension) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final Configuration configuration = configurationContainer.create(
			_POSHI_RUNNER_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Poshi Runner for this project.");
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
						addPoshiRunnerDependenciesPoshiRunner(
							project, poshiRunnerExtension);
						addPoshiRunnerDependenciesSikuli(project);
					}
				}

			});

		return configuration;
	}

	protected void addPoshiRunnerDependenciesPoshiRunner(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

		DependencyHandler dependencyHandler = project.getDependencies();

		dependencyHandler.add(
			_POSHI_RUNNER_CONFIGURATION_NAME,
			"com.liferay:com.liferay.poshi.runner:" +
				getPoshiRunnerVersion(project));
	}

	protected void addPoshiRunnerDependenciesSikuli(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		String bitMode = OSDetector.getBitmode();

		if (bitMode.equals("32")) {
			bitMode = "x86";
		}
		else {
			bitMode = "x86_64";
		}

		String os = "linux";

		if (OSDetector.isApple()) {
			os = "macosx";
		}
		else if (OSDetector.isWindows()) {
			os = "windows";
		}

		String classifier = os + "-" + bitMode;

		dependencyHandler.add(
			_POSHI_RUNNER_CONFIGURATION_NAME,
			"org.bytedeco.javacpp-presets:opencv:2.4.9-0.9:" + classifier);
	}

	protected PoshiRunnerExtension addPoshiRunnerExtension(Project project) {
		ExtensionContainer extensionContainer = project.getExtensions();

		return extensionContainer.create(
			"poshiRunner", PoshiRunnerExtension.class, project);
	}

	protected Copy addTasksExpandPoshiRunner(Project project) {
		TaskContainer taskContainer = project.getTasks();

		Copy copy = taskContainer.create(
			_EXPAND_POSHI_RUNNER_TASK_NAME, Copy.class);

		copy.into(getExpandedPoshiRunnerDir(project));

		return copy;
	}

	protected Test addTasksRunPoshi(Project project) {
		TaskContainer taskContainer = project.getTasks();

		Test test = taskContainer.create(_RUN_POSHI_TASK_NAME, Test.class);

		test.dependsOn(_EXPAND_POSHI_RUNNER_TASK_NAME);

		test.include("com/liferay/poshi/runner/PoshiRunner.class");

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration = configurationContainer.getByName(
			_POSHI_RUNNER_CONFIGURATION_NAME);

		test.setClasspath(configuration);
		test.setDescription("Execute tests using Poshi Runner.");
		test.setGroup("verification");
		test.setScanForTestClasses(false);
		test.setTestClassesDir(getExpandedPoshiRunnerDir(project));

		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setShowStandardStreams(true);

		test.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Test test = (Test)task;

					Map<String, Object> systemProperties =
						test.getSystemProperties();

					if (!systemProperties.containsKey("test.name")) {
						throw new GradleException(
							"Please set the property poshiTestName.");
					}
				}

		});

		return test;
	}

	protected void configureTasksExpandPoshiRunner(Project project) {
		TaskContainer taskContainer = project.getTasks();

		Copy copy = (Copy)taskContainer.getByName(
			_EXPAND_POSHI_RUNNER_TASK_NAME);

		configureTasksExpandPoshiRunnerFrom(copy);
	}

	protected void configureTasksExpandPoshiRunnerFrom(Copy copy) {
		Project project = copy.getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration = configurationContainer.getByName(
			_POSHI_RUNNER_CONFIGURATION_NAME);

		Iterator<File> iterator = configuration.iterator();

		while (iterator.hasNext()) {
			File file = iterator.next();

			String fileName = file.getName();

			if (fileName.startsWith("com.liferay.poshi.runner-")) {
				FileTree fileTree = project.zipTree(file);

				copy.from(fileTree);

				return;
			}
		}
	}

	protected void configureTasksRunPoshi(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

		TaskContainer taskContainer = project.getTasks();

		Test test = (Test)taskContainer.getByName(_RUN_POSHI_TASK_NAME);

		configureTasksRunPoshiBinResultsDir(test);
		configureTasksRunPoshiReports(test);
		configureTasksRunPoshiSystemProperties(test, poshiRunnerExtension);
	}

	protected void configureTasksRunPoshiBinResultsDir(Test test) {
		if (test.getBinResultsDir() != null) {
			return;
		}

		Project project = test.getProject();

		test.setBinResultsDir(
			project.file("test-results/binary/" + _RUN_POSHI_TASK_NAME));
	}

	protected void configureTasksRunPoshiReports(Test test) {
		Project project = test.getProject();
		TestTaskReports testTaskReports = test.getReports();

		DirectoryReport directoryReport = testTaskReports.getHtml();

		if (directoryReport.getDestination() == null) {
			directoryReport.setDestination(project.file("tests"));
		}

		directoryReport = testTaskReports.getJunitXml();

		if (directoryReport.getDestination() == null) {
			directoryReport.setDestination(project.file("test-results"));
		}
	}

	protected void configureTasksRunPoshiSystemProperties(
		Test test, PoshiRunnerExtension poshiRunnerExtension) {

		Map<String, Object> systemProperties = test.getSystemProperties();

		systemProperties.putAll(poshiRunnerExtension.getPoshiProperties());

		systemProperties.put("test.basedir", poshiRunnerExtension.getBaseDir());

		Project project = test.getProject();

		if (project.hasProperty("poshiTestName")) {
			systemProperties.put(
				"test.name", project.property("poshiTestName"));
		}
	}

	protected File getExpandedPoshiRunnerDir(Project project) {
		return new File(project.getBuildDir(), "poshi-runner");
	}

	protected String getPoshiRunnerVersion(Project project) {
		ScriptHandler scriptHandler = project.getBuildscript();

		ConfigurationContainer configurationContainer =
			scriptHandler.getConfigurations();

		Configuration configuration = configurationContainer.getByName(
			ScriptHandler.CLASSPATH_CONFIGURATION);

		for (Dependency dependency : configuration.getDependencies()) {
			String group = dependency.getGroup();
			String name = dependency.getName();

			if (group.equals("com.liferay") &&
				name.equals("com.liferay.poshi.runner")) {

				return dependency.getVersion();
			}
		}

		return "latest.release";
	}

	private static final String _EXPAND_POSHI_RUNNER_TASK_NAME =
		"expandPoshiRunner";

	private static final String _POSHI_RUNNER_CONFIGURATION_NAME =
		"poshiRunner";

	private static final String _RUN_POSHI_TASK_NAME = "runPoshi";

}