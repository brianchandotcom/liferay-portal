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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
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

		_standaloneDirPath = resourcesDirPath.resolve(
			GradleTemplates.TEMPLATES_STANDALONE_DIR);
		_workspaceDirPath = resourcesDirPath.resolve(
			GradleTemplates.TEMPLATES_WORKSPACE_DIR);
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

	private boolean _endsWithEmptyLine(Path path) throws IOException {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(
				path.toFile(), "r")) {

			long pos = randomAccessFile.length() - 1;

			if (pos < 0) {
				return false;
			}

			randomAccessFile.seek(pos);

			int c = randomAccessFile.read();

			if ((c == '\n') || (c == '\r')) {
				return true;
			}
		}

		return false;
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

	private String _getExtension(String fileName) {
		int pos = fileName.indexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	private boolean _isInJavaSrcDir(Path path) throws IOException {
		path = path.toRealPath();

		String pathString = path.toString();

		if (File.separatorChar != '/') {
			pathString = pathString.replace(File.separatorChar, '/');
		}

		for (String sourceSetName : _SOURCESET_NAMES) {
			if (pathString.contains("/src/" + sourceSetName + "/java/")) {
				return true;
			}
		}

		return false;
	}

	private boolean _isTextFile(String fileName, String extension) {
		if (fileName.equals("gitignore") ||
			_textFileExtensions.contains(extension)) {

			return true;
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

	private void _testLanguageProperties(Path path) throws IOException {
		try (BufferedReader bufferedReader = Files.newBufferedReader(
				path, StandardCharsets.UTF_8)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				Assert.assertFalse(
					"Forbidden empty line in " + path, line.isEmpty());
				Assert.assertFalse(
					"Forbidden comments in " + path, line.startsWith("##"));
			}
		}
	}

	private void _testTemplateFiles(Path rootDirPath) throws IOException {
		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path languagePropertiesPath = dirPath.resolve(
						"Language.properties");

					if (Files.exists(languagePropertiesPath)) {
						_testLanguageProperties(languagePropertiesPath);

						String glob = "Language_*.properties";

						Assert.assertFalse(
							"Forbidden " + dirPath + File.separator + glob,
							_exists(dirPath, glob));
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = path.getFileName();

					String fileName = fileNamePath.toString();

					String extension = _getExtension(fileName);

					if (!fileName.equals(".gitkeep") &&
						(_isInJavaSrcDir(path) != extension.equals("java"))) {

						Assert.fail("Wrong source directory " + path);
					}

					if (_isTextFile(fileName, extension)) {
						_testTextFileLines(path);

						if (!_trailingEmptyLineAllowedFileNames.contains(
								fileName)) {

							Assert.assertFalse(
								"Trailing empty line in " + path,
								_endsWithEmptyLine(path));
						}
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

	private void _testTextFileLines(Path path) throws IOException {
		try (BufferedReader bufferedReader = Files.newBufferedReader(
				path, StandardCharsets.UTF_8)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}

				Assert.assertFalse(
					"Forbidden whitespace trailing character in " + path,
					Character.isWhitespace(line.charAt(line.length() - 1)));
			}
		}
	}

	private static final String[] _SOURCESET_NAMES = {
		"main", "test", "testIntegration"
	};

	private static Path _standaloneDirPath;
	private static final Set<String> _textFileExtensions = new HashSet<>(
		Arrays.asList(
			"bnd", "gradle", "java", "jsp", "jspf", "properties", "xml"));
	private static final Set<String> _trailingEmptyLineAllowedFileNames =
		new HashSet<>(
			Arrays.asList(
				"gradle-wrapper.properties", "gradlew", "gradlew.bat"));
	private static Path _workspaceDirPath;

}