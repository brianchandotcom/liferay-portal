/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getHostName() {
		return GradleUtil.toString(_hostName);
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

	public File getModuleFrameworkBaseDir() {
		File dir = getLiferayHome();

		if (dir != null) {
			dir = new File(dir, "osgi");
		}

		return dir;
	}

	public int getPortNumber() {
		return GradleUtil.toInteger(_portNumber);
	}

	public boolean isOverwriteCopyTestModules() {
		return _overwriteCopyTestModules;
	}

	public void setCheckPath(Object checkPath) {
		_checkPath = checkPath;
	}

	public void setDir(Object dir) {
		_dir = dir;
	}

	public void setHostName(Object hostName) {
		_hostName = hostName;
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

	public void setOverwriteCopyTestModules(boolean overwriteCopyTestModules) {
		_overwriteCopyTestModules = overwriteCopyTestModules;
	}

	public void setPortNumber(Object portNumber) {
		_portNumber = portNumber;
	}

	private Object _checkPath = "/web/guest";
	private Object _dir;
	private Object _hostName = "localhost";
	private Object _jmxRemotePort = 8099;
	private Object _liferayHome;
	private Object _managerPassword = "tomcat";
	private Object _managerUserName = "tomcat";
	private boolean _overwriteCopyTestModules = true;
	private Object _portNumber = 8080;
	private final Project _project;

}