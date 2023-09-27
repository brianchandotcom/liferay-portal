/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.soy;

import com.liferay.gradle.plugins.soy.internal.SoyPluginConstants;
import com.liferay.gradle.plugins.soy.task.ReplaceSoyTranslationTask;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaLibraryPlugin;
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
			JavaLibraryPlugin.class,
			new Action<JavaLibraryPlugin>() {

				@Override
				public void execute(JavaLibraryPlugin javaLibraryPlugin) {
					_configureTaskReplaceSoyTranslationForJavaLibraryPlugin(
						replaceSoyTranslationTask);
				}

			});

		return replaceSoyTranslationTask;
	}

	private void _configureTaskReplaceSoyTranslationForJavaLibraryPlugin(
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