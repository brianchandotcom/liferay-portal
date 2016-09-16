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
import java.io.Writer;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;

/**
 * @author Andrea Di Giorgi
 */
public class FileTestUtil {

	public static final String PROJECT_TEMPLATE_DIR_PREFIX =
		"project-templates-";

	public static String getExtension(String fileName) {
		int pos = fileName.indexOf('.');

		if (pos == -1) {
			return "";
		}

		return fileName.substring(pos + 1);
	}

	public static DirectoryStream<Path> getProjectTemplatesDirectoryStream()
		throws IOException {

		return Files.newDirectoryStream(
			Paths.get("../"),
			new Filter<Path>() {

				@Override
				public boolean accept(Path path) throws IOException {
					if (!Files.isDirectory(path)) {
						return false;
					}

					Path fileNamePath = path.getFileName();

					String fileName = fileNamePath.toString();

					if (fileName.startsWith(PROJECT_TEMPLATE_DIR_PREFIX)) {
						return true;
					}

					return false;
				}

			});
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

	public static Properties readProperties(Path path) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties;
	}

	public static Properties readProperties(String name) throws IOException {
		Properties properties = new Properties();

		ClassLoader classLoader = FileTestUtil.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(name)) {
			properties.load(inputStream);
		}

		return properties;
	}

	public static void write(Writer writer, String... lines)
		throws IOException {

		for (String line : lines) {
			writer.write(line);
			writer.write(System.lineSeparator());
		}
	}

}