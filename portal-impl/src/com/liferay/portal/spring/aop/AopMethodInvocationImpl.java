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
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class AopMethodInvocationImpl implements AopMethodInvocation {

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdviceMethodContext() {
		return (T)_adviceMethodContext;
	}

	@Override
	public Method getMethod() {
		return _method;
	}

	@Override
	public Object getThis() {
		return _target;
	}

	@Override
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
			_nextAopMethodInvocation, arguments);
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

	protected AopMethodInvocationImpl(
		Object target, Method method, Object adviceMethodContext,
		ChainableMethodAdvice nextChainableMethodAdvice,
		AopMethodInvocation nextAopMethodInvocation) {

		_target = target;

		_method = method;

		_method.setAccessible(true);

		_adviceMethodContext = adviceMethodContext;
		_nextChainableMethodAdvice = nextChainableMethodAdvice;
		_nextAopMethodInvocation = nextAopMethodInvocation;
	}

	private final Object _adviceMethodContext;
	private final Method _method;
	private final AopMethodInvocation _nextAopMethodInvocation;
	private final ChainableMethodAdvice _nextChainableMethodAdvice;
	private final Object _target;
	private String _toString;

}