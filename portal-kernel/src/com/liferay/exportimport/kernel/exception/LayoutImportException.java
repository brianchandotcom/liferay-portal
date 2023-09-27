/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public LayoutImportException(int type, String msg, Throwable throwable) {
		this(msg, throwable);

		_type = type;
	}

	public LayoutImportException(int type, Throwable throwable) {
		this(throwable);

		_type = type;
	}

	public LayoutImportException(String msg) {
		super(msg);
	}

	public LayoutImportException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LayoutImportException(Throwable throwable) {
		super(throwable);
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