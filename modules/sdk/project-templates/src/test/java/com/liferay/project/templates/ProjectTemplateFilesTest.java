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

package com.liferay.project.templates;

import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class ProjectTemplateFilesTest {

	@BeforeClass
	public static void setUpClass() throws IOException {
		_projectTemplateDirPaths = new HashSet<>();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				_projectTemplateDirPaths.add(path);
			}
		}
	}

	@Test
	public void testProjectTemplateFiles() throws IOException {
		String gitIgnoreTemplate = FileTestUtil.read(
			"com/liferay/project/templates/dependencies" +
				"/archetype_resources_gitignore.tmpl");

		for (Path projectTemplateDirPath : _projectTemplateDirPaths) {
			_testProjectTemplateFiles(
				projectTemplateDirPath, gitIgnoreTemplate);
		}
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

	private void _testProjectTemplateFiles(
			Path projectTemplateDirPath, String gitIgnoreTemplate)
		throws IOException {

		Path archetypeMetadataXmlPath = projectTemplateDirPath.resolve(
			"src/main/resources/META-INF/maven/archetype-metadata.xml");

		Assert.assertTrue(
			"Missing " + archetypeMetadataXmlPath,
			Files.exists(archetypeMetadataXmlPath));

		Path archetypeResourcesDirPath = projectTemplateDirPath.resolve(
			"src/main/resources/archetype-resources");

		Assert.assertTrue(
			"Missing " + archetypeResourcesDirPath,
			Files.isDirectory(archetypeResourcesDirPath));

		Path buildGradlePath = archetypeResourcesDirPath.resolve(
			"build.gradle");

		Assert.assertTrue(
			"Missing " + buildGradlePath, Files.exists(buildGradlePath));

		Path dotGitIgnorePath = archetypeResourcesDirPath.resolve(".gitignore");
		Path gitIgnorePath = archetypeResourcesDirPath.resolve("gitignore");

		Assert.assertFalse(
			"Rename " + dotGitIgnorePath + " to " + gitIgnorePath +
				" to bypass GRADLE-1883",
			Files.exists(dotGitIgnorePath));

		Assert.assertTrue(
			"Missing " + gitIgnorePath, Files.exists(gitIgnorePath));

		Assert.assertEquals(
			"Incorrect " + gitIgnorePath, gitIgnoreTemplate,
			FileTestUtil.read(gitIgnorePath));

		Assert.assertFalse(
			"Forbidden Gradle wrapper in " + archetypeResourcesDirPath,
			Files.exists(archetypeResourcesDirPath.resolve("gradlew")));

		Path pomXmlPath = archetypeResourcesDirPath.resolve("pom.xml");

		Assert.assertTrue("Missing " + pomXmlPath, Files.exists(pomXmlPath));

		Files.walkFileTree(
			archetypeResourcesDirPath,
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

						Assert.assertNull(
							"Forbidden " + dirPath + File.separator + glob,
							FileUtil.getFile(dirPath, glob));
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = path.getFileName();

					String fileName = fileNamePath.toString();

					String extension = FileTestUtil.getExtension(fileName);

					if (!fileName.equals(".gitkeep") &&
						(_isInJavaSrcDir(path) != extension.equals("java"))) {

						Assert.fail("Wrong source directory " + path);
					}

					if (_isTextFile(fileName, extension)) {
						_testTextFile(path, fileName, extension);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _testTextFile(Path path, String fileName, String extension)
		throws IOException {

		String text = FileTestUtil.read(path);

		boolean trailingEmptyLine = false;

		if ((text.length() > 0) && text.charAt(text.length() - 1) == '\n') {
			trailingEmptyLine = true;
		}

		Assert.assertFalse("Trailing empty line in " + path, trailingEmptyLine);

		boolean firstEmptyLine = false;

		try (BufferedReader bufferedReader = new BufferedReader(
				new StringReader(text))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.isEmpty()) {
					Assert.assertFalse(
						"Forbidden empty line in " + path,
						firstEmptyLine && extension.equals("xml") &&
							!fileName.equals("service.xml"));

					firstEmptyLine = true;

					continue;
				}

				Assert.assertFalse(
					"Forbidden whitespace trailing character in " + path,
					Character.isWhitespace(line.charAt(line.length() - 1)));
			}
		}

		Matcher matcher = _velocityIfPattern.matcher(text);

		while (matcher.find()) {
			String condition = matcher.group(1);

			Assert.assertEquals(
				"Source formatting error in " + path,
				"#if (" + condition.trim() + ")", matcher.group());
		}

		if (extension.equals("xml")) {
			String xmlDeclaration = _XML_DECLARATION;

			if (fileName.equals("service.xml")) {
				xmlDeclaration = _SERVICE_XML_DECLARATION;
			}

			Assert.assertTrue(
				"Incorrect XML declaration in " + path,
				text.startsWith(xmlDeclaration));
		}
	}

	private static final String _SERVICE_XML_DECLARATION;

	private static final String[] _SOURCESET_NAMES = {
		"main", "test", "testIntegration"
	};

	private static final String _XML_DECLARATION =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n";

	private static Set<Path> _projectTemplateDirPaths;
	private static final Set<String> _textFileExtensions = new HashSet<>(
		Arrays.asList(
			"bnd", "gradle", "java", "jsp", "jspf", "properties", "xml"));
	private static final Pattern _velocityIfPattern = Pattern.compile(
		"#if\\s*\\(\\s*(.+)\\s*\\)");

	static {
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\"?>");
		sb.append('\n');
		sb.append("<!DOCTYPE service-builder PUBLIC ");
		sb.append("\"-//Liferay//DTD Service Builder 7.0.0//EN\" ");
		sb.append("\"http://www.liferay.com/dtd/");
		sb.append("liferay-service-builder_7_0_0.dtd\">\n\n");

		_SERVICE_XML_DECLARATION = sb.toString();
	}

}