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

package com.liferay.portal.modules;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Andrea Di Giorgi
 */
public class ModulesStructureTest {

	@BeforeClass
	public static void setUpClass() {
		_modulesDirPath = Paths.get("modules");
	}

	@Test
	public void testScanBuildScripts() throws IOException {
		ClassLoader classLoader = ModulesStructureTest.class.getClassLoader();

		final String gitRepoBuildGradleTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/" +
				"git_repo_build_gradle.tmpl");
		final String gitRepoGradlePropertiesTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/" +
				"git_repo_gradle_properties.tmpl");
		final String gitRepoSettingsGradleTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/" +
				"git_repo_settings_gradle.tmpl");

		Files.walkFileTree(
			_modulesDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (dirPath.equals(_modulesDirPath)) {
						return FileVisitResult.CONTINUE;
					}

					Path dirNamePath = dirPath.getFileName();

					String dirName = dirNamePath.toString();

					if (dirName.charAt(0) == '.') {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path buildGradlePath = dirPath.resolve("build.gradle");
					Path buildXMLPath = dirPath.resolve("build.xml");

					if (Files.exists(dirPath.resolve(".gitrepo"))) {
						_testGitRepoBuildScripts(
							dirPath, gitRepoBuildGradleTemplate,
							gitRepoGradlePropertiesTemplate,
							gitRepoSettingsGradleTemplate);
					}
					else if (Files.exists(dirPath.resolve("app.bnd"))) {
						_testAppBuildScripts(dirPath);
					}
					else if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						if (Files.notExists(buildGradlePath)) {
							Assert.fail("Missing " + buildGradlePath);
						}

						if (Files.exists(buildXMLPath)) {
							Assert.fail("Forbidden " + buildXMLPath);
						}

						Path ivyXmlPath = dirPath.resolve("ivy.xml");

						if (Files.exists(ivyXmlPath)) {
							Assert.fail("Forbidden " + ivyXmlPath);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}
					else if (Files.exists(buildXMLPath)) {
						if (Files.exists(buildGradlePath)) {
							Assert.fail("Forbidden " + buildGradlePath);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}
					else if (Files.exists(dirPath.resolve("package.json"))) {
						_testThemeBuildScripts(dirPath);

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testScanLog4JConfigurationXML() throws IOException {
		final Map<String, String> renameMap = new HashMap<>();

		renameMap.put("src/META-INF/portal-log4j.xml", "module-log4j.xml");
		renameMap.put(
			"src/META-INF/portal-log4j-ext.xml", "module-log4j-ext.xml");
		renameMap.put(
			"src/main/resources/META-INF/portal-log4j.xml", "module-log4j.xml");
		renameMap.put(
			"src/main/resources/META-INF/portal-log4j-ext.xml",
			"module-log4j-ext.xml");

		Files.walkFileTree(
			Paths.get("modules"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path dirPath, BasicFileAttributes basicFileAttributes) {

					if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						for (Entry<String, String> entry :
								renameMap.entrySet()) {

							Path path = dirPath.resolve(entry.getKey());

							if (Files.exists(path)) {
								Assert.fail(
									"Please rename " + path + " to " +
										path.resolveSibling(entry.getValue()));
							}
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _addGradlePluginNames(
			Set<String> pluginNames, String pluginNamePrefix,
			Path buildGradlePath, String pluginIdPrefix,
			String[] pluginIdSuffixes)
		throws IOException {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new FileReader(buildGradlePath.toFile()))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = StringUtil.trim(line);

				for (String pluginIdSuffix : pluginIdSuffixes) {
					String pluginLine =
						"apply plugin: \"" + pluginIdPrefix + pluginIdSuffix +
							"\"";

					if (line.equals(pluginLine)) {
						pluginNames.add(pluginNamePrefix + pluginIdSuffix);
					}
				}
			}
		}
	}

	private boolean _contains(Path path, String s) throws IOException {
		try (FileReader fileReader = new FileReader(path.toFile());
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.contains(s)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean _containsFile(Path dirPath, String pattern)
		throws IOException {

		FileSystem fileSystem = dirPath.getFileSystem();

		final PathMatcher pathMatcher = fileSystem.getPathMatcher(
			"glob:" + pattern);

		final AtomicBoolean found = new AtomicBoolean();

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					if (pathMatcher.matches(path)) {
						found.set(true);

						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return found.get();
	}

	private String _getGitRepoBuildGradle(
			Path dirPath, String buildGradleTemplate)
		throws IOException {

		if (Files.notExists(dirPath.resolve("app.bnd"))) {
			buildGradleTemplate = StringUtil.removeSubstring(
				buildGradleTemplate,
				_APP_BUILD_GRADLE + StringPool.NEW_LINE + StringPool.NEW_LINE);
		}

		final Set<String> pluginNames = new TreeSet<>();

		pluginNames.add("com.liferay.gradle.plugins.defaults");

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path buildGradlePath = dirPath.resolve("build.gradle");

					if (Files.exists(buildGradlePath) &&
						Files.notExists(dirPath.resolve(".gitrepo"))) {

						_addGradlePluginNames(
							pluginNames, "com.liferay.gradle.plugins.",
							buildGradlePath, "com.liferay.",
							new String[] {
								"lang.merger", "maven.plugin.builder"
							});

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		StringBundler sb = new StringBundler(pluginNames.size() * 5 - 1);

		int i = 0;

		for (String pluginName : pluginNames) {
			if (i > 0) {
				sb.append(CharPool.NEW_LINE);
			}

			sb.append("\t\t");
			sb.append("classpath group: \"com.liferay\", name: \"");
			sb.append(pluginName);
			sb.append("\", version: \"latest.release\"");

			i++;
		}

		return StringUtil.replace(
			buildGradleTemplate, "[$BUILDSCRIPT_DEPENDENCIES$]", sb.toString());
	}

	private String _getGitRepoGradleProperties(
		Path dirPath, String gradlePropertiesTemplate) {

		Path relativePath = _modulesDirPath.relativize(dirPath);

		String projectPathPrefix = relativePath.toString();

		projectPathPrefix =
			":" +
				StringUtil.replace(
					projectPathPrefix, File.separatorChar, CharPool.COLON);

		return gradlePropertiesTemplate.replace(
			"[$PROJECT_PATH_PREFIX$]", projectPathPrefix);
	}

	private String _read(Path path) throws IOException {
		String s = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, System.lineSeparator(), StringPool.NEW_LINE);
	}

	private void _testAppBuildScripts(Path dirPath) throws IOException {
		Path buildGradlePath = dirPath.resolve("build.gradle");

		Assert.assertTrue(
			"Missing " + buildGradlePath, Files.exists(buildGradlePath));

		String buildGradle = _read(buildGradlePath);

		Assert.assertEquals(
			"Incorrect " + buildGradlePath, _APP_BUILD_GRADLE, buildGradle);
	}

	private void _testGitRepoBuildScripts(
			Path dirPath, String buildGradleTemplate,
			String gradlePropertiesTemplate, String settingsGradleTemplate)
		throws IOException {

		Path buildGradlePath = dirPath.resolve("build.gradle");
		Path gradlePropertiesPath = dirPath.resolve("gradle.properties");
		Path settingsGradlePath = dirPath.resolve("settings.gradle");

		boolean buildGradleExists = Files.exists(buildGradlePath);
		boolean gradlePropertiesExists = Files.exists(gradlePropertiesPath);
		boolean settingsGradleExists = Files.exists(settingsGradlePath);

		if (!buildGradleExists) {
			Assert.fail("Missing " + buildGradlePath);
		}

		String buildGradle = _read(buildGradlePath);

		Assert.assertEquals(
			"Incorrect " + buildGradlePath,
			_getGitRepoBuildGradle(dirPath, buildGradleTemplate), buildGradle);

		if (!gradlePropertiesExists) {
			Assert.fail("Missing " + gradlePropertiesExists);
		}

		String gradleProperties = _read(gradlePropertiesPath);

		Assert.assertEquals(
			"Incorrect " + gradlePropertiesPath,
			_getGitRepoGradleProperties(dirPath, gradlePropertiesTemplate),
			gradleProperties);

		if (!settingsGradleExists) {
			Assert.fail("Missing " + settingsGradlePath);
		}

		String settingsGradle = _read(settingsGradlePath);

		Assert.assertEquals(
			"Incorrect " + settingsGradlePath, settingsGradleTemplate,
			settingsGradle);

		// LPS-67772

		Path gitAttributesPath = dirPath.resolve(".gitattributes");

		boolean gitAttributesExists = Files.exists(gitAttributesPath);

		if (_containsFile(dirPath, "**/src/main/resources/**/*.soy")) {
			Assert.assertTrue(
				"Missing " + gitAttributesPath, gitAttributesExists);
			Assert.assertEquals("*.soy\ttext eol=lf", _read(gitAttributesPath));
		}
		else {
			Assert.assertFalse(
				"Forbidden " + gitAttributesPath, gitAttributesExists);
		}
	}

	private void _testThemeBuildScripts(Path dirPath) throws IOException {
		if (!_contains(
				dirPath.resolve("package.json"), "\"liferay-theme-tasks\":")) {

			return;
		}

		Path gulpfileJsPath = dirPath.resolve("gulpfile.js");

		if (Files.notExists(gulpfileJsPath)) {
			Assert.fail("Missing " + gulpfileJsPath);
		}
	}

	private static final String _APP_BUILD_GRADLE =
		"apply plugin: \"com.liferay.app.defaults.plugin\"";

	private static Path _modulesDirPath;

}