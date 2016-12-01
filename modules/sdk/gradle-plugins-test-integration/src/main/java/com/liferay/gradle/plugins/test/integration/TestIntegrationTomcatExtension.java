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

package com.liferay.gradle.plugins.test.integration;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class TestIntegrationTomcatExtension {

	public TestIntegrationTomcatExtension(Project project) {
		_project = project;
	}

	public String getCheckPath() {
		return GradleUtil.toString(_checkPath);
	}

	public File getDir() {
		return GradleUtil.toFile(_project, _dir);
	}

	public int getJmxRemotePort() {
		return GradleUtil.toInteger(_jmxRemotePort);
	}

	public File getLiferayHome() {
		return GradleUtil.toFile(_project, _liferayHome);
	}

	public String getManagerPassword() {
		return GradleUtil.toString(_managerPassword);
	}

	public String getManagerUserName() {
		return GradleUtil.toString(_managerUserName);
	}

	public int getPortNumber() {
		return GradleUtil.toInteger(_portNumber);
	}

	public void setCheckPath(Object checkPath) {
		_checkPath = checkPath;
	}

	public void setDir(Object dir) {
		_dir = dir;
	}

	public void setJmxRemotePort(Object jmxRemotePort) {
		_jmxRemotePort = jmxRemotePort;
	}

	public void setLiferayHome(Object liferayHome) {
		_liferayHome = liferayHome;
	}

	public void setManagerPassword(Object managerPassword) {
		_managerPassword = managerPassword;
	}

	public void setManagerUserName(Object managerUserName) {
		_managerUserName = managerUserName;
	}

	public void setPortNumber(Object portNumber) {
		_portNumber = portNumber;
	}

	private Object _checkPath = "/web/guest";
	private Object _dir;
	private Object _jmxRemotePort = 8099;
	private Object _liferayHome;
	private Object _managerPassword = "tomcat";
	private Object _managerUserName = "tomcat";
	private Object _portNumber = 8080;
	private final Project _project;

}