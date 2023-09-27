/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.nio.file.Files;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Gregory Amerson
 */
public class ZipUtil {

	public static void unzip(File sourceFile, File destDir) throws IOException {
		try (ZipFile zipFile = new ZipFile(sourceFile)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String entryName = zipEntry.getName();

				if (zipEntry.isDirectory()) {
					_createDir(new File(destDir, entryName));

					continue;
				}

				File file = new File(destDir, entryName);

				_createDir(file.getParentFile());

				try (InputStream inputStream = zipFile.getInputStream(zipEntry);
					OutputStream outputStream = Files.newOutputStream(
						file.toPath())) {

					byte[] bytes = new byte[1024];

					int count = inputStream.read(bytes);

					while (count != -1) {
						outputStream.write(bytes, 0, count);

						count = inputStream.read(bytes);
					}

					outputStream.flush();
				}
			}
		}
	}

	private static void _createDir(File dir) throws IOException {
		if (!dir.exists() && !dir.mkdirs()) {
			throw new IOException(
				"Unable to create directory " + dir.getPath());
		}
	}

}