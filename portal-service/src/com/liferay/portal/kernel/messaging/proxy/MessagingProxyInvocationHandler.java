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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class MessagingProxyInvocationHandler implements InvocationHandler {

	public static InvocationHandlerFactory getInvocationHandlerFactory() {
		return _invocationHandlerFactory;
	}

	public MessagingProxyInvocationHandler(BaseProxyBean baseProxyBean) {
		_baseProxyBean = baseProxyBean;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		ProxyRequest proxyRequest = new ProxyRequest(method, args);

		if (proxyRequest.isSynchronous() ||
			ProxyModeThreadLocal.isForceSync()) {

			return _baseProxyBean.synchronousSend(proxyRequest);
		}
		else {
			_baseProxyBean.send(proxyRequest);

			return null;
		}
	}

	private static final InvocationHandlerFactory _invocationHandlerFactory =
		new InvocationHandlerFactory() {

		@Override
		public InvocationHandler createInvocationHandler(Object obj) {
			return new MessagingProxyInvocationHandler((BaseProxyBean)obj);
		}

	};

	private final BaseProxyBean _baseProxyBean;

}