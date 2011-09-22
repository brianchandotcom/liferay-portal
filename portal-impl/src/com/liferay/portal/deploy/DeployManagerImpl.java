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

package com.liferay.portal.deploy;

import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.DeployManager;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.plugin.PluginPackageUtil;

import java.io.File;

import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class DeployManagerImpl implements DeployManager {

	public void deploy(File file) throws Exception {
		deploy(file, null);
	}

	public void deploy(File file, String context) throws Exception {
		List<AutoDeployListener> autoDeployListeners =
			GlobalStartupAction.getAutoDeployListeners();

		for (AutoDeployListener autoDeployListener : autoDeployListeners) {
			autoDeployListener.deploy(file, context);
		}
	}

	public String getDeployDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
	}

	public PluginPackage getInstalledPluginPackage(String context) {
		return PluginPackageUtil.getInstalledPluginPackage(context);
	}

	public List<PluginPackage> getInstalledPluginPackages() {
		return PluginPackageUtil.getInstalledPluginPackages();
	}

	public boolean isDeployed(String context) {
		return PluginPackageUtil.isInstalled(context);
	}

	public void redeploy(String context) throws Exception {
		if (ServerDetector.isJetty()) {
			DeployUtil.redeployJetty(context);
		}
		else if (ServerDetector.isTomcat()) {
			DeployUtil.redeployTomcat(context);
		}
	}

	public void undeploy(String context) throws Exception {
		File deployDir = new File(getDeployDir(), context);

		if (!deployDir.exists()) {
			deployDir = new File(getDeployDir(), context + ".war");
		}

		DeployUtil.undeploy(ServerDetector.getServerId(), deployDir);
	}

}