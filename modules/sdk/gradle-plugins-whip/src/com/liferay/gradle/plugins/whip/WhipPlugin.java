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

package com.liferay.gradle.plugins.whip;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.testing.Test;

/**
 * @author Andrea Di Giorgi
 */
public class WhipPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "whip";

	public static final String EXTENSION_NAME = "whip";

	@Override
	public void apply(Project project) {
		addWhipConfiguration(project);

		GradleUtil.addExtension(project, EXTENSION_NAME, WhipExtension.class);

		applyToDefaultTasks(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addWhipDependencies(
						project, JavaPlugin.TEST_RUNTIME_CONFIGURATION_NAME);
				}

			});
	}

	protected Configuration addWhipConfiguration(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Whip for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addWhipDependencies(project, configuration.getName());
				}

			});

		return configuration;
	}

	protected void addWhipDependencies(
		Project project, String configurationName) {

		WhipExtension whipExtension = GradleUtil.getExtension(
			project, WhipExtension.class);

		GradleUtil.addDependency(
			project, configurationName, "com.liferay", "com.liferay.whip",
			whipExtension.getVersion());
	}

	protected void applyToDefaultTasks(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					WhipExtension whipExtension = GradleUtil.getExtension(
						test.getProject(), WhipExtension.class);

					whipExtension.applyTo(test);
				}

			});
	}

}