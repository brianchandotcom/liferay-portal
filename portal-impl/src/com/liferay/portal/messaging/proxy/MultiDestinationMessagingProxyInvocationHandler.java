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

package com.liferay.portal.messaging.proxy;

import com.liferay.portal.kernel.messaging.proxy.BaseMultiDestinationProxyBean;
import com.liferay.portal.kernel.messaging.proxy.ProxyModeThreadLocal;
import com.liferay.portal.kernel.messaging.proxy.ProxyRequest;
import com.liferay.portal.spring.aop.InvocationHandlerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class MultiDestinationMessagingProxyInvocationHandler
	implements InvocationHandler {

	public static InvocationHandlerFactory getInvocationHandlerFactory() {
		return _invocationHandlerFactory;
	}

	public MultiDestinationMessagingProxyInvocationHandler(
		BaseMultiDestinationProxyBean baseMultiDestinationProxyBean) {

		_baseMultiDestinationProxyBean = baseMultiDestinationProxyBean;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		ProxyRequest proxyRequest = new ProxyRequest(method, args);

		if (proxyRequest.isSynchronous() ||
			ProxyModeThreadLocal.isForceSync()) {

			return _baseMultiDestinationProxyBean.synchronousSend(proxyRequest);
		}
		else {
			_baseMultiDestinationProxyBean.send(proxyRequest);

			return null;
		}
	}

	private static final InvocationHandlerFactory _invocationHandlerFactory =
		new InvocationHandlerFactory() {

			@Override
			public InvocationHandler createInvocationHandler(Object obj) {
				return new MultiDestinationMessagingProxyInvocationHandler(
					(BaseMultiDestinationProxyBean)obj);
			}

		};

	private final BaseMultiDestinationProxyBean _baseMultiDestinationProxyBean;

}