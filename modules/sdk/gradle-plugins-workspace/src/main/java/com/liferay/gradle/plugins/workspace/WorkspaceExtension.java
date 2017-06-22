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
import com.liferay.gradle.plugins.workspace.configurators.RootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.ThemesProjectConfigurator;
import com.liferay.gradle.plugins.workspace.configurators.WarsProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.lang.MissingPropertyException;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
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
		_projectConfigurators.add(new WarsProjectConfigurator(settings));

		_bundleDistRootDirName = _getProperty(
			settings, "bundle.dist.root.dir", _BUNDLE_DIST_ROOT_DIR_NAME);
		_bundleUrl = _getProperty(settings, "bundle.url", _BUNDLE_URL);
		_configsDir = _getProperty(settings, "configs.dir", _CONFIGS_DIR);
		_environment = _getProperty(settings, "environment", _ENVIRONMENT);
		_homeDir = _getProperty(settings, "home.dir", _HOME_DIR);
		_rootProjectConfigurator = new RootProjectConfigurator(settings);
	}

	public String getBundleDistRootDirName() {
		return GradleUtil.toString(_bundleDistRootDirName);
	}

	public String getBundleUrl() {
		return GradleUtil.toString(_bundleUrl);
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

	public Plugin<Project> getRootProjectConfigurator() {
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

	public void setBundleDistRootDirName(Object bundleDistRootDirName) {
		_bundleDistRootDirName = bundleDistRootDirName;
	}

	public void setBundleUrl(Object bundleUrl) {
		_bundleUrl = bundleUrl;
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

	private String _getProperty(
		Object object, String keySuffix, String defaultValue) {

		return GradleUtil.getProperty(
			object, WorkspacePlugin.PROPERTY_PREFIX + keySuffix, defaultValue);
	}

	private static final String _BUNDLE_DIST_ROOT_DIR_NAME = null;

	private static final String _BUNDLE_URL =
		"https://cdn.lfrs.sl/releases.liferay.com/portal/7.0.3-ga4" +
			"/liferay-ce-portal-tomcat-7.0-ga4-20170613175008905.zip";

	private static final String _CONFIGS_DIR = "configs";

	private static final String _ENVIRONMENT = "local";

	private static final String _HOME_DIR = "bundles";

	private Object _bundleDistRootDirName;
	private Object _bundleUrl;
	private Object _configsDir;
	private Object _environment;
	private final Gradle _gradle;
	private Object _homeDir;
	private final Set<ProjectConfigurator> _projectConfigurators =
		new HashSet<>();
	private final Plugin<Project> _rootProjectConfigurator;

}