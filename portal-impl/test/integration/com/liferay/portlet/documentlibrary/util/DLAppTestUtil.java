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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;

/**
 * @author Alexander Chow
 */
public abstract class DLAppTestUtil {

	public static DLFileEntryType addDLFileEntryType(
			long groupId, long ddmStructureId)
		throws Exception {

		long userId = TestPropsValues.getUserId();
		String name = ServiceTestUtil.randomString();
		String description = ServiceTestUtil.randomString();
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addDLFileEntryType(
			userId, groupId, name, description, new long[] {ddmStructureId},
			serviceContext);
	}

	public static DLFileEntryType addDLFileEntryType(
			long userId, long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws Exception {

		return DLFileEntryTypeLocalServiceUtil.addFileEntryType(
			userId, groupId, name, description, ddmStructureIds,
			serviceContext);
	}

	public static DLFileRank addDLFileRank(long groupId, long fileEntryId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return DLAppLocalServiceUtil.addFileRank(
			groupId, TestPropsValues.getCompanyId(),
			TestPropsValues.getUserId(), fileEntryId, serviceContext);
	}

	public static DLFileShortcut addDLFileShortcut(
			FileEntry fileEntry, long groupId, long folderId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return DLAppServiceUtil.addFileShortcut(
			groupId, folderId, fileEntry.getFileEntryId(), serviceContext);
	}

	public static DLFileShortcut addDLFileShortcut(
			long groupId, FileEntry fileEntry)
		throws Exception {

		return addDLFileShortcut(fileEntry, groupId, fileEntry.getFolderId());
	}

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
			long userId, long groupId, long folderId, String sourceFileName,
			String mimeType, String title, byte[] bytes, int workflowAction)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addFileEntry(
			userId, groupId, folderId, sourceFileName, mimeType, title, bytes,
			workflowAction, serviceContext);
	}

	public static FileEntry addFileEntry(
			long userId, long groupId, long folderId, String sourceFileName,
			String mimeType, String title, byte[] bytes, int workflowAction,
			ServiceContext serviceContext)
		throws Exception {

		if ((bytes == null) && Validator.isNotNull(sourceFileName)) {
			bytes = _CONTENT.getBytes();
		}

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(workflowAction);

		return DLAppLocalServiceUtil.addFileEntry(
			userId, groupId, folderId, sourceFileName, mimeType, title,
			StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName)
		throws Exception {

		return addFileEntry(groupId, folderId, sourceFileName, sourceFileName);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, title, null,
			WorkflowConstants.ACTION_PUBLISH);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String title,
			boolean approved)
		throws Exception {

		int workflowAction = WorkflowConstants.ACTION_SAVE_DRAFT;

		if (approved) {
			workflowAction = WorkflowConstants.ACTION_PUBLISH;
		}

		return addFileEntry(
			groupId, folderId, sourceFileName, title, null, workflowAction);
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

		return addFileEntry(
			groupId, folderId, sourceFileName, ContentTypes.TEXT_PLAIN, title,
			bytes, workflowAction);
	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String mimeType,
			String title)
		throws Exception {

		return addFileEntry(
			groupId, folderId, sourceFileName, mimeType, title, null,
			WorkflowConstants.ACTION_PUBLISH);

	}

	public static FileEntry addFileEntry(
			long groupId, long folderId, String sourceFileName, String mimeType,
			String title, byte[] bytes, int workflowAction)
		throws Exception {

		return addFileEntry(
			TestPropsValues.getUserId(), groupId, folderId, sourceFileName,
			mimeType, title, bytes, workflowAction);
	}

	public static FileEntry addFileEntry(
			long folderId, String sourceFileName, String title,
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		int workflowAction = WorkflowConstants.ACTION_SAVE_DRAFT;

		if (approved) {
			workflowAction = WorkflowConstants.ACTION_PUBLISH;
		}

		return addFileEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			folderId, sourceFileName, ContentTypes.TEXT_PLAIN, title, null,
			workflowAction, serviceContext);
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

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		return addFolder(parentFolderId, name, deleteExisting, serviceContext);
	}

	public static Folder addFolder(
			long parentFolderId, String name, boolean deleteExisting,
			ServiceContext serviceContext)
		throws Exception {

		String description = StringPool.BLANK;

		if (deleteExisting) {
			try {
				DLAppServiceUtil.deleteFolder(
					serviceContext.getScopeGroupId(), parentFolderId, name);
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLAppServiceUtil.addFolder(
			serviceContext.getScopeGroupId(), parentFolderId, name, description,
			serviceContext);
	}

	public static Folder addFolder(
			long parentFolderId, String name, ServiceContext serviceContext)
		throws Exception {

		return addFolder(parentFolderId, name, false, serviceContext);
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

		return updateFileEntry(
			groupId, fileEntryId, sourceFileName, ContentTypes.TEXT_PLAIN,
			title, majorVersion, new ServiceContext());
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName,
			String mimeType, String title, boolean majorVersion,
			ServiceContext serviceContext)
		throws Exception {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = null;

		if (Validator.isNotNull(sourceFileName)) {
			String newContent = _CONTENT + "\n" + System.currentTimeMillis();

			bytes = newContent.getBytes();
		}

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, bytes, serviceContext);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

}