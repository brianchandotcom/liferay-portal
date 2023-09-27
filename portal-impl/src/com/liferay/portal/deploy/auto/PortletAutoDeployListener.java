/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		AutoDeployer autoDeployer = new PortletAutoDeployer();

		if (_mvcDeployer) {
			autoDeployer = new MVCPortletAutoDeployer();
		}

		if (_log.isDebugEnabled()) {
			Class<?> clazz = autoDeployer.getClass();

			_log.debug("Using deployer " + clazz.getName());
		}

		return autoDeployer;
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
			return true;
		}

		if (pluginAutoDeployListenerHelper.isMatchingFile("index_mvc.jsp")) {
			_mvcDeployer = true;

			return true;
		}

		String fileName = file.getName();

		if (!pluginAutoDeployListenerHelper.isHookPlugin() &&
			!pluginAutoDeployListenerHelper.isMatchingFile(
				"WEB-INF/liferay-layout-templates.xml") &&
			!pluginAutoDeployListenerHelper.isThemePlugin() &&
			!pluginAutoDeployListenerHelper.isWebPlugin() &&
			fileName.endsWith(".war")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletAutoDeployListener.class);

	private boolean _mvcDeployer;

}