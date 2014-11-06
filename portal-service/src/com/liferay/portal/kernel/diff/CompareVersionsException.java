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

package com.liferay.portal.kernel.diff;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public class CompareVersionsException extends PortalException {

	public CompareVersionsException(double version) {
		_version = version;
	}

	public CompareVersionsException(String msg) {
		super(msg);

		_version = 0;
	}

	public CompareVersionsException(String msg, Throwable cause) {
		super(msg, cause);

		_version = 0;
	}

	public CompareVersionsException(Throwable cause) {
		super(cause);

		_version = 0;
	}

	public double getVersion() {
		return _version;
	}

	private final double _version;

}