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

package com.liferay.wiki.model.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.service.WikiPageServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class WikiPageImpl extends WikiPageBaseImpl {

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
			getGroupId(), WikiConstants.SERVICE_NAME, serviceContext);

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
	public List<FileEntry> getAttachmentsFileEntries(
			int start, int end, OrderByComparator obc)
		throws PortalException {

		List<FileEntry> fileEntries = new ArrayList<>();

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			fileEntries = PortletFileRepositoryUtil.getPortletFileEntries(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_APPROVED, start, end, obc);
		}

		return fileEntries;
	}

	@Override
	public List<FileEntry> getAttachmentsFileEntries(
			String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return Collections.emptyList();
		}

		return PortletFileRepositoryUtil.getPortletFileEntries(
			getGroupId(), attachmentsFolderId, mimeTypes,
			WorkflowConstants.STATUS_APPROVED, start, end, obc);
	}

	@Override
	public int getAttachmentsFileEntriesCount() throws PortalException {
		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return 0;
		}

		return PortletFileRepositoryUtil.getPortletFileEntriesCount(
			getGroupId(), attachmentsFolderId,
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getAttachmentsFileEntriesCount(String[] mimeTypes)
		throws PortalException {

		int attachmentsFileEntriesCount = 0;

		long attachmentsFolderId = getAttachmentsFolderId();

		if (attachmentsFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			attachmentsFileEntriesCount =
				PortletFileRepositoryUtil.getPortletFileEntriesCount(
					getGroupId(), attachmentsFolderId, mimeTypes,
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
				getGroupId(), WikiConstants.SERVICE_NAME);

		long nodeAttachmentsFolderId = getNodeAttachmentsFolderId();

		if ((repository == null) ||
			(nodeAttachmentsFolderId ==
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(), nodeAttachmentsFolderId,
				String.valueOf(getResourcePrimKey()));

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

		if (attachmentsFolderId != 0) {
			fileEntries = PortletFileRepositoryUtil.getPortletFileEntries(
				getGroupId(), attachmentsFolderId,
				WorkflowConstants.STATUS_IN_TRASH, start, end, null);
		}

		return fileEntries;
	}

	@Override
	public int getDeletedAttachmentsFileEntriesCount() throws PortalException {
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
		List<WikiPage> parentPages = new ArrayList<>();

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
		List<WikiPage> pages = new ArrayList<>();

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

	private static final Log _log = LogFactoryUtil.getLog(WikiPageImpl.class);

	private long _attachmentsFolderId;

}