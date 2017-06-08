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

package com.liferay.upgrade.tool;

import java.io.File;

/**
 * @author David Truong
 */
public class AppServer {

	public static AppServer getJBossEAP() {
		return new AppServer(
			"../jboss-eap-6.4.0", "/modules/com/liferay/portal/main",
			"/standalone/deployments/ROOT.war"
		);
	}

	public static AppServer getJOnAS() {
		return new AppServer(
			"../jonas-5.2.3", "/lib/ext", "/deploy/liferay-portal"
		);
	}

	public static AppServer getResin() {
		return new AppServer(
			"./resin-4.0.44", "/ext-lib", "/webapps/ROOT"
		);
	}

	public static AppServer getTCServer() {
		return new AppServer(
			"../tc-server-2.9.11", "/liferay/lib", "/liferay/webapps/ROOT"
		);
	}

	public static AppServer getTomcat() {
		return new AppServer(
			"../tomcat-8.0.32", "/lib", "/webapps/ROOT"
		);
	}

	public static AppServer getWeblogic() {
		return new AppServer(
			"../weblogic-12.1.3", "/domains/liferay/lib",
			"/domains/liferay/autodeploy/ROOT"
		);
	}

	public static AppServer getWebsphere() {
		return new AppServer(
			"../websphere-8.5.5.0", "/lib/ext",
			"/profiles/liferay/installedApps/liferay-cell/liferay-portal.ear/" +
				"liferay-portal.war"
		);
	}

	public static AppServer getWildfly() {
		return new AppServer(
			"../wildfly-10.0.0", "/modules/com/liferay/portal/main",
			"/standalone/deployments/ROOT.war"
		);
	}

	public AppServer(String dir, String globalLibDir, String portalDir) {
		_dir = new File(dir);
		_globalLibDir = new File(dir, globalLibDir);
		_portalDir = new File(dir, portalDir);
	}

	public File getDir() {
		return _dir;
	}

	public File getGlobalLibDir() {
		return _globalLibDir;
	}

	public File getPortalClassesDir() {
		return new File(_portalDir, "/WEB-INF/classes");
	}

	public File getPortalDir() {
		return _portalDir;
	}

	public File getPortalLibDir() {
		return new File(_portalDir, "/WEB-INF/lib");
	}

	public void setDir(String dir) {
		_dir = new File(dir);
	}

	public void setGlobalLibDir(String globalLibDir) {
		_globalLibDir = new File(_dir, globalLibDir);
	}

	public void setPortalDir(String portalDir) {
		_portalDir = new File(_dir, portalDir);
	}

	private File _dir;
	private File _globalLibDir;
	private File _portalDir;

}