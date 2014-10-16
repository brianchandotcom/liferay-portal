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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;

/**
 * @author Jorge Ferrer
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portlet.journal.model.JournalFolder"
	}
)
public class JournalPermission implements ResourcePermissionChecker {

	public static final String RESOURCE_NAME = "com.liferay.portlet.journal";

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		Group group = GroupLocalServiceUtil.fetchGroup(classPK);

		if (group == null) {
			JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
				classPK);

			return JournalFolderPermission.contains(
				permissionChecker, folder, actionId);
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, group.getGroupId(), RESOURCE_NAME,
			group.getGroupId(), PortletKeys.JOURNAL, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(
			classPK, RESOURCE_NAME, group.getGroupId(), actionId);
	}

	@Override
	public Boolean checkResource(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return contains(permissionChecker, classPK, actionId);
	}

}