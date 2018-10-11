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

package com.liferay.portal.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ServiceBeanMethodInvocationFactory;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.aop.ServiceBeanMethodInvocation;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.portal.spring.transaction.TransactionInvokerImpl}
 */
@Deprecated
public class ServiceBeanMethodInvocationFactoryImpl
	implements ServiceBeanMethodInvocationFactory {

	@Override
	public Object proceed(
			Object target, Class<?> targetClass, Method method,
			Object[] arguments, String[] methodInterceptorBeanIds)
		throws Exception {

		if (ArrayUtil.isEmpty(methodInterceptorBeanIds)) {
			throw new IllegalArgumentException(
				"Method interceptor bean IDs array is empty");
		}

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			new ServiceBeanMethodInvocation(target, method, arguments);

		MethodInterceptor[] methodInterceptors = _getMethodInterceptors(
			methodInterceptorBeanIds);

		serviceBeanMethodInvocation.setMethodInterceptors(methodInterceptors);

		try {
			return serviceBeanMethodInvocation.proceed();
		}
		catch (Throwable t) {
			if (t instanceof Exception) {
				throw (Exception)t;
			}

			throw new Exception(t);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected ServiceBeanMethodInvocation create(
		Object target, Class<?> targetClass, Method method,
		Object[] arguments) {

		return new ServiceBeanMethodInvocation(target, method, arguments);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected List<MethodInterceptor> getMethodInterceptors(
		String... methodInterceptorBeanIds) {

		return Arrays.asList(_getMethodInterceptors(methodInterceptorBeanIds));
	}

	private MethodInterceptor[] _getMethodInterceptors(
		String... methodInterceptorBeanIds) {

		String methodInterceptorsKey = StringUtil.merge(
			methodInterceptorBeanIds);

		MethodInterceptor[] methodInterceptors = _methodInterceptors.get(
			methodInterceptorsKey);

		if (methodInterceptors != null) {
			return methodInterceptors;
		}

		methodInterceptors = new MethodInterceptor[
			methodInterceptorBeanIds.length];

		for (int i = 0; i < methodInterceptorBeanIds.length; i++) {
			String methodInterceptorBeanId = methodInterceptorBeanIds[i];

			MethodInterceptor methodInterceptor =
				(MethodInterceptor)PortalBeanLocatorUtil.locate(
					methodInterceptorBeanId);

			methodInterceptors[i] = methodInterceptor;
		}

		_methodInterceptors.put(methodInterceptorsKey, methodInterceptors);

		return methodInterceptors;
	}

	private final Map<String, MethodInterceptor[]> _methodInterceptors =
		new HashMap<>();

}