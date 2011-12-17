/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Jorge Ferrer
 * @author Mate Thurzo
 */
public class MBPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		String primKey = String.valueOf(groupId);

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				if (ResourcePermissionLocalServiceUtil.
						getResourcePermissionsCount(
							group.getCompanyId(), _CLASS_NAME,
							ResourceConstants.SCOPE_INDIVIDUAL, primKey) == 0) {

					throw new NoSuchResourceException();
				}
			}
			else {
				ResourceLocalServiceUtil.getResource(
					group.getCompanyId(), _CLASS_NAME,
					ResourceConstants.SCOPE_INDIVIDUAL, primKey);
			}
		}
		catch (NoSuchResourceException nsre) {
			boolean addGroupPermission = true;
			boolean addGuestPermission = true;
			boolean addPortletActions = false;

			ResourceLocalServiceUtil.addResources(
				group.getCompanyId(), groupId, groupId, _CLASS_NAME, primKey,
				addPortletActions, addGroupPermission, addGuestPermission);
		}

		return permissionChecker.hasPermission(
			groupId, _CLASS_NAME, groupId, actionId);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portlet.messageboards";

}