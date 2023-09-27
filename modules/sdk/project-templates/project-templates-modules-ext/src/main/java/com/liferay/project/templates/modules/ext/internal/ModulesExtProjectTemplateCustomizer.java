/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.modules.ext.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;

import java.io.File;

import java.util.Properties;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Charles Wu
 * @author Gregory Amerson
 */
public class ModulesExtProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "modules-ext";
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

		ModulesExtProjectTemplatesArgs modulesExtProjectTemplatesArgs =
			(ModulesExtProjectTemplatesArgs)
				projectTemplatesArgs.getProjectTemplatesArgsExt();

		Properties properties = archetypeGenerationRequest.getProperties();

		setProperty(
			properties, "originalModuleName",
			modulesExtProjectTemplatesArgs.getOriginalModuleName());

		if (!projectTemplatesArgs.isDependencyManagementEnabled()) {
			setProperty(
				properties, "originalModuleVersion",
				modulesExtProjectTemplatesArgs.getOriginalModuleVersion());
		}
		else {
			setProperty(properties, "originalModuleVersion", "1.0.0");
		}
	}

}