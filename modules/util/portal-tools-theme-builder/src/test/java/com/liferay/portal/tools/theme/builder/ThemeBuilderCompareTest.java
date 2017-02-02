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

package com.liferay.portal.tools.theme.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public class ThemeBuilderCompareTest {

	@Parameters(name = "{1}")
	public static Iterable<Object[]> getTestThemes() throws Exception {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(
				Paths.get("build/test-theme-dependencies.properties"))) {

			properties.load(inputStream);
		}

		List<Object[]> testThemes = new ArrayList<>(properties.size());

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		for (String dirName : properties.stringPropertyNames()) {
			String warFileName = properties.getProperty(dirName);

			testThemes.add(
				_getTestTheme(dirName, warFileName, documentBuilderFactory));
		}

		return testThemes;
	}

	@BeforeClass
	public static void setUpClass() throws IOException {
		try (BufferedReader bufferedReader = Files.newBufferedReader(
				Paths.get("build/parent-theme-dependencies.txt"))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				File jarFile = new File(line);

				Assert.assertTrue(jarFile.isFile());

				if (line.contains("com.liferay.frontend.theme.styled-")) {
					_styledJarFile = jarFile;
				}
				else if (line.contains(
							"com.liferay.frontend.theme.unstyled-")) {

					_unstyledJarFile = jarFile;
				}
			}
		}
	}

	public ThemeBuilderCompareTest(
		File diffsDir, String name, String parentName, String templateExtension,
		File warFile) {

		_diffsDir = diffsDir;
		_name = name;
		_parentDir = _getParentDir(parentName);
		_parentName = parentName;
		_templateExtension = templateExtension;
		_warFile = warFile;
	}

	@Test
	public void testThemeBuilderCompare() throws Exception {
		File outputDir = temporaryFolder.newFolder("output");

		ThemeBuilder themeBuilder = new ThemeBuilder(
			_diffsDir, _name, outputDir, _parentDir, _parentName,
			_templateExtension, _unstyledJarFile);

		themeBuilder.build();

		File warDir = temporaryFolder.newFolder("war");

		_unzip(_warFile, warDir);

		String otherTemplateExtension = "vm";

		if (_templateExtension.equals("vm")) {
			otherTemplateExtension = "ftl";
		}

		String[] excludePatterns = {
			"css/*.css", "images/thumbnail.png",
			"templates/*." + otherTemplateExtension, "WEB-INF/**"
		};

		Map<String, byte[]> outputFileNameDigests = _getFileNameDigests(
			outputDir.toPath(), excludePatterns);
		Map<String, byte[]> warFileNameDigests = _getFileNameDigests(
			warDir.toPath(), excludePatterns);

		Assert.assertEquals(
			warFileNameDigests.toString(), outputFileNameDigests.size(),
			warFileNameDigests.size());

		for (Map.Entry<String, byte[]> entry :
				outputFileNameDigests.entrySet()) {

			String fileName = entry.getKey();

			byte[] outputFileDigest = entry.getValue();
			byte[] warFileDigest = warFileNameDigests.get(fileName);

			Assert.assertArrayEquals(
				"File " + fileName + " does not match", outputFileDigest,
				warFileDigest);
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static Map<String, byte[]> _getFileNameDigests(
			final Path dirPath, String... excludePatterns)
		throws Exception {

		final Map<String, byte[]> fileNameDigests = new HashMap<>();

		final MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		final List<PathMatcher> excludePathMatchers = new ArrayList<>(
			excludePatterns.length);

		FileSystem fileSystem = dirPath.getFileSystem();

		String dirName = dirPath.toString();

		if (File.separatorChar != '/') {
			dirName = dirName.replace(File.separatorChar, '/');
		}

		for (String pattern : excludePatterns) {
			PathMatcher pathMatcher = fileSystem.getPathMatcher(
				"glob:" + dirName + "/" + pattern);

			excludePathMatchers.add(pathMatcher);
		}

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					for (PathMatcher pathMatcher : excludePathMatchers) {
						if (pathMatcher.matches(path)) {
							return FileVisitResult.CONTINUE;
						}
					}

					Path relativePath = dirPath.relativize(path);

					messageDigest.reset();

					messageDigest.update(Files.readAllBytes(path));

					fileNameDigests.put(
						relativePath.toString(), messageDigest.digest());

					return FileVisitResult.CONTINUE;
				}

			});

		return fileNameDigests;
	}

	private static File _getParentDir(String parentName) {
		if (parentName.equals(ThemeBuilder.STYLED)) {
			return _styledJarFile;
		}

		if (parentName.equals(ThemeBuilder.UNSTYLED)) {
			return _unstyledJarFile;
		}

		throw new IllegalArgumentException(
			"Unsupported parent name " + parentName);
	}

	private static Object[] _getTestTheme(
			String dirName, String warFileName,
			DocumentBuilderFactory documentBuilderFactory)
		throws Exception {

		File dir = new File(dirName);

		File diffsDir = new File(dir, "src");

		JSONObject packageJSON;

		try (FileReader fileReader = new FileReader(
				new File(dir, "package.json"))) {

			packageJSON = (JSONObject)JSONValue.parseWithException(fileReader);
		}

		String name = (String)packageJSON.get("name");

		JSONObject liferayThemeJSON = (JSONObject)packageJSON.get(
			"liferayTheme");

		String parentName = (String)liferayThemeJSON.get("baseTheme");

		if (parentName.equals("styled")) {
			parentName = ThemeBuilder.STYLED;
		}
		else if (parentName.equals("unstyled")) {
			parentName = ThemeBuilder.UNSTYLED;
		}
		else {
			throw new IllegalArgumentException(
				"Unsupported base theme " + parentName);
		}

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(
			new File(dir, "src/WEB-INF/liferay-look-and-feel.xml"));

		NodeList nodeList = document.getElementsByTagName("template-extension");

		Node node = nodeList.item(0);

		String templateExtension = node.getTextContent();

		File warFile = new File(warFileName);

		return new Object[] {
			diffsDir, name, parentName, templateExtension, warFile
		};
	}

	private static void _unzip(File file, File outputDir) throws IOException {
		Path outputDirPath = outputDir.toPath();

		try (ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith("/")) {
					continue;
				}

				Path path = outputDirPath.resolve(name);

				Files.createDirectories(path.getParent());

				Files.copy(zipFile.getInputStream(zipEntry), path);
			}
		}
	}

	private static File _styledJarFile;
	private static File _unstyledJarFile;

	private final File _diffsDir;
	private final String _name;
	private final File _parentDir;
	private final String _parentName;
	private final String _templateExtension;
	private final File _warFile;

}