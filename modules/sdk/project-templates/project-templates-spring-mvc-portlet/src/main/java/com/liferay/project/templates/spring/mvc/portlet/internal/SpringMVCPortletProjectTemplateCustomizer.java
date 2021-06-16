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

package com.liferay.project.templates.spring.mvc.portlet.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.VersionUtil;

import java.io.File;

import java.nio.file.Path;

import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Neil Griffin
 * @author Lawrence Lee
 */
public class SpringMVCPortletProjectTemplateCustomizer
	implements ProjectTemplateCustomizer {

	@Override
	public String getTemplateName() {
		return "spring-mvc-portlet";
	}

	@Override
	public void onAfterGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir,
			ArchetypeGenerationResult archetypeGenerationResult)
		throws Exception {

		SpringMVCPortletProjectTemplatesArgsExt ext =
			(SpringMVCPortletProjectTemplatesArgsExt)
				projectTemplatesArgs.getProjectTemplatesArgsExt();

		Path destinationDirPath = destinationDir.toPath();

		Path projectPath = destinationDirPath.resolve(
			projectTemplatesArgs.getName());

		File buildDir = projectPath.toFile();

		File webappDir = new File(buildDir, "src/main/webapp");

		File viewsDir = new File(webappDir, "WEB-INF/views");

		String packageName = projectTemplatesArgs.getPackageName();

		File spring4JavaPkgDir = new File(
			buildDir,
			"src/main/java/" + packageName.replaceAll("[.]", "/") + "/spring4");

		String viewType = ext.getViewType();

		if (viewType.equals("jsp")) {
			FileUtil.deleteFilesByPattern(viewsDir.toPath(), _jspPattern);
		}
		else {
			FileUtil.deleteFilesByPattern(viewsDir.toPath(), _thymeleafPattern);
		}

		String framework = ext.getFramework();

		if (viewType.equals("jsp") || framework.equals("portletmvc4spring")) {
			FileUtil.deleteDir(spring4JavaPkgDir.toPath());
		}

		String minorVersionString = String.valueOf(
			VersionUtil.getMinorVersion(
				projectTemplatesArgs.getLiferayVersion()));

		File liferayPortletXML = new File(
			webappDir, "WEB-INF/liferay-display.xml");

		File liferayDisplayXML = new File(
			webappDir, "WEB-INF/liferay-portlet.xml");

		FileUtil.replaceString(
			liferayDisplayXML, "7.0", "7." + minorVersionString);
		FileUtil.replaceString(
			liferayDisplayXML, "7_0", "7_" + minorVersionString);

		FileUtil.replaceString(
			liferayPortletXML, "7.0", "7." + minorVersionString);
		FileUtil.replaceString(
			liferayPortletXML, "7_0", "7_" + minorVersionString);
	}

	@Override
	public void onBeforeGenerateProject(
			ProjectTemplatesArgs projectTemplatesArgs,
			ArchetypeGenerationRequest archetypeGenerationRequest)
		throws Exception {

		Properties properties = archetypeGenerationRequest.getProperties();

		SpringMVCPortletProjectTemplatesArgsExt
			springMVCPortletProjectTemplatesArgsExt =
				(SpringMVCPortletProjectTemplatesArgsExt)
					projectTemplatesArgs.getProjectTemplatesArgsExt();

		setProperty(
			properties, "framework",
			springMVCPortletProjectTemplatesArgsExt.getFramework());
		setProperty(
			properties, "frameworkDependencies",
			springMVCPortletProjectTemplatesArgsExt.getFrameworkDependencies());
		setProperty(
			properties, "viewType",
			springMVCPortletProjectTemplatesArgsExt.getViewType());
	}

	private static final Pattern _jspPattern = Pattern.compile(".*.html");
	private static final Pattern _thymeleafPattern = Pattern.compile(".*.jspx");

}