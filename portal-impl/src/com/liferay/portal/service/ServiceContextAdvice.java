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

package com.liferay.portal.service;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.spring.aop.ChainableMethodAdvice;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

/**
 * @author Preston Crary
 */
public class ServiceContextAdvice extends ChainableMethodAdvice {

	@Override
	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation)
		throws Throwable {

		Integer index = _indexCache.get(serviceBeanMethodInvocation.getMethod());

		if (index == null) {
			return serviceBeanMethodInvocation.proceed();
		}

		Object[] arguments = serviceBeanMethodInvocation.getArguments();

		ServiceContext serviceContext = (ServiceContext)arguments[index];

		if (serviceContext != null) {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		try {
			return serviceBeanMethodInvocation.proceed();
		}
		finally {
			if (serviceContext != null) {
				ServiceContextThreadLocal.popServiceContext();
			}
		}
	}

	@Override
	public boolean isEnabled(Class<?> targetClass, Method method) {
		int index = _getServiceContextParameterIndex(method);

		if (index == -1) {
			return false;
		}

		_indexCache.put(method, index);

		return true;
	}

	private final Map<Method, Integer> _indexCache = new ConcurrentHashMap<>();

	private int _getServiceContextParameterIndex(Method method) {
		Class<?>[] parameterTypes = method.getParameterTypes();

		for (int i = parameterTypes.length - 1; i >= 0; i--) {
			if (ServiceContext.class.isAssignableFrom(parameterTypes[i])) {
				return i;
			}
		}

		return -1;
	}

}