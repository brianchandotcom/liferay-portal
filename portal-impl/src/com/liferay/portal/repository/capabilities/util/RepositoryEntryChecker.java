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

package com.liferay.portal.repository.capabilities.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Iván Zaera
 */
public class RepositoryEntryChecker {

	public RepositoryEntryChecker(DocumentRepository documentRepository) {
		_documentRepository = documentRepository;
	}

	public DLFileEntry checkDLFileEntry(DLFileEntry dlFileEntry) {
		long repositoryId = _documentRepository.getRepositoryId();

		if (dlFileEntry.getRepositoryId() != repositoryId) {
			throw new SystemException(
				StringBundler.concat(
					"File entry ", String.valueOf(dlFileEntry.getFileEntryId()),
					" does not belong to repository ",
					String.valueOf(repositoryId)));
		}

		return dlFileEntry;
	}

	public FileEntry checkFileEntry(FileEntry fileEntry) {
		long repositoryId = _documentRepository.getRepositoryId();

		if (fileEntry.getRepositoryId() != repositoryId) {
			throw new SystemException(
				StringBundler.concat(
					"File entry ", String.valueOf(fileEntry.getFileEntryId()),
					" does not belong to repository ",
					String.valueOf(repositoryId)));
		}

		return fileEntry;
	}

	private final DocumentRepository _documentRepository;

}