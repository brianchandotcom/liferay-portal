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

	public PersistentHttpServletRequestWrapper(HttpServletRequest request) {

		super(request);
	}

	public ServletRequest getRealRequest() {
		if (_forwardCompatibleInvocationHandler == null) {
			return super.getRequest();
		}
		else {
			return (ServletRequest)_forwardCompatibleInvocationHandler._target;
		}
	}

	public ServletRequest getRequest() {
		if (_forwardCompatibleInvocationHandler == null) {
			// First get, insert proxy wrapper to prevent appserver
			// trespass our request wrapper chain.

			_forwardCompatibleInvocationHandler =
				new ForwardCompatibleInvocationHandler(super.getRequest());

			// Generate a proxy request that is only type of HttpServletRequest,
			// not type of HttpServletRequestWrapper
			ServletRequest proxyRequest =
				(ServletRequest)ProxyUtil.newProxyInstance(
					HttpServletRequest.class.getClassLoader(),
					new Class[]{HttpServletRequest.class},
					_forwardCompatibleInvocationHandler);

			super.setRequest(proxyRequest);

			return proxyRequest;
		}
		else {
			return super.getRequest();
		}
	}

	public void setRealRequest(ServletRequest request) {
		if (_forwardCompatibleInvocationHandler == null) {
			super.setRequest(request);
		}
		else {
			_forwardCompatibleInvocationHandler.setTarget(request);
		}
	}

	private ForwardCompatibleInvocationHandler
		_forwardCompatibleInvocationHandler;

	// This InvocationHandler is required to support Servlet 3.0 API without
	// directly referring it.
	private class ForwardCompatibleInvocationHandler
		implements InvocationHandler {

		public ForwardCompatibleInvocationHandler(Object target) {
			_target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			return method.invoke(_target, args);
		}

		public void setTarget(Object target) {
			_target = target;
		}

		private Object _target;
	}

}