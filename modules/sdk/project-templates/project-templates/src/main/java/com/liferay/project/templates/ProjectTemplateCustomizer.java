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

package com.liferay.project.templates;

import java.io.File;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Gregory Amerson
 */
public interface ProjectTemplateCustomizer {

	public void onAfterGenerateProject(
		File destinationDir,
		ArchetypeGenerationResult archetypeGenerationResult);

	public void onBeforeGenerateProject(
		ProjectTemplatesArgs projectTemplatesArgs,
		ArchetypeGenerationRequest archetypeGenerationRequest);

}