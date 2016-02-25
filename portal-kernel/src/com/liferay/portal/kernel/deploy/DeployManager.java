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
public interface DeployManager {

	public void deploy(AutoDeploymentContext autoDeploymentContext)
		throws Exception;

	public String getDeployDir() throws Exception;

	public String getInstalledDir() throws Exception;

	public PluginPackage getInstalledPluginPackage(String context);

	public List<PluginPackage> getInstalledPluginPackages();

	public List<String[]> getLevelsRequiredDeploymentContexts();

	public List<String[]> getLevelsRequiredDeploymentWARFileNames();

	public boolean isDeployed(String context);

	public boolean isRequiredDeploymentContext(String context);

	public PluginPackage readPluginPackageProperties(
		String displayName, Properties properties);

	public PluginPackage readPluginPackageXml(String xml) throws Exception;

	public void redeploy(String context) throws Exception;

	public void undeploy(String context) throws Exception;

}