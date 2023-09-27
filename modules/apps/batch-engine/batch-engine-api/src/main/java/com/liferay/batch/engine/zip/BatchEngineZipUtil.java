/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.zip;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Igor Beslic
 */
public class BatchEngineZipUtil {

	public static <T> File toJarFile(Class<T> clazz, String fileName)
		throws Exception {

		return _toZipFile(clazz, fileName, "jar");
	}

	public static <T> File toZipFile(Class<T> clazz, String fileName)
		throws Exception {

		return _toZipFile(clazz, fileName, "zip");
	}

	private static <T> File _toZipFile(
			Class<T> clazz, String fileName, String format)
		throws Exception {

		URL url = clazz.getResource(fileName);

		if (url == null) {
			File file = new File(
				PortalUUIDUtil.generate() + "." + StringUtil.lowerCase(format));

			try (ZipOutputStream zipOutputStream = new ZipOutputStream(
					new FileOutputStream(file))) {

				zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

				zipOutputStream.closeEntry();
			}

			return file;
		}

		Path zipFileDirectoryPath = Paths.get(url.toURI());

		Path zipFilePath = zipFileDirectoryPath.resolveSibling(
			PortalUUIDUtil.generate() + "." + format);

		File zipFile = zipFilePath.toFile();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(zipFile))) {

			Files.walkFileTree(
				zipFileDirectoryPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						File zipEntryFile = filePath.toFile();

						if (zipEntryFile.isDirectory()) {
							return FileVisitResult.CONTINUE;
						}

						Path relativePath = zipFileDirectoryPath.relativize(
							filePath);

						try (FileInputStream fileInputStream =
								new FileInputStream(zipEntryFile)) {

							zipOutputStream.putNextEntry(
								new ZipEntry(relativePath.toString()));

							zipOutputStream.write(
								StreamUtil.toByteArray(fileInputStream));

							zipOutputStream.closeEntry();
						}
						catch (Exception exception) {
							throw new IllegalStateException(
								"Unable to add new ZIP entry", exception);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"Unable to write ZIP file", exception);
		}

		return zipFile;
	}

}