/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Anthony Chu
 */
public class ZipTestUtil {

	public static void zipDirToFile(File dir, File zipFile) throws Exception {
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(zipFile))) {

			for (File file : dir.listFiles()) {
				ZipEntry zipEntry = new ZipEntry(file.getName());

				zipOutputStream.putNextEntry(zipEntry);

				byte[] bytes = Files.readAllBytes(file.toPath());

				zipOutputStream.write(bytes, 0, bytes.length);

				zipOutputStream.closeEntry();
			}
		}
	}

}