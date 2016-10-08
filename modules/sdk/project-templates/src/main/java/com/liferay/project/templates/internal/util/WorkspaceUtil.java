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

package com.liferay.project.templates.internal.util;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.regex.Pattern;

/**
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 */
public class WorkspaceUtil {

	public static final String WORKSPACE = "workspace";

	public static boolean isWorkspace(File dir) throws IOException {
		Path rootDirPath = FileUtil.getRootDir(
			dir.toPath(), _SETTINGS_GRADLE_FILE_NAME);

		if (rootDirPath == null) {
			return false;
		}

		String settingsGradle = FileUtil.read(
			rootDirPath.resolve(_SETTINGS_GRADLE_FILE_NAME));

		if (StringUtil.contains(settingsGradle, _pattern)) {
			return true;
		}

		// For Workspace plugin < 1.0.5

		Path buildGradlePath = rootDirPath.resolve(_BUILD_GRADLE_FILE_NAME);

		if (Files.notExists(buildGradlePath)) {
			return false;
		}

		String buildGradle = FileUtil.read(buildGradlePath);

		if (StringUtil.contains(buildGradle, _pattern)) {
			return true;
		}

		return false;
	}

	private static final String _BUILD_GRADLE_FILE_NAME = "build.gradle";

	private static final String _SETTINGS_GRADLE_FILE_NAME = "settings.gradle";

	private static final Pattern _pattern = Pattern.compile(
		"apply\\s+plugin\\s*:\\s*['\"]com\\.liferay\\.workspace['\"]");

}