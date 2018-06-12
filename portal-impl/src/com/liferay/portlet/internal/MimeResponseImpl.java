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

package com.liferay.portlet.internal;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Locale;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public abstract class MimeResponseImpl
	extends PortletResponseImpl implements MimeResponse {

	@Override
	public void flushBuffer() throws IOException {
		response.flushBuffer();

		_calledFlushBuffer = true;
	}

	@Override
	public int getBufferSize() {
		return response.getBufferSize();
	}

	@Override
	public CacheControl getCacheControl() {
		Portlet portlet = getPortlet();

		int expirationTime = 0;

		Integer expCache = portlet.getExpCache();

		if (expCache != null) {
			expirationTime = expCache;
		}

		return new CacheControlImpl(null, expirationTime, false, false, this);
	}

	@Override
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return _contentType;
	}

	@Override
	public Locale getLocale() {
		return portletRequestImpl.getLocale();
	}

	@Override
	public OutputStream getPortletOutputStream()
		throws IllegalStateException, IOException {

		if (_calledGetWriter) {
			throw new IllegalStateException(
				"Unable to obtain OutputStream because Writer is already in " +
					"use");
		}

		if (_contentType == null) {
			setContentType(portletRequestImpl.getResponseContentType());
		}

		_calledGetPortletOutputStream = true;

		return response.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IllegalStateException, IOException {
		if (_calledGetPortletOutputStream) {
			throw new IllegalStateException(
				"Unable to obtain Writer because OutputStream is already in " +
					"use");
		}

		if (_contentType == null) {
			setContentType(portletRequestImpl.getResponseContentType());
		}

		_calledGetWriter = true;

		return response.getWriter();
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
		return response.isCommitted();
	}

	@Override
	public void reset() {
		if (_calledFlushBuffer) {
			throw new IllegalStateException(
				"Unable to reset a buffer that has been flushed");
		}
	}

	@Override
	public void resetBuffer() {
		if (_calledFlushBuffer) {
			throw new IllegalStateException(
				"Unable to reset a buffer that has been flushed");
		}

		response.resetBuffer();
	}

	@Override
	public void setBufferSize(int bufferSize) {
		response.setBufferSize(bufferSize);
	}

	@Override
	public void setContentType(String contentType) {
		if (_calledGetPortletOutputStream || _calledGetWriter) {
			return;
		}

		if (Validator.isNull(contentType)) {
			throw new IllegalArgumentException("Content type is null");
		}

		String lifecycle = getLifecycle();
		WindowState windowState = portletRequestImpl.getWindowState();

		if (!contentType.startsWith(
				portletRequestImpl.getResponseContentType()) &&
			!lifecycle.equals(PortletRequest.RESOURCE_PHASE) &&
			!windowState.equals(LiferayWindowState.EXCLUSIVE)) {

			throw new IllegalArgumentException(
				contentType + " is an unsupported content type");
		}

		_contentType = contentType;

		response.setContentType(contentType);
	}

	private boolean _calledFlushBuffer;
	private boolean _calledGetPortletOutputStream;
	private boolean _calledGetWriter;
	private String _contentType;

}