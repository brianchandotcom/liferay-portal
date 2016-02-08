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

package com.liferay.portal.background.task.model.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.background.task.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BackgroundTaskImpl extends BackgroundTaskBaseImpl {

	@Override
	public void addAttachment(long userId, String fileName, File file)
		throws PortalException {

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, getBackgroundTaskId(), fileName, file);
	}

	@Override
	public void addAttachment(
			long userId, String fileName, InputStream inputStream)
		throws PortalException {

		BackgroundTaskLocalServiceUtil.addBackgroundTaskAttachment(
			userId, getBackgroundTaskId(), fileName, inputStream);
	}

	@Override
	public Folder addAttachmentsFolder() throws PortalException {
		if (_attachmentsFolderId !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return PortletFileRepositoryUtil.getPortletFolder(
				_attachmentsFolderId);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			getGroupId(), PortletKeys.BACKGROUND_TASK, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getBackgroundTaskId()), serviceContext);

		_attachmentsFolderId = folder.getFolderId();

		return folder;
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() throws PortalException {
		return getAttachmentsFileEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries(int start, int end)
		throws PortalException {

		List<FileEntry> fileEntries = new ArrayList<>();

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			fileEntries = PortletFileRepositoryUtil.getPortletFileEntries(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_APPROVED, start, end, null);
		}

		return fileEntries;
	}

	@Override
	public int getAttachmentsFileEntriesCount() throws PortalException {
		int attachmentsFileEntriesCount = 0;

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			attachmentsFileEntriesCount =
				PortletFileRepositoryUtil.getPortletFileEntriesCount(
					getGroupId(), attachmentsFolderId,
					WorkflowConstants.STATUS_APPROVED);
		}

		return attachmentsFileEntriesCount;
	}

	@Override
	public long getAttachmentsFolderId() {
		if (_attachmentsFolderId !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return _attachmentsFolderId;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				getGroupId(), PortletKeys.BACKGROUND_TASK);

		if (repository == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(getBackgroundTaskId()));

			_attachmentsFolderId = folder.getFolderId();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No portlet repository for background task " +
						getBackgroundTaskId(),
					e);
			}
		}

		return _attachmentsFolderId;
	}

	@Override
	public String getStatusLabel() {
		return BackgroundTaskConstants.getStatusLabel(getStatus());
	}

	@Override
	public boolean isInProgress() {
		if (getStatus() == BackgroundTaskConstants.STATUS_IN_PROGRESS) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskImpl.class);

	private long _attachmentsFolderId;

}