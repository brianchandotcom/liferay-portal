/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.extensions;

import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.file.Path;

import java.util.Properties;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public interface ProjectTemplateCustomizer {

	public static void deleteFileInPath(String fileName, Path rootDirPath)
		throws IOException {

		FileUtil.deleteFileInPath(fileName, rootDirPath);
	}

	public default String getTemplateName() {
		return null;
	}

	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception;

	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception;

	public default void setProperty(
		Properties properties, String name, String value) {

		if (Validator.isNotNull(value)) {
			properties.setProperty(name, value);
		}
	}

}