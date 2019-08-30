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

package com.liferay.gradle.plugins.snapshot.development;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.DependencySubstitution;
import org.gradle.api.artifacts.DependencySubstitutions;
import org.gradle.api.artifacts.ResolutionStrategy;
import org.gradle.api.artifacts.component.ComponentSelector;
import org.gradle.internal.component.external.model.DefaultModuleComponentSelector;
import org.gradle.internal.component.local.model.DefaultProjectComponentSelector;

/**
 * @author David Truong
 */
public class SnapshotDevelopmentConfigurator implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		Set<Project> projects = project.getSubprojects();

		if (!projects.isEmpty()) {
			return;
		}

		_rootDir = GradleUtil.getRootDir(project, "portal-impl");

		_projectPrefix = _getProjectPrefix(project);

		ConfigurationContainer configurations = project.getConfigurations();

		configurations.all(
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					_configureResolutionStrategy(configuration);
				}

			});
	}

	private boolean _checkSnapshot(String moduleName) {
		File snapshotDir = new File(
			_rootDir, SnapshotDevelopmentPlugin.M2_REPO_PATH);

		File moduleFile = new File(snapshotDir, moduleName);

		return moduleFile.exists();
	}

	private void _configureDependencySubstitution(
		DependencySubstitution dependencySubstitution) {

		ComponentSelector componentSelector =
			dependencySubstitution.getRequested();

		if (componentSelector instanceof DefaultProjectComponentSelector) {
			DefaultProjectComponentSelector projectComponentSelector =
				(DefaultProjectComponentSelector)componentSelector;

			String projectName = projectComponentSelector.getProjectName();

			if (projectName.startsWith(_projectPrefix)) {
				return;
			}

			String moduleName = _getModuleName(projectName);

			if ((moduleName != null) && _checkSnapshot(moduleName)) {
				dependencySubstitution.useTarget(
					_getDependencyNotation(
						_DEFAULT_GROUP, moduleName, _DEFAULT_VERSION));
			}
		}
		else {
			DefaultModuleComponentSelector moduleComponentSelector =
				(DefaultModuleComponentSelector)componentSelector;

			String group = moduleComponentSelector.getGroup();

			if (group.equals("com.liferay.portal")) {
				dependencySubstitution.useTarget(
					_getDependencyNotation(
						_DEFAULT_GROUP,
						_getProjectName(moduleComponentSelector.getModule()),
						_DEFAULT_VERSION));
			}
		}
	}

	private void _configureResolutionStrategy(Configuration configuration) {
		ResolutionStrategy resolutionStrategy =
			configuration.getResolutionStrategy();

		DependencySubstitutions dependencySubstitutions =
			resolutionStrategy.getDependencySubstitution();

		dependencySubstitutions.all(
			new Action<DependencySubstitution>() {

				@Override
				public void execute(
					DependencySubstitution dependencySubstitution) {

					_configureDependencySubstitution(dependencySubstitution);
				}

			});
	}

	private String _getDependencyNotation(
		String group, String name, String version) {

		return group + ":" + name + ":" + version;
	}

	private String _getModuleName(String projectName) {
		return "com.liferay." + projectName.replaceAll("-", ".");
	}

	private String _getProjectName(String moduleName) {
		moduleName = moduleName.replaceAll("com\\.liferay\\.", "");

		return moduleName.replaceAll("\\.", "-");
	}

	private String _getProjectPrefix(Project project) {
		Project parentProject = project.getParent();

		return parentProject.getName();
	}

	private static final String _DEFAULT_GROUP = "com.liferay.snapshot";

	private static final String _DEFAULT_VERSION = "1.0.0";

	private String _projectPrefix;
	private File _rootDir;

}