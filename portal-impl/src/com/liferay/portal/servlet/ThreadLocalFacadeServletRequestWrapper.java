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

package com.liferay.portal.servlet;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Closeable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalFacadeServletRequestWrapper
	extends ServletRequestWrapper implements Closeable {

	public ThreadLocalFacadeServletRequestWrapper(
		ServletRequestWrapper servletRequestWrapper,
		ServletRequest nextServletRequest) {

		super(nextServletRequest);

		_servletRequestWrapper = servletRequestWrapper;

		_nextServletRequestThreadLocal.set(nextServletRequest);

		_locales = new ArrayList<>();

		Enumeration<Locale> enumeration = nextServletRequest.getLocales();

		while (enumeration.hasMoreElements()) {
			_locales.add(enumeration.nextElement());
		}
	}

	@Override
	public void close() {
		if (_servletRequestWrapper != null) {
			ServletRequest nextServletRequest =
				_nextServletRequestThreadLocal.get();

			_servletRequestWrapper.setRequest(nextServletRequest);
		}
	}

	@Override
	public Object getAttribute(String name) {
		ServletRequest servletRequest = getRequest();

		return servletRequest.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		ServletRequest servletRequest = getRequest();

		Lock lock = (Lock)servletRequest.getAttribute(
			WebKeys.PARALLEL_RENDERING_MERGE_LOCK);

		if (lock != null) {
			lock.lock();
		}

		try {
			return servletRequest.getAttributeNames();
		}
		finally {
			if (lock != null) {
				lock.unlock();
			}
		}
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return Collections.enumeration(_locales);
	}

	@Override
	public ServletRequest getRequest() {
		return _nextServletRequestThreadLocal.get();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String uri) {
		ServletRequest servletRequest = getRequest();

		return servletRequest.getRequestDispatcher(uri);
	}

	@Override
	public void removeAttribute(String name) {
		ServletRequest servletRequest = getRequest();

		servletRequest.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object o) {
		ServletRequest servletRequest = getRequest();

		servletRequest.setAttribute(name, o);
	}

	@Override
	public void setRequest(ServletRequest servletRequest) {
		_nextServletRequestThreadLocal.set(servletRequest);
	}

	private static final ThreadLocal<ServletRequest>
		_nextServletRequestThreadLocal = new CentralizedThreadLocal<>(
			ThreadLocalFacadeServletRequestWrapper.class +
				"._nextServletRequestThreadLocal",
			null, Function.identity(), true);

	private final List<Locale> _locales;
	private final ServletRequestWrapper _servletRequestWrapper;

}