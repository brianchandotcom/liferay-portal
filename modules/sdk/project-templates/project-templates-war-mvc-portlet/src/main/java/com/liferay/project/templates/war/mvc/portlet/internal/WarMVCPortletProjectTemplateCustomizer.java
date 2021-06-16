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

package com.liferay.project.templates.war.mvc.portlet.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.VersionUtil;

import java.io.File;

import java.nio.file.Path;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Lawrence Lee
 */
public class WarMVCPortletProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "war-mvc-portlet";
	}

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		Path destinationDirPath = destinationDir.toPath();

		Path projectPath = destinationDirPath.resolve(
			projectTemplatesArgs.getName());

		File buildDir = projectPath.toFile();

		File webINFDir = new File(buildDir, "src/main/webapp/WEB-INF");

		String minorVersionString = String.valueOf(
			VersionUtil.getMinorVersion(
				projectTemplatesArgs.getLiferayVersion()));

		File liferayDisplayXMLFile = new File(webINFDir, "liferay-display.xml");

		File liferayPortletXMLFile = new File(webINFDir, "liferay-portlet.xml");

		FileUtil.replaceString(
			liferayDisplayXMLFile, "7.0", "7." + minorVersionString);
		FileUtil.replaceString(
			liferayDisplayXMLFile, "7_0", "7_" + minorVersionString);

		FileUtil.replaceString(
			liferayPortletXMLFile, "7.0", "7." + minorVersionString);
		FileUtil.replaceString(
			liferayPortletXMLFile, "7_0", "7_" + minorVersionString);
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {
	}

}