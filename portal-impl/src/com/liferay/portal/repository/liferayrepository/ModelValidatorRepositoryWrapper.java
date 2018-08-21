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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileContentReference;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.repository.util.RepositoryWrapper;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class ModelValidatorRepositoryWrapper extends RepositoryWrapper {

	public ModelValidatorRepositoryWrapper(
		Repository repository,
		ModelValidator<FileContentReference> modelValidator) {

		super(repository);

		_modelValidator = modelValidator;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromFile(
				sourceFileName, DLAppUtil.getExtension(title, sourceFileName),
				mimeType, file);

		_modelValidator.validate(fileContentReference);

		return super.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromInputStream(
				sourceFileName, DLAppUtil.getExtension(title, sourceFileName),
				mimeType, is, size);

		_modelValidator.validate(fileContentReference);

		return super.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromFile(
				fileEntryId, sourceFileName,
				DLAppUtil.getExtension(title, sourceFileName), mimeType, file);

		_modelValidator.validate(fileContentReference);

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, file, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromInputStream(
				fileEntryId, sourceFileName,
				DLAppUtil.getExtension(title, sourceFileName), mimeType, is,
				size);

		_modelValidator.validate(fileContentReference);

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, is, size, serviceContext);
	}

	private final ModelValidator<FileContentReference> _modelValidator;

}