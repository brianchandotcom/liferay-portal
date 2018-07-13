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

package com.liferay.document.library.opener.google.drive.model;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.Optional;

/**
 * @author Adolfo Pérez
 */
public class DLOpenerGoogleDriveFileReference {

	public static
			<E extends Throwable> Optional<DLOpenerGoogleDriveFileReference>
				captureDLOpenerGoogleDriveFileReference(
					DLOpenerGoogleDriveFileReference.UnsafeRunnable<E>
						unsafeRunnable)
		throws E {

		try {
			_threadLocal.remove();

			unsafeRunnable.run();

			return Optional.ofNullable(_threadLocal.get());
		}
		finally {
			_threadLocal.remove();
		}
	}

	public static void setCurrentDLOpenerGoogleDriveFileReference(
		DLOpenerGoogleDriveFileReference dlOpenerGoogleDriveFileReference) {

		if (_threadLocal.get() != null) {
			throw new IllegalStateException(
				"Google Drive file reference initialized twice");
		}

		_threadLocal.set(dlOpenerGoogleDriveFileReference);
	}

	public DLOpenerGoogleDriveFileReference(String cookie, long fileEntryId) {
		_cookie = cookie;
		_fileEntryId = fileEntryId;
	}

	public String getCookie() {
		return _cookie;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public String getUrl() {
		return "https://docs.google.com/document/d/" + getCookie() + "/edit";
	}

	public interface UnsafeRunnable<E extends Throwable> {

		public void run() throws E;

	}

	private static final ThreadLocal<DLOpenerGoogleDriveFileReference>
		_threadLocal = new CentralizedThreadLocal<>(true);

	private final String _cookie;
	private final long _fileEntryId;

}