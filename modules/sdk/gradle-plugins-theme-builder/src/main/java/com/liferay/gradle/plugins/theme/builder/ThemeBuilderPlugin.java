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

package com.liferay.gradle.plugins.theme.builder;

import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class ThemeBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_THEME_TASK_NAME = "buildTheme";

	public static final String PARENT_THEMES_CONFIGURATION_NAME =
		"parentThemes";

	public static final String THEME_BUILDER_CONFIGURATION_NAME =
		"themeBuilder";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, CSSBuilderPlugin.class);
		GradleUtil.applyPlugin(project, WarPlugin.class);

		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		Configuration parentThemesConfiguration = _addConfigurationParentThemes(
			project);
		Configuration themeBuilderConfiguration = _addConfigurationThemeBuilder(
			project);

		BuildThemeTask buildThemeTask = _addTaskBuildTheme(
			project, parentThemesConfiguration, warPluginConvention);

		_configureTaskBuildCSS(buildThemeTask);
		_configureTaskWar(buildThemeTask);

		_configureTasksBuildTheme(project, themeBuilderConfiguration);
	}

	private Configuration _addConfigurationParentThemes(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, PARENT_THEMES_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesParentThemes(project);
				}

			});

		configuration.setDescription(
			"Configures the parent theme JARs required to build theme files.");
		configuration.setTransitive(false);
		configuration.setVisible(false);

		return configuration;
	}

	private Configuration _addConfigurationThemeBuilder(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, THEME_BUILDER_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					_addDependenciesThemeBuilder(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Portal Tools Theme Builder for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	private void _addDependenciesParentThemes(Project project) {
		GradleUtil.addDependency(
			project, PARENT_THEMES_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.frontend.theme.styled", "latest.release");
		GradleUtil.addDependency(
			project, PARENT_THEMES_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.frontend.theme.unstyled", "latest.release");
	}

	private void _addDependenciesThemeBuilder(Project project) {
		GradleUtil.addDependency(
			project, THEME_BUILDER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.portal.tools.theme.builder", "latest.release");
	}

	private BuildThemeTask _addTaskBuildTheme(
		Project project, final Iterable<File> parentThemeFiles,
		final WarPluginConvention warPluginConvention) {

		final BuildThemeTask buildThemeTask = GradleUtil.addTask(
			project, BUILD_THEME_TASK_NAME, BuildThemeTask.class);

		buildThemeTask.setDescription("Builds the theme files.");

		buildThemeTask.setDiffsDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						warPluginConvention.getWebAppDir(), "_diffs");
				}

			});

		buildThemeTask.setGroup(BasePlugin.BUILD_GROUP);

		buildThemeTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return warPluginConvention.getWebAppDir();
				}

			});

		buildThemeTask.setParentDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					String parentName = buildThemeTask.getParentName();

					if (Validator.isNull(parentName)) {
						return null;
					}

					return _getThemeFile(parentThemeFiles, parentName);
				}

			});

		buildThemeTask.setParentName("_styled");
		buildThemeTask.setTemplateExtension("ftl");
		buildThemeTask.setThemeName(project.getName());

		buildThemeTask.setUnstyledDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return _getThemeFile(parentThemeFiles, "_unstyled");
				}

			});

		return buildThemeTask;
	}

	private void _configureTaskBuildCSS(BuildThemeTask buildThemeTask) {
		BuildCSSTask buildCSSTask = (BuildCSSTask)GradleUtil.getTask(
			buildThemeTask.getProject(), CSSBuilderPlugin.BUILD_CSS_TASK_NAME);

		buildCSSTask.dependsOn(buildThemeTask);
	}

	private void _configureTasksBuildTheme(
		Project project, final FileCollection classpath) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildThemeTask.class,
			new Action<BuildThemeTask>() {

				@Override
				public void execute(BuildThemeTask buildThemeTask) {
					buildThemeTask.setClasspath(classpath);
				}

			});
	}

	private void _configureTaskWar(final BuildThemeTask buildThemeTask) {
		War war = (War)GradleUtil.getTask(
			buildThemeTask.getProject(), WarPlugin.WAR_TASK_NAME);

		war.dependsOn(buildThemeTask);

		war.exclude(
			new Spec<FileTreeElement>() {

				@Override
				public boolean isSatisfiedBy(FileTreeElement fileTreeElement) {
					File diffsDir = buildThemeTask.getDiffsDir();

					if ((diffsDir != null) &&
						FileUtil.isChild(fileTreeElement.getFile(), diffsDir)) {

						return true;
					}

					return false;
				}

			});
	}

	private File _getThemeFile(Iterable<File> files, String name)
		throws IOException {

		for (File file : files) {
			try (ZipFile zipFile = new ZipFile(file)) {
				ZipEntry zipEntry = zipFile.getEntry(
					"META-INF/resources/" + name + "/");

				if (zipEntry != null) {
					return file;
				}
			}
		}

		return null;
	}

}