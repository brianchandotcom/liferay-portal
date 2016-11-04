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

package com.liferay.portal.release.versions;

import aQute.bnd.osgi.Constants;
import aQute.bnd.version.Version;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class ReleaseVersionsTest {

	@Test
	public void testReleaseVersions() throws IOException {
		final Path portalPath = Paths.get(System.getProperty("user.dir"));

		String otherDirName = System.getProperty(
			"release.versions.test.other.dir");

		Assert.assertTrue(
			"Please set the property \"release.versions.test.other.dir\"",
			Validator.isNotNull(otherDirName));

		final Path otherPath = Paths.get(otherDirName);

		Assert.assertTrue(
			otherPath + " is not a valid Git repository",
			Files.exists(otherPath.resolve(".git")));

		final boolean otherRelease = _isRelease(otherPath);

		boolean differentTypes = false;

		if (otherRelease != _isRelease(portalPath)) {
			differentTypes = true;
		}

		Assert.assertTrue(
			portalPath + " and " + otherPath + " must be different types",
			differentTypes);

		final Set<Path> ignorePaths = new HashSet<>(
			Arrays.asList(portalPath.resolve("modules/third-party")));

		Files.walkFileTree(
			portalPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (ignorePaths.contains(dirPath)) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					Path bndBndPath = dirPath.resolve("bnd.bnd");

					if (Files.notExists(bndBndPath)) {
						return FileVisitResult.CONTINUE;
					}

					Path bndBndRelativePath = portalPath.relativize(bndBndPath);

					Path otherBndBndPath = otherPath.resolve(
						bndBndRelativePath);

					if (Files.notExists(otherBndBndPath)) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Ignoring " + bndBndRelativePath +
									" as it does not exist in " + otherPath);
						}

						return FileVisitResult.SKIP_SUBTREE;
					}

					_checkReleaseVersion(
						bndBndPath, otherBndBndPath, otherRelease,
						portalPath.relativize(dirPath));

					return FileVisitResult.SKIP_SUBTREE;
				}

			});
	}

	private void _checkReleaseVersion(
			Path bndBndPath, Path otherBndBndPath, boolean otherRelease,
			Path relativePath)
		throws IOException {

		Properties bndProperties = _loadProperties(bndBndPath);
		Properties otherBndProperties = _loadProperties(otherBndBndPath);

		String bundleSymbolicName = bndProperties.getProperty(
			Constants.BUNDLE_SYMBOLICNAME);
		String otherBundleSymbolicName = otherBndProperties.getProperty(
			Constants.BUNDLE_SYMBOLICNAME);

		Assert.assertEquals(bundleSymbolicName, otherBundleSymbolicName);

		ObjectValuePair<Version, Path> versionPathPair = _getVersion(
			bndBndPath, bndProperties);
		ObjectValuePair<Version, Path> otherVersionPathPair = _getVersion(
			otherBndBndPath, otherBndProperties);

		ObjectValuePair<Version, Path> masterVersionPair = otherVersionPathPair;

		ObjectValuePair<Version, Path> releaseVersionPair = versionPathPair;

		if (otherRelease) {
			masterVersionPair = versionPathPair;
			releaseVersionPair = otherVersionPathPair;
		}

		Version masterVersion = masterVersionPair.getKey();
		Version releaseVersion = releaseVersionPair.getKey();

		int delta = 0;

		for (int i = 0; i < 3; i++) {
			int masterVersionPart = masterVersion.get(i);
			int releaseVersionPart = releaseVersion.get(i);

			if (masterVersionPart != releaseVersionPart) {
				delta = masterVersionPart - releaseVersionPart;

				break;
			}
		}

		if ((delta != 0) && (delta != 1)) {
			StringBundler sb = new StringBundler(13);

			sb.append("Difference in ");
			sb.append(Constants.BUNDLE_VERSION);
			sb.append(" for ");
			sb.append(relativePath);
			sb.append(" between master (");
			sb.append(masterVersion);
			sb.append(", defined in ");

			Path masterVersionPath = masterVersionPair.getValue();

			sb.append(masterVersionPath.getFileName());

			sb.append(") and release (");
			sb.append(releaseVersion);
			sb.append(", defined in ");

			Path releaseVersionPath = releaseVersionPair.getValue();

			sb.append(releaseVersionPath.getFileName());

			sb.append(") branches is not allowed");

			Assert.fail(sb.toString());
		}
	}

	private ObjectValuePair<Version, Path> _getVersion(
			Path bndBndPath, Properties bndProperties)
		throws IOException {

		Path versionOverridePath = _getVersionOverrideFile(
			bndBndPath.getParent());

		if (versionOverridePath != null) {
			Properties versionOverrides = _loadProperties(versionOverridePath);

			String version = versionOverrides.getProperty(
				Constants.BUNDLE_VERSION);

			if (Validator.isNotNull(version)) {
				return new ObjectValuePair<>(
					Version.parseVersion(version), versionOverridePath);
			}
		}

		String version = bndProperties.getProperty(Constants.BUNDLE_VERSION);

		return new ObjectValuePair<>(Version.parseVersion(version), bndBndPath);
	}

	private Path _getVersionOverrideFile(Path dirPath) {
		Path dirNamePath = dirPath.getFileName();

		String fileName =
			".version-override-" + dirNamePath.toString() + ".properties";

		while (true) {
			Path path = dirPath.resolve(fileName);

			if (Files.exists(path)) {
				return path;
			}

			dirPath = dirPath.getParent();

			if (dirPath == null) {
				return null;
			}
		}
	}

	private boolean _isRelease(Path path) {
		if (Files.exists(path.resolve("modules/.releng"))) {
			return true;
		}

		return false;
	}

	private Properties _loadProperties(Path path) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseVersionsTest.class);

}