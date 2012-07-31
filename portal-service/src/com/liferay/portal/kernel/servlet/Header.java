/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class Header implements Serializable {

	private static enum Type {
		COOKIE, DATE, INTEGER, STRING
	}

	public Header(Cookie cookie) {
		_type = Type.COOKIE;

		_cookieValue = cookie;
	}

	public Header(long date) {
		_type = Type.DATE;

		_dateValue = date;
	}

	public Header(int integer) {
		_type = Type.INTEGER;

		_intValue = integer;
	}

	public Header(String string) {
		_type = Type.STRING;

		_stringValue = string;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Header)) {
			return false;
		}

		Header header = (Header)obj;

		if (_type != header._type) {
			return false;
		}

		String string = toString();

		return string.equals(header.toString());
	}

	public void setToResponse(String key, HttpServletResponse response) {
		switch (_type) {
			case COOKIE :
				response.addCookie(_cookieValue);

				break;
			case DATE :
				response.setDateHeader(key, _dateValue);

				break;
			case INTEGER :
				response.setIntHeader(key, _intValue);

				break;
			case STRING :
				response.setHeader(key, _stringValue);

				break;
		}
	}

	@Override
	public String toString() {
		switch (_type) {
			case COOKIE :
				StringBundler sb = new StringBundler(17);

				sb.append("{comment=");
				sb.append(_cookieValue.getComment());
				sb.append(", domain=");
				sb.append(_cookieValue.getDomain());
				sb.append(", maxAge=");
				sb.append(_cookieValue.getMaxAge());
				sb.append(", name=");
				sb.append(_cookieValue.getName());
				sb.append(", path=");
				sb.append(_cookieValue.getPath());
				sb.append(", secure=");
				sb.append(_cookieValue.getSecure());
				sb.append(", value=");
				sb.append(_cookieValue.getValue());
				sb.append(", version=");
				sb.append(_cookieValue.getVersion());
				sb.append("}");

				return sb.toString();

			case DATE :
				return String.valueOf(_dateValue);

			case INTEGER :
				return String.valueOf(_intValue);

			case STRING :
				return _stringValue;

			default :
				throw new IllegalStateException("Unknow Type " + _type);
		}
	}

	private Cookie _cookieValue;
	private long _dateValue;
	private int _intValue;
	private String _stringValue;
	private Type _type;

}