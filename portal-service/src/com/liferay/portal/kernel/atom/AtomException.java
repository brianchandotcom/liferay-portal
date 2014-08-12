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

package com.liferay.portal.kernel.atom;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Igor Spasic
 */
public class AtomException extends PortalException {

	public AtomException(int errorCode) {
		super(String.valueOf(errorCode));

		_errorCode = errorCode;
	}

	public AtomException(int errorCode, Throwable cause) {
		super(String.valueOf(errorCode), cause);

		_errorCode = errorCode;
	}

	public AtomException(String msg) {
		super(msg);

		_errorCode = AtomCollectionAdapter.SC_INTERNAL_SERVER_ERROR;
	}

	public int getErrorCode() {
		return _errorCode;
	}

	private final int _errorCode;

}