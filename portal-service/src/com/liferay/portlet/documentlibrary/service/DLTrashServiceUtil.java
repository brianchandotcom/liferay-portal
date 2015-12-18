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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for DLTrash. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashService
 * @see com.liferay.portlet.documentlibrary.service.base.DLTrashServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl
 * @generated
 */
@ProviderType
public class DLTrashServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Moves the file entry from a trashed folder to the new folder.
	*
	* @param fileEntryId the primary key of the file entry
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryFromTrash(
		long fileEntryId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileEntryFromTrash(fileEntryId, newFolderId,
			serviceContext);
	}

	/**
	* Moves the file entry with the primary key to the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	* @return the file entry
	*/
	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFileEntryToTrash(fileEntryId);
	}

	/**
	* Moves the file shortcut from a trashed folder to the new folder.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @param newFolderId the primary key of the new folder
	* @param serviceContext the service context to be applied
	* @return the file shortcut
	*/
	public static com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutFromTrash(
		long fileShortcutId, long newFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFileShortcutFromTrash(fileShortcutId, newFolderId,
			serviceContext);
	}

	/**
	* Moves the file shortcut with the primary key to the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	* @return the file shortcut
	*/
	public static com.liferay.portal.kernel.repository.model.FileShortcut moveFileShortcutToTrash(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFileShortcutToTrash(fileShortcutId);
	}

	/**
	* Moves the folder with the primary key from the trash portlet to the new
	* parent folder with the primary key.
	*
	* @param folderId the primary key of the folder
	* @param parentFolderId the primary key of the new parent folder
	* @param serviceContext the service context to be applied
	* @return the file entry
	*/
	public static com.liferay.portal.kernel.repository.model.Folder moveFolderFromTrash(
		long folderId, long parentFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .moveFolderFromTrash(folderId, parentFolderId, serviceContext);
	}

	/**
	* Moves the folder with the primary key to the trash portlet.
	*
	* @param folderId the primary key of the folder
	* @return the file entry
	*/
	public static com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
		long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().moveFolderToTrash(folderId);
	}

	/**
	* Restores the file entry with the primary key from the trash portlet.
	*
	* @param fileEntryId the primary key of the file entry
	*/
	public static void restoreFileEntryFromTrash(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFileEntryFromTrash(fileEntryId);
	}

	/**
	* Restores the file shortcut with the primary key from the trash portlet.
	*
	* @param fileShortcutId the primary key of the file shortcut
	*/
	public static void restoreFileShortcutFromTrash(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFileShortcutFromTrash(fileShortcutId);
	}

	/**
	* Restores the folder with the primary key from the trash portlet.
	*
	* @param folderId the primary key of the folder
	*/
	public static void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().restoreFolderFromTrash(folderId);
	}

	public static DLTrashService getService() {
		if (_service == null) {
			_service = (DLTrashService)PortalBeanLocatorUtil.locate(DLTrashService.class.getName());

			ReferenceRegistry.registerReference(DLTrashServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DLTrashService service) {
	}

	private static DLTrashService _service;
}