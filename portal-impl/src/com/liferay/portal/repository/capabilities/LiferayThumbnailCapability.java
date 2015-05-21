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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

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
	public FileEntry fetchFileEntryByImageId(long imageId)
		throws PortalException {

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

	protected DLFileEntry getDLFileEntry(FileEntry fileEntry) {
		_repositoryEntryChecker.checkFileEntry(fileEntry);

		return _repositoryEntryConverter.getDLFileEntry(fileEntry);
	}

	private final RepositoryEntryChecker _repositoryEntryChecker;
	private final RepositoryEntryConverter _repositoryEntryConverter;

}