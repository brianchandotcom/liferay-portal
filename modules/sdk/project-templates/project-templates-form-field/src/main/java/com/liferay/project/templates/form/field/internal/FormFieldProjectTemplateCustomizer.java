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

package com.liferay.project.templates.form.field.internal;

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplatesArgs;

import java.io.File;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Renato Rego
 */
public class FormFieldProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		String liferayVersion = projectTemplatesArgs.getLiferayVersion();

		if (!liferayVersion.startsWith("7.1")) {
			String name = projectTemplatesArgs.getName();

			Path destinationDirPath = destinationDir.toPath();

			Path projectDirPath = destinationDirPath.resolve(name);

			List<String> filesToDelete = new ArrayList<>();

			filesToDelete.add(".babelrc");
			filesToDelete.add(".npmbundlerrc");
			filesToDelete.add("package.json");
			filesToDelete.add(
				"src/main/resources/META-INF/resources/" + name + ".es.js");

			for (String file : filesToDelete) {
				ProjectTemplateCustomizer.deleteFileInPath(
					file, projectDirPath);
			}
		}
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {
	}

}