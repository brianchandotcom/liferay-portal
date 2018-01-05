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

package com.liferay.project.templates;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.stream.Stream;

/**
 * @author Gregory Amerson
 */
public class ProjectTemplatesUtil {

	public static void deleteFileInPath(String fileName, Path rootDirPath) {
		try (Stream<Path> projectFiles = Files.walk(rootDirPath)) {
			Stream<Path> filter = projectFiles.filter(
				path -> {
					return Paths.get(fileName).equals(path.getFileName());
				});

			filter.findFirst(
			).ifPresent(
				path -> {
					try {
						Files.deleteIfExists(path);
					}
					catch (IOException ioe) {
					}
				}
			);
		}
		catch (IOException ioe) {
		}
	}

}