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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Iterator;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static boolean endsWithEmptyLine(Path path) throws IOException {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(
				path.toFile(), "r")) {

			long pos = randomAccessFile.length() - 1;

			if (pos < 0) {
				return false;
			}

			randomAccessFile.seek(pos);

			int c = randomAccessFile.read();

			if ((c == '\n') || (c == '\r')) {
				return true;
			}
		}

		return false;
	}

	public static boolean exists(Path dirPath, String glob) throws IOException {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			if (iterator.hasNext()) {
				return true;
			}
		}

		return false;
	}

	public static String getExtension(String fileName) {
		int pos = fileName.indexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	public static String read(Path path) throws IOException {
		String content = new String(
			Files.readAllBytes(path), StandardCharsets.UTF_8);

		return content.replace("\r\n", "\n");
	}

	public static String read(String name) throws IOException {
		StringBuilder sb = new StringBuilder();

		ClassLoader classLoader = FileTestUtil.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(name);
			InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, StandardCharsets.UTF_8);
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		}

		String content = sb.toString();

		return content.trim();
	}

}