/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.util;

import java.util.function.Supplier;

/**
 * @author André de Oliveira
 */
public class ClassLoaderUtil {

	public static <T> T getWithContextClassLoader(
		Supplier<T> supplier, Class<?> clazz) {

		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		thread.setContextClassLoader(clazz.getClassLoader());

		try {
			return supplier.get();
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

}