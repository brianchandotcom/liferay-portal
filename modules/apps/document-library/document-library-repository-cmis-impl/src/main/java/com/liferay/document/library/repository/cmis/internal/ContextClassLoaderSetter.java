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

package com.liferay.document.library.repository.cmis.internal;

import java.io.Closeable;

/**
 * @author Adolfo Pérez
 */
public class ContextClassLoaderSetter implements Closeable {

	public ContextClassLoaderSetter(ClassLoader classLoader) {
		Thread currentThread = Thread.currentThread();

		_originalClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);
	}

	@Override
	public void close() {
		Thread currentThread = Thread.currentThread();

		currentThread.setContextClassLoader(_originalClassLoader);
	}

	private final ClassLoader _originalClassLoader;

}