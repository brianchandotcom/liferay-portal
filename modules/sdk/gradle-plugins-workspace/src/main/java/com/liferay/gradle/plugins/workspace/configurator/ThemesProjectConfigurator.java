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

package com.liferay.gradle.plugins.workspace.configurators;

import com.liferay.gradle.plugins.LiferayThemePlugin;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.theme.builder.BuildThemeTask;
import com.liferay.gradle.plugins.theme.builder.ThemeBuilderPlugin;
import com.liferay.gradle.plugins.workspace.ProjectConfigurator;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.LiferayThemeGulpPlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.Copy;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class ThemesProjectConfigurator extends BaseProjectConfigurator {

	public ThemesProjectConfigurator(Settings settings) {
		super(settings);

		_javaBuild = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + NAME + ".java.build",
			_JAVA_BUILD);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		if (isJavaBuild()) {
			ProjectConfigurator projectConfigurator =
				workspaceExtension.propertyMissing(
					WarsProjectConfigurator.NAME);

			projectConfigurator.apply(project);

			GradleUtil.applyPlugin(project, ThemeBuilderPlugin.class);

			_configureTaskBuildTheme(project);
			_configureWar(project);
		}
		else {
			GradleUtil.applyPlugin(project, LiferayThemePlugin.class);

			LiferayThemeGulpPlugin.INSTANCE.apply(project);

			configureLiferay(project, workspaceExtension);

			final Task assembleTask = GradleUtil.getTask(
				project, BasePlugin.ASSEMBLE_TASK_NAME);

			_configureRootTaskDistBundle(assembleTask);

			_configureTaskGulpBuild(project, workspaceExtension);

			Callable<ConfigurableFileCollection> warSourcePath =
				new Callable<ConfigurableFileCollection>() {

					@Override
					public ConfigurableFileCollection call() throws Exception {
						Project project = assembleTask.getProject();

						ConfigurableFileCollection configurableFileCollection =
							project.files(_getWarFile(project));

						return configurableFileCollection.builtBy(assembleTask);
					}

				};

			addTaskDockerDeploy(project, warSourcePath, workspaceExtension);
		}
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean isJavaBuild() {
		return _javaBuild;
	}

	public void setJavaBuild(boolean javaBuild) {
		_javaBuild = javaBuild;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (isExcludedDirName(dirName)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path gulpfileJsPath = dirPath.resolve("gulpfile.js");
					Path packageJsonPath = dirPath.resolve("package.json");

					if (Files.exists(gulpfileJsPath) &&
						Files.exists(packageJsonPath) &&
						_isLiferayTheme(packageJsonPath)) {

						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "themes";

	@SuppressWarnings({"serial", "unused"})
	private void _configureRootTaskDistBundle(final Task assembleTask) {
		Project project = assembleTask.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(assembleTask);

		copy.into(
			"osgi/war",
			new Closure<Void>(project) {

				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					File warFile = _getWarFile(project);

					ConfigurableFileCollection configurableFileCollection =
						project.files(warFile);

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(warFile);
				}

			});
	}

	@SuppressWarnings("unchecked")
	private void _configureTaskBuildTheme(Project project) {
		File packageJsonFile = project.file("package.json");

		if (!packageJsonFile.exists()) {
			return;
		}

		BuildThemeTask buildThemeTask = (BuildThemeTask)GradleUtil.getTask(
			project, ThemeBuilderPlugin.BUILD_THEME_TASK_NAME);

		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonFile);

		Map<String, String> liferayThemeMap =
			(Map<String, String>)packageJsonMap.get("liferayTheme");

		String baseTheme = liferayThemeMap.get("baseTheme");

		if (baseTheme.equals("styled") || baseTheme.equals("unstyled")) {
			baseTheme = "_" + baseTheme;
		}

		String templateLanguage = liferayThemeMap.get("templateLanguage");

		buildThemeTask.setParentName(baseTheme);
		buildThemeTask.setTemplateExtension(templateLanguage);
	}

	@SuppressWarnings("unchecked")
	private void _configureTaskGulpBuild(
		Project project, WorkspaceExtension workspaceExtension) {

		ExecuteGulpTask executeGulpTask = (ExecuteGulpTask)GradleUtil.getTask(
			project, "gulpBuild");

		File packageJsonFile = project.file("package.json");

		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonFile);

		Map<String, String> scriptsMap =
			(Map<String, String>)packageJsonMap.get("scripts");

		if (scriptsMap != null) {
			String buildScript = scriptsMap.get("build");

			if ((buildScript != null) && !buildScript.equals("")) {
				executeGulpTask.setEnabled(false);
			}
		}
		else {
			String nodePackageManager =
				workspaceExtension.getNodePackageManager();

			if (nodePackageManager.equals("yarn")) {
				Project rootProject = project.getRootProject();

				executeGulpTask.setScriptFile(
					rootProject.file("node_modules/gulp/bin/gulp.js"));
			}
		}
	}

	private void _configureWar(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("src");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	private File _getWarFile(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		return project.file(
			"dist/" + basePluginConvention.getArchivesBaseName() + ".war");
	}

	@SuppressWarnings("unchecked")
	private boolean _isLiferayTheme(Path packageJsonPath) {
		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonPath.toFile());

		Map<String, Object> liferayTheme =
			(Map<String, Object>)packageJsonMap.get("liferayTheme");

		if (liferayTheme != null) {
			return true;
		}

		return false;
	}

	private static final boolean _JAVA_BUILD = false;

	private boolean _javaBuild;

}