/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (InvocationTargetException invocationTargetException) {
				throw invocationTargetException.getTargetException();
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

		StringBundler sb = new StringBundler((parameterTypes.length * 2) + 6);

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