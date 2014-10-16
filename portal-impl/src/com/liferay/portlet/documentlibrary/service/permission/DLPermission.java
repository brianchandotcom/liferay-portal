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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourcePermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

/**
 * @author Jorge Ferrer
 */
@OSGiBeanProperties(
	property = {
		"model.class.name=com.liferay.portal.kernel.repository.model.Folder"
	}
)
public class DLPermission implements ResourcePermissionChecker {

	public static final String RESOURCE_NAME =
		"com.liferay.portlet.documentlibrary";

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
			Folder folder = DLAppLocalServiceUtil.getFolder(classPK);

			return DLFolderPermission.contains(
				permissionChecker, folder, actionId);
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, classPK, RESOURCE_NAME, classPK,
			PortletKeys.DOCUMENT_LIBRARY, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		try {
			int count =
				ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
					permissionChecker.getCompanyId(), RESOURCE_NAME,
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(classPK));

			if (count == 0) {
				ResourceLocalServiceUtil.addResources(
					permissionChecker.getCompanyId(), classPK, 0, RESOURCE_NAME,
					classPK, false, true, true);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return permissionChecker.hasPermission(
			classPK, RESOURCE_NAME, classPK, actionId);
	}

	@Override
	public Boolean checkResource(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return contains(permissionChecker, classPK, actionId);
	}

	private static final Log _log = LogFactoryUtil.getLog(DLPermission.class);

}