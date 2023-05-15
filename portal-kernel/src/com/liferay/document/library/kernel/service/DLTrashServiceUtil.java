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

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for DLTrash. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashService
 * @generated
 */
public class DLTrashServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLTrashServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
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
	public static com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryFromTrash(
				long fileEntryId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFileEntryFromTrash(
			fileEntryId, newFolderId, serviceContext);
	}

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 * @return the file entry
	 */
	public static com.liferay.portal.kernel.repository.model.FileEntry
			moveFileEntryToTrash(long fileEntryId)
		throws PortalException {

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
	public static com.liferay.portal.kernel.repository.model.FileShortcut
			moveFileShortcutFromTrash(
				long fileShortcutId, long newFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFileShortcutFromTrash(
			fileShortcutId, newFolderId, serviceContext);
	}

	/**
	 * Moves the file shortcut with the primary key to the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut
	 */
	public static com.liferay.portal.kernel.repository.model.FileShortcut
			moveFileShortcutToTrash(long fileShortcutId)
		throws PortalException {

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
	public static com.liferay.portal.kernel.repository.model.Folder
			moveFolderFromTrash(
				long folderId, long parentFolderId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().moveFolderFromTrash(
			folderId, parentFolderId, serviceContext);
	}

	/**
	 * Moves the folder with the primary key to the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 * @return the file entry
	 */
	public static com.liferay.portal.kernel.repository.model.Folder
			moveFolderToTrash(long folderId)
		throws PortalException {

		return getService().moveFolderToTrash(folderId);
	}

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 */
	public static void restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException {

		getService().restoreFileEntryFromTrash(fileEntryId);
	}

	/**
	 * Restores the file shortcut with the primary key from the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 */
	public static void restoreFileShortcutFromTrash(long fileShortcutId)
		throws PortalException {

		getService().restoreFileShortcutFromTrash(fileShortcutId);
	}

	/**
	 * Restores the folder with the primary key from the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 */
	public static void restoreFolderFromTrash(long folderId)
		throws PortalException {

		getService().restoreFolderFromTrash(folderId);
	}

	public static DLTrashService getService() {
		return _service;
	}

	public static void setService(DLTrashService service) {
		_service = service;
	}

	private static volatile DLTrashService _service;

}