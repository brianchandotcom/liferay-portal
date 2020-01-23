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

package com.liferay.project.templates.rest.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;

import java.io.File;

import java.nio.file.Path;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public class RESTProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "rest";
	}

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		String liferayVersion = projectTemplatesArgs.getLiferayVersion();

		if (liferayVersion.startsWith("7.1") ||
			liferayVersion.startsWith("7.2") ||
			liferayVersion.startsWith("7.3")) {

			String cxfConfig =
				"com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties";

			Path destinationDirPath = destinationDir.toPath();

			Path projectDirPath = destinationDirPath.resolve(
				projectTemplatesArgs.getName());

			ProjectTemplateCustomizer.deleteFileInPath(
				cxfConfig, projectDirPath);

			String restExtenderConfig =
				"com.liferay.portal.remote.rest.extender.configuration." +
					"RestExtenderConfiguration-rest.properties";

			ProjectTemplateCustomizer.deleteFileInPath(
				restExtenderConfig, projectDirPath);

			Path configPath = projectDirPath.resolve(
				"src/main/resources/configuration");

			ProjectTemplateCustomizer.deleteFileInPath(
				configPath.toString(), projectDirPath);
		}
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {
	}

}