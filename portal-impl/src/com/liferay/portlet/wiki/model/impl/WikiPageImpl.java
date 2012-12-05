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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiPageAttachmentsUtil;

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

	public List<FileEntry> getAttachmentsFileEntries()
		throws PortalException, SystemException {

		return getAttachmentsFileEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<FileEntry> getAttachmentsFileEntries(int start, int end)
		throws PortalException, SystemException {

		return PortletFileRepositoryUtil.getPortletFileEntries(
			getGroupId(), getAttachmentsFolderId(),
			WorkflowConstants.STATUS_APPROVED, start, end, null);
	}

	public int getAttachmentsFileEntriesCount()
		throws PortalException, SystemException {

		return PortletFileRepositoryUtil.getPortletFileEntriesCount(
			getGroupId(), getAttachmentsFolderId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	public long getAttachmentsFolderId()
		throws PortalException, SystemException {

		if (_attachmentsFolderId > 0) {
			return _attachmentsFolderId;
		}

		_attachmentsFolderId = WikiPageAttachmentsUtil.getFolderId(
			getGroupId(), getUserId(), getNodeId(), getResourcePrimKey());

		return _attachmentsFolderId;
	}

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

	public List<FileEntry> getDeletedAttachmentsFileEntries()
		throws PortalException, SystemException {

		return getDeletedAttachmentsFileEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<FileEntry> getDeletedAttachmentsFileEntries(int start, int end)
		throws PortalException, SystemException {

		return PortletFileRepositoryUtil.getPortletFileEntries(
			getGroupId(), getAttachmentsFolderId(),
			WorkflowConstants.STATUS_IN_TRASH, start, end, null);
	}

	public int getDeletedAttachmentsFileEntriesCount()
		throws PortalException, SystemException {

		return PortletFileRepositoryUtil.getPortletFileEntriesCount(
			getGroupId(), getAttachmentsFolderId(),
			WorkflowConstants.STATUS_IN_TRASH);
	}

	public WikiNode getNode() {
		try {
			return WikiNodeLocalServiceUtil.getNode(getNodeId());
		}
		catch (Exception e) {
			_log.error(e, e);

			return new WikiNodeImpl();
		}
	}

	public WikiPage getParentPage() {
		if (Validator.isNull(getParentTitle())) {
			return null;
		}

		try {
			return WikiPageLocalServiceUtil.getPage(
				getNodeId(), getParentTitle());
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	public List<WikiPage> getParentPages() {
		List<WikiPage> parentPages = new ArrayList<WikiPage>();

		WikiPage parentPage = getParentPage();

		if (parentPage != null) {
			parentPages.addAll(parentPage.getParentPages());
			parentPages.add(parentPage);
		}

		return parentPages;
	}

	public WikiPage getRedirectPage() {
		if (Validator.isNull(getRedirectTitle())) {
			return null;
		}

		try {
			return WikiPageLocalServiceUtil.getPage(
				getNodeId(), getRedirectTitle());
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

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

	public List<WikiPage> getViewableParentPages() {
		List<WikiPage> pages = new ArrayList<WikiPage>();

		WikiPage page = getViewableParentPage();

		if (page != null) {
			pages.addAll(page.getViewableParentPages());
			pages.add(page);
		}

		return pages;
	}

	public boolean isInTrashFolder() {
		WikiNode node = getNode();

		if (node != null) {
			return node.isInTrash();
		}

		return false;
	}

	@Override
	public boolean isResourceMain() {
		return isHead();
	}

	public void setAttachmentsFolderId(long attachmentsFolderId) {
		_attachmentsFolderId = attachmentsFolderId;
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageImpl.class);

	private long _attachmentsFolderId;

}