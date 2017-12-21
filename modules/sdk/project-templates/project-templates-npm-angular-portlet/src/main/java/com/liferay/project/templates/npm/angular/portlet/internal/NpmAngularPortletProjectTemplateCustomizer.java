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

package com.liferay.project.templates.npm.angular.portlet.internal;

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplatesArgs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public class NpmAngularPortletProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public void onAfterGenerateProject(
		File destinationDir,
		ArchetypeGenerationResult archetypeGenerationResult) {

		String liferayVersion = _projectTemplatesArgs.getLiferayVersion();

		if (!liferayVersion.equals("7.1")) {
			Path destinationDirPath = destinationDir.toPath();

			Path projectPath = destinationDirPath.resolve(
					_projectTemplatesArgs.getName());

			String className = _projectTemplatesArgs.getClassName();

			try (Stream<Path> projectFiles = Files.walk(projectPath)) {
				projectFiles.filter(
					path -> {
						return Paths.get(className + "WebKeys.java").equals(path.getFileName());
					}
				).findFirst(
				).ifPresent(
					path -> {
						try {
							Files.deleteIfExists(path);
						}
						catch (IOException e) {
						}
					}
				);
			}
			catch (IOException e) {
			}
		}
	}

	@Override
	public void onBeforeGenerateProject(
		ProjectTemplatesArgs projectTemplatesArgs,
		ArchetypeGenerationRequest archetypeGenerationRequest) {

		_projectTemplatesArgs = projectTemplatesArgs;

		Properties properties = archetypeGenerationRequest.getProperties();

		properties.put("packageJsonVersion", "1.0.0");
	}

	private ProjectTemplatesArgs _projectTemplatesArgs;
}