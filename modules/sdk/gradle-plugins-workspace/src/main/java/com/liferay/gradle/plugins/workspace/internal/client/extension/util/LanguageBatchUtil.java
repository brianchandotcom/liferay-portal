/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.client.extension.util;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Objects;

import org.gradle.api.Project;

/**
 * @author Thiago Buarque
 */
public class LanguageBatchUtil {

	public static boolean isSpecialLanguageProject(
		File rootDir, File projectDir) {

		Path dirPath = projectDir.toPath();

		if (Objects.equals(rootDir.toPath(), dirPath.getParent()) &&
			dirPath.endsWith(Paths.get("language")) &&
			Files.exists(
				Paths.get(dirPath.toString(), "lang", "Language.properties"))) {

			return true;
		}

		return false;
	}

	public static boolean isSpecialLanguageProject(Project project) {
		return isSpecialLanguageProject(
			project.getRootDir(), project.getProjectDir());
	}

}