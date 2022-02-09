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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.deploy.BaseAutoDeployer;

import java.io.File;

import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class ThemeAutoDeployer
	extends BaseAutoDeployer implements AutoDeployer {

	public ThemeAutoDeployer() {
		super(Plugin.TYPE_THEME);

		try {
			themeTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-theme.tld");

			if (Validator.isNull(themeTaglibDTD)) {
				throw new IllegalArgumentException(
					"The system property deployer.theme.taglib.dtd is not set");
			}

			utilTaglibDTD = DeployUtil.getResourcePath(
				tempDirPaths, "liferay-util.tld");

			if (Validator.isNull(utilTaglibDTD)) {
				throw new IllegalArgumentException(
					"The system property deployer.util.taglib.dtd is not set");
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public int autoDeploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		if (file.isDirectory()) {
			try {
				if (_log.isInfoEnabled()) {
					_log.info("Modifying themes for " + file.getPath());
				}

				deployDirectory(
					file, autoDeploymentContext.getContext(), false,
					autoDeploymentContext.getPluginPackage());

				if (_log.isInfoEnabled()) {
					_log.info(
						"Themes for " + file.getPath() +
							" modified successfully");
				}

				return AutoDeployer.CODE_DEFAULT;
			}
			catch (Exception exception) {
				throw new AutoDeployException(exception);
			}
		}
		else {
			return super.autoDeploy(autoDeploymentContext);
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

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeAutoDeployer.class);

}