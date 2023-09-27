/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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