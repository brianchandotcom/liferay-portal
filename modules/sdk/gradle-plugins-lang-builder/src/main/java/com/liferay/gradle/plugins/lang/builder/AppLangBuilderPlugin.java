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

import groovy.lang.Closure;

import java.io.File;

import java.util.Set;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.PluginContainer;

/**
 * @author Peter Shin
 */
public class AppLangBuilderPlugin implements Plugin<Project> {

	public static final String APP_BUILD_LANG_TASK_NAME = "appBuildLang";

	public static final String PLUGIN_NAME = "appLangBuilder";

	@Override
	@SuppressWarnings("serial")
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, BasePlugin.class);

		final AppLangBuilderExtension appLangBuilderExtension =
			GradleUtil.addExtension(
				project, PLUGIN_NAME, AppLangBuilderExtension.class);

		Configuration langBuilderConfiguration =
			LangBuilderPlugin.addConfigurationLangBuilder(project);

		final BuildLangTask appBuildLangTask = _addTaskAppBuildLang(
			project, langBuilderConfiguration);

		_configureTaskAppBuildLang(project);

		Gradle gradle = project.getGradle();

		gradle.afterProject(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(Project subproject) {
					Set<Project> subprojects =
						appLangBuilderExtension.getSubprojects();

					PluginContainer pluginContainer = subproject.getPlugins();

					if (subprojects.contains(subproject) &&
						pluginContainer.hasPlugin(LangBuilderPlugin.class)) {

						_configureTaskAppBuildLang(
							appBuildLangTask, subproject);
					}
				}

			});
	}

	private BuildLangTask _addTaskAppBuildLang(
		Project project, FileCollection classpath) {

		BuildLangTask buildLangTask = GradleUtil.addTask(
			project, APP_BUILD_LANG_TASK_NAME, BuildLangTask.class);

		buildLangTask.setClasspath(classpath);
		buildLangTask.setDescription(
			"Runs Liferay Lang Builder to translate language property files " +
				"for the app.");
		buildLangTask.setGroup(BasePlugin.BUILD_GROUP);
		buildLangTask.setLangDir(
			new File(project.getProjectDir(), "app.bnd-localization"));
		buildLangTask.setLangFileName("bundle");

		return buildLangTask;
	}

	private void _configureTaskAppBuildLang(
		BuildLangTask appBuildLangTask, Project subproject) {

		Task task = GradleUtil.getTask(
			subproject, LangBuilderPlugin.BUILD_LANG_TASK_NAME);

		appBuildLangTask.dependsOn(task);
	}

	private void _configureTaskAppBuildLang(Project project) {
		BuildLangTask buildLangTask = (BuildLangTask)GradleUtil.getTask(
			project, AppLangBuilderPlugin.APP_BUILD_LANG_TASK_NAME);

		File langFile = new File(
			buildLangTask.getLangDir(),
			buildLangTask.getLangFileName() + ".properties");

		if (!langFile.exists()) {
			buildLangTask.setEnabled(false);
		}
	}

}