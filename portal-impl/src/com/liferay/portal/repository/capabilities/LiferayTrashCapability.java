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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashVersionLocalServiceUtil;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class LiferayTrashCapability implements TrashCapability {

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		deleteTrashEntry(fileEntry);

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getGroupFileEntries(
				folder.getGroupId(), 0, folder.getFolderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			DLAppHelperLocalServiceUtil.deleteFileEntry(fileEntry);

			deleteTrashEntry(fileEntry);
		}

		DLAppHelperLocalServiceUtil.deleteFolder(folder);

		deleteTrashEntry(folder);

		DLFolderLocalServiceUtil.deleteFolder(folder.getFolderId(), false);
	}

	@Override
	public void deleteTrashEntry(FileEntry fileEntry) throws PortalException {
		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (dlFileEntry.isInTrashExplicitly()) {
			TrashEntryLocalServiceUtil.deleteEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());
		}
		else {
			List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);

			for (DLFileVersion dlFileVersion : dlFileVersions) {
				TrashVersionLocalServiceUtil.deleteTrashVersion(
					DLFileVersion.class.getName(),
					dlFileVersion.getFileVersionId());
			}
		}
	}

	@Override
	public void deleteTrashEntry(Folder folder) throws PortalException {
		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrashExplicitly()) {
			TrashEntryLocalServiceUtil.deleteEntry(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}
		else {
			TrashVersionLocalServiceUtil.deleteTrashVersion(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFileEntryFromTrash(
			userId, fileEntry, destinationFolder.getFolderId(), serviceContext);
	}

	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFileEntryToTrash(
			userId, fileEntry);
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFolderFromTrash(
			userId, folder, destinationFolder.getFolderId(), serviceContext);
	}

	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException {

		return DLAppHelperLocalServiceUtil.moveFolderToTrash(userId, folder);
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		DLAppHelperLocalServiceUtil.restoreFileEntryFromTrash(
			userId, fileEntry);
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException {

		DLAppHelperLocalServiceUtil.restoreFolderFromTrash(userId, folder);
	}

}