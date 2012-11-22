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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;

/**
 * @author Alexander Chow
 */
public abstract class DLAppTestUtil {

	public static FileEntry addFileEntry(
			long groupId, long parentFolderId, boolean rootFolder,
			String fileName)
		throws Exception {

		return addFileEntry(
			groupId, parentFolderId, rootFolder, fileName, fileName);
	}

	public static FileEntry addFileEntry(
			long groupId, long parentFolderId, boolean rootFolder,
			String sourceFileName, String title)
		throws Exception {

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			folderId = parentFolderId;
		}

		return addFileEntry(groupId, folderId, sourceFileName, title);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String fileName)
		throws Exception {

		return addFileEntry(groupId, folderId, fileName, fileName);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title)
		throws Exception {

		return addFileEntry(groupId, folderId, sourceFileName, title, null);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			byte[] bytes)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, title, bytes,
			WorkflowConstants.ACTION_PUBLISH);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			byte[] bytes, int workflowAction)
		throws Exception {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		if ((bytes == null) && Validator.isNotNull(sourceFileName)) {
			bytes = _CONTENT.getBytes();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setWorkflowAction(workflowAction);

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), groupId, folderId, sourceFileName,
			ContentTypes.TEXT_PLAIN, title, description, changeLog, bytes,
			serviceContext);
	}

	public static DLFileRank addDLFileRank(long groupId, long fileEntryId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return DLAppLocalServiceUtil.addFileRank(
			groupId, TestPropsValues.getCompanyId(),
			TestPropsValues.getUserId(), fileEntryId, serviceContext);
	}

	public static DLFileShortcut addDLFileShortcut(
			long groupId, FileEntry fileEntry)
		throws Exception {

		return addDLFileShortcut(fileEntry, groupId, fileEntry.getFolderId());
	}

	public static DLFileShortcut addDLFileShortcut(
			FileEntry fileEntry, long groupId, long folderId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return DLAppServiceUtil.addFileShortcut(
			groupId, folderId, fileEntry.getFileEntryId(), serviceContext);
	}

	public static Folder addFolder(
			long groupId, Folder parentFolder, boolean rootFolder, String name)
		throws Exception {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			parentFolderId = parentFolder.getFolderId();
		}

		return addFolder(groupId, parentFolderId, name);
	}

	public static Folder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		return addFolder(groupId, parentFolderId, name, false);
	}

	public static Folder addFolder(
			long groupId, long parentFolderId, String name,
			boolean deleteExisting)
		throws Exception {

		String description = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		if (deleteExisting) {
			try {
				DLAppServiceUtil.deleteFolder(groupId, parentFolderId, name);
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLAppServiceUtil.addFolder(
			groupId, parentFolderId, name, description, serviceContext);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName, String title)
		throws Exception {

		return updateFileEntry(
			groupId, fileEntryId, sourceFileName, title, false);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName, String title,
			boolean majorVersion)
		throws Exception {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = null;

		if (Validator.isNotNull(sourceFileName)) {
			String newContent = _CONTENT + "\n" + System.currentTimeMillis();

			bytes = newContent.getBytes();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, sourceFileName, ContentTypes.TEXT_PLAIN, title,
			description, changeLog, majorVersion, bytes, serviceContext);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

}