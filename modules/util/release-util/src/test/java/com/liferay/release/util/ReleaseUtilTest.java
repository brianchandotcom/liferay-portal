/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.release.util;

import java.io.File;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class ReleaseUtilTest extends ReleaseUtil {

	@Before
	public void setUp() throws Exception {
		_workspaceCacheDir = _createTempDir("workspaceCache");

		File mirrorDir = _createTempDir("releasesMirror");

		_writeFile(
			"dependencies/releases.json",
			Paths.get(mirrorDir.getPath(), "releases.json"));
		_writeFile(
			"dependencies/release.properties",
			Paths.get(
				mirrorDir.getPath(), "dxp", "second", "release.properties"));

		URI mirrorDirURI = mirrorDir.toURI();

		initialize(
			0, Collections.singletonList(mirrorDirURI.toString()),
			_workspaceCacheDir);
	}

	@Test
	public void testGetFromReleaseEntry() {
		Assert.assertEquals(
			"targetPlatformVersion value",
			getFromReleaseEntry(
				"FIRST", ReleaseEntry::getTargetPlatformVersion));
		Assert.assertNull(
			getFromReleaseEntry(
				"THIRD", ReleaseEntry::getTargetPlatformVersion));
	}

	@Test
	public void testGetPublicReleases() {
		initialize(
			0, Collections.singletonList("https://releases-cdn.liferay.com"),
			_workspaceCacheDir);

		Stream<ReleaseEntry> releaseEntryStream = getReleaseEntryStream();

		releaseEntryStream.map(
			ReleaseEntry::getReleaseKey
		).forEach(
			System.out::println
		);
	}

	@Test
	public void testGetReleaseEntries() {
		List<ReleaseEntry> releaseEntries = getReleaseEntries();

		Assert.assertEquals(
			releaseEntries.toString(), 2, releaseEntries.size());
	}

	@Test
	public void testGetReleaseEntry() throws Exception {
		ReleaseEntry releaseEntry1 = getReleaseEntry("FIRST");

		Assert.assertEquals(
			"appServerTomcatVersion value",
			releaseEntry1.getAppServerTomcatVersion());
		Assert.assertEquals(
			"buildTimestamp value", releaseEntry1.getBuildTimestamp());
		Assert.assertEquals(
			"bundleChecksumSHA512 value",
			releaseEntry1.getBundleChecksumSHA512());
		Assert.assertEquals("bundleURL value", releaseEntry1.getBundleURL());
		Assert.assertEquals(
			"gitHashLiferayDocker value",
			releaseEntry1.getGitHashLiferayDocker());
		Assert.assertEquals(
			"gitHashLiferayPortalEE value",
			releaseEntry1.getGitHashLiferayPortalEE());
		Assert.assertEquals(
			"liferayDockerImage value", releaseEntry1.getLiferayDockerImage());
		Assert.assertEquals(
			"liferayDockerTags value", releaseEntry1.getLiferayDockerTags());
		Assert.assertEquals(
			"liferayProductVersion value",
			releaseEntry1.getLiferayProductVersion());
		Assert.assertEquals("product value", releaseEntry1.getProduct());
		Assert.assertEquals(
			"productGroupVersion value",
			releaseEntry1.getProductGroupVersion());
		Assert.assertEquals(
			"productVersion value", releaseEntry1.getProductVersion());
		Assert.assertTrue(releaseEntry1.isPromoted());
		Assert.assertEquals(
			"releaseDate value", releaseEntry1.getReleaseDate());
		Assert.assertEquals("FIRST", releaseEntry1.getReleaseKey());
		Assert.assertEquals(
			"targetPlatformVersion value",
			releaseEntry1.getTargetPlatformVersion());
		Assert.assertEquals("url value", releaseEntry1.getURL());

		ReleaseEntry releaseEntry2 = getReleaseEntry("SECOND");

		Assert.assertEquals(
			"app.server.tomcat.version value",
			releaseEntry2.getAppServerTomcatVersion());
		Assert.assertEquals(
			"build.timestamp value", releaseEntry2.getBuildTimestamp());
		Assert.assertEquals(
			"bundle.checksum.sha512 value",
			releaseEntry2.getBundleChecksumSHA512());
		Assert.assertEquals("bundle.url value", releaseEntry2.getBundleURL());
		Assert.assertEquals(
			"git.hash.liferay-docker value",
			releaseEntry2.getGitHashLiferayDocker());
		Assert.assertEquals(
			"git.hash.liferay-portal-ee value",
			releaseEntry2.getGitHashLiferayPortalEE());
		Assert.assertEquals(
			"liferay.docker.image value",
			releaseEntry2.getLiferayDockerImage());
		Assert.assertEquals(
			"liferay.docker.tags value", releaseEntry2.getLiferayDockerTags());
		Assert.assertEquals(
			"liferay.product.version value",
			releaseEntry2.getLiferayProductVersion());
		Assert.assertEquals("product value", releaseEntry2.getProduct());
		Assert.assertEquals(
			"productGroupVersion value",
			releaseEntry2.getProductGroupVersion());
		Assert.assertEquals(
			"productVersion value", releaseEntry2.getProductVersion());
		Assert.assertTrue(releaseEntry2.isPromoted());
		Assert.assertEquals(
			"release.date value", releaseEntry2.getReleaseDate());
		Assert.assertEquals("SECOND", releaseEntry2.getReleaseKey());
		Assert.assertEquals(
			"target.platform.version value",
			releaseEntry2.getTargetPlatformVersion());
		Assert.assertEquals(
			"https://releases-cdn.liferay.com/dxp/second",
			releaseEntry2.getURL());
	}

	@Test
	public void testGetReleaseEntryMap() {
		Map<String, ReleaseEntry> releaseEntryMap = getReleaseEntryMap();

		Assert.assertNotNull(releaseEntryMap.get("FIRST"));
		Assert.assertNotNull(releaseEntryMap.get("SECOND"));
		Assert.assertNull(releaseEntryMap.get("THIRD"));
	}

	@Test
	public void testGetReleaseEntryStream() {
		Stream<ReleaseEntry> releaseEntryStream = getReleaseEntryStream();

		Assert.assertEquals(2, releaseEntryStream.count());
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
			throw new Exception("Unable to create directory: " + parentDirPath);
		}

		try (InputStream inputStream =
				ReleaseUtilTest.class.getResourceAsStream(resourceFileName)) {

			if (inputStream == null) {
				throw new Exception(
					"Unable to read resource from class path: " +
						resourceFileName);
			}

			Files.copy(inputStream, destinationPath);
		}
	}

	private File _workspaceCacheDir;

}