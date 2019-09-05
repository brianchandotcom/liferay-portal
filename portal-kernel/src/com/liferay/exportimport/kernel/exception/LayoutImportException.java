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
 * @author Brian Wing Shun Chan
 */
public class LayoutImportException extends PortalException {

	public static final int TYPE_DEFAULT = 0;

	public static final int TYPE_WRONG_BUILD_NUMBER = 1;

	public static final int TYPE_WRONG_LAR_SCHEMA_VERSION = 2;

	public static final int TYPE_WRONG_PORTLET_SCHEMA_VERSION = 3;

	public LayoutImportException() {
	}

	public LayoutImportException(int type) {
		_type = type;
	}

	public LayoutImportException(int type, Object... arguments) {
		_type = type;
		_arguments = arguments;
	}

	public LayoutImportException(int type, String msg) {
		this(msg);

		_type = type;
	}

	public LayoutImportException(int type, String msg, Throwable cause) {
		this(msg, cause);

		_type = type;
	}

	public LayoutImportException(int type, Throwable cause) {
		this(cause);

		_type = type;
	}

	public LayoutImportException(String msg) {
		super(msg);
	}

	public LayoutImportException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LayoutImportException(Throwable cause) {
		super(cause);
	}

	public Object[] getArguments() {
		return _arguments;
	}

	public int getType() {
		return _type;
	}

	public void setArguments(Object[] arguments) {
		_arguments = arguments;
	}

	public void setType(int type) {
		_type = type;
	}

	private Object[] _arguments = {};
	private int _type = TYPE_DEFAULT;

}