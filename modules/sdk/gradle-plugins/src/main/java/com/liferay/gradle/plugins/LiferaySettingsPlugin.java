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

import com.liferay.gradle.plugins.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.gradle.api.Plugin;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.initialization.ProjectDescriptor;
import org.gradle.api.initialization.Settings;

/**
 * @author Andrea Di Giorgi
 */
public class LiferaySettingsPlugin implements Plugin<Settings> {

	@Override
	public void apply(Settings settings) {
		File rootDir = settings.getRootDir();

		Path rootDirPath = rootDir.toPath();

		Path projectPathRootDirPath = rootDirPath;

		File portalRootDir = GradleUtil.getRootDir(rootDir, "portal-impl");

		if (portalRootDir != null) {
			projectPathRootDirPath = portalRootDir.toPath();

			projectPathRootDirPath = projectPathRootDirPath.resolve("modules");
		}

		try {
			includeProjects(settings, rootDirPath, projectPathRootDirPath);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	protected void includeProject(
		Settings settings, Path projectDirPath, Path projectPathRootDirPath) {

		Path relativePath = projectPathRootDirPath.relativize(projectDirPath);

		String projectPath = relativePath.toString();

		projectPath = ":" + projectPath.replace(File.separatorChar, ':');

		settings.include(new String[] {projectPath});

		ProjectDescriptor projectDescriptor = settings.findProject(projectPath);

		projectDescriptor.setProjectDir(projectDirPath.toFile());
	}

	protected void includeProjects(
			final Settings settings, final Path rootDirPath,
			final Path projectPathRootDirPath)
		throws IOException {

		final boolean modulesOnlyBuild = Boolean.getBoolean(
			"modules.only.build");
		final boolean portalBuild = Boolean.getBoolean("portal.build");

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (dirPath.equals(rootDirPath)) {
						return FileVisitResult.CONTINUE;
					}

					boolean moduleProjectDir = false;
					boolean otherProjectDir = false;

					if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						moduleProjectDir = true;
					}
					else {
						if (Files.exists(dirPath.resolve("build.xml")) ||
							Files.exists(dirPath.resolve("gulpfile.js"))) {

							otherProjectDir = true;
						}
					}

					if (!moduleProjectDir && !otherProjectDir) {
						return FileVisitResult.CONTINUE;
					}

					if (portalBuild &&
						Files.notExists(dirPath.resolve(".lfrbuild-portal"))) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					if (moduleProjectDir || !modulesOnlyBuild) {
						includeProject(
							settings, dirPath, projectPathRootDirPath);
					}

					return FileVisitResult.SKIP_SUBTREE;
				}

			});
	}

}