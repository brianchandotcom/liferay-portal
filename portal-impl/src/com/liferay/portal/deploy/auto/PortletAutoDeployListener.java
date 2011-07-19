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

import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class PortletAutoDeployListener extends BaseAutoDeployListener {

	public PortletAutoDeployListener() {
		_deployer = new PortletAutoDeployer();
	}

	public void deploy(File file) throws AutoDeployException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		AutoDeployer deployer = null;

		if (DeployManagerUtil.isPortletPlugin(file)) {
			deployer = _deployer;
		}
		else if (DeployManagerUtil.isMvcPortletPlugin(file)) {
			deployer = getMvcDeployer();
		}
		else if (DeployManagerUtil.isPhpPortletPlugin(file)) {
			deployer = getPhpDeployer();
		}
		else if (DeployManagerUtil.isWaiPortletPlugin(file)) {
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

		deployer.autoDeploy(file.getName());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Portlets for " + file.getPath() + " copied successfully. " +
					"Deployment will start in a few seconds.");
		}
	}

	protected AutoDeployer getMvcDeployer() {
		if (_mvcDeployer == null) {
			_mvcDeployer = new MVCPortletAutoDeployer();
		}

		return _mvcDeployer;
	}

	protected AutoDeployer getPhpDeployer() throws AutoDeployException {
		if (_phpDeployer == null) {
			_phpDeployer = new PHPPortletAutoDeployer();
		}

		return _phpDeployer;
	}

	protected AutoDeployer getWaiDeployer() throws AutoDeployException {
		if (_waiDeployer == null) {
			_waiDeployer = new WAIAutoDeployer();
		}

		return _waiDeployer;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletAutoDeployListener.class);

	private AutoDeployer _deployer;
	private MVCPortletAutoDeployer _mvcDeployer;
	private PHPPortletAutoDeployer _phpDeployer;
	private WAIAutoDeployer _waiDeployer;

}