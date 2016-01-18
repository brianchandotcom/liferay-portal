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

package com.liferay.portal.wab.extender.internal.adapter;

import com.liferay.osgi.util.classloader.PassThroughClassLoader;

import java.util.concurrent.Callable;

/**
 * @author Carlos Sierra Andrés
 */
public class TCCLUtil {

	public static void wrapTCCL(Callable<Void> callable) throws Exception {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		// Disable Equinox's boot class loader delegation

		ClassLoader classLoader = new PassThroughClassLoader(
			contextClassLoader);

		try {
			thread.setContextClassLoader(classLoader);

			callable.call();
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

}