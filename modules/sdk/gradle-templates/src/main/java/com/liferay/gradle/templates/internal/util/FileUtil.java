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

package com.liferay.gradle.templates.internal.util;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static final DirectoryStream.Filter<Path> directoriesOnlyFilter =
		new DirectoryStream.Filter<Path>() {

			@Override
			public boolean accept(Path path) throws IOException {
				if (Files.isDirectory(path)) {
					return true;
				}

				return false;
			}

		};

	public static void replace(Path path, Map<String, String> replacements)
		throws IOException {

		String extension = _getExtension(path);

		if (Arrays.binarySearch(_TEXT_EXTENSIONS, extension) < 0) {
			return;
		}

		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		String newContent = StringUtil.replace(content, replacements);

		if (!content.equals(newContent)) {
			Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));
		}
	}

	private static String _getExtension(Path path) {
		Path fileNamePath = path.getFileName();

		String fileName = fileNamePath.toString();

		int pos = fileName.lastIndexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	private static final String[] _TEXT_EXTENSIONS = {
		"bnd", "css", "gradle", "java", "js", "jsp", "jspf", "prefs", "project",
		"properties", "xml"
	};

}