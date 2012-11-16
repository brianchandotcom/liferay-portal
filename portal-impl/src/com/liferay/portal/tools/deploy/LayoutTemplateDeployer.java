/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.deploy.auto.AutoDeployer;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.util.InitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutTemplateDeployer extends BaseDeployer {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		List<String> wars = new ArrayList<String>();
		List<String> jars = new ArrayList<String>();

		for (String arg : args) {
			if (arg.endsWith(".war")) {
				wars.add(arg);
			}
			else if (arg.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		new LayoutTemplateDeployer(wars, jars);
	}

	public LayoutTemplateDeployer() {
	}

	public LayoutTemplateDeployer(List<String> wars, List<String> jars) {
		super(wars, jars);
	}

	@Override
	public AutoDeployer copy() {
		LayoutTemplateDeployer deployer = new LayoutTemplateDeployer();

		deployer.setAppServerType(appServerType);
		deployer.setAuiTaglibDTD(auiTaglibDTD);
		deployer.setBaseDir(baseDir);
		deployer.setDestDir(destDir);
		deployer.setFilePattern(filePattern);
		deployer.setJars(jars);
		deployer.setJbossPrefix(jbossPrefix);
		deployer.setPortletExtTaglibDTD(portletExtTaglibDTD);
		deployer.setPortletTaglibDTD(portletTaglibDTD);
		deployer.setSecurityTaglibDTD(securityTaglibDTD);
		deployer.setThemeTaglibDTD(themeTaglibDTD);
		deployer.setTomcatLibDir(tomcatLibDir);
		deployer.setUiTaglibDTD(uiTaglibDTD);
		deployer.setUnpackWar(unpackWar);
		deployer.setUtilTaglibDTD(utilTaglibDTD);
		deployer.setWars(wars);

		return deployer;
	}

	@Override
	public String getPluginType() {
		return Plugin.TYPE_LAYOUT_TEMPLATE;
	}

}