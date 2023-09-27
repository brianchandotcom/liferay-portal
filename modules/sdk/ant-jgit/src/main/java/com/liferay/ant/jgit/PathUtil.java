/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			throw new BuildException(
				"Unable to locate .git directory", location);
		}

		return new File(projectDir, ".git");
	}

	public static String toRelativePath(File gitDir, String pathString) {
		File projectDir = gitDir.getParentFile();

		Path projectPath = projectDir.toPath();

		projectPath = projectPath.toAbsolutePath();

		Path path = Paths.get(pathString);

		path = path.toAbsolutePath();

		String relativePathString = String.valueOf(
			projectPath.relativize(path));

		if (File.separatorChar == '\\') {
			relativePathString = relativePathString.replace('\\', '/');
		}

		return relativePathString;
	}

}