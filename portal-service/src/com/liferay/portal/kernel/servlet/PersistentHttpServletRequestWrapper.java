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

import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Shuyang Zhou
 */
public class PersistentHttpServletRequestWrapper
	extends HttpServletRequestWrapper {

	public PersistentHttpServletRequestWrapper(
		HttpServletRequest request, boolean stopTrespassing) {

		super(request);

		_stopTrespassing = stopTrespassing;
	}

	public ServletRequest getRealRequest() {
		if (_servlet30SupportInvocationHandler != null) {
			return (ServletRequest)_servlet30SupportInvocationHandler._target;
		}

		return super.getRequest();
	}

	public ServletRequest getRequest() {
		if (!_stopTrespassing || (_servlet30SupportInvocationHandler != null)) {
			return super.getRequest();
		}

		// Insert a proxy wrapper to prevent the application server from
		// trespassing our request wrapper chain

		_servlet30SupportInvocationHandler =
			new Servlet30SupportInvocationHandler(super.getRequest());

		// Generate a proxy request that is only type of HttpServletRequest and
		// not of type HttpServletRequestWrapper

		ServletRequest servletRequest =
			(ServletRequest)ProxyUtil.newProxyInstance(
				HttpServletRequest.class.getClassLoader(),
				new Class[] {HttpServletRequest.class},
				_servlet30SupportInvocationHandler);

		super.setRequest(servletRequest);

		return servletRequest;
	}

	public void setRealRequest(ServletRequest servletRequest) {
		if (_servlet30SupportInvocationHandler != null) {
			_servlet30SupportInvocationHandler.setTarget(servletRequest);
		}
		else {
			super.setRequest(servletRequest);
		}
	}

	private Servlet30SupportInvocationHandler
		_servlet30SupportInvocationHandler;
	private boolean _stopTrespassing;

	private class Servlet30SupportInvocationHandler
		implements InvocationHandler {

		public Servlet30SupportInvocationHandler(Object target) {
			_target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			return method.invoke(_target, arguments);
		}

		public void setTarget(Object target) {
			_target = target;
		}

		private Object _target;
	}

}