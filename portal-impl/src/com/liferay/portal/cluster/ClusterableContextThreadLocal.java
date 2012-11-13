/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.util.InitialThreadLocal;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class ClusterableContextThreadLocal {

	public static void putThreadLocalContext(String key, Serializable value) {
		Map<String, Serializable> context = _threadLocalContext.get();

		context.put(key, value);
	}

	protected static Map<String, Serializable> collectThreadLocalContext() {
		Map<String, Serializable> context = _threadLocalContext.get();

		_threadLocalContext.remove();

		return context;
	}

	private static ThreadLocal<HashMap<String, Serializable>>
		_threadLocalContext =
			new InitialThreadLocal<HashMap<String, Serializable>>(
				ClusterableContextThreadLocal.class.getName(),
				new HashMap<String, Serializable>(), true);

}