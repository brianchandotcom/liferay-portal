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

package com.liferay.portal.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ServiceBeanMethodInvocationFactory;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class ServiceBeanMethodInvocationFactoryImpl
	implements ServiceBeanMethodInvocationFactory {

	public void proceed(
			Object target, Class<?> targetClass, Method method,
			Object[] arguments)
		throws Exception {

		ServiceBeanMethodInvocation serviceBeanMethodInvocation = create(
			target, targetClass, method, arguments);

		List<MethodInterceptor> methodInterceptors = getMethodInterceptors();

		serviceBeanMethodInvocation.setMethodInterceptors(methodInterceptors);

		try {
			serviceBeanMethodInvocation.proceed();
		}
		catch (Throwable t) {
			throw new Exception(t);
		}
	}

	protected ServiceBeanMethodInvocation create(
		Object target, Class<?> targetClass, Method method,
		Object[] arguments) {

		return new ServiceBeanMethodInvocation(
			target, targetClass, method, arguments);

	}

	protected List<MethodInterceptor> getMethodInterceptors() {
		if (_methodInterceptors != null) {
			return _methodInterceptors;
		}

		List<MethodInterceptor> methodInterceptors =
			new ArrayList<MethodInterceptor>();

		MethodInterceptor methodInterceptor =
			(MethodInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		methodInterceptors.add(methodInterceptor);

		_methodInterceptors = methodInterceptors;

		return _methodInterceptors;
	}

	private List<MethodInterceptor> _methodInterceptors;

}