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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseService;

/**
 * Provides the remote service interface for DLTrash. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashServiceUtil
 * @see com.liferay.portlet.documentlibrary.service.base.DLTrashServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface DLTrashService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLTrashServiceUtil} to access the d l trash remote service. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Moves the file entry from a trashed folder to the new folder.
	*
	* @param fileEntryId the primary key of the file entry
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Moves the file entry with the primary key to the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	* @return the file entry
	*/
	public com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long fileEntryId) throws PortalException;

	/**
	* Moves the file shortcut from a trashed folder to the new folder.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file shortcut
	*/
	public com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutFromTrash(
		long fileShortcutId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Moves the file shortcut with the primary key to the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut
	*/
	public com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutToTrash(
		long fileShortcutId) throws PortalException;

	/**
	* Moves the folder with the primary key from the trash portlet to the new
	* parent folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @param parentFolderId the primary key of the new parent folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	*/
	public com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws PortalException;

	/**
	* Moves the folder with the primary key to the trash portlet.
	*
	* @param folderId the primary key of the folder
	* @return the file entry
	*/
	public com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long folderId) throws PortalException;

	/**
	* Restores the file entry with the primary key from the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	*/
	public void restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException;

	/**
	* Restores the file shortcut with the primary key from the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	*/
	public void restoreFileShortcutFromTrash(long fileShortcutId)
		throws PortalException;

	/**
	* Restores the folder with the primary key from the trash portlet.
	*
	* @param folderId the primary key of the folder
	*/
	public void restoreFolderFromTrash(long folderId) throws PortalException;
}