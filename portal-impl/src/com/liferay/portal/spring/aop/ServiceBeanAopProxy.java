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

package com.liferay.portal.spring.aop;

import com.liferay.portal.kernel.spring.aop.AdvisedSupport;
import com.liferay.portal.transaction.TransactionsUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopProxy
	implements AdvisedSupportProxy, InvocationHandler {

	public ServiceBeanAopProxy(
		AdvisedSupport advisedSupport,
		ServiceBeanAopCacheManager serviceBeanAopCacheManager) {

		_advisedSupport = advisedSupport;
		_serviceBeanAopCacheManager = serviceBeanAopCacheManager;
	}

	@Override
	public AdvisedSupport getAdvisedSupport() {
		return new AdvisedSupport() {

			@Override
			public Class<?>[] getProxiedInterfaces() {
				return _advisedSupport.getProxiedInterfaces();
			}

			@Override
			public Object getTarget() {
				return _advisedSupport.getTarget();
			}

			@Override
			public void setTarget(Object target) {
				_advisedSupport.setTarget(target);

				_serviceBeanAopCacheManager.reset();
			}

		};
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(
				_advisedSupport.getTarget(), method, arguments);

		if (TransactionsUtil.isEnabled()) {
			serviceBeanMethodInvocation.setMethodInterceptors(
				_serviceBeanAopCacheManager.getMethodInterceptors(
					serviceBeanMethodInvocation));
		}
		else {
			serviceBeanMethodInvocation.setMethodInterceptors(
				_emptyMethodInterceptors);
		}

		return serviceBeanMethodInvocation.proceed();
	}

	private static final MethodInterceptor[] _emptyMethodInterceptors =
		new MethodInterceptor[0];

	private final AdvisedSupport _advisedSupport;
	private final ServiceBeanAopCacheManager _serviceBeanAopCacheManager;

}