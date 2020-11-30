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
public final class ZipUtil {

	public static void unzip(File sourceFile, File destdir) throws IOException {
		try (ZipFile zipFile = new ZipFile(sourceFile)) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String entryName = zipEntry.getName();

				if (zipEntry.isDirectory()) {
					_mkdir(new File(destdir, entryName));

					continue;
				}

				File file = new File(destdir, entryName);

				_mkdir(file.getParentFile());

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

	private static void _mkdir(File dir) throws IOException {
		if (!dir.exists() && !dir.mkdirs()) {
			String msg = "Could not create dir: " + dir.getPath();

			throw new IOException(msg);
		}
	}

}