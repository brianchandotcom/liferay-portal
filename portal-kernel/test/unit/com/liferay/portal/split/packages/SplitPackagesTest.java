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
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (ignorePaths.contains(dirPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (portalPath.equals(dirPath.getParent())) {
						Path sourcePath = dirPath.resolve("src");

						if (Files.exists(sourcePath)) {
							_checkSplitPackages(
								dirPath, portalPath, moduleMap, sourcePath);

							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					if (Files.exists(dirPath.resolve("portal.build"))) {
						Path sourcePath = dirPath.resolve("src/main/java");

						if (Files.exists(dirPath.resolve("docroot"))) {
							sourcePath = dirPath.resolve(
								"docroot/WEB-INF/src/main/java");
						}

						if (Files.exists(sourcePath)) {
							_checkSplitPackages(
								dirPath, portalPath, moduleMap, sourcePath);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _checkSplitPackages(
			Path dirPath, Path portalPath, Map<Path, Set<String>> moduleMap,
			Path sourcePath)
		throws IOException {

		Set<String> packages = _getPackageNames(sourcePath);

		boolean addedToImpl = false;

		for (Map.Entry<Path, Set<String>> entry : moduleMap.entrySet()) {
			Set<String> modulePackages = new HashSet<>(entry.getValue());

			modulePackages.retainAll(packages);

			Path modulePath = entry.getKey();

			if (!modulePackages.isEmpty() &&
				modulePath.equals(Paths.get("portal-impl"))) {

				String buildGradleContent = new String(
					Files.readAllBytes(dirPath.resolve("build.gradle")));

				if (buildGradleContent.contains(
						"deployDir = new File(appServerPortalDir, " +
							"\"WEB-INF/lib\")")) {

					Set<String> portalImplPackages = entry.getValue();

					portalImplPackages.addAll(packages);

					addedToImpl = true;

					modulePackages.clear();
				}
			}

			Assert.assertTrue(
				"Detected split packages in " + portalPath.relativize(dirPath) +
					" and " + modulePath + ": " + modulePackages,
				modulePackages.isEmpty());
		}

		if (!addedToImpl) {
			moduleMap.put(portalPath.relativize(dirPath), packages);
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

}