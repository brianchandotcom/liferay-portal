/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.liferayrepository;

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileContentReference;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class ModelValidatorLocalRepositoryWrapper
	extends LocalRepositoryWrapper {

	public ModelValidatorLocalRepositoryWrapper(
		LocalRepository localRepository,
		ModelValidator<FileContentReference> modelValidator) {

		super(localRepository);

		_modelValidator = modelValidator;
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long folderId,
			String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromFile(
				serviceContext.getScopeGroupId(), sourceFileName,
				DLAppUtil.getExtension(title, sourceFileName), mimeType, file);

		_modelValidator.validate(fileContentReference);

		return super.addFileEntry(
			externalReferenceCode, userId, folderId, sourceFileName, mimeType,
			title, urlTitle, description, changeLog, file, expirationDate,
			reviewDate, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long folderId,
			String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromInputStream(
				serviceContext.getScopeGroupId(), sourceFileName,
				DLAppUtil.getExtension(title, sourceFileName), mimeType,
				inputStream, size);

		_modelValidator.validate(fileContentReference);

		return super.addFileEntry(
			externalReferenceCode, userId, folderId, sourceFileName, mimeType,
			title, urlTitle, description, changeLog, inputStream, size,
			expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			File file, Date expirationDate, Date reviewDate,
			ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromFile(
				fileEntryId, sourceFileName,
				DLAppUtil.getExtension(title, sourceFileName), mimeType, file);

		_modelValidator.validate(fileContentReference);

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, urlTitle,
			description, changeLog, dlVersionNumberIncrease, file,
			expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		FileContentReference fileContentReference =
			FileContentReference.fromInputStream(
				serviceContext.getScopeGroupId(), fileEntryId, sourceFileName,
				DLAppUtil.getExtension(title, sourceFileName), mimeType,
				inputStream, size);

		_modelValidator.validate(fileContentReference);

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, urlTitle,
			description, changeLog, dlVersionNumberIncrease, inputStream, size,
			expirationDate, reviewDate, serviceContext);
	}

	private final ModelValidator<FileContentReference> _modelValidator;

}