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

package com.liferay.ant.jgit;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Shuyang Zhou
 */
public class PathUtil {

	public static String toRelativePath(File gitDir, String pathString) {
		File projectDir = gitDir.getParentFile();

		Path projectPath = projectDir.toPath();

		projectPath = projectPath.toAbsolutePath();

		Path path = Paths.get(pathString);

		path = path.toAbsolutePath();

		Path relativePath = projectPath.relativize(path);

		return relativePath.toString();
	}

}