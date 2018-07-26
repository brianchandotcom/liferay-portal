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

package com.liferay.document.library.opener.google.drive.internal.service;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.DLOpenerGoogleDriveManager;
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
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
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
		super.cancelCheckOut(fileEntryId);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {
			_dlOpenerGoogleDriveManager.delete(_getUserId(), fileEntry);
		}
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (!_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {
			super.checkInFileEntry(
				fileEntryId, majorVersion, changeLog, serviceContext);

			return;
		}

		_updateFileEntryFromGoogleDrive(fileEntry, serviceContext);

		super.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);

		_dlOpenerGoogleDriveManager.delete(
			serviceContext.getUserId(), fileEntry);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

		if (!_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {
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

		try {
			updateFileEntry(
				fileEntry.getFileEntryId(), fileEntry.getFileName(),
				fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), StringPool.BLANK, false, file,
				serviceContext);
		}
		finally {
			if (file != null) {
				if (!file.delete()) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to delete temporary file " +
								file.getAbsolutePath());
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLOpenerGoogleDriveDLAppServiceWrapper.class);

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

}