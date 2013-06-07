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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JournalStructurePermission {

	public static void check(
			PermissionChecker permissionChecker, JournalStructure structure,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, structure, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId,
			String structureId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, structureId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, JournalStructure structure,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				structure.getCompanyId(), JournalStructure.class.getName(),
				structure.getId(), structure.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			structure.getGroupId(), JournalStructure.class.getName(),
			structure.getId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId,
			String structureId, String actionId)
		throws PortalException, SystemException {

		JournalStructure structure =
			JournalStructureLocalServiceUtil.getStructure(
				groupId, structureId, true);

		return contains(permissionChecker, structure, actionId);
	}

}