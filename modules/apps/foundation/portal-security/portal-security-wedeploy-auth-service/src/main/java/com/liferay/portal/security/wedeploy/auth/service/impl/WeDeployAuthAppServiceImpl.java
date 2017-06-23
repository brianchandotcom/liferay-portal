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

package com.liferay.portal.security.wedeploy.auth.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployAuthActionKeys;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppServiceBaseImpl;
import com.liferay.portal.security.wedeploy.auth.service.permission.WeDeployAuthAppPermission;
import com.liferay.portal.security.wedeploy.auth.service.permission.WeDeployAuthPermission;

/**
 * @author Supritha Sundaram
 */
public class WeDeployAuthAppServiceImpl extends WeDeployAuthAppServiceBaseImpl {

	@Override
	public WeDeployAuthApp addWeDeployAuthApp(
			String name, String redirectURI, ServiceContext serviceContext)
		throws PortalException {

		WeDeployAuthPermission.check(
			getPermissionChecker(), WeDeployAuthActionKeys.ADD_APP);

		return weDeployAuthAppLocalService.addWeDeployAuthApp(
			getUserId(), name, redirectURI, serviceContext);
	}

	@Override
	public WeDeployAuthApp deleteWeDeployAuthApp(long weDeployAuthAppId)
		throws PortalException {

		WeDeployAuthAppPermission.check(
			getPermissionChecker(), weDeployAuthAppId, ActionKeys.DELETE);

		return weDeployAuthAppLocalService.deleteWeDeployAuthApp(
			weDeployAuthAppId);
	}

}