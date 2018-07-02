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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

/**
 * @author     Iván Zaera
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.document.library.internal.capabilities.LiferayThumbnailCapability}
 */
@Deprecated
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

		return updateDLFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry setCustom2ImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setCustom2ImageId(imageId);

		return updateDLFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry setLargeImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setLargeImageId(imageId);

		return updateDLFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry setSmallImageId(FileEntry fileEntry, long imageId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry.getFileEntryId());

		dlFileEntry.setSmallImageId(imageId);

		return updateDLFileEntry(dlFileEntry);
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

	protected FileEntry updateDLFileEntry(DLFileEntry dlFileEntry) {
		return new LiferayFileEntry(
			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry));
	}

	private final RepositoryEntryChecker _repositoryEntryChecker;
	private final RepositoryEntryConverter _repositoryEntryConverter;

}