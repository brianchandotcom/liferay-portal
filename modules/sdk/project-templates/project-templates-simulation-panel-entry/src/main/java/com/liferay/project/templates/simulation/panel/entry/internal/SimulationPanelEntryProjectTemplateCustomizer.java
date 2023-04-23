package com.liferay.project.templates.simulation.panel.entry.internal;

import java.io.File;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

public class SimulationPanelEntryProjectTemplateCustomizer
	implements ProjectTemplateCustomizer{

	@Override
	public String getTemplateName() {
		return "simulation-panel-entry";
	}

	@Override
	public void onAfterGenerateProject(ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
		ArchetypeGenerationResult archetypeGenerationResult) throws Exception {

	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {

		setProperty(archetypeGenerationRequest.getProperties(), "newTemplate",
			String.valueOf(_isNewTemplate(projectTemplatesArgs)));
	}

	private boolean _isNewTemplate(ProjectTemplatesArgs projectTemplatesArgs) {
		String liferayProduct = projectTemplatesArgs.getLiferayProduct();
		String liferayVersion = projectTemplatesArgs.getLiferayVersion();

		if (liferayVersion.startsWith("7.4")) {
			String qualifiedVersion =
				liferayVersion.substring(liferayVersion.lastIndexOf(".") + 1);

			if (liferayProduct.equals("dxp")) {
				qualifiedVersion = qualifiedVersion.substring(1);
			}

			return Integer.valueOf(qualifiedVersion) > 71;
		}

		return false;
	}
}
