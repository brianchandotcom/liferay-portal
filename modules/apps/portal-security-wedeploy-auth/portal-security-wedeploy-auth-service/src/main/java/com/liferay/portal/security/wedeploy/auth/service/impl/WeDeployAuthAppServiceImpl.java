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
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployAuthActionKeys;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployConstants;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppServiceBaseImpl;

/**
 * @author Supritha Sundaram
 */
public class WeDeployAuthAppServiceImpl extends WeDeployAuthAppServiceBaseImpl {

	@Override
	public WeDeployAuthApp addWeDeployAuthApp(
			String name, String redirectURI, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), 0, WeDeployAuthActionKeys.ADD_APP);

		return weDeployAuthAppLocalService.addWeDeployAuthApp(
			getUserId(), name, redirectURI, serviceContext);
	}

	@Override
	public WeDeployAuthApp deleteWeDeployAuthApp(long weDeployAuthAppId)
		throws PortalException {

		_weDeployAuthAppModelResourcePermission.check(
			getPermissionChecker(), weDeployAuthAppId, ActionKeys.DELETE);

		return weDeployAuthAppLocalService.deleteWeDeployAuthApp(
			weDeployAuthAppId);
	}

	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				WeDeployAuthAppServiceImpl.class, "_portletResourcePermission",
				WeDeployConstants.RESOURCE_NAME);
	private static volatile ModelResourcePermission<WeDeployAuthApp>
		_weDeployAuthAppModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				WeDeployAuthAppServiceImpl.class,
				"_weDeployAuthAppModelResourcePermission",
				WeDeployAuthApp.class);

}