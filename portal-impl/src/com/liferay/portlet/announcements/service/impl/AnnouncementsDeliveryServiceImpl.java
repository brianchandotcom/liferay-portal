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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portlet.announcements.model.AnnouncementsDelivery;
import com.liferay.portlet.announcements.service.base.AnnouncementsDeliveryServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsDeliveryServiceImpl
	extends AnnouncementsDeliveryServiceBaseImpl {

	@Override
	public AnnouncementsDelivery updateDelivery(
			long userId, String type, boolean email, boolean sms,
			boolean website)
		throws PortalException, SystemException {

		if (!PortalPermissionUtil.contains(
				getPermissionChecker(), ActionKeys.ADD_USER) &&
			!UserPermissionUtil.contains(
				getPermissionChecker(), userId, ActionKeys.UPDATE)) {

			throw new PrincipalException();
		}

		return announcementsDeliveryLocalService.updateDelivery(
			userId, type, email, sms, website);
	}

}