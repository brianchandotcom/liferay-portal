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

package com.liferay.shielded.container.internal.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Shuyang Zhou
 */
public class HttpSessionListenerInvocationHandlerWrapper
	implements InvocationHandler {

	public HttpSessionListenerInvocationHandlerWrapper(
		InvocationHandler invocationHandler, ProxyFactory proxyFactory,
		ClassLoader classLoader) {

		_invocationHandler = invocationHandler;
		_proxyFactory = proxyFactory;
		_classLoader = classLoader;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		if (_sessionDestroyedMethod.equals(method)) {
			HttpSessionEvent httpSessionEvent = (HttpSessionEvent)args[0];

			HttpSession httpSession = httpSessionEvent.getSession();

			args[0] = new HttpSessionEvent(
				_proxyFactory.createASMWrapper(
					_classLoader, HttpSession.class,
					new HttpSessionDelegate(httpSession), httpSession));
		}

		return _invocationHandler.invoke(proxy, method, args);
	}

	private static final Method _sessionDestroyedMethod;

	static {
		try {
			_sessionDestroyedMethod = HttpSessionListener.class.getMethod(
				"sessionDestroyed", HttpSessionEvent.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new ExceptionInInitializerError(noSuchMethodException);
		}
	}

	private final ClassLoader _classLoader;
	private final InvocationHandler _invocationHandler;
	private final ProxyFactory _proxyFactory;

}