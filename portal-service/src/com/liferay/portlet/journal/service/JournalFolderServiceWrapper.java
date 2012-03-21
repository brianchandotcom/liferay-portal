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

package com.liferay.portlet.journal.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link JournalFolderService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFolderService
 * @generated
 */
public class JournalFolderServiceWrapper implements JournalFolderService,
	ServiceWrapper<JournalFolderService> {
	public JournalFolderServiceWrapper(
		JournalFolderService journalFolderService) {
		_journalFolderService = journalFolderService;
	}

	public com.liferay.portlet.journal.model.JournalFolder addFolder(
		long groupId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.addFolder(groupId, parentFolderId, name,
			description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_journalFolderService.deleteFolder(folderId);
	}

	public com.liferay.portlet.journal.model.JournalFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFolder(folderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.journal.model.JournalFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFolders(groupId, parentFolderId, start,
			end);
	}

	public java.util.List<java.lang.Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFoldersAndArticles(groupId, folderId,
			start, end, obc);
	}

	public int getFoldersAndArticlesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFoldersAndArticlesCount(groupId,
			folderId);
	}

	public int getFoldersArticlesCount(long groupId,
		java.util.List<java.lang.Long> folderIds, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFoldersArticlesCount(groupId,
			folderIds, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.getFoldersCount(groupId, parentFolderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public com.liferay.portlet.journal.model.JournalFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _journalFolderService.updateFolder(folderId, parentFolderId,
			name, description, mergeWithParentFolder, serviceContext);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public JournalFolderService getWrappedJournalFolderService() {
		return _journalFolderService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedJournalFolderService(
		JournalFolderService journalFolderService) {
		_journalFolderService = journalFolderService;
	}

	public JournalFolderService getWrappedService() {
		return _journalFolderService;
	}

	public void setWrappedService(JournalFolderService journalFolderService) {
		_journalFolderService = journalFolderService;
	}

	private JournalFolderService _journalFolderService;
}