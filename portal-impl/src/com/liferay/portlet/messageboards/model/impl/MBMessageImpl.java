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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class MBMessageImpl extends MBMessageBaseImpl {

	public MBMessageImpl() {
	}

	public String[] getAssetTagNames() throws SystemException {
		return AssetTagLocalServiceUtil.getTagNames(
			MBMessage.class.getName(), getMessageId());
	}

	public String getAttachmentsDir() {
		if (_attachmentDirs == null) {
			_attachmentDirs =
				getThreadAttachmentsDir() + StringPool.FORWARD_SLASH +
					getMessageId();
		}

		return _attachmentDirs;
	}

	public String[] getAttachmentsFiles()
		throws PortalException, SystemException {

		String[] fileNames = new String[0];

		try {
			fileNames = DLStoreUtil.getFileNames(
				getCompanyId(), CompanyConstants.SYSTEM, getAttachmentsDir());
		}
		catch (NoSuchDirectoryException nsde) {
		}

		return fileNames;
	}

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

	public MBCategory getCategory() throws PortalException, SystemException {
		return MBCategoryLocalServiceUtil.getCategory(getCategoryId());
	}

	public String getDeletedAttachmentsDir() {
		if (_deletedAttachmentDirs == null) {
			_deletedAttachmentDirs =
				getThreadAttachmentsDir() + StringPool.FORWARD_SLASH +
					TrashUtil.TRASH_ATTACHMENTS_DIR + getMessageId();
		}

		return _deletedAttachmentDirs;
	}

	public String[] getDeletedAttachmentsFiles()
		throws PortalException, SystemException {

		String[] fileNames = new String[0];

		try {
			fileNames = DLStoreUtil.getFileNames(
				getCompanyId(), CompanyConstants.SYSTEM,
				getDeletedAttachmentsDir());
		}
		catch (NoSuchDirectoryException nsde) {
		}

		return fileNames;
	}

	public MBThread getThread() throws PortalException, SystemException {
		return MBThreadLocalServiceUtil.getThread(getThreadId());
	}

	public String getThreadAttachmentsDir() {
		return MBMessageConstants.BASE_ATTACHMENTS_DIR + getThreadId();
	}

	public String getWorkflowClassName() {
		if (isDiscussion()) {
			return MBDiscussion.class.getName();
		}
		else {
			return MBMessage.class.getName();
		}
	}

	public boolean isDiscussion() {
		if (getCategoryId() == MBCategoryConstants.DISCUSSION_CATEGORY_ID) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isFormatBBCode() {
		String format = getFormat();

		if (format.equals("bbcode")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isInTrashThread() throws PortalException, SystemException {
		MBThread thread = getThread();

		return thread.isInTrash();
	}

	public boolean isReply() {
		return !isRoot();
	}

	public boolean isRoot() {
		if (getParentMessageId() ==
				MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

			return true;
		}
		else {
			return false;
		}
	}

	public void setAttachmentsDir(String attachmentsDir) {
		_attachmentDirs = attachmentsDir;
	}

	private String _attachmentDirs;
	private String _deletedAttachmentDirs;

}