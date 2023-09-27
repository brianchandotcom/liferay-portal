/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.war.hook.internal;

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
public class WarHookProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "war-hook";
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

		File liferayHookXMLFile = new File(webINFDir, "liferay-hook.xml");

		FileUtil.replaceString(
			liferayHookXMLFile, "7.0", "7." + minorVersionString);
		FileUtil.replaceString(
			liferayHookXMLFile, "7_0", "7_" + minorVersionString);
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {
	}

}