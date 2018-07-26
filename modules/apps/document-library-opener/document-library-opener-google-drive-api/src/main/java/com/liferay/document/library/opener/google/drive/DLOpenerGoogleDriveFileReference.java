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

import com.liferay.petra.string.StringBundler;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveFileReference {

	public DLOpenerGoogleDriveFileReference(
		String googleDriveFileId, long fileEntryId, String title) {

		_googleDriveFileId = googleDriveFileId;
		_fileEntryId = fileEntryId;
		_title = title;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public String getGoogleDocsEditURL() {
		return StringBundler.concat(
			"https://docs.google.com/document/d/", getGoogleDriveFileId(),
			"/edit");
	}

	public String getGoogleDriveFileId() {
		return _googleDriveFileId;
	}

	public String getTitle() {
		return _title;
	}

	private final long _fileEntryId;
	private final String _googleDriveFileId;
	private final String _title;

}