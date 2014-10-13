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

package com.liferay.portal.deploy.sandbox;

import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployException;
import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployListener;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.tools.deploy.ThemeDeployer;
import com.liferay.portal.util.PortalUtil;

import java.io.File;

import java.util.ArrayList;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class ThemeSandboxDeployListener
	extends ThemeDeployer implements SandboxDeployListener {

	public ThemeSandboxDeployListener() {
		_sandboxHandler = new ThemeSandboxHandler(this);

		appServerType = ServerDetector.getServerId();

		String portalWebDir = PortalUtil.getPortalWebDir();

		themeTaglibDTD = portalWebDir + "/WEB-INF/tld/liferay-theme.tld";
		utilTaglibDTD = portalWebDir + "/WEB-INF/tld/liferay-util.tld";

		jars = new ArrayList<String>();

		String portalLibDir = PortalUtil.getPortalLibDir();

		jars.add(portalLibDir + "/commons-logging.jar");
		jars.add(portalLibDir + "/log4j.jar");
		jars.add(portalLibDir + "/util-java.jar");
		jars.add(portalLibDir + "/util-taglib.jar");
	}

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		copyTomcatContextXml(srcFile);
	}

	@Override
	public void deploy(File dir) throws SandboxDeployException {
		_sandboxHandler.deploy(dir);
	}

	@Override
	public String getDisplayName(File srcFile) {
		String displayName = super.getDisplayName(srcFile);

		return _sandboxHandler.getDisplayName(displayName);
	}

	@Override
	public void undeploy(File dir) throws SandboxDeployException {
		_sandboxHandler.undeploy(dir);
	}

	private final SandboxHandler _sandboxHandler;

}