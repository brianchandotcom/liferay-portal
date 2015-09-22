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

package com.liferay.portal.service;

import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class ExceptionRetryAcceptor implements RetryAcceptor {

	public static final String EXCEPTION_NAME = "EXCEPTION_NAME";

	@Override
	public boolean acceptException(
		Throwable t, Map<String, String> propertyMap) {

		String name = propertyMap.get(EXCEPTION_NAME);

		if (name == null) {
			throw new IllegalArgumentException(
				"Missing property: " + EXCEPTION_NAME);
		}

		while (true) {
			Class<?> clazz = t.getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			try {
				Class<?> exceptionClass = classLoader.loadClass(name);

				if (exceptionClass.isInstance(t)) {
					return true;
				}
			}
			catch (ClassNotFoundException cnfe) {
			}

			Throwable cause = t.getCause();

			if ((t == cause) || (cause == null)) {
				break;
			}

			t = cause;
		}

		return false;
	}

	@Override
	public boolean acceptResult(
		Object returnValue, Map<String, String> propertyMap) {

		return false;
	}

}