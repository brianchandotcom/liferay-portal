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
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;

import java.io.File;

import java.util.Map;

import org.gradle.api.GradleException;
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
		if (_appServerDeployDir != null) {
			return _appServerDeployDir;
		}

		return getAppServerDir("deployDirName");
	}

	public File getAppServerDir() {
		if (_appServerDir != null) {
			return _appServerDir;
		}

		File appServerParentDir = getAppServerParentDir();
		String appServerName = getAppServerProperty("name");
		String appServerVersion = getAppServerProperty("version");

		if ((appServerParentDir == null) || Validator.isNull(appServerName) ||
			Validator.isNull(appServerVersion)) {

			return null;
		}

		return new File(
			appServerParentDir, appServerName + "-" + appServerVersion);
	}

	public File getAppServerLibGlobalDir() {
		if (_appServerLibGlobalDir != null) {
			return _appServerLibGlobalDir;
		}

		return getAppServerDir("libGlobalDirName");
	}

	public File getAppServerParentDir() {
		return _appServerParentDir;
	}

	public File getAppServerPortalDir() {
		if (_appServerPortalDir != null) {
			return _appServerPortalDir;
		}

		return getAppServerDir("portalDirName");
	}

	public String getAppServerProperty(String key) {
		return getAppServerProperty(getAppServerType(), key);
	}

	public String getAppServerProperty(String appServerType, String key) {
		if (Validator.isNull(appServerType)) {
			return null;
		}

		Map<String, Object> appServerProperties =
			(Map<String, Object>)_appServers.getProperty(appServerType);

		String value = String.valueOf(appServerProperties.get(key));

		if (Validator.isNull(value)) {
			throw new GradleException(
				"Unable to get property " + key + " for " + appServerType);
		}

		return value;
	}

	public ConfigObject getAppServers() {
		return _appServers;
	}

	public String getAppServerType() {
		return _appServerType;
	}

	public File getDeployDir() {
		if (_deployDir != null) {
			return _deployDir;
		}

		File appServerParentDir = getAppServerParentDir();

		if (appServerParentDir == null) {
			return null;
		}

		return new File(appServerParentDir, "deploy");
	}

	public int getJmxRemotePort() {
		return _jmxRemotePort;
	}

	public File getLiferayHome() {
		if (_liferayHome != null) {
			return _liferayHome;
		}

		File appServerParentDir = getAppServerParentDir();

		if (appServerParentDir == null) {
			return null;
		}

		return appServerParentDir;
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

	public void setJmxRemotePort(int jmxRemotePort) {
		_jmxRemotePort = jmxRemotePort;
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

	protected File getAppServerDir(String dirNameKey) {
		File appServerDir = getAppServerDir();

		if (appServerDir == null) {
			return null;
		}

		String dirName = getAppServerProperty(dirNameKey);

		return new File(appServerDir, dirName);
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
	private int _jmxRemotePort;
	private File _liferayHome;
	private String _portalVersion;
	private File _tmpDir;

}