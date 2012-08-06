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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Shuyang Zhou
 */
public class MetaInfoCacheServletResponse extends HttpServletResponseWrapper {

	public MetaInfoCacheServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addCookie(Cookie cookie) {
		// For cookies, the correct header name should be "Set-Cookie",
		// otherwise containsHeader() won't able to detect cookies with right
		// header name.

		Set<Header> values = _headers.get("Set-Cookie");

		if (values == null) {
			values = new HashSet<Header>();

			_headers.put("Set-Cookie", values);
		}

		Header header = new Header(cookie);

		values.add(header);

		super.addCookie(cookie);
	}

	@Override
	public void addDateHeader(String name, long value) {
		Set<Header> values = _headers.get(name);

		if (values == null) {
			values = new HashSet<Header>();

			_headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addDateHeader(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		if (name.equals(HttpHeaders.CONTENT_TYPE)) {
			setContentType(value);

			return;
		}

		Set<Header> values = _headers.get(name);

		if (values == null) {
			values = new HashSet<Header>();

			_headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addHeader(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		Set<Header> values = _headers.get(name);

		if (values == null) {
			values = new HashSet<Header>();

			_headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addIntHeader(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return _headers.containsKey(name);
	}

	public void finishResponse() throws IOException {
		HttpServletResponse response = (HttpServletResponse)getResponse();

		if (response.isCommitted()) {
			return;
		}

		// Add Headers for all cases
		for (Map.Entry<String, Set<Header>> entry : _headers.entrySet()) {
			String headerKey = entry.getKey();

			Set<Header> headers = entry.getValue();

			boolean first = true;

			for (Header header : headers) {
				if (first) {
					header.setToResponse(headerKey, response);

					first = false;
				}
				else {
					header.addToResponse(headerKey, response);
				}
			}
		}

		if (_location != null) {
			// sendRedirect()

			response.sendRedirect(_location);
		}
		else if (_error) {
			// sendError()

			response.sendError(_status, _errorMessage);
		}
		else {
			// Regular

			if (_charsetName != null) {
				response.setCharacterEncoding(_charsetName);
			}

			if (_contentType != null) {
				response.setContentType(_contentType);
			}

			if (_locale != null) {
				response.setLocale(_locale);
			}

			if (_contentLength != -1) {
				response.setContentLength(_contentLength);
			}

			if (_status != SC_OK) {
				response.setStatus(_status, _statusMessage);
			}
		}

		_committed = true;
	}

	@Override
	public void flushBuffer() {
		_committed = true;
	}

	@Override
	public int getBufferSize() {
		return _bufferSize;
	}

	@Override
	public String getCharacterEncoding() {
		// According to the Servlet Spec, we suppose to default to ISO-8859-1,
		// However most app-server honors the "file.encoding" system property.
		// So using system default charset should have better app-server
		// compatibility. If somehow you need to follow the servlet spec
		// exactly, add system property file.encoding=ISO-8859-1
		if (_charsetName == null) {
			return StringPool.DEFAULT_CHARSET_NAME;
		}
		else {
			return _charsetName;
		}
	}

	@Override
	public String getContentType() {
		String contentType = _contentType;

		if ((contentType != null) && (_charsetName != null)) {

			contentType = contentType.concat("; charset=").concat(_charsetName);
		}

		return contentType;
	}

	/**
	 * Servlet 3.0 support
	 *
	 * Warning! When the header for this given name is Cookie, the return value
	 * can not be used for Set-Cookie header. The String representation for
	 * Cookie is app-server depended. The only safe way to add the header is to
	 * call HttpServletResponse.addCookie().
	 */
	public String getHeader(String name) {
		Set<Header> values = _headers.get(name);

		if (values == null) {
			return null;
		}

		Header header = values.iterator().next();

		return header.toString();
	}

	/**
	 * Servlet 3.0 support
	 */
	public Collection<String> getHeaderNames() {
		return _headers.keySet();
	}

	public Map<String, Set<Header>> getHeaders() {
		return _headers;
	}

	/**
	 * Servlet 3.0 support
	 * Warning! When the header for this given name is Cookie, the return values
	 * can not be used for Set-Cookie header. The String representation for
	 * Cookie is app-server depended. The only safe way to add the header is to
	 * call HttpServletResponse.addCookie().
	 */
	public Collection<String> getHeaders(String name) {
		Set<Header> values = _headers.get(name);

		if (values == null) {
			return Collections.emptyList();
		}

		List<String> stringValues = new ArrayList<String>();

		for (Header header : values) {
			stringValues.add(header.toString());
		}

		return stringValues;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		calledGetOutputStream = true;

		return super.getOutputStream();
	}

	/**
	 * Servlet 3.0 support
	 */
	public int getStatus() {
		return _status;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		calledGetWriter = true;

		return super.getWriter();
	}

	@Override
	public boolean isCommitted() {
		return _committed || getResponse().isCommitted();
	}

	@Override
	public void reset() {
		if (isCommitted()) {
			throw new IllegalStateException("Reset after commit");
		}

		_charsetName = null;
		_contentLength = -1;
		_contentType = null;
		_headers.clear();
		_locale = null;
		_status = SC_OK;
		_statusMessage = null;

		// No need to reset _error, _errorMessage and _location, because setting
		// them requires commit.

		// calledGetOutputStream and calledGetWriter should be cleared by
		// resetBuffer() in subclass.

		resetBuffer();

		super.reset();
	}

	@Override
	public void resetBuffer() {
		if (isCommitted()) {
			throw new IllegalStateException("Reset buffer after commit");
		}

		resetBuffer(false);
	}

	@Override
	public void sendError(int status) throws IOException {
		sendError(status, null);
	}

	@Override
	public void sendError(int status, String errorMessage) throws IOException {
		if (isCommitted()) {
			throw new IllegalStateException("Send error after commit");
		}

		_status = status;
		_error = true;
		_errorMessage = errorMessage;

		resetBuffer();

		_committed = true;

		super.sendError(status, errorMessage);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		if (isCommitted()) {
			throw new IllegalStateException("Send redirect after commit");
		}

		resetBuffer(true);

		_location = location;

		_committed = true;

		super.sendRedirect(location);
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (isCommitted()) {
			throw new IllegalStateException("Set buffer size after commit");
		}

		_bufferSize = bufferSize;

		super.setBufferSize(bufferSize);
	}

	@Override
	public void setCharacterEncoding(String charsetName) {
		if (isCommitted()) {
			return;
		}

		if (calledGetWriter) {
			return;
		}

		if (charsetName == null) {
			return;
		}

		_charsetName = charsetName;

		super.setCharacterEncoding(charsetName);
	}

	@Override
	public void setContentLength(int contentLength) {
		if (isCommitted()) {
			return;
		}

		_contentLength = contentLength;

		super.setContentLength(contentLength);
	}

	@Override
	public void setContentType(String contentType) {
		if (isCommitted()) {
			return;
		}

		if (contentType == null) {
			return;
		}

		int index = contentType.indexOf(CharPool.SEMICOLON);

		if (index != -1) {
			String firstPart = contentType.substring(0, index);

			_contentType = firstPart.trim();

			index = contentType.indexOf("charset=");

			if (index != -1) {
				String charsetName = contentType.substring(index + 8).trim();

				setCharacterEncoding(charsetName);
			}
			else {
				_charsetName = null;
			}
		}
		else {
			_contentType = contentType;
			_charsetName = null;
		}

		super.setContentType(contentType);
	}

	@Override
	public void setDateHeader(String name, long value) {
		Set<Header> values = new HashSet<Header>();

		_headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setDateHeader(name, value);
	}

	@Override
	public void setHeader(String name, String value) {
		if (name.equals(HttpHeaders.CONTENT_TYPE)) {
			setContentType(value);

			return;
		}

		Set<Header> values = new HashSet<Header>();

		_headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setHeader(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		Set<Header> values = new HashSet<Header>();

		_headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setIntHeader(name, value);
	}

	@Override
	public void setLocale(Locale locale) {
		if (isCommitted()) {
			return;
		}

		_locale = locale;

		super.setLocale(locale);
	}

	@Override
	public void setStatus(int status) {
		setStatus(status, null);
	}

	@Override
	public void setStatus(int status, String statusMessage) {
		if (isCommitted()) {
			return;
		}

		_status = status;
		_statusMessage = statusMessage;

		super.setStatus(status, statusMessage);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{status=");
		sb.append(_status);
		sb.append(", headers=");
		sb.append(_headers);
		sb.append(", location=");
		sb.append(_location);
		sb.append(", error=");
		sb.append(_error);
		sb.append(", errorMessage=");
		sb.append(_errorMessage);
		sb.append(", charsetName=");
		sb.append(_charsetName);
		sb.append(", contentType=");
		sb.append(_contentType);
		sb.append(", locale=");
		sb.append(_locale);
		sb.append(", bufferSize=");
		sb.append(_bufferSize);
		sb.append(", contentLength=");
		sb.append(_contentLength);
		sb.append(", committed=");
		sb.append(_committed);
		sb.append("||");
		sb.append(getResponse().isCommitted());
		sb.append("}");

		return sb.toString();
	}

	/**
	 * Stub method for subclass to provide buffer resetting logic. By default
	 * delegating to super.
	 *
	 * @param nullOutReferences Indicating whether to reset flags, not used
	 *		  this class. Subclass with actual buffer may use this to behave
	 *		  differently.
	 */
	protected void resetBuffer(boolean nullOutReferences) {
		super.resetBuffer();
	}

	protected boolean calledGetOutputStream;
	protected boolean calledGetWriter;

	private int _bufferSize;
	private String _charsetName;
	private boolean _committed;
	private int _contentLength = -1;
	private String _contentType;
	private boolean _error;
	private String _errorMessage;
	private Map<String, Set<Header>> _headers =
		new HashMap<String, Set<Header>>();
	private Locale _locale;
	private String _location;
	private int _status = SC_OK;
	private String _statusMessage;

}