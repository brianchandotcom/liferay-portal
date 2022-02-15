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

package com.liferay.document.library.kernel.exception;

import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.portal.kernel.exception.PortalException;

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

		return DLValidatorUtil.getMaxAllowableSize(null);
	}

	private final long _maxSize;

}