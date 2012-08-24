/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.service.SocialRequestLocalServiceUtil;

/**
 * @author Shinn Lok
 */
public class SocialRequestPermission {

	public static void check(
			PermissionChecker permissionChecker, long requestId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, requestId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long requestId,
			String actionId)
		throws PortalException, SystemException {

		if (permissionChecker.isOmniadmin()) {
			return true;
		}

		if (actionId.equals(ActionKeys.UPDATE)) {
			SocialRequest request =
				SocialRequestLocalServiceUtil.getSocialRequest(requestId);

			if (permissionChecker.getUserId() == request.getReceiverUserId()) {
				return true;
			}
		}

		return false;
	}

}