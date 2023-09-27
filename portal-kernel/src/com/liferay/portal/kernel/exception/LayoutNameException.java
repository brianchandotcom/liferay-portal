/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutNameException extends PortalException {

	public static final int TOO_LONG = 1;

	public static final int TOO_SHORT = 2;

	public LayoutNameException() {
		_type = TOO_SHORT;
	}

	public LayoutNameException(int type) {
		_type = type;
	}

	public LayoutNameException(String msg) {
		super(msg);

		_type = TOO_SHORT;
	}

	public LayoutNameException(String msg, Throwable throwable) {
		super(msg, throwable);

		_type = TOO_SHORT;
	}

	public LayoutNameException(Throwable throwable) {
		super(throwable);

		_type = TOO_SHORT;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}