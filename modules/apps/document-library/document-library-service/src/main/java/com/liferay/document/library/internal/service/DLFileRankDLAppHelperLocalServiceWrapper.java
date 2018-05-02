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

package com.liferay.document.library.internal.service;

import com.liferay.document.library.file.rank.service.DLFileRankLocalService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceWrapper;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class DLFileRankDLAppHelperLocalServiceWrapper
	extends DLAppHelperLocalServiceWrapper {

	public DLFileRankDLAppHelperLocalServiceWrapper() {
		super(null);
	}

	public DLFileRankDLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {

		super(dlAppHelperLocalService);
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		super.deleteFileEntry(fileEntry);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		_dlFileRankLocalService.deleteFileRanksByFileEntryId(
			fileEntry.getFileEntryId());
	}

	@Override
	public void getFileAsStream(
		long userId, FileEntry fileEntry, boolean incrementCounter) {

		super.getFileAsStream(userId, fileEntry, incrementCounter);

		if (!incrementCounter) {
			return;
		}

		if (userId > 0) {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					_dlFileRankLocalService.updateFileRank(
						fileEntry.getGroupId(), fileEntry.getCompanyId(),
						userId, fileEntry.getFileEntryId(),
						new ServiceContext());

					return null;
				});
		}
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry curFileEntry = super.moveFileEntryFromTrash(
			userId, fileEntry, newFolderId, serviceContext);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return curFileEntry;
		}

		_dlFileRankLocalService.enableFileRanks(fileEntry.getFileEntryId());

		return curFileEntry;
	}

	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		FileEntry curFileEntry = super.moveFileEntryToTrash(userId, fileEntry);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return curFileEntry;
		}

		_dlFileRankLocalService.disableFileRanks(fileEntry.getFileEntryId());

		return curFileEntry;
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		Folder curFolder = super.moveFolderFromTrash(
			userId, folder, parentFolderId, serviceContext);

		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrashExplicitly()) {
			return curFolder;
		}

		_dlFileRankLocalService.enableFileRanksByFolderId(folder.getFolderId());

		return curFolder;
	}

	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException {

		Folder curFolder = super.moveFolderToTrash(userId, folder);

		_dlFileRankLocalService.disableFileRanksByFolderId(
			folder.getFolderId());

		return curFolder;
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		super.restoreFileEntryFromTrash(userId, fileEntry);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		_dlFileRankLocalService.enableFileRanks(fileEntry.getFileEntryId());
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException {

		super.restoreFolderFromTrash(userId, folder);

		_dlFileRankLocalService.enableFileRanksByFolderId(folder.getFolderId());
	}

	@Reference
	private DLFileRankLocalService _dlFileRankLocalService;

}