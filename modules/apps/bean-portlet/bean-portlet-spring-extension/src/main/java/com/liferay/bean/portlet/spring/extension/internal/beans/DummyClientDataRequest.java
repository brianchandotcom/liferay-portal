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

import java.io.BufferedReader;
import java.io.InputStream;

import java.util.Collection;

import javax.portlet.ClientDataRequest;

import javax.servlet.http.Part;

/**
 * @author Neil Griffin
 */
public class DummyClientDataRequest
	extends DummyPortletRequest implements ClientDataRequest {

	public static final ClientDataRequest INSTANCE =
		new DummyClientDataRequest();

	@Override
	public String getCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getContentLength() {
		throw new UnsupportedOperationException();
	}

	@Override
	public long getContentLengthLong() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContentType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getMethod() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Part getPart(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Part> getParts() {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getPortletInputStream() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BufferedReader getReader() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCharacterEncoding(String encoding) {
	}

}