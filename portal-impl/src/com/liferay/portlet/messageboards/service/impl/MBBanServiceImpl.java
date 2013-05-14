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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.service.base.MBBanServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class MBBanServiceImpl extends MBBanServiceBaseImpl {

	@Override
	public MBBan addBan(long banUserId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		PermissionChecker permissionChecker = getPermissionChecker();

		MBPermission.check(
			permissionChecker, serviceContext.getScopeGroupId(),
			ActionKeys.BAN_USER);

		User banUser = userPersistence.findByPrimaryKey(banUserId);

		boolean groupAdmin = false;

		try {
			groupAdmin = PortalUtil.isGroupAdmin(
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
		throws PortalException, SystemException {

		MBPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.BAN_USER);

		mbBanLocalService.deleteBan(banUserId, serviceContext);
	}

}