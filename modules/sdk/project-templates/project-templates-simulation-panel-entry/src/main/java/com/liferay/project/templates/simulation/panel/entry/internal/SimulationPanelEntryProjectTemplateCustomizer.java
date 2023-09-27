/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.simulation.panel.entry.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;

import java.io.File;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Seiphon Wang
 */
public class SimulationPanelEntryProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "simulation-panel-entry";
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
			String qualifiedVersion = liferayVersion.substring(
				liferayVersion.lastIndexOf(".") + 1);

			String liferayProduct = projectTemplatesArgs.getLiferayProduct();

			if (liferayProduct.equals("dxp")) {
				qualifiedVersion = qualifiedVersion.substring(1);
			}

			if (Integer.valueOf(qualifiedVersion) > 71) {
				return true;
			}

			return false;
		}

		return false;
	}

}