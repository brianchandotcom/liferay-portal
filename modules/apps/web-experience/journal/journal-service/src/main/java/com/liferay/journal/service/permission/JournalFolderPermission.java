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

package com.liferay.journal.service.permission;

import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan Fernández
 * @author Zsolt Berentey
 * @deprecated As of 4.0.0, with no direct replacement
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFolder",
	service = BaseModelPermissionChecker.class
)
@Deprecated
public class JournalFolderPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException {

		_journalFolderModelResourcePermission.check(
			permissionChecker, folder, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_journalFolderModelResourcePermission, permissionChecker, groupId,
			folderId, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, JournalFolder folder,
			String actionId)
		throws PortalException {

		return _journalFolderModelResourcePermission.contains(
			permissionChecker, folder, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long folderId,
			String actionId)
		throws PortalException {

		return ModelResourcePermissionHelper.contains(
			_journalFolderModelResourcePermission, permissionChecker, groupId,
			folderId, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_journalFolderModelResourcePermission, permissionChecker, groupId,
			primaryKey, actionId);
	}

	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {
	}

	@Reference(
		target = "(model.class.name=com.liferay.journal.model.JournalFolder)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<JournalFolder> modelResourcePermission) {

		_journalFolderModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<JournalFolder>
		_journalFolderModelResourcePermission;

}