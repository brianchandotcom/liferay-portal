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

package com.liferay.gradle.plugins.lang.merger;

import com.liferay.gradle.plugins.lang.merger.tasks.MergePropertiesTask;
import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;

/**
 * @author Andrea Di Giorgi
 */
public class LangMergerPlugin implements Plugin<Project> {

	public static final String MERGE_LANG_TASK_NAME = "mergeLang";

	@Override
	public void apply(Project project) {
		_addTaskMergeLang(project);
	}

	private MergePropertiesTask _addTaskMergeLang(Project project) {
		final MergePropertiesTask mergePropertiesTask = GradleUtil.addTask(
			project, MERGE_LANG_TASK_NAME, MergePropertiesTask.class);

		mergePropertiesTask.setDescription("Merges language property files.");

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskMergeLangForJavaPlugin(mergePropertiesTask);
				}

			});

		return mergePropertiesTask;
	}

	private void _configureTaskMergeLangForJavaPlugin(
		MergePropertiesTask mergePropertiesTask) {

		mergePropertiesTask.mustRunAfter(
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		final Project project = mergePropertiesTask.getProject();

		Logger logger = project.getLogger();

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		mergePropertiesTask.setDestinationDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return new File(
						sourceSetOutput.getResourcesDir(), "content");
				}

			});

		final Project langProject = _getLangProject(project);

		if (langProject != null) {
			if (logger.isInfoEnabled()) {
				logger.info("Using {} as merge source", langProject);
			}

			mergePropertiesTask.setSourceDirs(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						File contentDir = _getContentDir(sourceSet);

						return langProject.file(
							project.relativePath(contentDir));
					}

				},
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return _getContentDir(sourceSet);
					}

				});
		}
		else if (logger.isInfoEnabled()) {
			logger.info(
				"Unable to find a language project to use as merge source");
		}

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(mergePropertiesTask);
	}

	private File _getContentDir(SourceSet sourceSet) {
		File resourcesDir = GradleUtil.getSrcDir(sourceSet.getResources());

		return new File(resourcesDir, "content");
	}

	private Project _getLangProject(Project project) {
		Project parentProject = project.getParent();

		if (parentProject == null) {
			return null;
		}

		Logger logger = project.getLogger();

		String langProjectPath =
			parentProject.getPath() + ":" + parentProject.getName() + "-lang";

		if (logger.isDebugEnabled()) {
			logger.debug("Looking for {}", langProjectPath);
		}

		Project langProject = project.findProject(langProjectPath);

		if (langProject == null) {
			int index = langProjectPath.indexOf(':', 1);

			if (index != -1) {
				langProjectPath = langProjectPath.substring(index);

				if (logger.isDebugEnabled()) {
					logger.debug("Looking for {}", langProjectPath);
				}

				langProject = project.findProject(langProjectPath);
			}
		}

		return langProject;
	}

}