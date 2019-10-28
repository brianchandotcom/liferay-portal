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