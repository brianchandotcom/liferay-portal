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

package com.liferay.document.library.opener.google.drive.internal.service.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.opener.google.drive.constants.DLOpenerGoogleDriveConstants;
import com.liferay.document.library.opener.google.drive.model.DLOpenerGoogleDriveFileReference;
import com.liferay.document.library.opener.google.drive.manager.DLOpenerGoogleDriveManager;
import com.liferay.document.library.opener.google.drive.util.DLOpenerGoogleDriveDLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true)
public class DLOpenerGoogleDriveDLHelperImpl
	implements DLOpenerGoogleDriveDLHelper {

	@Override
	public void cancelCheckOut(long fileEntryId) throws PortalException {
		_dlAppService.cancelCheckOut(fileEntryId);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		_dlAppService.checkInFileEntry(
			fileEntryId, majorVersion, changeLog, serviceContext);
	}

	@Override
	public DLOpenerGoogleDriveFileReference checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		_dlAppService.checkOutFileEntry(fileEntryId, serviceContext);

		if (_isCheckOutInGoogleDrive(serviceContext)) {
			return _dlOpenerGoogleDriveManager.checkOut(
				serviceContext.getUserId(),
				_dlAppService.getFileEntry(fileEntryId));
		}

		return null;
	}

	@Override
	public DLOpenerGoogleDriveFileReference editInGoogleDrive(
			long userId, long fileEntryId)
		throws PortalException {

		FileEntry fileEntry = _dlAppService.getFileEntry(fileEntryId);

		if (!_dlOpenerGoogleDriveManager.isGoogleDriveFile(fileEntry)) {
			throw new IllegalArgumentException();
		}

		return _dlOpenerGoogleDriveManager.requestEditAccess(userId, fileEntry);
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

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLOpenerGoogleDriveManager _dlOpenerGoogleDriveManager;

}