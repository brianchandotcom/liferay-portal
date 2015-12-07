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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for BookmarksEntry. This utility wraps
 * {@link com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryService
 * @see com.liferay.bookmarks.service.base.BookmarksEntryServiceBaseImpl
 * @see com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl
 * @generated
 */
@ProviderType
public class BookmarksEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.bookmarks.model.BookmarksEntry addEntry(
		long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addEntry(groupId, folderId, name, url, description,
			serviceContext);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteEntry(entryId);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end) {
		return getService().getEntries(groupId, folderId, start, end);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator) {
		return getService()
				   .getEntries(groupId, folderId, start, end, orderByComparator);
	}

	public static int getEntriesCount(long groupId, long folderId) {
		return getService().getEntriesCount(groupId, folderId);
	}

	public static int getEntriesCount(long groupId, long folderId, int status) {
		return getService().getEntriesCount(groupId, folderId, status);
	}

	public static com.liferay.bookmarks.model.BookmarksEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(entryId);
	}

	public static int getFoldersEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds) {
		return getService().getFoldersEntriesCount(groupId, folderIds);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getGroupEntries(groupId, userId, rootFolderId, start, end);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupEntries(groupId, userId, start, end);
	}

	public static int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupEntriesCount(groupId);
	}

	public static int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupEntriesCount(groupId, userId);
	}

	public static int getGroupEntriesCount(long groupId, long userId,
		long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupEntriesCount(groupId, userId, rootFolderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.bookmarks.model.BookmarksEntry moveEntry(
		long entryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveEntry(entryId, parentFolderId);
	}

	public static com.liferay.bookmarks.model.BookmarksEntry moveEntryFromTrash(
		long entryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveEntryFromTrash(entryId, parentFolderId);
	}

	public static com.liferay.bookmarks.model.BookmarksEntry moveEntryToTrash(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveEntryToTrash(entryId);
	}

	public static com.liferay.bookmarks.model.BookmarksEntry openEntry(
		com.liferay.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().openEntry(entry);
	}

	public static com.liferay.bookmarks.model.BookmarksEntry openEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().openEntry(entryId);
	}

	public static void restoreEntryFromTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreEntryFromTrash(entryId);
	}

	public static com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().search(groupId, creatorUserId, status, start, end);
	}

	public static void subscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribeEntry(entryId);
	}

	public static void unsubscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribeEntry(entryId);
	}

	public static com.liferay.bookmarks.model.BookmarksEntry updateEntry(
		long entryId, long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateEntry(entryId, groupId, folderId, name, url,
			description, serviceContext);
	}

	public static BookmarksEntryService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(BookmarksEntryService service) {
	}

	private static ServiceTracker<BookmarksEntryService, BookmarksEntryService> _serviceTracker =
		ServiceTrackerFactory.open(BookmarksEntryService.class);
}