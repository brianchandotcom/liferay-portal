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

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Peter Shin
 */
public class FileUtil {

	public static String read(File file) throws IOException {
		String s = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return s.replace("\r\n", "\n");
	}

	public static void write(File file, String content) throws IOException {
		if (file.exists() && content.equals(read(file))) {
			return;
		}

		Path path = file.toPath();

		Files.createDirectories(path.getParent());

		Files.write(path, content.getBytes(StandardCharsets.UTF_8));

		System.out.println("Writing " + file);
	}

}