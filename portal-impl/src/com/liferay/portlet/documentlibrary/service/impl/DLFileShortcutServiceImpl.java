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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.FileShortcutPermissionException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.service.base.DLFileShortcutServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutServiceImpl extends DLFileShortcutServiceBaseImpl {

	@Override
	public DLFileShortcut addFileShortcut(
			long groupId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_SHORTCUT);

		try {
			DLFileEntryPermission.check(
				getPermissionChecker(), toFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException();
		}

		return dlFileShortcutLocalService.addFileShortcut(
			getUserId(), groupId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.DELETE);

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException, SystemException {

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.VIEW);

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	@Override
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.UPDATE);

		try {
			DLFileEntryPermission.check(
				getPermissionChecker(), toFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException();
		}

		return dlFileShortcutLocalService.updateFileShortcut(
			getUserId(), fileShortcutId, folderId, toFileEntryId,
			serviceContext);
	}

}