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

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Andrea Di Giorgi
 */
public class LangMergerPlugin implements Plugin<Project> {

	public static final String MERGE_LANG_TASK_NAME = "mergeLang";

	@Override
	public void apply(Project project) {
		addTaskMergeLang(project);
	}

	protected MergePropertiesTask addTaskMergeLang(Project project) {
		final MergePropertiesTask mergePropertiesTask = GradleUtil.addTask(
			project, MERGE_LANG_TASK_NAME, MergePropertiesTask.class);

		mergePropertiesTask.setDescription("Merges language property files.");

		TaskOutputs taskOutputs = mergePropertiesTask.getOutputs();

		taskOutputs.upToDateWhen(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					return false;
				}

			});

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					configureTaskMergeLangForJavaPlugin(mergePropertiesTask);
				}

			});

		return mergePropertiesTask;
	}

	protected void configureTaskMergeLangForJavaPlugin(
		MergePropertiesTask mergePropertiesTask) {

		mergePropertiesTask.mustRunAfter(
			JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		final Project project = mergePropertiesTask.getProject();

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

		final Project langProject = getLangProject(project);

		if (langProject != null) {
			if (_logger.isInfoEnabled()) {
				_logger.info("Using " + langProject + " as merge source");
			}

			mergePropertiesTask.setSourceDirs(
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						File contentDir = getContentDir(sourceSet);

						return langProject.file(
							project.relativePath(contentDir));
					}

				},
				new Callable<File>() {

					@Override
					public File call() throws Exception {
						return getContentDir(sourceSet);
					}

				});
		}
		else if (_logger.isInfoEnabled()) {
			_logger.info(
				"Unable to find a language project to use as merge source");
		}

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(mergePropertiesTask);
	}

	protected File getContentDir(SourceSet sourceSet) {
		File resourcesDir = getSrcDir(sourceSet.getResources());

		return new File(resourcesDir, "content");
	}

	protected Project getLangProject(Project project) {
		Project parentProject = project.getParent();

		if (parentProject == null) {
			return null;
		}

		String langProjectPath =
			parentProject.getPath() + ":" + parentProject.getName() + "-lang";

		if (_logger.isDebugEnabled()) {
			_logger.debug("Looking for " + langProjectPath);
		}

		Project langProject = project.findProject(langProjectPath);

		if (langProject == null) {
			int index = langProjectPath.indexOf(':', 1);

			if (index != -1) {
				langProjectPath = langProjectPath.substring(index);

				if (_logger.isDebugEnabled()) {
					_logger.debug("Looking for " + langProjectPath);
				}

				langProject = project.findProject(langProjectPath);
			}
		}

		return langProject;
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final Logger _logger = Logging.getLogger(
		LangMergerPlugin.class);

}