/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.theme.contributor.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.Validator;

import java.io.File;

import java.util.Properties;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public class ThemeContributorProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "theme-contributor";
	}

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

		Properties properties = archetypeGenerationRequest.getProperties();

		ThemeContributorProjectTemplatesArgs
			themeContributorProjectTemplatesArgs =
				(ThemeContributorProjectTemplatesArgs)
					projectTemplatesArgs.getProjectTemplatesArgsExt();

		if (Validator.isNull(
				themeContributorProjectTemplatesArgs.getContributorType())) {

			themeContributorProjectTemplatesArgs.setContributorType(
				projectTemplatesArgs.getName());
		}

		setProperty(
			properties, "contributorType",
			themeContributorProjectTemplatesArgs.getContributorType());
	}

}