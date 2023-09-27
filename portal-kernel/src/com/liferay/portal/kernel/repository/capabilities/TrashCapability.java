/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Adolfo Pérez
 */
@ProviderType
public interface TrashCapability extends Capability {

	public void deleteFileEntry(FileEntry fileEntry) throws PortalException;

	public void deleteFolder(Folder folder) throws PortalException;

	public boolean isInTrash(FileEntry fileEntry) throws PortalException;

	public boolean isInTrash(Folder folder) throws PortalException;

	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, Folder newFolder,
			ServiceContext serviceContext)
		throws PortalException;

	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException;

	public FileShortcut moveFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut, Folder newFolder,
			ServiceContext serviceContext)
		throws PortalException;

	public FileShortcut moveFileShortcutToTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException;

	public Folder moveFolderFromTrash(
			long userId, Folder folder, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException;

	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException;

	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException;

	public void restoreFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException;

	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException;

}