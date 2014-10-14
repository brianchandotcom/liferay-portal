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

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.util.InitialThreadLocal;

/**
 * @author Shuyang Zhou
 */
public class PortalContextLoaderLifecycleThreadLocal {

	public static boolean isDestroying() {
		return _destroying.get();
	}

	public static boolean isInitializing() {
		return _initializing.get();
	}

	public static void setDestroying(boolean destroying) {
		_destroying.set(destroying);
	}

	public static void setInitializing(boolean initializing) {
		_initializing.set(initializing);
	}

	private static final ThreadLocal<Boolean> _destroying =
		new InitialThreadLocal<Boolean>(
			PortalContextLoaderLifecycleThreadLocal.class +
				"._destroying",
			Boolean.FALSE);
	private static final ThreadLocal<Boolean> _initializing =
		new InitialThreadLocal<Boolean>(
			PortalContextLoaderLifecycleThreadLocal.class +
				"._initializing",
			Boolean.FALSE);

}