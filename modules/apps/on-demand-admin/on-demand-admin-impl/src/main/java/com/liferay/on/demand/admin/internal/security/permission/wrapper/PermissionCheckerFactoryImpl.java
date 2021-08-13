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

package com.liferay.on.demand.admin.internal.security.permission.wrapper;

import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.wrapper.PermissionCheckerWrapperFactory;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "service.ranking:Integer=100",
	service = PermissionCheckerWrapperFactory.class
)
public class PermissionCheckerFactoryImpl
	implements PermissionCheckerWrapperFactory {

	@Override
	public PermissionChecker wrapPermissionChecker(
		PermissionChecker permissionChecker) {

		return new OnDemandAdminPermissionCheckerWrapper(
			permissionChecker, _onDemandAdminManager, _userLocalService);
	}

	@Reference
	private OnDemandAdminManager _onDemandAdminManager;

	@Reference
	private UserLocalService _userLocalService;

}