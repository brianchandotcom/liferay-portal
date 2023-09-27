/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.portlet.provider.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;

import java.io.File;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Simon Jiang
 */
public class PortletProviderProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "portlet-provider";
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

		setProperty(
			archetypeGenerationRequest.getProperties(), "newTemplate",
			String.valueOf(_isNewTemplate(projectTemplatesArgs)));
	}

	private boolean _isNewTemplate(ProjectTemplatesArgs projectTemplatesArgs) {
		String liferayVersion = projectTemplatesArgs.getLiferayVersion();

		if (liferayVersion.startsWith("7.4")) {
			String liferayProduct = projectTemplatesArgs.getLiferayProduct();
			String qualifiedVersion = liferayVersion.substring(
				liferayVersion.lastIndexOf(".") + 1);

			if (liferayProduct.equals("dxp")) {
				qualifiedVersion = qualifiedVersion.substring(1);
			}

			if (Integer.valueOf(qualifiedVersion) > 85) {
				return true;
			}

			return false;
		}

		return false;
	}

}