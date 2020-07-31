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

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for BookmarksEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface BookmarksEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the bookmarks entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link BookmarksEntryServiceUtil} if injection and service tracking are not available.
	 */
	public BookmarksEntry addEntry(
			long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException;

	public void deleteEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BookmarksEntry getEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersEntriesCount(long groupId, List<Long> folderIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksEntry> getGroupEntries(
			long groupId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, long userId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public BookmarksEntry moveEntry(long entryId, long parentFolderId)
		throws PortalException;

	public BookmarksEntry moveEntryFromTrash(long entryId, long parentFolderId)
		throws PortalException;

	public BookmarksEntry moveEntryToTrash(long entryId) throws PortalException;

	public BookmarksEntry openEntry(BookmarksEntry entry)
		throws PortalException;

	public BookmarksEntry openEntry(long entryId) throws PortalException;

	public void restoreEntryFromTrash(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException;

	public void subscribeEntry(long entryId) throws PortalException;

	public void unsubscribeEntry(long entryId) throws PortalException;

	public BookmarksEntry updateEntry(
			long entryId, long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException;

}