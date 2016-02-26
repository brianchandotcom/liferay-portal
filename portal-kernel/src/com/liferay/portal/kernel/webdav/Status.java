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