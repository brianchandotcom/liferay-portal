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

import com.liferay.bookmarks.service.BookmarksEntryService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for BookmarksEntry. This utility wraps
 * {@link com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=bookmarks", "json.web.service.context.path=BookmarksEntry"}, service = BookmarksEntryJsonService.class)
@JSONWebService
@ProviderType
public class BookmarksEntryJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.bookmarks.model.BookmarksEntry addEntry(long groupId,
		long folderId, java.lang.String name, java.lang.String url,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addEntry(groupId, folderId, name, url, description,
			serviceContext);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.deleteEntry(entryId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end) {
		return _service.getEntries(groupId, folderId, start, end);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator) {
		return _service.getEntries(groupId, folderId, start, end,
			orderByComparator);
	}

	public int getEntriesCount(long groupId, long folderId) {
		return _service.getEntriesCount(groupId, folderId);
	}

	public int getEntriesCount(long groupId, long folderId, int status) {
		return _service.getEntriesCount(groupId, folderId, status);
	}

	public com.liferay.bookmarks.model.BookmarksEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getEntry(entryId);
	}

	public int getFoldersEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds) {
		return _service.getFoldersEntriesCount(groupId, folderIds);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getGroupEntries(groupId, start, end);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getGroupEntries(groupId, userId, rootFolderId, start,
			end);
	}

	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getGroupEntries(groupId, userId, start, end);
	}

	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getGroupEntriesCount(groupId);
	}

	public int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getGroupEntriesCount(groupId, userId);
	}

	public int getGroupEntriesCount(long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getGroupEntriesCount(groupId, userId, rootFolderId);
	}

	public com.liferay.bookmarks.model.BookmarksEntry moveEntry(long entryId,
		long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveEntry(entryId, parentFolderId);
	}

	public com.liferay.bookmarks.model.BookmarksEntry moveEntryFromTrash(
		long entryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveEntryFromTrash(entryId, parentFolderId);
	}

	public com.liferay.bookmarks.model.BookmarksEntry moveEntryToTrash(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.moveEntryToTrash(entryId);
	}

	public com.liferay.bookmarks.model.BookmarksEntry openEntry(
		com.liferay.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.openEntry(entry);
	}

	public com.liferay.bookmarks.model.BookmarksEntry openEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.openEntry(entryId);
	}

	public void restoreEntryFromTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.restoreEntryFromTrash(entryId);
	}

	public com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.search(groupId, creatorUserId, status, start, end);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public void subscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.subscribeEntry(entryId);
	}

	public void unsubscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_service.unsubscribeEntry(entryId);
	}

	public com.liferay.bookmarks.model.BookmarksEntry updateEntry(
		long entryId, long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateEntry(entryId, groupId, folderId, name, url,
			description, serviceContext);
	}

	@Reference
	protected void setService(BookmarksEntryService service) {
		_service = service;
	}

	private BookmarksEntryService _service;
}