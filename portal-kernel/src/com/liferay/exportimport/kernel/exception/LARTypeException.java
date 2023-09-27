/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public LARTypeException(int type, String msg, Throwable throwable) {
		this(msg, throwable);

		_type = type;
	}

	public LARTypeException(int type, Throwable throwable) {
		this(throwable);

		_type = type;
	}

	public LARTypeException(String msg) {
		super(msg);
	}

	public LARTypeException(String actualLARType, String[] expectedLARTypes) {
		_actualLARType = actualLARType;
		_expectedLARTypes = expectedLARTypes;
	}

	public LARTypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LARTypeException(Throwable throwable) {
		super(throwable);
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