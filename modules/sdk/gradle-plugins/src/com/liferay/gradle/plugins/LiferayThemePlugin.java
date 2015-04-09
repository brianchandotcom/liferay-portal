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
import com.liferay.gradle.plugins.tasks.BuildCss;
import com.liferay.gradle.plugins.tasks.BuildThumbnails;
import com.liferay.gradle.plugins.tasks.CompileTheme;
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.GradleUtil;
import com.liferay.gradle.plugins.util.Validator;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemePlugin extends LiferayWebAppPlugin {

	public static final String BUILD_THUMBNAILS_TASK_NAME = "buildThumbnails";

	public static final String COMPILE_THEME_TASK_NAME = "compileTheme";

	@Override
	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayThemeExtension.class);
	}

	@Override
	protected BuildCss addTaskBuildCss(Project project) {
		BuildCss buildCss = super.addTaskBuildCss(project);

		buildCss.dependsOn(COMPILE_THEME_TASK_NAME);

		return buildCss;
	}

	protected BuildThumbnails addTaskBuildThumbnails(Project project) {
		BuildThumbnails buildThumbnails = GradleUtil.addTask(
			project, BUILD_THUMBNAILS_TASK_NAME, BuildThumbnails.class);

		buildThumbnails.setDescription("Generates thumbnails.");
		buildThumbnails.setGroup(BasePlugin.BUILD_GROUP);

		return buildThumbnails;
	}

	protected CompileTheme addTaskCompileTheme(Project project) {
		CompileTheme compileTheme = GradleUtil.addTask(
			project, COMPILE_THEME_TASK_NAME, CompileTheme.class);

		compileTheme.dependsOn(BUILD_THUMBNAILS_TASK_NAME);

		compileTheme.setDescription(
			"Compiles the theme by merging the \"diffs\" directory with the " +
				"parent theme.");
		compileTheme.setGroup(BasePlugin.BUILD_GROUP);

		return compileTheme;
	}

	@Override
	protected void addTasks(
		Project project, LiferayExtension liferayExtension) {

		super.addTasks(project, liferayExtension);

		addTaskBuildThumbnails(project);
		addTaskCompileTheme(project);
	}

	@Override
	protected void configureDependenciesCompile(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependenciesCompile(project, liferayExtension);

		if (hasJavaSources(project)) {
			for (String dependencyNotation :
					_THEME_COMPILE_DEPENDENCY_NOTATIONS) {

				GradleUtil.addDependency(
					project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
					dependencyNotation);
			}
		}
	}

	@Override
	protected void configureDependenciesProvidedCompile(
		Project project, LiferayExtension liferayExtension) {

		super.configureDependenciesProvidedCompile(project, liferayExtension);

		if (hasJavaSources(project)) {
			GradleUtil.removeDependencies(
				project, WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				_THEME_COMPILE_DEPENDENCY_NOTATIONS);
		}
	}

	protected void configureTaskBuildThumbnails(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		BuildThumbnails buildThumbnails = (BuildThumbnails)GradleUtil.getTask(
			project, BUILD_THUMBNAILS_TASK_NAME);

		configureTaskBuildThumbnailsImagesDir(
			buildThumbnails, liferayThemeExtension);
	}

	protected void configureTaskBuildThumbnailsImagesDir(
		BuildThumbnails buildThumbnails,
		LiferayThemeExtension liferayThemeExtension) {

		if (buildThumbnails.getImagesDir() != null) {
			return;
		}

		File diffsDir = getDiffsDir(
			buildThumbnails.getProject(), liferayThemeExtension);

		if (diffsDir != null) {
			File imagesDir = new File(
				liferayThemeExtension.getDiffsDir(), "images");

			buildThumbnails.setImagesDir(imagesDir);
		}
	}

	@Override
	protected void configureTaskClassesDependsOn(Task classesTask) {
		super.configureTaskClassesDependsOn(classesTask);

		classesTask.dependsOn(COMPILE_THEME_TASK_NAME);
	}

	protected void configureTaskCompileTheme(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		CompileTheme compileTheme = (CompileTheme)GradleUtil.getTask(
			project, COMPILE_THEME_TASK_NAME);

		configureTaskCompileThemeDiffsDir(compileTheme, liferayThemeExtension);
		configureTaskCompileThemeParent(compileTheme, liferayThemeExtension);
		configureTaskCompileThemePortalWebFile(compileTheme);
		configureTaskCompileThemeType(compileTheme, liferayThemeExtension);

		configureTaskCompileThemeDependsOn(compileTheme);
	}

	protected void configureTaskCompileThemeDependsOn(
		CompileTheme compileTheme) {

		compileTheme.dependsOn(BUILD_THUMBNAILS_TASK_NAME);

		Project themeParentProject = compileTheme.getThemeParentProject();

		if (themeParentProject != null) {
			String taskName =
				themeParentProject.getPath() + Project.PATH_SEPARATOR +
					COMPILE_THEME_TASK_NAME;

			compileTheme.dependsOn(taskName);
		}
	}

	protected void configureTaskCompileThemeDiffsDir(
		CompileTheme compileTheme,
		LiferayThemeExtension liferayThemeExtension) {

		if (compileTheme.getDiffsDir() == null) {
			compileTheme.setDiffsDir(liferayThemeExtension.getDiffsDir());
		}
	}

	protected void configureTaskCompileThemeParent(
		CompileTheme compileTheme,
		LiferayThemeExtension liferayThemeExtension) {

		if (Validator.isNull(compileTheme.getThemeParent())) {
			compileTheme.setThemeParent(liferayThemeExtension.getThemeParent());
		}
	}

	protected void configureTaskCompileThemePortalWebFile(
		CompileTheme compileTheme) {

		if (compileTheme.getPortalWebFile() != null) {
			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			compileTheme.getProject(), PORTAL_WEB_CONFIGURATION_NAME);

		compileTheme.setPortalWebFile(configuration.getSingleFile());
	}

	protected void configureTaskCompileThemeType(
		CompileTheme compileTheme,
		LiferayThemeExtension liferayThemeExtension) {

		if (Validator.isNull(compileTheme.getThemeType())) {
			compileTheme.setThemeType(liferayThemeExtension.getThemeType());
		}
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		LiferayThemeExtension liferayThemeExtension =
			(LiferayThemeExtension)liferayExtension;

		configureTaskBuildThumbnails(project, liferayThemeExtension);
		configureTaskCompileTheme(project, liferayThemeExtension);
	}

	@Override
	protected void configureTaskWar(
		Project project, LiferayExtension liferayExtension) {

		super.configureTaskWar(project, liferayExtension);

		LiferayThemeExtension liferayThemeExtension =
			(LiferayThemeExtension)liferayExtension;

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		configureTaskWarExclude(war, liferayThemeExtension);
	}

	protected void configureTaskWarExclude(
		War war, LiferayThemeExtension liferayThemeExtension) {

		Project project = war.getProject();

		File diffsDir = getDiffsDir(project, liferayThemeExtension);

		if (diffsDir != null) {
			String relativeDiffsDir = FileUtil.relativize(
				diffsDir, getWebAppDir(project));

			if (Validator.isNotNull(relativeDiffsDir)) {
				war.exclude(relativeDiffsDir + "/**");
			}
		}
	}

	protected File getDiffsDir(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		CompileTheme compileTheme = (CompileTheme)GradleUtil.getTask(
			project, COMPILE_THEME_TASK_NAME);

		File diffsDir = compileTheme.getDiffsDir();

		if (diffsDir == null) {
			diffsDir = liferayThemeExtension.getDiffsDir();
		}

		return diffsDir;
	}

	protected boolean hasJavaSources(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getAllJava();

		if (sourceDirectorySet.isEmpty()) {
			return false;
		}

		return true;
	}

	private static final String[] _THEME_COMPILE_DEPENDENCY_NOTATIONS = {
		"com.liferay.portal:util-bridges:default",
		"com.liferay.portal:util-java:default",
		"com.liferay.portal:util-taglib:default",
		"commons-logging:commons-logging:1.1.1", "log4j:log4j:1.2.16"
	};

}