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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.exception.FileShortcutPermissionException;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.base.DLFileShortcutServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutServiceImpl extends DLFileShortcutServiceBaseImpl {

	@Override
	public DLFileShortcut addFileShortcut(
			long groupId, long repositoryId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		ModelResourcePermissionHelper.check(
			_folderModelResourcePermission, getPermissionChecker(), groupId,
			folderId, ActionKeys.ADD_SHORTCUT);

		try {
			_fileEntryModelResourcePermission.check(
				getPermissionChecker(), toFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException(pe);
		}

		return dlFileShortcutLocalService.addFileShortcut(
			getUserId(), groupId, repositoryId, folderId, toFileEntryId,
			serviceContext);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_fileShortcutModelResourcePermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.DELETE);

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		_fileShortcutModelResourcePermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.VIEW);

		return dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	@Override
	public DLFileShortcut updateFileShortcut(
			long fileShortcutId, long repositoryId, long folderId,
			long toFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		_fileShortcutModelResourcePermission.check(
			getPermissionChecker(), fileShortcutId, ActionKeys.UPDATE);

		try {
			_fileEntryModelResourcePermission.check(
				getPermissionChecker(), toFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException(pe);
		}

		return dlFileShortcutLocalService.updateFileShortcut(
			getUserId(), fileShortcutId, repositoryId, folderId, toFileEntryId,
			serviceContext);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		try {
			_fileEntryModelResourcePermission.check(
				getPermissionChecker(), oldToFileEntryId, ActionKeys.VIEW);

			_fileEntryModelResourcePermission.check(
				getPermissionChecker(), newToFileEntryId, ActionKeys.VIEW);
		}
		catch (PrincipalException pe) {
			throw new FileShortcutPermissionException(pe);
		}

		dlFileShortcutLocalService.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	private static volatile ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DLFileShortcutServiceImpl.class,
				"_fileEntryModelResourcePermission", FileEntry.class);
	private static volatile ModelResourcePermission<FileShortcut>
		_fileShortcutModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DLFileShortcutServiceImpl.class,
				"_fileShortcutModelResourcePermission", FileShortcut.class);
	private static volatile ModelResourcePermission<Folder>
		_folderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DLFileShortcutServiceImpl.class,
				"_folderModelResourcePermission", Folder.class);

}