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

package com.liferay.gradle.plugins.wedeploy;

import com.liferay.gradle.plugins.wedeploy.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.initialization.ProjectDescriptor;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;

/**
 * @author Andrea Di Giorgi
 */
public class WeDeploySettingsPlugin implements Plugin<Settings> {

	public static final String PROJECT_PATH_PREFIX_PROPERTY_NAME =
		"project.path.prefix";

	@Override
	public void apply(Settings settings) {
		try {
			_apply(settings);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private void _apply(Settings settings) throws IOException {
		final Map<String, Class<? extends Plugin<Project>>>
			projectPathPluginClasses = _includeProjects(settings);

		Gradle gradle = settings.getGradle();

		gradle.beforeProject(
			new Closure<Void>(settings) {

				@SuppressWarnings("unused")
				public void doCall(Project project) {
					Class<? extends Plugin<Project>> pluginClass =
						projectPathPluginClasses.get(project.getPath());

					if (pluginClass != null) {
						GradleUtil.applyPlugin(project, pluginClass);
					}
				}

			});
	}

	private String _getProjectPathPrefix(Settings settings) {
		String projectPathPrefix = GradleUtil.getProperty(
			settings, PROJECT_PATH_PREFIX_PROPERTY_NAME, "");

		if (Validator.isNotNull(projectPathPrefix)) {
			if (projectPathPrefix.charAt(0) != ':') {
				projectPathPrefix = ":" + projectPathPrefix;
			}

			if (projectPathPrefix.charAt(projectPathPrefix.length() - 1) ==
					':') {

				projectPathPrefix = projectPathPrefix.substring(
					0, projectPathPrefix.length() - 1);
			}
		}

		return projectPathPrefix;
	}

	private String _includeProject(
		Settings settings, Path dirPath, Path rootDirPath,
		String projectPathPrefix) {

		String projectPath = String.valueOf(rootDirPath.relativize(dirPath));

		projectPath =
			projectPathPrefix + ":" +
				projectPath.replace(File.separatorChar, ':');

		settings.include(new String[] {projectPath});

		ProjectDescriptor projectDescriptor = settings.findProject(projectPath);

		projectDescriptor.setProjectDir(dirPath.toFile());

		return projectPath;
	}

	private Map<String, Class<? extends Plugin<Project>>> _includeProjects(
			final Settings settings)
		throws IOException {

		final Map<String, Class<? extends Plugin<Project>>>
			projectPathPluginClasses = new HashMap<>();

		final String projectPathPrefix = _getProjectPathPrefix(settings);

		File rootDir = settings.getRootDir();

		final Path rootDirPath = rootDir.toPath();

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path wedeployJSONPath = dirPath.resolve("wedeploy.json");

					if (Files.notExists(wedeployJSONPath)) {
						return FileVisitResult.CONTINUE;
					}

					String wedeployJSON = new String(
						Files.readAllBytes(wedeployJSONPath),
						StandardCharsets.UTF_8);

					if (wedeployJSON.contains("wedeploy/data")) {
						String projectPath = _includeProject(
							settings, dirPath, rootDirPath, projectPathPrefix);

						projectPathPluginClasses.put(
							projectPath, DataWeDeployPlugin.class);
					}
					else if (wedeployJSON.contains("wedeploy/message-queue")) {
						String projectPath = _includeProject(
							settings, dirPath, rootDirPath, projectPathPrefix);

						projectPathPluginClasses.put(
							projectPath, MessageQueueWeDeployPlugin.class);
					}

					return FileVisitResult.SKIP_SUBTREE;
				}

			});

		return projectPathPluginClasses;
	}

}