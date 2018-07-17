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

package com.liferay.document.library.opener.google.drive.util;

import com.liferay.document.library.opener.google.drive.model.DLOpenerGoogleDriveFileReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Adolfo Pérez
 */
public interface DLOpenerGoogleDriveDLHelper {

	public void cancelCheckOut(long fileEntryId) throws PortalException;

	public void checkInFileEntry(
			long fileEntryId, boolean majorVersion, String changeLog,
			ServiceContext serviceContext)
		throws PortalException;

	public DLOpenerGoogleDriveFileReference checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException;

	public DLOpenerGoogleDriveFileReference editInGoogleDrive(
			long userId, long fileEntryId)
		throws PortalException;

}