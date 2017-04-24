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

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.plugins.internal.AlloyTaglibDefaultsPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class PortalToolsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_initDependencies();
		_initVersions();
	}

	@Test
	public void testVersions() {
		for (Map.Entry<String, String> entry :
				_dependencyPortalToolNamesMap.entrySet()) {

			String dependencyName = entry.getKey();
			String portalToolName = entry.getValue();

			String dependency = _dependencies.getProperty(dependencyName);

			String[] tokens = dependency.split(":");

			String dependencyVersion = tokens[2];

			Assert.assertEquals(
				"Please update \"" + portalToolName + "\" version to " +
					dependencyVersion + " in " + _file.getAbsolutePath(),
				dependencyVersion, _versions.get(portalToolName));
		}
	}

	private static void _initDependencies() throws IOException {
		Path libDirPath = Paths.get("../../../lib");

		Files.walkFileTree(
			libDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path path = dirPath.resolve("dependencies.properties");

					if (Files.notExists(path)) {
						return FileVisitResult.CONTINUE;
					}

					try (InputStream inputStream = Files.newInputStream(path)) {
						_dependencies.load(inputStream);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _initVersions() throws Exception {
		_file = new File(
			"src/main/resources/" + PortalTools.PORTAL_TOOLS_FILE_NAME);

		try (InputStream inputStream = new FileInputStream(_file)) {
			_versions.load(inputStream);
		}
	}

	private static final Properties _dependencies = new Properties();
	private static final Map<String, String> _dependencyPortalToolNamesMap =
		Collections.singletonMap(
			"alloy-taglib", AlloyTaglibDefaultsPlugin.PORTAL_TOOL_NAME);
	private static File _file;
	private static final Properties _versions = new Properties();

}