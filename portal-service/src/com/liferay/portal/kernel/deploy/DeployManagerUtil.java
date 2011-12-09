/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class DeployManagerUtil {

	public static void deploy(File file) throws Exception {
		getDeployManager().deploy(file);
	}

	public static void deploy(File file, String context) throws Exception {
		getDeployManager().deploy(file, context);
	}

	public static String getDeployDir() throws Exception {
		return getDeployManager().getDeployDir();
	}

	public static DeployManager getDeployManager() {
		return _deployManager;
	}

	public static PluginPackage getInstalledPluginPackage(String context) {
		return getDeployManager().getInstalledPluginPackage(context);
	}

	public static List<PluginPackage> getInstalledPluginPackages() {
		return getDeployManager().getInstalledPluginPackages();
	}

	public static File getUpdateDir() throws Exception {
		return getDeployManager().getUpdateDir();
	}

	public static boolean isDeployed(String context) {
		return getDeployManager().isDeployed(context);
	}

	public static void redeploy(String context) throws Exception {
		getDeployManager().redeploy(context);
	}

	public static void undeploy(String context) throws Exception {
		getDeployManager().undeploy(context);
	}

	public void setDeployManager(DeployManager deployManager) {
		_deployManager = deployManager;
	}

	private static DeployManager _deployManager;

}