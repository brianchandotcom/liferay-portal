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

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.gradle.api.Plugin;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.initialization.ProjectDescriptor;
import org.gradle.api.initialization.Settings;

/**
 * @author Andrea Di Giorgi
 */
public class LiferaySettingsPlugin implements Plugin<Settings> {

	public static final String PROJECT_PATH_PREFIX_PROPERTY_NAME =
		"project.path.prefix";

	@Override
	public void apply(Settings settings) {
		File rootDir = settings.getRootDir();

		Path rootDirPath = rootDir.toPath();

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

		try {
			_includeProjects(
				settings, rootDirPath, rootDirPath, projectPathPrefix);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private Set<Path> _getDirPaths(String key, Path rootDirPath) {
		String dirNamesString = System.getProperty(key);

		if (Validator.isNull(dirNamesString)) {
			return Collections.emptySet();
		}

		Set<Path> dirPaths = new HashSet<>();

		for (String dirName : dirNamesString.split(",")) {
			dirPaths.add(rootDirPath.resolve(dirName));
		}

		return dirPaths;
	}

	private <T extends Enum<T>> Set<T> _getFlags(
		String prefix, Class<T> clazz) {

		Set<T> flags = EnumSet.allOf(clazz);

		Iterator<T> iterator = flags.iterator();

		while (iterator.hasNext()) {
			T flag = iterator.next();

			String flagName = flag.toString();

			flagName = flagName.replace('_', '.');
			flagName = flagName.toLowerCase();

			if (!Boolean.getBoolean(prefix + flagName)) {
				iterator.remove();
			}
		}

		return flags;
	}

	private ProjectDirType _getProjectDirType(Path dirPath) {
		if (Files.exists(dirPath.resolve("build.xml"))) {
			return ProjectDirType.ANT_PLUGIN;
		}

		if (Files.exists(dirPath.resolve("bnd.bnd"))) {
			return ProjectDirType.MODULE;
		}

		if (Files.exists(dirPath.resolve("gulpfile.js"))) {
			return ProjectDirType.THEME;
		}

		return ProjectDirType.UNKNOWN;
	}

	private void _includeProject(
		Settings settings, Path projectDirPath, Path projectPathRootDirPath,
		String projectPathPrefix) {

		Path relativePath = projectPathRootDirPath.relativize(projectDirPath);

		String projectPath = relativePath.toString();

		projectPath =
			projectPathPrefix + ":" +
				projectPath.replace(File.separatorChar, ':');

		settings.include(new String[] {projectPath});

		ProjectDescriptor projectDescriptor = settings.findProject(projectPath);

		projectDescriptor.setProjectDir(projectDirPath.toFile());
	}

	private void _includeProjects(
			final Settings settings, final Path rootDirPath,
			final Path projectPathRootDirPath, final String projectPathPrefix)
		throws IOException {

		final String buildProfile = System.getProperty("build.profile");
		final Set<Path> excludedDirPaths = _getDirPaths(
			"build.exclude.dirs", rootDirPath);
		final Set<ProjectDirType> excludedProjectDirTypes = _getFlags(
			"build.exclude.", ProjectDirType.class);

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (dirPath.equals(rootDirPath)) {
						return FileVisitResult.CONTINUE;
					}

					if (excludedDirPaths.contains(dirPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					ProjectDirType projectDirType = _getProjectDirType(dirPath);

					if (projectDirType == ProjectDirType.UNKNOWN) {
						return FileVisitResult.CONTINUE;
					}

					if (excludedProjectDirTypes.contains(projectDirType)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (Validator.isNotNull(buildProfile) &&
						Files.notExists(
							dirPath.resolve(".lfrbuild-" + buildProfile))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					_includeProject(
						settings, dirPath, projectPathRootDirPath,
						projectPathPrefix);

					return FileVisitResult.SKIP_SUBTREE;
				}

			});
	}

	private static enum ProjectDirType {

		ANT_PLUGIN, MODULE, THEME, UNKNOWN

	}

}