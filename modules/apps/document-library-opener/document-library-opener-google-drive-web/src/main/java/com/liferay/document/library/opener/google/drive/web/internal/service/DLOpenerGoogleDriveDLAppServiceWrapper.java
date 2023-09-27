/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal.service;

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
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

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		FileEntry fileEntry = getFileEntry(fileEntryId);

		super.cancelCheckOut(fileEntryId);

		if (_dlOpenerGoogleDriveManager.isConfigured(
				fileEntry.getCompanyId()) &&
			_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {

			_dlOpenerGoogleDriveManager.delete(_getUserId(), fileEntry);
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
			_dlOpenerGoogleDriveManager.getDLOpenerGoogleDriveFileReference(
				serviceContext.getUserId(), fileEntry);

		File file = dlOpenerGoogleDriveFileReference.getContentFile();

		if (file == null) {
			return;
		}

		String title = fileEntry.getTitle();

		String mimeTypeExtension = DLOpenerMimeTypes.getMimeTypeExtension(
			fileEntry.getMimeType());

		if (!title.equals(dlOpenerGoogleDriveFileReference.getTitle())) {
			title = _uniqueFileEntryTitleProvider.provide(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				mimeTypeExtension,
				_dlValidator.fixName(
					dlOpenerGoogleDriveFileReference.getTitle()));
		}

		try {
			String sourceFileName = title.concat(mimeTypeExtension);

			updateFileEntry(
				fileEntry.getFileEntryId(), sourceFileName,
				fileEntry.getMimeType(), title, StringPool.BLANK,
				fileEntry.getDescription(), StringPool.BLANK,
				DLVersionNumberIncrease.NONE, file, null, null, serviceContext);
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