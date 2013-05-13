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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class MimeResponseImpl
	extends PortletResponseImpl implements MimeResponse {

	@Override
	public void flushBuffer() throws IOException {
		_response.flushBuffer();

		_calledFlushBuffer = true;
	}

	@Override
	public int getBufferSize() {
		return _response.getBufferSize();
	}

	@Override
	public CacheControl getCacheControl() {
		return new CacheControlImpl(null, 0, false, false, this);
	}

	@Override
	public String getCharacterEncoding() {
		return _response.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return _contentType;
	}

	@Override
	public Locale getLocale() {
		return _portletRequestImpl.getLocale();
	}

	@Override
	public OutputStream getPortletOutputStream() throws IOException {
		if (_calledGetWriter) {
			throw new IllegalStateException(
				"Cannot obtain OutputStream because Writer is already in use");
		}

		if (_contentType == null) {
			setContentType(_portletRequestImpl.getResponseContentType());
		}

		_calledGetPortletOutputStream = true;

		return _response.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (_calledGetPortletOutputStream) {
			throw new IllegalStateException(
				"Cannot obtain Writer because OutputStream is already in use");
		}

		if (_contentType == null) {
			setContentType(_portletRequestImpl.getResponseContentType());
		}

		_calledGetWriter = true;

		return _response.getWriter();
	}

	public boolean isCalledFlushBuffer() {
		return _calledFlushBuffer;
	}

	public boolean isCalledGetPortletOutputStream() {
		return _calledGetPortletOutputStream;
	}

	public boolean isCalledGetWriter() {
		return _calledGetWriter;
	}

	@Override
	public boolean isCommitted() {
		return false;
	}

	@Override
	public void reset() {
		if (_calledFlushBuffer) {
			throw new IllegalStateException(
				"Cannot reset a buffer that has been flushed");
		}
	}

	@Override
	public void resetBuffer() {
		if (_calledFlushBuffer) {
			throw new IllegalStateException(
				"Cannot reset a buffer that has been flushed");
		}

		_response.resetBuffer();
	}

	@Override
	public void setBufferSize(int bufferSize) {
		_response.setBufferSize(bufferSize);
	}

	@Override
	public void setContentType(String contentType) {
		if (Validator.isNull(contentType)) {
			throw new IllegalArgumentException("Content type cannot be null");
		}

		Enumeration<String> enu = _portletRequestImpl.getResponseContentTypes();

		boolean valid = false;

		if (getLifecycle().equals(PortletRequest.RESOURCE_PHASE) ||
			_portletRequestImpl.getWindowState().equals(
				LiferayWindowState.EXCLUSIVE)) {

			valid = true;
		}
		else {
			while (enu.hasMoreElements()) {
				String resContentType = enu.nextElement();

				if (contentType.startsWith(resContentType)) {
					valid = true;

					break;
				}
			}
		}

		if (!valid) {
			throw new IllegalArgumentException(
				contentType + " is not a supported mime type");
		}

		_contentType = contentType;

		_response.setContentType(contentType);
	}

	@Override
	protected void init(
		PortletRequestImpl portletRequestImpl, HttpServletResponse response,
		String portletName, long companyId, long plid) {

		super.init(portletRequestImpl, response, portletName, companyId, plid);

		_portletRequestImpl = portletRequestImpl;
		_response = response;
	}

	private boolean _calledFlushBuffer;
	private boolean _calledGetPortletOutputStream;
	private boolean _calledGetWriter;
	private String _contentType;
	private PortletRequestImpl _portletRequestImpl;
	private HttpServletResponse _response;

}