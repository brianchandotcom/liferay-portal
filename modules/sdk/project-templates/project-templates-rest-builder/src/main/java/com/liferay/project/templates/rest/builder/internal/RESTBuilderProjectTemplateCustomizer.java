/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.rest.builder.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;

import java.io.File;

import java.nio.file.Path;

import java.util.Properties;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Javier de Arcos
 */
public class RESTBuilderProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "rest-builder";
	}

	@Override
	public void onAfterGenerateProject(
		ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
		ArchetypeGenerationResult archetypeGenerationResult) {
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {

		String artifactId = archetypeGenerationRequest.getArtifactId();

		String apiPath = ":" + artifactId + "-api";
		String clientPath = ":" + artifactId + "-client";

		File destinationDir = new File(
			archetypeGenerationRequest.getOutputDirectory());

		File workspaceDir = WorkspaceUtil.getWorkspaceDir(destinationDir);

		if (workspaceDir != null) {
			Path destinationDirPath = destinationDir.toPath();
			Path workspaceDirPath = workspaceDir.toPath();

			destinationDirPath = destinationDirPath.toAbsolutePath();
			workspaceDirPath = workspaceDirPath.toAbsolutePath();

			String relativePath = String.valueOf(
				workspaceDirPath.relativize(destinationDirPath));

			relativePath = relativePath.replace(File.separatorChar, ':');

			if (relativePath.isEmpty()) {
				apiPath = ":" + artifactId + apiPath;
				clientPath = ":" + artifactId + clientPath;
			}
			else {
				apiPath = ":" + relativePath + ":" + artifactId + apiPath;
				clientPath = ":" + relativePath + ":" + artifactId + clientPath;
			}
		}

		Properties properties = archetypeGenerationRequest.getProperties();

		setProperty(properties, "apiPath", apiPath);
		setProperty(properties, "clientPath", clientPath);
	}

}