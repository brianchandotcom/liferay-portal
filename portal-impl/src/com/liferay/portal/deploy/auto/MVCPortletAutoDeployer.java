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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class MVCPortletAutoDeployer extends PortletAutoDeployer {

	@Override
	public AutoDeployer copy() {
		MVCPortletAutoDeployer deployer = new MVCPortletAutoDeployer();

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
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		Map<String, String> filterMap = new HashMap<String, String>();

		String pluginName = displayName;

		if (pluginPackage != null) {
			pluginName = pluginPackage.getName();
		}

		filterMap.put(
			"portlet_class", "com.liferay.util.bridges.mvc.MVCPortlet");
		filterMap.put("portlet_name", pluginName);
		filterMap.put("portlet_title", pluginName);
		filterMap.put("restore_current_view", "false");
		filterMap.put("friendly_url_mapper_class", "");
		filterMap.put("friendly_url_mapping", "");
		filterMap.put("friendly_url_routes", "");
		filterMap.put("init_param_name_0", "view-jsp");
		filterMap.put("init_param_value_0", "/index_mvc.jsp");

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml("portlet.xml", srcFile + "/WEB-INF", filterMap);
	}

}