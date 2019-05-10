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

package com.liferay.portal.kernel.deploy;

import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Deployer {

	public void addExtJar(List<String> jars, String resource) throws Exception;

	public void addRequiredJar(List<String> jars, String resource)
		throws Exception;

	public void checkArguments();

	public void copyDependencyXml(String fileName, String targetDir)
		throws Exception;

	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap)
		throws Exception;

	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap,
			boolean overwrite)
		throws Exception;

	public void copyJars(File srcFile, PluginPackage pluginPackage)
		throws Exception;

	public void copyProperties(File srcFile, PluginPackage pluginPackage)
		throws Exception;

	public void copyTlds(File srcFile, PluginPackage pluginPackage)
		throws Exception;

	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception;

	public Map<String, String> processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception;

	public PluginPackage readPluginPackage(File file);

	public void setAppServerType(String appServerType);

	public void setAuiTaglibDTD(String auiTaglibDTD);

	public void setBaseDir(String baseDir);

	public void setDestDir(String destDir);

	public void setFilePattern(String filePattern);

	public void setJars(List<String> jars);

	public void setJbossPrefix(String jbossPrefix);

	public void setPortletExtTaglibDTD(String portletExtTaglibDTD);

	public void setPortletTaglibDTD(String portletTaglibDTD);

	public void setSecurityTaglibDTD(String securityTaglibDTD);

	public void setThemeTaglibDTD(String themeTaglibDTD);

	public void setTomcatLibDir(String tomcatLibDir);

	public void setUiTaglibDTD(String uiTaglibDTD);

	public void setUnpackWar(boolean unpackWar);

	public void setUtilTaglibDTD(String utilTaglibDTD);

	public void setWars(List<String> wars);

	public void setWildflyPrefix(String wildflyPrefix);

	public void updateWebXml(
			File webXml, File srcFile, String displayName,
			PluginPackage pluginPackage)
		throws Exception;

	public String wrapCDATA(String string);

}