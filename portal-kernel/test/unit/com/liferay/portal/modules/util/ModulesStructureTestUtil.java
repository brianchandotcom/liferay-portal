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

package com.liferay.portal.modules.util;

import aQute.bnd.osgi.Constants;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

/**
 * @author Andrea Di Giorgi
 */
public class ModulesStructureTestUtil {

	public static List<GradleDependency> getGradleDependencies(
			String gradleContent, Path gradlePath, Path rootDirPath)
		throws IOException {

		List<GradleDependency> gradleDependencies = new ArrayList<>();

		_addGradleModuleDependencies(
			gradleDependencies, gradleContent, gradlePath);
		_addGradleProjectDependencies(
			gradleDependencies, gradleContent, gradlePath, rootDirPath);

		return gradleDependencies;
	}

	private static void _addGradleModuleDependencies(
		List<GradleDependency> gradleDependencies, String gradleContent,
		Path gradlePath) {

		Matcher matcher = _gradleModuleDependencyPattern.matcher(gradleContent);

		while (matcher.find()) {
			String dependency = matcher.group();

			String configuration = matcher.group(1);
			String moduleGroup = matcher.group(2);
			String moduleName = matcher.group(3);
			String moduleVersion = matcher.group(4);

			try {
				GradleDependency gradleDependency = new GradleDependency(
					dependency, configuration, moduleGroup, moduleName,
					moduleVersion);

				gradleDependencies.add(gradleDependency);
			}
			catch (IllegalArgumentException iae) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Ignoring dependency in " + gradlePath +
							" since version " + moduleVersion +
								" cannot be parsed: " + dependency,
						iae);
				}
			}
		}
	}

	private static void _addGradleProjectDependencies(
			List<GradleDependency> gradleDependencies, String gradleContent,
			Path gradlePath, Path rootDirPath)
		throws IOException {

		Matcher matcher = _gradleProjectDependencyPattern.matcher(
			gradleContent);

		while (matcher.find()) {
			String dependency = matcher.group();

			String configuration = matcher.group(1);
			String projectPath = matcher.group(2);

			String projectDirName = StringUtil.replace(
				projectPath.substring(1), CharPool.COLON, File.separatorChar);

			Path projectDirPath = rootDirPath.resolve(projectDirName);

			Assert.assertTrue(
				"Dependency in " + gradlePath +
					" points to non-existent project directory " +
						projectDirPath + ": " + matcher.group(),
				Files.exists(projectDirPath));

			Path bndBndPath = projectDirPath.resolve("bnd.bnd");

			Properties bndProperties = new Properties();

			try (InputStream inputStream = Files.newInputStream(bndBndPath)) {
				bndProperties.load(inputStream);
			}
			catch (NoSuchFileException nsfe) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Ignoring dependency in " + gradlePath +
							" since it points to a non-OSGi project: " +
								matcher.group(),
						nsfe);
				}

				continue;
			}

			String moduleGroup = "com.liferay";
			String moduleName = bndProperties.getProperty(
				Constants.BUNDLE_SYMBOLICNAME);
			String moduleVersion = bndProperties.getProperty(
				Constants.BUNDLE_VERSION);

			GradleDependency gradleDependency = new GradleDependency(
				dependency, configuration, moduleGroup, moduleName,
				moduleVersion);

			gradleDependencies.add(gradleDependency);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ModulesStructureTestUtil.class);

	private static final Pattern _gradleModuleDependencyPattern =
		Pattern.compile(
			"(\\w+)(?:\\s|\\()+group:\\s*['\"](.+)['\"],\\s*" +
				"name:\\s*['\"](.+)['\"],\\s*(?:transitive:\\s*\\w+,\\s*)?" +
					"version:\\s*['\"](.+)['\"]");
	private static final Pattern _gradleProjectDependencyPattern =
		Pattern.compile("(\\w+)\\s+project\\(['\"](.+)['\"]\\)");

}