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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.io.Closeable;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalFacadeHttpServletRequestWrapper
	extends HttpServletRequestWrapper implements Closeable {

	public ThreadLocalFacadeHttpServletRequestWrapper(
		ServletRequestWrapper superWrapper, HttpServletRequest nextRequest) {

		super(nextRequest);

		_nextRequestThreadLocal.set(nextRequest);
		_superWrapper = superWrapper;
	}

	public void close() {
		if (_superWrapper != null) {
			HttpServletRequest nextRequest = _nextRequestThreadLocal.get();

			_superWrapper.setRequest(nextRequest);
		}
	}

	@Override
	public Object getAttribute(String name) {
		return getRequest().getAttribute(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		return getRequest().getAttributeNames();
	}

	@Override
	public ServletRequest getRequest() {
		return _nextRequestThreadLocal.get();
	}

	@Override
	public void removeAttribute(String name) {
		getRequest().removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		getRequest().setAttribute(name, o);
	}

	@Override
	public void setRequest(ServletRequest request) {
		_nextRequestThreadLocal.set((HttpServletRequest)request);
	}

	private static ThreadLocal<HttpServletRequest> _nextRequestThreadLocal =
		new AutoResetThreadLocal<HttpServletRequest>(
			ThreadLocalFacadeHttpServletRequestWrapper.class +
				"._nextRequestThreadLocal") {

			@Override
			protected HttpServletRequest copy(HttpServletRequest request) {
				return request;
			}

		};

	private ServletRequestWrapper _superWrapper;

}