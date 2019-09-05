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

package com.liferay.document.library.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLTrashService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashService
 * @generated
 */
public class DLTrashServiceWrapper
	implements DLTrashService, ServiceWrapper<DLTrashService> {

	public DLTrashServiceWrapper(DLTrashService dlTrashService) {
		_dlTrashService = dlTrashService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlTrashService.getOSGiServiceIdentifier();
	}

	/**
	 * Moves the file entry from a trashed folder to the new folder.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @param newFolderId the primary key of the new folder
	 * @param serviceContext the service context to be applied
	 * @return the file entry
	 */
	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryFromTrash(
				long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashService.moveFileEntryFromTrash(
			fileEntryId, newFolderId, serviceContext);
	}

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @return the file entry
	 */
	@Override
	public com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryToTrash(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashService.moveFileEntryToTrash(fileEntryId);
	}

	/**
	 * Moves the file shortcut from a trashed folder to the new folder.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 * @param newFolderId the primary key of the new folder
	 * @param serviceContext the service context to be applied
	 * @return the file shortcut
	 */
	@Override
	public com.liferay.portal.kernel.repository.model.FileShortcut
			moveFileShortcutFromTrash(
				long fileShortcutId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashService.moveFileShortcutFromTrash(
			fileShortcutId, newFolderId, serviceContext);
	}

	/**
	 * Moves the file shortcut with the primary key to the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut
	 */
	@Override
	public com.liferay.portal.kernel.repository.model.FileShortcut
			moveFileShortcutToTrash(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashService.moveFileShortcutToTrash(fileShortcutId);
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
	@Override
	public com.liferay.portal.kernel.repository.model.Folder
			moveFolderFromTrash(
				long folderId, long parentFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashService.moveFolderFromTrash(
			folderId, parentFolderId, serviceContext);
	}

	/**
	 * Moves the folder with the primary key to the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 * @return the file entry
	 */
	@Override
	public com.liferay.portal.kernel.repository.model.Folder moveFolderToTrash(
			long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlTrashService.moveFolderToTrash(folderId);
	}

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 */
	@Override
	public void restoreFileEntryFromTrash(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlTrashService.restoreFileEntryFromTrash(fileEntryId);
	}

	/**
	 * Restores the file shortcut with the primary key from the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 */
	@Override
	public void restoreFileShortcutFromTrash(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlTrashService.restoreFileShortcutFromTrash(fileShortcutId);
	}

	/**
	 * Restores the folder with the primary key from the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 */
	@Override
	public void restoreFolderFromTrash(long folderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlTrashService.restoreFolderFromTrash(folderId);
	}

	@Override
	public DLTrashService getWrappedService() {
		return _dlTrashService;
	}

	@Override
	public void setWrappedService(DLTrashService dlTrashService) {
		_dlTrashService = dlTrashService;
	}

	private DLTrashService _dlTrashService;

}