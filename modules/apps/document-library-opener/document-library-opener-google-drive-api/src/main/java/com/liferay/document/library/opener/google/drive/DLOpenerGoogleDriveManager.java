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

package com.liferay.document.library.opener.google.drive;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.File;
import java.io.IOException;

/**
 * @author Adolfo Pérez
 */
public interface DLOpenerGoogleDriveManager {

	public DLOpenerGoogleDriveFileReference checkOut(
			long userId, FileEntry fileEntry)
		throws PortalException;

	public DLOpenerGoogleDriveFileReference create(
			long userId, FileEntry fileEntry)
		throws PortalException;

	public void delete(long userId, FileEntry fileEntry) throws PortalException;

	public String getAuthorizationURL(String state, String redirectUri);

	public boolean hasValidCredential(long userId) throws IOException;

	public boolean isGoogleDriveFile(FileEntry fileEntry);

	public void requestAuthorizationToken(
			long userId, String code, String redirectUri)
		throws IOException;

	public DLOpenerGoogleDriveFileReference requestEditAccess(
			long userId, FileEntry fileEntry)
		throws PortalException;

}