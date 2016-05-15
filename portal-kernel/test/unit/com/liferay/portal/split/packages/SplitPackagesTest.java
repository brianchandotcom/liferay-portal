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

package com.liferay.portal.split.packages;

import java.io.IOException;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tom Wang
 */
public class SplitPackagesTest {

	@Test
	public void testSplitPackage() throws IOException {
		final Map<Path, Set<String>> moduleMap = new HashMap<>();

		final Path portalPath = Paths.get(System.getProperty("user.dir"));

		final Set<Path> ignorePaths = new HashSet<>(
			Arrays.asList(
				portalPath.resolve("portal-test"),
				portalPath.resolve("portal-test-integration")));

		Files.walkFileTree(
			portalPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (ignorePaths.contains(path)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (portalPath.equals(path.getParent())) {
						Path sourcePath = path.resolve("src");

						if (Files.exists(sourcePath)) {
							Set<String> packages = _getPackageNames(sourcePath);

							_checkPackageSet(
								path, portalPath, moduleMap, packages);

							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					if (Files.exists(path.resolve("portal.build"))) {
						Set<String> packages = new HashSet<>();

						if (Files.exists(path.resolve("docroot"))) {
							Path sourcePath = path.resolve(_DOCROOTPATH);

							if (Files.exists(sourcePath)) {
								packages = _getPackageNames(sourcePath);
							}
						}
						else {
							Path sourcePath = path.resolve(_MAINJAVAPATH);

							if (Files.exists(sourcePath)) {
								packages = _getPackageNames(sourcePath);
							}
						}

						_checkPackageSet(path, portalPath, moduleMap, packages);

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _checkPackageSet(
			Path path, Path portalPath, Map<Path, Set<String>> moduleMap,
			Set<String> packages)
		throws IOException {

		boolean addedToImpl = false;

		for (Path mapKeyPath : moduleMap.keySet()) {
			Set<String> mapPackages = moduleMap.get(mapKeyPath);

			Set<String> currentPackages = new HashSet<>(packages);

			currentPackages.retainAll(mapPackages);

			if (!currentPackages.isEmpty()) {
				if (mapKeyPath.equals(Paths.get("portal-impl"))) {
					String text = new String(
						Files.readAllBytes(path.resolve("build.gradle")));

					if (text.contains(
							"deployDir = new File(appServerPortalDir, " +
								"\"WEB-INF/lib\")")) {

						mapPackages.addAll(currentPackages);

						moduleMap.put(mapKeyPath, mapPackages);

						addedToImpl = true;

						currentPackages.clear();
					}
				}
			}

			Assert.assertTrue(
				"Detected split packages in " + portalPath.relativize(path) +
					" and " + mapKeyPath + ": " + currentPackages,
				currentPackages.isEmpty());
		}

		if (!addedToImpl) {
			moduleMap.put(portalPath.relativize(path), packages);
		}
	}

	private Set<String> _getPackageNames(final Path path) throws IOException {
		final Set<String> packageNames = new HashSet<>();

		Files.walkFileTree(
			path,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					try (DirectoryStream<Path> directoryStream =
							Files.newDirectoryStream(dirPath, "*.java")) {

						Iterator<Path> iterator = directoryStream.iterator();

						if (iterator.hasNext()) {
							Path relativePath = path.relativize(dirPath);

							String relativePathString = relativePath.toString();

							packageNames.add(
								relativePathString.replace('/', '.'));
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return packageNames;
	}

	private static final Path _DOCROOTPATH = Paths.get(
		"docroot", "WEB-INF", "src", "main", "java");

	private static final Path _MAINJAVAPATH = Paths.get("src", "main", "java");

}