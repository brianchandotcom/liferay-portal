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

package com.liferay.document.library.opener.google.drive.web.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.opener.constants.DLOpenerFileEntryReferenceConstants;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.google.drive.web.internal.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.web.internal.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.web.internal.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.File;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a service wrapper responsible for uploading, updating, or deleting
 * the Google Drive file linked to a Documents and Media file entry.
 *
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class DLOpenerGoogleDriveDLAppServiceWrapper
	extends DLAppServiceWrapper {

	public DLOpenerGoogleDriveDLAppServiceWrapper() {
		super(null);
	}

	public DLOpenerGoogleDriveDLAppServiceWrapper(DLAppService dlAppService) {
		super(dlAppService);
	}

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		FileEntry fileEntry = getFileEntry(fileEntryId);

		super.cancelCheckOut(fileEntryId);

		if (_dlOpenerGoogleDriveManager.isConfigured(
				fileEntry.getCompanyId()) &&
			_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {

			DLOpenerFileEntryReference dlOpenerFileEntryReference =
				_dlOpenerFileEntryReferenceLocalService.
					getDLOpenerFileEntryReference(
						DLOpenerGoogleDriveConstants.
							GOOGLE_DRIVE_REFERENCE_TYPE,
						fileEntry);

			_dlOpenerGoogleDriveManager.delete(_getUserId(), fileEntry);

			if ((dlOpenerFileEntryReference.getType() ==
					DLOpenerFileEntryReferenceConstants.TYPE_NEW) &&
				DLFileEntryConstants.VERSION_DEFAULT.equals(
					fileEntry.getVersion())) {

				deleteFileEntry(fileEntryId);
			}
		}
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, DLVersionNumberIncrease dlVersionNumberIncrease,
			String changeLog, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (!_dlOpenerGoogleDriveManager.isConfigured(
				fileEntry.getCompanyId()) ||
			!_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {

			super.checkInFileEntry(
				fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);

			return;
		}

		_updateFileEntryFromGoogleDrive(fileEntry, serviceContext);

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(
					DLOpenerGoogleDriveConstants.GOOGLE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		if (dlOpenerFileEntryReference.getType() ==
				DLOpenerFileEntryReferenceConstants.TYPE_NEW) {

			dlVersionNumberIncrease = DLVersionNumberIncrease.NONE;
		}

		super.checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);

		_dlOpenerGoogleDriveManager.delete(
			serviceContext.getUserId(), fileEntry);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (!_dlOpenerGoogleDriveManager.isConfigured(
				fileEntry.getCompanyId()) ||
			!_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {

			super.checkInFileEntry(fileEntryId, lockUuid, serviceContext);

			return;
		}

		_updateFileEntryFromGoogleDrive(fileEntry, serviceContext);

		super.checkInFileEntry(fileEntryId, lockUuid, serviceContext);

		_dlOpenerGoogleDriveManager.delete(
			serviceContext.getUserId(), fileEntry);
	}

	private long _getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	private void _updateFileEntryFromGoogleDrive(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		DLOpenerGoogleDriveFileReference dlOpenerGoogleDriveFileReference =
			_dlOpenerGoogleDriveManager.requestEditAccess(
				serviceContext.getUserId(), fileEntry);

		File file = dlOpenerGoogleDriveFileReference.getContentFile();

		String title = fileEntry.getTitle();

		if (!title.equals(dlOpenerGoogleDriveFileReference.getTitle())) {
			title = _uniqueFileEntryTitleProvider.provide(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				_dlValidator.fixName(
					dlOpenerGoogleDriveFileReference.getTitle()));
		}

		try {
			String sourceFileName = title;

			sourceFileName += DLOpenerMimeTypes.getMimeTypeExtension(
				fileEntry.getMimeType());

			updateFileEntry(
				fileEntry.getFileEntryId(), sourceFileName,
				fileEntry.getMimeType(), title, fileEntry.getDescription(),
				StringPool.BLANK, DLVersionNumberIncrease.NONE, file,
				serviceContext);
		}
		finally {
			if ((file != null) && !file.delete()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to delete temporary file " +
							file.getAbsolutePath());
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLOpenerGoogleDriveDLAppServiceWrapper.class);

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}