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

import java.util.Arrays;
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
public class ProjectTemplatesAPIJarTest
	implements BaseProjectTemplatesTestCase {

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

		Path classesPath = classesDir.toPath();

		Set<String> classPaths = _getPaths(classesPath, ".class");

		Assert.assertFalse(classPaths.isEmpty());

		Set<String> javaPaths = _getPaths(sourcesDir.toPath(), ".java");

		Assert.assertFalse(javaPaths.isEmpty());

		for (String classPath : classPaths) {
			if (!classPath.contains("$") &&
				!_ignoreClassPaths.contains(classPath)) {

				Assert.assertTrue(
					"Missing class " + classPath,
					javaPaths.contains(classPath));
			}
		}

		Map<String, Set<String>> tldClassPaths = _getTLDClassPaths(
			"/taglib/tag/tag-class", classesPath);

		Assert.assertFalse(tldClassPaths.isEmpty());

		Set<Map.Entry<String, Set<String>>> tldEntries =
			tldClassPaths.entrySet();

		StringBuilder tldSb = new StringBuilder("");

		tldEntries.forEach(
			entry -> {
				Set<String> tldClasses = entry.getValue();

				Stream<String> stream = tldClasses.stream();

				List<String> missingTLDClasses = stream.filter(
					tldClass -> !classPaths.contains(tldClass)
				).collect(
					Collectors.toList()
				);

				if (!missingTLDClasses.isEmpty()) {
					tldSb.append(entry.getKey() + "\n");

					missingTLDClasses.forEach(
						tldClass -> tldSb.append("\t" + tldClass + "\n"));
				}
			});

		Assert.assertTrue(
			"Missing TLD classes: " + tldSb.toString(),
			Objects.equals("", tldSb.toString()));

		Map<String, Set<String>> teiClassPaths = _getTLDClassPaths(
			"/taglib/tag/tei-class", classesPath);

		Assert.assertFalse(teiClassPaths.isEmpty());

		Set<Map.Entry<String, Set<String>>> teiEntries =
			teiClassPaths.entrySet();

		StringBuilder teriSb = new StringBuilder("");

		teiEntries.forEach(
			entry -> {
				Set<String> teiClasses = entry.getValue();

				Stream<String> stream = teiClasses.stream();

				List<String> missingTEIClasses = stream.filter(
					teiClass -> !classPaths.contains(teiClass)
				).collect(
					Collectors.toList()
				);

				if (!missingTEIClasses.isEmpty()) {
					teriSb.append(entry.getKey() + "\n");

					missingTEIClasses.forEach(
						teiClass -> teriSb.append("\t" + teiClass + "\n"));
				}
			});

		Assert.assertTrue(
			"Missing TEI classes: " + teriSb.toString(),
			Objects.equals("", teriSb.toString()));

		Set<Path> sevicesPaths = _getServicesPaths(classesDir.toPath());

		Stream<Path> seviceFilesStream = sevicesPaths.stream();

		List<String> serviceClassNames = seviceFilesStream.map(
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
		).collect(
			Collectors.toList()
		);

		for (String serviceClassName : serviceClassNames) {
			String servicePath = serviceClassName.replace(".", "/");

			Assert.assertTrue(
				"Missing service class " + servicePath,
				classPaths.contains(servicePath));
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

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

	private static final List<String> _ignoreClassPaths = Arrays.asList(
		"com/fasterxml/jackson/databind/deser/std/BaseNodeDeserializer",
		"javax/servlet/http/NoBodyOutputStream",
		"javax/servlet/http/NoBodyResponse",
		"org/osgi/framework/AdaptPermissionCollection",
		"org/osgi/framework/AdminPermissionCollection",
		"org/osgi/framework/BundlePermissionCollection",
		"org/osgi/framework/CapabilityPermissionCollection",
		"org/osgi/framework/PackagePermissionCollection",
		"org/osgi/framework/ServicePermissionCollection",
		"org/osgi/service/cm/ConfigurationPermissionCollection",
		"org/osgi/service/condpermadmin/BooleanCondition",
		"org/osgi/service/event/TopicPermissionCollection");

}