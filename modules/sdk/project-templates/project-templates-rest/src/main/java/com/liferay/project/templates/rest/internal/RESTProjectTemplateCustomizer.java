/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (!liferayVersion.startsWith("7.0")) {
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