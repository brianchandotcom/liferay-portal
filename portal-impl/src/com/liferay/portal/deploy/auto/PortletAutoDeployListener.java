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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.Portal;

import java.io.File;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletAutoDeployListener extends BaseAutoDeployListener {

	public PortletAutoDeployListener() {
		_autoDeployer = new PortletAutoDeployer();
	}

	public void deploy(File file, String context) throws AutoDeployException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		AutoDeployer deployer = null;

		if (isMatchingFile(
				file, "WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD)) {

			deployer = _autoDeployer;
		}
		else if (isMatchingFile(file, "index_mvc.jsp")) {
			deployer = getMvcDeployer();
		}
		else if (isMatchingFile(file, "index.php")) {
			deployer = getPhpDeployer();
		}
		else if (!isExtPlugin(file) && !isHookPlugin(file) &&
				 !isMatchingFile(
					file, "WEB-INF/liferay-layout-templates.xml") &&
				 !isThemePlugin(file) && !isWebPlugin(file) &&
				 file.getName().endsWith(".war")) {

			if (_log.isInfoEnabled()) {
				_log.info("Deploying package as a web application");
			}

			deployer = getWaiDeployer();
		}
		else {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Copying portlets for " + file.getPath());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Using deployer " + deployer.getClass().getName());
		}

		deployer.autoDeploy(file, context);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Portlets for " + file.getPath() + " copied successfully. " +
					"Deployment will start in a few seconds.");
		}
	}

	protected AutoDeployer getMvcDeployer() {
		if (_mvcPortletAutoDeployer == null) {
			_mvcPortletAutoDeployer = new MVCPortletAutoDeployer();
		}

		return _mvcPortletAutoDeployer;
	}

	protected AutoDeployer getPhpDeployer() throws AutoDeployException {
		if (_phpPortletAutoDeployer == null) {
			_phpPortletAutoDeployer = new PHPPortletAutoDeployer();
		}

		return _phpPortletAutoDeployer;
	}

	protected AutoDeployer getWaiDeployer() throws AutoDeployException {
		if (_waiAutoDeployer == null) {
			_waiAutoDeployer = new WAIAutoDeployer();
		}

		return _waiAutoDeployer;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletAutoDeployListener.class);

	private AutoDeployer _autoDeployer;
	private MVCPortletAutoDeployer _mvcPortletAutoDeployer;
	private PHPPortletAutoDeployer _phpPortletAutoDeployer;
	private WAIAutoDeployer _waiAutoDeployer;

}