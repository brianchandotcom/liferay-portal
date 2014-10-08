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

package com.liferay.portal.deploy.auto.exploded.tomcat;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.Portal;

import java.io.File;

/**
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class PortletExplodedTomcatListener extends BaseExplodedTomcatListener {

	public PortletExplodedTomcatListener() {
		_deployer = new PortletExplodedTomcatDeployer();
	}

	@Override
	protected int deploy(File file) throws AutoDeployException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		ExplodedTomcatDeployer deployer = null;

		File docBaseDir = getDocBaseDir(file, "index.php");

		if (docBaseDir != null) {
			deployer = getPhpDeployer();
		}
		else {
			docBaseDir = getDocBaseDir(
				file, "WEB-INF/" + Portal.PORTLET_XML_FILE_NAME_STANDARD);

			if (docBaseDir != null) {
				deployer = _deployer;
			}
			else {
				return AutoDeployer.CODE_NOT_APPLICABLE;
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Modifying portlets for " + file.getPath());
		}

		deployer.explodedTomcatDeploy(file, docBaseDir, null);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Portlets for " + file.getPath() + " modified successfully");
		}

		copyContextFile(file);

		return AutoDeployer.CODE_DEFAULT;
	}

	protected ExplodedTomcatDeployer getPhpDeployer()
		throws AutoDeployException {

		if (_phpDeployer == null) {
			_phpDeployer = new PHPPortletExplodedTomcatDeployer();
		}

		return _phpDeployer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletExplodedTomcatListener.class);

	private final ExplodedTomcatDeployer _deployer;
	private PHPPortletExplodedTomcatDeployer _phpDeployer;

}