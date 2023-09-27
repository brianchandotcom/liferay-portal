/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

}