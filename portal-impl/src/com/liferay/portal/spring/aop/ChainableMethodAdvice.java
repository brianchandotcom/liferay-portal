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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class ChainableMethodAdvice {

	public void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {
	}

	public void afterThrowing(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Throwable throwable)
		throws Throwable {
	}

	public Object before(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		return null;
	}

	public abstract Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations);

	public void duringFinally(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {
	}

	public Object invoke(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		Object returnValue = before(aopMethodInvocation, arguments);

		if (returnValue != null) {
			if (returnValue == nullResult) {
				return null;
			}

			return returnValue;
		}

		try {
			returnValue = aopMethodInvocation.proceed(arguments);

			afterReturning(aopMethodInvocation, arguments, returnValue);
		}
		catch (Throwable throwable) {
			afterThrowing(aopMethodInvocation, arguments, throwable);

			throw throwable;
		}
		finally {
			duringFinally(aopMethodInvocation, arguments);
		}

		return returnValue;
	}

	protected static Object nullResult = new Object();

}