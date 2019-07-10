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

package com.liferay.portal.vulcan.internal.context;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Shuyang Zhou
 */
public class LazyProxyUtil {

	public static <T> T createProxy(
		Class<T> clazz, UnsafeSupplier<T, PortalException> unsafeSupplier) {

		ClassLoader classLoader = clazz.getClassLoader();

		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}

		return (T)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {clazz},
			new LazyInvocationHandler(unsafeSupplier));
	}

	private static class LazyInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			if (_target == null) {
				_target = _unsafeSupplier.get();
			}

			return method.invoke(_target, args);
		}

		private LazyInvocationHandler(
			UnsafeSupplier<?, PortalException> unsafeSupplier) {

			_unsafeSupplier = unsafeSupplier;
		}

		private Object _target;
		private final UnsafeSupplier<?, PortalException> _unsafeSupplier;

	}

}