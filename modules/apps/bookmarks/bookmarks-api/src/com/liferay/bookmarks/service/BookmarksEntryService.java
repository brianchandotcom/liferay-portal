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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for BookmarksEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksEntryServiceUtil
 * @see com.liferay.bookmarks.service.base.BookmarksEntryServiceBaseImpl
 * @see com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BookmarksEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksEntryServiceUtil} to access the bookmarks entry remote service. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.bookmarks.model.BookmarksEntry addEntry(long groupId,
		long folderId, java.lang.String name, java.lang.String url,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.bookmarks.model.BookmarksEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.bookmarks.model.BookmarksEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersEntriesCount(long groupId,
		java.util.List<java.lang.Long> folderIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, long rootFolderId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.bookmarks.model.BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupEntriesCount(long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry moveEntry(long entryId,
		long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry moveEntryFromTrash(
		long entryId, long parentFolderId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry moveEntryToTrash(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry openEntry(
		com.liferay.bookmarks.model.BookmarksEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry openEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void restoreEntryFromTrash(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long groupId,
		long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	public void subscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public void unsubscribeEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.bookmarks.model.BookmarksEntry updateEntry(
		long entryId, long groupId, long folderId, java.lang.String name,
		java.lang.String url, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException;
}