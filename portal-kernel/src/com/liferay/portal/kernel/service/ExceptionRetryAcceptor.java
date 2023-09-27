/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class ExceptionRetryAcceptor implements RetryAcceptor {

	public static final String EXCEPTION_NAME = "EXCEPTION_NAME";

	@Override
	public boolean acceptException(
		Throwable throwable, Map<String, String> propertyMap) {

		String name = propertyMap.get(EXCEPTION_NAME);

		if (name == null) {
			throw new IllegalArgumentException(
				"Missing property " + EXCEPTION_NAME);
		}

		while (true) {
			Class<?> clazz = throwable.getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			if (classLoader == null) {
				classLoader = ClassLoader.getSystemClassLoader();
			}

			try {
				Class<?> exceptionClass = classLoader.loadClass(name);

				if (exceptionClass.isInstance(throwable)) {
					return true;
				}
			}
			catch (ClassNotFoundException classNotFoundException) {
			}

			Throwable causeThrowable = throwable.getCause();

			if ((throwable == causeThrowable) || (causeThrowable == null)) {
				break;
			}

			throwable = causeThrowable;
		}

		return false;
	}

	@Override
	public boolean acceptResult(
		Object returnValue, Map<String, String> propertyMap) {

		return false;
	}

}