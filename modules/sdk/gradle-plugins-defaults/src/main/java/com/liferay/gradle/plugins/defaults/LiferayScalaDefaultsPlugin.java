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

import com.github.maiflai.ScalaTestPlugin;

import com.liferay.gradle.plugins.SourceFormatterDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.source.formatter.SourceFormatterPlugin;

import java.io.File;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.scala.ScalaPlugin;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.logging.TestExceptionFormat;
import org.gradle.api.tasks.testing.logging.TestLogEvent;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;
import org.gradle.jvm.tasks.Jar;
import org.gradle.language.scala.tasks.AbstractScalaCompile;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.idea.IdeaPlugin;

/**
 * @author Peter Shin
 */
public class LiferayScalaDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		_applyPlugins(project);

		File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		GradlePluginsDefaultsUtil.configureRepositories(project, portalRootDir);

		_configureProject(project);

		_configureTaskCompileScala(project);
		_configureTaskJar(project);
		_configureTaskTest(project);
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, EclipsePlugin.class);
		GradleUtil.applyPlugin(project, IdeaPlugin.class);
		GradleUtil.applyPlugin(project, ScalaPlugin.class);
		GradleUtil.applyPlugin(project, ScalaTestPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterDefaultsPlugin.class);
		GradleUtil.applyPlugin(project, SourceFormatterPlugin.class);
	}

	private void _configureProject(Project project) {
		project.setGroup(GradleUtil.getProjectGroup(project, _GROUP));
	}

	private void _configureTaskCompileScala(Project project) {
		AbstractScalaCompile abstractScalaCompile =
			(AbstractScalaCompile)GradleUtil.fetchTask(project, "compileScala");

		if (abstractScalaCompile != null) {
			abstractScalaCompile.setSourceCompatibility(
				JavaVersion.VERSION_1_8.toString());
			abstractScalaCompile.setTargetCompatibility(
				JavaVersion.VERSION_1_8.toString());
		}
	}

	private void _configureTaskJar(final Project project) {
		Jar jarTask = (Jar)GradleUtil.fetchTask(
			project, JavaPlugin.JAR_TASK_NAME);

		if (jarTask != null) {
			jarTask.exclude(
				"META-INF/*.DSA", "META-INF/*.RSA", "META-INF/*.SF");

			jarTask.from(
				new Callable<FileTree>() {

					@Override
					public FileTree call() throws Exception {
						FileCollection files = project.files();

						ConfigurationContainer configurationContainer =
							project.getConfigurations();

						Configuration compileConfiguration =
							configurationContainer.getByName(
								JavaPlugin.COMPILE_CONFIGURATION_NAME);

						for (File file : compileConfiguration) {
							if (file.isDirectory()) {
								files = files.plus(project.files(file));
							}
							else {
								files = files.plus(project.zipTree(file));
							}
						}

						return files.getAsFileTree();
					}

				});
		}
	}

	private void _configureTaskTest(Project project) {
		Test test = (Test)GradleUtil.fetchTask(
			project, JavaPlugin.TEST_TASK_NAME);

		if (test != null) {
			test.setIgnoreFailures(false);

			TestLoggingContainer testLoggingContainer = test.getTestLogging();

			Set<TestLogEvent> testLogEvents = new HashSet<>();

			testLogEvents.add(TestLogEvent.FAILED);
			testLogEvents.add(TestLogEvent.PASSED);
			testLogEvents.add(TestLogEvent.SKIPPED);
			testLogEvents.add(TestLogEvent.STANDARD_ERROR);
			testLogEvents.add(TestLogEvent.STARTED);

			testLoggingContainer.setEvents(testLogEvents);

			testLoggingContainer.setExceptionFormat(TestExceptionFormat.FULL);
			testLoggingContainer.setShowCauses(true);
			testLoggingContainer.setShowExceptions(true);
			testLoggingContainer.setShowStackTraces(true);
		}
	}

	private static final String _GROUP = "com.liferay";

}