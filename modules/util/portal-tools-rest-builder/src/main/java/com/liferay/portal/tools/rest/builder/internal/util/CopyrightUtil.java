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

package com.liferay.portal.tools.rest.builder.internal.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Peter Shin
 */
public class CopyrightUtil {

	public static String getCopyright() throws IOException {
		return _getCopyright();
	}

	public static boolean isCommercial() throws IOException {
		Path path = Paths.get(".");

		if (path == null) {
			return false;
		}

		Path absolutePath = path.toAbsolutePath();

		absolutePath = absolutePath.normalize();

		String absoluteFileName = absolutePath.toString();

		absoluteFileName = absoluteFileName.replace('\\', '/');

		if (absoluteFileName.contains("/modules/private/apps/")) {
			return true;
		}

		File dir = absolutePath.toFile();

		while (dir != null) {
			File file = new File(dir, "gradle.properties");

			if (file.exists()) {
				Properties properties = new Properties();

				properties.load(new FileInputStream(file));

				if (properties.containsKey("project.path.prefix")) {
					String s = properties.getProperty("project.path.prefix");

					if (s.startsWith(":private:apps")) {
						return true;
					}

					return false;
				}
			}

			dir = dir.getParentFile();
		}

		return false;
	}

	private static String _getCopyright() throws IOException {
		ClassLoader classLoader = CopyrightUtil.class.getClassLoader();

		String name = "copyright.txt";

		if (isCommercial()) {
			name = "copyright-commercial.txt";
		}

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portal/tools/rest/builder/dependencies/" + name);

		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(inputStream));

		Stream<String> stream = bufferedReader.lines();

		return stream.collect(Collectors.joining("\n"));
	}

}