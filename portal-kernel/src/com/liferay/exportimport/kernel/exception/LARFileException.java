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

package com.liferay.exportimport.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Raymond Augé
 */
public class LARFileException extends PortalException {

	public static final int TYPE_DEFAULT = 0;

	public static final int TYPE_INVALID_MANIFEST = 2;

	public static final int TYPE_MISSING_MANIFEST = 1;

	public LARFileException() {
	}

	public LARFileException(int type) {
		_type = type;
	}

	public LARFileException(int type, String msg) {
		this(msg);

		_type = type;
	}

	public LARFileException(int type, String msg, Throwable cause) {
		this(msg, cause);

		_type = type;
	}

	public LARFileException(int type, Throwable cause) {
		this(cause);

		_type = type;
	}

	public LARFileException(String msg) {
		super(msg);
	}

	public LARFileException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LARFileException(Throwable cause) {
		super(cause);
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private int _type = TYPE_DEFAULT;

}