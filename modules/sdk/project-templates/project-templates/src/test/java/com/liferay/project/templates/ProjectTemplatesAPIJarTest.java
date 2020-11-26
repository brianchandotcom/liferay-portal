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

		File portalClassesFolder = temporaryFolder.newFolder(
			"portal-classes-" + releaseApiJarFile.getName());

		ZipUtil.unzip(releaseApiJarFile, portalClassesFolder);

		File portalClassesSourceFolder = temporaryFolder.newFolder(
			"portal-classes-sources-" + releaseApiJarSourcesFile.getName());

		ZipUtil.unzip(releaseApiJarSourcesFile, portalClassesSourceFolder);

		Set<String> classesSets = _extractApiFiles(
			portalClassesFolder.toPath(), ".class");

		Set<String> javaSets = _extractApiFiles(
			portalClassesSourceFolder.toPath(), ".java");

		List<String> ignoreSourceList = Arrays.asList(_IGNORESOURCE);

		for (String classItem : classesSets) {
			if (!classItem.contains("$") &&
				!ignoreSourceList.contains(classItem)) {

				Assert.assertTrue(javaSets.contains(classItem));
			}
		}

		Set<String> tldClasses = _extractTldFi1les(
			portalClassesFolder.toPath());

		for (String tldClass : tldClasses) {
			if (!javaSets.contains(tldClass)) {
				Assert.assertTrue(classesSets.contains(tldClass));
			}
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private Set<String> _extractApiFiles(Path sourcePath, String filterName) {
		Set<String> apiSets = new HashSet<>();

		try {
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

							URI uri = path.toUri();

							URI relativeURI = folderURI.relativize(uri);

							String relativePath = relativeURI.getPath();

							apiSets.add(relativePath.replace(filterName, ""));
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (Exception exception) {
		}

		return apiSets;
	}

	private Set<String> _extractTldFi1les(Path sourcePath) {
		Set<String> tldClassSets = new HashSet<>();

		try {
			Files.walkFileTree(
				sourcePath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						String fileName = String.valueOf(path.getFileName());

						if (fileName.endsWith(".tld")) {
							try (FileInputStream fileIS = new FileInputStream(
									path.toFile())) {

								DocumentBuilderFactory builderFactory =
									DocumentBuilderFactory.newInstance();

								DocumentBuilder builder =
									builderFactory.newDocumentBuilder();

								Document xmlDocument = builder.parse(fileIS);

								XPathFactory xPathFactoryInstance =
									XPathFactory.newInstance();

								XPath xPath = xPathFactoryInstance.newXPath();

								String expression = "/taglib/tag/tag-class";

								XPathExpression xPathExpression = xPath.compile(
									expression);

								NodeList nodeList =
									(NodeList)xPathExpression.evaluate(
										xmlDocument, XPathConstants.NODESET);

								for (int n = nodeList.getLength() - 1; n >= 0;
									 n--) {

									Node child = nodeList.item(n);

									String tagClassContent =
										child.getTextContent();

									if (tagClassContent.startsWith(
											"com.liferay.")) {

										tldClassSets.add(
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
		}
		catch (Exception exception) {
		}

		return tldClassSets;
	}

	private static final String[] _IGNORESOURCE = {
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
		"org/osgi/service/condpermadmin/BooleanCondition"
	};

	private static final String _RELEASE_API_JAR_FILE = System.getProperty(
		"releaseApiJarFile");

	private static final String _RELEASE_API_JAR_SOURCES_FILE =
		System.getProperty("releaseApiJarSourcesFile");

}