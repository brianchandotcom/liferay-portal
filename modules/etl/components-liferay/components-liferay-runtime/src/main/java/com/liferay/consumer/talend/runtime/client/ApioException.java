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

package com.liferay.consumer.talend.runtime.client;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Zoltán Takács
 */
public class ApioException extends Exception {

	public ApioException(int code, String message) {
		super(message);
		_code = Integer.valueOf(code).toString();
	}

	public ApioException(int code, String message, Throwable cause) {
		super(message, cause);
		_code = Integer.valueOf(code).toString();
	}

	public ApioException(String message) {
		super(message);
	}

	public ApioException(String code, String message) {
		super(message);
		_code = code;
	}

	public ApioException(String code, String message, Throwable cause) {
		super(message, cause);
		_code = code;
	}

	public ApioException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApioException(Throwable cause) {
		super(cause);
	}

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		_code = code;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("ApioException{");

		if (!StringUtils.isEmpty(_code)) {
			sb.append(", code='");
			sb.append(_code).append('\'');
		}

		sb.append(", message='");
		sb.append(getMessage());
		sb.append('\'');

		if (getCause() != null) {
			sb.append(", cause='");
			sb.append(getCause());
			sb.append('\'');
		}

		sb.append('}');

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private String _code = "";

}