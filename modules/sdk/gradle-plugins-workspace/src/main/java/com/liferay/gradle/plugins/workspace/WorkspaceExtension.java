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

import com.liferay.gradle.plugins.workspace.configurators.ModulesProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.PluginsProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.ProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.RootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.ThemesProjectConfigurator;
import com.liferay.gradle.plugins.workspace.util.GradleUtil;

import groovy.lang.MissingPropertyException;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.gradle.api.initialization.Settings;
import org.gradle.api.invocation.Gradle;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class WorkspaceExtension {

	public WorkspaceExtension(Settings settings) {
		_gradle = settings.getGradle();

		_projectConfigurators.add(new ModulesProjectConfigurator(settings));
		_projectConfigurators.add(new PluginsProjectConfigurator(settings));
		_projectConfigurators.add(new ThemesProjectConfigurator(settings));

		_bundleArtifactGroup = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.group",
			_BUNDLE_ARTIFACT_GROUP);
		_bundleArtifactName = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.name",
			_BUNDLE_ARTIFACT_NAME);
		_bundleArtifactVersion = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + "bundle.artifact.version",
			_BUNDLE_ARTIFACT_VERSION);
		_bundleMavenUrl = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "bundle.maven.url",
			_BUNDLE_MAVEN_URL);
		_configsDir = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "configs.dir",
			_CONFIGS_DIR);
		_environment = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "environment",
			_ENVIRONMENT);
		_homeDir = GradleUtil.getProperty(
			settings, WorkspacePlugin.PROPERTY_PREFIX + "home.dir", _HOME_DIR);
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
		return GradleUtil.toFile(_gradle.getRootProject(), _configsDir);
	}

	public String getEnvironment() {
		return GradleUtil.toString(_environment);
	}

	public File getHomeDir() {
		return GradleUtil.toFile(_gradle.getRootProject(), _homeDir);
	}

	public Iterable<ProjectConfigurator> getProjectConfigurators() {
		return Collections.unmodifiableSet(_projectConfigurators);
	}

	public RootProjectConfigurator getRootProjectConfigurator() {
		return _rootProjectConfigurator;
	}

	public ProjectConfigurator propertyMissing(String name) {
		for (ProjectConfigurator projectConfigurator : _projectConfigurators) {
			if (name.equals(projectConfigurator.getName())) {
				return projectConfigurator;
			}
		}

		throw new MissingPropertyException(name, ProjectConfigurator.class);
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
	private final Gradle _gradle;
	private Object _homeDir;
	private final Set<ProjectConfigurator> _projectConfigurators =
		new HashSet<>();
	private final RootProjectConfigurator _rootProjectConfigurator =
		new RootProjectConfigurator();

}