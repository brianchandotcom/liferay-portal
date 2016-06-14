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

package com.liferay.gradle.plugins.poshi.runner;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.OSDetector;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.nio.charset.StandardCharsets;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.reporting.DirectoryReport;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestTaskReports;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerPlugin implements Plugin<Project> {

	public static final String EVALUATE_POSHI_CONSOLE_TASK_NAME =
		"evaluatePoshiConsole";

	public static final String EXPAND_POSHI_RUNNER_TASK_NAME =
		"expandPoshiRunner";

	public static final String POSHI_RUNNER_CONFIGURATION_NAME = "poshiRunner";

	public static final String RUN_POSHI_TASK_NAME = "runPoshi";

	public static final String SIKULI_CONFIGURATION_NAME = "sikuli";

	public static final String VALIDATE_POSHI_TASK_NAME = "validatePoshi";

	public static final String WRITE_POSHI_PROPERTIES_TASK_NAME =
		"writePoshiProperties";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);

		final PoshiRunnerExtension poshiRunnerExtension =
			GradleUtil.addExtension(
				project, "poshiRunner", PoshiRunnerExtension.class);

		addConfigurationPoshiRunner(project, poshiRunnerExtension);
		addConfigurationSikuli(project, poshiRunnerExtension);

		final JavaExec evaluatePoshiConsoleTask = addTaskEvaluatePoshiConsole(
			project);

		addTaskExpandPoshiRunner(project);

		final Test runPoshiTask = addTaskRunPoshi(project);
		final JavaExec validatePoshiTask = addTaskValidatePoshi(project);
		final JavaExec writePoshiPropertiesTask = addTaskWritePoshiProperties(
			project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					Properties poshiProperties = null;

					File poshiPropertiesFile =
						poshiRunnerExtension.getPoshiPropertiesFile();

					if ((poshiPropertiesFile != null) &&
						poshiPropertiesFile.exists()) {

						poshiProperties = GUtil.loadProperties(
							poshiPropertiesFile);
					}

					configureTaskEvaluatePoshiConsole(
						evaluatePoshiConsoleTask, poshiProperties,
						poshiRunnerExtension);
					configureTaskRunPoshi(
						runPoshiTask, poshiProperties, poshiRunnerExtension);
					configureTaskValidatePoshi(
						validatePoshiTask, poshiProperties,
						poshiRunnerExtension);
					configureTaskWritePoshiProperties(
						writePoshiPropertiesTask, poshiProperties,
						poshiRunnerExtension);
				}

			});
	}

	protected Configuration addConfigurationPoshiRunner(
		final Project project,
		final PoshiRunnerExtension poshiRunnerExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, POSHI_RUNNER_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Poshi Runner for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesPoshiRunner(project, poshiRunnerExtension);
				}

			});

		return configuration;
	}

	protected Configuration addConfigurationSikuli(
		final Project project,
		final PoshiRunnerExtension poshiRunnerExtension) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, SIKULI_CONFIGURATION_NAME);

		configuration.setDescription("Configures Sikuli for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesSikuli(project, poshiRunnerExtension);
				}

			});

		return configuration;
	}

	protected void addDependenciesPoshiRunner(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

		GradleUtil.addDependency(
			project, POSHI_RUNNER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.poshi.runner", poshiRunnerExtension.getVersion());
	}

	protected void addDependenciesSikuli(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

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

		GradleUtil.addDependency(
			project, SIKULI_CONFIGURATION_NAME, "org.bytedeco.javacpp-presets",
			"opencv", poshiRunnerExtension.getOpenCVVersion(), classifier,
			true);
	}

	protected JavaExec addTaskEvaluatePoshiConsole(Project project) {
		JavaExec javaExec = GradleUtil.addTask(
			project, EVALUATE_POSHI_CONSOLE_TASK_NAME, JavaExec.class);

		javaExec.setClasspath(getPoshiRunnerClasspath(project));
		javaExec.setDescription("Evaluate the console output errors.");
		javaExec.setGroup("verification");
		javaExec.setMain(
			"com.liferay.poshi.runner.PoshiRunnerConsoleEvaluator");

		return javaExec;
	}

	protected Copy addTaskExpandPoshiRunner(final Project project) {
		Copy copy = GradleUtil.addTask(
			project, EXPAND_POSHI_RUNNER_TASK_NAME, Copy.class);

		Closure<Void> closure = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public FileTree doCall() {
				Configuration configuration = GradleUtil.getConfiguration(
					project, POSHI_RUNNER_CONFIGURATION_NAME);

				Iterator<File> iterator = configuration.iterator();

				while (iterator.hasNext()) {
					File file = iterator.next();

					String fileName = file.getName();

					if (fileName.startsWith("com.liferay.poshi.runner-")) {
						return project.zipTree(file);
					}
				}

				return null;
			}

		};

		copy.from(closure);

		copy.into(getExpandedPoshiRunnerDir(project));

		return copy;
	}

	@SuppressWarnings("rawtypes")
	protected Test addTaskRunPoshi(Project project) {
		final Test test = GradleUtil.addTask(
			project, RUN_POSHI_TASK_NAME, Test.class);

		test.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(RUN_POSHI_TASK_NAME),
			EXPAND_POSHI_RUNNER_TASK_NAME);

		test.include("com/liferay/poshi/runner/PoshiRunner.class");
		test.setClasspath(getPoshiRunnerClasspath(project));
		test.setDefaultCharacterEncoding(StandardCharsets.UTF_8.toString());
		test.setDescription("Execute tests using Poshi Runner.");
		test.setGroup("verification");
		test.setScanForTestClasses(false);
		test.setTestClassesDir(getExpandedPoshiRunnerDir(project));

		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setShowStandardStreams(true);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withId(
			"com.liferay.test.integration",
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					test.dependsOn(_START_TESTABLE_TOMCAT_TASK_NAME);

					Task task = GradleUtil.getTask(
						test.getProject(), _STOP_TESTABLE_TOMCAT_TASK_NAME);

					task.mustRunAfter(test);
				}

			});

		return test;
	}

	protected JavaExec addTaskValidatePoshi(Project project) {
		JavaExec javaExec = GradleUtil.addTask(
			project, VALIDATE_POSHI_TASK_NAME, JavaExec.class);

		javaExec.setClasspath(getPoshiRunnerClasspath(project));
		javaExec.setDescription("Validates the Poshi files syntax.");
		javaExec.setGroup("verification");
		javaExec.setMain("com.liferay.poshi.runner.PoshiRunnerValidation");

		return javaExec;
	}

	protected JavaExec addTaskWritePoshiProperties(Project project) {
		JavaExec javaExec = GradleUtil.addTask(
			project, WRITE_POSHI_PROPERTIES_TASK_NAME, JavaExec.class);

		javaExec.setClasspath(getPoshiRunnerClasspath(project));
		javaExec.setDescription("Write the Poshi properties files.");
		javaExec.setGroup("verification");
		javaExec.setMain("com.liferay.poshi.runner.PoshiRunnerContext");

		return javaExec;
	}

	protected void configureTaskEvaluatePoshiConsole(
		JavaExec javaExec, Properties poshiProperties,
		PoshiRunnerExtension poshiRunnerExtension) {

		populateSystemProperties(
			javaExec.getSystemProperties(), poshiProperties,
			poshiRunnerExtension);
	}

	protected void configureTaskRunPoshi(
		Test test, Properties poshiProperties,
		PoshiRunnerExtension poshiRunnerExtension) {

		configureTaskRunPoshiBinResultsDir(test);
		configureTaskRunPoshiReports(test);
		populateSystemProperties(
			test.getSystemProperties(), poshiProperties, poshiRunnerExtension);
	}

	protected void configureTaskRunPoshiBinResultsDir(Test test) {
		if (test.getBinResultsDir() != null) {
			return;
		}

		Project project = test.getProject();

		test.setBinResultsDir(
			project.file("test-results/binary/" + RUN_POSHI_TASK_NAME));
	}

	protected void configureTaskRunPoshiReports(Test test) {
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

	protected void configureTaskValidatePoshi(
		JavaExec javaExec, Properties poshiProperties,
		PoshiRunnerExtension poshiRunnerExtension) {

		populateSystemProperties(
			javaExec.getSystemProperties(), poshiProperties,
			poshiRunnerExtension);
	}

	protected void configureTaskWritePoshiProperties(
		JavaExec javaExec, Properties poshiProperties,
		PoshiRunnerExtension poshiRunnerExtension) {

		populateSystemProperties(
			javaExec.getSystemProperties(), poshiProperties,
			poshiRunnerExtension);
	}

	protected File getExpandedPoshiRunnerDir(Project project) {
		return new File(project.getBuildDir(), "poshi-runner");
	}

	protected FileCollection getPoshiRunnerClasspath(Project project) {
		Configuration poshiRunnerConfiguration = GradleUtil.getConfiguration(
			project, POSHI_RUNNER_CONFIGURATION_NAME);

		Configuration sikuliConfiguration = GradleUtil.getConfiguration(
			project, SIKULI_CONFIGURATION_NAME);

		return project.files(poshiRunnerConfiguration, sikuliConfiguration);
	}

	protected void populateSystemProperties(
		Map<String, Object> systemProperties, Properties poshiProperties,
		PoshiRunnerExtension poshiRunnerExtension) {

		if (poshiProperties != null) {
			Enumeration<String> enumeration =
				(Enumeration<String>)poshiProperties.propertyNames();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();
				String value = poshiProperties.getProperty(key);

				systemProperties.put(key, value);
			}
		}

		systemProperties.putAll(poshiRunnerExtension.getPoshiProperties());

		File baseDir = poshiRunnerExtension.getBaseDir();

		if ((baseDir != null) && baseDir.exists()) {
			systemProperties.put(
				"test.base.dir.name",
				poshiRunnerExtension.project.relativePath(baseDir));
		}

		List<String> testNames = poshiRunnerExtension.getTestNames();

		if (!testNames.isEmpty()) {
			systemProperties.put(
				"test.name", CollectionUtils.join(",", testNames));
		}

		String testName = GradleUtil.getProperty(
			poshiRunnerExtension.project, "poshiTestName", (String)null);

		if (Validator.isNotNull(testName)) {
			systemProperties.put("test.name", testName);
		}
	}

	private static final String _START_TESTABLE_TOMCAT_TASK_NAME =
		"startTestableTomcat";

	private static final String _STOP_TESTABLE_TOMCAT_TASK_NAME =
		"stopTestableTomcat";

}