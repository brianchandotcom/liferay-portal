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

package com.liferay.portal.deploy.auto.exploded.tomcat;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

/**
 * @author Olaf Fricke
 * @author Brian Wing Shun Chan
 */
public class LayoutTemplateExplodedTomcatListener
	extends BaseExplodedTomcatListener {

	public LayoutTemplateExplodedTomcatListener() {
		_deployer = new LayoutTemplateExplodedTomcatDeployer();
	}

	@Override
	protected void deploy(File file) throws AutoDeployException {

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		File docBaseDir = getDocBaseDir(
			file, "WEB-INF/liferay-layout-templates.xml");

		if (docBaseDir == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Modifying layout templates for " + file.getPath());
		}

		_deployer.explodedTomcatDeploy(file, docBaseDir, null);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Layout templates for " + file.getPath() +
					" modified successfully");
		}

		copyContextFile(file);
	}

	private static Log _log = LogFactoryUtil.getLog(
		LayoutTemplateExplodedTomcatListener.class);

	private ExplodedTomcatDeployer _deployer;

}