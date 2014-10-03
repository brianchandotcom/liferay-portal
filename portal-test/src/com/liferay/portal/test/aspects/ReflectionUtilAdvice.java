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

package com.liferay.portal.test.aspects;

import com.liferay.portal.kernel.util.ReflectionUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Shuyang Zhou
 */
@Aspect
public class ReflectionUtilAdvice {

	public static void setDeclaredFieldThrowable(
		Throwable declaredFieldThrowable) {

		_declaredFieldThrowable = declaredFieldThrowable;
	}

	public static void setDeclaredMethodThrowable(
			Throwable declaredMethodThrowable)
		throws ClassNotFoundException {

		Class.forName(
			ReflectionUtil.class.getName(), true,
			ReflectionUtil.class.getClassLoader());

		_declaredMethodThrowable = declaredMethodThrowable;
	}

	@Around(
		"execution(public static java.lang.reflect.Field " +
			"com.liferay.portal.kernel.util.ReflectionUtil." +
				"getDeclaredField(Class, String))")
	public Object getDeclaredField(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		if (_declaredFieldThrowable != null) {
			throw _declaredFieldThrowable;
		}

		return proceedingJoinPoint.proceed();
	}

	@Around(
		"execution(public static java.lang.reflect.Method " +
			"com.liferay.portal.kernel.util.ReflectionUtil." +
				"getDeclaredMethod(Class, String, Class...))")
	public Object getDeclaredMethod(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		if (_declaredMethodThrowable != null) {
			throw _declaredMethodThrowable;
		}

		return proceedingJoinPoint.proceed();
	}

	private static Throwable _declaredFieldThrowable;
	private static Throwable _declaredMethodThrowable;

}