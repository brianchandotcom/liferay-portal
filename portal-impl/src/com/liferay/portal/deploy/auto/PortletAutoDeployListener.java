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

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Miguel Pastor
 * @author Manuel de la Peña
 */
public class PortletAutoDeployListener extends BaseAutoDeployListener {

	@Override
	protected AutoDeployer buildAutoDeployer() throws AutoDeployException {
		AutoDeployer autoDeployer = null;

		if (_portletDeployer) {
			autoDeployer = new PortletAutoDeployer();
		}
		else if (_mvcDeployer) {
			autoDeployer = new MVCPortletAutoDeployer();
		}
		else if (_waiDeployer) {
			if (_log.isInfoEnabled()) {
				_log.info("Deploying package as a web application");
			}

			autoDeployer = new WAIAutoDeployer();
		}

		if (autoDeployer == null) {
			throw new AutoDeployException("Unable to find an auto deployer");
		}

		if (_log.isDebugEnabled()) {
			Class<?> clazz = autoDeployer.getClass();

			_log.debug("Using deployer " + clazz.getName());
		}

		return new ThreadSafeAutoDeployer(autoDeployer);
	}

	@Override
	protected String getPluginPathInfoMessage(File file) {
		return "Copying portlets for " + file.getPath();
	}

	@Override
	protected String getSuccessMessage(File file) {
		return "Portlets for " + file.getPath() + " copied successfully";
	}

	@Override
	protected boolean isDeployable(File file) throws AutoDeployException {
		PluginAutoDeployListenerHelper pluginAutoDeployListenerHelper =
			new PluginAutoDeployListenerHelper(file);

		if (pluginAutoDeployListenerHelper.isPortletPlugin()) {
			_portletDeployer = true;

			return true;
		}

		if (pluginAutoDeployListenerHelper.isMatchingFile("index_mvc.jsp")) {
			_mvcDeployer = true;

			return true;
		}

		String fileName = file.getName();

		if (!pluginAutoDeployListenerHelper.isExtPlugin() &&
			!pluginAutoDeployListenerHelper.isHookPlugin() &&
			!pluginAutoDeployListenerHelper.isMatchingFile(
				"WEB-INF/liferay-layout-templates.xml") &&
			!pluginAutoDeployListenerHelper.isThemePlugin() &&
			!pluginAutoDeployListenerHelper.isWebPlugin() &&
			fileName.endsWith(".war")) {

			_waiDeployer = true;

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAutoDeployListener.class);

	private boolean _mvcDeployer;
	private boolean _portletDeployer;
	private boolean _waiDeployer;

}