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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolDependencies;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeDeployer extends BaseDeployer {

	public static void main(String[] args) {
		ToolDependencies.wireDeployers();

		List<String> wars = new ArrayList<>();
		List<String> jars = new ArrayList<>();

		for (String arg : args) {
			if (arg.endsWith(".war")) {
				wars.add(arg);
			}
			else if (arg.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		new ThemeDeployer(wars, jars);
	}

	public ThemeDeployer() {
	}

	public ThemeDeployer(List<String> wars, List<String> jars) {
		super(wars, jars);
	}

	@Override
	public void checkArguments() {
		super.checkArguments();

		if (Validator.isNull(themeTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.theme.taglib.dtd is not set");
		}

		if (Validator.isNull(utilTaglibDTD)) {
			throw new IllegalArgumentException(
				"The system property deployer.util.taglib.dtd is not set");
		}
	}

	@Override
	public String getExtraFiltersContent(double webXmlVersion, File srcFile)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		String extraFiltersContent = super.getExtraFiltersContent(
			webXmlVersion, srcFile);

		sb.append(extraFiltersContent);

		// Ignore filters

		sb.append(getIgnoreFiltersContent(srcFile));

		// Speed filters

		sb.append(getSpeedFiltersContent(srcFile));

		return sb.toString();
	}

	@Override
	public String getPluginType() {
		return Plugin.TYPE_THEME;
	}

	@Override
	public Map<String, String> processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		Map<String, String> filterMap = super.processPluginPackageProperties(
			srcFile, displayName, pluginPackage);

		if (filterMap == null) {
			return null;
		}

		String moduleArtifactId = filterMap.get("module_artifact_id");

		int pos = moduleArtifactId.indexOf("-theme");

		String themeId = moduleArtifactId.substring(0, pos);

		filterMap.put("theme_id", themeId);

		String themeName = filterMap.get("plugin_name");

		filterMap.put("theme_name", StringUtil.stripCDATA(themeName));

		String liferayVersions = filterMap.get("liferay_versions");

		filterMap.put(
			"theme_versions",
			StringUtil.replace(liferayVersions, "liferay-version", "version"));

		copyDependencyXml(
			"liferay-look-and-feel.xml", srcFile + "/WEB-INF", filterMap, true);

		return filterMap;
	}

}