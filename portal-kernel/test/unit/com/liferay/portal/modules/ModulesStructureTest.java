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
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

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
			"com/liferay/portal/modules/dependencies" +
				"/git_repo_build_gradle.tmpl");
		final String gitRepoGitAttributesTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies" +
				"/git_repo_gitattributes.tmpl");
		final String gitRepoSettingsGradleTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies" +
				"/git_repo_settings_gradle.tmpl");

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

					String dirName = String.valueOf(dirPath.getFileName());

					if (dirName.charAt(0) == '.') {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path buildGradlePath = dirPath.resolve("build.gradle");
					Path buildXMLPath = dirPath.resolve("build.xml");

					if (Files.exists(dirPath.resolve(".gitrepo"))) {
						_testGitRepoBuildScripts(
							dirPath, gitRepoBuildGradleTemplate,
							gitRepoGitAttributesTemplate,
							gitRepoSettingsGradleTemplate);
					}
					else if (Files.exists(dirPath.resolve("app.bnd"))) {
						_testAppBuildScripts(dirPath);
					}
					else if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						Assert.assertTrue(
							"Missing " + buildGradlePath,
							Files.exists(buildGradlePath));
						Assert.assertFalse(
							"Forbidden " + buildXMLPath,
							Files.exists(buildXMLPath));

						Path ivyXmlPath = dirPath.resolve("ivy.xml");

						Assert.assertFalse(
							"Forbidden " + ivyXmlPath,
							Files.exists(ivyXmlPath));

						return FileVisitResult.SKIP_SUBTREE;
					}
					else if (Files.exists(buildXMLPath)) {
						Assert.assertFalse(
							"Forbidden " + buildGradlePath,
							Files.exists(buildGradlePath));

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
	public void testScanIgnoreFiles() throws IOException {
		ClassLoader classLoader = ModulesStructureTest.class.getClassLoader();

		final String gitRepoGitIgnoreTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/git_repo_gitignore.tmpl");
		final String themeGitIgnoreTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/theme_gitignore.tmpl");

		Files.walkFileTree(
			_modulesDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (dirName.equals("gradleTest")) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					if (dirPath.equals(_modulesDirPath) ||
						Files.exists(dirPath.resolve(".gitrepo"))) {

						_testGitRepoIgnoreFiles(
							dirPath, gitRepoGitIgnoreTemplate);
					}
					else if (!dirPath.equals(_modulesDirPath) &&
							 Files.exists(dirPath.resolve("build.xml"))) {

						_testAntPluginIgnoreFiles(dirPath);
					}
					else if (dirName.startsWith("frontend-theme-") &&
							 Files.exists(dirPath.resolve("gulpfile.js"))) {

						_testThemeIgnoreFiles(dirPath, themeGitIgnoreTemplate);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.equals(".gitignore")) {
						_testGitIgnoreFile(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Test
	public void testScanLog4JConfigurationXML() throws IOException {
		final Map<String, String> renameMap = new HashMap<>();

		renameMap.put(
			"src/main/resources/META-INF/portal-log4j-ext.xml",
			"module-log4j-ext.xml");
		renameMap.put(
			"src/main/resources/META-INF/portal-log4j.xml", "module-log4j.xml");
		renameMap.put(
			"src/META-INF/portal-log4j-ext.xml", "module-log4j-ext.xml");
		renameMap.put("src/META-INF/portal-log4j.xml", "module-log4j.xml");

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

	private String _getAntPluginLibGitIgnore(Path dirPath) throws IOException {
		Path liferayPluginPackagePropertiesPath = dirPath.resolve(
			"docroot/WEB-INF/liferay-plugin-package.properties");

		if (Files.notExists(liferayPluginPackagePropertiesPath)) {
			return null;
		}

		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(
				liferayPluginPackagePropertiesPath)) {

			properties.load(inputStream);
		}

		Set<String> jars = new TreeSet<>();

		jars.add("commons-logging.jar");
		jars.add("log4j.jar");
		jars.add("util-bridges.jar");
		jars.add("util-java.jar");
		jars.add("util-taglib.jar");

		String[] portalDependencyJars = StringUtil.split(
			properties.getProperty(
				"portal-dependency-jars",
				properties.getProperty("portal.dependency.jars")));

		for (String portalDependencyJar : portalDependencyJars) {
			jars.add(portalDependencyJar);
		}

		StringBundler sb = new StringBundler(jars.size() * 3 - 1);

		boolean first = true;

		for (String jar : jars) {
			if (!first) {
				sb.append(CharPool.NEW_LINE);
			}

			first = false;

			sb.append(CharPool.SLASH);
			sb.append(jar);
		}

		return sb.toString();
	}

	private String _getAntPluginsGitIgnore(final Path dirPath, String gitIgnore)
		throws IOException {

		if (dirPath.equals(_modulesDirPath)) {
			return gitIgnore;
		}

		final Set<String> pluginDirNames = new TreeSet<>();

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
					Path pluginDirPath,
					BasicFileAttributes basicFileAttributes) {

					if (Files.exists(pluginDirPath.resolve("build.xml")) &&
						(Files.exists(pluginDirPath.resolve("ivy.xml")) ||
						 Files.exists(
							 pluginDirPath.resolve(
								 "docroot/WEB-INF/service.xml"))) &&
						Files.isDirectory(pluginDirPath.resolve("docroot"))) {

						String pluginDirName = String.valueOf(
							dirPath.relativize(pluginDirPath));

						if (File.separatorChar != CharPool.SLASH) {
							pluginDirName = StringUtil.replace(
								pluginDirName, File.separatorChar,
								CharPool.SLASH);
						}

						pluginDirNames.add(pluginDirName);

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		if (pluginDirNames.isEmpty()) {
			return gitIgnore;
		}

		StringBundler sb = new StringBundler(pluginDirNames.size() * 4 + 2);

		if (Validator.isNotNull(gitIgnore)) {
			sb.append(gitIgnore);
			sb.append(CharPool.NEW_LINE);
			sb.append(CharPool.NEW_LINE);
		}

		boolean first = true;

		for (String pluginDirName : pluginDirNames) {
			if (!first) {
				sb.append(CharPool.NEW_LINE);
			}

			first = false;

			sb.append("!/");
			sb.append(pluginDirName);
			sb.append("/docroot/WEB-INF/lib");
		}

		return sb.toString();
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

	private String _getProjectPathPrefix(Path dirPath) {
		String projectPathPrefix = String.valueOf(
			_modulesDirPath.relativize(dirPath));

		projectPathPrefix =
			":" +
				StringUtil.replace(
					projectPathPrefix, File.separatorChar, CharPool.COLON);

		return projectPathPrefix;
	}

	private boolean _isInGitRepo(Path dirPath) {
		while (dirPath != null) {
			if (Files.exists(dirPath.resolve(".gitrepo"))) {
				return true;
			}

			dirPath = dirPath.getParent();
		}

		return false;
	}

	private String _read(Path path) throws IOException {
		Assert.assertTrue("Missing " + path, Files.exists(path));

		String s = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, System.lineSeparator(), StringPool.NEW_LINE);
	}

	private void _testAntPluginIgnoreFiles(Path dirPath) throws IOException {
		_testEquals(
			dirPath.resolve("docroot/WEB-INF/lib/.gitignore"),
			_getAntPluginLibGitIgnore(dirPath));

		if (!_isInGitRepo(dirPath)) {
			Path parentDirPath = dirPath.getParent();

			_testEquals(
				parentDirPath.resolve(".gitignore"),
				_getAntPluginsGitIgnore(parentDirPath, null));
		}
	}

	private void _testAppBuildScripts(Path dirPath) throws IOException {
		Path buildGradlePath = dirPath.resolve("build.gradle");

		String buildGradle = _read(buildGradlePath);

		Assert.assertEquals(
			"Incorrect " + buildGradlePath, _APP_BUILD_GRADLE, buildGradle);
	}

	private void _testEquals(Path path, String expected) throws IOException {
		if (Validator.isNotNull(expected)) {
			String actual = _read(path);

			Assert.assertEquals("Incorrect " + path, expected, actual);
		}
		else {
			Assert.assertFalse("Forbidden " + path, Files.exists(path));
		}
	}

	private void _testGitIgnoreFile(Path path) throws IOException {
		Path dirPath = path.getParent();

		try (UnsyncBufferedReader unsyncBufferedReader =
			new UnsyncBufferedReader(new FileReader(path.toFile()))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = StringUtil.trim(line);

				if (!StringUtil.startsWith(line, "!/")) {
					continue;
				}

				int end = line.indexOf(CharPool.SLASH, 2);

				if (end == -1) {
					end = line.length();
				}

				String name = line.substring(2, end);

				Assert.assertTrue(
					"Incorrect \"" + line + "\" in " + path,
					Files.exists(dirPath.resolve(name)));
			}
		}
	}

	private void _testGitRepoBuildScripts(
			Path dirPath, String buildGradleTemplate,
			String gitAttributesTemplate, String settingsGradleTemplate)
		throws IOException {

		Path buildGradlePath = dirPath.resolve("build.gradle");
		Path gradlePropertiesPath = dirPath.resolve("gradle.properties");
		Path settingsGradlePath = dirPath.resolve("settings.gradle");

		String buildGradle = _read(buildGradlePath);

		Assert.assertEquals(
			"Incorrect " + buildGradlePath,
			_getGitRepoBuildGradle(dirPath, buildGradleTemplate), buildGradle);

		String gradleProperties = _read(gradlePropertiesPath);

		Assert.assertEquals(
			"Forbidden leading or trailing whitespaces in " +
				gradlePropertiesPath,
			gradleProperties.trim(), gradleProperties);

		String previousKey = null;
		String projectPathPrefix = null;

		for (String line : StringUtil.split(
				gradleProperties, CharPool.NEW_LINE)) {

			Assert.assertFalse(
				"Forbbiden empty line in " + gradlePropertiesPath,
				Validator.isNull(line));

			int pos = line.indexOf(CharPool.EQUAL);

			Assert.assertTrue(
				"Incorrect line \"" + line + "\" in " +
					gradlePropertiesPath,
				pos != -1);

			String key = line.substring(0, pos);

			Assert.assertTrue(
				gradlePropertiesPath +
					" contains duplicate lines or is not sorted",
				(previousKey == null) || (key.compareTo(previousKey) > 0));

			if (key.equals(_GIT_REPO_GRADLE_PROJECT_PATH_PREFIX_KEY)) {
				projectPathPrefix = line.substring(pos + 1);
			}
			else {
				Assert.assertTrue(
					"Incorrect key \"" + key + "\" in " +
						gradlePropertiesPath,
					_gitRepoGradlePropertiesKeys.contains(key));
			}

			previousKey = key;
		}

		Assert.assertEquals(
			"Incorrect \"" + _GIT_REPO_GRADLE_PROJECT_PATH_PREFIX_KEY +
				"\" in " +
					gradlePropertiesPath,
			_getProjectPathPrefix(dirPath), projectPathPrefix);

		String settingsGradle = _read(settingsGradlePath);

		Assert.assertEquals(
			"Incorrect " + settingsGradlePath, settingsGradleTemplate,
			settingsGradle);

		// LPS-67772

		Path gitAttributesPath = dirPath.resolve(".gitattributes");

		Assert.assertTrue(
			"Missing " + gitAttributesPath, Files.exists(gitAttributesPath));

		String gitAttributes = _read(gitAttributesPath);

		Assert.assertEquals(
			"Incorrect " + gitAttributesPath, gitAttributesTemplate,
			gitAttributes);
	}

	private void _testGitRepoIgnoreFiles(Path dirPath, String gitIgnoreTemplate)
		throws IOException {

		Path gitIgnorePath = dirPath.resolve(".gitignore");

		String gitIgnore = _read(gitIgnorePath);

		Assert.assertEquals(
			"Incorrect " + gitIgnorePath,
			_getAntPluginsGitIgnore(dirPath, gitIgnoreTemplate), gitIgnore);
	}

	private void _testThemeBuildScripts(Path dirPath) throws IOException {
		if (!_contains(
				dirPath.resolve("package.json"), "\"liferay-theme-tasks\":")) {

			return;
		}

		Path gulpfileJsPath = dirPath.resolve("gulpfile.js");

		Assert.assertTrue(
			"Missing " + gulpfileJsPath, Files.exists(gulpfileJsPath));
	}

	private void _testThemeIgnoreFiles(Path dirPath, String gitIgnoreTemplate)
		throws IOException {

		Path resourcesImporterDirPath = dirPath.resolve("resources-importer");

		if (Files.exists(resourcesImporterDirPath)) {
			Path resourcesImporterIgnorePath = resourcesImporterDirPath.resolve(
				_SOURCE_FORMATTER_IGNORE_FILE_NAME);

			Assert.assertTrue(
				"Missing " + resourcesImporterIgnorePath,
				Files.exists(resourcesImporterIgnorePath));
		}

		Path gitIgnorePath = dirPath.resolve(".gitignore");

		String gitIgnore = _read(gitIgnorePath);

		Assert.assertEquals(
			"Incorrect " + gitIgnorePath, gitIgnoreTemplate, gitIgnore);
	}

	private static final String _APP_BUILD_GRADLE =
		"apply plugin: \"com.liferay.app.defaults.plugin\"";

	private static final String _GIT_REPO_GRADLE_PROJECT_PATH_PREFIX_KEY =
		"project.path.prefix";

	private static final String _SOURCE_FORMATTER_IGNORE_FILE_NAME =
		"source_formatter.ignore";

	private static final Set<String> _gitRepoGradlePropertiesKeys =
		Collections.singleton("com.liferay.source.formatter.version");
	private static Path _modulesDirPath;

}