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

package com.liferay.project.templates.release.api;

import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.ZipUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

/**
 * @author Simon Jiang
 * @author Lawrence Lee
 */
public class ReleaseAPIJarTest implements BaseReleaseAPIJarTestCase {

	public static final String RELEASE_API_JAR_SOURCES_FILE =
		System.getProperty("releaseApiJarSourcesFile");

	public List<String> getIgnorePaths(String jarName) throws Exception {
		List<String> ignoredPaths = FileTestUtil.readAllLines(
			"com/liferay/project/templates/dependencies" +
				"/api-jar-ignore-paths.txt");

		if (jarName.contains("7.0")) {
			ignoredPaths.addAll(
				FileTestUtil.readAllLines(
					"com/liferay/project/templates/dependencies" +
						"/api-jar-ignore-paths_7_0.txt"));
		}
		else if (jarName.contains("7.1")) {
			ignoredPaths.addAll(
				FileTestUtil.readAllLines(
					"com/liferay/project/templates/dependencies" +
						"/api-jar-ignore-paths_7_1.txt"));
		}
		else if (jarName.contains("7.2")) {
			ignoredPaths.addAll(
				FileTestUtil.readAllLines(
					"com/liferay/project/templates/dependencies" +
						"/api-jar-ignore-paths_7_2.txt"));
		}

		return ignoredPaths;
	}

	public Path getSourcesDirPath() throws IOException {
		File releaseApiJarSourcesFile = new File(RELEASE_API_JAR_SOURCES_FILE);

		Assert.assertTrue(releaseApiJarSourcesFile.exists());
		File sourcesDir = temporaryFolder.newFolder(
			releaseApiJarSourcesFile.getName() + "-sources");

		ZipUtil.unzip(releaseApiJarSourcesFile, sourcesDir);

		return sourcesDir.toPath();
	}

	@Test
	public void testReleaseApiJar() throws Exception {
		Assume.assumeNotNull(RELEASE_API_JAR_FILE);

		File releaseApiJarFile = new File(RELEASE_API_JAR_FILE);

		Path classesDirPath = getClassesDirPath();

		Path sourcesDirPath = getSourcesDirPath();

		Set<String> classFilePaths = getPaths(classesDirPath, ".class");

		Assert.assertFalse(classFilePaths.isEmpty());

		Set<String> javaFilePaths = getPaths(sourcesDirPath, ".java");

		Assert.assertFalse(javaFilePaths.isEmpty());

		List<String> missingClassFilePaths = new ArrayList<>();

		List<String> ignorePaths = getIgnorePaths(releaseApiJarFile.getName());

		for (String classFilePath : classFilePaths) {
			if (!classFilePath.contains("$") &&
				!ignorePaths.contains(classFilePath) &&
				!javaFilePaths.contains(classFilePath)) {

				missingClassFilePaths.add(classFilePath);
			}
		}

		Assert.assertTrue(
			"Sources jar missing: " + getFileNames(missingClassFilePaths),
			missingClassFilePaths.isEmpty());
	}

}