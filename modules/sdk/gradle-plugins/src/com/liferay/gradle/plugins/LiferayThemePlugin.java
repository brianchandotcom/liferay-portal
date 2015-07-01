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

import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayThemeExtension;
import com.liferay.gradle.plugins.tasks.BuildThumbnailsTask;
import com.liferay.gradle.plugins.tasks.CompileThemeTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayThemePlugin extends LiferayWebAppPlugin {

	public static final String BUILD_THUMBNAILS_TASK_NAME = "buildThumbnails";

	public static final String COMPILE_THEME_TASK_NAME = "compileTheme";

	public static final String FRONTEND_THEMES_WEB_CONFIGURATION_NAME =
		"frontendThemesWeb";

	protected Configuration addConfigurationFrontendThemesWeb(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, FRONTEND_THEMES_WEB_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures com.liferay.frontend.themes.web for compiling themes.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addDependenciesFrontendThemesWeb(project);
				}

			});

		return configuration;
	}

	protected void addConfigurations(Project project) {
		super.addConfigurations(project);

		addConfigurationFrontendThemesWeb(project);
	}

	protected void addDependenciesFrontendThemesWeb(Project project) {
		GradleUtil.addDependency(
			project, FRONTEND_THEMES_WEB_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.frontend.themes.web", "1.0.0-SNAPSHOT", false);
	}

	@Override
	protected LiferayExtension addLiferayExtension(Project project) {
		return GradleUtil.addExtension(
			project, LiferayPlugin.PLUGIN_NAME, LiferayThemeExtension.class);
	}

	protected BuildThumbnailsTask addTaskBuildThumbnails(Project project) {
		BuildThumbnailsTask buildThumbnailsTask = GradleUtil.addTask(
			project, BUILD_THUMBNAILS_TASK_NAME, BuildThumbnailsTask.class);

		buildThumbnailsTask.setDescription("Generates thumbnails.");
		buildThumbnailsTask.setGroup(BasePlugin.BUILD_GROUP);

		return buildThumbnailsTask;
	}

	protected CompileThemeTask addTaskCompileTheme(Project project) {
		CompileThemeTask compileThemeTask = GradleUtil.addTask(
			project, COMPILE_THEME_TASK_NAME, CompileThemeTask.class);

		compileThemeTask.dependsOn(BUILD_THUMBNAILS_TASK_NAME);

		compileThemeTask.setDescription(
			"Compiles the theme by merging the \"diffs\" directory with the " +
				"parent theme.");
		compileThemeTask.setGroup(BasePlugin.BUILD_GROUP);

		return compileThemeTask;
	}

	@Override
	protected void addTasks(Project project) {
		super.addTasks(project);

		addTaskBuildThumbnails(project);
		addTaskCompileTheme(project);
	}

	@Override
	protected void configureDependencies(Project project) {
		super.configureDependencies(project);

		configureDependenciesProvided(project);
		configureDependenciesRuntime(project);
	}

	protected void configureDependenciesProvided(Project project) {
		super.configureDependenciesProvided(project);

		if (!isAddDefaultDependencies(project) || !hasSources(project)) {
			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			project, ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME());

		for (String dependencyNotationPrefix :
				_THEME_RUNTIME_DEPENDENCY_NOTATION_PREFIXES) {

			int pos = dependencyNotationPrefix.indexOf(':');

			String group = dependencyNotationPrefix.substring(0, pos);
			String module = dependencyNotationPrefix.substring(pos + 1);

			Map<String, String> args = new HashMap<>();

			args.put("group", group);
			args.put("module", module);

			configuration.exclude(args);
		}
	}

	protected void configureDependenciesRuntime(Project project) {
		super.configureDependenciesCompile(project);

		if (!isAddDefaultDependencies(project) || !hasSources(project)) {
			return;
		}

		for (String dependencyNotationPrefix :
				_THEME_RUNTIME_DEPENDENCY_NOTATION_PREFIXES) {

			for (String dependencyNotation : DEFAULT_DEPENDENCY_NOTATIONS) {
				if (dependencyNotation.startsWith(dependencyNotationPrefix)) {
					GradleUtil.addDependency(
						project, JavaPlugin.RUNTIME_CONFIGURATION_NAME,
						dependencyNotation);
				}
			}
		}
	}

	@Override
	protected void configureTaskBuildCSS(
		Project project, LiferayExtension liferayExtension) {

		super.configureTaskBuildCSS(project, liferayExtension);

		Task task = GradleUtil.getTask(
			project, CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

		task.dependsOn(COMPILE_THEME_TASK_NAME);
	}

	protected void configureTaskBuildThumbnails(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		BuildThumbnailsTask buildThumbnailsTask =
			(BuildThumbnailsTask)GradleUtil.getTask(
				project, BUILD_THUMBNAILS_TASK_NAME);

		configureTaskBuildThumbnailsImagesDir(
			buildThumbnailsTask, liferayThemeExtension);
	}

	protected void configureTaskBuildThumbnailsImagesDir(
		BuildThumbnailsTask buildThumbnailsTask,
		LiferayThemeExtension liferayThemeExtension) {

		FileCollection imageDirs = buildThumbnailsTask.getImageDirs();

		if (!imageDirs.isEmpty()) {
			return;
		}

		File diffsDir = getDiffsDir(
			buildThumbnailsTask.getProject(), liferayThemeExtension);

		if (diffsDir != null) {
			File imagesDir = new File(
				liferayThemeExtension.getDiffsDir(), "images");

			buildThumbnailsTask.imageDirs(imagesDir);
		}
	}

	@Override
	protected void configureTaskClassesDependsOn(Task classesTask) {
		super.configureTaskClassesDependsOn(classesTask);

		classesTask.dependsOn(COMPILE_THEME_TASK_NAME);
	}

	protected void configureTaskCompileTheme(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		CompileThemeTask compileThemeTask =
			(CompileThemeTask)GradleUtil.getTask(
				project, COMPILE_THEME_TASK_NAME);

		configureTaskCompileThemeDiffsDir(
			compileThemeTask, liferayThemeExtension);
		configureTaskCompileThemeFrontendThemesWebFile(compileThemeTask);
		configureTaskCompileThemeParent(
			compileThemeTask, liferayThemeExtension);
		configureTaskCompileThemeRootDir(compileThemeTask);
		configureTaskCompileThemeType(compileThemeTask, liferayThemeExtension);

		configureTaskCompileThemeDependsOn(compileThemeTask);
	}

	protected void configureTaskCompileThemeDependsOn(
		CompileThemeTask compileThemeTask) {

		compileThemeTask.dependsOn(BUILD_THUMBNAILS_TASK_NAME);

		Project themeParentProject = compileThemeTask.getThemeParentProject();

		if (themeParentProject != null) {
			String taskName =
				themeParentProject.getPath() + Project.PATH_SEPARATOR +
					COMPILE_THEME_TASK_NAME;

			compileThemeTask.dependsOn(taskName);
		}
	}

	protected void configureTaskCompileThemeDiffsDir(
		CompileThemeTask compileThemeTask,
		LiferayThemeExtension liferayThemeExtension) {

		if (compileThemeTask.getDiffsDir() == null) {
			compileThemeTask.setDiffsDir(liferayThemeExtension.getDiffsDir());
		}
	}

	protected void configureTaskCompileThemeFrontendThemesWebFile(
		CompileThemeTask compileThemeTask) {

		if ((compileThemeTask.getFrontendThemesWebDir() != null) ||
			(compileThemeTask.getFrontendThemesWebFile() != null)) {

			return;
		}

		Configuration configuration = GradleUtil.getConfiguration(
			compileThemeTask.getProject(),
			FRONTEND_THEMES_WEB_CONFIGURATION_NAME);

		compileThemeTask.setFrontendThemesWebFile(
			configuration.getSingleFile());
	}

	protected void configureTaskCompileThemeParent(
		CompileThemeTask compileThemeTask,
		LiferayThemeExtension liferayThemeExtension) {

		if (Validator.isNull(compileThemeTask.getThemeParent())) {
			compileThemeTask.setThemeParent(
				liferayThemeExtension.getThemeParent());
		}
	}

	protected void configureTaskCompileThemeRootDir(
		CompileThemeTask compileThemeTask) {

		if (compileThemeTask.getThemeRootDir() != null) {
			return;
		}

		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			compileThemeTask.getProject(), WarPluginConvention.class);

		compileThemeTask.setThemeRootDir(warPluginConvention.getWebAppDir());
	}

	protected void configureTaskCompileThemeType(
		CompileThemeTask compileThemeTask,
		LiferayThemeExtension liferayThemeExtension) {

		Set<String> themeTypes = compileThemeTask.getThemeTypes();

		if (themeTypes.isEmpty()) {
			compileThemeTask.themeTypes(liferayThemeExtension.getThemeType());
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

			war.exclude(relativeDiffsDir + "/**");
		}
	}

	protected File getDiffsDir(
		Project project, LiferayThemeExtension liferayThemeExtension) {

		CompileThemeTask compileThemeTask =
			(CompileThemeTask)GradleUtil.getTask(
				project, COMPILE_THEME_TASK_NAME);

		File diffsDir = compileThemeTask.getDiffsDir();

		if (diffsDir == null) {
			diffsDir = liferayThemeExtension.getDiffsDir();
		}

		return diffsDir;
	}

	protected boolean hasSources(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet sourceDirectorySet = sourceSet.getAllSource();

		if (sourceDirectorySet.isEmpty()) {
			return false;
		}

		return true;
	}

	private static final String[] _THEME_RUNTIME_DEPENDENCY_NOTATION_PREFIXES =
		{
			"com.liferay.portal:util-bridges", "com.liferay.portal:util-java",
			"com.liferay.portal:util-taglib", "commons-logging:commons-logging",
			"log4j:log4j"
		};

}