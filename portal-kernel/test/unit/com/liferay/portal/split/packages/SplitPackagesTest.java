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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tom Wang
 */
public class SplitPackagesTest {

	@Test
	public void testSplitPackage() throws IOException {
		Set<String> portalImplPackageNames = _getPackageNames(
			Paths.get("portal-impl/src"));

		Set<String> portalServicePackageNames = _getPackageNames(
			Paths.get("portal-service/src"));

		portalImplPackageNames.retainAll(portalServicePackageNames);

		Assert.assertTrue(
			"Detected split packages in portal-service and portal-impl: " +
				portalImplPackageNames,
			portalImplPackageNames.isEmpty());
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