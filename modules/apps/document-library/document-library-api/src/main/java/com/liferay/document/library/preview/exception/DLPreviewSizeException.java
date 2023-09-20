/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.exception;

import com.liferay.document.library.kernel.util.DLProcessorRegistryUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alejandro Tardín
 */
public class DLPreviewSizeException extends PortalException {

	public DLPreviewSizeException() {
		this(0);
	}

	public DLPreviewSizeException(long maxSize) {
		_maxSize = maxSize;
	}

	public DLPreviewSizeException(long maxSize, Throwable throwable) {
		super(throwable);

		_maxSize = maxSize;
	}

	public DLPreviewSizeException(String msg) {
		this(msg, 0);
	}

	public DLPreviewSizeException(String msg, long maxSize) {
		super(msg);

		_maxSize = maxSize;
	}

	public DLPreviewSizeException(
		String msg, long maxSize, Throwable throwable) {

		super(msg, throwable);

		_maxSize = maxSize;
	}

	public DLPreviewSizeException(String msg, Throwable throwable) {
		this(msg, 0, throwable);
	}

	public DLPreviewSizeException(Throwable throwable) {
		this(0, throwable);
	}

	public long getMaxSize() {
		if (_maxSize != 0) {
			return _maxSize;
		}

		return DLProcessorRegistryUtil.getPreviewableProcessorMaxSize();
	}

	private final long _maxSize;

}