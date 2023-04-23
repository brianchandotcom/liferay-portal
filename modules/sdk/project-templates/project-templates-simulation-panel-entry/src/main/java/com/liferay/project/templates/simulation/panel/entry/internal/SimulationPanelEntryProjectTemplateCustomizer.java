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