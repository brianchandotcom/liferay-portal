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
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.internal.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.StringTestUtil;
import com.liferay.project.templates.util.XMLTestUtil;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class ProjectTemplateFilesTest {

	@Test
	public void testProjectTemplateFiles() throws Exception {
		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				_testProjectTemplateFiles(path, documentBuilder);
			}
		}
	}

	private static String _readXmlDeclarationTemplate(String name)
		throws IOException {

		String template = FileTestUtil.read(
			"com/liferay/project/templates/dependencies/" + name);

		int pos = template.indexOf("[$XML$]");

		return template.substring(0, pos);
	}

	private List<BuildGradleDependency> _getBuildGradleDependencies(
			Path buildGradlePath)
		throws IOException {

		List<BuildGradleDependency> buildGradleDependencies = new ArrayList<>();

		String buildGradle = new String(
			Files.readAllBytes(buildGradlePath), StandardCharsets.UTF_8);

		Matcher matcher = _buildGradleDependencyPattern.matcher(buildGradle);

		while (matcher.find()) {
			String configuration = matcher.group(1);
			String dependencyGroup = matcher.group(2);
			String dependencyName = matcher.group(3);
			String dependencyVersion = matcher.group(4);

			boolean provided = false;

			if (configuration.equals("compileOnly")) {
				provided = true;
			}

			buildGradleDependencies.add(
				new BuildGradleDependency(
					dependencyGroup, dependencyName, dependencyVersion,
					provided));
		}

		return buildGradleDependencies;
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

	private void _testArchetypeMetadataXml(
			Path projectTemplateDirPath, String projectTemplateDirName,
			boolean hasJavaFiles)
		throws IOException {

		Path archetypeMetadataXmlPath = projectTemplateDirPath.resolve(
			"src/main/resources/META-INF/maven/archetype-metadata.xml");

		Assert.assertTrue(
			"Missing " + archetypeMetadataXmlPath,
			Files.exists(archetypeMetadataXmlPath));

		String archetypeDescriptorName = projectTemplateDirName.substring(
			FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX.length());

		if (archetypeDescriptorName.equals(WorkspaceUtil.WORKSPACE)) {
			archetypeDescriptorName = "liferay-" + archetypeDescriptorName;
		}
		else {
			archetypeDescriptorName =
				"liferay-module-" + archetypeDescriptorName;
		}

		String archetypeMetadataXml = FileUtil.read(archetypeMetadataXmlPath);

		Assert.assertTrue(
			"Incorrect archetype descriptor name in " +
				archetypeMetadataXmlPath,
			archetypeMetadataXml.startsWith(
				"<?xml version=\"1.0\"?>\n\n<archetype-descriptor name=\"" +
					archetypeDescriptorName + "\">"));

		boolean hasArchetypeMetadataAuthorProperty =
			archetypeMetadataXml.contains("<requiredProperty key=\"author\">");

		if (hasJavaFiles) {
			Assert.assertTrue(
				"Missing \"author\" required property in " +
					archetypeMetadataXmlPath,
				hasArchetypeMetadataAuthorProperty);
		}
		else {
			Assert.assertFalse(
				"Forbidden \"author\" required property in " +
					archetypeMetadataXmlPath,
				hasArchetypeMetadataAuthorProperty);
		}
	}

	private void _testBndBnd(Path projectTemplateDirPath) throws IOException {
		Path bndBndPath = projectTemplateDirPath.resolve("bnd.bnd");

		Properties properties = FileUtil.readProperties(bndBndPath);

		String bundleDescription = properties.getProperty("Bundle-Description");

		Assert.assertTrue(
			"Missing 'Bundle-Description' header in " + bndBndPath,
			Validator.isNotNull(bundleDescription));

		Matcher matcher = _bundleDescriptionPattern.matcher(bundleDescription);

		Assert.assertTrue(
			"Header \"Bundle-Description\" in " + bndBndPath +
				" must match pattern \"" + _bundleDescriptionPattern.pattern() +
					"\"",
			matcher.matches());
	}

	private void _testBuildGradle(Path archetypeResourcesDirPath) {
		Path buildGradlePath = archetypeResourcesDirPath.resolve(
			"build.gradle");

		Assert.assertTrue(
			"Missing " + buildGradlePath, Files.exists(buildGradlePath));
	}

	private void _testGitIgnore(
			String projectTemplateDirName, Path archetypeResourcesDirPath)
		throws IOException {

		Path dotGitIgnorePath = archetypeResourcesDirPath.resolve(".gitignore");
		Path gitIgnorePath = archetypeResourcesDirPath.resolve("gitignore");

		Assert.assertFalse(
			"Rename " + dotGitIgnorePath + " to " + gitIgnorePath +
				" to bypass GRADLE-1883",
			Files.exists(dotGitIgnorePath));

		Assert.assertTrue(
			"Missing " + gitIgnorePath, Files.exists(gitIgnorePath));

		if (!projectTemplateDirName.equals(
				FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX +
					WorkspaceUtil.WORKSPACE)) {

			String gitIgnore = _GIT_IGNORE;

			if (Files.exists(
					archetypeResourcesDirPath.resolve("package.json"))) {

				gitIgnore = _GIT_IGNORE_WITH_PACKAGE_JSON;
			}

			Assert.assertEquals(
				"Incorrect " + gitIgnorePath, gitIgnore,
				FileUtil.read(gitIgnorePath));
		}
	}

	private void _testGradleWrapper(Path archetypeResourcesDirPath) {
		Assert.assertFalse(
			"Forbidden Gradle Wrapper in " + archetypeResourcesDirPath,
			Files.exists(archetypeResourcesDirPath.resolve("gradlew")));
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

	private void _testMavenWrapper(Path archetypeResourcesDirPath) {
		Assert.assertFalse(
			"Forbidden Maven Wrapper in " + archetypeResourcesDirPath,
			Files.exists(archetypeResourcesDirPath.resolve("mvnw")));
	}

	private void _testPomXml(
			Path archetypeResourcesDirPath, DocumentBuilder documentBuilder)
		throws Exception {

		Path pomXmlPath = archetypeResourcesDirPath.resolve("pom.xml");

		Assert.assertTrue("Missing " + pomXmlPath, Files.exists(pomXmlPath));

		Document document = documentBuilder.parse(pomXmlPath.toFile());

		Element projectElement = document.getDocumentElement();

		Element packagingElement = XMLTestUtil.getChildElement(
			projectElement, "packaging");

		if (packagingElement != null) {
			Assert.assertNotEquals(
				"Incorrect packaging in " + pomXmlPath, "jar",
				packagingElement.getTextContent());
		}

		Element propertiesElement = XMLTestUtil.getChildElement(
			projectElement, "properties");

		Assert.assertNotNull(
			"Missing \"properties\" element in " + pomXmlPath,
			propertiesElement);

		String sourceEncoding = null;

		Element sourceEncodingElement = XMLTestUtil.getChildElement(
			propertiesElement, "project.build.sourceEncoding");

		if (sourceEncodingElement != null) {
			sourceEncoding = sourceEncodingElement.getTextContent();
		}

		Assert.assertEquals(
			"Incorrect property \"project.build.sourceEncoding\" in " +
				pomXmlPath,
			sourceEncoding, StandardCharsets.UTF_8.name());

		NodeList executionNodeList = projectElement.getElementsByTagName(
			"execution");

		for (int i = 0; i < executionNodeList.getLength(); i++) {
			Element executionElement = (Element)executionNodeList.item(i);

			Element idElement = XMLTestUtil.getChildElement(
				executionElement, "id");

			if (idElement != null) {
				String id = idElement.getTextContent();

				Assert.assertFalse(
					"Execution ID \"" + id + "\" in " + pomXmlPath +
						" cannot start with \"default-\"",
					id.startsWith("default-"));

				Matcher matcher = _pomXmlExecutionIdPattern.matcher(id);

				Assert.assertTrue(
					"Execution ID \"" + id + "\" in " + pomXmlPath +
						" must match pattern \"" +
							_pomXmlExecutionIdPattern.pattern() + "\"",
					matcher.matches());
			}
		}

		Path buildGradlePath = archetypeResourcesDirPath.resolve(
			"build.gradle");

		List<BuildGradleDependency> buildGradleDependencies =
			_getBuildGradleDependencies(buildGradlePath);

		Element dependenciesElement = XMLTestUtil.getChildElement(
			projectElement, "dependencies");

		List<Element> dependencyElements;

		if (dependenciesElement != null) {
			dependencyElements = XMLTestUtil.getChildElements(
				dependenciesElement);
		}
		else {
			dependencyElements = Collections.emptyList();
		}

		Assert.assertEquals(
			"Number of dependencies in " + pomXmlPath + " must match " +
				buildGradlePath,
			buildGradleDependencies.size(), dependencyElements.size());

		for (int i = 0; i < buildGradleDependencies.size(); i++) {
			BuildGradleDependency buildGradleDependency =
				buildGradleDependencies.get(i);
			Element dependencyElement = dependencyElements.get(i);

			List<Element> dependencyChildElements =
				XMLTestUtil.getChildElements(dependencyElement);

			String dependencyElementString = XMLTestUtil.toString(
				dependencyElement);

			XMLTestUtil.testXmlElement(
				pomXmlPath, dependencyElementString, dependencyChildElements, 0,
				"groupId", buildGradleDependency.group);
			XMLTestUtil.testXmlElement(
				pomXmlPath, dependencyElementString, dependencyChildElements, 1,
				"artifactId", buildGradleDependency.name);
			XMLTestUtil.testXmlElement(
				pomXmlPath, dependencyElementString, dependencyChildElements, 2,
				"version", buildGradleDependency.version);

			if (buildGradleDependency.provided) {
				XMLTestUtil.testXmlElement(
					pomXmlPath, dependencyElementString,
					dependencyChildElements, 3, "scope", "provided");
			}
			else {
				Assert.assertEquals(
					"Incorrect number of child nodes of " +
						dependencyElementString + " in " + pomXmlPath,
					dependencyChildElements.size(), 3);
			}
		}

		_testPomXmlVersions(pomXmlPath, projectElement, "dependency");
		_testPomXmlVersions(pomXmlPath, projectElement, "plugin");
	}

	private void _testPomXmlVersions(
		Path pomXmlPath, Element projectElement, String name) {

		Properties systemProperties = System.getProperties();

		NodeList nodeList = projectElement.getElementsByTagName(name);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element)nodeList.item(i);

			Element artifactIdElement = XMLTestUtil.getChildElement(
				element, "artifactId");

			String artifactId = artifactIdElement.getTextContent();

			String key = artifactId + ".version";

			if (systemProperties.containsKey(key)) {
				Element versionElement = XMLTestUtil.getChildElement(
					element, "version");

				Assert.assertEquals(
					"Incorrect version of " + name + " \"" + artifactId +
						"\" in " + pomXmlPath,
					"@" + key + "@", versionElement.getTextContent());
			}
		}
	}

	private void _testProjectTemplateFiles(
			Path projectTemplateDirPath, DocumentBuilder documentBuilder)
		throws Exception {

		Path archetypeResourcesDirPath = projectTemplateDirPath.resolve(
			"src/main/resources/archetype-resources");

		Assert.assertTrue(
			"Missing " + archetypeResourcesDirPath,
			Files.isDirectory(archetypeResourcesDirPath));

		String projectTemplateDirName = String.valueOf(
			projectTemplateDirPath.getFileName());

		_testBndBnd(projectTemplateDirPath);
		_testBuildGradle(archetypeResourcesDirPath);
		_testGitIgnore(projectTemplateDirName, archetypeResourcesDirPath);
		_testGradleWrapper(archetypeResourcesDirPath);
		_testMavenWrapper(archetypeResourcesDirPath);
		_testPomXml(archetypeResourcesDirPath, documentBuilder);

		final AtomicBoolean hasJavaFiles = new AtomicBoolean();

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

					String fileName = String.valueOf(path.getFileName());

					String extension = FileTestUtil.getExtension(fileName);

					boolean javaFile = extension.equals("java");

					if (javaFile) {
						hasJavaFiles.set(true);
					}

					if (!fileName.equals(".gitkeep") &&
						(_isInJavaSrcDir(path) != javaFile)) {

						Assert.fail("Wrong source directory " + path);
					}

					if (_isTextFile(fileName, extension)) {
						_testTextFile(path, fileName, extension);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		_testArchetypeMetadataXml(
			projectTemplateDirPath, projectTemplateDirName, hasJavaFiles.get());
	}

	private void _testTextFile(Path path, String fileName, String extension)
		throws IOException {

		String text = FileUtil.read(path);

		boolean trailingEmptyLine = false;

		if ((text.length() > 0) && (text.charAt(text.length() - 1) == '\n')) {
			trailingEmptyLine = true;
		}

		Assert.assertFalse("Trailing empty line in " + path, trailingEmptyLine);

		try (BufferedReader bufferedReader = new BufferedReader(
				new StringReader(text))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				Assert.assertFalse(
					"Forbidden whitespace trailing character in " + path,
					!line.isEmpty() &&
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

		if (extension.equals("java")) {
			Assert.assertTrue(
				"Missing @author tag in " + path,
				text.contains("* @author ${author}"));
		}

		if (extension.equals("xml") &&
			!fileName.equals("liferay-layout-templates.xml") &&
			Validator.isNotNull(text)) {

			String xmlDeclaration = _XML_DECLARATION;

			if (fileName.equals("pom.xml")) {
				xmlDeclaration = _POM_XML_DECLARATION;
			}
			else if (fileName.equals("service.xml")) {
				xmlDeclaration = _SERVICE_XML_DECLARATION;
			}

			Assert.assertTrue(
				"Incorrect XML declaration in " + path,
				text.startsWith(xmlDeclaration));
		}
	}

	private static final String _GIT_IGNORE;

	private static final String _GIT_IGNORE_WITH_PACKAGE_JSON;

	private static final String _POM_XML_DECLARATION;

	private static final String _SERVICE_XML_DECLARATION;

	private static final String[] _SOURCESET_NAMES = {
		"main", "test", "testIntegration"
	};

	private static final String _XML_DECLARATION;

	private static final Pattern _buildGradleDependencyPattern =
		Pattern.compile(
			"(compile(?:Only)?) group: \"(.+)\", name: \"(.+)\", " +
				"(?:transitive: (?:true|false), )?version: \"(.+)\"");
	private static final Pattern _bundleDescriptionPattern = Pattern.compile(
		"Creates a .+\\.");
	private static final Pattern _pomXmlExecutionIdPattern = Pattern.compile(
		"[a-z]+(?:-[a-z]+)*");
	private static final Set<String> _textFileExtensions = new HashSet<>(
		Arrays.asList(
			"bnd", "gradle", "java", "jsp", "jspf", "properties", "xml"));
	private static final Pattern _velocityIfPattern = Pattern.compile(
		"#if\\s*\\(\\s*(.+)\\s*\\)");

	static {
		Set<String> gitIgnoreLines = new TreeSet<>();

		gitIgnoreLines.add(".gradle/");
		gitIgnoreLines.add("build/");
		gitIgnoreLines.add("target/");

		_GIT_IGNORE = StringTestUtil.merge(gitIgnoreLines, '\n');

		gitIgnoreLines.add("node_modules/");

		_GIT_IGNORE_WITH_PACKAGE_JSON = StringTestUtil.merge(
			gitIgnoreLines, '\n');

		try {
			_XML_DECLARATION = _readXmlDeclarationTemplate(
				"xml_declaration.tmpl");
			_POM_XML_DECLARATION = _readXmlDeclarationTemplate(
				"pom_xml_declaration.tmpl");
			_SERVICE_XML_DECLARATION = _readXmlDeclarationTemplate(
				"service_xml_declaration.tmpl");
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private static class BuildGradleDependency {

		public BuildGradleDependency(
			String group, String name, String version, boolean provided) {

			this.group = group;
			this.name = name;
			this.version = version;
			this.provided = provided;
		}

		public final String group;
		public final String name;
		public final boolean provided;
		public final String version;

	}

}