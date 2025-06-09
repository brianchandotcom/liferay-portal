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
							file, _JAVAX_STRING, _JAKARTA_STRING);

						String fileName = file.getName();

						if (fileName.endsWith(".jsp")) {
							FileUtil.replaceString(
								file, _OLD_TAGLIB_URL, _JAKARTA_TAGLIB_URL);
						}
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static final String _JAKARTA_STRING = "jakarta";

	private static final String _JAKARTA_TAGLIB_URL = "jakarta.tags.core";

	private static final String _JAVAX_STRING = "javax";

	private static final String _OLD_TAGLIB_URL =
		"http://java.sun.com/jsp/jstl/core";

}