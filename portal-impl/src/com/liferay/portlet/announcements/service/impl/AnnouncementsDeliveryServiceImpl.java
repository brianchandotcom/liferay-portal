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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.announcements.kernel.model.AnnouncementsDelivery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portlet.announcements.service.base.AnnouncementsDeliveryServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsDeliveryServiceImpl
	extends AnnouncementsDeliveryServiceBaseImpl {

	@Override
	public AnnouncementsDelivery updateDelivery(
			long userId, String type, boolean email, boolean sms)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_USER) &&
			!UserPermissionUtil.contains(
				permissionChecker, userId, ActionKeys.UPDATE)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, ActionKeys.ADD_USER, ActionKeys.UPDATE);
		}

		return announcementsDeliveryLocalService.updateDelivery(
			userId, type, email, sms);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateDelivery(long, String, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public AnnouncementsDelivery updateDelivery(
			long userId, String type, boolean email, boolean sms,
			boolean website)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!PortalPermissionUtil.contains(
				permissionChecker, ActionKeys.ADD_USER) &&
			!UserPermissionUtil.contains(
				permissionChecker, userId, ActionKeys.UPDATE)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, ActionKeys.ADD_USER, ActionKeys.UPDATE);
		}

		return announcementsDeliveryLocalService.updateDelivery(
			userId, type, email, sms, website);
	}

}