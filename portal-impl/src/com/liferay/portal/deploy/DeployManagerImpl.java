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

package com.liferay.portal.deploy;

import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.DeployManager;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.plugin.PluginPackageUtil;

import java.io.File;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class DeployManagerImpl implements DeployManager {

	@Override
	public void deploy(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		AutoDeployDir.deploy(
			autoDeploymentContext,
			GlobalStartupAction.getAutoDeployListeners(false));
	}

	@Override
	public String getDeployDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
	}

	@Override
	public String getInstalledDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
	}

	@Override
	public PluginPackage getInstalledPluginPackage(String context) {
		return PluginPackageUtil.getInstalledPluginPackage(context);
	}

	@Override
	public List<PluginPackage> getInstalledPluginPackages() {
		return PluginPackageUtil.getInstalledPluginPackages();
	}

	@Override
	public List<String[]> getLevelsRequiredDeploymentContexts() {
		return Collections.emptyList();
	}

	@Override
	public List<String[]> getLevelsRequiredDeploymentWARFileNames() {
		return Collections.emptyList();
	}

	@Override
	public boolean isDeployed(String context) {
		return PluginPackageUtil.isInstalled(context);
	}

	@Override
	public boolean isRequiredDeploymentContext(String context) {
		return false;
	}

	@Override
	public PluginPackage readPluginPackageProperties(
		String displayName, Properties properties) {

		return PluginPackageUtil.readPluginPackageProperties(
			displayName, properties);
	}

	@Override
	public PluginPackage readPluginPackageXml(String xml) throws Exception {
		return PluginPackageUtil.readPluginPackageXml(xml);
	}

	@Override
	public void redeploy(String context) throws Exception {
		if (ServerDetector.isTomcat()) {
			DeployUtil.redeployTomcat(context);
		}
	}

	@Override
	public void undeploy(String context) throws Exception {
		File deployDir = new File(getDeployDir(), context);

		DeployUtil.undeploy(ServerDetector.getServerId(), deployDir);
	}

}