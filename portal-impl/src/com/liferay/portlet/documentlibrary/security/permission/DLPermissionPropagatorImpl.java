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

package com.liferay.portlet.documentlibrary.security.permission;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.BasePermissionPropagator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Jonathan McCann
 */
public class DLPermissionPropagatorImpl extends BasePermissionPropagator {

	@Override
	public void propagateRolePermissions(
			ActionRequest actionRequest, String className, String primKey,
			long[] roleIds)
		throws PortalException, SystemException {

		if (!className.equals(DLFolder.class.getName())) {
			return;
		}

		long folderId = GetterUtil.getLong(primKey);

		Folder folder = DLAppServiceUtil.getFolder(folderId);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				folder.getGroupId(), folderId, WorkflowConstants.STATUS_ANY,
				true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Object folderAndFileEntryAndFileShortcut :
				foldersAndFileEntriesAndFileShortcuts) {

			if (folderAndFileEntryAndFileShortcut instanceof FileEntry) {
				FileEntry fileEntry =
					(FileEntry)folderAndFileEntryAndFileShortcut;

				for (long roleId : roleIds) {
					propagateRolePermissions(
						actionRequest, roleId, DLFolder.class.getName(),
						folderId, DLFileEntry.class.getName(),
						fileEntry.getFileEntryId());
				}
			}
			else if (folderAndFileEntryAndFileShortcut instanceof Folder) {
				Folder subFolder = (Folder)folderAndFileEntryAndFileShortcut;

				for (long roleId : roleIds) {
					propagateRolePermissions(
						actionRequest, roleId, DLFolder.class.getName(),
						folderId, DLFolder.class.getName(),
						subFolder.getFolderId());
				}

				propagateRolePermissions(
					actionRequest, className,
					String.valueOf(subFolder.getFolderId()), roleIds);
			}
			else if (folderAndFileEntryAndFileShortcut
						instanceof DLFileShortcut) {

				DLFileShortcut dlFileShorcut =
					(DLFileShortcut)folderAndFileEntryAndFileShortcut;

				for (long roleId : roleIds) {
					propagateRolePermissions(
						actionRequest, roleId, DLFolder.class.getName(),
						folderId, DLFileShortcut.class.getName(),
						dlFileShorcut.getFileShortcutId());
				}
			}
		}
	}

}