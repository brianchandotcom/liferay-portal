/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.google.drive.web.internal;

import java.io.File;

import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveFileReference {

	public DLOpenerGoogleDriveFileReference(
		long fileEntryId, Supplier<String> titleSupplier,
		Supplier<File> fileSupplier, long backgroundTaskId) {

		_fileEntryId = fileEntryId;
		_titleSupplier = titleSupplier;
		_fileSupplier = fileSupplier;
		_backgroundTaskId = backgroundTaskId;
	}

	public long getBackgroundTaskId() {
		return _backgroundTaskId;
	}

	public File getContentFile() {
		return _fileSupplier.get();
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public String getTitle() {
		return _titleSupplier.get();
	}

	private final long _backgroundTaskId;
	private final long _fileEntryId;
	private final Supplier<File> _fileSupplier;
	private final Supplier<String> _titleSupplier;

}