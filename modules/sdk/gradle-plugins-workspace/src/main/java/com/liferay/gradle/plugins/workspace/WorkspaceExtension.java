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
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

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
		_bundleTokenDownload = _getProperty(
			settings, "bundle.token.download", _BUNDLE_TOKEN_DOWNLOAD);
		_bundleTokenEmailAddress = _getProperty(
			settings, "bundle.token.email.address",
			_BUNDLE_TOKEN_EMAIL_ADDRESS);
		_bundleTokenForce = _getProperty(
			settings, "bundle.token.force", _BUNDLE_TOKEN_FORCE);
		_bundleTokenPassword = _getProperty(
			settings, "bundle.token.password", _BUNDLE_TOKEN_PASSWORD);
		_bundleUrl = _getProperty(
			settings, "bundle.url", BundleSupportConstants.DEFAULT_BUNDLE_URL);
		_configsDir = _getProperty(
			settings, "configs.dir",
			BundleSupportConstants.DEFAULT_CONFIGS_DIR_NAME);
		_environment = _getProperty(
			settings, "environment",
			BundleSupportConstants.DEFAULT_ENVIRONMENT);
		_homeDir = _getProperty(
			settings, "home.dir",
			BundleSupportConstants.DEFAULT_LIFERAY_HOME_DIR_NAME);
		_rootProjectConfigurator = new RootProjectConfigurator(settings);
	}

	public String getBundleDistRootDirName() {
		return GradleUtil.toString(_bundleDistRootDirName);
	}

	public String getBundleTokenEmailAddress() {
		return GradleUtil.toString(_bundleTokenEmailAddress);
	}

	public String getBundleTokenPassword() {
		return GradleUtil.toString(_bundleTokenPassword);
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

	public boolean isBundleTokenDownload() {
		return GradleUtil.toBoolean(_bundleTokenDownload);
	}

	public boolean isBundleTokenForce() {
		return GradleUtil.toBoolean(_bundleTokenForce);
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

	public void setBundleTokenDownload(Object bundleTokenDownload) {
		_bundleTokenDownload = bundleTokenDownload;
	}

	public void setBundleTokenEmailAddress(Object bundleTokenEmailAddress) {
		_bundleTokenEmailAddress = bundleTokenEmailAddress;
	}

	public void setBundleTokenForce(Object bundleTokenForce) {
		_bundleTokenForce = bundleTokenForce;
	}

	public void setBundleTokenPassword(Object bundleTokenPassword) {
		_bundleTokenPassword = bundleTokenPassword;
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

	private boolean _getProperty(
		Object object, String keySuffix, boolean defaultValue) {

		return GradleUtil.getProperty(
			object, WorkspacePlugin.PROPERTY_PREFIX + keySuffix, defaultValue);
	}

	private String _getProperty(
		Object object, String keySuffix, String defaultValue) {

		return GradleUtil.getProperty(
			object, WorkspacePlugin.PROPERTY_PREFIX + keySuffix, defaultValue);
	}

	private static final String _BUNDLE_DIST_ROOT_DIR_NAME = null;

	private static final boolean _BUNDLE_TOKEN_DOWNLOAD = false;

	private static final String _BUNDLE_TOKEN_EMAIL_ADDRESS = null;

	private static final boolean _BUNDLE_TOKEN_FORCE = false;

	private static final String _BUNDLE_TOKEN_PASSWORD = null;

	private Object _bundleDistRootDirName;
	private Object _bundleTokenDownload;
	private Object _bundleTokenEmailAddress;
	private Object _bundleTokenForce;
	private Object _bundleTokenPassword;
	private Object _bundleUrl;
	private Object _configsDir;
	private Object _environment;
	private final Gradle _gradle;
	private Object _homeDir;
	private final Set<ProjectConfigurator> _projectConfigurators =
		new HashSet<>();
	private final Plugin<Project> _rootProjectConfigurator;

}