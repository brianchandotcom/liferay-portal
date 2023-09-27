/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.Properties;

import org.junit.Assert;

import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public abstract class LPKGVersionChangeTestCase {

	protected void testVersionChange(
			int majorDelta, int minorDelta, int microDelta)
		throws IOException {

		String liferayHome = SystemProperties.get("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(liferayHome, "/osgi/marketplace"))) {

			for (Path lpkgPath : directoryStream) {
				String lpkgPathString = lpkgPath.toString();

				if (lpkgPathString.contains("override")) {
					continue;
				}

				try (FileSystem fileSystem = FileSystems.newFileSystem(
						lpkgPath, null)) {

					Path path = fileSystem.getPath(
						"liferay-marketplace.properties");

					String propertiesString = new String(
						Files.readAllBytes(path), StandardCharsets.UTF_8);

					Properties properties = new Properties();

					properties.load(new UnsyncStringReader(propertiesString));

					String versionString = properties.getProperty("version");

					Version version = new Version(versionString);

					version = new Version(
						version.getMajor() + majorDelta,
						version.getMinor() + minorDelta,
						version.getMicro() + microDelta);

					propertiesString = StringUtil.replace(
						propertiesString, "version=".concat(versionString),
						"version=".concat(version.toString()));

					Files.write(
						path, Arrays.asList(propertiesString),
						StandardCharsets.UTF_8,
						StandardOpenOption.TRUNCATE_EXISTING,
						StandardOpenOption.WRITE);
				}
			}
		}
	}

}