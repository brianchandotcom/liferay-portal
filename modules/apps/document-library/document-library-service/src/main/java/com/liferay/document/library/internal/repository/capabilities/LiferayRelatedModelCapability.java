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

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;

/**
 * @author Iván Zaera
 */
public class LiferayRelatedModelCapability implements RelatedModelCapability {

	public LiferayRelatedModelCapability(
		RepositoryEntryConverter repositoryEntryConverter,
		RepositoryEntryChecker repositoryEntryChecker) {

		_repositoryEntryConverter = repositoryEntryConverter;
		_repositoryEntryChecker = repositoryEntryChecker;
	}

	@Override
	public String getClassName(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry);

		return dlFileEntry.getClassName();
	}

	@Override
	public long getClassPK(FileEntry fileEntry) {
		DLFileEntry dlFileEntry = getDLFileEntry(fileEntry);

		return dlFileEntry.getClassPK();
	}

	protected DLFileEntry getDLFileEntry(FileEntry fileEntry) {
		_repositoryEntryChecker.checkFileEntry(fileEntry);

		return _repositoryEntryConverter.getDLFileEntry(fileEntry);
	}

	private final RepositoryEntryChecker _repositoryEntryChecker;
	private final RepositoryEntryConverter _repositoryEntryConverter;

}