/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.split.packages;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

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

		Path portalImplPath = portalPath.resolve("portal-impl");

		_checkSplitPackages(
			portalImplPath, portalPath, moduleMap,
			portalImplPath.resolve("src"));

		final Set<Path> ignorePaths = new HashSet<>(
			Arrays.asList(
				portalPath.resolve("portal-impl"),
				portalPath.resolve("portal-test")));

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

					if (Files.exists(dirPath.resolve(".lfrbuild-portal"))) {
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

		Set<String> packageNames = _getPackageNames(sourcePath);

		boolean addedToImpl = false;

		for (Map.Entry<Path, Set<String>> entry : moduleMap.entrySet()) {
			Set<String> modulePackageNames = new HashSet<>(entry.getValue());

			modulePackageNames.retainAll(packageNames);

			Path modulePath = entry.getKey();

			if (!modulePackageNames.isEmpty() &&
				modulePath.equals(Paths.get("portal-impl")) &&
				Files.exists(dirPath.resolve(".lfrbuild-app-server-lib"))) {

				Set<String> portalImplPackages = entry.getValue();

				portalImplPackages.addAll(packageNames);

				addedToImpl = true;

				modulePackageNames.clear();
			}

			Assert.assertTrue(
				StringBundler.concat(
					"Detected split packages in ",
					portalPath.relativize(dirPath), " and ", modulePath, ": ",
					modulePackageNames),
				modulePackageNames.isEmpty());
		}

		if (!addedToImpl) {
			moduleMap.put(portalPath.relativize(dirPath), packageNames);
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
							String relativePathString = String.valueOf(
								path.relativize(dirPath));

							packageNames.add(
								StringUtil.replace(
									relativePathString, '/', '.'));
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return packageNames;
	}

}