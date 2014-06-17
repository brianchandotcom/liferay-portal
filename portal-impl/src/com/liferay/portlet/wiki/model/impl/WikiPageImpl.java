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

package com.liferay.portlet.wiki.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Repository;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class WikiPageImpl extends WikiPageBaseImpl {

	public WikiPageImpl() {
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
			getGroupId(), PortletKeys.WIKI, serviceContext);

		WikiNode node = getNode();

		Folder nodeFolder = node.addAttachmentsFolder();

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			getUserId(), repository.getRepositoryId(), nodeFolder.getFolderId(),
			String.valueOf(getResourcePrimKey()), serviceContext);

		_attachmentsFolderId = folder.getFolderId();

		return folder;
	}

	@Override
	public WikiPage fetchParentPage() {
		if (Validator.isNull(getParentTitle())) {
			return null;
		}

		return WikiPageLocalServiceUtil.fetchPage(
			getNodeId(), getParentTitle());
	}

	@Override
	public WikiPage fetchRedirectPage() {
		if (Validator.isNull(getRedirectTitle())) {
			return null;
		}

		return WikiPageLocalServiceUtil.fetchPage(
			getNodeId(), getRedirectTitle());
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries() {
		return getAttachmentsFileEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries(int start, int end) {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			fileEntries = PortletFileRepositoryUtil.getPortletFileEntries(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_APPROVED, start, end, null);
		}

		return fileEntries;
	}

	@Override
	public int getAttachmentsFileEntriesCount() {
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
				getGroupId(), PortletKeys.WIKI);

		long nodeAttachmentsFolderId = getNodeAttachmentsFolderId();

		if ((repository == null) ||
			(nodeAttachmentsFolderId ==
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				getUserId(), repository.getRepositoryId(),
				nodeAttachmentsFolderId, String.valueOf(getResourcePrimKey()),
				serviceContext);

			_attachmentsFolderId = folder.getFolderId();
		}
		catch (Exception e) {
		}

		return _attachmentsFolderId;
	}

	@Override
	public List<WikiPage> getChildPages() {
		try {
			return WikiPageLocalServiceUtil.getChildren(
				getNodeId(), true, getTitle());
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	@Override
	public List<FileEntry> getDeletedAttachmentsFileEntries() {
		return getDeletedAttachmentsFileEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<FileEntry> getDeletedAttachmentsFileEntries(
		int start, int end) {

		List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != 0) {
			fileEntries = PortletFileRepositoryUtil.getPortletFileEntries(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_IN_TRASH, start, end, null);
		}

		return fileEntries;
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount() {
		int deletedAttachmentsFileEntriesCount = 0;

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return PortletFileRepositoryUtil.getPortletFileEntriesCount(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_IN_TRASH);
		}

		return deletedAttachmentsFileEntriesCount;
	}

	@Override
	public WikiNode getNode() {
		try {
			return WikiNodeLocalServiceUtil.getNode(getNodeId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return new WikiNodeImpl();
		}
	}

	@Override
	public long getNodeAttachmentsFolderId() {
		WikiNode node = getNode();

		return node.getAttachmentsFolderId();
	}

	@Override
	public WikiPage getParentPage() throws PortalException {
		if (Validator.isNull(getParentTitle())) {
			return null;
		}

		return WikiPageLocalServiceUtil.getPage(getNodeId(), getParentTitle());
	}

	@Override
	public List<WikiPage> getParentPages() {
		List<WikiPage> parentPages = new ArrayList<WikiPage>();

		WikiPage parentPage = fetchParentPage();

		if (parentPage != null) {
			parentPages.addAll(parentPage.getParentPages());
			parentPages.add(parentPage);
		}

		return parentPages;
	}

	@Override
	public WikiPage getRedirectPage() throws PortalException {
		if (Validator.isNull(getRedirectTitle())) {
			return null;
		}

		return WikiPageLocalServiceUtil.getPage(
			getNodeId(), getRedirectTitle());
	}

	@Override
	public long getTrashEntryClassPK() {
		return getResourcePrimKey();
	}

	@Override
	public List<WikiPage> getViewableChildPages() {
		try {
			return WikiPageServiceUtil.getChildren(
				getGroupId(), getNodeId(), true, getTitle());
		}
		catch (Exception e) {
			_log.error(e, e);

			return Collections.emptyList();
		}
	}

	@Override
	public WikiPage getViewableParentPage() {
		if (Validator.isNull(getParentTitle())) {
			return null;
		}

		try {
			return WikiPageServiceUtil.getPage(
				getGroupId(), getNodeId(), getParentTitle());
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	@Override
	public List<WikiPage> getViewableParentPages() {
		List<WikiPage> pages = new ArrayList<WikiPage>();

		WikiPage page = getViewableParentPage();

		if (page != null) {
			pages.addAll(page.getViewableParentPages());
			pages.add(page);
		}

		return pages;
	}

	@Override
	public boolean isResourceMain() {
		return isHead();
	}

	@Override
	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_attachmentsFolderId = attachmentsFolderId;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageImpl.class);

	private long _attachmentsFolderId;

}