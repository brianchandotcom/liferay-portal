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

package com.liferay.bean.portlet.spring.extension.internal.beans;

import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.Locale;

import javax.portlet.ActionURL;
import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;
import javax.portlet.ResourceURL;

/**
 * @author Neil Griffin
 */
public class DummyMimeResponse
	extends DummyPortletResponse implements MimeResponse {

	public static final MimeResponse INSTANCE = new DummyMimeResponse();

	@Override
	public <T extends PortletURL & ActionURL> T createActionURL() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ActionURL createActionURL(Copy copy) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends PortletURL & RenderURL> T createRenderURL() {
		throw new UnsupportedOperationException();
	}

	@Override
	public RenderURL createRenderURL(Copy copy) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResourceURL createResourceURL() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void flushBuffer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getBufferSize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public CacheControl getCacheControl() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContentType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	@Override
	public OutputStream getPortletOutputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PrintWriter getWriter() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCommitted() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetBuffer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBufferSize(int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContentType(String contentType) {
		throw new UnsupportedOperationException();
	}

}