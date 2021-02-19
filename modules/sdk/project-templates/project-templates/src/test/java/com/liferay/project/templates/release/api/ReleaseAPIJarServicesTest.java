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

package com.liferay.project.templates.release.api;

import java.io.IOException;

import java.net.URI;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Simon Jiang
 * @author Lawrence Lee
 */
public class ReleaseAPIJarServicesTest implements BaseReleaseAPIJarTestCase {

	public Stream<Path> getServicePaths(Path sourcePath) throws Exception {
		Set<Path> results = new HashSet<>();

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					URI folderURI = sourcePath.toUri();

					URI relativeURI = folderURI.relativize(path.toUri());

					String relativeString = relativeURI.getPath();

					if (relativeString.contains("META-INF/services/")) {
						results.add(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return results.stream();
	}

	@Test
	public void testReleaseApiJarService() throws Exception {
		Assume.assumeNotNull(RELEASE_API_JAR_FILE);

		Path classesDirPath = getClassesDirPath(temporaryFolder);

		Set<String> classFileStrings = getPaths(classesDirPath, ".class");

		Assert.assertFalse(classFileStrings.isEmpty());

		Stream<Path> serviceClassPathsStream = getServicePaths(classesDirPath);

		List<String> missingServiceClassFileStrings = new ArrayList<>();

		serviceClassPathsStream.map(
			filePath -> {
				try {
					return Files.readAllLines(filePath);
				}
				catch (Exception exception) {
				}

				return null;
			}
		).flatMap(
			services -> services.stream()
		).map(
			serviceClassPath -> serviceClassPath.replace(".", "/")
		).forEach(
			serviceClassPath -> {
				if (!classFileStrings.contains(serviceClassPath)) {
					missingServiceClassFileStrings.add(serviceClassPath);
				}
			}
		);

		Assert.assertTrue(
			"Sources jar missing service classes: " +
				getFileNames(missingServiceClassFileStrings),
			missingServiceClassFileStrings.isEmpty());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

}