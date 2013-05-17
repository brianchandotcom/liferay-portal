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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.tools.deploy.BaseDeployer;

/**
 * @author Miguel Pastor
 */
public class PortletModuleAutoDeployer extends BaseDeployer {

	@Override
	public int deployFile(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		_log.info(
			"This is a portlet OSGi bundle. We need to copy the " +
				"web.xml and liferay-web.xml files");

		return AutoDeployer.CODE_DEFAULT;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletModuleAutoDeployer.class);

}