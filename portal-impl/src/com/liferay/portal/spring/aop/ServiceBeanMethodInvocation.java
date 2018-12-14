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

import java.lang.reflect.Method;

import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanMethodInvocation {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceBeanMethodInvocation)) {
			return false;
		}

		ServiceBeanMethodInvocation serviceBeanMethodInvocation =
			(ServiceBeanMethodInvocation)obj;

		if (Objects.equals(
				_aopMethod, serviceBeanMethodInvocation._aopMethod)) {

			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAdviceMethodContext() {
		return (T)_adviceMethodContext;
	}

	public Method getMethod() {
		return _aopMethod.getMethod();
	}

	public Object getThis() {
		return _aopMethod.getTarget();
	}

	@Override
	public int hashCode() {
		return _aopMethod.hashCode();
	}

	public Object proceed(Object[] arguments) throws Throwable {
		if (_nextChainableMethodAdvice == null) {
			return _aopMethod.invoke(arguments);
		}

		return _nextChainableMethodAdvice.invoke(
			_nextServiceBeanMethodInvocation, arguments);
	}

	@Override
	public String toString() {
		return _aopMethod.toString();
	}

	protected ServiceBeanMethodInvocation(
		AopMethod aopMethod, Object adviceMethodContext,
		ChainableMethodAdvice nextChainableMethodAdvice,
		ServiceBeanMethodInvocation nextServiceBeanMethodInvocation) {

		_aopMethod = aopMethod;
		_adviceMethodContext = adviceMethodContext;
		_nextChainableMethodAdvice = nextChainableMethodAdvice;
		_nextServiceBeanMethodInvocation = nextServiceBeanMethodInvocation;
	}

	private final Object _adviceMethodContext;
	private final AopMethod _aopMethod;
	private final ChainableMethodAdvice _nextChainableMethodAdvice;
	private final ServiceBeanMethodInvocation _nextServiceBeanMethodInvocation;

}