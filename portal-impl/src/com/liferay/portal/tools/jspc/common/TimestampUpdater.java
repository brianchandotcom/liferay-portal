/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.jspc.common;

import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.EnumSet;

/**
 * @author Minhchau Dang
 */
public class TimestampUpdater {

	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			new TimestampUpdater(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public TimestampUpdater(String classDirName) throws IOException {
		Files.walkFileTree(
			Paths.get(classDirName), EnumSet.of(FileVisitOption.FOLLOW_LINKS),
			Integer.MAX_VALUE,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(filePath.getFileName());

					if (fileName.endsWith(".java")) {
						String fileNameWithoutExtension = fileName.substring(
							0, fileName.length() - 5);

						Path classFilePath = filePath.resolveSibling(
							fileNameWithoutExtension.concat(".class"));

						Files.setLastModifiedTime(
							classFilePath,
							basicFileAttributes.lastModifiedTime());
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

}