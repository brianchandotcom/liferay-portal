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

package com.liferay.gradle.plugins.lang.builder;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_LANG_TASK_NAME = "buildLang";

	public static final String CONFIGURATION_NAME = "langBuilder";

	@Override
	public void apply(Project project) {
		addConfigurationLangBuilder(project);

		addTaskBuildLang(project);
	}

	protected Configuration addConfigurationLangBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Lang Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesLangBuilder(project);
				}

			});

		return configuration;
	}

	protected void addDependenciesLangBuilder(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.lang.builder", "latest.release");
	}

	protected BuildLangTask addTaskBuildLang(Project project) {
		BuildLangTask buildLangTask = GradleUtil.addTask(
			project, BUILD_LANG_TASK_NAME, BuildLangTask.class);

		buildLangTask.setDescription(
			"Runs Liferay Lang Builder to translate language property files.");

		return buildLangTask;
	}

}