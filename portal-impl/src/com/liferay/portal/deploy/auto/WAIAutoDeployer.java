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
import com.liferay.portal.kernel.portlet.DefaultFriendlyURLMapper;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.bridges.wai.WAIPortlet;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Jorge Ferrer
 * @author Connor McKay
 */
public class WAIAutoDeployer extends PortletAutoDeployer {

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		super.copyXmls(srcFile, displayName, pluginPackage);

		// The default context.xml file for Tomcat causes portlets to be run
		// from the temp directory, which prevents some applications from saving
		// their settings. There is no easy way to prevent this file from being
		// copied, so it must be deleted afterwards.

		FileUtil.delete(srcFile + "/META-INF/context.xml");

		String portletName = displayName;

		if (pluginPackage != null) {
			portletName = pluginPackage.getName();
		}

		Map<String, String> filterMap = new HashMap<>();

		filterMap.put("portlet_name", displayName);
		filterMap.put("portlet_title", portletName);

		if (pluginPackage != null) {
			Properties deploymentSettings =
				pluginPackage.getDeploymentSettings();

			filterMap.put(
				"portlet_class",
				deploymentSettings.getProperty(
					"wai.portlet", WAIPortlet.class.getName()));

			filterMap.put(
				"friendly_url_mapper_class",
				deploymentSettings.getProperty(
					"wai.friendly.url.mapper",
					DefaultFriendlyURLMapper.class.getName()));

			filterMap.put(
				"friendly_url_mapping",
				deploymentSettings.getProperty(
					"wai.friendly.url.mapping", "waiapp"));

			filterMap.put(
				"friendly_url_routes",
				deploymentSettings.getProperty(
					"wai.friendly.url.routes",
					"com/liferay/util/bridges/wai" +
						"/wai-friendly-url-routes.xml"));
		}
		else {
			filterMap.put("portlet_class", WAIPortlet.class.getName());

			filterMap.put(
				"friendly_url_mapper_class",
				DefaultFriendlyURLMapper.class.getName());

			filterMap.put("friendly_url_mapping", "waiapp");

			filterMap.put(
				"friendly_url_routes",
				"com/liferay/util/bridges/wai/wai-friendly-url-routes.xml");
		}

		_setInitParams(filterMap, pluginPackage);

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml("portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml("iframe.jsp", srcFile + "/WEB-INF/jsp/liferay/wai");
	}

	private void _setInitParams(
		Map<String, String> filterMap, PluginPackage pluginPackage) {

		for (int i = 0; i < _INIT_PARAM_NAMES.length; i++) {
			String name = _INIT_PARAM_NAMES[i];

			String value = null;

			if (pluginPackage != null) {
				Properties deploymentSettings =
					pluginPackage.getDeploymentSettings();

				value = deploymentSettings.getProperty(name);
			}

			if (Validator.isNull(value)) {
				value = _INIT_PARAM_DEFAULT_VALUES[i];
			}

			filterMap.put("init_param_name_" + i, name);
			filterMap.put("init_param_value_" + i, value);
		}
	}

	private static final String[] _INIT_PARAM_DEFAULT_VALUES = {"500"};

	private static final String[] _INIT_PARAM_NAMES = {
		"wai.connector.iframe.height.default"
	};

}