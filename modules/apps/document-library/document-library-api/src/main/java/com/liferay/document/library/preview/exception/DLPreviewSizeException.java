/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alejandro Tardín
 */
public class DLPreviewSizeException extends PortalException {

	public DLPreviewSizeException() {
		this(0);
	}

	public DLPreviewSizeException(long maxFileSize) {
		_maxFileSize = maxFileSize;
	}

	public DLPreviewSizeException(long maxFileSize, Throwable throwable) {
		super(throwable);

		_maxFileSize = maxFileSize;
	}

	public DLPreviewSizeException(String msg) {
		this(msg, 0);
	}

	public DLPreviewSizeException(String msg, long maxFileSize) {
		super(msg);

		_maxFileSize = maxFileSize;
	}

	public DLPreviewSizeException(
		String msg, long maxFileSize, Throwable throwable) {

		super(msg, throwable);

		_maxFileSize = maxFileSize;
	}

	public DLPreviewSizeException(String msg, Throwable throwable) {
		this(msg, 0, throwable);
	}

	public DLPreviewSizeException(Throwable throwable) {
		this(0, throwable);
	}

	public long getMaxFileSize() {
		return _maxFileSize;
	}

	private final long _maxFileSize;

}