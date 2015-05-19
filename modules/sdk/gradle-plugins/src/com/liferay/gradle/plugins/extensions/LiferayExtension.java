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

package com.liferay.gradle.plugins.extensions;

import com.liferay.gradle.util.ClosureBackedScript;

import groovy.lang.Closure;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayExtension {

	public LiferayExtension(Project project) {
		this.project = project;
	}

	public void appServers(Closure<?> closure) {
		ConfigSlurper configSlurper = new ConfigSlurper();

		ConfigObject newAppServers = configSlurper.parse(
			new ClosureBackedScript(closure));

		_appServers.merge(newAppServers);
	}

	public File getAppServerDeployDir() {
		return _appServerDeployDir;
	}

	public File getAppServerDir() {
		return _appServerDir;
	}

	public File getAppServerLibGlobalDir() {
		return _appServerLibGlobalDir;
	}

	public File getAppServerParentDir() {
		return _appServerParentDir;
	}

	public File getAppServerPortalDir() {
		return _appServerPortalDir;
	}

	public ConfigObject getAppServers() {
		return _appServers;
	}

	public String getAppServerType() {
		return _appServerType;
	}

	public File getDeployDir() {
		return _deployDir;
	}

	public File getLiferayHome() {
		return _liferayHome;
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public File getTmpDir() {
		return _tmpDir;
	}

	public String getVersionPrefix() {
		String version = getPortalVersion();

		int index = version.indexOf("-");

		if (index != -1) {
			version = version.substring(0, index);
		}

		return version;
	}

	public void setAppServerDeployDir(File appServerDeployDir) {
		_appServerDeployDir = appServerDeployDir;
	}

	public void setAppServerDir(File appServerDir) {
		_appServerDir = appServerDir;
	}

	public void setAppServerLibGlobalDir(File appServerLibGlobalDir) {
		_appServerLibGlobalDir = appServerLibGlobalDir;
	}

	public void setAppServerParentDir(File appServerParentDir) {
		_appServerParentDir = appServerParentDir;
	}

	public void setAppServerPortalDir(File appServerPortalDir) {
		_appServerPortalDir = appServerPortalDir;
	}

	public void setAppServerType(String appServerType) {
		_appServerType = appServerType;
	}

	public void setDeployDir(File deployDir) {
		_deployDir = deployDir;
	}

	public void setLiferayHome(File liferayHome) {
		_liferayHome = liferayHome;
	}

	public void setPortalVersion(String portalVersion) {
		_portalVersion = portalVersion;
	}

	public void setTmpDir(File tmpDir) {
		_tmpDir = tmpDir;
	}

	protected final Project project;

	private File _appServerDeployDir;
	private File _appServerDir;
	private File _appServerLibGlobalDir;
	private File _appServerParentDir;
	private File _appServerPortalDir;
	private final ConfigObject _appServers = new ConfigObject();
	private String _appServerType;
	private File _deployDir;
	private File _liferayHome;
	private String _portalVersion;
	private File _tmpDir;

}