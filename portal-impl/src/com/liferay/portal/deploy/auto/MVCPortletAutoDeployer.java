/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.File;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class MVCPortletAutoDeployer extends PortletAutoDeployer {

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		String pluginName = displayName;

		if (pluginPackage != null) {
			pluginName = pluginPackage.getName();
		}

		Map<String, String> filterMap = HashMapBuilder.put(
			"friendly_url_mapper_class", ""
		).put(
			"friendly_url_mapping", ""
		).put(
			"friendly_url_routes", ""
		).put(
			"init_param_name_0", "view-jsp"
		).put(
			"init_param_value_0", "/index_mvc.jsp"
		).put(
			"portlet_class",
			"com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet"
		).put(
			"portlet_name", pluginName
		).put(
			"portlet_title", pluginName
		).put(
			"restore_current_view", "false"
		).build();

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml("portlet.xml", srcFile + "/WEB-INF", filterMap);
	}

}