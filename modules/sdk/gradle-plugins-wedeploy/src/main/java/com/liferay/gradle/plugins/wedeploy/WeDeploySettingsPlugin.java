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

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashSet;
import java.util.Set;

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

	@SuppressWarnings("serial")
	private void _apply(Settings settings) throws IOException {
		final Set<String> projectPaths = _includeProjects(settings);

		Gradle gradle = settings.getGradle();

		gradle.beforeProject(
			new Closure<Void>(settings) {

				@SuppressWarnings("unused")
				public void doCall(Project project) {
					if (projectPaths.contains(project.getPath())) {
						GradleUtil.applyPlugin(project, WeDeployPlugin.class);
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

	private Set<String> _includeProjects(final Settings settings)
		throws IOException {

		final String projectPathPrefix = _getProjectPathPrefix(settings);
		final Set<String> projectPaths = new HashSet<>();

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

					String projectPath = _includeProject(
						settings, dirPath, rootDirPath, projectPathPrefix);

					projectPaths.add(projectPath);

					return FileVisitResult.SKIP_SUBTREE;
				}

			});

		return projectPaths;
	}

}