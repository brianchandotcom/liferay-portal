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

import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Andrea Di Giorgi
 */
public class ModulesStructureTest {

	@Test
	public void testScanBuildScripts() throws IOException {
		ClassLoader classLoader = ModulesStructureTest.class.getClassLoader();

		final String gitRepoBuildGradleTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/" +
				"git_repo_build_gradle.tmpl");
		final String gitRepoSettingsGradleTemplate = StringUtil.read(
			classLoader,
			"com/liferay/portal/modules/dependencies/" +
				"git_repo_settings_gradle.tmpl");

		Files.walkFileTree(
			Paths.get("modules"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path dirNamePath = dirPath.getFileName();

					String dirName = dirNamePath.toString();

					if (dirName.charAt(0) == '.') {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path ivyXmlPath = dirPath.resolve("ivy.xml");

					if (Files.exists(ivyXmlPath)) {
						Assert.fail("Forbidden " + ivyXmlPath);
					}

					if (Files.exists(dirPath.resolve(".gitrepo"))) {
						_testGitRepoBuildScripts(
							dirPath, gitRepoBuildGradleTemplate,
							gitRepoSettingsGradleTemplate);
					}
					else if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						Path buildGradlePath = dirPath.resolve("build.gradle");

						if (Files.notExists(buildGradlePath)) {
							Assert.fail("Missing " + buildGradlePath);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}
					else if (Files.exists(dirPath.resolve("package.json"))) {
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

	private String _getGitRepoBuildGradle(
			Path dirPath, String buildGradleTemplate)
		throws IOException {

		final Set<String> pluginNames = new TreeSet<>();

		pluginNames.add("com.liferay.gradle.plugins");

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
								"cache", "lang.merger", "maven.plugin.builder"
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

	private String _read(Path path) throws IOException {
		String s = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		return StringUtil.replace(
			s, System.lineSeparator(), StringPool.NEW_LINE);
	}

	private void _testGitRepoBuildScripts(
			Path dirPath, String buildGradleTemplate,
			String settingsGradleTemplate)
		throws IOException {

		Path buildGradlePath = dirPath.resolve("build.gradle");
		Path settingsGradlePath = dirPath.resolve("settings.gradle");

		boolean buildGradleExists = Files.exists(buildGradlePath);
		boolean settingsGradleExists = Files.exists(settingsGradlePath);

		if (!buildGradleExists && !settingsGradleExists) {
			return;
		}

		if (!buildGradleExists) {
			Assert.fail("Missing " + buildGradlePath);
		}

		String buildGradle = _read(buildGradlePath);

		Assert.assertEquals(
			"Incorrect " + buildGradlePath,
			_getGitRepoBuildGradle(dirPath, buildGradleTemplate), buildGradle);

		if (!settingsGradleExists) {
			Assert.fail("Missing " + settingsGradlePath);
		}

		String settingsGradle = _read(settingsGradlePath);

		Assert.assertEquals(
			"Incorrect " + settingsGradlePath, settingsGradleTemplate,
			settingsGradle);
	}

}