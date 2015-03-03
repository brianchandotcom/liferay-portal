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

package com.liferay.portlet.documentlibrary.util.test;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public abstract class DLAppTestUtil {

	public static FileEntry addFileEntryWithWorkflow(
			long userId, long groupId, long folderId, String sourceFileName,
			String title, boolean approved, ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				userId, groupId, folderId, sourceFileName,
				ContentTypes.TEXT_PLAIN, title, StringPool.BLANK,
				StringPool.BLANK, RandomTestUtil.randomBytes(), serviceContext);

			if (approved) {
				return updateStatus(fileEntry, serviceContext);
			}

			return fileEntry;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static void populateNotificationsServiceContext(
			ServiceContext serviceContext, String command)
		throws Exception {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	public static void populateServiceContext(
			ServiceContext serviceContext, long fileEntryTypeId)
		throws Exception {

		if (fileEntryTypeId !=
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) {

			serviceContext.setAttribute("fileEntryTypeId", fileEntryTypeId);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, boolean majorVersion)
		throws Exception {

		return updateFileEntry(
			groupId, fileEntryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), majorVersion, true, true);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName, String title)
		throws Exception {

		return updateFileEntry(
			groupId, fileEntryId, sourceFileName, title, false, false, false);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName, String title,
			boolean majorVersion, boolean workflowEnabled, boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return updateFileEntry(
			groupId, fileEntryId, sourceFileName, ContentTypes.TEXT_PLAIN,
			title, majorVersion, workflowEnabled, approved, serviceContext);
	}

	public static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String sourceFileName,
			String mimeType, String title, boolean majorVersion,
			boolean workflowEnabled, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = null;

		if (Validator.isNotNull(sourceFileName)) {
			String newContent = _CONTENT + "\n" + System.currentTimeMillis();

			bytes = newContent.getBytes();
		}

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		if (workflowEnabled && !approved) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		FileEntry fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, bytes, serviceContext);

		if (workflowEnabled && approved) {
			updateStatus(fileEntry, serviceContext);
		}

		return fileEntry;
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

	public static FileEntry updateFileEntryWithWorkflow(
			long groupId, long fileEntryId, boolean majorVersion,
			boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.UPDATE);
		serviceContext.setLayoutFullURL("http://localhost");

		return updateFileEntry(
			groupId, fileEntryId, RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			majorVersion, true, approved, serviceContext);
	}

	protected static FileEntry updateStatus(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");
		workflowContext.put("event", DLSyncConstants.EVENT_ADD);

		DLFileEntryLocalServiceUtil.updateStatus(
			TestPropsValues.getUserId(),
			fileEntry.getFileVersion().getFileVersionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);

		return DLAppLocalServiceUtil.getFileEntry(fileEntry.getFileEntryId());
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

}