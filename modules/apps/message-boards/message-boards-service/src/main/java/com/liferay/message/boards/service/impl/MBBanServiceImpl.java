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

package com.liferay.message.boards.service.impl;

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBBan;
import com.liferay.message.boards.service.base.MBBanServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=mb",
		"json.web.service.context.path=MBBan"
	},
	service = AopService.class
)
public class MBBanServiceImpl extends MBBanServiceBaseImpl {

	@Override
	public MBBan addBan(long banUserId, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.BAN_USER);

		User banUser = _userLocalService.getUser(banUserId);

		boolean groupAdmin = false;

		try {
			groupAdmin = _portal.isGroupAdmin(
				banUser, serviceContext.getScopeGroupId());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		if (groupAdmin) {
			throw new PrincipalException();
		}

		return mbBanLocalService.addBan(getUserId(), banUserId, serviceContext);
	}

	@Override
	public void deleteBan(long banUserId, ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.BAN_USER);

		mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

	@Reference
	private Portal _portal;

	@Reference(target = "(resource.name=" + MBConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private UserLocalService _userLocalService;

}