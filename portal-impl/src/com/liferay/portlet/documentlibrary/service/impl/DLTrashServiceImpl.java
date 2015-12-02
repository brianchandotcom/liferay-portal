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

package com.liferay.portlet.documentlibrary.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLTrashServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFileShortcutPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

/**
 * The implementation of the d l trash remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.documentlibrary.service.DLTrashService} interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLTrashServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.DLTrashServiceUtil
 */
@ProviderType
public class DLTrashServiceImpl extends DLTrashServiceBaseImpl {

	/**
	 * Moves the file entry from a trashed folder to the new folder.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 */
	@Override
	public FileEntry moveFileEntryFromTrash(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = repositoryProvider.getFileEntryRepository(
			fileEntryId);

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.UPDATE);

		Folder destinationFolder = repository.getFolder(newFolderId);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFileEntryFromTrash(
			getUserId(), fileEntry, destinationFolder, serviceContext);
	}

	/**
	 * Moves the file entry with the primary key to the trash portlet.
	 *
	 * @param  fileEntryId the primary key of the file entry
	 * @return the file entry
	 */
	@Override
	public FileEntry moveFileEntryToTrash(long fileEntryId)
		throws PortalException {

		Repository repository = repositoryProvider.getFileEntryRepository(
			fileEntryId);

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFileEntryToTrash(getUserId(), fileEntry);
	}

	/**
	 * Moves the file shortcut from a trashed folder to the new folder.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @param  newFolderId the primary key of the new folder
	 * @param  serviceContext the service context to be applied
	 * @return the file shortcut
	 */
	@Override
	public FileShortcut moveFileShortcutFromTrash(
			long fileShortcutId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(fileShortcutId);

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcut, ActionKeys.UPDATE);

		return dlAppHelperLocalService.moveFileShortcutFromTrash(
			getUserId(), fileShortcut, newFolderId, serviceContext);
	}

	/**
	 * Moves the file shortcut with the primary key to the trash portlet.
	 *
	 * @param  fileShortcutId the primary key of the file shortcut
	 * @return the file shortcut
	 */
	@Override
	public FileShortcut moveFileShortcutToTrash(long fileShortcutId)
		throws PortalException {

		Repository repository = repositoryProvider.getFileShortcutRepository(
			fileShortcutId);

		FileShortcut fileShortcut = repository.getFileShortcut(fileShortcutId);

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcut, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFileShortcutToTrash(
			getUserId(), fileShortcut);
	}

	/**
	 * Moves the folder with the primary key from the trash portlet to the new
	 * parent folder with the primary key.
	 *
	 * @param  folderId the primary key of the folder
	 * @param  parentFolderId the primary key of the new parent folder
	 * @param  serviceContext the service context to be applied
	 * @return the file entry
	 */
	@Override
	public Folder moveFolderFromTrash(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = repositoryProvider.getFolderRepository(
			folderId);

		Folder folder = repository.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.UPDATE);

		Folder destinationFolder = null;

		if (parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			destinationFolder = repository.getFolder(parentFolderId);
		}

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFolderFromTrash(
			getUserId(), folder, destinationFolder, serviceContext);
	}

	/**
	 * Moves the folder with the primary key to the trash portlet.
	 *
	 * @param  folderId the primary key of the folder
	 * @return the file entry
	 */
	@Override
	public Folder moveFolderToTrash(long folderId) throws PortalException {
		Repository repository = repositoryProvider.getFolderRepository(
			folderId);

		Folder folder = repository.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFolderToTrash(getUserId(), folder);
	}

	/**
	 * Restores the file entry with the primary key from the trash portlet.
	 *
	 * @param fileEntryId the primary key of the file entry
	 */
	@Override
	public void restoreFileEntryFromTrash(long fileEntryId)
		throws PortalException {

		Repository repository = repositoryProvider.getFileEntryRepository(
			fileEntryId);

		FileEntry fileEntry = repository.getFileEntry(fileEntryId);

		DLFileEntryPermission.check(
			getPermissionChecker(), fileEntry, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		trashCapability.restoreFileEntryFromTrash(getUserId(), fileEntry);
	}

	/**
	 * Restores the file shortcut with the primary key from the trash portlet.
	 *
	 * @param fileShortcutId the primary key of the file shortcut
	 */
	@Override
	public void restoreFileShortcutFromTrash(long fileShortcutId)
		throws PortalException {

		FileShortcut fileShortcut = getFileShortcut(fileShortcutId);

		DLFileShortcutPermission.check(
			getPermissionChecker(), fileShortcut, ActionKeys.DELETE);

		dlAppHelperLocalService.restoreFileShortcutFromTrash(
			getUserId(), fileShortcut);
	}

	/**
	 * Restores the folder with the primary key from the trash portlet.
	 *
	 * @param folderId the primary key of the folder
	 */
	@Override
	public void restoreFolderFromTrash(long folderId) throws PortalException {
		Repository repository = repositoryProvider.getFolderRepository(
			folderId);

		Folder folder = repository.getFolder(folderId);

		DLFolderPermission.check(
			getPermissionChecker(), folder, ActionKeys.DELETE);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		trashCapability.restoreFolderFromTrash(getUserId(), folder);
	}

	protected FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		Repository repository = repositoryProvider.getFileShortcutRepository(
			fileShortcutId);

		return repository.getFileShortcut(fileShortcutId);
	}

	@BeanReference(type = RepositoryProvider.class)
	protected RepositoryProvider repositoryProvider;

}