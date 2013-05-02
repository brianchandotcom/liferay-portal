/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class Header implements Serializable {

	public Header(Cookie cookie) {
		if (cookie == null) {
			throw new IllegalArgumentException("Cookie is null");
		}

		_type = Type.COOKIE;

		_cookieValue = cookie;
	}

	public Header(int integer) {
		_type = Type.INTEGER;

		_intValue = integer;
	}

	public Header(long date) {
		_type = Type.DATE;

		_dateValue = date;
	}

	public Header(String string) {
		if (string == null) {
			throw new IllegalArgumentException("String is null");
		}

		_type = Type.STRING;

		_stringValue = string;
	}

	public void addToResponse(String key, HttpServletResponse response) {
		if (_type == Type.COOKIE) {
			response.addCookie(_cookieValue);
		}
		else if (_type == Type.DATE) {
			response.addDateHeader(key, _dateValue);
		}
		else if (_type == Type.INTEGER) {
			response.addIntHeader(key, _intValue);
		}
		else if (_type == Type.STRING) {
			response.addHeader(key, _stringValue);
		}
		else {
			throw new IllegalStateException("Invalid type " + _type);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Header)) {
			return false;
		}

		Header header = (Header)obj;

		if (_type != header._type) {
			return false;
		}

		if (_type == Type.COOKIE) {
			return _equals(_cookieValue, header._cookieValue);
		}
		else if (_type == Type.DATE) {
			return _dateValue == header._dateValue;
		}
		else if (_type == Type.INTEGER) {
			return _intValue == header._intValue;
		}
		else if (_type == Type.STRING) {
			return _stringValue.equals(header._stringValue);
		}
		else {
			throw new IllegalStateException("Invalid type " + _type);
		}
	}

	@Override
	public int hashCode() {
		if (_type == Type.COOKIE) {
			return _hashCode(_cookieValue);
		}
		else if (_type == Type.DATE) {
			return (int)(_dateValue ^ (_dateValue >>> 32));
		}
		else if (_type == Type.INTEGER) {
			return _intValue;
		}
		else if (_type == Type.STRING) {
			return _stringValue.hashCode();
		}
		else {
			throw new IllegalStateException("Invalid type " + _type);
		}
	}

	public void setToResponse(String key, HttpServletResponse response) {
		if (_type == Type.COOKIE) {
			response.addCookie(_cookieValue);
		}
		else if (_type == Type.DATE) {
			response.setDateHeader(key, _dateValue);
		}
		else if (_type == Type.INTEGER) {
			response.setIntHeader(key, _intValue);
		}
		else if (_type == Type.STRING) {
			response.setHeader(key, _stringValue);
		}
		else {
			throw new IllegalStateException("Invalid type " + _type);
		}
	}

	@Override
	public String toString() {
		if (_type == Type.COOKIE) {
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
		}
		else if (_type == Type.DATE) {
			return String.valueOf(_dateValue);
		}
		else if (_type == Type.INTEGER) {
			return String.valueOf(_intValue);
		}
		else if (_type == Type.STRING) {
			return _stringValue;
		}
		else {
			throw new IllegalStateException("Invalid type " + _type);
		}
	}

	private boolean _equals(Cookie cookie1, Cookie cookie2) {
		if (cookie1 == cookie2) {
			return true;
		}

		if (!Validator.equals(cookie1.getComment(), cookie2.getComment()) ||
			!Validator.equals(cookie1.getDomain(), cookie2.getDomain()) ||
			(cookie1.getMaxAge() != cookie2.getMaxAge()) ||
			!Validator.equals(cookie1.getName(), cookie2.getName()) ||
			!Validator.equals(cookie1.getPath(), cookie2.getPath()) ||
			(cookie1.getSecure() != cookie2.getSecure()) ||
			!Validator.equals(cookie1.getValue(), cookie2.getValue()) ||
			(cookie1.getVersion() != cookie2.getVersion())) {

			return false;
		}

		return true;
	}

	private int _hashCode(Cookie cookie) {
		int hashCode = HashUtil.hash(0, cookie.getComment());

		hashCode = HashUtil.hash(hashCode, cookie.getDomain());
		hashCode = HashUtil.hash(hashCode, cookie.getMaxAge());
		hashCode = HashUtil.hash(hashCode, cookie.getName());
		hashCode = HashUtil.hash(hashCode, cookie.getPath());
		hashCode = HashUtil.hash(hashCode, cookie.getSecure());
		hashCode = HashUtil.hash(hashCode, cookie.getValue());
		hashCode = HashUtil.hash(hashCode, cookie.getVersion());

		return hashCode;
	}

	private Cookie _cookieValue;
	private long _dateValue;
	private int _intValue;
	private String _stringValue;
	private Type _type;

	private static enum Type {

		COOKIE, DATE, INTEGER, STRING

	}

}