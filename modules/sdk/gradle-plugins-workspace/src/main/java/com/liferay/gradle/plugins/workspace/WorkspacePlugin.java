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

package com.liferay.gradle.plugins.workspace;

import com.liferay.gradle.plugins.workspace.configurators.ProjectConfigurator;
import com.liferay.gradle.plugins.workspace.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.ExtensionContainer;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class WorkspacePlugin implements Plugin<Settings> {

	public static final String EXTENSION_NAME = "liferayWorkspace";

	public static final String PROPERTY_PREFIX = "liferay.workspace.";

	@Override
	public void apply(Settings settings) {
		Gradle gradle = settings.getGradle();

		final WorkspaceExtension workspaceExtension = addWorkspaceExtension(
			settings);

		for (ProjectConfigurator projectConfigurator :
				workspaceExtension.getProjectConfigurators()) {

			for (File rootDir : projectConfigurator.getDefaultRootDirs()) {
				for (File projectDir : projectConfigurator.getProjectDirs(
						rootDir)) {

					String projectPath = GradleUtil.getProjectPath(
						projectDir, settings.getRootDir());

					settings.include(new String[] {projectPath});

					_projectConfiguratorsMap.put(
						projectPath, projectConfigurator);
				}
			}
		}

		gradle.beforeProject(
			new Closure<Void>(settings) {

				@SuppressWarnings("unused")
				public void doCall(Project project) {
					Plugin<Project> plugin = null;

					if (project.getParent() == null) {
						plugin =
							workspaceExtension.getRootProjectConfigurator();
					}
					else {
						plugin = _projectConfiguratorsMap.get(
							project.getPath());
					}

					if (plugin != null) {
						plugin.apply(project);
					}
				}

			});
	}

	protected WorkspaceExtension addWorkspaceExtension(Settings settings) {
		ExtensionAware extensionAware = (ExtensionAware)settings.getGradle();

		ExtensionContainer extensionContainer = extensionAware.getExtensions();

		return extensionContainer.create(
			EXTENSION_NAME, WorkspaceExtension.class, settings);
	}

	private static final Map<String, ProjectConfigurator>
		_projectConfiguratorsMap = new HashMap<>();

}