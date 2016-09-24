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
import com.liferay.portal.kernel.util.StringUtil;

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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Matthew Tambara
 * @author Shuyang Zhou
 * @author Andrea Di Giorgi
 */
public class LibraryReferenceTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_portalPath = Paths.get(System.getProperty("user.dir"));

		_initLibJars();
		_initModuleSourceDirs();

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		_initEclipse(documentBuilder);
		_initNetBeans(documentBuilder);
		_initVersionsJars(documentBuilder);
	}

	@Test
	public void testEclipseJarsInLib() {
		testNonexistentJarReferences(_eclipseJars, _ECLIPSE_FILE_NAME);
	}

	@Test
	public void testEclipseSourceDirsInModules() {
		testNonexistentModuleSourceDirReferences(
			_eclipseModuleSourceDirs, _ECLIPSE_FILE_NAME);
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
	public void testModulesSourceDirsInEclipse() {
		testMissingModuleSourceDirReferences(
			_eclipseModuleSourceDirs, _ECLIPSE_FILE_NAME);
	}

	@Test
	public void testModulesSourceDirsInNetBeans() {
		testMissingModuleSourceDirReferences(
			_netBeansModuleSourceDirs, _NETBEANS_FILE_NAME);
	}

	@Test
	public void testNetBeansJarsInLib() {
		testNonexistentJarReferences(_netBeansJars, _NETBEANS_FILE_NAME);
	}

	@Test
	public void testNetBeansSourceDirsInModules() {
		testNonexistentModuleSourceDirReferences(
			_netBeansModuleSourceDirs, _NETBEANS_FILE_NAME);
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

	protected void testMissingModuleSourceDirReferences(
		Set<String> dirs, String fileName) {

		for (String dir : _moduleSourceDirs) {
			Assert.assertTrue(
				fileName + " is missing a reference to " + dir,
				dirs.contains(dir));
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

	protected void testNonexistentModuleSourceDirReferences(
		Set<String> dirs, String fileName) {

		for (String dir : dirs) {
			Assert.assertTrue(
				fileName + " has a nonexistent reference to " + dir,
				_moduleSourceDirs.contains(dir));
		}
	}

	private static void _initEclipse(DocumentBuilder documentBuilder)
		throws Exception {

		Document document = documentBuilder.parse(new File(_ECLIPSE_FILE_NAME));

		NodeList nodelist = document.getElementsByTagName("classpathentry");

		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);

			NamedNodeMap namedNodeMap = node.getAttributes();

			Node kindNode = namedNodeMap.getNamedItem("kind");
			Node pathNode = namedNodeMap.getNamedItem("path");

			String kind = kindNode.getNodeValue();
			String path = pathNode.getNodeValue();

			if (kind.equals(_LIB_DIR_NAME)) {
				_eclipseJars.add(path);
			}
			else if (kind.equals("src")) {
				if (path.startsWith(_MODULES_DIR_NAME + CharPool.SLASH)) {
					_eclipseModuleSourceDirs.add(path);
				}
			}
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

	private static void _initModuleSourceDirs() throws IOException {
		Files.walkFileTree(
			_portalPath.resolve(_MODULES_DIR_NAME),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (Files.exists(dirPath.resolve(".lfrbuild-portal-pre"))) {
						Path sourceDirPath = dirPath.resolve(
							_SRC_JAVA_DIR_NAME);

						Path relativePath = _portalPath.relativize(
							sourceDirPath);

						String sourceDir = relativePath.toString();

						if (File.separatorChar != CharPool.SLASH) {
							sourceDir = StringUtil.replace(
								sourceDir, File.separatorChar, CharPool.SLASH);
						}

						_moduleSourceDirs.add(sourceDir);

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _initNetBeans(DocumentBuilder documentBuilder)
		throws Exception {

		Document document = documentBuilder.parse(
			new File(_NETBEANS_FILE_NAME));

		NodeList nodelist = document.getElementsByTagName("classpath");

		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);

			_netBeansJars.add(node.getTextContent());
		}

		nodelist = document.getElementsByTagName("source-folder");

		for (int i = 0; i < nodelist.getLength(); i++) {
			Element element = (Element)nodelist.item(i);

			NodeList locationNodeList = element.getElementsByTagName(
				"location");

			Node locationNode = locationNodeList.item(0);

			String location = locationNode.getTextContent();

			if (location.startsWith(_MODULES_DIR_NAME + CharPool.SLASH) &&
				location.endsWith(CharPool.SLASH + _SRC_JAVA_DIR_NAME)) {

				_netBeansModuleSourceDirs.add(location);
			}
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

	private static final String _MODULES_DIR_NAME = "modules";

	private static final String _NETBEANS_FILE_NAME = "nbproject/project.xml";

	private static final String _SRC_JAVA_DIR_NAME = "src/main/java";

	private static final String _VERSIONS_FILE_NAME =
		_LIB_DIR_NAME + "/versions.xml";

	private static final Set<String> _eclipseJars = new HashSet<>();
	private static final Set<String> _eclipseModuleSourceDirs = new HashSet<>();
	private static final Set<String> _excludeJars = new HashSet<>();
	private static final Set<String> _libJars = new HashSet<>();
	private static final Set<String> _moduleSourceDirs = new HashSet<>();
	private static final Set<String> _netBeansJars = new HashSet<>();
	private static final Set<String> _netBeansModuleSourceDirs =
		new HashSet<>();
	private static Path _portalPath;
	private static final Set<String> _versionsJars = new HashSet<>();

}