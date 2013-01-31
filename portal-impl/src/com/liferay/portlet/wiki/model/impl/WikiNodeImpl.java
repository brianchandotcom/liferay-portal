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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiNodeImpl extends WikiNodeBaseImpl {

	public WikiNodeImpl() {
	}

	public long getAttachmentsFolderId()
		throws PortalException, SystemException {

		if (_attachmentsFolderId > 0) {
			return _attachmentsFolderId;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = PortletFileRepositoryUtil.getPortletRepository(
			getGroupId(), PortletKeys.WIKI, serviceContext);

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			getUserId(), repositoryId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getNodeId()), serviceContext);

		_attachmentsFolderId = folder.getFolderId();

		return _attachmentsFolderId;
	}

	public List<FileEntry> getDeletedAttachmentsFiles()
		throws PortalException, SystemException {

		List<WikiPage> wikiPages = WikiPageLocalServiceUtil.getPages(
			getNodeId(), true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		for (WikiPage wikiPage : wikiPages) {
			fileEntries.addAll(wikiPage.getDeletedAttachmentsFileEntries());
		}

		return fileEntries;
	}

	private long _attachmentsFolderId;

}