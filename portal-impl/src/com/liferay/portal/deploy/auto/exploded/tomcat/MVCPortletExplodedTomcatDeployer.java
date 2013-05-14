/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.auto.exploded.tomcat;

import com.liferay.portal.deploy.auto.MVCPortletAutoDeployer;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class MVCPortletExplodedTomcatDeployer
	extends MVCPortletAutoDeployer implements ExplodedTomcatDeployer {

	@Override
	public void explodedTomcatDeploy(
			File contextFile, File webAppDir, PluginPackage pluginPackage)
		throws AutoDeployException {

		try {
			deployDirectory(
				webAppDir, getDisplayName(contextFile), false, pluginPackage);
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

}