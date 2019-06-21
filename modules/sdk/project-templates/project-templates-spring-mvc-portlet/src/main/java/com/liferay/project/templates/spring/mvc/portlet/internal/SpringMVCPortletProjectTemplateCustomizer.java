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

package com.liferay.project.templates.spring.mvc.portlet.internal;

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplatesArgs;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Neil Griffin
 * @author Lawrence Lee
 */
public class SpringMVCPortletProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {

		Path projectPath = Paths.get(
			archetypeGenerationRequest.getOutputDirectory(),
			archetypeGenerationRequest.getArtifactId());

		Path buildGradlePath = projectPath.resolve("build.gradle");

		Files.deleteIfExists(buildGradlePath);

		File buildDir = projectPath.toFile();

		File viewsDir = new File(buildDir, "src/main/webapp/WEB-INF/views");

		Properties properties = archetypeGenerationRequest.getProperties();

		String packageProperty = properties.getProperty("package");

		File spring4JavaPkgDir = new File(
			buildDir,
			"src/main/java/" + packageProperty.replaceAll("[.]", "/") +
				"/spring4");

		String viewTypeProperty = properties.getProperty("viewType");

		File[] viewFiles = viewsDir.listFiles();

		if (viewTypeProperty.equals("jsp")) {
			for (File viewFile : viewFiles) {
				String viewFileName = viewFile.getName();

				if (viewFileName.endsWith(".html")) {
					viewFile.delete();
				}
			}
		}
		else {
			for (File viewFile : viewFiles) {
				String viewFileName = viewFile.getName();

				if (viewFileName.endsWith(".jspx")) {
					viewFile.delete();
				}
			}
		}

		String frameWorkProperty = properties.getProperty("framework");

		File[] spring4JavaPkgFiles = spring4JavaPkgDir.listFiles();

		if (viewTypeProperty.equals("jsp") ||
			frameWorkProperty.equals("portletmvc4spring")) {

			for (File spring4JavaPkgFile : spring4JavaPkgFiles) {
				String spring4JavaPkgFileName = spring4JavaPkgDir.getName();

				if (spring4JavaPkgFileName.endsWith(".java")) {
					spring4JavaPkgFile.delete();
				}
			}
		}
	}

}