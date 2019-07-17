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

package com.liferay.portal.kernel.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * Provides method advice to implement an aspect for services.
 *
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public abstract class ChainableMethodAdvice {

	/**
	 * Creates the context to be used when invoking this advice. The context can
	 * be useful for caching information derived from reflective calls on the
	 * method. Returning <code>null</code> disables this advice for all
	 * invocations on the target class and method. The context object can be
	 * obtained by calling {@link AopMethodInvocation#getAdviceMethodContext()}.
	 * The context should be immutable as it is reused by concurrent calls to
	 * {@link #invoke(AopMethodInvocation, Object[])}.
	 *
	 * @param  targetClass the target class for the context
	 * @param  method the method for the context
	 * @param  annotations a map of the method's annotations
	 * @return the context object for use during method invocations or
	 *         <code>null</code> to disable this advice for the method
	 */
	public abstract Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations);

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

	protected void afterReturning(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Object result)
		throws Throwable {
	}

	protected void afterThrowing(
			AopMethodInvocation aopMethodInvocation, Object[] arguments,
			Throwable throwable)
		throws Throwable {
	}

	protected Object before(
			AopMethodInvocation aopMethodInvocation, Object[] arguments)
		throws Throwable {

		return null;
	}

	protected void duringFinally(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {
	}

	protected static Object nullResult = new Object();

}