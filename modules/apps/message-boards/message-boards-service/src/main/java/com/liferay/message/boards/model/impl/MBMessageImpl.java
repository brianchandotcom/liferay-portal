/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model.impl;

import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageImpl extends MBMessageBaseImpl {

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
			getGroupId(), MBConstants.SERVICE_NAME, serviceContext);

		MBThread thread = getThread();

		Folder threadFolder = thread.addAttachmentsFolder();

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			getUserId(), repository.getRepositoryId(),
			threadFolder.getFolderId(), String.valueOf(getMessageId()),
			serviceContext);

		_attachmentsFolderId = folder.getFolderId();

		return folder;
	}

	@Override
	public String[] getAssetTagNames() {
		return AssetTagLocalServiceUtil.getTagNames(
			MBMessage.class.getName(), getMessageId());
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
				WorkflowConstants.STATUS_APPROVED, start, end,
				new RepositoryModelTitleComparator<>(true));
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
	public FileEntry getAttachmentsFileEntryByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		FileEntry fileEntry =
			PortletFileRepositoryUtil.
				getPortletFileEntryByExternalReferenceCode(
					externalReferenceCode, groupId);

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId == fileEntry.getFolderId()) {
			return fileEntry;
		}

		throw new NoSuchFileEntryException(
			StringBundler.concat(
				"No FileEntry exists with the key {fileEntryId=",
				fileEntry.getFileEntryId(), "}"));
	}

	@Override
	public long getAttachmentsFolderId() throws PortalException {
		if (_attachmentsFolderId !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return _attachmentsFolderId;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				getGroupId(), MBConstants.SERVICE_NAME);

		long threadAttachmentsFolderId = getThreadAttachmentsFolderId();

		if ((repository == null) ||
			(threadAttachmentsFolderId ==
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(), threadAttachmentsFolderId,
				String.valueOf(getMessageId()));

			_attachmentsFolderId = folder.getFolderId();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return _attachmentsFolderId;
	}

	@Override
	public String getBody(boolean translate) {
		String body = null;

		if (translate) {
			body = BBCodeTranslatorUtil.getHTML(getBody());
		}
		else {
			body = getBody();
		}

		return body;
	}

	@Override
	public MBCategory getCategory() throws PortalException {
		return MBCategoryLocalServiceUtil.getCategory(getCategoryId());
	}

	@Override
	public List<FileEntry> getDeletedAttachmentsFileEntries()
		throws PortalException {

		return getDeletedAttachmentsFileEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<FileEntry> getDeletedAttachmentsFileEntries(int start, int end)
		throws PortalException {

		List<FileEntry> fileEntries = new ArrayList<>();

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			fileEntries = PortletFileRepositoryUtil.getPortletFileEntries(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_IN_TRASH, start, end,
				new RepositoryModelTitleComparator<>(true));
		}

		return fileEntries;
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount() throws PortalException {
		int deletedAttachmentsFileEntriesCount = 0;

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			deletedAttachmentsFileEntriesCount =
				PortletFileRepositoryUtil.getPortletFileEntriesCount(
					getGroupId(), attachmentsFolderId,
					WorkflowConstants.STATUS_IN_TRASH);
		}

		return deletedAttachmentsFileEntriesCount;
	}

	@Override
	public MBThread getThread() throws PortalException {
		return MBThreadLocalServiceUtil.getThread(getThreadId());
	}

	@Override
	public long getThreadAttachmentsFolderId() throws PortalException {
		return getThread().getAttachmentsFolderId();
	}

	@Override
	public String getWorkflowClassName() {
		if (isDiscussion()) {
			return "com.liferay.message.boards.model.MBDiscussion";
		}

		return MBMessage.class.getName();
	}

	@Override
	public boolean isDiscussion() {
		if (getCategoryId() == MBCategoryConstants.DISCUSSION_CATEGORY_ID) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isFormatBBCode() {
		String format = getFormat();

		if (format.equals("bbcode")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isReply() {
		return !isRoot();
	}

	@Override
	public boolean isRoot() {
		if (getParentMessageId() ==
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

			return true;
		}

		return false;
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_attachmentsFolderId = attachmentsFolderId;
	}

	private static final Log _log = LogFactoryUtil.getLog(MBMessageImpl.class);

	private long _attachmentsFolderId;

}