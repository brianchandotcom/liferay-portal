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

package com.liferay.portal.lpkg.persistence;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.Constants;

/**
 * @author Matthew Tambara
 */
public class LPKGPersistenceTest {

	@Test
	public void testLPKGPersistenceDeploy() throws Exception {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		Path path = Paths.get(liferayHome, "osgi/marketplace/Test.lpkg");

		Files.createFile(path);

		_createLPKG(path);
	}

	private InputStream _createJAR() throws IOException {
		try (UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream()) {

			try (JarOutputStream jarOutputStream = new JarOutputStream(
					unsyncByteArrayOutputStream)) {

				Manifest manifest = new Manifest();

				Attributes attributes = manifest.getMainAttributes();

				attributes.putValue(Constants.BUNDLE_MANIFESTVERSION, "2");
				attributes.putValue(
					Constants.BUNDLE_SYMBOLICNAME, _SYMBOLIC_NAME);
				attributes.putValue(Constants.BUNDLE_VERSION, "1.0.0");
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

	private void _createLPKG(Path path) throws IOException {
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(path.toFile()))) {

			StringBundler sb = new StringBundler(11);

			sb.append("bundles=");
			sb.append(_SYMBOLIC_NAME);
			sb.append("#1.0.0##\n");
			sb.append("context-names=\n");
			sb.append("remote-app-id=Test\n");
			sb.append("version=1.0\n");
			sb.append("category=Test\n");
			sb.append("description=Test\n");
			sb.append("title=Test\n");
			sb.append("icon-url=https://www.liferay.com/web/guest/");
			sb.append("marketplace/-/mp/asset/icon/71985553");

			zipOutputStream.putNextEntry(
				new ZipEntry("liferay-marketplace.properties"));

			String properties = sb.toString();

			zipOutputStream.write(properties.getBytes());
			zipOutputStream.closeEntry();

			zipOutputStream.putNextEntry(
				new ZipEntry(_SYMBOLIC_NAME.concat("-1.0.0.jar")));

			try (InputStream inputStream = _createJAR();
				OutputStream outputStream = StreamUtil.uncloseable(
					zipOutputStream)) {

				StreamUtil.transfer(inputStream, outputStream);
			}

			zipOutputStream.closeEntry();
		}
	}

	private static final String _SYMBOLIC_NAME = "LPKG Persistence Test";

}