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

package com.liferay.portal.kernel.messaging.proxy;

import com.liferay.portal.kernel.spring.aop.InvocationHandlerFactory;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class MessagingProxyInvocationHandler implements InvocationHandler {

	public static Object createProxy(BaseProxyBean baseProxyBean) {
		Class<?> beanClass = baseProxyBean.getClass();

		Thread currentThread = Thread.currentThread();

		return ProxyUtil.newProxyInstance(
			currentThread.getContextClassLoader(), beanClass.getInterfaces(),
			new MessagingProxyInvocationHandler(baseProxyBean));
	}

	public static InvocationHandlerFactory getInvocationHandlerFactory() {
		return _invocationHandlerFactory;
	}

	public MessagingProxyInvocationHandler(BaseProxyBean baseProxyBean) {
		_baseProxyBean = baseProxyBean;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		return _baseProxyBean.synchronousSend(new ProxyRequest(method, args));
	}

	private static final InvocationHandlerFactory _invocationHandlerFactory =
		new InvocationHandlerFactory() {

			@Override
			public InvocationHandler createInvocationHandler(Object object) {
				return new MessagingProxyInvocationHandler(
					(BaseProxyBean)object);
			}

		};

	private final BaseProxyBean _baseProxyBean;

}