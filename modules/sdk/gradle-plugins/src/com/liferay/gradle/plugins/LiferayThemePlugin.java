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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayThemeExtension;
import com.liferay.gradle.plugins.tasks.BuildThumbnailsTask;
import com.liferay.gradle.plugins.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemePlugin extends LiferayWebAppPlugin {

	@Override
	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayThemeExtension.class);
	}

	protected BuildThumbnailsTask addTaskBuildThumbnails(Project project) {
		BuildThumbnailsTask buildThumbnailsTask = GradleUtil.addTask(
			project, _BUILD_THUMBNAILS_TASK_NAME, BuildThumbnailsTask.class);

		buildThumbnailsTask.setDescription("Generates thumbnails.");
		buildThumbnailsTask.setGroup(BasePlugin.BUILD_GROUP);

		return buildThumbnailsTask;
	}

	@Override
	protected void addTasks(
		Project project, LiferayExtension liferayExtension) {

		super.addTasks(project, liferayExtension);

		addTaskBuildThumbnails(project);
	}

	@Override
	protected void configureDependenciesCompile(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependenciesCompile(project, liferayExtension);

		for (String dependencyNotation : _THEME_COMPILE_DEPENDENCY_NOTATIONS) {
			GradleUtil.addDependency(
				project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
				dependencyNotation);
		}
	}

	protected void configureDependenciesProvidedCompile(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependenciesProvidedCompile(project, liferayExtension);

		GradleUtil.removeDependencies(
			project, WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			_THEME_COMPILE_DEPENDENCY_NOTATIONS);
	}

	protected void configureTaskBuildThumbnails(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		BuildThumbnailsTask buildThumbnailsTask =
			(BuildThumbnailsTask)GradleUtil.getTask(
				project, _BUILD_THUMBNAILS_TASK_NAME);

		configureTaskBuildThumbnailsImagesDir(
			buildThumbnailsTask, liferayThemeExtension);
	}

	protected void configureTaskBuildThumbnailsImagesDir(
		BuildThumbnailsTask buildThumbnailsTask,
		LiferayThemeExtension liferayThemeExtension) {

		if (buildThumbnailsTask.getImagesDir() == null) {
			File imagesDir = new File(
				liferayThemeExtension.getDiffsDir(), "images");

			buildThumbnailsTask.setImagesDir(imagesDir);
		}
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		LiferayThemeExtension liferayThemeExtension =
			(LiferayThemeExtension)liferayExtension;

		configureTaskBuildThumbnails(project, liferayThemeExtension);
	}

	private static final String _BUILD_THUMBNAILS_TASK_NAME = "buildThumbnails";

	private static final String[] _THEME_COMPILE_DEPENDENCY_NOTATIONS = {
		"com.liferay.portal:util-bridges:default",
		"com.liferay.portal:util-java:default",
		"com.liferay.portal:util-taglib:default",
		"commons-logging:commons-logging:1.1.1", "log4j:log4j:1.2.16"
	};

}