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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.gradle.internal.impldep.org.junit.Assume;

import org.junit.Assert;
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

		File classesFolder = temporaryFolder.newFolder(
			releaseApiJarFile.getName() + "-classes");

		ZipUtil.unzip(releaseApiJarFile, classesFolder);

		File sourcesFolder = temporaryFolder.newFolder(
			releaseApiJarSourcesFile.getName() + "-sources");

		ZipUtil.unzip(releaseApiJarSourcesFile, sourcesFolder);

		Path classesPath = classesFolder.toPath();

		Set<String> classFiles = _searchFiles(classesPath, ".class");

		Assert.assertFalse(classFiles.isEmpty());

		Set<String> javaFiles = _searchFiles(sourcesFolder.toPath(), ".java");

		Assert.assertFalse(javaFiles.isEmpty());

		for (String classFile : classFiles) {
			if (!classFile.contains("$") &&
				!_ignoreClasses.contains(classFile)) {

				Assert.assertTrue(javaFiles.contains(classFile));
			}
		}

		Set<String> tldClasses = _findTldClasses(classesPath);

		Assert.assertFalse(tldClasses.isEmpty());

		List<String> missingTldClasses = new ArrayList<>();

		for (String tldClass : tldClasses) {
			if (!classFiles.contains(tldClass)) {
				missingTldClasses.add(tldClass);
			}
		}

		Assert.assertTrue(
			"Missing tldClasses: " + missingTldClasses.toString(),
			missingTldClasses.isEmpty());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private Set<String> _findTldClasses(Path sourcePath) throws Exception {
		Set<String> tldClasses = new HashSet<>();

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".tld")) {
						try (FileInputStream fileInputStream =
								new FileInputStream(path.toFile())) {

							DocumentBuilderFactory builderFactory =
								DocumentBuilderFactory.newInstance();

							DocumentBuilder builder =
								builderFactory.newDocumentBuilder();

							Document xmlDocument = builder.parse(
								fileInputStream);

							XPathFactory xPathFactoryInstance =
								XPathFactory.newInstance();

							XPath xPath = xPathFactoryInstance.newXPath();

							XPathExpression xPathExpression = xPath.compile(
								"/taglib/tag/tag-class");

							NodeList nodeList =
								(NodeList)xPathExpression.evaluate(
									xmlDocument, XPathConstants.NODESET);

							for (int i = nodeList.getLength() - 1; i >= 0;
								 i--) {

								Node child = nodeList.item(i);

								String tagClassContent = child.getTextContent();

								if (tagClassContent.startsWith(
										"com.liferay.")) {

									tldClasses.add(
										tagClassContent.replace('.', '/'));
								}
							}
						}
						catch (Exception exception) {
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return tldClasses;
	}

	private Set<String> _searchFiles(Path sourcePath, String filterName)
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

					if (fileName.endsWith(filterName)) {
						URI folderURI = sourcePath.toUri();

						URI relativeURI = folderURI.relativize(path.toUri());

						String relativePath = relativeURI.getPath();

						results.add(relativePath.replace(filterName, ""));
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

	private static final List<String> _ignoreClasses = Arrays.asList(
		"com/fasterxml/jackson/databind/deser/std/BaseNodeDeserializer",
		"org/osgi/service/event/TopicPermissionCollection",
		"org/osgi/framework/AdaptPermissionCollection",
		"org/osgi/framework/PackagePermissionCollection",
		"org/osgi/service/cm/ConfigurationPermissionCollection",
		"javax/servlet/http/NoBodyOutputStream",
		"org/osgi/framework/CapabilityPermissionCollection",
		"javax/servlet/http/NoBodyResponse",
		"org/osgi/framework/ServicePermissionCollection",
		"org/osgi/framework/BundlePermissionCollection",
		"org/osgi/framework/AdminPermissionCollection",
		"org/osgi/service/condpermadmin/BooleanCondition");

}