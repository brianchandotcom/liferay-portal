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

package com.liferay.portal.tools.db.upgrade.client;

import com.liferay.portal.tools.db.upgrade.client.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author David Truong
 */
public class AppServer {

	public static AppServer getJBossEAPAppServer() {
		return new AppServer(
			"../../jboss-eap-6.4.0", "", "/modules/com/liferay/portal/main",
			"/standalone/deployments/ROOT.war");
	}

	public static AppServer getJOnASAppServer() {
		return new AppServer(
			"../../jonas-5.2.3", "", "/lib/ext", "/deploy/liferay-portal");
	}

	public static AppServer getResinAppServer() {
		return new AppServer(
			"../../resin-4.0.44", "", "/ext-lib", "/webapps/ROOT");
	}

	public static AppServer getTCServerAppServer() {
		return new AppServer(
			"../../tc-server-2.9.11", "", "/liferay/lib",
			"/liferay/webapps/ROOT");
	}

	public static AppServer getTomcatAppServer() {
		return new AppServer(
			"../../tomcat-8.0.32", "/bin", "/lib", "/webapps/ROOT");
	}

	public static AppServer getWebLogicAppServer() {
		return new AppServer(
			"../../weblogic-12.1.3", "", "/domains/liferay/lib",
			"/domains/liferay/autodeploy/ROOT");
	}

	public static AppServer getWebSphereAppServer() {
		return new AppServer(
			"../../websphere-8.5.5.0", "", "/lib/ext",
			"/profiles/liferay/installedApps/liferay-cell/liferay-portal.ear" +
				"/liferay-portal.war");
	}

	public static AppServer getWildFlyAppServer() {
		String extraLibDirPrefix = "/modules/system/layers/base/";

		return new AppServer(
			"../../wildfly-10.0.0",
			extraLibDirPrefix + "javax/mail," + extraLibDirPrefix +
				"javax/persistence," + extraLibDirPrefix + "javax/servlet",
			"/modules/com/liferay/portal/main",
			"/standalone/deployments/ROOT.war");
	}

	public AppServer(
		String dirName, String extraLibDirNames, String globalLibDirName,
		String portalDirName) {

		_dir = new File(dirName);

		if (extraLibDirNames != null) {
			for (String extraLibDir : extraLibDirNames.split(",")) {
				_extraLibDirs.add(new File(dirName, extraLibDir));
			}
		}

		_globalLibDir = new File(dirName, globalLibDirName);
		_portalDir = new File(dirName, portalDirName);
	}

	public File getDir() {
		return _dir;
	}

	public String getExtraLibDirNames() {
		return StringUtil.join(_extraLibDirs, ',');
	}

	public List<File> getExtraLibDirs() {
		return _extraLibDirs;
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

	public void setDirName(String dirName) {
		_dir = new File(dirName);
	}

	public void setExtraLibDirNames(String extraLibDirNames) {
		if (extraLibDirNames != null) {
			for (String extraLibDirName : extraLibDirNames.split(",")) {
				_extraLibDirs.add(new File(extraLibDirNames, extraLibDirName));
			}
		}
	}

	public void setGlobalLibDirName(String globalLibDirName) {
		_globalLibDir = new File(_dir, globalLibDirName);
	}

	public void setPortalDirName(String portalDirName) {
		_portalDir = new File(_dir, portalDirName);
	}

	private File _dir;
	private final List<File> _extraLibDirs = new ArrayList<>();
	private File _globalLibDir;
	private File _portalDir;

}