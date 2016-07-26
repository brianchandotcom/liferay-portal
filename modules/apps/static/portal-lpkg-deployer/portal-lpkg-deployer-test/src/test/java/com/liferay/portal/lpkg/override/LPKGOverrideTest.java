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

package com.liferay.portal.lpkg.override;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public class LPKGOverrideTest {

	@Test
	public void testOverrideLPKG() throws IOException {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		File file = new File(liferayHome, "/osgi/marketplace/override");

		if (file.exists()) {
			String[] files = file.list();

			for (String childPath : files) {
				File childFile = new File(file.getPath(), childPath);

				childFile.delete();
			}
		}
		else {
			file.mkdir();
		}

		Map<String, String> overrides = new HashMap<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(liferayHome, "/osgi/marketplace"))) {

			for (Path lpkgPath : directoryStream) {
				String lpkgPathString = lpkgPath.toString();

				if (lpkgPathString.endsWith("override")) {
					continue;
				}

				try (ZipFile zipFile = new ZipFile(lpkgPath.toFile())) {
					Enumeration<? extends ZipEntry> zipEntries =
						zipFile.entries();

					while (zipEntries.hasMoreElements()) {
						ZipEntry zipEntry = zipEntries.nextElement();

						String name = zipEntry.getName();

						if ((name.startsWith("com.liferay") &&
							 name.endsWith(".jar")) ||
							name.endsWith(".war")) {

							Matcher matcher = _pattern.matcher(name);

							if (matcher.matches()) {
								name = matcher.group(1) + matcher.group(4);
							}

							if (lpkgPathString.contains("Static")) {
								Files.copy(
									zipFile.getInputStream(zipEntry),
									Paths.get(
										liferayHome, "/osgi/static/", name),
									StandardCopyOption.REPLACE_EXISTING);

								overrides.put(
									"static.".concat(
										name.substring(0, name.length() - 4)),
									null);
							}
							else {
								Files.copy(
									zipFile.getInputStream(zipEntry),
									Paths.get(file.toString(), name),
									StandardCopyOption.REPLACE_EXISTING);
							}
						}
					}
				}
			}
		}

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				Paths.get(file.toURI()))) {

			for (Path overridePath : directoryStream) {
				String overrideString = overridePath.toString();

				if (overrideString.endsWith(".war")) {
					Path fileName = overridePath.getFileName();

					String fileNameString = fileName.toString();

					overrides.put(
						"war.".concat(
							fileNameString.substring(
								0, fileNameString.length() - 4)),
						null);

					continue;
				}

				if (!overrideString.endsWith(".jar")) {
					continue;
				}

				try (FileSystem fileSystem = FileSystems.newFileSystem(
						overridePath, null)) {

					Path path = fileSystem.getPath("META-INF/MANIFEST.MF");

					try (InputStream inputStream = Files.newInputStream(path);
						UnsyncByteArrayOutputStream outputStream =
							new UnsyncByteArrayOutputStream()) {

						Manifest manifest = new Manifest(inputStream);

						Attributes attributes = manifest.getMainAttributes();

						String versionString = (String)attributes.getValue(
							"Bundle-Version");

						Version version = new Version(versionString);

						version = new Version(
							version.getMajor(), version.getMinor(),
							version.getMicro() + 1, version.getQualifier());

						versionString = version.toString();

						attributes.putValue("Bundle-Version", versionString);

						overrides.put(
							attributes.getValue("Bundle-SymbolicName"),
							versionString);

						manifest.write(outputStream);

						Files.write(
							path, outputStream.toByteArray(),
							StandardOpenOption.TRUNCATE_EXISTING,
							StandardOpenOption.WRITE);
					}
				}
			}

			StringBundler sb = new StringBundler(overrides.size() * 4);

			for (Entry<String, String> entry : overrides.entrySet()) {
				sb.append(entry.getKey());
				sb.append(StringPool.COLON);
				sb.append(entry.getValue());
				sb.append(StringPool.NEW_LINE);
			}

			sb.setIndex(sb.index() - 1);

			Files.write(
				Paths.get(liferayHome, "/overrides"),
				Arrays.asList(sb.toString()), StandardCharsets.UTF_8,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.WRITE);
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"(.*?)(-\\d+\\.\\d+\\.\\d+)(\\..+)?(\\.[jw]ar)");

}