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

import java.io.File;

import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveFileReference {

	public DLOpenerGoogleDriveFileReference(
		long fileEntryId, String googleDocsEditURL,
		Supplier<String> titleSupplier, Supplier<File> fileSupplier) {

		_fileEntryId = fileEntryId;
		_googleDocsEditURL = googleDocsEditURL;
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
		return _googleDocsEditURL;
	}

	public String getTitle() {
		return _titleSupplier.get();
	}

	private final long _fileEntryId;
	private final Supplier<File> _fileSupplier;
	private final String _googleDocsEditURL;
	private final Supplier<String> _titleSupplier;

}