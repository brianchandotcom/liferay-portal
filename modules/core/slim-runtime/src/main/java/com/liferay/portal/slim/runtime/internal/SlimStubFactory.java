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

package com.liferay.portal.slim.runtime.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.liferay.portal.kernel.util.ProxyUtil;

/**
 * @author Raymond Augé
 */
public class SlimStubFactory {

	public static Object createStub(Class<?> clazz) {
		return ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {clazz},
			new StubInvocationHandker(clazz));
	}

	public static class StubInvocationHandker implements InvocationHandler {

		public StubInvocationHandker(Class<?> clazz) {
			_clazz = clazz;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			throw new UnsupportedOperationException(
				"In the slim runtime the component <" + _clazz.getName() +
					"> is not supported!");
		}

		private final Class<?> _clazz;

	}

}