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
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.document.library.opener.constants.DLOpenerMimeTypes;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveFileReference;
import com.liferay.document.library.opener.onedrive.web.internal.DLOpenerOneDriveManager;
import com.liferay.document.library.opener.upload.UniqueFileEntryTitleProvider;
import com.liferay.petra.string.CharPool;
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

	public DLOpenerOneDriveDLAppServiceWrapper() {
		super(null);
	}

	public DLOpenerOneDriveDLAppServiceWrapper(DLAppService dlAppService) {
		super(dlAppService);
	}

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		super.cancelCheckOut(fileEntryId);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (_dlOpenerOneDriveManager.isConfigured(fileEntry.getCompanyId()) &&
			_dlOpenerOneDriveManager.isOneDriveFile(fileEntry)) {

			_dlOpenerOneDriveManager.deleteFile(_getUserId(), fileEntry);

			if (DLFileEntryConstants.VERSION_DEFAULT.equals(
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

	private long _getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	private String _removeExtension(String string) {
		int i = string.lastIndexOf(CharPool.PERIOD);

		if (i == -1) {
			return string;
		}

		return string.substring(0, i);
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

			title = _uniqueFileEntryTitleProvider.provide(
				fileEntry.getGroupId(), fileEntry.getFolderId(),
				_dlValidator.fixName(
					_removeExtension(
						dLOpenerOneDriveFileReference.getTitle())));

			sourceFileName = title;

			sourceFileName += DLOpenerMimeTypes.getMimeTypeExtension(
				fileEntry.getMimeType());
		}

		try {
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
		DLOpenerOneDriveDLAppServiceWrapper.class);

	@Reference
	private DLOpenerOneDriveManager _dlOpenerOneDriveManager;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private UniqueFileEntryTitleProvider _uniqueFileEntryTitleProvider;

}