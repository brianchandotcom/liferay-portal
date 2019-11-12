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

package com.liferay.gradle.plugins.soy;

import com.liferay.gradle.plugins.soy.internal.SoyPluginConstants;
import com.liferay.gradle.plugins.soy.internal.util.GradleUtil;
import com.liferay.gradle.plugins.soy.tasks.ReplaceSoyTranslationTask;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author     Andrea Di Giorgi
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class SoyTranslationPlugin implements Plugin<Project> {

	public static final String REPLACE_SOY_TRANSLATION_TASK_NAME =
		"replaceSoyTranslation";

	@Override
	public void apply(Project project) {
		_addTaskReplaceSoyTranslation(project);
	}

	@SuppressWarnings("rawtypes")
	private ReplaceSoyTranslationTask _addTaskReplaceSoyTranslation(
		Project project) {

		final ReplaceSoyTranslationTask replaceSoyTranslationTask =
			GradleUtil.addTask(
				project, REPLACE_SOY_TRANSLATION_TASK_NAME,
				ReplaceSoyTranslationTask.class);

		replaceSoyTranslationTask.mustRunAfter(
			new OptionalTaskCallable(project, _CONFIG_JS_MODULES_TASK_NAME),
			new OptionalTaskCallable(project, _TRANSPILE_JS_TASK_NAME));
		replaceSoyTranslationTask.setDescription(
			"Replaces 'goog.getMsg' definitions.");
		replaceSoyTranslationTask.setGroup(BasePlugin.BUILD_GROUP);
		replaceSoyTranslationTask.setIncludes(
			Collections.singleton("**/*.soy.js"));

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withId(
			SoyPluginConstants.JS_MODULE_CONFIG_GENERATOR_PLUGIN_ID,
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					replaceSoyTranslationTask.dependsOn(
						SoyPluginConstants.CONFIG_JS_MODULES_TASK_NAME);
				}

			});

		pluginContainer.withId(
			SoyPluginConstants.JS_TRANSPILER_PLUGIN_ID,
			new Action<Plugin>() {

				@Override
				public void execute(Plugin plugin) {
					replaceSoyTranslationTask.dependsOn(
						SoyPluginConstants.TRANSPILE_JS_TASK_NAME);
				}

			});

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskReplaceSoyTranslationForJavaPlugin(
						replaceSoyTranslationTask);
				}

			});

		return replaceSoyTranslationTask;
	}

	private void _configureTaskReplaceSoyTranslationForJavaPlugin(
		final ReplaceSoyTranslationTask replaceSoyTranslationTask) {

		replaceSoyTranslationTask.dependsOn(
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		replaceSoyTranslationTask.setSource(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSet sourceSet = GradleUtil.getSourceSet(
						replaceSoyTranslationTask.getProject(),
						SourceSet.MAIN_SOURCE_SET_NAME);

					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return sourceSetOutput.getResourcesDir();
				}

			});

		Task classesTask = GradleUtil.getTask(
			replaceSoyTranslationTask.getProject(),
			JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(replaceSoyTranslationTask);
	}

	private static final String _CONFIG_JS_MODULES_TASK_NAME =
		"configJSModules";

	private static final String _TRANSPILE_JS_TASK_NAME = "transpileJS";

	private static class OptionalTaskCallable implements Callable<Task> {

		public OptionalTaskCallable(Project project, String name) {
			_project = project;
			_name = name;
		}

		@Override
		public Task call() throws Exception {
			TaskContainer taskContainer = _project.getTasks();

			return taskContainer.findByName(_name);
		}

		private final String _name;
		private final Project _project;

	}

}