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
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments, Object result)
		throws Throwable {
	}

	public void afterThrowing(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments, Throwable throwable)
		throws Throwable {
	}

	public Object before(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		return null;
	}

	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		return nullResult;
	}

	public void duringFinally(
		ServiceBeanMethodInvocation serviceBeanMethodInvocation,
		Object[] arguments) {
	}

	public Object invoke(
			ServiceBeanMethodInvocation serviceBeanMethodInvocation,
			Object[] arguments)
		throws Throwable {

		Object returnValue = before(serviceBeanMethodInvocation, arguments);

		if (returnValue != null) {
			if (returnValue == nullResult) {
				return null;
			}

			return returnValue;
		}

		try {
			returnValue = serviceBeanMethodInvocation.proceed(arguments);

			afterReturning(serviceBeanMethodInvocation, arguments, returnValue);
		}
		catch (Throwable throwable) {
			afterThrowing(serviceBeanMethodInvocation, arguments, throwable);

			throw throwable;
		}
		finally {
			duringFinally(serviceBeanMethodInvocation, arguments);
		}

		return returnValue;
	}

	protected static Object nullResult = new Object();

}