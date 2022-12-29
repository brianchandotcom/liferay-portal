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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.service.base.UserNotificationEventServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class UserNotificationEventServiceImpl
	extends UserNotificationEventServiceBaseImpl {

	@Override
	public UserNotificationEvent getUserNotificationEvent(
			long userNotificationEventId)
		throws PortalException {

		UserNotificationEvent userNotificationEvent =
			userNotificationEventLocalService.getUserNotificationEvent(
				userNotificationEventId);

		UserPermissionUtil.check(
			getPermissionChecker(), userNotificationEvent.getUserId(),
			ActionKeys.VIEW);

		return userNotificationEvent;
	}

	@Override
	public UserNotificationEvent updateUserNotificationEvent(
			String uuid, long companyId, boolean archive)
		throws PortalException {

		UserNotificationEvent userNotificationEvent =
			userNotificationEventLocalService.
				getUserNotificationEventByUuidAndCompanyId(uuid, companyId);

		UserPermissionUtil.check(
			getPermissionChecker(), userNotificationEvent.getUserId(),
			ActionKeys.UPDATE);

		return userNotificationEventLocalService.updateUserNotificationEvent(
			uuid, companyId, archive);
	}

}