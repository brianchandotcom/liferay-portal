/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.resource;

import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class ContextClassLoaderResourceRetriever implements ResourceRetriever {

	public ContextClassLoaderResourceRetriever(String fileName) {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		_inputStream = contextClassLoader.getResourceAsStream(fileName);
	}

	@Override
	public InputStream getInputStream() {
		return _inputStream;
	}

	private InputStream _inputStream;

}