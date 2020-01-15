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

import java.util.Collection;

import javax.portlet.PortletResponse;

import javax.servlet.http.Cookie;

import org.w3c.dom.Element;

/**
 * @author Neil Griffin
 */
public class DummyPortletResponse implements PortletResponse {

	public static final PortletResponse INSTANCE = new DummyPortletResponse();

	@Override
	public void addProperty(Cookie cookie) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addProperty(String key, Element element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addProperty(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Element createElement(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeURL(String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNamespace() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getProperty(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getPropertyNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> getPropertyValues(String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setProperty(String key, String value) {
		throw new UnsupportedOperationException();
	}

}