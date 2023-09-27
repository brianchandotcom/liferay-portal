/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

/**
 * @author Iván Zaera
 */
public class LiferayThumbnailCapability implements ThumbnailCapability {

	public LiferayThumbnailCapability(
		RepositoryEntryConverter repositoryEntryConverter,
		RepositoryEntryChecker repositoryEntryChecker) {

		_repositoryEntryConverter = repositoryEntryConverter;
		_repositoryEntryChecker = repositoryEntryChecker;
	}

	@Override
	public FileEntry fetchImageFileEntry(long imageId) {
		DLFileEntry dlFileEntry =
			DLFileEntryLocalServiceUtil.fetchFileEntryByAnyImageId(imageId);

		if (dlFileEntry == null) {
			return null;
		}

		_repositoryEntryChecker.checkDLFileEntry(dlFileEntry);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public long getCustom1ImageId(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry);

		return dlFileEntry.getCustom1ImageId();
	}

	@Override
	public long getCustom2ImageId(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry);

		return dlFileEntry.getCustom2ImageId();
	}

	@Override
	public long getLargeImageId(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry);

		return dlFileEntry.getLargeImageId();
	}

	@Override
	public long getSmallImageId(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry);

		return dlFileEntry.getSmallImageId();
	}

	@Override
	public FileEntry setCustom1ImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setCustom1ImageId(imageId);

		return _updateDLFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry setCustom2ImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setCustom2ImageId(imageId);

		return _updateDLFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry setLargeImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setLargeImageId(imageId);

		return _updateDLFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry setSmallImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setSmallImageId(imageId);

		return _updateDLFileEntry(dlFileEntry);
	}

	protected DLFileEntry getDLFileEntry(FileEntry fileEntry) {
		_repositoryEntryChecker.checkFileEntry(fileEntry);

		return _repositoryEntryConverter.getDLFileEntry(fileEntry);
	}

	protected DLFileEntry getDLFileEntry(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntryId);

		_repositoryEntryChecker.checkDLFileEntry(dlFileEntry);

		return dlFileEntry;
	}

	private FileEntry _updateDLFileEntry(DLFileEntry dlFileEntry) {
		return new LiferayFileEntry(
			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry));
	}

	private final RepositoryEntryChecker _repositoryEntryChecker;
	private final RepositoryEntryConverter _repositoryEntryConverter;

}