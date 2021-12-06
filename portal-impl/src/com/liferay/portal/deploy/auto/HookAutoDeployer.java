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

import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.tools.deploy.BaseAutoDeployer;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class HookAutoDeployer extends BaseAutoDeployer implements AutoDeployer {

	public HookAutoDeployer() {
		try {
			appServerType = ServerDetector.getServerId();
			jbossPrefix = PropsValues.AUTO_DEPLOY_JBOSS_PREFIX;
			tomcatLibDir = PropsValues.AUTO_DEPLOY_TOMCAT_LIB_DIR;
			wildflyPrefix = PropsValues.AUTO_DEPLOY_WILDFLY_PREFIX;

			checkArguments();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	@Override
	public String getPluginType() {
		return Plugin.TYPE_HOOK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HookAutoDeployer.class);

}