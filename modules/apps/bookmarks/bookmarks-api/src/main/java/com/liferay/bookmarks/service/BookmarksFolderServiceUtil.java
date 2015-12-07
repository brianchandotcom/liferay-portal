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
 * Provides the remote service utility for BookmarksFolder. This utility wraps
 * {@link com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderService
 * @see com.liferay.bookmarks.service.base.BookmarksFolderServiceBaseImpl
 * @see com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl
 * @generated
 */
@ProviderType
public class BookmarksFolderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.bookmarks.model.BookmarksFolder addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFolder(parentFolderId, name, description, serviceContext);
	}

	public static void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFolder(folderId);
	}

	public static void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFolder(folderId, includeTrashedEntries);
	}

	public static com.liferay.bookmarks.model.BookmarksFolder getFolder(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFolder(folderId);
	}

	public static java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFolderIds(groupId, folderId);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId) {
		return getService().getFolders(groupId);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId) {
		return getService().getFolders(groupId, parentFolderId);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {
		return getService().getFolders(groupId, parentFolderId, start, end);
	}

	public static java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {
		return getService()
				   .getFolders(groupId, parentFolderId, status, start, end);
	}

	public static java.util.List<java.lang.Object> getFoldersAndEntries(
		long groupId, long folderId) {
		return getService().getFoldersAndEntries(groupId, folderId);
	}

	public static java.util.List<java.lang.Object> getFoldersAndEntries(
		long groupId, long folderId, int status) {
		return getService().getFoldersAndEntries(groupId, folderId, status);
	}

	public static java.util.List<java.lang.Object> getFoldersAndEntries(
		long groupId, long folderId, int status, int start, int end) {
		return getService()
				   .getFoldersAndEntries(groupId, folderId, status, start, end);
	}

	public static int getFoldersAndEntriesCount(long groupId, long folderId) {
		return getService().getFoldersAndEntriesCount(groupId, folderId);
	}

	public static int getFoldersAndEntriesCount(long groupId, long folderId,
		int status) {
		return getService().getFoldersAndEntriesCount(groupId, folderId, status);
	}

	public static int getFoldersCount(long groupId, long parentFolderId) {
		return getService().getFoldersCount(groupId, parentFolderId);
	}

	public static int getFoldersCount(long groupId, long parentFolderId,
		int status) {
		return getService().getFoldersCount(groupId, parentFolderId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@Deprecated
	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId) {
		getService().getSubfolderIds(folderIds, groupId, folderId);
	}

	public static void getSubfolderIds(
		java.util.List<java.lang.Long> folderIds, long groupId, long folderId,
		boolean recurse) {
		getService().getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public static java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse) {
		return getService().getSubfolderIds(groupId, folderId, recurse);
	}

	public static void mergeFolders(long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().mergeFolders(folderId, parentFolderId);
	}

	public static com.liferay.bookmarks.model.BookmarksFolder moveFolder(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFolder(folderId, parentFolderId);
	}

	public static com.liferay.bookmarks.model.BookmarksFolder moveFolderFromTrash(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFolderFromTrash(folderId, parentFolderId);
	}

	public static com.liferay.bookmarks.model.BookmarksFolder moveFolderToTrash(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFolderToTrash(folderId);
	}

	public static void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFolderFromTrash(folderId);
	}

	public static void subscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().subscribeFolder(groupId, folderId);
	}

	public static void unsubscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsubscribeFolder(groupId, folderId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #updateFolder(long, long,
	String, String, ServiceContext)} and {@link
	#mergeFolders(long, long)}
	*/
	@Deprecated
	public static com.liferay.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			mergeWithParentFolder, serviceContext);
	}

	public static com.liferay.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFolder(folderId, parentFolderId, name, description,
			serviceContext);
	}

	public static BookmarksFolderService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(BookmarksFolderService service) {
	}

	private static ServiceTracker<BookmarksFolderService, BookmarksFolderService> _serviceTracker =
		ServiceTrackerFactory.open(BookmarksFolderService.class);
}