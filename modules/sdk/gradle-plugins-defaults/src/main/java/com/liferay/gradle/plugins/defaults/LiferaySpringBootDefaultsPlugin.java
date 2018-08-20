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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.SourceFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.logging.TestExceptionFormat;
import org.gradle.api.tasks.testing.logging.TestLogEvent;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;
import org.gradle.language.base.plugins.LifecycleBasePlugin;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;

import org.springframework.boot.gradle.plugin.SpringBootPlugin;
import org.springframework.boot.gradle.run.BootRunTask;

/**
 * @author Peter Shin
 * @author Andrea Di Giorgi
 */
public class LiferaySpringBootDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		_applyPlugins(project);

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		GradlePluginsDefaultsUtil.configureRepositories(project, portalRootDir);

		_configureProject(project);

		_addTaskRun(project);

		_configureTaskBootRun(project);
		_configureTaskCheck(project);
		_configureTaskTest(project);
		_configureTaskTestIntegration(project);
	}

	private Task _addTaskRun(Project project) {
		Task task = project.task(ApplicationPlugin.TASK_RUN_NAME);

		BootRunTask bootRunTask = (BootRunTask)GradleUtil.getTask(
			project, "bootRun");

		task.dependsOn(bootRunTask);
		task.setDescription(
			"Runs Spring Boot '" + bootRunTask.getName() + "' task.");

		task.setGroup(BasePlugin.BUILD_GROUP);

		return task;
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, JavaPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
		GradleUtil.applyPlugin(project, SpringBootPlugin.class);
		GradleUtil.applyPlugin(project, TestIntegrationBasePlugin.class);
	}

	private void _configureProject(Project project) {
		String group = GradleUtil.getGradlePropertiesValue(
			project, "project.group", _GROUP);

		project.setGroup(group);
	}

	private void _configureTaskBootRun(Project project) {
		String springBootJavaOpts = System.getenv("SPRING_BOOT_JAVA_OPTS");

		if (Validator.isNotNull(springBootJavaOpts)) {
			BootRunTask bootRunTask = (BootRunTask)GradleUtil.getTask(
				project, "bootRun");

			bootRunTask.setJvmArgs(Collections.singleton(springBootJavaOpts));
		}
	}

	private void _configureTaskCheck(Project project) {
		Task checkTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CHECK_TASK_NAME);

		Task testIntegrationTask = GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		checkTask.dependsOn(testIntegrationTask);
	}

	private void _configureTaskTest(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, JavaPlugin.TEST_TASK_NAME);

		test.setIgnoreFailures(false);

		_configureTaskTestLogging(test);
	}

	private void _configureTaskTestIntegration(Project project) {
		Test test = (Test)GradleUtil.getTask(
			project, TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME);

		test.setIgnoreFailures(false);

		_configureTaskTestLogging(test);
	}

	private void _configureTaskTestLogging(Test test) {
		Set<TestLogEvent> testLogEvents = new HashSet<>();

		testLogEvents.add(TestLogEvent.FAILED);
		testLogEvents.add(TestLogEvent.PASSED);
		testLogEvents.add(TestLogEvent.SKIPPED);
		testLogEvents.add(TestLogEvent.STANDARD_ERROR);
		testLogEvents.add(TestLogEvent.STARTED);

		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setEvents(testLogEvents);
		testLoggingContainer.setExceptionFormat(TestExceptionFormat.FULL);
		testLoggingContainer.setShowCauses(true);
		testLoggingContainer.setShowExceptions(true);
		testLoggingContainer.setShowStackTraces(true);
	}

	private static final String _GROUP = "com.liferay";

}