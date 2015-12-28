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

package com.liferay.bookmarks.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link BookmarksEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryService
 * @generated
 */
@ProviderType
public class BookmarksEntryServiceWrapper implements BookmarksEntryService,
	ServiceWrapper<BookmarksEntryService> {
	public BookmarksEntryServiceWrapper(
		BookmarksEntryService bookmarksEntryService) {
		_bookmarksEntryService = bookmarksEntryService;
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry addEntry(long groupId,
		long folderId, java.lang.String name, java.lang.String url,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.addEntry(groupId, folderId, name, url,
			description, serviceContext);
	}

	@Override
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_bookmarksEntryService.deleteEntry(entryId);
	}

	@Override
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end) {
		return _bookmarksEntryService.getEntries(groupId, folderId, start, end);
	}

	@Override
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator) {
		return _bookmarksEntryService.getEntries(groupId, folderId, start, end,
			orderByComparator);
	}

	@Override
	public int getEntriesCount(long groupId, long folderId) {
		return _bookmarksEntryService.getEntriesCount(groupId, folderId);
	}

	@Override
	public int getEntriesCount(long groupId, long folderId, int status) {
		return _bookmarksEntryService.getEntriesCount(groupId, folderId, status);
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getEntry(entryId);
	}

	@Override
	public int getFoldersEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds) {
		return _bookmarksEntryService.getFoldersEntriesCount(groupId, folderIds);
	}

	@Override
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getGroupEntries(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getGroupEntries(groupId, userId,
			rootFolderId, start, end);
	}

	@Override
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getGroupEntries(groupId, userId, start,
			end);
	}

	@Override
	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getGroupEntriesCount(groupId);
	}

	@Override
	public int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getGroupEntriesCount(groupId, userId);
	}

	@Override
	public int getGroupEntriesCount(long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.getGroupEntriesCount(groupId, userId,
			rootFolderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _bookmarksEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry moveEntry(long entryId,
		long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.moveEntry(entryId, parentFolderId);
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry moveEntryFromTrash(
		long entryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.moveEntryFromTrash(entryId, parentFolderId);
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry moveEntryToTrash(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.moveEntryToTrash(entryId);
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry openEntry(
		com.liferay.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.openEntry(entry);
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry openEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.openEntry(entryId);
	}

	@Override
	public void restoreEntryFromTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_bookmarksEntryService.restoreEntryFromTrash(entryId);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.search(groupId, creatorUserId, status,
			start, end);
	}

	@Override
	public void subscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_bookmarksEntryService.subscribeEntry(entryId);
	}

	@Override
	public void unsubscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_bookmarksEntryService.unsubscribeEntry(entryId);
	}

	@Override
	public com.liferay.bookmarks.model.BookmarksEntry updateEntry(
		long entryId, long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _bookmarksEntryService.updateEntry(entryId, groupId, folderId,
			name, url, description, serviceContext);
	}

	@Override
	public BookmarksEntryService getWrappedService() {
		return _bookmarksEntryService;
	}

	@Override
	public void setWrappedService(BookmarksEntryService bookmarksEntryService) {
		_bookmarksEntryService = bookmarksEntryService;
	}

	private BookmarksEntryService _bookmarksEntryService;
}