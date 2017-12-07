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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;

/**
 * @author Shuyang Zhou
 */
public class ServiceWrapperProxyUtil {

	public static Closeable createProxy(
			Object springServiceProxy, Class<?> serviceWrapperClass)
		throws Exception {

		if (!ProxyUtil.isProxyClass(springServiceProxy.getClass())) {
			throw new IllegalArgumentException(
				springServiceProxy + " is not a Spring service proxy");
		}

		final AdvisedSupport advisedSupport =
			ServiceBeanAopProxy.getAdvisedSupport(springServiceProxy);

		final TargetSource targetSource = advisedSupport.getTargetSource();

		final Object previousService = targetSource.getTarget();

		Constructor<?>[] constructors =
			serviceWrapperClass.getDeclaredConstructors();

		Constructor<?> constructor = constructors[0];

		constructor.setAccessible(true);

		advisedSupport.setTargetSource(
			new SingletonTargetSource(
				constructor.newInstance(previousService)));

		return new Closeable() {

			@Override
			public void close() throws IOException {
				advisedSupport.setTargetSource(targetSource);

				try {
					targetSource.releaseTarget(previousService);
				}
				catch (Exception e) {
					throw new IOException(e);
				}
			}

		};
	}

	public static Closeable injectFieldProxy(
			Object springServiceProxy, String fieldName, Class<?> wrapperClass)
		throws Exception {

		if (!ProxyUtil.isProxyClass(springServiceProxy.getClass())) {
			throw new IllegalArgumentException(
				springServiceProxy + " is not a Spring service proxy");
		}

		AdvisedSupport advisedSupport = ServiceBeanAopProxy.getAdvisedSupport(
			springServiceProxy);

		TargetSource targetSource = advisedSupport.getTargetSource();

		final Object targetService = targetSource.getTarget();

		Class<?> clazz = targetService.getClass();

		Field field = null;

		while (clazz != null) {
			try {
				field = ReflectionUtil.getDeclaredField(clazz, fieldName);

				break;
			}
			catch (NoSuchFieldException nsfe) {
				clazz = clazz.getSuperclass();
			}
		}

		if (field == null) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to locate field ", fieldName, " in ",
					String.valueOf(targetService)));
		}

		final Field finalField = field;

		final Object previousValue = finalField.get(targetService);

		Constructor<?>[] constructors = wrapperClass.getDeclaredConstructors();

		Constructor<?> constructor = constructors[0];

		constructor.setAccessible(true);

		finalField.set(targetService, constructor.newInstance(previousValue));

		return new Closeable() {

			@Override
			public void close() throws IOException {
				try {
					finalField.set(targetService, previousValue);
				}
				catch (ReflectiveOperationException roe) {
					throw new IOException(roe);
				}
			}

		};
	}

}