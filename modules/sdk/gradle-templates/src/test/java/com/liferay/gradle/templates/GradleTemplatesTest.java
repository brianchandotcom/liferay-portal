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

package com.liferay.gradle.templates;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class GradleTemplatesTest {

	@BeforeClass
	public static void setUpClass() {
		Path resourcesDirPath = Paths.get("src/main/resources");

		_standaloneDirPath = resourcesDirPath.resolve("standalone");
		_workspaceDirPath = resourcesDirPath.resolve("workspace");
	}

	@Test
	public void testTemplateFiles() throws IOException {
		_testTemplateFiles(_standaloneDirPath);
		_testTemplateFiles(_workspaceDirPath);
	}

	@Test
	public void testTemplates() throws IOException {
		_testTemplates(_standaloneDirPath, _workspaceDirPath, false, false);
		_testTemplates(_workspaceDirPath, _standaloneDirPath, true, true);
	}

	private boolean _exists(Path dirPath, String glob) throws IOException {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			if (iterator.hasNext()) {
				return true;
			}
		}

		return false;
	}

	private String _readProperty(Path path, String key) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties.getProperty(key);
	}

	private void _testTemplateFiles(Path rootDirPath) throws IOException {
		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (Files.exists(dirPath.resolve("language.properties"))) {
						String glob = "Language_*.properties";

						Assert.assertFalse(
							"Forbidden " + dirPath + File.separator + glob,
							_exists(dirPath, glob));
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _testTemplates(
			Path rootDirPath, Path otherRootDirPath, boolean gitIgnoreForbidden,
			boolean gradlewForbidden)
		throws IOException {

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				rootDirPath)) {

			final AtomicReference<String> previousGradlewDistributionUrl =
				new AtomicReference<>(null);

			for (Path path : directoryStream) {
				if (!Files.isDirectory(path)) {
					continue;
				}

				Path dirNamePath = path.getFileName();

				Path otherDirPath = otherRootDirPath.resolve(
					dirNamePath.toString());

				Assert.assertTrue(
					"Missing " + otherDirPath, Files.exists(otherDirPath));

				Path gitIgnorePath = path.resolve("gitignore");
				Path dotGitIgnorePath = path.resolve(".gitignore");

				Assert.assertFalse(
					"Rename " + dotGitIgnorePath + " to " + gitIgnorePath +
						" to bypass GRADLE-1883",
					Files.exists(dotGitIgnorePath));

				if (gitIgnoreForbidden) {
					Assert.assertFalse(
						"Forbidden " + gitIgnorePath,
						Files.exists(gitIgnorePath));
				}
				else {
					Assert.assertTrue(
						"Missing " + gitIgnorePath,
						Files.exists(gitIgnorePath));
				}

				boolean gradlewExists = Files.exists(path.resolve("gradlew"));

				if (gradlewForbidden) {
					Assert.assertFalse(
						"Forbidden Gradle wrapper in " + path, gradlewExists);
				}
				else {
					Assert.assertTrue(
						"Missing Gradle wrapper in " + path, gradlewExists);

					String gradlewDistributionUrl = _readProperty(
						path.resolve(
							"gradle/wrapper/gradle-wrapper.properties"),
						"distributionUrl");

					boolean first =
						previousGradlewDistributionUrl.compareAndSet(
							null, gradlewDistributionUrl);

					if (!first) {
						Assert.assertEquals(
							"Wrong Gradle wrapper distribution URL in " + path,
							previousGradlewDistributionUrl.get(),
							gradlewDistributionUrl);
					}
				}
			}
		}
	}

	private static Path _standaloneDirPath;
	private static Path _workspaceDirPath;

}