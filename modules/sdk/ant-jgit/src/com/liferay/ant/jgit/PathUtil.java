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

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;

/**
 * @author Shuyang Zhou
 */
public class PathUtil {

	public static File getGitDir(
		File gitDir, Project project, Location location) {

		if (gitDir != null) {
			return gitDir;
		}

		String projectDir = project.getProperty("project.dir");

		if (projectDir == null) {
			projectDir = project.getProperty("lp.portal.project.dir");
		}

		if (projectDir == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Unable to locate .git folder.\n");
			sb.append("You need to at least do one of the followings :\n");
			sb.append(
				"1) Set \"gitDir\" attribute explicitly on the ant task\n");
			sb.append("2) For portal project, set property \"project.dir\"\n");
			sb.append(
				"3) For SDK project, set property \"lp.portal.project.dir\"\n");

			throw new BuildException(sb.toString(), location);
		}

		return new File(projectDir, ".git");
	}

	public static String toRelativePath(File gitDir, String pathString) {
		File projectDir = gitDir.getParentFile();

		Path projectPath = projectDir.toPath();

		projectPath = projectPath.toAbsolutePath();

		Path path = Paths.get(pathString);

		path = path.toAbsolutePath();

		Path relativePath = projectPath.relativize(path);

		String relativePathString = relativePath.toString();

		if (File.separatorChar == '\\') {
			relativePathString = relativePathString.replace('\\', '/');
		}

		return relativePathString;
	}

}