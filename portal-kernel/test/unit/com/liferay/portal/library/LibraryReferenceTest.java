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

package com.liferay.portal.library;

import com.liferay.portal.kernel.util.CharPool;

import java.io.File;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 */
public class LibraryReferenceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_initLibJars();

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		_initEclipseJars(documentBuilder);
		_initNetBeansJars(documentBuilder);
		_initVersionsJars(documentBuilder);
	}

	@Test
	public void testEclipseJarsInLib() {
		testNonexistentJarReferences(_eclipseJars, _ECLIPSE_FILE_NAME);
	}

	@Test
	public void testLibJarsInEclipse() {
		testMissingJarReferences(_eclipseJars, _ECLIPSE_FILE_NAME);
	}

	@Test
	public void testLibJarsInNetBeans() {
		testMissingJarReferences(_netBeansJars, _NETBEANS_FILE_NAME);
	}

	@Test
	public void testLibJarsInVersions() {
		testMissingJarReferences(_versionsJars, _VERSIONS_FILE_NAME);
	}

	@Test
	public void testNetBeansJarsInLib() {
		testNonexistentJarReferences(_netBeansJars, _NETBEANS_FILE_NAME);
	}

	@Test
	public void testVersionsJarsInLib() {
		testNonexistentJarReferences(_versionsJars, _VERSIONS_FILE_NAME);
	}

	protected void testMissingJarReferences(Set<String> jars, String fileName) {
		for (String jar : _libJars) {
			if (fileName.equals(_VERSIONS_FILE_NAME) &&
				_excludeJars.contains(jar)) {

				continue;
			}

			Assert.assertTrue(
				fileName + " is missing a reference to " + jar,
				jars.contains(jar));
		}
	}

	protected void testNonexistentJarReferences(
		Set<String> jars, String fileName) {

		for (String jar : jars) {
			Assert.assertTrue(
				fileName + " has a nonexistent reference to " + jar,
				_libJars.contains(jar));
		}
	}

	private static void _initEclipseJars(DocumentBuilder documentBuilder)
		throws Exception {

		Document document = documentBuilder.parse(new File(_ECLIPSE_FILE_NAME));

		NodeList nodelist = document.getElementsByTagName("classpathentry");

		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);

			NamedNodeMap namedNodeMap = node.getAttributes();

			Node kindNode = namedNodeMap.getNamedItem("kind");

			String value = kindNode.getNodeValue();

			if (!value.equals(_LIB_DIR_NAME)) {
				continue;
			}

			Node pathNode = namedNodeMap.getNamedItem("path");

			_eclipseJars.add(pathNode.getNodeValue());
		}
	}

	private static void _initLibJars() throws IOException {
		for (String line :
				Files.readAllLines(
					Paths.get(_LIB_DIR_NAME, "/versions-ignore.txt"),
					Charset.forName("UTF-8"))) {

			line = line.trim();

			if (!line.isEmpty()) {
				_excludeJars.add(line);
			}
		}

		Files.walkFileTree(
			Paths.get(_LIB_DIR_NAME),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					String pathString = path.toString();

					if (pathString.endsWith(".jar")) {
						if (File.separatorChar != CharPool.SLASH) {
							pathString = pathString.replace(
								File.separatorChar, CharPool.SLASH);
						}

						_libJars.add(pathString);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _initNetBeansJars(DocumentBuilder documentBuilder)
		throws Exception {

		Document document = documentBuilder.parse(
			new File(_NETBEANS_FILE_NAME));

		NodeList nodelist = document.getElementsByTagName("classpath");

		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);

			_netBeansJars.add(node.getTextContent());
		}
	}

	private static void _initVersionsJars(DocumentBuilder documentBuilder)
		throws Exception {

		Document document = documentBuilder.parse(
			new File(_VERSIONS_FILE_NAME));

		NodeList nodelist = document.getElementsByTagName("file-name");

		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);

			_versionsJars.add(node.getTextContent());
		}
	}

	private static final String _ECLIPSE_FILE_NAME = ".classpath";

	private static final String _LIB_DIR_NAME = "lib";

	private static final String _NETBEANS_FILE_NAME = "nbproject/project.xml";

	private static final String _VERSIONS_FILE_NAME =
		_LIB_DIR_NAME + "/versions.xml";

	private static final Set<String> _eclipseJars = new HashSet<>();
	private static final Set<String> _excludeJars = new HashSet<>();
	private static final Set<String> _libJars = new HashSet<>();
	private static final Set<String> _netBeansJars = new HashSet<>();
	private static final Set<String> _versionsJars = new HashSet<>();

}