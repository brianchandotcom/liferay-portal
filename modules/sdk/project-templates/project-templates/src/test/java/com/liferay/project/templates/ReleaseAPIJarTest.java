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

import com.liferay.petra.string.StringBundler;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URI;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Simon Jiang
 */
public class ReleaseAPIJarTest {

	@Test
	public void testReleaseApiJar() throws Exception {
		Assume.assumeNotNull(_RELEASE_API_JAR_FILE);

		File releaseApiJarFile = new File(_RELEASE_API_JAR_FILE);

		Assert.assertTrue(releaseApiJarFile.exists());

		File releaseApiJarSourcesFile = new File(_RELEASE_API_JAR_SOURCES_FILE);

		Assert.assertTrue(releaseApiJarSourcesFile.exists());

		File classesDir = temporaryFolder.newFolder(
			releaseApiJarFile.getName() + "-classes");

		ZipUtil.unzip(releaseApiJarFile, classesDir);

		File sourcesDir = temporaryFolder.newFolder(
			releaseApiJarSourcesFile.getName() + "-sources");

		ZipUtil.unzip(releaseApiJarSourcesFile, sourcesDir);

		Path classesDirPath = classesDir.toPath();

		Set<String> classFilePaths = _getPaths(classesDirPath, ".class");

		Assert.assertFalse(classFilePaths.isEmpty());

		Set<String> javaFilePaths = _getPaths(sourcesDir.toPath(), ".java");

		Assert.assertFalse(javaFilePaths.isEmpty());

		List<String> missingClassFilePaths = new ArrayList<>();

		List<String> ignoredPaths = _getIgnorePaths(
			releaseApiJarFile.getName());

		for (String classFilePath : classFilePaths) {
			if (!classFilePath.contains("$") &&
				!ignoredPaths.contains(classFilePath) &&
				!javaFilePaths.contains(classFilePath)) {

				missingClassFilePaths.add(classFilePath);
			}
		}

		_assertTLDClasses(
			"/taglib/tag/tag-class", classesDirPath, classFilePaths);

		_assertTLDClasses(
			"/taglib/tag/tei-class", classesDirPath, classFilePaths);

		Set<Path> serviceClassFilePaths = _getServicesPaths(
			classesDir.toPath());

		Stream<Path> serviceFilesStream = serviceClassFilePaths.stream();

		List<String> missingServiceClassFilePaths = new ArrayList<>();

		serviceFilesStream.map(
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
			serviceClassFilePath -> serviceClassFilePath.replace(".", "/")
		).forEach(
			serviceClassFilePath -> {
				if (!classFilePaths.contains(serviceClassFilePath)) {
					missingServiceClassFilePaths.add(serviceClassFilePath);
				}
			}
		);

		Assert.assertTrue(
			"Sources jar missing: " + _getFileNames(missingClassFilePaths),
			missingClassFilePaths.isEmpty());
		Assert.assertTrue(
			"Sources jar missing service classes: " +
				_getFileNames(missingServiceClassFilePaths),
			missingServiceClassFilePaths.isEmpty());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _assertTLDClasses(
			String filterPath, Path classesPath, Set<String> classes)
		throws Exception {

		Map<String, Set<String>> tldClassPaths = _getTLDClassPaths(
			filterPath, classesPath);

		Assert.assertFalse(tldClassPaths.isEmpty());

		Set<Map.Entry<String, Set<String>>> tldEntries =
			tldClassPaths.entrySet();

		StringBuilder sb = new StringBuilder("");

		tldEntries.forEach(
			entry -> {
				Set<String> tldClasses = entry.getValue();

				Stream<String> stream = tldClasses.stream();

				List<String> missingTLDClasses = stream.filter(
					tldClass -> !classes.contains(tldClass)
				).collect(
					Collectors.toList()
				);

				if (!missingTLDClasses.isEmpty()) {
					sb.append(entry.getKey() + "\n");

					missingTLDClasses.forEach(
						tldClass -> sb.append("\t" + tldClass + "\n"));
				}
			});

		Assert.assertTrue(
			"Missing TLD classes:\n" + sb.toString(),
			Objects.equals("", sb.toString()));
	}

	private String _getFileNames(List<String> fileNames) throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("\n");

		for (String fileName : fileNames) {
			sb.append(fileName);
			sb.append("\n");
		}

		return sb.toString();
	}

	private List<String> _getIgnorePaths(String jarName) throws Exception {
		List<String> ignoredPaths = FileTestUtil.readAllLines(
			"com/liferay/project/templates/dependencies" +
				"/api-jar-ignore-paths.txt");

		if (jarName.contains("7.0")) {
			ignoredPaths.addAll(
				FileTestUtil.readAllLines(
					"com/liferay/project/templates/dependencies" +
						"/api-jar-ignore-paths_7_0.txt"));
		}
		else if (jarName.contains("7.1")) {
			ignoredPaths.addAll(
				FileTestUtil.readAllLines(
					"com/liferay/project/templates/dependencies" +
						"/api-jar-ignore-paths_7_1.txt"));
		}
		else if (jarName.contains("7.2")) {
			ignoredPaths.addAll(
				FileTestUtil.readAllLines(
					"com/liferay/project/templates/dependencies" +
						"/api-jar-ignore-paths_7_2.txt"));
		}

		return ignoredPaths;
	}

	private Set<String> _getPaths(Path sourcePath, String extension)
		throws Exception {

		Set<String> results = new HashSet<>();

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(extension)) {
						URI folderURI = sourcePath.toUri();

						URI relativeURI = folderURI.relativize(path.toUri());

						String relativePath = relativeURI.getPath();

						results.add(relativePath.replace(extension, ""));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return results;
	}

	private Set<Path> _getServicesPaths(Path sourcePath) throws Exception {
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

					String relativePath = relativeURI.getPath();

					if (relativePath.contains("META-INF/services/")) {
						results.add(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return results;
	}

	private Map<String, Set<String>> _getTLDClassPaths(
			String xpathFilter, Path sourcePath)
		throws Exception {

		Map<String, Set<String>> results = new HashMap<>();

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".tld")) {
						results.computeIfAbsent(
							fileName, key -> new HashSet<String>());

						try (FileInputStream fileInputStream =
								new FileInputStream(path.toFile())) {

							DocumentBuilderFactory builderFactory =
								DocumentBuilderFactory.newInstance();

							DocumentBuilder documentBuilder =
								builderFactory.newDocumentBuilder();

							Document xmlDocument = documentBuilder.parse(
								fileInputStream);

							XPathFactory xPathFactory =
								XPathFactory.newInstance();

							XPath xPath = xPathFactory.newXPath();

							XPathExpression xPathExpression = xPath.compile(
								xpathFilter);

							NodeList nodeList =
								(NodeList)xPathExpression.evaluate(
									xmlDocument, XPathConstants.NODESET);

							Set<String> paths = results.get(fileName);

							for (int i = nodeList.getLength() - 1; i >= 0;
								 i--) {

								Node childNode = nodeList.item(i);

								String textContent = childNode.getTextContent();

								if (textContent.startsWith("com.liferay.")) {
									paths.add(textContent.replace('.', '/'));
								}
							}
						}
						catch (Exception exception) {
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return results;
	}

	private static final String _RELEASE_API_JAR_FILE = System.getProperty(
		"releaseApiJarFile");

	private static final String _RELEASE_API_JAR_SOURCES_FILE =
		System.getProperty("releaseApiJarSourcesFile");

}