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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
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
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class ReleaseVersionsTest {

	@BeforeClass
	public static void setUpClass() {
		_portalPath = Paths.get(System.getProperty("user.dir"));
	}

	@Test
	public void testReleaseVersions() throws IOException {
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

		if (otherRelease != _isRelease(_portalPath)) {
			differentTypes = true;
		}

		Assert.assertTrue(
			_portalPath + " and " + otherPath + " must be different types",
			differentTypes);

		final Set<Path> ignorePaths = new HashSet<>(
			Arrays.asList(_portalPath.resolve("modules/third-party")));

		Files.walkFileTree(
			_portalPath,
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

					Path bndBndRelativePath = _portalPath.relativize(
						bndBndPath);

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
						bndBndPath, otherBndBndPath, otherRelease, dirPath);

					return FileVisitResult.SKIP_SUBTREE;
				}

			});
	}

	private void _checkReleaseVersion(
			Path bndBndPath, Path otherBndBndPath, boolean otherRelease,
			Path dirPath)
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
			StringBundler sb = new StringBundler(21);

			sb.append("Difference in ");
			sb.append(Constants.BUNDLE_VERSION);
			sb.append(" for ");
			sb.append(_portalPath.relativize(dirPath));
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

			sb.append(") branches is not allowed. Please ");

			Path updateVersionPath;
			String updateVersionSeparator;

			Path gitRepoPath = _getParentFile(dirPath, ".gitrepo");

			if (gitRepoPath != null) {
				String gitRepo = _read(gitRepoPath);

				if (!gitRepo.contains("mode = pull")) {
					gitRepoPath = null;
				}
			}

			if (gitRepoPath != null) {
				updateVersionPath = gitRepoPath.getParent();

				updateVersionPath = updateVersionPath.getParent();

				updateVersionPath = updateVersionPath.resolve(
					_getVersionOverrideFileName(dirPath));

				updateVersionSeparator = StringPool.EQUAL;
			}
			else {
				updateVersionPath = dirPath.resolve("bnd.bnd");
				updateVersionSeparator = ": ";
			}

			if (Files.exists(updateVersionPath)) {
				sb.append("update");
			}
			else {
				sb.append("add");
			}

			sb.append(" \"");
			sb.append(Constants.BUNDLE_VERSION);
			sb.append(updateVersionSeparator);
			sb.append(releaseVersion);
			sb.append("\" in ");
			sb.append(_portalPath.relativize(updateVersionPath));
			sb.append(" for the master branch.");

			Assert.fail(sb.toString());
		}
	}

	private Path _getParentFile(Path dirPath, String fileName) {
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

	private ObjectValuePair<Version, Path> _getVersion(
			Path bndBndPath, Properties bndProperties)
		throws IOException {

		Path dirPath = bndBndPath.getParent();

		Path versionOverridePath = _getParentFile(
			dirPath, _getVersionOverrideFileName(dirPath));

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

	private String _getVersionOverrideFileName(Path dirPath) {
		return ".version-override-" + String.valueOf(dirPath.getFileName()) +
			".properties";
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

	private String _read(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReleaseVersionsTest.class);

	private static Path _portalPath;

}