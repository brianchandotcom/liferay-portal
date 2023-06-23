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

package com.liferay.portal.tools.rest.builder;

import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sarai Díaz
 */
public class RESTBuilderTest {

	@Test
	public void testCreateRESTBuilder() throws Exception {
		Path dependenciesPath = _getDependenciesPath();

		Path copyrightFilePath = dependenciesPath.resolve("copyright.txt");

		RESTBuilder restBuilder = new RESTBuilder(
			copyrightFilePath.toFile(), dependenciesPath.toFile(), null, null);

		restBuilder.build();

		_assertDirectoryEquals(
			dependenciesPath.resolve("expected"), _getActualPath());
	}

	private void _assertDirectoryEquals(Path expectedPath, Path actualPath)
		throws Exception {

		Files.walkFileTree(
			expectedPath,
			new FileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
					Path path, IOException ioException) {

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
					Path path, BasicFileAttributes basicFileAttributes) {

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path relativePath = expectedPath.relativize(path);

					Assert.assertEquals(
						"Error comparing " + relativePath,
						new String(Files.readAllBytes(path)),
						new String(
							Files.readAllBytes(
								actualPath.resolve(relativePath))));

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFileFailed(
					Path file, IOException ioException) {

					return FileVisitResult.TERMINATE;
				}

			});
	}

	private Path _getActualPath() {
		Path path = Paths.get("");

		Path absolutePath = path.toAbsolutePath();

		return Paths.get(absolutePath.toString(), "test-classes", "actual");
	}

	private Path _getDependenciesPath() {
		URL resource = getClass().getResource("");

		return Paths.get(resource.getPath(), "dependencies");
	}

}