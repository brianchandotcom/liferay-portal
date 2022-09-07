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

package com.liferay.portal.test.function;

/**
 * @author Raymond Augé
 */
public class ThreadContextClassLoaderCloseableHolder
	extends CloseableHolder<ClassLoader> {

	public ThreadContextClassLoaderCloseableHolder(Class<?> clazz)
		throws Exception {

		this(clazz.getClassLoader());
	}

	public ThreadContextClassLoaderCloseableHolder(ClassLoader classLoader)
		throws Exception {

		super(
			originalClassLoader -> {
				Thread currentThread = Thread.currentThread();

				currentThread.setContextClassLoader(originalClassLoader);
			},
			() -> {
				Thread currentThread = Thread.currentThread();

				ClassLoader originalClassLoader =
					currentThread.getContextClassLoader();

				currentThread.setContextClassLoader(classLoader);

				return originalClassLoader;
			});
	}

}