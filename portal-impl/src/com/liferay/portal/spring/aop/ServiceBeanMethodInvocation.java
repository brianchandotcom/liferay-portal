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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanMethodInvocation {

	@SuppressWarnings("unchecked")
	public <T> T getAdviceMethodContext() {
		return (T)_adviceMethodContext;
	}

	public Method getMethod() {
		return _method;
	}

	public Object getThis() {
		return _target;
	}

	public Object proceed(Object[] arguments) throws Throwable {
		if (_nextChainableMethodAdvice == null) {
			try {
				return _method.invoke(_target, arguments);
			}
			catch (InvocationTargetException ite) {
				throw ite.getTargetException();
			}
		}

		return _nextChainableMethodAdvice.invoke(
			_nextServiceBeanMethodInvocation, arguments);
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		Class<?>[] parameterTypes = _method.getParameterTypes();

		StringBundler sb = new StringBundler(parameterTypes.length * 2 + 6);

		Class<?> declaringClass = _method.getDeclaringClass();

		sb.append(declaringClass.getName());

		sb.append(StringPool.PERIOD);
		sb.append(_method.getName());
		sb.append(StringPool.OPEN_PARENTHESIS);

		for (Class<?> parameterType : parameterTypes) {
			sb.append(parameterType.getName());

			sb.append(StringPool.COMMA);
		}

		if (parameterTypes.length > 0) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		sb.append(StringPool.AT);

		Class<?> targetClass = _target.getClass();

		sb.append(targetClass.getName());

		_toString = sb.toString();

		return _toString;
	}

	protected ServiceBeanMethodInvocation(
		Object target, Method method, Object adviceMethodContext,
		ChainableMethodAdvice nextChainableMethodAdvice,
		ServiceBeanMethodInvocation nextServiceBeanMethodInvocation) {

		_target = target;
		_method = method;

		_method.setAccessible(true);

		_adviceMethodContext = adviceMethodContext;
		_nextChainableMethodAdvice = nextChainableMethodAdvice;
		_nextServiceBeanMethodInvocation = nextServiceBeanMethodInvocation;
	}

	private final Object _adviceMethodContext;
	private final Method _method;
	private final ChainableMethodAdvice _nextChainableMethodAdvice;
	private final ServiceBeanMethodInvocation _nextServiceBeanMethodInvocation;
	private final Object _target;
	private String _toString;

}