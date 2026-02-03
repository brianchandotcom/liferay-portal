/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GroupThreadLocal;

/**
 * @author Brian Wing Shun Chan
 */
public class FileSizeException extends PortalException {

	public FileSizeException() {
		this(0);
	}

	public FileSizeException(long maxSize) {
		_maxSize = maxSize;
	}

	public FileSizeException(long maxSize, Throwable throwable) {
		super(throwable);

		_maxSize = maxSize;
	}

	public FileSizeException(String msg) {
		this(msg, 0);
	}

	public FileSizeException(String msg, long maxSize) {
		super(msg);

		_maxSize = maxSize;
	}

	public FileSizeException(String msg, long maxSize, String mimeType) {
		this(msg, maxSize);

		_mimeType = mimeType;
	}

	public FileSizeException(String msg, long maxSize, Throwable throwable) {
		super(msg, throwable);

		_maxSize = maxSize;
	}

	public FileSizeException(String msg, Throwable throwable) {
		this(msg, 0, throwable);
	}

	public FileSizeException(Throwable throwable) {
		this(0, throwable);
	}

	public long getMaxSize() {
		if (_maxSize != 0) {
			return _maxSize;
		}

		return DLValidatorUtil.getMaxAllowableSize(
			GroupThreadLocal.getGroupId(), null);
	}

	public String getMimeType() {
		return _mimeType;
	}

	private final long _maxSize;
	private String _mimeType;

}