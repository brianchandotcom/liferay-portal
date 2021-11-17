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

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.util.List;
import java.util.Properties;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class DeployManagerUtil {

	public static void deploy(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		_deployManager.deploy(autoDeploymentContext);
	}

	public static String getDeployDir() throws Exception {
		return _deployManager.getDeployDir();
	}

	public static DeployManager getDeployManager() {
		return _deployManager;
	}

	public static String getInstalledDir() throws Exception {
		return _deployManager.getInstalledDir();
	}

	public static PluginPackage getInstalledPluginPackage(String context) {
		return _deployManager.getInstalledPluginPackage(context);
	}

	public static List<PluginPackage> getInstalledPluginPackages() {
		return _deployManager.getInstalledPluginPackages();
	}

	public static List<String[]> getLevelsRequiredDeploymentContexts() {
		return _deployManager.getLevelsRequiredDeploymentContexts();
	}

	public static List<String[]> getLevelsRequiredDeploymentWARFileNames() {
		return _deployManager.getLevelsRequiredDeploymentWARFileNames();
	}

	public static boolean isDeployed(String context) {
		return _deployManager.isDeployed(context);
	}

	public static boolean isRequiredDeploymentContext(String context) {
		return _deployManager.isRequiredDeploymentContext(context);
	}

	public static PluginPackage readPluginPackageProperties(
		String displayName, Properties properties) {

		return _deployManager.readPluginPackageProperties(
			displayName, properties);
	}

	public static PluginPackage readPluginPackageXml(String xml)
		throws Exception {

		return _deployManager.readPluginPackageXml(xml);
	}

	public static void redeploy(String context) throws Exception {
		_deployManager.redeploy(context);
	}

	public static void reset() {
		_deployManager = null;
	}

	public void setDeployManager(DeployManager deployManager) {
		_deployManager = deployManager;
	}

	private static DeployManager _deployManager;

}