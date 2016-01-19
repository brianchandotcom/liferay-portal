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

import com.liferay.gradle.plugins.workspace.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class WorkspaceExtension {

	public WorkspaceExtension(Project project) {
		_project = project;

		_bundleArtifactGroup = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.group",
			_BUNDLE_ARTIFACT_GROUP);
		_bundleArtifactName = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.name",
			_BUNDLE_ARTIFACT_NAME);
		_bundleArtifactVersion = GradleUtil.getProperty(
			project,
			WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.version",
			_BUNDLE_ARTIFACT_VERSION);
		_bundleMavenUrl = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "bundle.maven.url",
			_BUNDLE_MAVEN_URL);
		_configsDir = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "configs.dir",
			_CONFIGS_DIR);
		_environment = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "environment",
			_ENVIRONMENT);
		_homeDir = GradleUtil.getProperty(
			project, WorkspacePlugin.PROPERTY_PREFIX + "home.dir", _HOME_DIR);
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
		return GradleUtil.toString(_bundleArtifactGroup);
	}

	public String getBundleArtifactName() {
		return GradleUtil.toString(_bundleArtifactName);
	}

	public String getBundleArtifactVersion() {
		return GradleUtil.toString(_bundleArtifactVersion);
	}

	public String getBundleMavenUrl() {
		return GradleUtil.toString(_bundleMavenUrl);
	}

	public File getConfigsDir() {
		return GradleUtil.toFile(_project, _configsDir);
	}

	public String getEnvironment() {
		return GradleUtil.toString(_environment);
	}

	public File getHomeDir() {
		return GradleUtil.toFile(_project, _homeDir);
	}

	public File getModulesDir() {
		return GradleUtil.toFile(_project, _modulesDir);
	}

	public File getPluginsSDKDir() {
		return _pluginsSDKDir;
	}

	public File getThemesDir() {
		return GradleUtil.toFile(_project, _themesDir);
	}

	public boolean isModulesDefaultRepositoryEnabled() {
		return _modulesDefaultRepositoryEnabled;
	}

	public void setBundleArtifactGroup(Object bundleArtifactGroup) {
		_bundleArtifactGroup = bundleArtifactGroup;
	}

	public void setBundleArtifactName(Object bundleArtifactName) {
		_bundleArtifactName = bundleArtifactName;
	}

	public void setBundleArtifactVersion(Object bundleArtifactVersion) {
		_bundleArtifactVersion = bundleArtifactVersion;
	}

	public void setBundleMavenUrl(Object bundleMavenUrl) {
		_bundleMavenUrl = bundleMavenUrl;
	}

	public void setConfigsDir(Object configsDir) {
		_configsDir = configsDir;
	}

	public void setEnvironment(Object environment) {
		_environment = environment;
	}

	public void setHomeDir(Object homeDir) {
		_homeDir = homeDir;
	}

	private static final String _BUNDLE_ARTIFACT_GROUP = "com.liferay";

	private static final String _BUNDLE_ARTIFACT_NAME = "portal-tomcat-bundle";

	private static final String _BUNDLE_ARTIFACT_VERSION =
		"7.0-ce-b2-20160105152151933";

	private static final String _BUNDLE_MAVEN_URL =
		"https://liferay-test-01.ci.cloudbees.com/job/" +
			"liferay-bundle-publishing/lastSuccessfulBuild/artifact/build/" +
				"m2_repo/";

	private static final String _CONFIGS_DIR = "configs";

	private static final String _ENVIRONMENT = "local";

	private static final String _HOME_DIR = "bundles";

	private Object _bundleArtifactGroup;
	private Object _bundleArtifactName;
	private Object _bundleArtifactVersion;
	private Object _bundleMavenUrl;
	private Object _configsDir;
	private Object _environment;
	private Object _homeDir;
	private final boolean _modulesDefaultRepositoryEnabled;
	private final File _modulesDir;
	private final File _pluginsSDKDir;
	private final Project _project;
	private final File _themesDir;

}