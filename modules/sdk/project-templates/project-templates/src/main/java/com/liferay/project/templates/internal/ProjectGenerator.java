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

import aQute.bnd.version.Version;
import aQute.bnd.version.VersionRange;

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplates;
import com.liferay.project.templates.ProjectTemplatesArgs;
import com.liferay.project.templates.WorkspaceUtil;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.ProjectTemplatesUtil;
import com.liferay.project.templates.internal.util.Validator;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ServiceLoader;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;
import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.common.ArchetypeArtifactManager;

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

		File templateFile = _getTemplateFile(projectTemplatesArgs);

		String liferayVersions = FileUtil.getManifestProperty(
			templateFile, "Liferay-Versions");

		if ((liferayVersions != null) &&
			!_isInVersionRange(liferayVersion, liferayVersions)) {

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

		String template = projectTemplatesArgs.getTemplate();

		ArchetypeGenerationRequest archetypeGenerationRequest =
			new ArchetypeGenerationRequest();

		archetypeGenerationRequest.setArchetypeArtifactId(
			ProjectTemplates.TEMPLATE_BUNDLE_PREFIX +
				template.replace('-', '.'));
		archetypeGenerationRequest.setArchetypeGroupId("com.liferay");
		archetypeGenerationRequest.setArchetypeVersion("0");
		archetypeGenerationRequest.setArtifactId(artifactId);
		archetypeGenerationRequest.setGroupId(groupId);
		archetypeGenerationRequest.setInteractiveMode(false);
		archetypeGenerationRequest.setOutputDirectory(destinationDir.getPath());
		archetypeGenerationRequest.setPackage(packageName);

		String buildType = "gradle";

		if (projectTemplatesArgs.isMaven()) {
			buildType = "maven";
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
		_setProperty(properties, "projectType", projectType);

		archetypeGenerationRequest.setProperties(properties);

		archetypeGenerationRequest.setVersion("1.0.0");

		Archetyper archetyper = new Archetyper(archetypesDirs);

		ArchetypeArtifactManager archetypeArtifactManager =
			archetyper.createArchetypeArtifactManager();

		ProjectTemplateCustomizer projectTemplateCustomizer =
			_getProjectTemplateCustomizer(
				archetypeArtifactManager.getArchetypeFile(
					archetypeGenerationRequest.getArchetypeGroupId(),
					archetypeGenerationRequest.getArchetypeArtifactId(),
					archetypeGenerationRequest.getArchetypeVersion(), null,
					null, null));

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

	private static File _getTemplateFile(
			ProjectTemplatesArgs projectTemplatesArgs)
		throws Exception {

		String template = projectTemplatesArgs.getTemplate();

		for (File archetypesDir : projectTemplatesArgs.getArchetypesDirs()) {
			if (!archetypesDir.isDirectory()) {
				continue;
			}

			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(
						archetypesDir.toPath(), "*.project.templates.*")) {

				for (Path path : directoryStream) {
					String fileName = String.valueOf(path.getFileName());

					String templateName = ProjectTemplatesUtil.getTemplateName(
						fileName);

					if (templateName.equals(template)) {
						return path.toFile();
					}
				}
			}
		}

		String artifactId =
			ProjectTemplates.TEMPLATE_BUNDLE_PREFIX +
				template.replace('-', '.');

		return ProjectTemplatesUtil.getArchetypeFile(artifactId);
	}

	private static boolean _isInVersionRange(
		String versionString, String range) {

		Version version = new Version(versionString);

		VersionRange versionRange = new VersionRange(range);

		return versionRange.includes(version);
	}

	private ProjectTemplateCustomizer _getProjectTemplateCustomizer(
			File archetypeFile)
		throws MalformedURLException {

		URI uri = archetypeFile.toURI();

		URLClassLoader urlClassLoader = new URLClassLoader(
			new URL[] {uri.toURL()});

		ServiceLoader<ProjectTemplateCustomizer> serviceLoader =
			ServiceLoader.load(ProjectTemplateCustomizer.class, urlClassLoader);

		Iterator<ProjectTemplateCustomizer> iterator = serviceLoader.iterator();

		if (iterator.hasNext()) {
			return iterator.next();
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