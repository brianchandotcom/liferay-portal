/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.document.library.opener.onedrive.web.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.opener.constants.DLOpenerFileEntryReferenceConstants;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveConstants;
import com.liferay.document.library.opener.onedrive.web.internal.constants.DLOpenerOneDriveMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.exception.GraphServicePortalException;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides a service wrapper responsible for uploading, updating, or deleting
 * the OneDrive file linked to a Documents and Media file entry.
 *
 * @author Cristina González
 * @review
 */
@Component(service = ServiceWrapper.class)
public class DLOpenerOneDriveDLAppServiceWrapper extends DLAppServiceWrapper {

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		FileEntry fileEntry = getFileEntry(fileEntryId);

		super.cancelCheckOut(fileEntryId);

		if (_dlOpenerOneDriveManager.isConfigured(fileEntry.getCompanyId()) &&
			_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			DLOpenerFileEntryReference dlOpenerFileEntryReference =
				_dlOpenerFileEntryReferenceLocalService.
					getDLOpenerFileEntryReference(
						DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
						fileEntry);

			try {
				_dlOpenerOneDriveManager.deleteFile(_getUserId(), fileEntry);
			}
			catch (GraphServicePortalException.ItemNotFound
						graphServicePortalException) {

				_log.error(
					"The OneDrive file does not exist",
					graphServicePortalException);
			}

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

		if (!_dlOpenerOneDriveManager.isConfigured(fileEntry.getCompanyId()) ||
			!_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			super.checkInFileEntry(
				fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);

			return;
		}

		_updateFileEntryFromOneDrive(fileEntry, serviceContext);

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(
					DLOpenerOneDriveConstants.ONE_DRIVE_REFERENCE_TYPE,
					fileEntry);

		if (dlOpenerFileEntryReference.getType() ==
				DLOpenerFileEntryReferenceConstants.TYPE_NEW) {

			dlVersionNumberIncrease = DLVersionNumberIncrease.NONE;
		}

		super.checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);

		_dlOpenerOneDriveManager.deleteFile(
			serviceContext.getUserId(), fileEntry);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (!_dlOpenerOneDriveManager.isConfigured(fileEntry.getCompanyId()) ||
			!_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			super.checkInFileEntry(fileEntryId, lockUuid, serviceContext);

			return;
		}

		_updateFileEntryFromOneDrive(fileEntry, serviceContext);

		super.checkInFileEntry(fileEntryId, lockUuid, serviceContext);

		_dlOpenerOneDriveManager.deleteFile(
			serviceContext.getUserId(), fileEntry);
	}

	@Override
	public FileEntry updateFileEntryAndCheckIn(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String urlTitle, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date revisionDate, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (!_dlOpenerOneDriveManager.isConfigured(fileEntry.getCompanyId()) ||
			!_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			return super.updateFileEntryAndCheckIn(
				fileEntryId, sourceFileName, mimeType, title, urlTitle,
				description, changeLog, dlVersionNumberIncrease, null,
				expirationDate, revisionDate, serviceContext);
		}

		checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);

		return super.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, urlTitle, description,
			changeLog, dlVersionNumberIncrease, null, 0, expirationDate,
			revisionDate, serviceContext);
	}

	private long _getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	private void _updateFileEntryFromOneDrive(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		DLOpenerOneDriveFileReference dLOpenerOneDriveFileReference =
			_dlOpenerOneDriveManager.getDLOpenerOneDriveFileReference(
				serviceContext.getUserId(), fileEntry);

		File file = dLOpenerOneDriveFileReference.getContentFile();

		String title = fileEntry.getTitle();

		String sourceFileName = fileEntry.getFileName();

		if (!Objects.equals(
				sourceFileName, dLOpenerOneDriveFileReference.getTitle())) {

			String mimeTypeExtension =
				DLOpenerOneDriveMimeTypes.getMimeTypeExtension(
					fileEntry.getMimeType());

			title = _uniqueFileEntryTitleProvider.provide(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				mimeTypeExtension,
				_dlValidator.fixName(
					FileUtil.stripExtension(
						dLOpenerOneDriveFileReference.getTitle())));

			sourceFileName = title.concat(mimeTypeExtension);
		}

		try {
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
		DLOpenerOneDriveDLAppServiceWrapper.class);

	@Reference
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}