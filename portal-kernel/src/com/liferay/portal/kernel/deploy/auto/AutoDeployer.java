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

package com.liferay.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public interface AutoDeployer extends Closeable {

	public static final int CODE_DEFAULT = 1;

	public static final int CODE_NOT_APPLICABLE = 0;

	public static final int CODE_SKIP_NEWER_VERSION = 2;

	public void addRequiredJar(List<String> jars, String resource)
		throws Exception;

	public int autoDeploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException;

	public void checkArguments();

	@Override
	public default void close() throws IOException {
	}

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

	public void updateWebXml(
			File webXml, File srcFile, String displayName,
			PluginPackage pluginPackage)
		throws Exception;

	public String wrapCDATA(String string);

}