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

package com.liferay.gradle.plugins.workspace.extensions;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author David Truong
 */
public class WorkspaceExtension {

	public WorkspaceExtension(Project project) {
		_project = project;
	}

	public String getBundleGroup() {
		return _bundleGroup;
	}

	public String getBundleMavenUrl() {
		return _bundleMavenUrl;
	}

	public String getBundleName() {
		return _bundleName;
	}

	public String getBundleVersion() {
		return _bundleVersion;
	}

	public String getEnvironment() {
		return _environment;
	}

	public File getHomeDir() {
		return _project.file(_homeDir);
	}

	public File getModulesDir() {
		return _project.file(_modulesDir);
	}

	public File getPluginsSDKDir() {
		return _project.file(_pluginsSDKDir);
	}

	public File getThemesDir() {
		return _project.file(_themesDir);
	}

	public void setBundleGroup(String bundleGroup) {
		_bundleGroup = bundleGroup;
	}

	public void setBundleMavenUrl(String bundleMavenUrl) {
		_bundleMavenUrl = bundleMavenUrl;
	}

	public void setBundleName(String bundleName) {
		_bundleName = bundleName;
	}

	public void setBundleVersion(String bundleVersion) {
		_bundleVersion = bundleVersion;
	}

	public void setEnvironment(String environment) {
		_environment = environment;
	}

	public void setHomeDir(Object homeDir) {
		_homeDir = homeDir;
	}

	public void setModulesDir(Object modulesDir) {
		_modulesDir = modulesDir;
	}

	public void setPluginsSDKDir(Object pluginsSDKDir) {
		_pluginsSDKDir = pluginsSDKDir;
	}

	public void setThemesDir(Object themesDir) {
		_themesDir = themesDir;
	}

	private String _bundleGroup;
	private String _bundleMavenUrl;
	private String _bundleName;
	private String _bundleVersion;
	private String _environment;
	private Object _homeDir;
	private Object _modulesDir;
	private Object _pluginsSDKDir;
	private final Project _project;
	private Object _themesDir;

}