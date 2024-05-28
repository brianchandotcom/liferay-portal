/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import java.io.File;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class ReleaseUtilTest {

	@Before
	public void setUp() throws Exception {
		_mirrorDir = _createTempDir("releasesMirror");
		_workspaceCacheDir = _createTempDir("workspaceCache");
	}

	@Test
	public void testCustomMirrors() throws Exception {
		_writeFile(
			"releases.json", Paths.get(_mirrorDir.getPath(), "releases.json"));
		_writeFile(
			"release.properties",
			Paths.get(
				_mirrorDir.getPath(), "dxp", "dxp-test-release-key",
				"release.properties"));

		URI mirrorDirURI = _mirrorDir.toURI();

		ReleaseUtil.initialize(
			0, Collections.singletonList(mirrorDirURI.toString()),
			_workspaceCacheDir);

		ReleaseUtil.ReleaseProperties releaseProperties =
			ReleaseUtil.getReleaseProperties("dxp-test-release-key");

		Assert.assertNotNull(releaseProperties);

		Assert.assertEquals(
			"DXP Test Product Version",
			releaseProperties.getLiferayProductVersion());
	}

	private File _createTempDir(String name) throws Exception {
		Path tempDirectoryPath = Files.createTempDirectory(
			"ReleasesUtilTest_" + name);

		File tempDirectoryFile = tempDirectoryPath.toFile();

		tempDirectoryFile.deleteOnExit();

		return tempDirectoryFile;
	}

	private void _writeFile(String resourceFileName, Path destinationPath)
		throws Exception {

		Path parentDirPath = destinationPath.getParent();

		File parentDirFile = parentDirPath.toFile();

		if (!parentDirFile.exists() && !parentDirFile.mkdirs()) {
			throw new Exception("Failed to create directory: " + parentDirPath);
		}

		try (InputStream inputStream =
				ReleaseUtilTest.class.getResourceAsStream(resourceFileName)) {

			if (inputStream == null) {
				throw new Exception(
					"Could not read resource from classpath: " +
						resourceFileName);
			}

			Files.copy(inputStream, destinationPath);
		}
	}

	private File _mirrorDir;
	private File _workspaceCacheDir;

}