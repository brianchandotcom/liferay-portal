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

package com.liferay.portlet.social.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.social.model.SocialActivitySet;

/**
 * @author Zsolt Berentey
 */
public class SocialActivityPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		if (permissionChecker.isGroupAdmin(groupId) ||
			permissionChecker.isGroupOwner(groupId)) {

			return true;
		}

		if (permissionChecker.hasPermission(
				groupId, PortletKeys.SOCIAL_ACTIVITY, 0, actionId)) {

			return true;
		}

		return false;
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SocialActivitySet activitySet,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				activitySet.getCompanyId(), SocialActivitySet.class.getName(),
				activitySet.getActivitySetId(), activitySet.getUserId(),
				actionId)) {

			return true;
		}

		if (permissionChecker.hasPermission(
				activitySet.getGroupId(), SocialActivitySet.class.getName(),
				activitySet.getActivitySetId(), actionId)) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				activitySet.getCompanyId(), activitySet.getClassName(),
				activitySet.getClassPK(), activitySet.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			activitySet.getGroupId(), activitySet.getClassName(),
			activitySet.getClassPK(), actionId);
	}

}