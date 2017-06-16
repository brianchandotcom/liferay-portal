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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

	private static void _addXmlDeclaration(
			String fileName, String templateFileName)
		throws IOException {

		String template = FileTestUtil.read(
			"com/liferay/project/templates/dependencies/" + templateFileName);

		int pos = template.indexOf("[$XML$]");

		template = template.substring(0, pos);

		_xmlDeclarations.put(fileName, template);
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
			boolean requireAuthorProperty)
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

		boolean authorProperty = archetypeMetadataXml.contains(
			"<requiredProperty key=\"author\">");

		if (requireAuthorProperty) {
			Assert.assertTrue(
				"Missing \"author\" required property in " +
					archetypeMetadataXmlPath,
				authorProperty);
		}
		else {
			Assert.assertFalse(
				"Forbidden \"author\" required property in " +
					archetypeMetadataXmlPath,
				authorProperty);
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

			Set<String> gitIgnoreLines = new TreeSet<>(_gitIgnoreLines);

			if (Files.exists(
					archetypeResourcesDirPath.resolve("package.json"))) {

				gitIgnoreLines.add("node_modules/");
			}

			if (FileTestUtil.containsFile(
					archetypeResourcesDirPath, "*.scss")) {

				gitIgnoreLines.add(".sass-cache/");
			}

			Assert.assertEquals(
				"Incorrect " + gitIgnorePath,
				StringTestUtil.merge(gitIgnoreLines, '\n'),
				FileUtil.read(gitIgnorePath));
		}
	}

	private void _testGradleWrapper(Path archetypeResourcesDirPath) {
		Assert.assertFalse(
			"Forbidden Gradle Wrapper in " + archetypeResourcesDirPath,
			Files.exists(archetypeResourcesDirPath.resolve("gradlew")));
	}

	private void _testLanguageProperties(Path path) throws IOException {
		boolean portlet = false;

		try (BufferedReader bufferedReader = Files.newBufferedReader(
				path, StandardCharsets.UTF_8)) {

			String line = null;
			String previousKey = null;

			while ((line = bufferedReader.readLine()) != null) {
				Assert.assertFalse(
					"Forbidden empty line in " + path, line.isEmpty());
				Assert.assertFalse(
					"Forbidden comments in " + path, line.startsWith("##"));
				Assert.assertFalse(
					"Forbidden whitespace leading character in " + path,
					Character.isWhitespace(line.charAt(0)));

				int pos = line.indexOf('=');

				Assert.assertNotEquals(
					"Incorrect line \"" + line + "\" in " + path, -1, pos);

				String key = line.substring(0, pos);

				Assert.assertTrue(
					path + " contains duplicate lines or is not sorted",
					(previousKey == null) ||
					(_languagePropertiesKeyComparator.compare(
						key, previousKey) > 0));

				if (key.startsWith("javax.portlet.")) {
					portlet = true;
				}

				previousKey = key;
			}
		}

		if (portlet) {
			Properties properties = FileUtil.readProperties(path);

			String keywords = properties.getProperty(
				"javax.portlet.keywords.${artifactId}");

			Assert.assertTrue(
				"Value of \"javax.portlet.keywords.${artifactId}\" in " + path +
					" must start with \"${artifactId},\"",
				(keywords != null) && keywords.startsWith("${artifactId},"));

			String title = properties.getProperty(
				"javax.portlet.title.${artifactId}");

			Assert.assertTrue(
				"Value of \"javax.portlet.title.${artifactId}\" in " + path +
					" must end with \" Portlet\"",
				(title != null) && title.endsWith(" Portlet"));

			String expectedShortTitle = title.substring(0, title.length() - 8);

			Assert.assertEquals(
				"Incorrect value of " +
					"\"javax.portlet.display-name.${artifactId}\" in " + path,
				expectedShortTitle,
				properties.getProperty(
					"javax.portlet.display-name.${artifactId}"));

			Assert.assertEquals(
				"Incorrect value of " +
					"\"javax.portlet.short-title.${artifactId}\" in " + path,
				expectedShortTitle,
				properties.getProperty(
					"javax.portlet.short-title.${artifactId}"));
		}
	}

	private void _testLiferayPluginPackageProperties(Path path)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = Files.newBufferedReader(
				path, StandardCharsets.UTF_8)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				Assert.assertFalse(
					"Forbidden empty line in " + path, line.isEmpty());

				if (line.startsWith("#set")) {
					continue;
				}

				line = line.replace("${symbol_escape}", "\\");
				line = line.replace("${symbol_pound}", "#");

				if (sb.length() > 0) {
					sb.append('\n');
				}

				sb.append(line);
			}
		}

		Properties properties = new Properties();

		properties.load(new StringReader(sb.toString()));

		_testPropertyValue(path, properties, "author", "${author}");
		_testPropertyValue(path, properties, "change-log", "");
		_testPropertyValue(path, properties, "licenses", "LGPL");
		_testPropertyValue(path, properties, "liferay-versions", "7.0.0+");
		_testPropertyValue(path, properties, "long-description", "");
		_testPropertyValue(path, properties, "module-group-id", "liferay");
		_testPropertyValue(path, properties, "module-incremental-version", "1");
		_testPropertyValue(path, properties, "name", "${artifactId}");
		_testPropertyValue(
			path, properties, "page-url", "http://www.liferay.com");
		_testPropertyValue(path, properties, "short-description", "");
		_testPropertyValue(path, properties, "tags", "");
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

		final AtomicBoolean requireAuthorProperty = new AtomicBoolean();

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

					Path liferayPluginPackagePropertiesPath = dirPath.resolve(
						"liferay-plugin-package.properties");

					if (Files.exists(liferayPluginPackagePropertiesPath)) {
						requireAuthorProperty.set(true);

						_testLiferayPluginPackageProperties(
							liferayPluginPackagePropertiesPath);
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
						requireAuthorProperty.set(true);
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
			projectTemplateDirPath, projectTemplateDirName,
			requireAuthorProperty.get());
	}

	private void _testPropertyValue(
		Path path, Properties properties, String key, String expectedValue) {

		Assert.assertEquals(
			"Incorrect value of \"" + key + "\" in " + path, expectedValue,
			properties.getProperty(key));
	}

	private void _testTextFile(Path path, String fileName, String extension)
		throws IOException {

		String text = FileUtil.read(path);

		Assert.assertEquals(
			"Forbidden leading or trailing whitespaces in " + path, text.trim(),
			text);

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

		if (extension.equals("xml") && Validator.isNotNull(text)) {
			String xmlDeclaration = _xmlDeclarations.get(fileName);

			if (xmlDeclaration == null) {
				xmlDeclaration = _xmlDeclarations.get(null);
			}

			Assert.assertTrue(
				"Incorrect XML declaration in " + path,
				text.startsWith(xmlDeclaration));
		}
	}

	private static final String[] _SOURCESET_NAMES =
		{"main", "test", "testIntegration"};

	private static final Pattern _buildGradleDependencyPattern =
		Pattern.compile(
			"(compile(?:Only)?) group: \"(.+)\", name: \"(.+)\", " +
				"(?:transitive: (?:true|false), )?version: \"(.+)\"");
	private static final Pattern _bundleDescriptionPattern = Pattern.compile(
		"Creates a .+\\.");
	private static final List<String> _gitIgnoreLines = Arrays.asList(
		".gradle/", "build/", "target/");

	private static final Comparator<String> _languagePropertiesKeyComparator =
		new Comparator<String>() {

			@Override
			public int compare(String key1, String key2) {
				boolean key1StartsWithLetter = Character.isLetter(
					key1.charAt(0));
				boolean key2StartsWithLetter = Character.isLetter(
					key2.charAt(0));

				if (key1StartsWithLetter && !key2StartsWithLetter) {
					return -1;
				}

				if (!key1StartsWithLetter && key2StartsWithLetter) {
					return 1;
				}

				return key1.compareTo(key2);
			}

		};

	private static final Pattern _pomXmlExecutionIdPattern = Pattern.compile(
		"[a-z]+(?:-[a-z]+)*");
	private static final Set<String> _textFileExtensions = new HashSet<>(
		Arrays.asList(
			"bnd", "gradle", "java", "jsp", "jspf", "properties", "xml"));
	private static final Pattern _velocityIfPattern = Pattern.compile(
		"#if\\s*\\(\\s*(.+)\\s*\\)");
	private static final Map<String, String> _xmlDeclarations = new HashMap<>();

	static {
		try {
			_addXmlDeclaration(null, "xml_declaration.tmpl");
			_addXmlDeclaration(
				"liferay-display.xml", "liferay_display_xml_declaration.tmpl");
			_addXmlDeclaration(
				"liferay-layout-templates.xml",
				"liferay_layout_templates_xml_declaration.tmpl");
			_addXmlDeclaration(
				"liferay-portlet.xml", "liferay_portlet_xml_declaration.tmpl");
			_addXmlDeclaration("pom.xml", "pom_xml_declaration.tmpl");
			_addXmlDeclaration("portlet.xml", "portlet_xml_declaration.tmpl");
			_addXmlDeclaration("service.xml", "service_xml_declaration.tmpl");
			_addXmlDeclaration("web.xml", "web_xml_declaration.tmpl");
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