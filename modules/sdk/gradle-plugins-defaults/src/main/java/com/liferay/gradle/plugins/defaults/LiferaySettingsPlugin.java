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

import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
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
			Path projectPathRootDirPath = rootDirPath;

			if (_isPortalRootDirPath(rootDirPath)) {
				projectPathRootDirPath = rootDirPath.resolve("modules");
			}

			_includeProjects(
				settings, projectPathRootDirPath, projectPathPrefix);
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
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
			String flagName = String.valueOf(iterator.next());

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

		Path applicationPropertiesPath = dirPath.resolve(
			"src/main/resources/application.properties");

		if (Files.exists(applicationPropertiesPath)) {
			return ProjectDirType.SPRING_BOOT;
		}

		if (Files.exists(dirPath.resolve("gulpfile.js"))) {
			return ProjectDirType.THEME;
		}

		return ProjectDirType.UNKNOWN;
	}

	private boolean _includeDXPProjects(
		String buildProfile, Set<String> buildProfileFileNames,
		Path projectPathRootDirPath) {

		if ((buildProfile == null) && (buildProfileFileNames == null)) {
			File portalRootDir = GradleUtil.getRootDir(
				projectPathRootDirPath.toFile(), "portal-impl");

			if (portalRootDir == null) {
				return false;
			}

			File buildProfileDXPPropertiesFile = new File(
				portalRootDir, "build.profile-dxp.properties");

			if (!buildProfileDXPPropertiesFile.exists()) {
				return false;
			}

			return true;
		}

		return Objects.equals(buildProfile, "dxp");
	}

	private void _includeProject(
		Settings settings, Path projectDirPath, Path projectPathRootDirPath,
		String projectPathPrefix) {

		String projectPath = String.valueOf(
			projectPathRootDirPath.relativize(projectDirPath));

		projectPath =
			projectPathPrefix + ":" +
				projectPath.replace(File.separatorChar, ':');

		settings.include(new String[] {projectPath});

		ProjectDescriptor projectDescriptor = settings.findProject(projectPath);

		projectDescriptor.setProjectDir(projectDirPath.toFile());
	}

	private void _includeProjects(
			final Settings settings, final Path projectPathRootDirPath,
			final String projectPathPrefix)
		throws IOException {

		String buildProfile = System.getProperty("build.profile");

		final Set<String> buildProfileFileNames =
			GradlePluginsDefaultsUtil.getBuildProfileFileNames(
				buildProfile,
				GradleUtil.getProperty(
					settings, "liferay.releng.public", true));

		final Set<Path> excludedDirPaths = _getDirPaths(
			"build.exclude.dirs", projectPathRootDirPath);
		final Set<Path> includedDirPaths = _getDirPaths(
			"build.include.dirs", projectPathRootDirPath);
		final Set<ProjectDirType> excludedProjectDirTypes = _getFlags(
			"build.exclude.", ProjectDirType.class);

		final boolean includeDXPProjects = _includeDXPProjects(
			buildProfile, buildProfileFileNames, projectPathRootDirPath);

		Files.walkFileTree(
			projectPathRootDirPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
			10,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (dirPath.equals(projectPathRootDirPath)) {
						return FileVisitResult.CONTINUE;
					}

					if (excludedDirPaths.contains(dirPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (!includeDXPProjects) {
						Path dxpPath = projectPathRootDirPath.resolve("dxp");

						if (dirPath.equals(dxpPath)) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					String dirName = String.valueOf(dirPath.getFileName());

					if (dirName.equals("build") ||
						dirName.equals("node_modules") ||
						dirName.equals("node_modules_cache")) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					ProjectDirType projectDirType = _getProjectDirType(dirPath);

					if (projectDirType == ProjectDirType.UNKNOWN) {
						return FileVisitResult.CONTINUE;
					}

					if (excludedProjectDirTypes.contains(projectDirType) ||
						(!includedDirPaths.isEmpty() &&
						 !_startsWith(dirPath, includedDirPaths))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					if (buildProfileFileNames != null) {
						boolean found = false;

						for (String fileName : buildProfileFileNames) {
							if (Files.exists(dirPath.resolve(fileName))) {
								found = true;

								break;
							}
						}

						if (!found) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					_includeProject(
						settings, dirPath, projectPathRootDirPath,
						projectPathPrefix);

					return FileVisitResult.SKIP_SUBTREE;
				}

			});
	}

	private boolean _isPortalRootDirPath(Path dirPath) {
		if (!Files.exists(dirPath.resolve("modules")) ||
			!Files.exists(dirPath.resolve("portal-impl"))) {

			return false;
		}

		return true;
	}

	private boolean _startsWith(Path path, Iterable<Path> parentPaths) {
		for (Path parentPath : parentPaths) {
			if (path.startsWith(parentPath)) {
				return true;
			}
		}

		return false;
	}

	private static enum ProjectDirType {

		ANT_PLUGIN, MODULE, SPRING_BOOT, THEME, UNKNOWN

	}

}