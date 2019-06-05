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

package com.liferay.portal.lpkg.deployer.test.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Path;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.osgi.framework.Constants;
import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
public class LPKGTestUtil {

	public static InputStream createJAR(String symbolicName)
		throws IOException {

		return createJAR(symbolicName, _DEFAULT_VERSION);
	}

	public static InputStream createJAR(String symbolicName, Version version)
		throws IOException {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, symbolicName);
				attributes.putValue(
					Constants.BUNDLE_VERSION, version.toString());
				attributes.putValue("Manifest-Version", "2");

				jarOutputStream.putNextEntry(
					new ZipEntry(JarFile.MANIFEST_NAME));

				manifest.write(jarOutputStream);

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	public static void createLPKG(
			Path path, String symbolicName, boolean createWar)
		throws IOException {

		createLPKG(
			path, symbolicName, createWar, _DEFAULT_VERSION, _DEFAULT_VERSION);
	}

	public static void createLPKG(
			Path path, String symbolicName, boolean createWar,
			Version lpkgVersion, Version jarVersion)
		throws IOException {

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(path.toFile()))) {

			zipOutputStream.putNextEntry(
				new ZipEntry("liferay-marketplace.properties"));

			StringBundler sb = new StringBundler(16);

			sb.append("bundles=");
			sb.append(symbolicName);
			sb.append("#");
			sb.append(jarVersion.toString());
			sb.append("##\n");
			sb.append("category=Test\n");
			sb.append("context-names=\n");
			sb.append("description=Test\n");
			sb.append("icon-url=https://www.liferay.com/web/guest/marketplace");
			sb.append("/-/mp/asset/icon/71985553\n");
			sb.append("remote-app-id=Test\n");
			sb.append("restart-required=false\n");
			sb.append("title=");

			Path namePath = path.getFileName();

			String name = namePath.toString();

			sb.append(name.substring(0, name.indexOf(".lpkg")));

			sb.append("\nversion=");
			sb.append(lpkgVersion.toString());

			String properties = sb.toString();

			zipOutputStream.write(properties.getBytes());

			zipOutputStream.closeEntry();

			zipOutputStream.putNextEntry(
				new ZipEntry(
					StringBundler.concat(
						symbolicName, "-", jarVersion.toString(), ".jar")));

			try (InputStream inputStream = createJAR(symbolicName, jarVersion);
				OutputStream outputStream = StreamUtil.uncloseable(
					zipOutputStream)) {

				StreamUtil.transfer(inputStream, outputStream);
			}

			zipOutputStream.closeEntry();

			if (createWar) {
				zipOutputStream.putNextEntry(
					new ZipEntry(symbolicName.concat("-war-1.0.0.war")));

				try (InputStream inputStream = createWAR(symbolicName);
					OutputStream outputStream = StreamUtil.uncloseable(
						zipOutputStream)) {

					StreamUtil.transfer(inputStream, outputStream);
				}
			}
		}
	}

	public static InputStream createWAR(String symbolicName)
		throws IOException {

		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				jarOutputStream.putNextEntry(
					new ZipEntry("WEB-INF/liferay-plugin-package.properties"));

				StringBundler sb = new StringBundler(12);

				sb.append("author=Liferay, Inc.\n");
				sb.append("change-log=\n");
				sb.append("licenses=LGPL\n");
				sb.append("liferay-versions=7.0.1+\n");
				sb.append("long-description=\n");
				sb.append("module-group-id=liferay\n");
				sb.append("module-incremental-version=1\n");
				sb.append("page-url=http://www.liferay.com\n");
				sb.append("module-version=1.0.20\n");
				sb.append("name=");
				sb.append(symbolicName);
				sb.append("-war");

				String properties = sb.toString();

				jarOutputStream.write(properties.getBytes());

				jarOutputStream.closeEntry();
			}

			return new UnsyncByteArrayInputStream(
				unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
				unsyncByteArrayOutputStream.size());
		}
	}

	private static final Version _DEFAULT_VERSION = new Version(1, 0, 0);

}