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
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.google.drive.model.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.service.DLOpenerGoogleDriveManager;
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
import java.io.Serializable;

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

		_dlOpenerGoogleDriveManager.delete(
			_getUserId(), getFileEntry(fileEntryId));
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = getFileEntry(fileEntryId);

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

		_updateFileEntryFromGoogleDrive(fileEntry, serviceContext);

		super.checkInFileEntry(fileEntryId, lockUuid, serviceContext);

		_dlOpenerGoogleDriveManager.delete(
			serviceContext.getUserId(), fileEntry);
	}

	@Override
	public void checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		super.checkOutFileEntry(fileEntryId, serviceContext);

		if (_isCheckOutInGoogleDrive(serviceContext)) {
			DLOpenerGoogleDriveFileReference.
				setCurrentDLOpenerGoogleDriveFileReference(
					_dlOpenerGoogleDriveManager.checkOut(
						serviceContext.getUserId(), getFileEntry(fileEntryId)));
		}
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = super.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);

		if (_isCheckOutInGoogleDrive(serviceContext)) {
			DLOpenerGoogleDriveFileReference.
				setCurrentDLOpenerGoogleDriveFileReference(
					_dlOpenerGoogleDriveManager.checkOut(
						serviceContext.getUserId(), fileEntry));
		}

		return fileEntry;
	}

	private long _getUserId() {
		return GetterUtil.getLong(PrincipalThreadLocal.getName());
	}

	private boolean _isCheckOutInGoogleDrive(ServiceContext serviceContext) {
		Serializable checkOutInGoogleDrive = serviceContext.getAttribute(
			DLOpenerGoogleDriveConstants.CHECK_OUT_IN_GOOGLE_DRIVE);

		if ((checkOutInGoogleDrive != null) &&
			checkOutInGoogleDrive.equals(Boolean.TRUE)) {

			return true;
		}

		return false;
	}

	private void _updateFileEntryFromGoogleDrive(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		File file = _dlOpenerGoogleDriveManager.getContentFile(
			serviceContext.getUserId(), fileEntry);

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
							"Couldn't delete temporary file " +
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