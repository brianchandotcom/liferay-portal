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

package com.liferay.portal.kernel.servlet;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
			MetaData metaInfoDataBag, HttpServletResponse response)
		throws IOException {

		if (response.isCommitted()) {
			return;
		}

		resetThrough(response);

		for (Map.Entry<String, Set<Header>> entry :
				metaInfoDataBag._headers.entrySet()) {

			String key = entry.getKey();

			boolean first = true;

			for (Header header : entry.getValue()) {
				if (first) {
					header.setToResponse(key, response);

					first = false;
				}
				else {
					header.addToResponse(key, response);
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

		Set<Header> values = _metaData._headers.get(HttpHeaders.SET_COOKIE);

		if (values == null) {
			values = new HashSet<>();

			_metaData._headers.put(HttpHeaders.SET_COOKIE, values);
		}

		Header header = new Header(cookie);

		values.add(header);

		super.addCookie(cookie);
	}

	@Override
	public void addDateHeader(String name, long value) {
		Set<Header> values = _metaData._headers.get(name);

		if (values == null) {
			values = new HashSet<>();

			_metaData._headers.put(name, values);
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

		Set<Header> values = _metaData._headers.get(name);

		if (values == null) {
			values = new HashSet<>();

			_metaData._headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addHeader(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		Set<Header> values = _metaData._headers.get(name);

		if (values == null) {
			values = new HashSet<>();

			_metaData._headers.put(name, values);
		}

		Header header = new Header(value);

		values.add(header);

		super.addIntHeader(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return _metaData._headers.containsKey(name);
	}

	public void finishResponse() throws IOException {
		finishResponse(_metaData, (HttpServletResponse)getResponse());

		_committed = true;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #finishResponse()}}
	 */
	@Deprecated
	public void finishResponse(boolean reapplyMetaData) throws IOException {
		HttpServletResponse response = (HttpServletResponse)getResponse();

		if (reapplyMetaData) {
			finishResponse(_metaData, response);
		}

		_committed = true;
	}

	@Override
	public void flushBuffer() throws IOException {
		_committed = true;
	}

	@Override
	public int getBufferSize() {
		return _metaData._bufferSize;
	}

	@Override
	public String getCharacterEncoding() {

		// We are supposed to default to ISO-8859-1 based on the Servlet
		// specification. However, most application servers honors the system
		// property "file.encoding". Using the system default character gives us
		// better application server compatibility.

		if (_metaData._charsetName == null) {
			return StringPool.DEFAULT_CHARSET_NAME;
		}

		return _metaData._charsetName;
	}

	@Override
	public String getContentType() {
		String contentType = _metaData._contentType;

		if ((contentType != null) && (_metaData._charsetName != null)) {
			contentType = contentType.concat("; charset=").concat(
				_metaData._charsetName);
		}

		return contentType;
	}

	/**
	 * When the header for this given name is "Cookie", the return value cannot
	 * be used for the "Set-Cookie" header. The string representation for
	 * "Cookie" is application server specific. The only safe way to add the
	 * header is to call {@link HttpServletResponse#addCookie(Cookie)}.
	 */
	@Override
	public String getHeader(String name) {
		Set<Header> values = _metaData._headers.get(name);

		if (values == null) {
			return null;
		}

		Iterator<Header> iterator = values.iterator();

		Header header = iterator.next();

		return header.toString();
	}

	@Override
	public Collection<String> getHeaderNames() {
		return _metaData._headers.keySet();
	}

	public Map<String, Set<Header>> getHeaders() {
		return _metaData._headers;
	}

	/**
	 * When the header for this given name is "Cookie", the return value cannot
	 * be used for the "Set-Cookie" header. The string representation for
	 * "Cookie" is application server specific. The only safe way to add the
	 * header is to call {@link HttpServletResponse#addCookie(Cookie)}.
	 */
	@Override
	public Collection<String> getHeaders(String name) {
		Set<Header> values = _metaData._headers.get(name);

		if (values == null) {
			return Collections.emptyList();
		}

		List<String> stringValues = new ArrayList<>();

		for (Header header : values) {
			stringValues.add(header.toString());
		}

		return stringValues;
	}

	@Override
	public Locale getLocale() {
		return _metaData._locale;
	}

	public MetaData getMetaData() {
		return _metaData;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		calledGetOutputStream = true;

		return super.getOutputStream();
	}

	@Override
	public int getStatus() {
		return _metaData._status;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		calledGetWriter = true;

		return super.getWriter();
	}

	@Override
	public boolean isCommitted() {
		ServletResponse servletResponse = getResponse();

		if (_committed || servletResponse.isCommitted()) {
			return true;
		}

		return false;
	}

	@Override
	public void reset() {
		if (isCommitted()) {
			throw new IllegalStateException("Reset after commit");
		}

		// No need to reset _error, _errorMessage and _location, because setting
		// them requires commit.

		_metaData._charsetName = null;
		_metaData._contentLength = -1;
		_metaData._contentType = null;
		_metaData._headers.clear();
		_metaData._locale = null;
		_metaData._status = SC_OK;
		_metaData._statusMessage = null;

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
		if (isCommitted()) {
			throw new IllegalStateException("Send error after commit");
		}

		_metaData._error = true;
		_metaData._status = status;

		resetBuffer();

		_committed = true;

		super.sendError(status);
	}

	@Override
	public void sendError(int status, String errorMessage) throws IOException {
		if (isCommitted()) {
			throw new IllegalStateException("Send error after commit");
		}

		_metaData._error = true;
		_metaData._errorMessage = errorMessage;
		_metaData._status = status;

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

		setStatus(SC_FOUND);

		_metaData._location = location;

		_committed = true;

		super.sendRedirect(location);
	}

	@Override
	public void setBufferSize(int bufferSize) {
		if (isCommitted()) {
			throw new IllegalStateException("Set buffer size after commit");
		}

		_metaData._bufferSize = bufferSize;

		super.setBufferSize(bufferSize);
	}

	@Override
	public void setCharacterEncoding(String charsetName) {
		if (isCommitted()) {
			return;
		}

		_setCharacterEncoding(charsetName);
	}

	@Override
	public void setContentLength(int contentLength) {
		if (isCommitted()) {
			return;
		}

		_metaData._contentLength = contentLength;

		super.setContentLength(contentLength);
	}

	public void setContentLengthLong(long contentLengthLong) {
		int contentLength = Math.toIntExact(contentLengthLong);

		setContentLength(contentLength);
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

			_metaData._contentType = firstPart.trim();

			index = contentType.indexOf("charset=");

			if (index != -1) {
				String charsetName = contentType.substring(index + 8);

				charsetName = charsetName.trim();

				_setCharacterEncoding(charsetName);
			}
			else {
				_metaData._charsetName = null;
			}
		}
		else {
			_metaData._contentType = contentType;

			_metaData._charsetName = null;
		}

		super.setContentType(contentType);
	}

	@Override
	public void setDateHeader(String name, long value) {
		Set<Header> values = new HashSet<>();

		_metaData._headers.put(name, values);

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

		Set<Header> values = new HashSet<>();

		_metaData._headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setHeader(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		Set<Header> values = new HashSet<>();

		_metaData._headers.put(name, values);

		Header header = new Header(value);

		values.add(header);

		super.setIntHeader(name, value);
	}

	@Override
	public void setLocale(Locale locale) {
		if (isCommitted()) {
			return;
		}

		_metaData._locale = locale;

		super.setLocale(locale);
	}

	@Override
	public void setStatus(int status) {
		if (isCommitted()) {
			return;
		}

		_metaData._status = status;

		super.setStatus(status);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void setStatus(int status, String statusMessage) {
		if (isCommitted()) {
			return;
		}

		_metaData._status = status;
		_metaData._statusMessage = statusMessage;

		super.setStatus(status, statusMessage);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{bufferSize=");
		sb.append(_metaData._bufferSize);
		sb.append(", charsetName=");
		sb.append(_metaData._charsetName);
		sb.append(", committed=");
		sb.append(_committed);
		sb.append(", contentLength=");
		sb.append(_metaData._contentLength);
		sb.append(", contentType=");
		sb.append(_metaData._contentType);
		sb.append(", error=");
		sb.append(_metaData._error);
		sb.append(", errorMessage=");
		sb.append(_metaData._errorMessage);
		sb.append(", headers=");
		sb.append(_metaData._headers);
		sb.append(", location=");
		sb.append(_metaData._location);
		sb.append(", locale=");
		sb.append(_metaData._locale);
		sb.append(", status=");
		sb.append(_metaData._status);
		sb.append("}");

		return sb.toString();
	}

	public static class MetaData implements Serializable {

		private int _bufferSize;
		private String _charsetName;
		private int _contentLength = -1;
		private String _contentType;
		private boolean _error;
		private String _errorMessage;
		private final Map<String, Set<Header>> _headers = new HashMap<>();
		private Locale _locale;
		private String _location;
		private int _status = SC_OK;
		private String _statusMessage;

	}

	protected static void resetThrough(HttpServletResponse response) {
		if (response instanceof MetaInfoCacheServletResponse) {
			MetaInfoCacheServletResponse metaInfoCacheServletResponse =
				(MetaInfoCacheServletResponse)response;

			resetThrough(
				(HttpServletResponse)
					metaInfoCacheServletResponse.getResponse());
		}
		else {
			response.reset();
		}
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

	private void _setCharacterEncoding(String charsetName) {
		if (calledGetWriter) {
			return;
		}

		if (charsetName == null) {
			return;
		}

		_metaData._charsetName = charsetName;

		super.setCharacterEncoding(charsetName);
	}

	private boolean _committed;
	private final MetaData _metaData = new MetaData();

}