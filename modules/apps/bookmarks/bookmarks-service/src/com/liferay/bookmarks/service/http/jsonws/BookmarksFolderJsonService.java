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

package com.liferay.bookmarks.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.bookmarks.service.BookmarksFolderService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for BookmarksFolder. This utility wraps
 * {@link com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=bookmarks", "json.web.service.context.path=BookmarksFolder"}, service = BookmarksFolderJsonService.class)
@JSONWebService
@ProviderType
public class BookmarksFolderJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.bookmarks.model.BookmarksFolder addFolder(
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addFolder(parentFolderId, name, description,
			serviceContext);
	}

	public void deleteFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteFolder(folderId);
	}

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteFolder(folderId, includeTrashedEntries);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.bookmarks.model.BookmarksFolder getFolder(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getFolder(folderId);
	}

	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getFolderIds(groupId, folderId);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId) {
		return _service.getFolders(groupId);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId) {
		return _service.getFolders(groupId, parentFolderId);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {
		return _service.getFolders(groupId, parentFolderId, start, end);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {
		return _service.getFolders(groupId, parentFolderId, status, start, end);
	}

	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId) {
		return _service.getFoldersAndEntries(groupId, folderId);
	}

	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status) {
		return _service.getFoldersAndEntries(groupId, folderId, status);
	}

	public java.util.List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status, int start, int end) {
		return _service.getFoldersAndEntries(groupId, folderId, status, start,
			end);
	}

	public int getFoldersAndEntriesCount(long groupId, long folderId) {
		return _service.getFoldersAndEntriesCount(groupId, folderId);
	}

	public int getFoldersAndEntriesCount(long groupId, long folderId, int status) {
		return _service.getFoldersAndEntriesCount(groupId, folderId, status);
	}

	public int getFoldersCount(long groupId, long parentFolderId) {
		return _service.getFoldersCount(groupId, parentFolderId);
	}

	public int getFoldersCount(long groupId, long parentFolderId, int status) {
		return _service.getFoldersCount(groupId, parentFolderId, status);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@Deprecated
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId) {
		_service.getSubfolderIds(folderIds, groupId, folderId);
	}

	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId, boolean recurse) {
		_service.getSubfolderIds(folderIds, groupId, folderId, recurse);
	}

	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse) {
		return _service.getSubfolderIds(groupId, folderId, recurse);
	}

	public com.liferay.bookmarks.model.BookmarksFolder moveFolder(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveFolder(folderId, parentFolderId);
	}

	public com.liferay.bookmarks.model.BookmarksFolder moveFolderFromTrash(
		long folderId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveFolderFromTrash(folderId, parentFolderId);
	}

	public com.liferay.bookmarks.model.BookmarksFolder moveFolderToTrash(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveFolderToTrash(folderId);
	}

	public void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.restoreFolderFromTrash(folderId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public void subscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.subscribeFolder(groupId, folderId);
	}

	public void unsubscribeFolder(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.unsubscribeFolder(groupId, folderId);
	}

	public com.liferay.bookmarks.model.BookmarksFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, boolean mergeWithParentFolder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateFolder(folderId, parentFolderId, name,
			description, mergeWithParentFolder, serviceContext);
	}

	@Reference
	protected void setService(BookmarksFolderService service) {
		_service = service;
	}

	private BookmarksFolderService _service;
}