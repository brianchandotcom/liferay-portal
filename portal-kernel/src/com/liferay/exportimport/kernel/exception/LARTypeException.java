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
public class LARTypeException extends PortalException {

	public static final int TYPE_COMPANY_GROUP = 5;

	public static final int TYPE_DEFAULT = 0;

	public static final int TYPE_LAYOUT_PROTOTYPE = 1;

	public static final int TYPE_LAYOUT_SET = 2;

	public static final int TYPE_LAYOUT_SET_PROTOTYPE = 3;

	public LARTypeException() {
	}

	public LARTypeException(int type) {
		_type = type;
	}

	public LARTypeException(int type, String msg) {
		this(msg);

		_type = type;
	}

	public LARTypeException(int type, String msg, Throwable cause) {
		this(msg, cause);

		_type = type;
	}

	public LARTypeException(int type, Throwable cause) {
		this(cause);

		_type = type;
	}

	public LARTypeException(String msg) {
		super(msg);
	}

	public LARTypeException(String actualLARType, String[] expectedLARTypes) {
		_actualLARType = actualLARType;
		_expectedLARTypes = expectedLARTypes;
	}

	public LARTypeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LARTypeException(Throwable cause) {
		super(cause);
	}

	public String getActualLARType() {
		return _actualLARType;
	}

	public String[] getExpectedLARTypes() {
		return _expectedLARTypes;
	}

	public int getType() {
		return _type;
	}

	public void setActualLARType(String actualLARType) {
		_actualLARType = actualLARType;
	}

	public void setExpectedLARTypes(String[] expectedLARTypes) {
		_expectedLARTypes = expectedLARTypes;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _actualLARType;
	private String[] _expectedLARTypes;
	private int _type = TYPE_DEFAULT;

}