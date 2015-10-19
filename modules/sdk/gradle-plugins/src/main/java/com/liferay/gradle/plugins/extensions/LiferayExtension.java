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

import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayExtension {

	public LiferayExtension(Project project) {
		this.project = project;

		_appServers = project.container(
			AppServer.class, new AppServerFactory(project));
	}

	public void appServers(Closure<?> closure) {
		_appServers.configure(closure);
	}

	public AppServer getAppServer() {
		return getAppServer(getAppServerType());
	}

	public AppServer getAppServer(String type) {
		return _appServers.getAt(type);
	}

	public File getAppServerDeployDir() {
		AppServer appServer = getAppServer();

		return appServer.getDeployDir();
	}

	public File getAppServerDir() {
		AppServer appServer = getAppServer();

		return appServer.getDir();
	}

	public File getAppServerLibGlobalDir() {
		AppServer appServer = getAppServer();

		return appServer.getLibGlobalDir();
	}

	public File getAppServerParentDir() {
		return project.file(_appServerParentDir);
	}

	public File getAppServerPortalDir() {
		AppServer appServer = getAppServer();

		return appServer.getPortalDir();
	}

	public NamedDomainObjectContainer<AppServer> getAppServers() {
		return _appServers;
	}

	public String getAppServerType() {
		return _appServerType;
	}

	public File getDeployDir() {
		return project.file(_deployDir);
	}

	public int getJmxRemotePort() {
		Integer jmxRemotePort = GradleUtil.toInteger(_jmxRemotePort);

		if (jmxRemotePort != null) {
			return jmxRemotePort;
		}

		return 0;
	}

	public File getLiferayHome() {
		return project.file(_liferayHome);
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public String getVersionPrefix() {
		String version = getPortalVersion();

		int index = version.indexOf("-");

		if (index != -1) {
			version = version.substring(0, index);
		}

		return version;
	}

	public void setAppServerParentDir(Object appServerParentDir) {
		_appServerParentDir = appServerParentDir;
	}

	public void setAppServerType(String appServerType) {
		_appServerType = appServerType;
	}

	public void setDeployDir(Object deployDir) {
		_deployDir = deployDir;
	}

	public void setJmxRemotePort(Object jmxRemotePort) {
		_jmxRemotePort = jmxRemotePort;
	}

	public void setLiferayHome(Object liferayHome) {
		_liferayHome = liferayHome;
	}

	public void setPortalVersion(String portalVersion) {
		_portalVersion = portalVersion;
	}

	protected final Project project;

	private Object _appServerParentDir;
	private final NamedDomainObjectContainer<AppServer> _appServers;
	private String _appServerType;
	private Object _deployDir;
	private Object _jmxRemotePort;
	private Object _liferayHome;
	private String _portalVersion;

}