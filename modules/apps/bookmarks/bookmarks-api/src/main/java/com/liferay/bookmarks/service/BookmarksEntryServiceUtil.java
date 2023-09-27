/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.service;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for BookmarksEntry. This utility wraps
 * <code>com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryService
 * @generated
 */
public class BookmarksEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BookmarksEntry addEntry(
			long groupId, long folderId, String name, String url,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addEntry(
			groupId, folderId, name, url, description, serviceContext);
	}

	public static void deleteEntry(long entryId) throws PortalException {
		getService().deleteEntry(entryId);
	}

	public static List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end) {

		return getService().getEntries(groupId, folderId, start, end);
	}

	public static List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return getService().getEntries(
			groupId, folderId, start, end, orderByComparator);
	}

	public static int getEntriesCount(long groupId, long folderId) {
		return getService().getEntriesCount(groupId, folderId);
	}

	public static int getEntriesCount(long groupId, long folderId, int status) {
		return getService().getEntriesCount(groupId, folderId, status);
	}

	public static BookmarksEntry getEntry(long entryId) throws PortalException {
		return getService().getEntry(entryId);
	}

	public static int getFoldersEntriesCount(
		long groupId, List<Long> folderIds) {

		return getService().getFoldersEntriesCount(groupId, folderIds);
	}

	public static List<BookmarksEntry> getGroupEntries(
			long groupId, int start, int end)
		throws PortalException {

		return getService().getGroupEntries(groupId, start, end);
	}

	public static List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, int start, int end)
		throws PortalException {

		return getService().getGroupEntries(groupId, userId, start, end);
	}

	public static List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws PortalException {

		return getService().getGroupEntries(
			groupId, userId, rootFolderId, start, end);
	}

	public static int getGroupEntriesCount(long groupId)
		throws PortalException {

		return getService().getGroupEntriesCount(groupId);
	}

	public static int getGroupEntriesCount(long groupId, long userId)
		throws PortalException {

		return getService().getGroupEntriesCount(groupId, userId);
	}

	public static int getGroupEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException {

		return getService().getGroupEntriesCount(groupId, userId, rootFolderId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static BookmarksEntry moveEntry(long entryId, long parentFolderId)
		throws PortalException {

		return getService().moveEntry(entryId, parentFolderId);
	}

	public static BookmarksEntry moveEntryFromTrash(
			long entryId, long parentFolderId)
		throws PortalException {

		return getService().moveEntryFromTrash(entryId, parentFolderId);
	}

	public static BookmarksEntry moveEntryToTrash(long entryId)
		throws PortalException {

		return getService().moveEntryToTrash(entryId);
	}

	public static BookmarksEntry openEntry(BookmarksEntry entry)
		throws PortalException {

		return getService().openEntry(entry);
	}

	public static BookmarksEntry openEntry(long entryId)
		throws PortalException {

		return getService().openEntry(entryId);
	}

	public static void restoreEntryFromTrash(long entryId)
		throws PortalException {

		getService().restoreEntryFromTrash(entryId);
	}

	public static com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return getService().search(groupId, creatorUserId, status, start, end);
	}

	public static void subscribeEntry(long entryId) throws PortalException {
		getService().subscribeEntry(entryId);
	}

	public static void unsubscribeEntry(long entryId) throws PortalException {
		getService().unsubscribeEntry(entryId);
	}

	public static BookmarksEntry updateEntry(
			long entryId, long groupId, long folderId, String name, String url,
			String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateEntry(
			entryId, groupId, folderId, name, url, description, serviceContext);
	}

	public static BookmarksEntryService getService() {
		return _service;
	}

	public static void setService(BookmarksEntryService service) {
		_service = service;
	}

	private static volatile BookmarksEntryService _service;

}