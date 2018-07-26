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

import java.io.File;

import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveFileReference {

	public DLOpenerGoogleDriveFileReference(
		String googleDriveFileId, long fileEntryId,
		Supplier<String> titleSupplier, Supplier<File> fileSupplier) {

		_googleDriveFileId = googleDriveFileId;
		_fileEntryId = fileEntryId;
		_titleSupplier = titleSupplier;
		_fileSupplier = fileSupplier;
	}

	public File getContentFile() {
		return _fileSupplier.get();
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
		return _titleSupplier.get();
	}

	private final long _fileEntryId;
	private final Supplier<File> _fileSupplier;
	private final String _googleDriveFileId;
	private final Supplier<String> _titleSupplier;

}