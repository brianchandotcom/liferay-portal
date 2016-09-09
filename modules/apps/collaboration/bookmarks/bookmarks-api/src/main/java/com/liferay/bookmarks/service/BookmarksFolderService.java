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

import com.liferay.bookmarks.model.BookmarksFolder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

/**
 * Provides the remote service interface for BookmarksFolder. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see BookmarksFolderServiceUtil
 * @see com.liferay.bookmarks.service.base.BookmarksFolderServiceBaseImpl
 * @see com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=bookmarks", "json.web.service.context.path=BookmarksFolder"}, service = BookmarksFolderService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface BookmarksFolderService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link BookmarksFolderServiceUtil} to access the bookmarks folder remote service. Add custom service methods to {@link com.liferay.bookmarks.service.impl.BookmarksFolderServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public BookmarksFolder addFolder(long parentFolderId,
		java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BookmarksFolder getFolder(long folderId) throws PortalException;

	public BookmarksFolder moveFolder(long folderId, long parentFolderId)
		throws PortalException;

	public BookmarksFolder moveFolderFromTrash(long folderId,
		long parentFolderId) throws PortalException;

	public BookmarksFolder moveFolderToTrash(long folderId)
		throws PortalException;

	/**
	* @deprecated As of 1.0.0, replaced by {@link #updateFolder(long, long,
	String, String, ServiceContext)} and {@link
	#mergeFolders(long, long)}
	*/
	@java.lang.Deprecated
	public BookmarksFolder updateFolder(long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException;

	public BookmarksFolder updateFolder(long folderId, long parentFolderId,
		java.lang.String name, java.lang.String description,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndEntriesCount(long groupId, long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndEntriesCount(long groupId, long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId, int status);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Long> getFolderIds(long groupId, long folderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksFolder> getFolders(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksFolder> getFolders(long groupId, long parentFolderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksFolder> getFolders(long groupId, long parentFolderId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BookmarksFolder> getFolders(long groupId, long parentFolderId,
		int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Object> getFoldersAndEntries(long groupId,
		long folderId, int status, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<java.lang.Long> getSubfolderIds(long groupId, long folderId,
		boolean recurse);

	public void deleteFolder(long folderId) throws PortalException;

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException;

	/**
	* @deprecated As of 1.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(List<java.lang.Long> folderIds, long groupId,
		long folderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(List<java.lang.Long> folderIds, long groupId,
		long folderId, boolean recurse);

	public void mergeFolders(long folderId, long parentFolderId)
		throws PortalException;

	public void restoreFolderFromTrash(long folderId) throws PortalException;

	public void subscribeFolder(long groupId, long folderId)
		throws PortalException;

	public void unsubscribeFolder(long groupId, long folderId)
		throws PortalException;
}