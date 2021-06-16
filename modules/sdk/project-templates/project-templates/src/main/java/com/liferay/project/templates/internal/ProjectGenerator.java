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

package com.liferay.project.templates.internal;

import com.liferay.project.templates.extensions.ProjectTemplateCustomizer;
import com.liferay.project.templates.extensions.ProjectTemplatesArgs;
import com.liferay.project.templates.extensions.constants.ProjectTemplatesConstants;
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.ProjectTemplatesUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.VersionUtil;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;

import java.io.File;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ServiceLoader;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;
import org.apache.maven.archetype.ArchetypeManager;

/**
 * @author Gregory Amerson
 */
public class ProjectGenerator {

	public ArchetypeGenerationResult generateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir)
		throws Exception {

		List<File> archetypesDirs = projectTemplatesArgs.getArchetypesDirs();
		String artifactId = projectTemplatesArgs.getName();
		String author = projectTemplatesArgs.getAuthor();
		String className = projectTemplatesArgs.getClassName();
		boolean dependencyManagementEnabled =
			projectTemplatesArgs.isDependencyManagementEnabled();

		String groupId = projectTemplatesArgs.getGroupId();
		String liferayVersion = projectTemplatesArgs.getLiferayVersion();
		String packageName = projectTemplatesArgs.getPackageName();
		String product = projectTemplatesArgs.getProduct();

		String template = projectTemplatesArgs.getTemplate();

		if (template.equals("portlet")) {
			projectTemplatesArgs.setTemplate("mvc-portlet");
		}

		File templateFile = ProjectTemplatesUtil.getTemplateFile(
			projectTemplatesArgs);

		String liferayVersions = FileUtil.getManifestProperty(
			templateFile, "Liferay-Versions");

		if ((liferayVersions != null) &&
			!VersionUtil.isLiferayVersion(liferayVersion)) {

			throw new IllegalArgumentException(
				"Specified Liferay version is invalid. Must be in range " +
					liferayVersions);
		}

		if (Objects.isNull(groupId)) {
			groupId = packageName;
		}

		File workspaceDir = WorkspaceUtil.getWorkspaceDir(destinationDir);

		String projectType = "standalone";

		if (workspaceDir != null) {
			projectType = WorkspaceUtil.WORKSPACE;
		}

		ArchetypeGenerationRequest archetypeGenerationRequest =
			new ArchetypeGenerationRequest();

		String archetypeArtifactId =
			ProjectTemplatesConstants.TEMPLATE_BUNDLE_PREFIX +
				template.replace('-', '.');

		if (archetypeArtifactId.equals(
				"com.liferay.project.templates.portlet")) {

			archetypeArtifactId = "com.liferay.project.templates.mvc.portlet";
		}

		archetypeGenerationRequest.setArchetypeArtifactId(archetypeArtifactId);

		archetypeGenerationRequest.setArchetypeGroupId("com.liferay");
		archetypeGenerationRequest.setArchetypeVersion(
			FileUtil.getManifestProperty(templateFile, "Bundle-Version"));
		archetypeGenerationRequest.setArtifactId(artifactId);
		archetypeGenerationRequest.setGroupId(groupId);
		archetypeGenerationRequest.setInteractiveMode(false);
		archetypeGenerationRequest.setOutputDirectory(destinationDir.getPath());
		archetypeGenerationRequest.setPackage(packageName);

		String buildType = "gradle";

		if (projectTemplatesArgs.isMaven()) {
			buildType = "maven";
		}

		if (buildType.equals("maven") && template.contains("-ext")) {
			throw new IllegalArgumentException(
				"EXT project is not supported for Maven");
		}

		if (buildType.equals("maven") && template.equals("form-field") &&
			!liferayVersion.startsWith("7.0") &&
			!liferayVersion.startsWith("7.1")) {

			throw new IllegalArgumentException(
				"Form Field project in Maven is only supported in 7.0 and 7.1");
		}

		Properties properties = new Properties();

		_setProperty(properties, "author", author);
		_setProperty(properties, "buildType", buildType);
		_setProperty(properties, "className", className);
		_setProperty(
			properties, "dependencyManagementEnabled",
			String.valueOf(dependencyManagementEnabled));
		_setProperty(properties, "liferayVersion", liferayVersion);
		_setProperty(properties, "package", packageName);
		_setProperty(properties, "product", product);
		_setProperty(properties, "projectType", projectType);

		archetypeGenerationRequest.setProperties(properties);

		archetypeGenerationRequest.setVersion("1.0.0");

		Archetyper archetyper = new Archetyper(archetypesDirs);

		ProjectTemplateCustomizer projectTemplateCustomizer =
			_getProjectTemplateCustomizer(template);

		if (projectTemplateCustomizer != null) {
			projectTemplateCustomizer.onBeforeGenerateProject(
				projectTemplatesArgs, archetypeGenerationRequest);
		}

		ArchetypeManager archetypeManager = archetyper.createArchetypeManager();

		ArchetypeGenerationResult archetypeGenerationResult =
			archetypeManager.generateProjectFromArchetype(
				archetypeGenerationRequest);

		if (projectTemplateCustomizer != null) {
			projectTemplateCustomizer.onAfterGenerateProject(
				projectTemplatesArgs, destinationDir,
				archetypeGenerationResult);
		}

		return archetypeGenerationResult;
	}

	private ProjectTemplateCustomizer _getProjectTemplateCustomizer(
			String templateName)
		throws Exception {

		ServiceLoader<ProjectTemplateCustomizer> serviceLoader =
			ServiceLoader.load(ProjectTemplateCustomizer.class);

		Iterator<ProjectTemplateCustomizer> iterator = serviceLoader.iterator();

		while (iterator.hasNext()) {
			ProjectTemplateCustomizer projectTemplateCustomizer =
				iterator.next();

			if (templateName.equals(
					projectTemplateCustomizer.getTemplateName())) {

				return projectTemplateCustomizer;
			}
		}

		return null;
	}

	private void _setProperty(
		Properties properties, String name, String value) {

		if (Validator.isNotNull(value)) {
			properties.setProperty(name, value);
		}
	}

}