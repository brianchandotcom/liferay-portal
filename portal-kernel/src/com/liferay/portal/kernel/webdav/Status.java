/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webdav;

/**
 * @author Brian Wing Shun Chan
 */
public class Status {

	public Status(int code) {
		this(null, code);
	}

	public Status(Object object, int code) {
		_object = object;
		_code = code;
	}

	public int getCode() {
		return _code;
	}

	public Object getObject() {
		return _object;
	}

	private final int _code;
	private final Object _object;

}