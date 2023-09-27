/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchListTypeException extends NoSuchModelException {

	public NoSuchListTypeException() {
	}

	public NoSuchListTypeException(String msg) {
		super(msg);
	}

	public NoSuchListTypeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchListTypeException(Throwable throwable) {
		super(throwable);
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _type;

}