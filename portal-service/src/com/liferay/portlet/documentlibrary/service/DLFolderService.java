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

package com.liferay.portlet.documentlibrary.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for DLFolder. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolderServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLFolderServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLFolderService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFolderServiceUtil} to access the document library folder remote service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFolderServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portlet.documentlibrary.model.DLFolder addFolder(
		long groupId, long repositoryId, boolean mountPoint,
		long parentFolderId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteFolder(long folderId) throws PortalException;

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException;

	public void deleteFolder(long groupId, long parentFolderId,
		java.lang.String name) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFileEntriesAndFileShortcutsCount(long groupId, long folderId,
		int status, java.lang.String[] mimeTypes) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long folderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.documentlibrary.model.DLFolder getFolder(
		long groupId, long parentFolderId, java.lang.String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getFolderIds(long groupId,
		long folderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getFolders(
		long groupId, long parentFolderId, int status,
		boolean includeMountfolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, java.lang.String[] mimeTypes,
		boolean includeMountFolders,
		com.liferay.portal.kernel.dao.orm.QueryDefinition<?> queryDefinition)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, boolean includeMountFolders,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object> getFoldersAndFileEntriesAndFileShortcuts(
		long groupId, long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<?> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, boolean includeMountFolders)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersAndFileEntriesAndFileShortcutsCount(long groupId,
		long folderId, int status, java.lang.String[] mimeTypes,
		boolean includeMountFolders) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFoldersCount(long groupId, long parentFolderId, int status,
		boolean includeMountfolders) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFolder> getMountFolders(
		long groupId, long parentFolderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFolder> obc)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMountFoldersCount(long groupId, long parentFolderId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getSubfolderIds(List, long,
	long, boolean)}
	*/
	@java.lang.Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void getSubfolderIds(java.util.List<java.lang.Long> folderIds,
		long groupId, long folderId, boolean recurse) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getSubfolderIds(long groupId,
		long folderId, boolean recurse) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasFolderLock(long folderId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isFolderLocked(long folderId);

	public com.liferay.portal.kernel.lock.Lock lockFolder(long folderId)
		throws PortalException;

	public com.liferay.portal.kernel.lock.Lock lockFolder(long folderId,
		java.lang.String owner, boolean inheritable, long expirationTime)
		throws PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFolder moveFolder(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portal.kernel.lock.Lock refreshFolderLock(
		java.lang.String lockUuid, long companyId, long expirationTime)
		throws PortalException;

	public void unlockFolder(long folderId, java.lang.String lockUuid)
		throws PortalException;

	public void unlockFolder(long groupId, long parentFolderId,
		java.lang.String name, java.lang.String lockUuid)
		throws PortalException;

	/**
	* @deprecated As of 7.0.0, replaced by more general {@link
	#updateFolder(long, String, String, long, List, int,
	ServiceContext)}
	*/
	@java.lang.Deprecated
	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds,
		boolean overrideFileEntryTypes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, java.lang.String name, java.lang.String description,
		long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	public com.liferay.portlet.documentlibrary.model.DLFolder updateFolder(
		long folderId, long parentFolderId, java.lang.String name,
		java.lang.String description, long defaultFileEntryTypeId,
		java.util.List<java.lang.Long> fileEntryTypeIds, int restrictionType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;
}