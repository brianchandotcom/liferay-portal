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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public class PHPPortletAutoDeployer extends PortletAutoDeployer {

	public PHPPortletAutoDeployer() throws AutoDeployException {
		try {
			String[] phpJars = {"resin.jar", "script-10.jar"};

			for (int i = 0; i < phpJars.length; i++) {
				String phpJar = phpJars[i];

				jars.add(downloadJar(phpJar));
			}

			addRequiredJar(jars, "portals-bridges.jar");
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		Map<String, String> filterMap = new HashMap<>();

		String pluginName = displayName;

		if (pluginPackage != null) {
			pluginName = pluginPackage.getName();
		}

		filterMap.put(
			"portlet_class", "com.liferay.util.bridges.php.PHPPortlet");
		filterMap.put("portlet_name", pluginName);
		filterMap.put("portlet_title", pluginName);
		filterMap.put("restore_current_view", "false");
		filterMap.put("friendly_url_mapper_class", "");
		filterMap.put("friendly_url_mapping", "");
		filterMap.put("friendly_url_routes", "");
		filterMap.put("init_param_name_0", "view-uri");
		filterMap.put("init_param_value_0", "/index.php");
		filterMap.put("init_param_name_1", "add-portlet-params");
		filterMap.put("init_param_value_1", "true");

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml("portlet.xml", srcFile + "/WEB-INF", filterMap);
	}

	@Override
	public String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		String extraContent = super.getExtraContent(
			webXmlVersion, srcFile, displayName);

		return extraContent + _PHP_SECURITY_CONSTRAINT;
	}

	private static final String _PHP_SECURITY_CONSTRAINT =
		"<security-constraint>\n" +
			"<web-resource-collection>\n" +
			"<web-resource-name>PHP File</web-resource-name>\n" +
			"<url-pattern>*.php</url-pattern>\n" +
			"<url-pattern>*.php3</url-pattern>\n" +
			"<url-pattern>*.php4</url-pattern>\n" +
			"<url-pattern>*.php5</url-pattern>\n" +
			"<url-pattern>*.php6</url-pattern>\n" +
			"<url-pattern>*.php7</url-pattern>\n" +
			"<url-pattern>*.phps</url-pattern>\n" +
			"<url-pattern>*.phtml</url-pattern>\n" +
			"<url-pattern>*.pcgi</url-pattern>\n" +
			"<url-pattern>*.pcgi3</url-pattern>\n" +
			"<url-pattern>*.pcgi4</url-pattern>\n" +
			"<url-pattern>*.pcgi5</url-pattern>\n" +
			"<url-pattern>*.pcgi6</url-pattern>\n" +
			"<url-pattern>*.pcgi7</url-pattern>\n" +
			"<url-pattern>*.inc</url-pattern>\n" +
			"<url-pattern>*.htaccess</url-pattern>\n" +
			"</web-resource-collection>\n" +
			"<auth-constraint>\n" +
			"<role-name>nobody</role-name>\n" +
			"</auth-constraint>\n" +
			"</security-constraint>";

}