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

	public ServiceBeanMethodInvocation(
		AopMethod aopMethod, Object[] arguments) {

		_aopMethod = aopMethod;
		_arguments = arguments;
	}

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

	public Object[] getArguments() {
		return _arguments;
	}

	public int getIndex() {
		return _index;
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

	public Object proceed() throws Throwable {
		ChainableMethodAdvice[] chainableMethodAdvices =
			_aopMethod.getChainableMethodAdvices();

		if (_index < chainableMethodAdvices.length) {
			return chainableMethodAdvices[_index++].invoke(this);
		}

		return _aopMethod.invoke(_arguments);
	}

	public void setIndex(int index) {
		_index = index;
	}

	@Override
	public String toString() {
		return _aopMethod.toString();
	}

	private final AopMethod _aopMethod;
	private final Object[] _arguments;
	private int _index;

}