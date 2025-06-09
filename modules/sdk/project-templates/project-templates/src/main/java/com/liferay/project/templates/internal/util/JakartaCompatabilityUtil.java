/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.internal.util;

import com.liferay.project.templates.extensions.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Brian Greenwald
 */
public class JakartaCompatabilityUtil {

	public static void updateForJakarta(File destinationDir) throws Exception {
		Files.walkFileTree(
			destinationDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (basicFileAttributes.isRegularFile()) {
						File file = path.toFile();

						FileUtil.replaceString(
							file, _IMPORT_PACKAGE_OLD, _IMPORT_PACKAGE_NEW);

						String fileName = file.getName();

						if (fileName.endsWith(".jsp")) {
							FileUtil.replaceString(
								file, _TAGLIB_URL_OLD, _TAGLIB_URL_NEW);
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static final String _IMPORT_PACKAGE_NEW = "jakarta";

	private static final String _IMPORT_PACKAGE_OLD = "javax";

	private static final String _TAGLIB_URL_NEW = "jakarta.tags.core";

	private static final String _TAGLIB_URL_OLD =
		"http://java.sun.com/jsp/jstl/core";

}