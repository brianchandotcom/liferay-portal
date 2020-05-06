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

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.util.PortalTools;

import java.util.Collections;
import java.util.EnumSet;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.logging.TestExceptionFormat;
import org.gradle.api.tasks.testing.logging.TestLogEvent;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;
import org.gradle.jvm.tasks.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class JavaDefaultsPlugin extends BaseDefaultsPlugin<JavaPlugin> {

	public static final Plugin<Project> INSTANCE = new JavaDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(Project project, JavaPlugin javaPlugin) {
		_configureTasksJar(project, PortalTools.getPortalVersion(project));

		_configureTasksTest(project);
	}

	@Override
	protected Class<JavaPlugin> getPluginClass() {
		return JavaPlugin.class;
	}

	private JavaDefaultsPlugin() {
	}

	private void _configureTaskJarEnabled(Jar jar, String portalVersion) {
		if (PortalTools.PORTAL_VERSION_7_0_X.equals(portalVersion)) {
			return;
		}

		Project project = jar.getProject();

		String name = project.getName();

		if (name.endsWith("-test")) {
			jar.setEnabled(false);
		}
	}

	private void _configureTasksJar(
		Project project, final String portalVersion) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Jar.class,
			new Action<Jar>() {

				@Override
				public void execute(Jar jar) {
					String taskName = jar.getName();

					if (taskName.startsWith(JavaPlugin.JAR_TASK_NAME)) {
						_configureTaskJarEnabled(jar, portalVersion);
					}
				}

			});
	}

	private void _configureTasksTest(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					String taskName = test.getName();

					if (taskName.startsWith(JavaPlugin.TEST_TASK_NAME)) {
						_configureTaskTestLogging(test);
					}
				}

			});
	}

	private void _configureTaskTestLogging(Test test) {
		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setEvents(EnumSet.allOf(TestLogEvent.class));
		testLoggingContainer.setExceptionFormat(TestExceptionFormat.FULL);
		testLoggingContainer.setStackTraceFilters(Collections.emptyList());
	}

}