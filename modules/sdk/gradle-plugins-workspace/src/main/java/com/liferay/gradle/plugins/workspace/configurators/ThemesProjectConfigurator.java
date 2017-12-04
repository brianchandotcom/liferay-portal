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
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.theme.builder.BuildThemeTask;
import com.liferay.gradle.plugins.theme.builder.ThemeBuilderPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
			settings, WorkspacePlugin.PROPERTY_PREFIX + _NAME + ".java.build",
			_JAVA_BUILD);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		if (isJavaBuild()) {
			GradleUtil.applyPlugin(project, ThemeBuilderPlugin.class);

			_configureThemeBuilderPlugin(project);

			_configureWarPlugin(project);
		}
		else {
			GradleUtil.applyPlugin(project, LiferayThemePlugin.class);

			_configureLiferay(project, workspaceExtension);

			Task assembleTask = GradleUtil.getTask(
				project, BasePlugin.ASSEMBLE_TASK_NAME);

			_configureRootTaskDistBundle(assembleTask);
		}
	}

	@Override
	public String getName() {
		return _NAME;
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

					Path dirNamePath = dirPath.getFileName();

					String dirName = dirNamePath.toString();

					if (dirName.equals("build") ||
						dirName.equals("build_gradle") ||
						dirName.equals("node_modules")) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					if (Files.exists(dirPath.resolve("package.json"))) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	private void _configureLiferay(
		Project project, WorkspaceExtension workspaceExtension) {

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setAppServerParentDir(workspaceExtension.getHomeDir());
	}

	private void _configureRootTaskDistBundle(final Task assembleTask) {
		Project project = assembleTask.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(assembleTask);

		copy.into(
			"osgi/modules",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					Project project = assembleTask.getProject();

					ConfigurableFileCollection configurableFileCollection =
						project.files(_getWarFile(project));

					configurableFileCollection.builtBy(assembleTask);

					copySpec.from(_getWarFile(project));
				}

			});
	}

	private void _configureThemeBuilderPlugin(Project project) {
		File packageJsonFile = new File(
			project.getProjectDir(), "package.json");

		if (packageJsonFile.exists()) {
			JsonSlurper jsonSlurper = new JsonSlurper();

			Map<String, Map<String, String>> packageJsonData =
				(Map<String, Map<String, String>>)jsonSlurper.parse(
					packageJsonFile);

			Map<String, String> liferayTheme = packageJsonData.get(
				"liferayTheme");

			String baseTheme = liferayTheme.get("baseTheme");

			if (baseTheme.equals("styled") || baseTheme.equals("unsytyled")) {
				baseTheme = "_" + baseTheme;
			}

			String templateLanguage = liferayTheme.get("templateLanguage");

			BuildThemeTask buildThemeTask = (BuildThemeTask)GradleUtil.getTask(
				project, ThemeBuilderPlugin.BUILD_THEME_TASK_NAME);

			buildThemeTask.setParentName(baseTheme);
			buildThemeTask.setTemplateExtension(templateLanguage);
		}
	}

	private void _configureWarPlugin(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("src");
	}

	private File _getWarFile(Project project) {
		BasePluginConvention basePluginConvention = GradleUtil.getConvention(
			project, BasePluginConvention.class);

		return project.file(
			"dist/" + basePluginConvention.getArchivesBaseName() + ".war");
	}

	private static final boolean _JAVA_BUILD = false;

	private static final String _NAME = "themes";

	private boolean _javaBuild;

}