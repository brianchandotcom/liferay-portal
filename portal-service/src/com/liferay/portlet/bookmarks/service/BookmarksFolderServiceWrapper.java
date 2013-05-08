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

package com.liferay.portlet.bookmarks.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BookmarksFolderService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       BookmarksFolderService
 * @generated
 */
public class BookmarksFolderServiceWrapper implements BookmarksFolderService,
	ServiceWrapper<BookmarksFolderService> {
	public BookmarksFolderServiceWrapper(
		BookmarksFolderService bookmarksFolderService) {
		_bookmarksFolderService = bookmarksFolderService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _bookmarksFolderService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_bookmarksFolderService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.addFolder(parentFolderId, name,
			description, serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.deleteFolder(folderId);
	}

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.deleteFolder(folderId, includeTrashedEntries);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolder(folderId);
	}

	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolderIds(groupId, folderId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolders(groupId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolders(groupId, parentFolderId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFolders(groupId, parentFolderId,
			status, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersAndEntries(groupId, folderId);
	}

	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersAndEntries(groupId, folderId,
			status);
	}

	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersAndEntries(groupId, folderId,
			status, start, end);
	}

	public int getFoldersAndEntriesCount(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersAndEntriesCount(groupId,
			folderId);
	}

	public int getFoldersAndEntriesCount(long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersAndEntriesCount(groupId,
			folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersCount(long groupId, long parentFolderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getFoldersCount(groupId, parentFolderId,
			status);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.getSubfolderIds(folderIds, groupId, folderId);
	}

	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.getSubfolderIds(groupId, folderId,
			recurse);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder moveFolder(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.moveFolder(folderId, parentFolderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder moveFolderFromTrash(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.moveFolderFromTrash(folderId,
			parentFolderId);
	}

	public void moveFolderToTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.moveFolderToTrash(folderId);
	}

	public void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.restoreFolderFromTrash(folderId);
	}

	public void subscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.subscribeFolder(groupId, folderId);
	}

	public void unsubscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_bookmarksFolderService.unsubscribeFolder(groupId, folderId);
	}

	public com.liferay.portlet.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _bookmarksFolderService.updateFolder(folderId, parentFolderId,
			name, description, mergeWithParentFolder, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public BookmarksFolderService getWrappedBookmarksFolderService() {
		return _bookmarksFolderService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedBookmarksFolderService(
		BookmarksFolderService bookmarksFolderService) {
		_bookmarksFolderService = bookmarksFolderService;
	}

	public BookmarksFolderService getWrappedService() {
		return _bookmarksFolderService;
	}

	public void setWrappedService(BookmarksFolderService bookmarksFolderService) {
		_bookmarksFolderService = bookmarksFolderService;
	}

	private BookmarksFolderService _bookmarksFolderService;
}