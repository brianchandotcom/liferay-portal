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

package com.liferay.portal.kernel.jsonwebservice;

import java.lang.reflect.Method;

/**
 * @author Miguel Pastor
 */
public interface JSONWebServiceScannerStrategy {

	public MethodDescriptor[] scan(Object service);

	public class MethodDescriptor {

		public MethodDescriptor(Method method) {
			_method = method;

			_clazz = method.getDeclaringClass();
		}

		public Class<?> getDeclaringClass() {
			return _clazz;
		}

		public Method getMethod() {
			return _method;
		}

		private final Class<?> _clazz;
		private final Method _method;

	}

}