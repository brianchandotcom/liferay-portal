/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;

import java.io.File;

import java.util.List;
import java.util.Properties;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
@DoPrivileged
public class DeployManagerImpl implements DeployManager {

	public void deploy(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		List<AutoDeployListener> autoDeployListeners =
			GlobalStartupAction.getAutoDeployListeners();

		for (AutoDeployListener autoDeployListener : autoDeployListeners) {
			autoDeployListener.deploy(autoDeploymentContext);
		}
	}

	public String getDeployDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
	}

	public String getInstalledDir() throws Exception {
		if (ServerDetector.isGlassfish()) {
			File file = new File(
				System.getProperty("com.sun.aas.instanceRoot"), "autodeploy");

			return file.getAbsolutePath();
		}

		boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

		try {
			PortalSecurityManagerThreadLocal.setEnabled(false);

			return DeployUtil.getAutoDeployDestDir();
		}
		finally {
			PortalSecurityManagerThreadLocal.setEnabled(enabled);
		}
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

	public PluginPackage readPluginPackageProperties(
		String displayName, Properties properties) {

		return PluginPackageUtil.readPluginPackageProperties(
			displayName, properties);
	}

	public PluginPackage readPluginPackageXml(String xml) throws Exception {
		return PluginPackageUtil.readPluginPackageXml(xml);
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

		DeployUtil.undeploy(ServerDetector.getServerId(), deployDir);
	}

}