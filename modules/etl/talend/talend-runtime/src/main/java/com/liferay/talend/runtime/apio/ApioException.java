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

package com.liferay.talend.runtime.apio;

import java.io.IOException;

/**
 * @author Zoltán Takács
 */
public class ApioException extends IOException {

	public ApioException(int code, String message) {
		super(message);

		_code = code;
	}

	public ApioException(int code, String message, Throwable cause) {
		super(message, cause);

		_code = code;
	}

	public ApioException(Throwable cause) {
		super(cause);

		_code = _DEFAULT_ERROR_CODE;
	}

	public int getCode() {
		return _code;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");

		if (getCause() != null) {
			sb.append("cause=");
			sb.append(getCause());
			sb.append(", ");
		}

		sb.append("code=");
		sb.append(_code);
		sb.append(", message=");
		sb.append(getMessage());
		sb.append("}");

		return sb.toString();
	}

	private static final int _DEFAULT_ERROR_CODE = 400;

	private final int _code;

}