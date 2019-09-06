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