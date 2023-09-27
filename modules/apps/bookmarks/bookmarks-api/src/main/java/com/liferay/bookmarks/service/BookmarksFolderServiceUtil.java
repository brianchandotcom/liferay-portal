/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.service;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for BookmarksFolder. This utility wraps
 * <code>com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderService
 * @generated
 */
public class BookmarksFolderServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static BookmarksFolder addFolder(
			long parentFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFolder(
			parentFolderId, name, description, serviceContext);
	}

	public static void deleteFolder(long folderId) throws PortalException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws PortalException {

		getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static BookmarksFolder getFolder(long folderId)
		throws PortalException {

		return getService().getFolder(folderId);
	}

	public static List<Long> getFolderIds(long groupId, long folderId)
		throws PortalException {

		return getService().getFolderIds(groupId, folderId);
	}

	public static List<BookmarksFolder> getFolders(long groupId) {
		return getService().getFolders(groupId);
	}

	public static List<BookmarksFolder> getFolders(
		long groupId, long parentFolderId) {

		return getService().getFolders(groupId, parentFolderId);
	}

	public static List<BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {

		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static List<BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {

		return getService().getFolders(
			groupId, parentFolderId, status, start, end);
	}

	public static List<Object> getFoldersAndEntries(
		long groupId, long folderId) {

		return getService().getFoldersAndEntries(groupId, folderId);
	}

	public static List<Object> getFoldersAndEntries(
		long groupId, long folderId, int status) {

		return getService().getFoldersAndEntries(groupId, folderId, status);
	}

	public static List<Object> getFoldersAndEntries(
		long groupId, long folderId, int status, int start, int end) {

		return getService().getFoldersAndEntries(
			groupId, folderId, status, start, end);
	}

	public static int getFoldersAndEntriesCount(long groupId, long folderId) {
		return getService().getFoldersAndEntriesCount(groupId, folderId);
	}

	public static int getFoldersAndEntriesCount(
		long groupId, long folderId, int status) {

		return getService().getFoldersAndEntriesCount(
			groupId, folderId, status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId) {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(
		long groupId, long parentFolderId, int status) {

		return getService().getFoldersCount(groupId, parentFolderId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void getSubfolderIds(
		List<Long> folderIds, long groupId, long folderId, boolean recurse) {

		getService().getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public static List<Long> getSubfolderIds(
		long groupId, long folderId, boolean recurse) {

		return getService().getSubfolderIds(groupId, folderId, recurse);
	}

	public static void mergeFolders(long folderId, long parentFolderId)
		throws PortalException {

		getService().mergeFolders(folderId, parentFolderId);
	}

	public static BookmarksFolder moveFolder(long folderId, long parentFolderId)
		throws PortalException {

		return getService().moveFolder(folderId, parentFolderId);
	}

	public static BookmarksFolder moveFolderFromTrash(
			long folderId, long parentFolderId)
		throws PortalException {

		return getService().moveFolderFromTrash(folderId, parentFolderId);
	}

	public static BookmarksFolder moveFolderToTrash(long folderId)
		throws PortalException {

		return getService().moveFolderToTrash(folderId);
	}

	public static void restoreFolderFromTrash(long folderId)
		throws PortalException {

		getService().restoreFolderFromTrash(folderId);
	}

	public static void subscribeFolder(long groupId, long folderId)
		throws PortalException {

		getService().subscribeFolder(groupId, folderId);
	}

	public static void unsubscribeFolder(long groupId, long folderId)
		throws PortalException {

		getService().unsubscribeFolder(groupId, folderId);
	}

	public static BookmarksFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	public static BookmarksFolderService getService() {
		return _service;
	}

	public static void setService(BookmarksFolderService service) {
		_service = service;
	}

	private static volatile BookmarksFolderService _service;

}