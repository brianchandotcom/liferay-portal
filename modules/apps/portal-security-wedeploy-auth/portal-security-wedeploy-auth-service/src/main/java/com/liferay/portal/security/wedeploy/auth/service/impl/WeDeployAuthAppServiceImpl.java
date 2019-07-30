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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployAuthActionKeys;
import com.liferay.portal.security.wedeploy.auth.constants.WeDeployConstants;
import com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp;
import com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Supritha Sundaram
 */
@Component(
	property = {
		"json.web.service.context.name=wedeployauth",
		"json.web.service.context.path=WeDeployAuthApp"
	},
	service = AopService.class
)
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

	@Reference(
		target = "(resource.name=" + WeDeployConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp)"
	)
	private ModelResourcePermission<WeDeployAuthApp>
		_weDeployAuthAppModelResourcePermission;

}