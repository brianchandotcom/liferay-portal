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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gregory Amerson
 */
public class WorkspaceUtil {

	public static boolean isWorkspace(File dir) {
		File workspaceDir = _getWorkspaceDir(dir);

		File gradleFile = new File(workspaceDir, _SETTINGS_GRADLE_FILE_NAME);

		if (!gradleFile.exists()) {
			return false;
		}

		try {
			String script = _read(gradleFile);

			Matcher matcher = _PATTERN_WORKSPACE_PLUGIN.matcher(script);

			if (matcher.find()) {
				return true;
			}
			else {
				//For workspace plugin < 1.0.5

				gradleFile = new File(workspaceDir, _BUILD_GRADLE_FILE_NAME);

				script = _read(gradleFile);

				matcher = _PATTERN_WORKSPACE_PLUGIN.matcher(script);

				return matcher.find();
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	private static File _findParentFile(
		File dir, String[] fileNames, boolean checkParents) {

		if (dir == null) {
			return null;
		}

		for (String fileName : fileNames) {
			File file = new File(dir, fileName);

			if (file.exists()) {
				return dir;
			}
		}

		if (checkParents) {
			return _findParentFile(
				dir.getParentFile(), fileNames, checkParents);
		}

		return null;
	}

	private static File _getWorkspaceDir(File dir) {
		return _findParentFile(
			dir,
			new String[] {
				_SETTINGS_GRADLE_FILE_NAME, _GRADLE_PROPERTIES_FILE_NAME
			},
			true);
	}

	private static String _read(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()));
	}

	private static final String _BUILD_GRADLE_FILE_NAME = "build.gradle";

	private static final String _GRADLE_PROPERTIES_FILE_NAME =
		"gradle.properties";

	private static final Pattern _PATTERN_WORKSPACE_PLUGIN = Pattern.compile(
		".*apply\\s*plugin\\s*:\\s*[\'\"]com\\.liferay\\.workspace[\'\"]\\s*$",
		Pattern.MULTILINE | Pattern.DOTALL);

	private static final String _SETTINGS_GRADLE_FILE_NAME = "settings.gradle";

}