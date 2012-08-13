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
import java.io.Serializable;

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
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Shuyang Zhou
 */
public class MetaInfoCacheServletResponse extends HttpServletResponseWrapper {

	@SuppressWarnings("deprecation")
	public static void finishResponse(
			MetaInfoDataBag metaInfoDataBag, HttpServletResponse response)
		throws IOException {

		if (response.isCommitted()) {
			return;
		}

		for (Map.Entry<String, Set<Header>> entry :
			metaInfoDataBag._headers.entrySet()) {

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

		if (metaInfoDataBag._location != null) {
			response.sendRedirect(metaInfoDataBag._location);
		}
		else if (metaInfoDataBag._error) {
			response.sendError(
				metaInfoDataBag._status, metaInfoDataBag._errorMessage);
		}
		else {
			if (metaInfoDataBag._charsetName != null) {
				response.setCharacterEncoding(metaInfoDataBag._charsetName);
			}

			if (metaInfoDataBag._contentLength != -1) {
				response.setContentLength(metaInfoDataBag._contentLength);
			}

			if (metaInfoDataBag._contentType != null) {
				response.setContentType(metaInfoDataBag._contentType);
			}

			if (metaInfoDataBag._locale != null) {
				response.setLocale(metaInfoDataBag._locale);
			}

			if (metaInfoDataBag._status != SC_OK) {
				response.setStatus(
					metaInfoDataBag._status, metaInfoDataBag._statusMessage);
			}
		}
	}

	public MetaInfoCacheServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void addCookie(Cookie cookie) {

		// The correct header name should be "Set-Cookie". Otherwise, the method
		// containsHeader will not able to detect cookies with the correct
		// header name.

		Set<Header> values = _metaInfoDataBag._headers.get(
			HttpHeaders.SET_COOKIE);

		if (values == null) {
			values = new HashSet<Header>();

			_metaInfoDataBag._headers.put(HttpHeaders.SET_COOKIE, values);
		}

		Header header = new Header(cookie);

		values.add(header);

		super.addCookie(cookie);
	}

	@Override
	public void addDateHeader(String name, long value) {
		Set<Header> values = _metaInfoDataBag._headers.get(name);

		if (values == null) {
			values = new HashSet<Header>();

			_metaInfoDataBag._headers.put(name, values);
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

		Set<Header> values = _metaInfoDataBag._headers.get(name);

		if (values == null) {
			values = new HashSet<Header>();

			_metaInfoDataBag._headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addHeader(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		Set<Header> values = _metaInfoDataBag._headers.get(name);

		if (values == null) {
			values = new HashSet<Header>();

			_metaInfoDataBag._headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addIntHeader(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return _metaInfoDataBag._headers.containsKey(name);
	}

	@SuppressWarnings("deprecation")
	public void finishResponse() throws IOException {
		HttpServletResponse response = (HttpServletResponse)getResponse();

		finishResponse(_metaInfoDataBag, response);

		_committed = true;
	}

	@Override
	public void flushBuffer() {
		_committed = true;
	}

	@Override
	public int getBufferSize() {
		return _metaInfoDataBag._bufferSize;
	}

	@Override
	public String getCharacterEncoding() {

		// We are supposed to default to ISO-8859-1 based on the Servlet
		// specification. However, most application servers honors the system
		// property "file.encoding". Using the system default character gives us
		// better application server compatibility.

		if (_metaInfoDataBag._charsetName == null) {
			return StringPool.DEFAULT_CHARSET_NAME;
		}

		return _metaInfoDataBag._charsetName;
	}

	@Override
	public String getContentType() {
		String contentType = _metaInfoDataBag._contentType;

		if ((contentType != null) && (_metaInfoDataBag._charsetName != null)) {
			contentType = contentType.concat("; charset=").concat(
				_metaInfoDataBag._charsetName);
		}

		return contentType;
	}

	/**
	 * When the header for this given name is "Cookie", the return value cannot
	 * be used for the "Set-Cookie" header. The string representation for
	 * "Cookie" is application server specific. The only safe way to add the
	 * header is to call {@link HttpServletResponse#addCookie(Cookie)}.
	 */
	public String getHeader(String name) {
		Set<Header> values = _metaInfoDataBag._headers.get(name);

		if (values == null) {
			return null;
		}

		Header header = values.iterator().next();

		return header.toString();
	}

	public Collection<String> getHeaderNames() {
		return _metaInfoDataBag._headers.keySet();
	}

	public Map<String, Set<Header>> getHeaders() {
		return _metaInfoDataBag._headers;
	}

	/**
	 * When the header for this given name is "Cookie", the return value cannot
	 * be used for the "Set-Cookie" header. The string representation for
	 * "Cookie" is application server specific. The only safe way to add the
	 * header is to call {@link HttpServletResponse#addCookie(Cookie)}.
	 */
	public Collection<String> getHeaders(String name) {
		Set<Header> values = _metaInfoDataBag._headers.get(name);

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
		return _metaInfoDataBag._locale;
	}

	public MetaInfoDataBag getMetaInfoDataBag() {
		return _metaInfoDataBag;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		calledGetOutputStream = true;

		return super.getOutputStream();
	}

	public int getStatus() {
		return _metaInfoDataBag._status;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		calledGetWriter = true;

		return super.getWriter();
	}

	@Override
	public boolean isCommitted() {
		ServletResponse servletResponse = getResponse();

		return _committed || servletResponse.isCommitted();
	}

	@Override
	public void reset() {
		if (isCommitted()) {
			throw new IllegalStateException("Reset after commit");
		}

		// No need to reset _error, _errorMessage and _location, because setting
		// them requires commit.

		_metaInfoDataBag._charsetName = null;
		_metaInfoDataBag._contentLength = -1;
		_metaInfoDataBag._contentType = null;
		_metaInfoDataBag._headers.clear();
		_metaInfoDataBag._locale = null;
		_metaInfoDataBag._status = SC_OK;
		_metaInfoDataBag._statusMessage = null;

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

		_metaInfoDataBag._error = true;
		_metaInfoDataBag._errorMessage = errorMessage;
		_metaInfoDataBag._status = status;

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

		_metaInfoDataBag._location = location;

		_committed = true;

		super.sendRedirect(location);
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (isCommitted()) {
			throw new IllegalStateException("Set buffer size after commit");
		}

		_metaInfoDataBag._bufferSize = bufferSize;

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

		_metaInfoDataBag._charsetName = charsetName;

		super.setCharacterEncoding(charsetName);
	}

	@Override
	public void setContentLength(int contentLength) {
		if (isCommitted()) {
			return;
		}

		_metaInfoDataBag._contentLength = contentLength;

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

			_metaInfoDataBag._contentType = firstPart.trim();

			index = contentType.indexOf("charset=");

			if (index != -1) {
				String charsetName = contentType.substring(index + 8);

				charsetName = charsetName.trim();

				setCharacterEncoding(charsetName);
			}
			else {
				_metaInfoDataBag._charsetName = null;
			}
		}
		else {
			_metaInfoDataBag._contentType = contentType;

			_metaInfoDataBag._charsetName = null;
		}

		super.setContentType(contentType);
	}

	@Override
	public void setDateHeader(String name, long value) {
		Set<Header> values = new HashSet<Header>();

		_metaInfoDataBag._headers.put(name, values);

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

		_metaInfoDataBag._headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setHeader(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		Set<Header> values = new HashSet<Header>();

		_metaInfoDataBag._headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setIntHeader(name, value);
	}

	@Override
	public void setLocale(Locale locale) {
		if (isCommitted()) {
			return;
		}

		_metaInfoDataBag._locale = locale;

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

		_metaInfoDataBag._status = status;
		_metaInfoDataBag._statusMessage = statusMessage;

		super.setStatus(status, statusMessage);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{bufferSize=");
		sb.append(_metaInfoDataBag._bufferSize);
		sb.append(", charsetName=");
		sb.append(_metaInfoDataBag._charsetName);
		sb.append(", committed=");
		sb.append(_committed);
		sb.append(", contentLength=");
		sb.append(_metaInfoDataBag._contentLength);
		sb.append(", contentType=");
		sb.append(_metaInfoDataBag._contentType);
		sb.append(", error=");
		sb.append(_metaInfoDataBag._error);
		sb.append(", errorMessage=");
		sb.append(_metaInfoDataBag._errorMessage);
		sb.append(", headers=");
		sb.append(_metaInfoDataBag._headers);
		sb.append(", location=");
		sb.append(_metaInfoDataBag._location);
		sb.append(", locale=");
		sb.append(_metaInfoDataBag._locale);
		sb.append(", status=");
		sb.append(_metaInfoDataBag._status);
		sb.append("}");

		return sb.toString();
	}

	public static class MetaInfoDataBag implements Serializable {

		private int _bufferSize;
		private String _charsetName;
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

	/**
	 * Stub method for subclass to provide buffer resetting logic.
	 *
	 * @param nullOutReferences whether to reset flags. It is not directly used
	 *        by this class. Subclasses with an actual buffer may behave
	 *        differently depending on the value of this parameter.
	 */
	protected void resetBuffer(boolean nullOutReferences) {
		super.resetBuffer();
	}

	protected boolean calledGetOutputStream;
	protected boolean calledGetWriter;

	private boolean _committed;
	private MetaInfoDataBag _metaInfoDataBag = new MetaInfoDataBag();

}