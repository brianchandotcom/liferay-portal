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

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class WorkspaceExtension {

	public WorkspaceExtension(Project project) {
		_bundleArtifactGroup = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.group",
			(String)null);
		_bundleArtifactName = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.name",
			(String)null);
		_bundleArtifactVersion = GradleUtil.getProperty(
			project,
			WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.version",
			(String)null);
		_bundleMavenUrl = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "bundle.maven.url",
			(String)null);
		_environment = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "environment",
			(String)null);
		_homeDir = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "home.dir", (File)null);
		_modulesDefaultRepositoryEnabled = GradleUtil.getProperty(
			project,
			WorkspacePlugin.PROPERTY_PREFIX +
				"modules.default.repository.enabled",
			true);
		_modulesDir = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "modules.dir",
			(File)null);
		_pluginsSDKDir = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "plugins.sdk.dir",
			(File)null);
		_themesDir = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "themes.dir",
			(File)null);
	}

	public String getBundleArtifactGroup() {
		return _bundleArtifactGroup;
	}

	public String getBundleArtifactName() {
		return _bundleArtifactName;
	}

	public String getBundleArtifactVersion() {
		return _bundleArtifactVersion;
	}

	public String getBundleMavenUrl() {
		return _bundleMavenUrl;
	}

	public String getEnvironment() {
		return _environment;
	}

	public File getHomeDir() {
		return _homeDir;
	}

	public File getModulesDir() {
		return _modulesDir;
	}

	public File getPluginsSDKDir() {
		return _pluginsSDKDir;
	}

	public File getThemesDir() {
		return _themesDir;
	}

	public boolean isModulesDefaultRepositoryEnabled() {
		return _modulesDefaultRepositoryEnabled;
	}

	private final String _bundleArtifactGroup;
	private final String _bundleArtifactName;
	private final String _bundleArtifactVersion;
	private final String _bundleMavenUrl;
	private final String _environment;
	private final File _homeDir;
	private final boolean _modulesDefaultRepositoryEnabled;
	private final File _modulesDir;
	private final File _pluginsSDKDir;
	private final File _themesDir;

}